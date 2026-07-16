package com.scholarmatch.usecase.login;

/**
 * Output boundary (presenter interface) for the login use case.
 */
public interface LoginOutputBoundary {

    /**
     * Called when authentication succeeds.
     *
     * @param outputData the authenticated user's basic info
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Called when authentication fails (wrong email or password).
     *
     * @param errorMessage a human-readable error description
     */
    void prepareFailView(String errorMessage);
}

