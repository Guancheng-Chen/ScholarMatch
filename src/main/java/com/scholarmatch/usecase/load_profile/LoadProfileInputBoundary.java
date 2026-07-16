package com.scholarmatch.usecase.load_profile;

/**
 * Input boundary for loading the current user's profile.
 */
public interface LoadProfileInputBoundary {

    /**
     * Loads the currently authenticated user's profile.
     */
    void execute();
}
