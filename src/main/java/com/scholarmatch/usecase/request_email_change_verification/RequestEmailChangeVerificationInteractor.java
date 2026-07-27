package com.scholarmatch.usecase.request_email_change_verification;

import com.scholarmatch.usecase.data_access_interface.RequestEmailChangeVerificationDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Requests a verification code for a proposed account email.
 */
public final class RequestEmailChangeVerificationInteractor
        implements RequestEmailChangeVerificationInputBoundary {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final RequestEmailChangeVerificationDataAccessInterface dataAccessObject;
    private final RequestEmailChangeVerificationOutputBoundary outputBoundary;

    public RequestEmailChangeVerificationInteractor(
            final RequestEmailChangeVerificationDataAccessInterface dataAccessObject,
            final RequestEmailChangeVerificationOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final RequestEmailChangeVerificationInputData inputData) {
        final String email = normalize(inputData.email());
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            this.outputBoundary.prepareFailView("Enter a valid new email address.");
            return;
        }
        try {
            this.dataAccessObject.requestEmailChangeVerification(email);
            this.outputBoundary.prepareSuccessView(
                    new RequestEmailChangeVerificationOutputData(email));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }

    private String normalize(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
