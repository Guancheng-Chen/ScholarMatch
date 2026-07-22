package com.scholarmatch.usecase.load_postings;

public interface LoadPostingsOutputBoundary {
    void prepareSuccessView(LoadPostingsOutputData outputData);

    void prepareFailView(String error);
}
