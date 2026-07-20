package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_message.LoadMessageInputBoundary;
import com.scholarmatch.usecase.load_message.LoadMessageInputData;

/**
 * Controller that forwards conversation-loading requests to the load-message use case.
 */
public final class LoadMessageController {

    private final LoadMessageInputBoundary interactor;

    /**
     * Constructs a LoadMessageController.
     *
     * @param interactor the load-message use case
     */
    public LoadMessageController(final LoadMessageInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Loads the conversation between the current user and the given scholar.
     *
     * @param otherUserId the ID of the other participant in the conversation
     */
    public void loadMessages(final String otherUserId) {
        this.interactor.execute(new LoadMessageInputData(otherUserId));
    }
}
