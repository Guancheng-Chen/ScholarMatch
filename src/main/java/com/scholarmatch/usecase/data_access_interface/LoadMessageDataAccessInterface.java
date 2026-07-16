package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.exception.ExternalServiceException;

import java.util.List;

/**
 * Port for loading a conversation between the current user and another scholar.
 *
 * <p>Kept separate from SendMessageDataAccessInterface — the load-message use
 * case only reads messages, it has no reason to depend on send capability just because a
 * single server-facing class happens to implement both ports.
 */
public interface LoadMessageDataAccessInterface {

    /**
     * Returns the full conversation between the current user and the given scholar,
     * ordered oldest to newest.
     *
     * @param otherUserId the ID of the other participant in the conversation
     * @return the conversation history
     * @throws ExternalServiceException if the request fails
     */
    List<Message> getConversation(String otherUserId);
}
