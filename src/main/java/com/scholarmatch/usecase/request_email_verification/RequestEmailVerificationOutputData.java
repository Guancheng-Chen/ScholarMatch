package com.scholarmatch.usecase.request_email_verification;

/**
 * Successful verification-code request result.
 *
 * @param email the normalized destination address
 */
public record RequestEmailVerificationOutputData(String email) {
}
