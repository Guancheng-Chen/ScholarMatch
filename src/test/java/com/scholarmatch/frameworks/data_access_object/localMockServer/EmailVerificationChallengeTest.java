package com.scholarmatch.frameworks.data_access_object.localMockServer;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

class EmailVerificationChallengeTest {

    private static final Instant CREATED_AT = Instant.parse("2026-07-26T12:00:00Z");

    @Test
    void testCorrectCodeVerifiesAndStaysVerifiedOnRecheck() {
        final EmailVerificationChallenge challenge = new EmailVerificationChallenge(
                " Ada@Example.com ", "123456", CREATED_AT.plus(Duration.ofMinutes(10)));

        final EmailVerificationResult first = challenge.verify("123456", CREATED_AT);
        assertEquals(EmailVerificationOutcome.VERIFIED, first.outcome());
        assertEquals("ada@example.com", challenge.getEmail());

        final EmailVerificationResult again = challenge.verify("wrong-code-now", CREATED_AT);
        assertEquals(EmailVerificationOutcome.VERIFIED, again.outcome());
    }

    @Test
    void testExpiredChallengeReportsExpired() {
        final EmailVerificationChallenge challenge = new EmailVerificationChallenge(
                "ada@example.com", "123456", CREATED_AT.plus(Duration.ofMinutes(10)));

        final EmailVerificationResult result = challenge.verify(
                "123456", CREATED_AT.plus(Duration.ofMinutes(10)));

        assertEquals(EmailVerificationOutcome.EXPIRED, result.outcome());
    }

    @Test
    void testWrongCodeDecrementsAttemptsThenExhausts() {
        final EmailVerificationChallenge challenge = new EmailVerificationChallenge(
                "ada@example.com", "123456", CREATED_AT.plus(Duration.ofMinutes(10)));

        final EmailVerificationResult firstMiss = challenge.verify(null, CREATED_AT);
        assertEquals(EmailVerificationOutcome.INVALID_CODE, firstMiss.outcome());
        assertEquals(2, firstMiss.attemptsRemaining());

        final EmailVerificationResult secondMiss = challenge.verify("000000", CREATED_AT);
        assertEquals(EmailVerificationOutcome.INVALID_CODE, secondMiss.outcome());
        assertEquals(1, secondMiss.attemptsRemaining());

        final EmailVerificationResult thirdMiss = challenge.verify("000000", CREATED_AT);
        assertEquals(EmailVerificationOutcome.ATTEMPTS_EXHAUSTED, thirdMiss.outcome());
        assertEquals(0, thirdMiss.attemptsRemaining());

        final EmailVerificationResult afterExhausted = challenge.verify("123456", CREATED_AT);
        assertEquals(EmailVerificationOutcome.ATTEMPTS_EXHAUSTED, afterExhausted.outcome());
    }

    @Test
    void testMissingShaAlgorithmIsTranslatedToIllegalStateException() {
        try (MockedStatic<MessageDigest> digest = mockStatic(MessageDigest.class)) {
            digest.when(() -> MessageDigest.getInstance("SHA-256"))
                    .thenThrow(new NoSuchAlgorithmException("no SHA-256"));

            final IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> new EmailVerificationChallenge(
                            "ada@example.com", "123456", CREATED_AT.plus(Duration.ofMinutes(10))));

            assertEquals("SHA-256 is unavailable.", exception.getMessage());
        }
    }
}
