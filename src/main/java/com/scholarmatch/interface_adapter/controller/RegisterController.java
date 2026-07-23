package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.register.RegisterInputBoundary;
import com.scholarmatch.usecase.register.RegisterInputData;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInputBoundary;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInputData;

/**
 * Controller for the register use case.
 *
 * <p>Translates raw UI strings from the registration form into a
 * RegisterInputData and invokes the use case input boundary.
 * Controllers in CA never call presenters directly.
 */
public final class RegisterController {

    private final RegisterInputBoundary registerInteractor;
    private final RequestEmailVerificationInputBoundary verificationInteractor;

    /**
     * Constructs a RegisterController.
     *
     * @param registerInteractor the input boundary to invoke
     */
    public RegisterController(final RegisterInputBoundary registerInteractor) {
        this(registerInteractor, null);
    }

    public RegisterController(
            final RegisterInputBoundary registerInteractor,
            final RequestEmailVerificationInputBoundary verificationInteractor) {
        this.registerInteractor = registerInteractor;
        this.verificationInteractor = verificationInteractor;
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
            final String password,
            final String verificationCode) {
        this.registerInteractor.execute(
                new RegisterInputData(firstName, lastName, email, password, verificationCode));
    }

    public void sendVerificationCode(final String email) {
        if (this.verificationInteractor == null) {
            throw new IllegalStateException("Email verification is not configured.");
        }
        this.verificationInteractor.execute(new RequestEmailVerificationInputData(email));
    }
}
