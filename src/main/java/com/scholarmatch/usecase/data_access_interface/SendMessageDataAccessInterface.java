package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;

/**
 * Port for sending a chat message to another scholar.
 *
 * <p>Kept separate from LoadMessageDataAccessInterface — the send-message use
 * case has no reason to depend on conversation-loading capability just because a single
 * server-facing class happens to implement both ports.
 *
 * <p>Chat is only open between users who have mutually matched — implementations reject
 * an attempt to message someone who hasn't with an InvalidRequestException, mirroring
 * the same gate the server enforces.
 */
public interface SendMessageDataAccessInterface {

    /**
     * Sends a message from the current user to the given scholar.
     *
     * @param receiverId the ID of the scholar to send the message to
     * @param content    the message text
     * @return the persisted message, including its assigned ID and send timestamp
     * @throws InvalidRequestException  if the current user and receiverId have not
     *                                   mutually matched
     * @throws ExternalServiceException if the request fails
     */
    Message sendMessage(String receiverId, String content);
}
