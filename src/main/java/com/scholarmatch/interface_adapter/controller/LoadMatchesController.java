package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_matches.LoadMatchesInputBoundary;

/**
 * Controller for the load-matches use case.
 */
public final class LoadMatchesController {

    private final LoadMatchesInputBoundary loadMatchesInteractor;

    /**
     * Constructs a LoadMatchesController.
     *
     * @param loadMatchesInteractor the load-matches use case
     */
    public LoadMatchesController(
            final LoadMatchesInputBoundary loadMatchesInteractor) {
        this.loadMatchesInteractor = loadMatchesInteractor;
    }
}
