package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.paper_lookup.PaperLookupInputBoundary;

/**
 * Controller for the paper lookup use case.
 */
public final class PaperLookupController {

    private final PaperLookupInputBoundary paperLookupInteractor;

    /**
     * Constructs a PaperLookupController.
     *
     * @param paperLookupInteractor the paper lookup use case
     */
    public PaperLookupController(final PaperLookupInputBoundary paperLookupInteractor) {
        this.paperLookupInteractor = paperLookupInteractor;
    }
}
