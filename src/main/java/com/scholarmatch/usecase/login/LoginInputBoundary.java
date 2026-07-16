package com.scholarmatch.usecase.login;

/**
 * Input boundary (use-case interface) for the login use case.
 */
public interface LoginInputBoundary {

    /**
     * Authenticates the user with the given credentials.
     *
     * @param inputData the email and password supplied by the user
     */
    void execute(LoginInputData inputData);
}
