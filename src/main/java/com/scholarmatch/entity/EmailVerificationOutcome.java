package com.scholarmatch.entity;

/**
 * Possible outcomes from checking an email verification code.
 */
public enum EmailVerificationOutcome {
    VERIFIED,
    INVALID_CODE,
    EXPIRED,
    ATTEMPTS_EXHAUSTED
}
