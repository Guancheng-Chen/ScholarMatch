package com.scholarmatch.usecase.accept_application;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.usecase.data_access_interface.AcceptApplicationDataAccessInterface;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.exception.DataAccessException;

public final class AcceptApplicationInteractor implements AcceptApplicationInputBoundary {

    private final AcceptApplicationDataAccessInterface dataAccessObject;
    private final AcceptApplicationOutputBoundary outputBoundary;

    public AcceptApplicationInteractor(
            final AcceptApplicationDataAccessInterface dataAccessObject,
            final AcceptApplicationOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final AcceptApplicationInputData inputData) {
        try {
            final PostingApplication saved = this.dataAccessObject
                    .acceptApplication(inputData.applicationId());
            this.outputBoundary.prepareSuccessView(new AcceptApplicationOutputData(
                    PostingApplicationData.from(saved)));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
