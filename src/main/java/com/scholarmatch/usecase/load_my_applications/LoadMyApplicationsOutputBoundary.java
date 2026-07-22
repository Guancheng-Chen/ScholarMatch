package com.scholarmatch.usecase.load_my_applications;

public interface LoadMyApplicationsOutputBoundary {
    void prepareSuccessView(LoadMyApplicationsOutputData outputData);

    void prepareFailView(String error);
}
