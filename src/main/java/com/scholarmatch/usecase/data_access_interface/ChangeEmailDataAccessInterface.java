package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.User;

/**
 * Data access boundary for changing the authenticated account email.
 */
public interface ChangeEmailDataAccessInterface {

    User changeEmail(String email, String currentPassword, String verificationCode);
}
