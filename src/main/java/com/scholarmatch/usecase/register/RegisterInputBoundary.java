package com.scholarmatch.usecase.register;

/**
 * Input port (boundary) for the register use case.
 */
public interface RegisterInputBoundary {
    /**
     * Executes the register use case with the supplied input.
     *
     * @param inputData the registration form data
     */
    void execute(RegisterInputData inputData);
}
