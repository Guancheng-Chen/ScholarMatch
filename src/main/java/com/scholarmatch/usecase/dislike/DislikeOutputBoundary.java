package com.scholarmatch.usecase.dislike;

/**
 * Output boundary (presenter interface) for the dislike use case.
 */
public interface DislikeOutputBoundary {

    /**
     * Called when a dislike action is successfully recorded.
     */
    void prepareSuccessView();

    /**
     * Called when a dislike action fails to be recorded.
     *
     * @param errorMessage the reason for failure
     */
    void prepareFailView(String errorMessage);
}
