package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.skip.SkipInputBoundary;
import com.scholarmatch.usecase.skip.SkipInputData;

/**
 * Controller that forwards skip actions to the skip use case.
 */
public final class SkipController {

    private final SkipInputBoundary interactor;

    /**
     * Constructs a SkipController.
     *
     * @param interactor the skip use case
     */
    public SkipController(final SkipInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the current user skips a user card.
     *
     * @param skippedUserId the ID of the user shown on the card
     */
    public void skip(final String skippedUserId) {
        this.interactor.execute(new SkipInputData(skippedUserId));
    }
}
