package com.scholarmatch.usecase.change_email;

/**
 * Output boundary for changing an account email.
 */
public interface ChangeEmailOutputBoundary {

    void prepareSuccessView(ChangeEmailOutputData outputData);

    void prepareFailView(String errorMessage);
}
