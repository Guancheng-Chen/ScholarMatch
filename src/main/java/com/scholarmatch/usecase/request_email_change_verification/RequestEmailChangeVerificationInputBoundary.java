package com.scholarmatch.usecase.request_email_change_verification;

/**
 * Input boundary for requesting an email-change code.
 */
public interface RequestEmailChangeVerificationInputBoundary {

    void execute(RequestEmailChangeVerificationInputData inputData);
}
