package com.scholarmatch.usecase.register;

import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.EmailVerificationChallenge;
import com.scholarmatch.entity.EmailVerificationOutcome;
import com.scholarmatch.entity.EmailVerificationResult;
import com.scholarmatch.usecase.data_access_interface.AcademicEmailDomainDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.EmailVerificationChallengeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SessionWriterInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.time.Clock;
import java.util.regex.Pattern;

/**
 * Interactor implementing the register use case.
 *
 * <p>Runs a local validation pass over the submitted fields (names, email format, password
 * length) before ever contacting the server, collecting every violation found so the caller
 * can present the complete list in one shot. On success, delegates account creation (hashing,
 * embedding, persistence) to the server via RegisterDataAccessInterface and stores the
 * returned JWT and user ID in the session.
 */
public final class RegisterInteractor implements RegisterInputBoundary {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 64;

    private final RegisterDataAccessInterface authDataAccessObject;
    private final SessionWriterInterface sessionManager;
    private final RegisterOutputBoundary outputBoundary;
    private final AcademicEmailDomainDataAccessInterface academicEmailDomains;
    private final EmailVerificationChallengeDataAccessInterface verificationChallenges;
    private final Clock clock;

    /**
     * Constructs a RegisterInteractor.
     *
     * @param authDataAccessObject server-side auth gateway
     * @param sessionManager       records the newly created user and token
     * @param outputBoundary       the presenter that will receive the result
     */
    public RegisterInteractor(
            final RegisterDataAccessInterface authDataAccessObject,
            final SessionWriterInterface sessionManager,
            final RegisterOutputBoundary outputBoundary,
            final AcademicEmailDomainDataAccessInterface academicEmailDomains,
            final EmailVerificationChallengeDataAccessInterface verificationChallenges,
            final Clock clock) {
        this.authDataAccessObject = authDataAccessObject;
        this.sessionManager = sessionManager;
        this.outputBoundary = outputBoundary;
        this.academicEmailDomains = academicEmailDomains;
        this.verificationChallenges = verificationChallenges;
        this.clock = clock;
    }

    @Override
    public void execute(final RegisterInputData inputData) {
        final List<String> errors = validate(inputData);
        if (!errors.isEmpty()) {
            outputBoundary.prepareFailView(String.join("\n", errors));
            return;
        }
        if (!verifyEmail(inputData)) {
            return;
        }
        try {
            final EmailAccountType emailAccountType = academicEmailDomains.isAcademicEmail(inputData.getEmail())
                    ? EmailAccountType.ACADEMIC : EmailAccountType.REGULAR;
            final RegisterAccountData accountData = new RegisterAccountData(
                    inputData.getFirstName(), inputData.getLastName(), inputData.getEmail(),
                    inputData.getPassword(), emailAccountType);
            final AuthResult result = authDataAccessObject.register(accountData);
            sessionManager.setCurrentUserId(result.userId());
            sessionManager.setToken(result.token());
            outputBoundary.prepareSuccessView(
                    new RegisterOutputData(result.userId(), result.displayName()));
            this.verificationChallenges.deleteByEmail(inputData.getEmail());
        } catch (final DataAccessException e) {
            outputBoundary.prepareFailView(e.getMessage());
        }
    }

    private boolean verifyEmail(final RegisterInputData inputData) {
        final EmailVerificationChallenge challenge = this.verificationChallenges
                .findByEmail(inputData.getEmail())
                .orElse(null);
        if (challenge == null) {
            this.outputBoundary.prepareFailView(
                    "Request a verification code for this email before registering.");
            return false;
        }
        final EmailVerificationResult result = challenge.verify(
                inputData.getVerificationCode(), this.clock.instant());
        if (result.outcome() == EmailVerificationOutcome.VERIFIED) {
            return true;
        }
        if (result.outcome() == EmailVerificationOutcome.INVALID_CODE) {
            this.outputBoundary.prepareFailView(
                    "Verification code is incorrect. " + result.attemptsRemaining()
                            + " attempts remaining.");
        } else if (result.outcome() == EmailVerificationOutcome.ATTEMPTS_EXHAUSTED) {
            this.outputBoundary.prepareFailView(
                    "Registration failed: verification code is incorrect. "
                            + "No attempts remaining. Request a new code.");
        } else {
            this.outputBoundary.prepareFailView(
                    "Verification code has expired. Request a new code.");
        }
        return false;
    }

    private List<String> validate(final RegisterInputData inputData) {
        final List<String> errors = new ArrayList<>();

        if (isBlank(inputData.getFirstName())) {
            errors.add("First name is required.");
        }
        if (isBlank(inputData.getLastName())) {
            errors.add("Last name is required.");
        }

        final String email = inputData.getEmail();
        if (isBlank(email)) {
            errors.add("Email is required.");
        } else if (!isValidEmail(email)) {
            errors.add("Email format is invalid, e.g. name@example.com.");
        }

        final String password = inputData.getPassword();
        if (password == null || password.isEmpty()) {
            errors.add("Password is required.");
        } else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            errors.add("Password must be between " + MIN_PASSWORD_LENGTH + " and "
                    + MAX_PASSWORD_LENGTH + " characters (currently " + password.length() + ").");
        }

        return errors;
    }

    private boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }

    private boolean isValidEmail(final String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
