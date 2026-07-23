package com.scholarmatch.usecase.data_access_interface;

/**
 * Boundary for asking the server to generate and deliver a registration verification code.
 * The code itself is generated, stored, and later checked server-side — the client never
 * handles anything but the destination email.
 */
public interface VerificationEmailSenderDataAccessInterface {

    void requestVerificationCode(String email);
}
