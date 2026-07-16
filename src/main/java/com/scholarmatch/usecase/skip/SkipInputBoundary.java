package com.scholarmatch.usecase.skip;

/**
 * Input boundary for the skip use case.
 */
public interface SkipInputBoundary {

    /**
     * Executes the skip use case for the selected candidate.
     *
     * @param inputData the skip input data
     */
    void execute(SkipInputData inputData);
}
