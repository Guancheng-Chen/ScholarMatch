package com.scholarmatch.usecase.recommend;

/**
 * Output port (boundary) for the get-recommendations use case.
 */
public interface RecommendOutputBoundary {
    /**
     * Prepares the success view with a ranked recommendation list.
     *
     * @param outputData the ranked recommendations
     */
    void prepareSuccessView(RecommendOutputData outputData);

    /**
     * Prepares the failure view.
     *
     * @param errorMessage the reason for failure
     */
    void prepareFailView(String errorMessage);
}
