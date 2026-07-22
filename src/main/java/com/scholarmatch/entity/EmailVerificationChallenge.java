package com.scholarmatch.entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Locale;

/**
 * Registration verification state for one normalized email address.
 */
public final class EmailVerificationChallenge {

    public static final int MAX_ATTEMPTS = 3;

    private final String email;
    private final String codeDigest;
    private final Instant expiresAt;
    private int attemptsRemaining;
    private boolean verified;

    /**
     * Creates a new unverified challenge.
     *
     * @param email the destination email
     * @param code the generated plain-text code
     * @param expiresAt the expiration instant
     */
    public EmailVerificationChallenge(
            final String email,
            final String code,
            final Instant expiresAt) {
        this.email = normalize(email);
        this.codeDigest = digest(code);
        this.expiresAt = expiresAt;
        this.attemptsRemaining = MAX_ATTEMPTS;
    }

    /**
     * Checks one submitted code and updates the remaining-attempt count.
     *
     * @param submittedCode the user-entered code
     * @param now the current instant
     * @return the verification result
     */
    public synchronized EmailVerificationResult verify(
            final String submittedCode,
            final Instant now) {
        if (this.verified) {
            return result(EmailVerificationOutcome.VERIFIED);
        }
        if (!now.isBefore(this.expiresAt)) {
            return result(EmailVerificationOutcome.EXPIRED);
        }
        if (this.attemptsRemaining == 0) {
            return result(EmailVerificationOutcome.ATTEMPTS_EXHAUSTED);
        }
        final String submittedDigest = digest(submittedCode == null ? "" : submittedCode.trim());
        if (MessageDigest.isEqual(
                this.codeDigest.getBytes(StandardCharsets.US_ASCII),
                submittedDigest.getBytes(StandardCharsets.US_ASCII))) {
            this.verified = true;
            return result(EmailVerificationOutcome.VERIFIED);
        }
        this.attemptsRemaining--;
        if (this.attemptsRemaining == 0) {
            return result(EmailVerificationOutcome.ATTEMPTS_EXHAUSTED);
        }
        return result(EmailVerificationOutcome.INVALID_CODE);
    }

    /**
     * @return the normalized challenge email
     */
    public String getEmail() {
        return this.email;
    }

    private EmailVerificationResult result(final EmailVerificationOutcome outcome) {
        return new EmailVerificationResult(outcome, this.attemptsRemaining);
    }

    private static String normalize(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private static String digest(final String value) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(
                    messageDigest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (final NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable.", exception);
        }
    }
}
