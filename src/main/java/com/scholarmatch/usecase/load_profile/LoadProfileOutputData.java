package com.scholarmatch.usecase.load_profile;

import com.scholarmatch.usecase.dto.UserData;

/**
 * Output data containing the current user's profile.
 */
public final class LoadProfileOutputData {

    private final UserData profile;

    /**
     * Constructs load-profile output.
     *
     * @param profile the current user's profile snapshot
     */
    public LoadProfileOutputData(final UserData profile) {
        this.profile = profile;
    }

    /** @return the current user's profile snapshot */
    public UserData getProfile() {
        return this.profile;
    }
}
