package com.scholarmatch.usecase.send_message;

import com.scholarmatch.entity.Message;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;
import com.scholarmatch.usecase.dto.MessageData;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor implementing the send-message use case.
 *
 * <p>Runs a local validation pass over the message content (required, length-limited)
 * before delegating persistence to the server via SendMessageDataAccessInterface, which
 * enforces that the sender and receiver have mutually matched.
 */
public final class SendMessageInteractor implements SendMessageInputBoundary {


    private static final int MAX_CONTENT_LENGTH = 1000;


    private final SendMessageDataAccessInterface messageDataAccessObject;
    private final SendMessageOutputBoundary outputBoundary;


    /**
     * Constructs a SendMessageInteractor.
     *
     * @param messageDataAccessObject server gateway for sending messages
     * @param outputBoundary          the presenter that receives the result
     */
    public SendMessageInteractor(
            final SendMessageDataAccessInterface messageDataAccessObject,
            final SendMessageOutputBoundary outputBoundary) {
        this.messageDataAccessObject = messageDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final SendMessageInputData inputData) {
        final List<String> errors = validate(inputData);
        if (!errors.isEmpty()) {
            this.outputBoundary.prepareFailView(String.join("\n", errors));
            return;
        }
        try {
            final Message message =
                    this.messageDataAccessObject.sendMessage(inputData.getReceiverId(), inputData.getContent());
            this.outputBoundary.prepareSuccessView(new SendMessageOutputData(MessageData.from(message)));
        } catch (final DataAccessException e) {
            this.outputBoundary.prepareFailView(e.getMessage());
        }
    }

    /**
     * Runs every local (non-server) validation rule against the given input, returning
     * every violation found. An empty list means the input is locally valid and can be
     * sent to the server.
     */
    private List<String> validate(final SendMessageInputData inputData) {
        final List<String> errors = new ArrayList<>();

        final String content = inputData.getContent();
        if (content == null || content.isBlank()) {
            errors.add("Message cannot be empty.");
        } else if (content.length() > MAX_CONTENT_LENGTH) {
            errors.add("Message must be at most " + MAX_CONTENT_LENGTH
                    + " characters (currently " + content.length() + ").");
        }

        if (inputData.getReceiverId() == null || inputData.getReceiverId().isBlank()) {
            errors.add("Receiver is required.");
        }

        return errors;
    }
}
