package com.scholarmatch.usecase.change_password;

import com.scholarmatch.usecase.data_access_interface.ChangePasswordDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

/**
 * Changes the authenticated account password.
 */
public final class ChangePasswordInteractor implements ChangePasswordInputBoundary {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 64;

    private final ChangePasswordDataAccessInterface dataAccessObject;
    private final ChangePasswordOutputBoundary outputBoundary;

    public ChangePasswordInteractor(
            final ChangePasswordDataAccessInterface dataAccessObject,
            final ChangePasswordOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final ChangePasswordInputData inputData) {
        if (inputData.currentPassword() == null || inputData.currentPassword().isEmpty()) {
            this.outputBoundary.prepareFailView("Current password is required.");
            return;
        }
        if (inputData.newPassword() == null
                || inputData.newPassword().length() < MIN_PASSWORD_LENGTH
                || inputData.newPassword().length() > MAX_PASSWORD_LENGTH) {
            this.outputBoundary.prepareFailView(
                    "New password must be between 8 and 64 characters.");
            return;
        }
        if (!inputData.newPassword().equals(inputData.confirmPassword())) {
            this.outputBoundary.prepareFailView("New passwords do not match.");
            return;
        }
        try {
            this.dataAccessObject.changePassword(
                    inputData.currentPassword(), inputData.newPassword());
            this.outputBoundary.prepareSuccessView(new ChangePasswordOutputData());
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
