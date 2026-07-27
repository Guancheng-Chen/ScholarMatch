package com.scholarmatch.usecase.request_email_change_verification;

/**
 * Output data for a successful email-change code request.
 *
 * @param email the normalized destination email
 */
public record RequestEmailChangeVerificationOutputData(String email) {
}
