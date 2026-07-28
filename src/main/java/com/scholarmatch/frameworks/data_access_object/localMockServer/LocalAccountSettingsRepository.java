package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.ChangeEmailDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ChangePasswordDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

/**
 * In-memory offline implementation of requesting a verification code and using it to change
 * the current user's email or password.
 */
public final class LocalAccountSettingsRepository implements
        VerificationEmailSenderDataAccessInterface,
        ChangeEmailDataAccessInterface,
        ChangePasswordDataAccessInterface {

    private final LocalServerState state;
    private final CurrentUserProviderInterface session;
    private final InMemoryEmailVerificationChallengeRepository emailChallenges;
    private final VerificationCodeGeneratorInterface codeGenerator;
    private final EmailChangeCodeDeliveryDataAccessInterface codeDelivery;
    private final AcademicEmailDomainDataAccessInterface academicEmailDomains;
    private final Clock clock;

    public LocalAccountSettingsRepository(
            final LocalServerState state,
            final CurrentUserProviderInterface session,
            final InMemoryEmailVerificationChallengeRepository emailChallenges,
            final VerificationCodeGeneratorInterface codeGenerator,
            final EmailChangeCodeDeliveryDataAccessInterface codeDelivery,
            final AcademicEmailDomainDataAccessInterface academicEmailDomains,
            final Clock clock) {
        this.state = state;
        this.session = session;
        this.emailChallenges = emailChallenges;
        this.codeGenerator = codeGenerator;
        this.codeDelivery = codeDelivery;
        this.academicEmailDomains = academicEmailDomains;
        this.clock = clock;
    }

    @Override
    public void requestVerificationCode(final String email) {
        final String normalizedEmail = normalizeEmail(email);
        final User existing = this.state.findByEmail(normalizedEmail);
        if (existing != null
                && !existing.getUserId().equals(this.session.getCurrentUserId())) {
            throw new InvalidRequestException("Email is already registered");
        }
        final String code = this.codeGenerator.generateCode();
        final EmailVerificationChallenge challenge =
                new EmailVerificationChallenge(
                        normalizedEmail,
                        code,
                        Instant.now(this.clock).plus(Duration.ofMinutes(10)));
        this.emailChallenges.save(challenge);
        try {
            this.codeDelivery.sendCode(normalizedEmail, code);
        } catch (final RuntimeException exception) {
            this.emailChallenges.deleteByEmail(normalizedEmail);
            throw exception;
        }
    }

    @Override
    public User changeEmail(
            final String email,
            final String currentPassword,
            final String verificationCode) {
        final User user = currentUser();
        if (!user.getPasswordHash().equals(currentPassword)) {
            throw new InvalidRequestException("Current password is incorrect");
        }
        final String normalizedEmail = normalizeEmail(email);
        final User existing = this.state.findByEmail(normalizedEmail);
        if (existing != null && !existing.getUserId().equals(user.getUserId())) {
            throw new InvalidRequestException("Email is already registered");
        }
        final EmailVerificationChallenge challenge = this.emailChallenges
                .findByEmail(normalizedEmail)
                .orElseThrow(() -> new InvalidRequestException(
                        "Request a verification code for this email first"));
        final EmailVerificationResult result =
                challenge.verify(verificationCode, Instant.now(this.clock));
        // Every outcome other than VERIFIED rejects the request — checked as a chain of
        // reachable conditions (rather than an enum switch) so there's no dead default/VERIFIED
        // branch to satisfy exhaustiveness with: falling through past all three is what
        // "verified" means here.
        if (result.outcome() == EmailVerificationOutcome.EXPIRED) {
            throw new InvalidRequestException("Verification code has expired");
        } else if (result.outcome() == EmailVerificationOutcome.ATTEMPTS_EXHAUSTED) {
            throw new InvalidRequestException("Verification failed after three incorrect attempts");
        } else if (result.outcome() == EmailVerificationOutcome.INVALID_CODE) {
            throw new InvalidRequestException("Verification code is incorrect. "
                    + result.attemptsRemaining() + " attempts remaining");
        }
        user.setEmail(normalizedEmail);
        user.setEmailAccountType(
                this.academicEmailDomains.isAcademicEmail(normalizedEmail)
                        ? EmailAccountType.ACADEMIC : EmailAccountType.REGULAR);
        this.emailChallenges.deleteByEmail(normalizedEmail);
        return user;
    }

    @Override
    public void changePassword(
            final String currentPassword,
            final String newPassword) {
        final User user = currentUser();
        if (!user.getPasswordHash().equals(currentPassword)) {
            throw new InvalidRequestException("Current password is incorrect");
        }
        user.setPasswordHash(newPassword);
    }

    private User currentUser() {
        final User user = this.state.usersById().get(this.session.getCurrentUserId());
        if (user == null) {
            throw new ResourceNotFoundException("No profile found for the current user");
        }
        return user;
    }

    private String normalizeEmail(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
