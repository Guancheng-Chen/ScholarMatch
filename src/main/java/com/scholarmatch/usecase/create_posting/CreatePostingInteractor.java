package com.scholarmatch.usecase.create_posting;

import com.scholarmatch.entity.Posting;
import com.scholarmatch.usecase.data_access_interface.CreatePostingDataAccessInterface;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.exception.DataAccessException;


public final class CreatePostingInteractor implements CreatePostingInputBoundary {

    private final CreatePostingDataAccessInterface dataAccessObject;
    private final CreatePostingOutputBoundary outputBoundary;

    public CreatePostingInteractor(
            final CreatePostingDataAccessInterface dataAccessObject,
            final CreatePostingOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final CreatePostingInputData inputData) {
        if (inputData.capacity() != null && inputData.capacity() <= 0) {
            this.outputBoundary.prepareFailView("Team capacity must be greater than zero.");
            return;
        }
        try {
            final Posting saved = this.dataAccessObject.createPosting(
                    inputData.title(), inputData.description(), inputData.researchField(),
                    inputData.collaborationType(), inputData.capacity());
            this.outputBoundary.prepareSuccessView(new CreatePostingOutputData(
                    PostingData.from(saved)));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
