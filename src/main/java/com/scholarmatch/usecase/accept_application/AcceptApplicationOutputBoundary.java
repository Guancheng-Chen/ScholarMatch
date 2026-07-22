package com.scholarmatch.usecase.accept_application;

public interface AcceptApplicationOutputBoundary {
    void prepareSuccessView(AcceptApplicationOutputData outputData);

    void prepareFailView(String error);
}
