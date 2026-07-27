package com.scholarmatch.usecase.load_public_profile;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.LoadPublicProfileDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

public final class LoadPublicProfileInteractor
        implements LoadPublicProfileInputBoundary {

    private static final String MISSING_OWNER_MESSAGE =
            "The posting owner is unavailable.";

    private final LoadPublicProfileDataAccessInterface dataAccessObject;
    private final LoadPublicProfileOutputBoundary outputBoundary;

    public LoadPublicProfileInteractor(
            final LoadPublicProfileDataAccessInterface dataAccessObject,
            final LoadPublicProfileOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final LoadPublicProfileInputData inputData) {
        if (inputData == null || inputData.userId() == null
                || inputData.userId().isBlank()) {
            this.outputBoundary.prepareFailView(MISSING_OWNER_MESSAGE);
            return;
        }
        try {
            final User user = this.dataAccessObject.getPublicProfile(
                    inputData.userId());
            this.outputBoundary.prepareSuccessView(
                    LoadPublicProfileOutputData.from(user));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
