package com.scholarmatch.usecase.data_access_interface;

/**
 * Data access boundary for requesting an email-change verification code.
 */
public interface RequestEmailChangeVerificationDataAccessInterface {

    void requestEmailChangeVerification(String email);
}
