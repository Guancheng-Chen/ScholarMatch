package com.scholarmatch.usecase.request_email_verification;

import com.scholarmatch.entity.EmailVerificationChallenge;
import com.scholarmatch.usecase.data_access_interface.EmailVerificationChallengeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationCodeGeneratorInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.time.Clock;
import java.time.Duration;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Creates and delivers short-lived registration verification challenges.
 */
public final class RequestEmailVerificationInteractor
        implements RequestEmailVerificationInputBoundary {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Duration CHALLENGE_LIFETIME = Duration.ofMinutes(10);

    private final VerificationEmailSenderDataAccessInterface emailSender;
    private final EmailVerificationChallengeDataAccessInterface challengeRepository;
    private final VerificationCodeGeneratorInterface codeGenerator;
    private final RequestEmailVerificationOutputBoundary outputBoundary;
    private final Clock clock;

    /**
     * Creates the verification request interactor.
     *
     * @param emailSender the delivery gateway
     * @param challengeRepository pending challenge storage
     * @param codeGenerator verification code generator
     * @param outputBoundary presenter boundary
     * @param clock source of the current instant
     */
    public RequestEmailVerificationInteractor(
            final VerificationEmailSenderDataAccessInterface emailSender,
            final EmailVerificationChallengeDataAccessInterface challengeRepository,
            final VerificationCodeGeneratorInterface codeGenerator,
            final RequestEmailVerificationOutputBoundary outputBoundary,
            final Clock clock) {
        this.emailSender = emailSender;
        this.challengeRepository = challengeRepository;
        this.codeGenerator = codeGenerator;
        this.outputBoundary = outputBoundary;
        this.clock = clock;
    }

    @Override
    public void execute(final RequestEmailVerificationInputData inputData) {
        final String email = normalize(inputData.email());
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            this.outputBoundary.prepareFailView("Enter a valid email address before requesting a code.");
            return;
        }
        final String code = this.codeGenerator.generateCode();
        final EmailVerificationChallenge challenge = new EmailVerificationChallenge(
                email, code, this.clock.instant().plus(CHALLENGE_LIFETIME));
        try {
            this.emailSender.sendVerificationCode(email, code);
            this.challengeRepository.save(challenge);
            this.outputBoundary.prepareSuccessView(new RequestEmailVerificationOutputData(email));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }

    private String normalize(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
