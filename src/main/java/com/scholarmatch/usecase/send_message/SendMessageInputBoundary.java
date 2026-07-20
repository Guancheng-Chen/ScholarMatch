package com.scholarmatch.usecase.send_message;

/**
 * Input boundary for the send-message use case.
 */
public interface SendMessageInputBoundary {


    /**
     * Sends a chat message from the current user to another scholar.
     *
     * @param inputData the receiver and message text
     */
    void execute(SendMessageInputData inputData);
}
