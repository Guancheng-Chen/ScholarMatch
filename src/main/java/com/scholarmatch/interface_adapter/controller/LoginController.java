package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.login.LoginInputBoundary;

/**
 * Controller for the login use case.
 */
public final class LoginController {

    private final LoginInputBoundary interactor;

    /**
     * Constructs a LoginController.
     *
     * @param interactor the login use case
     */
    public LoginController(final LoginInputBoundary interactor) {
        this.interactor = interactor;
    }
}
