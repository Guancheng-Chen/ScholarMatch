package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.change_email.ChangeEmailInputBoundary;
import com.scholarmatch.usecase.change_email.ChangeEmailInputData;

/**
 * Controller for changing an account email.
 */
public final class ChangeEmailController {

    private final ChangeEmailInputBoundary interactor;

    public ChangeEmailController(final ChangeEmailInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(
            final String email,
            final String currentPassword,
            final String verificationCode) {
        this.interactor.execute(
                new ChangeEmailInputData(
                        email, currentPassword, verificationCode));
    }
}
