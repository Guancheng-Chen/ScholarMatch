package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.accept_application.AcceptApplicationInputBoundary;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInputData;

public final class AcceptApplicationController {
    private final AcceptApplicationInputBoundary interactor;

    public AcceptApplicationController(final AcceptApplicationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(final String applicationId) {
        this.interactor.execute(new AcceptApplicationInputData(applicationId));
    }

    public void accept(final String applicationId) {
        execute(applicationId);
    }
}
