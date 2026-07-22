package com.scholarmatch.usecase.create_posting;

public interface CreatePostingOutputBoundary {
    void prepareSuccessView(CreatePostingOutputData outputData);

    void prepareFailView(String error);
}
