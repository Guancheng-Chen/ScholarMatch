package com.scholarmatch.usecase.skip;

/**
 * Interactor for the skip use case.
 *
 * <p>This interactor does not persist a dislike or contact the server.
 * It only reports that the current candidate profile should be skipped.
 */
public final class SkipInteractor implements SkipInputBoundary {

    private final SkipOutputBoundary outputBoundary;

    /**
     * Constructs a skip interactor.
     *
     * @param outputBoundary the output boundary used to prepare the success view
     */
    public SkipInteractor(final SkipOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Executes the skip use case.
     *
     * @param inputData the input data containing the skipped candidate's ID
     */
    @Override
    public void execute(final SkipInputData inputData) {
        outputBoundary.prepareSuccessView();
    }
}
