package com.scholarmatch.entity;

/**
 * Result of checking a registration email verification code.
 */
public enum EmailVerificationOutcome {
    VERIFIED,
    INVALID_CODE,
    ATTEMPTS_EXHAUSTED,
    EXPIRED
}
