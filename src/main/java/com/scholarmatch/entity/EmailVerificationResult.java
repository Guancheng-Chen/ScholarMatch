package com.scholarmatch.entity;

/**
 * Immutable result of a verification attempt.
 *
 * @param outcome the verification outcome
 * @param attemptsRemaining the number of incorrect attempts still available
 */
public record EmailVerificationResult(
        EmailVerificationOutcome outcome,
        int attemptsRemaining) {
}
