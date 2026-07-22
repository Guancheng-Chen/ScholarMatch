package com.scholarmatch.usecase.request_email_verification;

/**
 * Input boundary for requesting a registration verification code.
 */
public interface RequestEmailVerificationInputBoundary {

    void execute(RequestEmailVerificationInputData inputData);
}
