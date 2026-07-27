package com.scholarmatch.usecase.change_email;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.ChangeEmailDataAccessInterface;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Changes the authenticated account email after code and password verification.
 */
public final class ChangeEmailInteractor implements ChangeEmailInputBoundary {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final ChangeEmailDataAccessInterface dataAccessObject;
    private final ChangeEmailOutputBoundary outputBoundary;

    public ChangeEmailInteractor(
            final ChangeEmailDataAccessInterface dataAccessObject,
            final ChangeEmailOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final ChangeEmailInputData inputData) {
        final String email = normalize(inputData.email());
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            this.outputBoundary.prepareFailView("Enter a valid new email address.");
            return;
        }
        if (isBlank(inputData.currentPassword())) {
            this.outputBoundary.prepareFailView("Current password is required.");
            return;
        }
        if (isBlank(inputData.verificationCode())) {
            this.outputBoundary.prepareFailView("Verification code is required.");
            return;
        }
        try {
            final User user = this.dataAccessObject.changeEmail(
                    email, inputData.currentPassword(), inputData.verificationCode().trim());
            this.outputBoundary.prepareSuccessView(
                    new ChangeEmailOutputData(UserData.from(user)));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }

    private String normalize(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }
}
