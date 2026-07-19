package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.update_profile.UpdateProfileInputBoundary;

/**
 * Controller for the update-profile use case.
 */
public final class UpdateProfileController {

    private final UpdateProfileInputBoundary interactor;

    /**
     * Constructs an UpdateProfileController.
     *
     * @param interactor the update-profile use case
     */
    public UpdateProfileController(final UpdateProfileInputBoundary interactor) {
        this.interactor = interactor;
    }
}
