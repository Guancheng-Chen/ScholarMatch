package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.logout.LogoutInputBoundary;
import com.scholarmatch.usecase.logout.LogoutInputData;

/**
 * Controller that forwards logout requests to the logout use case.
 */
public final class LogoutController {

    private final LogoutInputBoundary interactor;

    /**
     * Constructs a LogoutController.
     *
     * @param interactor the logout use case
     */
    public LogoutController(final LogoutInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the user clicks Logout.
     */
    public void logout() {
        this.interactor.execute(new LogoutInputData());
    }
}
