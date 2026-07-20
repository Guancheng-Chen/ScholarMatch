package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_message.LoadMessageInputBoundary;

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
}
