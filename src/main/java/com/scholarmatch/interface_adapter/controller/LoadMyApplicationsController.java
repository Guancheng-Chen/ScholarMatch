package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsInputBoundary;

public final class LoadMyApplicationsController {
    private final LoadMyApplicationsInputBoundary interactor;

    public LoadMyApplicationsController(final LoadMyApplicationsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        this.interactor.execute();
    }

    public void loadMyApplications() {
        execute();
    }
}
