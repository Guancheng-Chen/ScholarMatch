package com.scholarmatch.usecase.update_profile;

/**
 * Input boundary for updating the current user's profile.
 */
public interface UpdateProfileInputBoundary {

    /**
     * Updates the current user's profile with edited values.
     *
     * @param inputData the edited profile values
     */
    void execute(UpdateProfileInputData inputData);
}
