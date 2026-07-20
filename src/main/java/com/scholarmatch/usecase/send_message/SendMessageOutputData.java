package com.scholarmatch.usecase.send_message;

import com.scholarmatch.usecase.dto.MessageData;

/**
 * Output data for the send-message use case.
 */
public final class SendMessageOutputData {


    private final MessageData message;


    /**
     * Constructs send-message output.
     *
     * @param message the message as persisted by the server
     */
    public SendMessageOutputData(final MessageData message) {
        this.message = message;
    }


    /**
     * Returns the persisted message.
     *
     * @return the sent message
     */
    public MessageData getMessage() {
        return this.message;
    }
}
