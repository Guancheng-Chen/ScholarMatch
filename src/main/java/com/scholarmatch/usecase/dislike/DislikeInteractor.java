package com.scholarmatch.usecase.dislike;

import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

/**
 * Interactor for recording a Dislike decision.
 */
public final class DislikeInteractor implements DislikeInputBoundary {

    private final DislikeDataAccessInterface dataAccessObject;
    private final DislikeOutputBoundary outputBoundary;

    /**
     * Constructs a Dislike interactor.
     *
     * @param dataAccessObject the data access object used to record the decision
     * @param outputBoundary the presenter used to report the result
     */
    public DislikeInteractor(
            final DislikeDataAccessInterface dataAccessObject,
            final DislikeOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Records the Dislike decision and prepares the appropriate view.
     *
     * @param inputData the selected scholar's ID
     */
    @Override
    public void execute(final DislikeInputData inputData) {
        try {
            dataAccessObject.dislike(inputData.getDislikedUserId());
            outputBoundary.prepareSuccessView();
        }
        catch (final DataAccessException exception) {
            outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
