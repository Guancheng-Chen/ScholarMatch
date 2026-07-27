package com.scholarmatch.usecase.change_password;

/**
 * Input data for changing an account password.
 *
 * @param currentPassword the current password
 * @param newPassword the replacement password
 * @param confirmPassword confirmation of the replacement password
 */
public record ChangePasswordInputData(
        String currentPassword,
        String newPassword,
        String confirmPassword) {
}
