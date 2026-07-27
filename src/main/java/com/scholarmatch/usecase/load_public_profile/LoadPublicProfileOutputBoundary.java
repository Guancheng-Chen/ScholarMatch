package com.scholarmatch.usecase.load_public_profile;

public interface LoadPublicProfileOutputBoundary {

    void prepareSuccessView(LoadPublicProfileOutputData outputData);

    void prepareFailView(String errorMessage);
}
