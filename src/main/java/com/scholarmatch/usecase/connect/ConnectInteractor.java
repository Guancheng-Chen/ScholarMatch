package com.scholarmatch.usecase.connect;

import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;

/**
 * Interactor for recording a Connect decision and reporting whether it creates
 * a mutual match.
 */
public final class ConnectInteractor implements ConnectInputBoundary {

    private final ConnectDataAccessInterface dataAccessObject;
    private final ConnectOutputBoundary outputBoundary;

    /**
     * Constructs a Connect interactor.
     *
     * @param dataAccessObject the data access object used to record the decision
     * @param outputBoundary the presenter used to report the result
     */
    public ConnectInteractor(
            final ConnectDataAccessInterface dataAccessObject,
            final ConnectOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Records the Connect decision and prepares the appropriate view.
     *
     * @param inputData the selected scholar's ID and profile snapshot
     */
    @Override
    public void execute(final ConnectInputData inputData) {
        final ConnectOutputData outputData =
                dataAccessObject.connect(inputData.getConnectedUserId());

        if (outputData == null) {
            outputBoundary.prepareNoMatch();
        }
        else {
            outputBoundary.prepareMatchFound(outputData);
        }
    }
}
