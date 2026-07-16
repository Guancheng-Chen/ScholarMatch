package com.scholarmatch.usecase.connect;

import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;

public final class ConnectInteractor implements ConnectInputBoundary {

    private final ConnectDataAccessInterface dataAccessObject;
    private final ConnectOutputBoundary outputBoundary;
}