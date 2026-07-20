package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.register.RegisterInputBoundary;
import com.scholarmatch.usecase.register.RegisterInputData;

/**
 * Controller for the register use case.
 *
 * <p>Translates raw UI strings from the registration form into a
 * RegisterInputData and invokes the use case input boundary.
 * Controllers in CA never call presenters directly.
 */
public final class RegisterController {

    private final RegisterInputBoundary registerInteractor;

    /**
     * Constructs a RegisterController.
     *
     * @param registerInteractor the input boundary to invoke
     */
    public RegisterController(final RegisterInputBoundary registerInteractor) {
        this.registerInteractor = registerInteractor;
    }

    /**
     * Handles the user submitting the registration form.
     *
     * @param firstName given name
     * @param lastName  family name
     * @param email     email address
     * @param password  plain-text password
     */
    public void execute(
        final String firstName,
        final String lastName,
        final String email,
        final String password) {
        this.registerInteractor.execute(new RegisterInputData(firstName, lastName, email, password));
    }
}
