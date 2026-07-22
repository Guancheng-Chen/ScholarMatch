package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailVerificationChallengeTest {

    private static final Instant NOW = Instant.parse("2026-07-21T12:00:00Z");

    @Test
    void testCorrectCodeVerifiesChallenge() {
        final EmailVerificationChallenge challenge = challenge();

        final EmailVerificationResult result = challenge.verify("123456", NOW);

        assertEquals(EmailVerificationOutcome.VERIFIED, result.outcome());
        assertEquals(3, result.attemptsRemaining());
    }

    @Test
    void testThirdIncorrectCodeExhaustsAttempts() {
        final EmailVerificationChallenge challenge = challenge();

        assertEquals(EmailVerificationOutcome.INVALID_CODE, challenge.verify("000000", NOW).outcome());
        assertEquals(EmailVerificationOutcome.INVALID_CODE, challenge.verify("000001", NOW).outcome());
        final EmailVerificationResult result = challenge.verify("000002", NOW);

        assertEquals(EmailVerificationOutcome.ATTEMPTS_EXHAUSTED, result.outcome());
        assertEquals(0, result.attemptsRemaining());
    }

    @Test
    void testExpiredCodeCannotVerify() {
        final EmailVerificationChallenge challenge = challenge();

        final EmailVerificationResult result = challenge.verify("123456", NOW.plusSeconds(601));

        assertEquals(EmailVerificationOutcome.EXPIRED, result.outcome());
    }

    private EmailVerificationChallenge challenge() {
        return new EmailVerificationChallenge("Ada@Example.com", "123456", NOW.plusSeconds(600));
    }
}
