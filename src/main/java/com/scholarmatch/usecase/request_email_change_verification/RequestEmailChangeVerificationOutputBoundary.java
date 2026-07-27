package com.scholarmatch.usecase.request_email_change_verification;

/**
 * Output boundary for requesting an email-change code.
 */
public interface RequestEmailChangeVerificationOutputBoundary {

    void prepareSuccessView(RequestEmailChangeVerificationOutputData outputData);

    void prepareFailView(String errorMessage);
}
