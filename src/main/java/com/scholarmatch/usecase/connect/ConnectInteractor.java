package com.scholarmatch.usecase.connect;

import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;

public final class ConnectInteractor implements ConnectInputBoundary {

    private final ConnectDataAccessInterface dataAccessObject;
    private final ConnectOutputBoundary outputBoundary;

    public ConnectInteractor(
            final ConnectDataAccessInterface dataAccessObject,
            final ConnectOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }
}