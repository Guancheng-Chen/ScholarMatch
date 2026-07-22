package com.scholarmatch.usecase.apply_to_posting;

public interface ApplyToPostingOutputBoundary {
    void prepareSuccessView(ApplyToPostingOutputData outputData);

    void prepareFailView(String error);
}
