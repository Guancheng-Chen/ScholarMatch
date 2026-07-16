package com.scholarmatch.usecase.register;

/**
 * Output port (boundary) for the register use case.
 */
public interface RegisterOutputBoundary {
    /**
     * Prepares the success view with the registration result.
     *
     * @param outputData the registration result data
     */
    void prepareSuccessView(RegisterOutputData outputData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param errorMessage the human-readable reason for failure
     */
    void prepareFailView(String errorMessage);
}

