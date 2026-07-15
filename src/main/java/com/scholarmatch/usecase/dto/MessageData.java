package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Plain, read-only snapshot of a single chat message for use in Input/OutputData DTOs
 * and everything downstream of them (presenters, view models, views).
 */
public final class MessageData {

    private final String messageId;
    private final String senderId;
    private final String receiverId;
    private final String content;
    private final LocalDateTime sentAt;

    /**
     * Constructs a MessageData snapshot.
     *
     * @param messageId  the unique identifier assigned by the server
     * @param senderId   the ID of the user who sent the message
     * @param receiverId the ID of the user the message was sent to
     * @param content    the message text
     * @param sentAt     when the message was sent
     */
    public MessageData(
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
     * Builds a MessageData snapshot from a Message entity.
     *
     * @param message the entity to snapshot
     * @return the equivalent read-only DTO
     */
    public static MessageData from(final Message message) {
        return new MessageData(
                message.getMessageId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent(),
                message.getSentAt());
    }

    /**
     * Builds a list of MessageData snapshots from a list of Message entities.
     *
     * @param messages the entities to snapshot
     * @return the equivalent read-only DTOs, in the same order
     */
    public static List<MessageData> fromAll(final List<Message> messages) {
        return messages.stream().map(MessageData::from).collect(Collectors.toList());
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
