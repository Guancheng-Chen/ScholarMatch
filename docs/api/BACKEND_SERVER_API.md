# ScholarMatch Client — Server Integration Guide

**Server Base URL:** `https://scholarmatch-server-production.up.railway.app`

The client needs one environment variable:
```
SERVER_URL=https://scholarmatch-server-production.up.railway.app
```

It's optional — `Config.build()` defaults to the Railway URL above. There is also one optional variable:
```
OFFLINE_MODE=true   # force in-memory fake data (LocalServerRepository) instead of the real server
```

When `OFFLINE_MODE` is unset, the client probes `GET /api/health` on startup (up to 3 retries, 2 seconds apart, since a cold start on Railway's free tier often returns a 502 on the first request). If the probe fails, the client falls back to `LocalServerRepository` automatically. In other words: as long as the server is reachable, the client talks to the real API, not local fake data. `ServerRepository` and `SemanticScholarGateway` are both working HTTP implementations (see `src/main/java/com/scholarmatch/frameworks/data_access_object/`); every request/response format below was checked against the actual code in those two files, not guessed.

---

## Authentication

Login/register return a JWT token from the server. Every subsequent request that needs an authenticated session carries it in the header:
```
Authorization: Bearer <token>
```

The token is valid for 7 days. Each endpoint below is marked with whether it requires one.

---

## Endpoint Summary

| Purpose | Method | Path | Auth required |
|---|---|---|---|
| Register | POST | `/api/auth/register` | No |
| Login | POST | `/api/auth/login` | No |
| Get own profile | GET | `/api/profile` | Yes |
| Update profile | PUT | `/api/profile` | Yes |
| Delete account (irreversible) | DELETE | `/api/profile` | Yes |
| Get recommendation list | GET | `/api/recommend` | Yes |
| Swipe right (send collaboration request) | POST | `/api/connect` | Yes |
| Dislike (reject, one-directional, never triggers a match) | POST | `/api/dislike` | Yes |
| Get matched users | GET | `/api/matches` | Yes |
| Send a message (only open between matched users) | POST | `/api/messages` | Yes |
| Get full conversation with a user (chronological, unpaginated) | GET | `/api/messages/{otherScholarId}` | Yes |
| Health check | GET | `/api/health` | No |

Each section below covers method, path, request body, response body, and known gotchas. Check the relevant section before writing a Postman request or implementing/verifying a `XxxDataAccessInterface`.

---

## Auth

### POST /api/auth/register

**Request body** (server `RegisterRequest`; fields marked **required** are validated with `@NotBlank` server-side and return 400 if missing):
```json
{
  "firstName": "string",           // required
  "lastName": "string",            // required
  "email": "string",               // required, validated as an email format
  "password": "string",            // required, minimum 6 characters
  "phoneNumber": "string",         // optional
  "institution": "string",         // optional, see Institution in the enum table below
  "academicLevel": "string",       // required, see enum table below (not an arbitrary string)
  "researchField": "string",       // required, see enum table below
  "lookingFor": "string",          // required, see enum table below
  "collaborationDescription": "string",  // optional
  "researchDescription": "string",       // optional, used to generate the embedding
  "weeklyAvailabilityHours": 0,          // optional, integer
  "fundingStatus": "string",             // required, see enum table below
  "researchInterests": ["string"],       // optional
  "papers": [{ "title": "string", "doi": "string" }]  // optional
}
```

**Response 200** (server `AuthResponse`):
```json
{
  "token": "eyJ...",
  "scholarId": "uuid",
  "name": "First Last",
  "avatarUrl": null
}
```

**Known client/server field mismatch — confirm this when implementing or verifying this endpoint:**
1. **The client currently only collects 4 fields.** `RegisterInputData` (`usecase/register/RegisterInputData.java`) and `RegisterController.execute(...)` only take `firstName`, `lastName`, `email`, `password` — the rest is meant to be filled in later from Edit Profile. But the server's `RegisterRequest` marks `academicLevel`, `researchField`, `lookingFor`, and `fundingStatus` as `@NotBlank`; missing them gets rejected by Spring's `@Valid` with a 400. That 400 does not go through the server's own `GlobalExceptionHandler` `{"error": "..."}` format (it only handles `InvalidRequestException`, not `MethodArgumentNotValidException` from validation failures), so it's likely Spring Boot's default error JSON (`{"timestamp","status","error":"Bad Request","path"}`, with no field-level detail). This was worked out by reading both codebases, not confirmed against a live request — sending the actual 4-field request body to `/api/auth/register` via Postman and recording the raw response and status code is the most useful thing to verify first.
2. **The response field is `name`, not `displayName`.** The server's own `API.md` (in the `scholarmatch-server` repo) shows the example response using `"displayName"`, but the server's `AuthResponse` source (`dto/AuthResponse.java`) is `record AuthResponse(String token, String scholarId, String name, String avatarUrl)`, and the client's `ServerRepository.login/register` reads `node.get("name")` — both sides of the actual code agree, the server's own documentation is just wrong here. Trust this document (or the source) over the server repo's `API.md`.

---

### POST /api/auth/login

**Request body:**
```json
{
  "email": "string",
  "password": "string"
}
```

**Response 200:** same shape as register (`token` / `scholarId` / `name` / `avatarUrl`).

**On failure** (unknown email or wrong password; server throws `InvalidRequestException`), 400:
```json
{ "error": "Email not found" }
```
or
```json
{ "error": "Incorrect password" }
```

---

## Profile (requires `Authorization: Bearer <token>`)

### GET /api/profile

Returns the full profile of the currently logged-in user.

**Response 200** (server `ScholarDto`, fields listed in source order — an earlier version of this document omitted `phoneNumber`):
```json
{
  "scholarId": "uuid",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
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

**Note:** `hIndex` and `totalCitations` are primitive `int` on the server (not `Integer` in `ScholarDto`), so they are never `null` — an unset value comes back as `0`. The client's `User` entity javadoc says "`null` if never looked up or entered," and `ServerRepository` does check `!node.get("hIndex").isNull()` — but the server never actually sends `null`, so that branch is currently dead code. Don't assume the server can return `null` here; in practice it's always `0`.

---

### PUT /api/profile

Updates the current user's profile and regenerates the embedding. All fields are optional — only send what changed; fields that are `null` or omitted are left untouched server-side. Changing `email` checks uniqueness — a 400 is returned if the new address is already taken.

**Request body** (server `UpdateProfileRequest`):
```json
{
  "email": "string",
  "institution": "string",
  "academicLevel": "string",
  "researchField": "string",
  "lookingFor": "string",
  "collaborationDescription": "string",
  "researchDescription": "string",
  "weeklyAvailabilityHours": 0,
  "fundingStatus": "string",
  "phoneNumber": "string",
  "researchInterests": ["string"],
  "papers": [{ "title": "string", "doi": "string" }],
  "educations": [{ "school": "string", "degree": "string", "field": "string" }],
  "avatarBase64": "base64-encoded image (optional; uploaded to Cloudinary and returned as avatarUrl)"
}
```

**Response 200:** same shape as `GET /api/profile`.

**Note: sending `hIndex` / `totalCitations` here has no effect.** The client's `ServerRepository.updateProfile(...)` includes `hIndex` / `totalCitations` in the request body (from the manually-entered fields in the Edit Profile form), but the server's `UpdateProfileRequest` record has no such fields — Jackson silently drops unknown fields on deserialization, and `ProfileService.updateProfile(...)` never reads or writes them. There is currently no endpoint that persists `hIndex` / `totalCitations`; values entered manually in Edit Profile only live in local memory and are lost on the next login/profile refresh. This likely needs an issue: either the server adds these fields to `UpdateProfileRequest` / `ProfileService`, or the client-side input is dead functionality — worth confirming with whoever owns the server before deciding.

---

### DELETE /api/profile

Permanently deletes the current user's account: first clears every `messages` and `collaboration_requests` row where the user is sender or receiver (neither table has `ON DELETE CASCADE`, so the server clears them manually), then deletes the `scholars` row itself. Irreversible; the email can be re-registered afterward.

**Response 204:** empty body (`ServerRepository.delete(...)` handles this case separately — a 204 has no body, so it can't call `mapper.readTree(response.body())` like the other endpoints).

---

## Recommend (requires `Authorization: Bearer <token>`)

### GET /api/recommend

Returns up to 20 scholars most similar to the current user (pgvector cosine distance over a 1024-dimension Jina embedding, computed on every request server-side — not cached).

**Response 200:** `ScholarDto[]`, same shape as `GET /api/profile`.

Any scholar with an existing `collaboration_requests` record involving the current user — in either direction, in any state (`PENDING` / `ACCEPTED` / `REJECTED`) — is excluded from the results. Users already connected, awaiting a response, or already passed on don't reappear in recommendations.

**Client responsibilities:**
- Results are cached in memory (handled by `RecommendInteractor`, not the server)
- Each connect/dislike pops the next entry from the front of the cache; a new request is made once the cache is empty, or a similarity threshold decides when to show "no more results"
- `getProfile()` is called before fetching recommendations to check `User.isProfileComplete()` — the request isn't sent at all for an incomplete profile, to avoid generating a low-quality embedding from an empty `researchDescription`

---

## Connect / Dislike / Matches (requires `Authorization: Bearer <token>`)

### POST /api/connect

The current user swipes right on a scholar (sends a collaboration request). If that scholar already has a `PENDING` request pointing at the current user (i.e. they swiped first), the server marks both records `ACCEPTED` — a match.

**Request body:**
```json
{ "connectedScholarId": "uuid" }
```

**Response 200** (server `ConnectResponse`):
```json
{
  "matched": true,
  "matchedScholar": { "scholarId": "uuid", "firstName": "string", "...": "same shape as ScholarDto" }
}
```
`matchedScholar` is `null` when `matched` is `false`.

**Note: the client doesn't currently use the `matchedScholar` field.** `ServerRepository.connect(...)` only reads `node.get("matched").asBoolean()`; the matched scholar's full `ScholarDto`, already included in the response, is discarded. If a "You matched with X" notification card is added later, this field already carries the data needed — no extra `GET /api/profile` call or wait for `GET /api/matches` to refresh is required.

---

### POST /api/dislike

The current user rejects a scholar, recording a `status=REJECTED` collaboration request so that scholar no longer appears in `GET /api/recommend`. One-directional; never triggers a match. This endpoint used to be `/api/pass` with a `passedScholarId` field, which didn't match the client's `DislikeDataAccessInterface.dislike(...)` naming — it has since been renamed to match the client.

**Request body:**
```json
{ "dislikedScholarId": "uuid" }
```

**Response 200:** empty body.

---

### GET /api/matches

Returns every scholar the current user has mutually matched with (`ACCEPTED` in both directions). This endpoint used to be `/api/collaborators`: the server originally called recommendation candidates "matches" and confirmed matches "collaborators," the reverse of the client's naming (the client's `RecommendDataAccessInterface` is for recommendation candidates, `LoadMatchesDataAccessInterface`/`getMatches()` is for confirmed matches). The server has since adopted the client's naming: recommendation candidates moved to `GET /api/recommend`, and this endpoint became `GET /api/matches`.

**Response 200:** `ScholarDto[]`, same shape as `GET /api/recommend`.

**Note: this endpoint returns duplicate entries — the client must deduplicate.** A mutual right-swipe leaves two rows in `collaboration_requests` (A→B and B→A); `acceptMatch(...)` marks both `ACCEPTED`. The server's `getMatches(...)` query condition is `senderId = :scholarId OR receiverId = :scholarId`, so the same counterpart matches both rows — the same matched user appears twice in the response array. The client's `ServerRepository.getMatches()` already deduplicates by `userId` using a `LinkedHashMap<userId, User>`; don't drop that step if this call is reimplemented.

---

## Messages (requires `Authorization: Bearer <token>`)

Chat is only open between two scholars who have mutually matched (an `ACCEPTED` row exists in `collaboration_requests`).

### POST /api/messages

**Request body:**
```json
{ "receiverId": "uuid", "content": "string" }
```

**Response 200** (server `MessageDto`):
```json
{
  "messageId": "uuid",
  "senderId": "uuid",
  "receiverId": "uuid",
  "content": "string",
  "sentAt": "2026-07-10T12:34:56"
}
```
`sentAt` is a timezone-free `LocalDateTime` format (`yyyy-MM-ddTHH:mm:ss`); the client parses it directly with `LocalDateTime.parse(...)` — don't treat it as a timezone-aware ISO format.

**If the two users have not matched, response 400:**
```json
{ "error": "You can only message users you have matched with" }
```

---

### GET /api/messages/{otherScholarId}

Returns the full conversation between the current user and `otherScholarId`, ordered chronologically. Not paginated — the entire history is returned in one call.

**Response 200:** `MessageDto[]`, same per-item shape as the `POST /api/messages` response.

---

## Health

### GET /api/health

No token required. The client probes this on startup to decide whether to fall back to `OFFLINE_MODE` (see the top of this document).
```json
{ "status": "ok" }
```

---

## Error Format

By default, errors are returned as:
```json
{ "error": "description" }
```
HTTP status codes: `400` invalid request, `401` unauthenticated, `404` not found, `500` server error.

**Exception:** the server's `GlobalExceptionHandler` only catches its own `InvalidRequestException`. Exceptions Spring throws itself — e.g. `MethodArgumentNotValidException` from `@NotBlank` validation failures on `/api/auth/register` — are not converted to the `{"error": "..."}` format and fall through to Spring Boot's default error response, which has no field-level detail. The client's `ServerRepository.parseResponse(...)` has a fallback for this case — when there's no `error` field, it translates the status code into a generic message via `describeStatusCode(...)` — but the resulting message will be generic. When one of these errors shows up, check the HTTP status code directly rather than expecting detail in the `error` field.

---

## Enum Value Tables

The server stores these fields as plain strings with no value validation beyond `@NotBlank`, but the client parses responses by strictly matching a Java enum's `name()` (`ServerRepository.safeParseEnum(...)`). Sending a string outside the list gets stored as-is server-side, but silently falls back to the default value listed below on the next read — no error, and easy to lose time debugging. Use the exact constant names below (uppercase with underscores) when writing request bodies; don't invent values.

### AcademicLevel (`academicLevel`; falls back to `GRADUATE_STUDENT` if unrecognized)
The server's own `API.md` lists example values `PHD_STUDENT | MASTER_STUDENT | PROFESSOR | RESEARCHER` — none of these exist in the client's enum, and using them will make the profile read back as `GRADUATE_STUDENT` for all of them. Use this table instead:

| Value | Meaning |
|---|---|
| `UNDERGRADUATE` | Undergraduate student |
| `GRADUATE_STUDENT` | Master's/PhD student |
| `POSTDOCTORAL_RESEARCHER` | Postdoc |
| `FACULTY` | Faculty at a university or research institution |
| `INDUSTRY_RESEARCHER` | Researcher in industry |

### CollaborationType (`lookingFor`; falls back to `INTEREST_SHARING` if unrecognized)

| Value | Meaning |
|---|---|
| `CO_AUTHOR` | Looking for co-authors |
| `RESEARCH_GROUP` | Wants to join or build a research group |
| `PEER_REVIEW` | Mutual review of manuscripts/proposals |
| `MENTORSHIP` | Looking for a mentor, or willing to mentor |
| `INTEREST_SHARING` | Browsing for people with similar research interests, no specific collaboration goal |

### FundingStatus (`fundingStatus`; falls back to `OTHER` if unrecognized)

| Value | Meaning |
|---|---|
| `SELF_FUNDED` | Self-funded |
| `INSTITUTIONAL_FUNDING` | Funded by their institution |
| `GOVERNMENT_GRANT` | Government grant |
| `INDUSTRY_SPONSORED` | Sponsored by industry |
| `SCHOLARSHIP_FELLOWSHIP` | Scholarship/fellowship |
| `UNFUNDED` | Unfunded |
| `OTHER` | Other |

### DegreeType (`educations[].degree`; falls back to `BACHELOR` if unrecognized)

| Value | Meaning |
|---|---|
| `HIGH_SCHOOL` | High school |
| `BACHELOR` | Bachelor's |
| `MASTER` | Master's |
| `PHD` | PhD |
| `POSTDOC` | Postdoc |

### ResearchField (`researchField`; falls back to `OTHER` if unrecognized)

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

### Institution (`institution`; falls back to `OTHER` if unrecognized)

A large closed enum (QS 2025 World University Rankings plus major research institutes, roughly 1140 values) — not listed in full here; the complete definition is in the client's `entity/Institution.java`. A few examples to get a sense of the naming convention (full name, uppercased, underscored): `MIT`, `STANFORD_UNIVERSITY`, `TSINGHUA_UNIVERSITY`, ..., fallback value `OTHER`. Pick any real constant name for Postman test data — don't invent a string (the server will store it, but the client will read it back as `OTHER`).

---

## Third-Party API: Semantic Scholar (author search autofill)

Used for author search and paper auto-import during registration/profile editing — unrelated to ScholarMatch's own server, so it's documented separately in [`SEMANTIC_SCHOLAR_API.md`](./SEMANTIC_SCHOLAR_API.md).

---

## Client Responsibilities (Clean Architecture layers)

The server is a plain REST API — caching, deduplication, and profile-completeness checks are all implemented client-side, with no dependency on any server-side cache.

### HTTP implementations already wired up

`frameworks/data_access_object/ServerRepository.java` is the single HTTP implementation for every server interface; `SemanticScholarGateway.java` is the single HTTP implementation for Semantic Scholar. Both are working code, not placeholders — the request/response formats in this document were checked against these two files. `Config.build()` uses them by default at startup; it only switches to `LocalServerRepository` / `LocalUserApiGateway` (in-memory fake data, for demos/offline development) when `OFFLINE_MODE=true` or the health check fails.

### DataAccessInterface per endpoint (`usecase/data_access_interface/`)

| Feature | DataAccessInterface | HTTP |
|---|---|---|
| Login | `LoginDataAccessInterface` | `POST /api/auth/login` |
| Register | `RegisterDataAccessInterface` | `POST /api/auth/register` |
| Get profile | `LoadProfileDataAccessInterface` | `GET /api/profile` |
| Update profile | `UpdateProfileDataAccessInterface` | `PUT /api/profile` |
| Delete account | `DeleteAccountDataAccessInterface` | `DELETE /api/profile` |
| Get recommendations | `RecommendDataAccessInterface` (also has `getProfile()`, used for the completeness check before recommending) | `GET /api/recommend` |
| Swipe right / connect | `ConnectDataAccessInterface` | `POST /api/connect` |
| Dislike | `DislikeDataAccessInterface` | `POST /api/dislike` |
| Matched list | `LoadMatchesDataAccessInterface` | `GET /api/matches` |
| Send message | `SendMessageDataAccessInterface` | `POST /api/messages` |
| Load conversation | `LoadMessageDataAccessInterface` | `GET /api/messages/{id}` |
| Author search / paper import | `UserAPIGatewayInterface` | Semantic Scholar `/author/search`, `/author/{id}/papers` |

These interfaces are split by consumer — `LoadProfileDataAccessInterface` and `UpdateProfileDataAccessInterface` are two separate interfaces rather than one combined `ProfileDataAccessInterface`, so each interface only declares the methods its use case actually needs, and unrelated use cases aren't forced to depend on methods they don't use. Check this directory for an existing interface before adding a new one for a new use case.

### Recommend cache (`RecommendInteractor`)
- `GET /api/recommend` returns up to 20 scholars per call and is not cached server-side; the client keeps the results in memory as a `List<User>`
- Each connect/dislike pops the next entry from the front of the cache
- A new request is made once the cache is empty, or a similarity threshold decides when to show "no more results"
- `profileDataAccessObject.getProfile()` is checked for `User.isProfileComplete()` before fetching recommendations

### Session management
- `CurrentUserProvider` (`frameworks/data_access_object/`) implements `CurrentUserProviderInterface`, `SessionWriterInterface`, and `SessionClearerInterface`; it stores the JWT token and user ID in memory after a successful login/register
- Every interactor reads/writes session state through these interfaces rather than handling the token string directly
- `ServerRepository` reads the token from `CurrentUserProviderInterface.getToken()` fresh on every request, rather than capturing it once at construction time

---

## Deployment & Secrets (server-side, not the client's concern)

| Variable | Value |
|---|---|
| PG connection | Railway's built-in Postgres (auto-injected) |
| JWT_SECRET | Set in Railway |
| JINA_API_KEY | Set in Railway (used to generate embeddings) |
| CLOUDINARY_URL | Set in Railway (used for avatar uploads) |

The client needs no database or API key configuration — only `SERVER_URL`, which has a default and usually doesn't need to be set.
