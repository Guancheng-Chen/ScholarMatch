# Resend registration email verification

Registration requires a six-digit email verification code. The code expires after ten minutes,
and a challenge allows three incorrect submissions before registration is rejected.

Set both environment variables before launching the desktop client:

```powershell
$env:RESEND_API_KEY = "your-resend-api-key"
$env:RESEND_FROM_EMAIL = "ScholarMatch <verify@your-verified-domain.example>"
mvn exec:java

RESEND_FROM_EMAIL must use a sender accepted by the configured Resend account. The API key is
read only from the process environment and must not be committed to the repository.

The current repository is a desktop client, so this implementation calls Resend directly. For a
public production distribution, move the Resend credential and send-code operation to the backend
and expose a ScholarMatch verification endpoint to the client. This prevents end users from
extracting a mail-service credential from a distributed application process.

The same backend endpoint should own the challenge and set academicEmailVerified itself. The
desktop flow blocks unverified registration through this GUI, but client-side verification alone
cannot stop a custom HTTP caller from bypassing the GUI or submitting a forged verification flag.
