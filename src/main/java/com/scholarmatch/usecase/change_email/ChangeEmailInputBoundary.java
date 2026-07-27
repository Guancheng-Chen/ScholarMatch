package com.scholarmatch.usecase.change_email;

/**
 * Input boundary for changing an account email.
 */
public interface ChangeEmailInputBoundary {

    void execute(ChangeEmailInputData inputData);
}
