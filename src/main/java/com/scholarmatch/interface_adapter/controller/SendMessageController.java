package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.send_message.SendMessageInputBoundary;
import com.scholarmatch.usecase.send_message.SendMessageInputData;

/**
 * Controller that forwards send actions to the send-message use case.
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
     * Sends a chat message from the current user to the given scholar.
     *
     * @param receiverId the ID of the scholar to send the message to
     * @param content    the message text
     */
    public void sendMessage(final String receiverId, final String content) {
        this.interactor.execute(new SendMessageInputData(receiverId, content));
    }
}
