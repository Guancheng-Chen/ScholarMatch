package com.scholarmatch.usecase.decline_application;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.usecase.data_access_interface.DeclineApplicationDataAccessInterface;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.exception.DataAccessException;

public final class DeclineApplicationInteractor implements DeclineApplicationInputBoundary {

    private final DeclineApplicationDataAccessInterface dataAccessObject;
    private final DeclineApplicationOutputBoundary outputBoundary;

    public DeclineApplicationInteractor(
            final DeclineApplicationDataAccessInterface dataAccessObject,
            final DeclineApplicationOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final DeclineApplicationInputData inputData) {
        try {
            final PostingApplication saved = this.dataAccessObject
                    .declineApplication(inputData.applicationId());
            this.outputBoundary.prepareSuccessView(new DeclineApplicationOutputData(
                    PostingApplicationData.from(saved)));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
