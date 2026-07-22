package com.scholarmatch.usecase.load_postings;

/**
 * Output boundary for the load postings use case.
 */
public interface LoadPostingsOutputBoundary {

    /**
     * Prepares the success view.
     *
     * @param outputData the output data for the view
     */
    void prepareSuccessView(LoadPostingsOutputData outputData);

    /**
     * Prepares the failure view.
     *
     * @param error the error message to display
     */
    void prepareFailView(String error);
}
