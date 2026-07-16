package com.scholarmatch.usecase.dislike;

/**
 * Input boundary for the dislike use case.
 */
public interface DislikeInputBoundary {

    /**
     * Records that the current user disliked (permanently rejected) the given user, so they
     * are not recommended to the current user again.
     *
     * @param inputData the ID of the user who was disliked
     */
    void execute(DislikeInputData inputData);
}
