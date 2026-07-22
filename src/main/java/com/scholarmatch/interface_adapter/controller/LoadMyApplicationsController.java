package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsInputBoundary;

/**
 * Controller for loading the current user's applications.
 */
public final class LoadMyApplicationsController {
    private final LoadMyApplicationsInputBoundary interactor;

    /**
     * Constructs a controller for loading applications.
     *
     * @param interactor the load applications input boundary
     */
    public LoadMyApplicationsController(final LoadMyApplicationsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the load applications use case.
     */
    public void execute() {
        this.interactor.execute();
    }

    /**
     * Loads the current user's applications.
     */
    public void loadMyApplications() {
        execute();
    }
}
