package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.skip.SkipInputBoundary;

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
}
