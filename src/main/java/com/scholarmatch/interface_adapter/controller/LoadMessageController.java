package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_message.LoadMessageInputBoundary;
import com.scholarmatch.usecase.load_message.LoadMessageInputData;

/**
 * Controller for the load-message use case.
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
     * Loads messages exchanged with another user.
     *
     * @param otherUserId the other user's ID
     */
    public void loadMessages(final String otherUserId) {
        final LoadMessageInputData inputData =
                new LoadMessageInputData(otherUserId);

        this.interactor.execute(inputData);
    }
}
