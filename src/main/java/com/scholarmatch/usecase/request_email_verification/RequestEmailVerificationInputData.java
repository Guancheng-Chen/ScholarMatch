package com.scholarmatch.usecase.request_email_verification;

/**
 * Input data for requesting a registration verification code.
 *
 * @param email the destination email
 */
public record RequestEmailVerificationInputData(String email) {
}
