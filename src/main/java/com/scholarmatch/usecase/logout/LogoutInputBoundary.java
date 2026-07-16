package com.scholarmatch.usecase.logout;

/**
 * Input boundary (use-case interface) for the logout use case.
 */
public interface LogoutInputBoundary {

    /**
     * Ends the current session.
     *
     * @param inputData the logout request (currently carries no fields)
     */
    void execute(LogoutInputData inputData);
}

