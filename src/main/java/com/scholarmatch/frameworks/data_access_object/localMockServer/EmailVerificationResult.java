package com.scholarmatch.frameworks.data_access_object.localMockServer;

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
