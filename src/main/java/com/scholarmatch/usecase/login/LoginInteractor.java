package com.scholarmatch.usecase.login;

import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SessionWriterInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

/**
 * Interactor implementing the login use case.
 *
 * <p>Delegates credential verification to the server via LoginDataAccessInterface.
 * On success, stores the returned JWT and user ID in the session.
 */
public final class LoginInteractor implements LoginInputBoundary {

    private final LoginDataAccessInterface authDataAccessObject;
    private final SessionWriterInterface sessionManager;
    private final LoginOutputBoundary outputBoundary;

    /**
     * Constructs a LoginInteractor.
     *
     * @param authDataAccessObject server-side auth gateway
     * @param sessionManager       records the authenticated user and token after login
     * @param outputBoundary       the presenter that receives the result
     */
    public LoginInteractor(
        final LoginDataAccessInterface authDataAccessObject,
        final SessionWriterInterface sessionManager,
        final LoginOutputBoundary outputBoundary) {
        this.authDataAccessObject = authDataAccessObject;
        this.sessionManager = sessionManager;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final LoginInputData inputData) {
        try {
            final AuthResult result =
                authDataAccessObject.login(inputData.getEmail(), inputData.getPassword());
            sessionManager.setCurrentUserId(result.userId());
            sessionManager.setToken(result.token());
            outputBoundary.prepareSuccessView(
                new LoginOutputData(result.userId(), result.displayName()));
        } catch (final DataAccessException e) {
            outputBoundary.prepareFailView(e.getMessage());
        }
    }
}

