package com.scholarmatch.usecase.connect;

import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

/**
 * Interactor implementing the connect use case.
 *
 * <p>Sends the connect to the server, which persists it and checks for mutual matches.
 * If a mutual match is detected, the connected user (already available on the client
 * from the card being displayed) is passed to the presenter.
 */
public final class ConnectInteractor implements ConnectInputBoundary {

    private final ConnectDataAccessInterface connectDataAccessObject;
    private final ConnectOutputBoundary outputBoundary;

    /**
     * Constructs a ConnectInteractor.
     *
     * @param connectDataAccessObject server gateway for recording connects
     * @param outputBoundary          the presenter that receives the result
     */
    public ConnectInteractor(
            final ConnectDataAccessInterface connectDataAccessObject,
            final ConnectOutputBoundary outputBoundary) {
        this.connectDataAccessObject = connectDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final ConnectInputData inputData) {
        try {
            final boolean matched = connectDataAccessObject.connect(inputData.getConnectedUserId());
            if (matched) {
                outputBoundary.prepareMatchFound(new ConnectOutputData(inputData.getConnectedUser()));
            } else {
                outputBoundary.prepareNoMatch();
            }
        } catch (final DataAccessException e) {
            outputBoundary.prepareNoMatch();
        }
    }
}
