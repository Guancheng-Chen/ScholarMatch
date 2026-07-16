package com.scholarmatch.usecase.load_profile;

/**
 * Output boundary for loading the current user's profile.
 */
public interface LoadProfileOutputBoundary {

    /**
     * Presents the loaded profile.
     *
     * @param outputData the current user's profile snapshot
     */
    void prepareSuccessView(LoadProfileOutputData outputData);

    /**
     * Presents a profile loading failure.
     *
     * @param errorMessage the reason the profile could not be loaded
     */
    void prepareFailView(String errorMessage);
}
