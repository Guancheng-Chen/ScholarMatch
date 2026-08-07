package com.scholarmatch.frameworks.data_access_object.local_mock_server;

/**
 * Result from one verification-code attempt.
 *
 * @param outcome the verification outcome
 * @param attemptsRemaining the remaining invalid attempts
 */
public record EmailVerificationResult(
        EmailVerificationOutcome outcome,
        int attemptsRemaining) {
}
