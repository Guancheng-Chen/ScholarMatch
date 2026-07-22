package com.scholarmatch.usecase.decline_application;

public interface DeclineApplicationOutputBoundary {
    void prepareSuccessView(DeclineApplicationOutputData outputData);

    void prepareFailView(String error);
}
