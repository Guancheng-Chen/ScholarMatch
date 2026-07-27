package com.scholarmatch.usecase.data_access_interface;

/**
 * Data access boundary for changing the authenticated account password.
 */
public interface ChangePasswordDataAccessInterface {

    void changePassword(String currentPassword, String newPassword);
}
