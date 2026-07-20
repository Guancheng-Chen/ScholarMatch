package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.register.RegisterInputBoundary;
import com.scholarmatch.usecase.register.RegisterInputData;

/**
 * Controller for the register use case.
 */
public final class RegisterController {

    private final RegisterInputBoundary registerInteractor;

    /**
     * Constructs a RegisterController.
     *
     * @param registerInteractor the register use case
     */
    public RegisterController(final RegisterInputBoundary registerInteractor) {
        this.registerInteractor = registerInteractor;
    }

    /**
     * Submits registration data to the register use case.
     *
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param email     the user's email
     * @param password  the user's password
     */
    public void execute(
            final String firstName,
            final String lastName,
            final String email,
            final String password) {
        this.registerInteractor.execute(
                new RegisterInputData(firstName, lastName, email, password));
    }
}
