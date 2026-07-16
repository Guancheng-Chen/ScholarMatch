package com.scholarmatch.usecase.logout;

/**
 * Output boundary (presenter interface) for the logout use case.
 */
public interface LogoutOutputBoundary {

    /**
     * Called once the session has been cleared.
     *
     * @param outputData the (currently empty) logout result
     */
    void prepareSuccessView(LogoutOutputData outputData);
}

