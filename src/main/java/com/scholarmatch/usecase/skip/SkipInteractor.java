package com.scholarmatch.usecase.skip;

public final class SkipInteractor implements SkipInputBoundary {

    private final SkipOutputBoundary outputBoundary;

    public SkipInteractor(final SkipOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final SkipInputData inputData) {
        outputBoundary.prepareSuccessView();
    }
}
