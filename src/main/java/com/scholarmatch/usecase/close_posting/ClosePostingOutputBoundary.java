package com.scholarmatch.usecase.close_posting;

public interface ClosePostingOutputBoundary {
    void prepareSuccessView(ClosePostingOutputData outputData);

    void prepareFailView(String error);
}
