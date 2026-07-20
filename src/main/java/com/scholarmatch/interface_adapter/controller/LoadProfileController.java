package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_profile.LoadProfileInputBoundary;

/**
 * Controller for the load-current-profile use case.
 */
public final class LoadProfileController {

    private final LoadProfileInputBoundary loadProfileInteractor;

    /**
     * Constructs a LoadProfileController.
     *
     * @param loadProfileInteractor the input boundary to invoke
     */
    public LoadProfileController(final LoadProfileInputBoundary loadProfileInteractor) {
        this.loadProfileInteractor = loadProfileInteractor;
    }

    /**
     * Requests the current user's full saved profile.
     */
    public void execute() {
        this.loadProfileInteractor.execute();
    }
}