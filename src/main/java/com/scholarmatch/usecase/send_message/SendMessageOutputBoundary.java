package com.scholarmatch.usecase.send_message;


/**
 * Output boundary (presenter interface) for the send-message use case.
 */
public interface SendMessageOutputBoundary {


    /**
     * Called when a message is sent successfully.
     *
     * @param outputData the persisted message
     */
    void prepareSuccessView(SendMessageOutputData outputData);


    /**
     * Called when sending a message fails — e.g. the two users have not mutually
     * matched, or the request could not reach the server.
     *
     * @param errorMessage the reason for failure
     */
    void prepareFailView(String errorMessage);
}
