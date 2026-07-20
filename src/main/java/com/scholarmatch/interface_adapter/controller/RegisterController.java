package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.register.RegisterInputBoundary;

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
}
