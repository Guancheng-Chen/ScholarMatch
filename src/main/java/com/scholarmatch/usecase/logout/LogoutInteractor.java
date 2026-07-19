package com.scholarmatch.usecase.logout;

import com.scholarmatch.usecase.data_access_interface.SessionClearerInterface;

/**
 * Interactor implementing the logout use case.
 *
 * <p>Clears the session held behind SessionClearerInterface — logout only ever
 * clears, never reads or writes, so it depends on that narrow port rather than the full
 * session abstraction — so the view layer never touches the session directly and only ever
 * learns that a user logged out via LogoutOutputBoundary.
 */
public final class LogoutInteractor implements LogoutInputBoundary {

    private final SessionClearerInterface sessionManager;
    private final LogoutOutputBoundary outputBoundary;

    /**
     * Constructs a LogoutInteractor.
     *
     * @param sessionManager clears the authenticated user and token on logout
     * @param outputBoundary the presenter that receives the result
     */
    public LogoutInteractor(
            final SessionClearerInterface sessionManager,
            final LogoutOutputBoundary outputBoundary) {
        this.sessionManager = sessionManager;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final LogoutInputData inputData) {
        this.sessionManager.clearSession();
        this.outputBoundary.prepareSuccessView(new LogoutOutputData());
    }
}

