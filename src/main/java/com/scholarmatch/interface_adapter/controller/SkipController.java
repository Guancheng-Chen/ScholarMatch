package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.skip.SkipInputBoundary;
import com.scholarmatch.usecase.skip.SkipInputData;

/**
 * Controller for the skip use case.
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
     * Submits a skipped user ID.
     *
     * @param skippedUserId the skipped user's ID
     */
    public void skip(final String skippedUserId) {
        this.interactor.execute(new SkipInputData(skippedUserId));
    }
}
