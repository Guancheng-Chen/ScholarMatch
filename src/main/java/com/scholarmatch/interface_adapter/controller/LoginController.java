package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.login.LoginInputBoundary;
import com.scholarmatch.usecase.login.LoginInputData;

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

    /**
     * Sends the entered credentials to the login use case.
     *
     * @param email the entered email
     * @param password the entered password
     */
    public void login(final String email, final String password) {
        final LoginInputData inputData = new LoginInputData(email, password);
        this.interactor.execute(inputData);
    }
}
