package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.send_message.SendMessageInputBoundary;

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
}