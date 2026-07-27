package com.scholarmatch.usecase.change_password;

/**
 * Output boundary for changing an account password.
 */
public interface ChangePasswordOutputBoundary {

    void prepareSuccessView(ChangePasswordOutputData outputData);

    void prepareFailView(String errorMessage);
}
