package com.scholarmatch.usecase.change_password;

/**
 * Input boundary for changing an account password.
 */
public interface ChangePasswordInputBoundary {

    void execute(ChangePasswordInputData inputData);
}
