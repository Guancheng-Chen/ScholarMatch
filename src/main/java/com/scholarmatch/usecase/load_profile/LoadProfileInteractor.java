package com.scholarmatch.usecase.load_profile;

import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.exception.DataAccessException;

/**
 * Interactor implementing the load-current-profile use case.
 *
 * <p>Fetches the current user's full saved profile so the edit screen can be
 * pre-populated with their existing data instead of starting blank.
 */
public final class LoadProfileInteractor implements LoadProfileInputBoundary {

    private final LoadProfileDataAccessInterface profileDataAccessObject;
    private final LoadProfileOutputBoundary outputBoundary;

    /**
     * Constructs a LoadProfileInteractor.
     *
     * @param profileDataAccessObject server gateway for reading the profile
     * @param outputBoundary          the presenter that receives the result
     */
    public LoadProfileInteractor(
            final LoadProfileDataAccessInterface profileDataAccessObject,
            final LoadProfileOutputBoundary outputBoundary) {
        this.profileDataAccessObject = profileDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute() {
        try {
            this.outputBoundary.prepareSuccessView(
                    new LoadProfileOutputData(UserData.from(this.profileDataAccessObject.getProfile())));
        } catch (final DataAccessException e) {
            this.outputBoundary.prepareFailView(e.getMessage());
        }
    }
}
