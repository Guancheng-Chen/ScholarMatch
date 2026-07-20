package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.send_message.SendMessageInputBoundary;
import com.scholarmatch.usecase.send_message.SendMessageInputData;

/**
 * Controller for the send-message use case.
 */
public final class SendMessageController {

    private final SendMessageInputBoundary interactor;

    /**
     * Constructs a SendMessageController.
     *
     * @param interactor the send-message use case
     */
    public SendMessageController(final SendMessageInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Submits a message to the send-message use case.
     *
     * @param receiverId the receiver's ID
     * @param content the message content
     */
    public void sendMessage(final String receiverId, final String content) {
        this.interactor.execute(new SendMessageInputData(receiverId, content));
    }
}