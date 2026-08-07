package com.scholarmatch.frameworks.data_access_object.local_mock_server;

import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * In-memory offline implementation of sending a message to a match and loading a conversation.
 */
public final class LocalMessagingRepository implements
        SendMessageDataAccessInterface,
        LoadMessageDataAccessInterface {

    private final LocalServerState state;
    private final CurrentUserProviderInterface session;

    public LocalMessagingRepository(
            final LocalServerState state,
            final CurrentUserProviderInterface session) {
        this.state = state;
        this.session = session;
    }

    @Override
    public Message sendMessage(final String receiverId, final String content) {
        final String currentId = this.session.getCurrentUserId();
        if (!this.state.hasMutualConnection(currentId, receiverId)) {
            throw new InvalidRequestException("You can only message users you have matched with");
        }
        final Message message = new Message(
                UUID.randomUUID().toString(), currentId, receiverId, content, LocalDateTime.now());
        this.state.messages().add(message);
        return message;
    }

    @Override
    public List<Message> getConversation(final String otherUserId) {
        final String currentId = this.session.getCurrentUserId();
        if (!this.state.hasMutualConnection(currentId, otherUserId)) {
            throw new InvalidRequestException("You can only message users you have matched with");
        }
        final List<Message> conversation = new ArrayList<>();
        for (final Message message : this.state.messages()) {
            final boolean betweenTheseTwo =
                    (message.getSenderId().equals(currentId) && message.getReceiverId().equals(otherUserId))
                            || (message.getSenderId().equals(otherUserId) && message.getReceiverId().equals(currentId));
            if (betweenTheseTwo) {
                conversation.add(message);
            }
        }
        return conversation;
    }
}
