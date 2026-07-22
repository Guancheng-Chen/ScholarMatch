package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.decline_application.DeclineApplicationInputBoundary;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInputData;

public final class DeclineApplicationController {
    private final DeclineApplicationInputBoundary interactor;

    public DeclineApplicationController(final DeclineApplicationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(final String applicationId) {
        this.interactor.execute(new DeclineApplicationInputData(applicationId));
    }

    public void decline(final String applicationId) {
        execute(applicationId);
    }
}
