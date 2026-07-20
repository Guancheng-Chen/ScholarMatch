package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_matches.LoadMatchesInputBoundary;

/**
 * Controller for the load-confirmed-matches use case.
 */
public final class LoadMatchesController {

    private final LoadMatchesInputBoundary loadMatchesInteractor;

    /**
     * Constructs a LoadMatchesController.
     *
     * @param loadMatchesInteractor the input boundary to invoke
     */
    public LoadMatchesController(final LoadMatchesInputBoundary loadMatchesInteractor) {
        this.loadMatchesInteractor = loadMatchesInteractor;
    }

    /**
     * Requests the current user's confirmed matches.
     */
    public void execute() {
        this.loadMatchesInteractor.execute();
    }
}
