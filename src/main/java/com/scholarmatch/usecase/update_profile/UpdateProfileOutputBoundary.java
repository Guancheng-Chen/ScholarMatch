package com.scholarmatch.usecase.update_profile;

/**
 * Output boundary for updating the current user's profile.
 */
public interface UpdateProfileOutputBoundary {

    /**
     * Presents the saved profile after a successful update.
     *
     * @param outputData the saved profile snapshot
     */
    void prepareSuccessView(UpdateProfileOutputData outputData);

    /**
     * Presents an update failure.
     *
     * @param errorMessage the reason the profile could not be updated
     */
    void prepareFailView(String errorMessage);
}
