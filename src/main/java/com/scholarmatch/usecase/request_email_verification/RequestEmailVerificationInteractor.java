package com.scholarmatch.usecase.request_email_verification;

import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Requests a registration verification code. The code itself is generated, stored, and later
 * checked by the server — this interactor only validates the email format locally and asks
 * the server to send the code.
 */
public final class RequestEmailVerificationInteractor
        implements RequestEmailVerificationInputBoundary {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final VerificationEmailSenderDataAccessInterface emailSender;
    private final RequestEmailVerificationOutputBoundary outputBoundary;

    /**
     * Creates the verification request interactor.
     *
     * @param emailSender the delivery gateway
     * @param outputBoundary presenter boundary
     */
    public RequestEmailVerificationInteractor(
            final VerificationEmailSenderDataAccessInterface emailSender,
            final RequestEmailVerificationOutputBoundary outputBoundary) {
        this.emailSender = emailSender;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final RequestEmailVerificationInputData inputData) {
        final String email = normalize(inputData.email());
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            this.outputBoundary.prepareFailView("Enter a valid email address before requesting a code.");
            return;
        }
        try {
            this.emailSender.requestVerificationCode(email);
            this.outputBoundary.prepareSuccessView(new RequestEmailVerificationOutputData(email));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }

    private String normalize(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
