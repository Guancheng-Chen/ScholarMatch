package com.scholarmatch.usecase.update_profile;

import com.scholarmatch.usecase.dto.UserData;

/**
 * Output data returned after a profile update succeeds.
 */
public final class UpdateProfileOutputData {

    private final UserData profile;

    /**
     * Constructs profile update output.
     *
     * @param profile the saved profile snapshot
     */
    public UpdateProfileOutputData(final UserData profile) {
        this.profile = profile;
    }

    /** @return the saved profile snapshot */
    public UserData getProfile() {
        return this.profile;
    }
}
