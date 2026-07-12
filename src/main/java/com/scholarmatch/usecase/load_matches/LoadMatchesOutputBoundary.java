package com.scholarmatch.usecase.load_matches;

/**
 * Output port (boundary) for the load-confirmed-matches use case.
 */
public interface LoadMatchesOutputBoundary {

    /**
     * Prepares the success view with the current user's confirmed matches.
     *
     * @param outputData the confirmed matches
     */
    void prepareSuccessView(LoadMatchesOutputData outputData);

    /**
     * Prepares the failure view.
     *
     * @param errorMessage the reason for failure
     */
    void prepareFailView(String errorMessage);
}
