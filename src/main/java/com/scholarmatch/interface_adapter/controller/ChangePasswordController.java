package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.change_password.ChangePasswordInputBoundary;
import com.scholarmatch.usecase.change_password.ChangePasswordInputData;

/**
 * Controller for changing an account password.
 */
public final class ChangePasswordController {

    private final ChangePasswordInputBoundary interactor;

    public ChangePasswordController(
            final ChangePasswordInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(
            final String currentPassword,
            final String newPassword,
            final String confirmPassword) {
        this.interactor.execute(
                new ChangePasswordInputData(
                        currentPassword, newPassword, confirmPassword));
    }
}
