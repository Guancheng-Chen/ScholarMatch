package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.logout.LogoutInputBoundary;
import com.scholarmatch.usecase.logout.LogoutInputData;

/**
 * Controller for the logout use case.
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
     * Sends a logout request to the logout use case.
     */
    public void logout() {
        final LogoutInputData inputData = new LogoutInputData();
        this.interactor.execute(inputData);
    }
}
