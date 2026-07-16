package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;

/**
 * Port for authenticating an existing user, delegated to the server.
 *
 * <p>Kept separate from RegisterDataAccessInterface — the login use case has no
 * reason to depend on RegisterInputData just because a single server-facing
 * class happens to implement both ports.
 */
public interface LoginDataAccessInterface {

    /**
     * Authenticates a user by email and password.
     *
     * @param email    the user's email
     * @param password the plain-text password to verify
     * @return an AuthResult containing the JWT and user identity
     * @throws InvalidRequestException  if the credentials are invalid
     * @throws ExternalServiceException if the server is unreachable
     */
    AuthResult login(String email, String password);
}
