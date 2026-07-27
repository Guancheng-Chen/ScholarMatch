package com.scholarmatch.usecase.request_email_change_verification;

/**
 * Input data for requesting an email-change code.
 *
 * @param email the proposed account email
 */
public record RequestEmailChangeVerificationInputData(String email) {
}
