# Registration email verification

Registration requires a six-digit email verification code. The code expires after ten minutes,
and a challenge allows three incorrect submissions before registration is rejected.

The server owns the entire verification lifecycle — code generation, delivery, storage,
expiry, and comparison — plus the academic-domain classification that used to be a
client-computed, client-trusted flag. The desktop client only ever handles the email address
and whatever code the user types in:

- `RemoteVerificationEmailSender` posts `{"email"}` to
  `POST {SERVER_URL}/api/auth/request-verification-code`. The server generates the code,
  stores the challenge (10 min expiry, 3 attempts, in-memory), and sends it via Resend. The
  client never sees the code or the `RESEND_API_KEY` credential.
- Registration (`POST {SERVER_URL}/api/auth/register`) now includes the `code` field the user
  typed in. The server checks it against the stored challenge — `RegisterInteractor` no longer
  does this locally, it just forwards whatever the user typed and surfaces the server's
  rejection message (e.g. "Verification code is incorrect. 2 attempts remaining.") like any
  other server-rejected request.
- The server also decides `academicEmailVerified` itself, from its own copy of the university
  domain catalog (`AcademicEmailDomainService`, `academic-email-domains.txt` in the server
  repo) — it no longer trusts a client-supplied classification. `GET /api/profile` returns the
  authoritative value; the client's `EmailAccountType` enum only exists to render that value in
  the UI (e.g. an "academic" badge), it carries no authority of its own.

This closes a real gap: previously the client decided both "was this code correct" and "is
this an academic email," and could reach the register endpoint with any answer it liked — a
raw HTTP caller didn't need to go through the code-verification UI at all. Now every check that
matters happens server-side, and the client is a thin, unauthoritative front end for it.

Server-side configuration (in the `scholarmatch-server` repo, not this one):

```
RESEND_API_KEY=re_...
RESEND_FROM_EMAIL=ScholarMatch <verify@your-verified-domain.example>
```

`RESEND_FROM_EMAIL` must use a sender accepted by the configured Resend account.
