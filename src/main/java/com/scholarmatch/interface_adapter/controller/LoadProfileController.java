package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_profile.LoadProfileInputBoundary;

/**
 * Controller for loading a user profile.
 */
public final class LoadProfileController {

    private final LoadProfileInputBoundary loadProfileInteractor;

    /**
     * Constructs a LoadProfileController.
     *
     * @param loadProfileInteractor the load profile use case
     */
    public LoadProfileController(final LoadProfileInputBoundary loadProfileInteractor) {
        this.loadProfileInteractor = loadProfileInteractor;
    }
}