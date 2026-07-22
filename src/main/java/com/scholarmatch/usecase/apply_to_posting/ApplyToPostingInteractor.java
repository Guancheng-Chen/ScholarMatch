package com.scholarmatch.usecase.apply_to_posting;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.usecase.data_access_interface.ApplyToPostingDataAccessInterface;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.exception.DataAccessException;


public final class ApplyToPostingInteractor implements ApplyToPostingInputBoundary {

    private final ApplyToPostingDataAccessInterface dataAccessObject;
    private final ApplyToPostingOutputBoundary outputBoundary;

    public ApplyToPostingInteractor(
            final ApplyToPostingDataAccessInterface dataAccessObject,
            final ApplyToPostingOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final ApplyToPostingInputData inputData) {
        try {
            final PostingApplication saved = this.dataAccessObject
                    .applyToPosting(inputData.postingId(), inputData.message());
            this.outputBoundary.prepareSuccessView(new ApplyToPostingOutputData(
                    PostingApplicationData.from(saved)));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
