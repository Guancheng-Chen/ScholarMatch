package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.register.RegisterAccountData;

import java.util.UUID;

/**
 * In-memory offline implementation of login and registration.
 */
public final class LocalAuthRepository implements LoginDataAccessInterface, RegisterDataAccessInterface {

    private final LocalServerState state;

    public LocalAuthRepository(final LocalServerState state) {
        this.state = state;
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
                // Offline mode has no server to check the verification code against, so it can't
                // legitimately claim ACADEMIC status either — that's the server's call to make.
                EmailAccountType.REGULAR);
        this.state.usersById().put(user.getUserId(), user);
        return toAuthResult(user);
    }

    private AuthResult toAuthResult(final User user) {
        return new AuthResult(
                "local-token-" + user.getUserId(),
                user.getUserId(),
                user.getFullName());
    }
}
