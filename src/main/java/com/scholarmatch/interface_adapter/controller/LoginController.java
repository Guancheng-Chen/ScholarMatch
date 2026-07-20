package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.login.LoginInputBoundary;
import com.scholarmatch.usecase.login.LoginInputData;

/**
 * Controller that forwards login form submission to the login use case.
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
     * Called when the user submits the login form.
     *
     * @param email    the entered email address
     * @param password the entered plain-text password
     */
    public void login(final String email, final String password) {
        this.interactor.execute(new LoginInputData(email, password));
    }
}
