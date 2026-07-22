package com.scholarmatch.usecase.close_posting;

import com.scholarmatch.entity.Posting;
import com.scholarmatch.usecase.data_access_interface.ClosePostingDataAccessInterface;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.exception.DataAccessException;

public final class ClosePostingInteractor implements ClosePostingInputBoundary {
    private final ClosePostingDataAccessInterface dataAccessObject;
    private final ClosePostingOutputBoundary outputBoundary;

    public ClosePostingInteractor(
            final ClosePostingDataAccessInterface dataAccessObject,
            final ClosePostingOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final ClosePostingInputData inputData) {
        try {
            final Posting closed = this.dataAccessObject.closePosting(inputData.postingId());
            this.outputBoundary.prepareSuccessView(
                    new ClosePostingOutputData(PostingData.from(closed)));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
