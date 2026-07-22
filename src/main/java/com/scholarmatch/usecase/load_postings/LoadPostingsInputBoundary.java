package com.scholarmatch.usecase.load_postings;

/**
 * Input boundary for loading postings.
 */
public interface LoadPostingsInputBoundary {

    /**
     * Executes the load postings use case.
     *
     * @param inputData the requested posting scope
     */
    void execute(LoadPostingsInputData inputData);
}
