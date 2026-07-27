package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.request_email_change_verification.RequestEmailChangeVerificationInputBoundary;
import com.scholarmatch.usecase.request_email_change_verification.RequestEmailChangeVerificationInputData;

/**
 * Controller for requesting an account email-change code.
 */
public final class RequestEmailChangeVerificationController {

    private final RequestEmailChangeVerificationInputBoundary interactor;

    public RequestEmailChangeVerificationController(
            final RequestEmailChangeVerificationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(final String email) {
        this.interactor.execute(
                new RequestEmailChangeVerificationInputData(email));
    }
}
