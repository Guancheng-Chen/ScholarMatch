package com.scholarmatch.usecase.request_email_verification;

/**
 * Output boundary for requesting a registration verification code.
 */
public interface RequestEmailVerificationOutputBoundary {

    void prepareSuccessView(RequestEmailVerificationOutputData outputData);

    void prepareFailView(String error);
}
