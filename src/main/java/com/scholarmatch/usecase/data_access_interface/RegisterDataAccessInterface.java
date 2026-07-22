package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.register.RegisterAccountData;

/**
 * Port for registering a new user, delegated to the server.
 *
 * <p>Kept separate from LoginDataAccessInterface — the login use case has no
 * reason to depend on RegisterInputData just because a single server-facing
 * class happens to implement both ports.
 */
public interface RegisterDataAccessInterface {

    /**
     * Registers a new user account.
     *
     * @param data all registration fields collected from the UI
     * @return an AuthResult containing the JWT and newly created user identity
     * @throws InvalidRequestException  if the email is already taken or validation fails
     * @throws ExternalServiceException if the server is unreachable
     */
    AuthResult register(RegisterAccountData data);
}
