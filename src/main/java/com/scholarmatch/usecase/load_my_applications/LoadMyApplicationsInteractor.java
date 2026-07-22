package com.scholarmatch.usecase.load_my_applications;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.usecase.data_access_interface.LoadMyApplicationsDataAccessInterface;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.List;

public final class LoadMyApplicationsInteractor implements LoadMyApplicationsInputBoundary {

    private final LoadMyApplicationsDataAccessInterface dataAccessObject;
    private final LoadMyApplicationsOutputBoundary outputBoundary;

    public LoadMyApplicationsInteractor(
            final LoadMyApplicationsDataAccessInterface dataAccessObject,
            final LoadMyApplicationsOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute() {
        try {
            final List<PostingApplication> applications = this.dataAccessObject.getMyApplications();
            this.outputBoundary.prepareSuccessView(new LoadMyApplicationsOutputData(
                    PostingApplicationData.fromAll(applications)));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
