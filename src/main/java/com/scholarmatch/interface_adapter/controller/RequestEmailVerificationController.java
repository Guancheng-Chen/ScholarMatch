package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInputBoundary;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInputData;

/**
 * Controller for the request-email-verification use case.
 *
 * <p>Split out from RegisterController so each controller has exactly one reason to
 * change — this one only when the "send me a code" input translation changes, independent
 * of registration's own input translation.
 */
public final class RequestEmailVerificationController {

    private final RequestEmailVerificationInputBoundary interactor;

    /**
     * Constructs a RequestEmailVerificationController.
     *
     * @param interactor the input boundary to invoke
     */
    public RequestEmailVerificationController(final RequestEmailVerificationInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Handles the user asking for a verification code to be sent to the given email.
     *
     * @param email the destination email
     */
    public void sendVerificationCode(final String email) {
        this.interactor.execute(new RequestEmailVerificationInputData(email));
    }
}
