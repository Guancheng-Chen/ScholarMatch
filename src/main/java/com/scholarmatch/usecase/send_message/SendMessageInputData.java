package com.scholarmatch.usecase.send_message;

/**
 * Input data for sending a chat message.
 */
public final class SendMessageInputData {


    private final String receiverId;
    private final String content;


    /**
     * Constructs send-message input data.
     *
     * @param receiverId the ID of the scholar to send the message to
     * @param content    the message text
     */
    public SendMessageInputData(final String receiverId, final String content) {
        this.receiverId = receiverId;
        this.content = content;
    }


    /**
     * Returns the ID of the scholar to send the message to.
     *
     * @return the receiver's user ID
     */
    public String getReceiverId() {
        return this.receiverId;
    }


    /**
     * Returns the message text.
     *
     * @return the content
     */
    public String getContent() {
        return this.content;
    }
}
