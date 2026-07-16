package com.scholarmatch.entity;

import java.time.LocalDateTime;

/**
 * Represents a single chat message sent between two users.
 */
public final class Message {

    private final String messageId;
    private final String senderId;
    private final String receiverId;
    private final String content;
    private final LocalDateTime sentAt;

    /**
     * Constructs a Message.
     *
     * @param messageId  the unique identifier assigned by the server
     * @param senderId   the ID of the user who sent the message
     * @param receiverId the ID of the user the message was sent to
     * @param content    the message text
     * @param sentAt     when the message was sent
     */
    public Message(
            final String messageId,
            final String senderId,
            final String receiverId,
            final String content,
            final LocalDateTime sentAt) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = sentAt;
    }

    /**
     * Returns the unique identifier for this message.
     *
     * @return the message ID
     */
    public String getMessageId() {
        return this.messageId;
    }

    /**
     * Returns the ID of the user who sent this message.
     *
     * @return the sender's user ID
     */
    public String getSenderId() {
        return this.senderId;
    }

    /**
     * Returns the ID of the user this message was sent to.
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

    /**
     * Returns when this message was sent.
     *
     * @return the send timestamp
     */
    public LocalDateTime getSentAt() {
        return this.sentAt;
    }
}
