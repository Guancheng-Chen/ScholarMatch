package com.scholarmatch.frameworks.data_access_object.localMockServer;

/**
 * Possible outcomes from checking an email verification code.
 */
public enum EmailVerificationOutcome {
    VERIFIED,
    INVALID_CODE,
    EXPIRED,
    ATTEMPTS_EXHAUSTED
}
