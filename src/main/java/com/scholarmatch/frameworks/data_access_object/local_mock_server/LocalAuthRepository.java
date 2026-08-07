package com.scholarmatch.frameworks.data_access_object.local_mock_server;

import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.register.RegisterAccountData;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

/**
 * In-memory offline implementation of login and registration.
 */
public final class LocalAuthRepository implements LoginDataAccessInterface, RegisterDataAccessInterface {

    private final LocalServerState state;
    private final InMemoryEmailVerificationChallengeRepository emailChallenges;
    private final Clock clock;

    public LocalAuthRepository(
            final LocalServerState state,
            final InMemoryEmailVerificationChallengeRepository emailChallenges,
            final Clock clock) {
        this.state = state;
        this.emailChallenges = emailChallenges;
        this.clock = clock;
    }

    @Override
    public AuthResult login(final String email, final String password) {
        final User user = this.state.findByEmail(email);
        if (user == null || !user.getPasswordHash().equals(password)) {
            throw new InvalidRequestException("Invalid email or password");
        }
        return toAuthResult(user);
    }

    @Override
    public AuthResult register(final RegisterAccountData data) {
        if (this.state.findByEmail(data.getEmail()) != null) {
            throw new InvalidRequestException("Email is already registered");
        }
        final EmailVerificationChallenge challenge = this.emailChallenges
                .findByEmail(data.getEmail())
                .orElseThrow(() -> new InvalidRequestException(
                        "Request a verification code for this email first"));
        final EmailVerificationResult result =
                challenge.verify(data.getVerificationCode(), Instant.now(this.clock));
        // Every outcome other than VERIFIED rejects the request — see
        // LocalAccountSettingsRepository#changeEmail for the same pattern.
        if (result.outcome() == EmailVerificationOutcome.EXPIRED) {
            throw new InvalidRequestException("Verification code has expired");
        } else if (result.outcome() == EmailVerificationOutcome.ATTEMPTS_EXHAUSTED) {
            throw new InvalidRequestException("Verification failed after three incorrect attempts");
        } else if (result.outcome() == EmailVerificationOutcome.INVALID_CODE) {
            throw new InvalidRequestException("Verification code is incorrect. "
                    + result.attemptsRemaining() + " attempts remaining");
        }
        // Registration only collects the account-creation essentials; every other profile
        // field starts blank/null and is filled in later from the Edit Profile screen —
        // see User#isProfileComplete(), which the recommend use case gates on.
        final User user = new User(
                UUID.randomUUID().toString(),
                data.getFirstName(),
                data.getLastName(),
                data.getEmail(),
                "",
                null,
                null,
                null,
                null,
                "",
                "",
                null,
                null,
                data.getPassword(),
                EmailAccountType.REGULAR);
        this.state.usersById().put(user.getUserId(), user);
        this.emailChallenges.deleteByEmail(data.getEmail());
        return toAuthResult(user);
    }

    private AuthResult toAuthResult(final User user) {
        return new AuthResult(
                "local-token-" + user.getUserId(),
                user.getUserId(),
                user.getFullName());
    }
}
