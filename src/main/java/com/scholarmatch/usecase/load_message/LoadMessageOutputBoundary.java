package com.scholarmatch.usecase.load_message;


/**
 * Output boundary (presenter interface) for the load-message use case.
 */
public interface LoadMessageOutputBoundary {


    /**
     * Prepares the success view with the loaded conversation.
     *
     * @param outputData the conversation history
     */
    void prepareSuccessView(LoadMessageOutputData outputData);


    /**
     * Prepares the failure view.
     *
     * @param errorMessage the reason for failure
     */
    void prepareFailView(String errorMessage);
}
