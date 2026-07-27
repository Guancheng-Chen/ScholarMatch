package com.scholarmatch.usecase.change_email;

/**
 * Input data for changing an account email.
 *
 * @param email the verified replacement email
 * @param currentPassword the current account password
 * @param verificationCode the code delivered to the replacement email
 */
public record ChangeEmailInputData(
        String email,
        String currentPassword,
        String verificationCode) {
}
