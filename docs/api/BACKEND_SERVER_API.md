# ScholarMatch Client — Server Integration Guide

**Server Base URL:** `https://scholarmatch-server-production.up.railway.app`

The client needs one environment variable:
```
SERVER_URL=https://scholarmatch-server-production.up.railway.app
```

It's optional — `AppBuilder` defaults to the Railway URL above. There is also one optional variable:
```
OFFLINE_MODE=true   # force in-memory fake data (LocalServerRepository) instead of the real server
```

When `OFFLINE_MODE` is unset, the client probes `GET /api/health` on startup (up to 3 retries, 2 seconds apart, since a cold start on Railway's free tier often returns a 502 on the first request). If the probe fails, the client falls back to `LocalServerRepository` automatically. In other words: as long as the server is reachable, the client talks to the real API, not local fake data.

The real HTTP implementations live under `src/main/java/com/scholarmatch/frameworks/data_access_object/server/`: `ServerHttpClient` (shared token-attaching HTTP layer), `AuthGateway`, `ProfileGateway`, `AccountSettingsGateway`, `MatchingGateway`, `MessagingGateway`, `PostingGateway`. `SemanticScholarGateway` (one level up, not under `server/`) is the Semantic Scholar implementation. Every request/response format below was checked against the actual server source in `/Users/austin/项目/scholarmatch-server` (controllers, services, DTOs, `schema.sql`), not guessed or copied from the server repo's own `API.md` — see the notes throughout for places where the server's own docs disagree with its actual code.

---

## Authentication

Login/register return a JWT from the server. Every subsequent request that needs an authenticated session carries it in the header:
```
Authorization: Bearer <token>
```

The token is valid for 7 days (`app.jwt.expiration-ms`, default `604800000`). The server has no roles/authorities — "has a valid token" is the only check; there is no per-endpoint permission tier beyond that. The server reads the caller's identity from the token subject, never from a client-supplied id, so every "auth required" endpoint below only ever acts on the caller's own account/applications/postings.

**Missing/invalid/expired token → `401`** with body `{"error": "Missing or invalid authentication token."}` (a custom `AuthenticationEntryPoint`, not Spring's bare default 403).

Only `/api/health` and `/api/auth/**` are reachable without a token; every other path requires one.

---

## Endpoint Summary

| Purpose | Method | Path | Auth required |
|---|---|---|---|
| Request a registration verification code | POST | `/api/auth/request-verification-code` | No |
| Register | POST | `/api/auth/register` | No |
| Login | POST | `/api/auth/login` | No |
| Request an email-change verification code | POST | `/api/account/email-change/request-code` | Yes |
| Change email | PUT | `/api/account/email` | Yes |
| Change password | PUT | `/api/account/password` | Yes |
| Get own profile | GET | `/api/profile` | Yes |
| Update profile | PUT | `/api/profile` | Yes |
| Delete account (irreversible) | DELETE | `/api/profile` | Yes |
| View another scholar's public profile | GET | `/api/scholars/{scholarId}/public-profile` | Yes |
| Get recommendation list | GET | `/api/recommend` | Yes |
| Connect (send collaboration request) | POST | `/api/connect` | Yes |
| Dislike (reject, one-directional, never triggers a match) | POST | `/api/dislike` | Yes |
| Get matched users | GET | `/api/matches` | Yes |
| Create a posting | POST | `/api/postings` | Yes |
| Browse/list postings | GET | `/api/postings?scope=ALL_ACTIVE\|MINE` | Yes |
| Apply to a posting | POST | `/api/postings/{postingId}/apply` | Yes |
| Close a posting | POST | `/api/postings/{postingId}/close` | Yes |
| Accept an application | POST | `/api/postings/applications/{applicationId}/accept` | Yes |
| Decline an application | POST | `/api/postings/applications/{applicationId}/decline` | Yes |
| List my own applications | GET | `/api/postings/applications/mine` | Yes |
| Send a message (only open between matched users) | POST | `/api/messages` | Yes |
| Get full conversation with a user (chronological, unpaginated) | GET | `/api/messages/{otherScholarId}` | Yes |
| Health check | GET | `/api/health` | No |

Each section below covers method, path, request body, response body, and known gotchas.

---

## Auth

### POST /api/auth/request-verification-code

**Request body:**
```json
{ "email": "string" }
```
**Response 200:** empty body.

**Behavior:** normalizes the email (trim + lowercase), generates a six-digit code, sends it via Resend, then stores an in-memory challenge (10-minute expiry, 3 attempts, `Purpose.REGISTER`). If Resend delivery fails, the challenge is never stored and the request fails as a `500` (see Error Format). No rate limiting on this endpoint — rate limiting only applies to the authenticated email-change flow below.

### POST /api/auth/register

**Request body:**
```json
{
  "firstName": "string",           // required
  "lastName": "string",            // required
  "email": "string",               // required
  "password": "string",            // required, minimum 6 characters
  "code": "string",                // required — the code from request-verification-code
  "phoneNumber": "string",         // optional
  "institution": "string",         // optional
  "academicLevel": "string",       // optional, free text — see the enum-table caveat below
  "researchField": "string",       // optional, free text
  "lookingFor": "string",          // optional, free text
  "collaborationDescription": "string",  // optional
  "researchDescription": "string",       // optional, used to generate the embedding
  "weeklyAvailabilityHours": 0,          // optional, integer
  "fundingStatus": "string",             // optional, free text
  "researchInterests": ["string"],       // optional
  "papers": [{ "title": "string", "doi": "string" }]  // optional
}
```

**Response 200:**
```json
{ "token": "eyJ...", "scholarId": "uuid", "name": "First Last", "avatarUrl": null }
```
`avatarUrl` is always `null` on register — there is no avatar upload at registration time. The field is `name`, not `displayName` — the server's own `API.md` shows an example with `displayName`, but the actual `AuthResponse` record and the actual `ProfileService`/`AuthService` code both use `name`; the client reads `node.get("name")`. Trust this document (or the server source) over the server repo's own `API.md`.

**The client currently only collects and sends 5 of these fields** (`firstName`, `lastName`, `email`, `password`, `code` — see `RegisterAccountData`/`AuthGateway`); the rest is filled in later from Edit Profile. This is safe: every field besides the first five is `@Valid`-unannotated and nullable on the server's `RegisterRequest`, so omitting them does not trigger a validation error. (An earlier version of this document flagged `academicLevel`/`researchField`/`lookingFor`/`fundingStatus` as server-required `@NotBlank` fields that would 400 if the client omitted them — that has since been relaxed to nullable server-side; this is no longer an active mismatch.)

**Behavior:** `400 InvalidRequestException` if the email is already registered (exact match). Verifies `code` against the stored `REGISTER` challenge — `400` with messages like `"Verification code is incorrect. N attempts remaining."`, `"...No attempts remaining. Request a new code."`, `"Verification code has expired. Request a new code."`, or `"Request a verification code for this email before registering."` if no challenge exists. `academicEmailVerified` is computed server-side from the email's domain (`AcademicEmailDomainService`, its own copy of a recognized-domains list) — never trusted from the client. Best-effort embeds the profile text via Jina immediately after saving; a Jina failure here is only logged, registration still succeeds.

### POST /api/auth/login

**Request body:**
```json
{ "email": "string", "password": "string" }
```

**Response 200:** same shape as register (`token` / `scholarId` / `name` / `avatarUrl`, this time populated from the stored profile).

**On failure** (unknown email or wrong password), `400`:
```json
{ "error": "Email not found" }
```
or
```json
{ "error": "Incorrect password" }
```

---

## Account Settings (requires `Authorization: Bearer <token>`)

All three endpoints read the scholar id from the JWT — never from a path or body parameter, so a caller can only ever change their own account.

### POST /api/account/email-change/request-code

**Request body:**
```json
{ "newEmail": "string" }
```
**Response 204:** empty body.

**Behavior:** `400` if `newEmail` equals the current email; `409 ConflictException` `{"error": "Email is already registered"}` if another scholar already owns it. **Rate-limited**: at most 3 requests per 15-minute sliding window, keyed independently by both the calling scholar and the target email — hitting either cap returns `429` `{"error": "Too many verification code requests. Try again later."}`. A new request overwrites any previous unconsumed code for the same (scholar, email) pair. The code is delivered via Resend, 10-minute expiry, 3 attempts, and is scoped to `(scholarId, email, Purpose.CHANGE_EMAIL)` — it cannot be reused for registration, and cannot be used by or against a different scholar.

### PUT /api/account/email

**Request body:**
```json
{ "newEmail": "string", "verificationCode": "string", "currentPassword": "string" }
```
**Response 200:**
```json
{ "scholarId": "uuid", "email": "string", "academicEmailVerified": true }
```
(Deliberately excludes password hash and JWT data.)

**Behavior:** `400 "Current password is incorrect."` on a bad password. Same unchanged/taken checks as the request-code step. Verifies `verificationCode` against the `CHANGE_EMAIL` challenge (same incorrect/exhausted/expired/no-challenge outcomes as registration, all `400`). The challenge is only consumed **after** the database save succeeds, so a failure between verifying and saving lets the user retry with the same already-verified code. `academicEmailVerified` is recomputed from the **new** email's domain, server-side only — same as registration, never trusted from the client.

### PUT /api/account/password

**Request body:**
```json
{ "currentPassword": "string", "newPassword": "string", "confirmNewPassword": "string" }
```
**Response 204:** empty body.

**Behavior, in order:** `400` if `currentPassword` is wrong; `400 "New password and confirmation do not match."` if `newPassword != confirmNewPassword`; `400 "Password must be at least 6 characters."`; `400 "New password must be different from your current password."` if the new password matches the existing hash. Otherwise re-hashes and saves.

---

## Profile (requires `Authorization: Bearer <token>`)

### GET /api/profile

Returns the full profile of the currently logged-in user.

**Response 200:**
```json
{
  "scholarId": "uuid",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "academicEmailVerified": true,
  "phoneNumber": "string",
  "institution": "string",
  "academicLevel": "string",
  "researchField": "string",
  "lookingFor": "string",
  "collaborationDescription": "string",
  "researchDescription": "string",
  "weeklyAvailabilityHours": 0,
  "fundingStatus": "string",
  "avatarUrl": "string | null",
  "hIndex": 0,
  "totalCitations": 0,
  "researchInterests": ["string"],
  "papers": [{ "title": "string", "doi": "string" }],
  "educations": [{ "school": "string", "degree": "string", "field": "string" }]
}
```

**Note:** `hIndex` and `totalCitations` are primitive `int` server-side, so they're never `null` — an unset value comes back as `0`.

**Note on error handling:** if the scholar id from a valid token somehow doesn't resolve to a row, the server throws a bare `IllegalArgumentException`, which is **not** caught by name — it falls through to the generic handler and returns `500`, not `404`. Don't assume a 404 here.

### PUT /api/profile

Updates the current user's profile and regenerates the embedding. Every field is optional and treated as "leave unchanged if omitted/`null`," except `papers`/`educations`, which are fully replaced (cleared and re-added) whenever the field is present and non-null.

**Request body:**
```json
{
  "institution": "string",
  "academicLevel": "string",
  "researchField": "string",
  "lookingFor": "string",
  "collaborationDescription": "string",
  "researchDescription": "string",
  "weeklyAvailabilityHours": 0,
  "fundingStatus": "string",
  "phoneNumber": "string",
  "hIndex": 0,
  "totalCitations": 0,
  "researchInterests": ["string"],
  "papers": [{ "title": "string", "doi": "string" }],
  "educations": [{ "school": "string", "degree": "string", "field": "string" }],
  "avatarBase64": "base64-encoded image (optional; uploaded to Cloudinary and returned as avatarUrl)"
}
```
`email` is deliberately absent — it can only change through `PUT /api/account/email`.

**Response 200:** same shape as `GET /api/profile`.

**`hIndex` / `totalCitations` are persisted.** The server's `UpdateProfileRequest` includes both fields, and `ProfileService.updateProfile` writes them (`if (req.hIndex() != null) scholar.setHIndex(...)`, same for `totalCitations`) whenever they're present in the request. The client's `ProfileGateway.updateProfile(...)` already sends both from the manually-entered Edit Profile fields. (An earlier version of this document reported these fields as silently dropped/never persisted — that gap has since been closed on the server; this is no longer an active issue, just confirming the fix is real end-to-end.)

`avatarBase64`, if non-blank, is uploaded to Cloudinary (`publicId = "<scholarId>.jpg"`, always overwriting the same asset) and the resulting URL becomes the new `avatarUrl`. After saving, the profile is re-embedded via Jina for future recommendation ranking; a Jina failure here is only logged, the profile update still succeeds.

### DELETE /api/profile

Permanently deletes the current user's account: clears messages sent/received, collaboration requests sent/received, and posting applications submitted elsewhere, then deletes the scholar's own postings (which cascades to applications *received* on them at the database level), then the scholar row itself. Irreversible; the email can be re-registered afterward.

**Response 204:** empty body.

---

## Public Profile (requires `Authorization: Bearer <token>`)

### GET /api/scholars/{scholarId}/public-profile

Returns another scholar's public-facing profile — used, for example, to view a posting owner's background before applying. **Any authenticated scholar can view any other scholar's public profile; there is no prior-match or prior-connect requirement for this endpoint.**

**Response 200:**
```json
{
  "scholarId": "uuid",
  "displayName": "First Last",
  "institution": "string",
  "academicLevel": "string",
  "researchField": "string",
  "lookingFor": "string",
  "collaborationDescription": "string",
  "researchDescription": "string",
  "weeklyAvailabilityHours": 0,
  "fundingStatus": "string",
  "avatarUrl": "string | null",
  "hIndex": 0,
  "totalCitations": 0,
  "researchInterests": ["string"],
  "papers": [{ "title": "string", "doi": "string" }],
  "educations": [{ "school": "string", "degree": "string", "field": "string" }],
  "academicEmailVerified": true
}
```
Unlike `ScholarDto`, this excludes `email`, `phoneNumber`, and separate `firstName`/`lastName` (combined into `displayName` instead).

**Response 404** if the scholar id doesn't exist:
```json
{ "error": "Scholar not found" }
```

---

## Recommend (requires `Authorization: Bearer <token>`)

### GET /api/recommend

Returns up to 20 scholars most similar to the current user (pgvector cosine-distance ranking over a Jina embedding of the caller's `researchDescription` and paper titles, computed fresh on every request — not cached server-side).

**Response 200:** `ScholarDto[]`, same shape as `GET /api/profile`.

Excludes anyone the caller has already recorded a decision **as sender** toward (connected, disliked, or matched) — but does **not** exclude scholars who have connected with the caller and are still awaiting a response, so a one-sided swipe never blocks the other party from discovering and reciprocating.

**Note on error handling:** a `Jina` embedding failure here propagates as a real `500` (not caught/logged-only, unlike register/profile-update) — this call fails loudly if Jina is unreachable or misconfigured.

**Client responsibilities:**
- Results are cached in memory by `RecommendInteractor`, not the server
- Each connect/dislike pops the next entry from the front of the cache; a new request is made once the cache is empty
- The client calls `getProfile()` and checks `User.isProfileComplete()` before requesting recommendations at all, to avoid generating a low-quality embedding from an empty `researchDescription`

---

## Connect / Dislike / Matches (requires `Authorization: Bearer <token>`)

### POST /api/connect

**Request body:**
```json
{ "connectedScholarId": "uuid" }
```
**Response 200:**
```json
{ "matched": true, "matchedScholar": { "...": "same shape as ScholarDto, or null when matched is false" } }
```

**Behavior:** `400 "Cannot connect with yourself"` if the target is the caller. Upserts a request (sender=caller, receiver=target) to `PENDING` — never downgrades an existing `ACCEPTED` row. If the reverse row (target → caller) already exists as `PENDING` or `ACCEPTED`, this is a mutual match: both directional rows are updated to `ACCEPTED` and the response includes the matched scholar's full profile. A `REJECTED` reverse row never counts as a match.

**Note: the client doesn't currently use `matchedScholar`.** `MatchingGateway.connect(...)` only reads the `matched` boolean; the full profile already included in the response is discarded. If a "You matched with X" notification is ever built to show the other scholar's details immediately, this field already carries what's needed — no extra profile fetch required.

### POST /api/dislike

**Request body:**
```json
{ "dislikedScholarId": "uuid" }
```
**Response 200:** empty body.

**Behavior:** `400 "Cannot dislike yourself"`. Upserts (sender=caller, receiver=target) to `REJECTED` — never downgrades an existing `ACCEPTED` row, so disliking someone you've already matched with does not silently unmatch them. One-directional; never triggers a match.

### GET /api/matches

**Response 200:** `ScholarDto[]`.

Returns every scholar the caller has a mutual (`ACCEPTED` in both directions) match with.

**Note: this endpoint returns duplicate entries — the client must deduplicate.** A mutual match leaves two accepted rows (caller→other and other→caller); the server's query matches on "caller is either sender or receiver," so the same counterpart is returned once per row — twice total. `MatchingGateway.getMatches()` already deduplicates by scholar id using a `LinkedHashMap`; don't drop that step if this call is ever reimplemented. (Confirmed still true by reading the current `SwipeService.getMatches` — this has not been fixed server-side.)

---

## Postings & Applications (requires `Authorization: Bearer <token>`)

### POST /api/postings

**Request body:**
```json
{
  "title": "string",              // required
  "description": "string",
  "researchField": "string",       // free text
  "collaborationType": "string",   // free text
  "maxApplicants": 0               // optional; omit/null = unlimited capacity
}
```
**Response 200:** a `PostingDto` (see shape below) with `applications: null` — creating a posting never returns a nested application list.

### GET /api/postings?scope=ALL_ACTIVE|MINE

`scope` defaults to `ALL_ACTIVE` if omitted; any value other than `ALL_ACTIVE`/`MINE` returns `400 "Unknown scope: <value>"`.

- **`ALL_ACTIVE`**: every open posting **not created by the caller**, ordered newest first. Each item's `applications` is `null` — Opportunities never exposes who applied to a posting you don't own.
- **`MINE`**: every posting the caller created (open or closed), ordered newest first. Each item's `applications` **is** populated with the full applicant list for that posting.

**Response 200:** `PostingDto[]`:
```json
{
  "postingId": "uuid",
  "posterUserId": "uuid",
  "posterName": "string",
  "posterAcademicEmailVerified": true,
  "title": "string",
  "description": "string",
  "researchField": "string",
  "collaborationType": "string",
  "maxApplicants": 0,
  "applicantCount": 0,
  "createdAt": "2026-07-10T12:34:56",
  "active": true,
  "full": false,
  "closed": false,
  "applications": null
}
```

### POST /api/postings/{postingId}/apply

**Request body** (optional — may be omitted entirely):
```json
{ "message": "string" }
```
**Response 200:** a `PostingApplicationDto` (see shape under "my applications" below).

**Behavior, in order:** `400 "Posting not found"`; `400 "You cannot apply to your own posting"`; `400 "This posting has been closed"` or `400 "This posting is full"`; `400 "You have already applied to this posting"` (one application per (posting, applicant) pair, enforced in application code, no database constraint). On success, `applicantCount` is incremented — **this happens even for an application that is later declined**; a posting's fullness is based on total applications ever received, not accepted ones. Concurrent applies to the same posting are serialized with a database advisory lock so two applicants can't both slip into the last available slot.

### POST /api/postings/{postingId}/close

No request body. `400 "Posting not found"`; `400 "Only the poster can close this posting"`; `400 "This posting is already closed"`. Otherwise permanently closes it — **there is no reopen endpoint.**

**Response 200:** the updated `PostingDto` (with `applications` populated, since only the poster can reach this endpoint successfully).

### POST /api/postings/applications/{applicationId}/accept
### POST /api/postings/applications/{applicationId}/decline

No request body for either. `400 "Application not found"`; `400 "Only the poster can review applications for this posting"`; `400 "This application has already been reviewed"` (status must currently be `PENDING`, so each application can only be decided once, and a decision can't be reversed through these endpoints).

**Important:** accepting one applicant does **not** automatically decline the posting's other pending applicants — each is reviewed independently, by design. Accept/decline also do not change `applicantCount` or a posting's fullness; a posting can be "full" purely from pending applications, before any are accepted.

**Response 200:**
```json
{
  "applicationId": "uuid",
  "postingId": "uuid",
  "postingTitle": "string",
  "applicantUserId": "uuid",
  "applicantName": "string",
  "message": "string",
  "status": "PENDING | ACCEPTED | REJECTED",
  "appliedAt": "2026-07-10T12:34:56",
  "posterUserId": "uuid",
  "posterName": "string",
  "posterAcademicEmailVerified": true
}
```

### GET /api/postings/applications/mine

**Response 200:** `PostingApplicationDto[]`, most recently applied first. If a referenced posting was since deleted, `postingTitle`/`posterUserId`/`posterName` degrade to `null` rather than the request failing.

---

## Messages (requires `Authorization: Bearer <token>`)

Chat is only open between two scholars with a confirmed mutual match — enforced identically on both endpoints below.

### POST /api/messages

**Request body:**
```json
{ "receiverId": "uuid", "content": "string" }
```
**Response 200:**
```json
{ "messageId": "uuid", "senderId": "uuid", "receiverId": "uuid", "content": "string", "sentAt": "2026-07-10T12:34:56" }
```
`sentAt` is a timezone-free `LocalDateTime` (`yyyy-MM-ddTHH:mm:ss`); the client parses it directly — don't treat it as timezone-aware ISO-8601.

**If the two users have not matched, response 400:**
```json
{ "error": "You can only message users you have matched with" }
```

### GET /api/messages/{otherScholarId}

Returns the full conversation between the caller and `otherScholarId`, ordered chronologically. Not paginated. Same match-gate `400` as sending applies to reading.

**Response 200:** `MessageDto[]`, same per-item shape as the send response.

---

## Health

### GET /api/health

No token required.
```json
{ "status": "ok" }
```
The client probes this on startup to decide whether to fall back to `OFFLINE_MODE`.

---

## Error Format

Default shape:
```json
{ "error": "description" }
```

| Status | Meaning |
|---|---|
| 400 | Invalid request (bad credentials, business-rule rejection, bean-validation failure) |
| 401 | Missing/invalid/expired token |
| 404 | Resource genuinely not found (only for the handful of endpoints that throw `ResourceNotFoundException` — see the per-endpoint notes above for the ones that don't) |
| 409 | State conflict — currently only "email already registered to a different scholar" |
| 429 | Rate limit exceeded — currently only the email-change verification code request |
| 500 | Unexpected server error; the client only ever sees a generic message, never the real exception |

**Exception:** a handful of older service methods throw bare `IllegalArgumentException`/`IllegalStateException` instead of the app's own exception types (`GET /api/profile` when the token's scholar id somehow doesn't resolve; `GET /api/recommend` on the same condition; a misconfigured Resend integration). These aren't caught by name, so they fall through to the generic handler and return `500` with a generic message — not the status code you might expect from the underlying cause. When one of these shows up, check the HTTP status code directly; don't expect a matching `error` message.

---

## Enum-Like Fields — Client Convention Only, Not Server-Enforced

**The server does not validate `academicLevel`, `researchField`, `lookingFor`, `fundingStatus`, `institution`, or any posting/application/connection "status" value against a fixed list.** Every one of these is a plain `String`/`VARCHAR` column with no `CHECK` constraint (confirmed in `schema.sql`) and no corresponding Java `enum` anywhere in the server codebase (confirmed by an exhaustive `grep -rn "enum "` — the only two real enums in the entire server are internal, never serialized: `EmailVerificationChallenge.Purpose` and `EmailVerificationOutcome`). The server will happily persist and echo back **any** string sent for these fields.

The value lists below are a **client-side-only convention** — the client's own enums parse a server response by strictly matching `name()`, and fall back to a default value if the string doesn't match one of these constants. Sending a string outside this list is not rejected by the server; it's stored as-is, and the client will simply display the fallback value on the next read. Use the exact constant names below when writing Postman/test request bodies so the client-side round-trip behaves as expected — but understand that's a client convention being tested, not a server contract.

### AcademicLevel (`academicLevel`; client falls back to `GRADUATE_STUDENT` if unrecognized)

| Value | Meaning |
|---|---|
| `UNDERGRADUATE` | Undergraduate student |
| `GRADUATE_STUDENT` | Master's/PhD student |
| `POSTDOCTORAL_RESEARCHER` | Postdoc |
| `FACULTY` | Faculty at a university or research institution |
| `INDUSTRY_RESEARCHER` | Researcher in industry |

### CollaborationType (`lookingFor`; client falls back to `INTEREST_SHARING` if unrecognized)

| Value | Meaning |
|---|---|
| `CO_AUTHOR` | Looking for co-authors |
| `RESEARCH_GROUP` | Wants to join or build a research group |
| `PEER_REVIEW` | Mutual review of manuscripts/proposals |
| `MENTORSHIP` | Looking for a mentor, or willing to mentor |
| `INTEREST_SHARING` | Browsing for people with similar research interests, no specific collaboration goal |

### FundingStatus (`fundingStatus`; client falls back to `OTHER` if unrecognized)

| Value | Meaning |
|---|---|
| `SELF_FUNDED` | Self-funded |
| `INSTITUTIONAL_FUNDING` | Funded by their institution |
| `GOVERNMENT_GRANT` | Government grant |
| `INDUSTRY_SPONSORED` | Sponsored by industry |
| `SCHOLARSHIP_FELLOWSHIP` | Scholarship/fellowship |
| `UNFUNDED` | Unfunded |
| `OTHER` | Other |

### DegreeType (`educations[].degree`; client falls back to `BACHELOR` if unrecognized)

| Value | Meaning |
|---|---|
| `HIGH_SCHOOL` | High school |
| `BACHELOR` | Bachelor's |
| `MASTER` | Master's |
| `PHD` | PhD |
| `POSTDOC` | Postdoc |

### ResearchField (`researchField`; client falls back to `OTHER` if unrecognized)

75 values total, fully defined in the client's `entity/ResearchField.java`:
```
COMPUTER_SCIENCE, ARTIFICIAL_INTELLIGENCE, MACHINE_LEARNING, DATA_SCIENCE, STATISTICS,
COMPUTER_VISION, NATURAL_LANGUAGE_PROCESSING, ROBOTICS, HUMAN_COMPUTER_INTERACTION,
CYBERSECURITY, DISTRIBUTED_SYSTEMS_NETWORKING, SOFTWARE_ENGINEERING,
BIOINFORMATICS_COMPUTATIONAL_BIOLOGY, QUANTUM_COMPUTING, INFORMATION_SCIENCE_LIBRARY_SCIENCE,
MATHEMATICS, APPLIED_MATHEMATICS, PHYSICS, ASTROPHYSICS_ASTRONOMY, CHEMISTRY,
PHYSICAL_CHEMISTRY, MATERIALS_SCIENCE, NANOTECHNOLOGY, NUCLEAR_SCIENCE_ENGINEERING,
EARTH_SCIENCES_GEOLOGY, ATMOSPHERIC_CLIMATE_SCIENCE, OCEANOGRAPHY_MARINE_SCIENCE,
ENVIRONMENTAL_SCIENCE, SUSTAINABILITY_ENERGY_SYSTEMS, ECOLOGY_EVOLUTIONARY_BIOLOGY, BIOLOGY,
MOLECULAR_CELL_BIOLOGY, GENETICS_GENOMICS, MICROBIOLOGY_IMMUNOLOGY, NEUROSCIENCE, PHYSIOLOGY,
BIOMEDICAL_ENGINEERING, MEDICINE_CLINICAL_RESEARCH, PUBLIC_HEALTH_EPIDEMIOLOGY,
PHARMACOLOGY_PHARMACY, NURSING, DENTISTRY, VETERINARY_SCIENCE, NUTRITION_FOOD_SCIENCE,
AGRICULTURAL_SCIENCE, ELECTRICAL_ENGINEERING, MECHANICAL_ENGINEERING, CIVIL_ENGINEERING,
CHEMICAL_ENGINEERING, AEROSPACE_ENGINEERING, INDUSTRIAL_SYSTEMS_ENGINEERING,
ENVIRONMENTAL_ENGINEERING, ECONOMICS, POLITICAL_SCIENCE, SOCIOLOGY, ANTHROPOLOGY, PSYCHOLOGY,
COGNITIVE_SCIENCE, GEOGRAPHY, DEMOGRAPHY_POPULATION_STUDIES, INTERNATIONAL_RELATIONS,
CRIMINOLOGY, BUSINESS_MANAGEMENT, FINANCE, ACCOUNTING, MARKETING,
ENTREPRENEURSHIP_INNOVATION, LAW, PUBLIC_POLICY_ADMINISTRATION, EDUCATION, LINGUISTICS,
PHILOSOPHY, HISTORY, LITERATURE_LANGUAGES, RELIGIOUS_STUDIES_THEOLOGY, CULTURAL_STUDIES,
ART_DESIGN, MUSIC, ARCHITECTURE_URBAN_PLANNING, OTHER
```

### Institution (`institution`; client falls back to `OTHER` if unrecognized)

A large closed enum client-side (QS 2025 World University Rankings plus major research institutes, roughly 1140 values) — not listed in full here; the complete definition is in `entity/Institution.java`. Naming convention: full name, uppercased, underscored (e.g. `MIT`, `STANFORD_UNIVERSITY`, `TSINGHUA_UNIVERSITY`), fallback value `OTHER`.

### Application / connection status values (server-assigned, not client enums)

The server itself only ever assigns these literal strings (not enums, just string constants in service code): `"PENDING"`, `"ACCEPTED"`, `"REJECTED"` — reused identically for both `collaboration_requests.status` (connect/dislike/match) and `posting_applications.status` (apply/accept/decline).

---

## Third-Party API: Semantic Scholar (author search autofill)

Used for author search and paper auto-import during profile editing — unrelated to ScholarMatch's own server, so it's documented separately in [`SEMANTIC_SCHOLAR_API.md`](./SEMANTIC_SCHOLAR_API.md).

---

## Client Responsibilities (Clean Architecture layers)

The server is a plain REST API — caching, deduplication, and profile-completeness checks are all implemented client-side, with no dependency on any server-side cache.

### HTTP implementations already wired up

Under `frameworks/data_access_object/server/`: `ServerHttpClient` (shared, attaches the bearer token from `CurrentUserProviderInterface.getToken()` fresh on every request), `AuthGateway`, `ProfileGateway`, `AccountSettingsGateway`, `MatchingGateway`, `MessagingGateway`, `PostingGateway`. `SemanticScholarGateway` (one level up) is the Semantic Scholar implementation. All are working code, not placeholders. `AppBuilder` wires them in by default at startup; it only switches to `LocalServerRepository` / `LocalUserApiGateway` (in-memory fake data, for demos/offline development) when `OFFLINE_MODE=true` or the health check fails.

### DataAccessInterface per endpoint (`usecase/data_access_interface/`)

| Feature | DataAccessInterface | HTTP |
|---|---|---|
| Login | `LoginDataAccessInterface` | `POST /api/auth/login` |
| Register | `RegisterDataAccessInterface` | `POST /api/auth/register` |
| Request email verification code (register or email-change) | `VerificationEmailSenderDataAccessInterface` | `POST /api/auth/request-verification-code` (anonymous, registration) |
| Get profile | `LoadProfileDataAccessInterface` | `GET /api/profile` |
| Update profile | `UpdateProfileDataAccessInterface` | `PUT /api/profile` |
| Delete account | `DeleteAccountDataAccessInterface` | `DELETE /api/profile` |
| Change email (and its own verification-code request) | `ChangeEmailDataAccessInterface` | `PUT /api/account/email`, `POST /api/account/email-change/request-code` |
| Change password | `ChangePasswordDataAccessInterface` | `PUT /api/account/password` |
| Get recommendations | `RecommendDataAccessInterface` (also has `getProfile()`, used for the completeness check before recommending) | `GET /api/recommend` |
| Connect | `ConnectDataAccessInterface` | `POST /api/connect` |
| Dislike | `DislikeDataAccessInterface` | `POST /api/dislike` |
| Matched list | `LoadMatchesDataAccessInterface` | `GET /api/matches` |
| Create posting | `CreatePostingDataAccessInterface` | `POST /api/postings` |
| Load postings (browse or my postings) | `LoadPostingsDataAccessInterface` | `GET /api/postings?scope=...` |
| Apply to posting | `ApplyToPostingDataAccessInterface` | `POST /api/postings/{id}/apply` |
| Close posting | `ClosePostingDataAccessInterface` | `POST /api/postings/{id}/close` |
| Accept application | `AcceptApplicationDataAccessInterface` | `POST /api/postings/applications/{id}/accept` |
| Decline application | `DeclineApplicationDataAccessInterface` | `POST /api/postings/applications/{id}/decline` |
| My applications | `LoadMyApplicationsDataAccessInterface` | `GET /api/postings/applications/mine` |
| Send message | `SendMessageDataAccessInterface` | `POST /api/messages` |
| Load conversation | `LoadMessageDataAccessInterface` | `GET /api/messages/{id}` |
| Author search / paper import | `UserAPIGatewayInterface` | Semantic Scholar `/author/search`, `/author/{id}/papers` |

Interfaces are split by consumer rather than combined into one large DAO — e.g. `LoadProfileDataAccessInterface` and `UpdateProfileDataAccessInterface` are separate, so each use case only depends on the methods it actually needs. Check this directory for an existing interface before adding a new one for a new use case.

### Recommend cache (`RecommendInteractor`)
- `GET /api/recommend` returns up to 20 scholars per call and is not cached server-side; the client keeps the results in memory
- Each connect/dislike pops the next entry from the front of the cache
- A new request is made once the cache is empty
- `getProfile()` is checked for `User.isProfileComplete()` before fetching recommendations

### Session management
- `CurrentUserProvider` (`frameworks/data_access_object/`) implements `CurrentUserProviderInterface`, `SessionWriterInterface`, and `SessionClearerInterface`; it stores the JWT and user id in memory after a successful login/register
- Every interactor reads/writes session state through these interfaces rather than handling the token string directly
- Gateways read the token via `CurrentUserProviderInterface.getToken()` fresh on every request, rather than capturing it once at construction time

---

## Deployment & Secrets (server-side, not the client's concern)

| Variable | Value |
|---|---|
| PG connection | Railway's built-in Postgres (auto-injected) |
| JWT_SECRET | Set in Railway |
| JINA_API_KEY | Set in Railway (used to generate embeddings) |
| CLOUDINARY_URL | Set in Railway (used for avatar uploads) |
| RESEND_API_KEY / RESEND_FROM_EMAIL | Set in Railway (used to deliver verification codes) |

The client needs no database or third-party API key configuration in normal operation — only `SERVER_URL`, which has a default and usually doesn't need to be set. All of Resend, Jina, and Cloudinary are called exclusively by the server; the client never talks to any of them directly.
