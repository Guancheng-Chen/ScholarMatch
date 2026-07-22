package com.scholarmatch.usecase.data_access_interface;

/**
 * Boundary for delivering a registration verification code.
 */
public interface VerificationEmailSenderDataAccessInterface {

    void sendVerificationCode(String email, String code);
}
