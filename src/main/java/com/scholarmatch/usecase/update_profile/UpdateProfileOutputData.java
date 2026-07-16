package com.scholarmatch.usecase.update_profile;

/**
 * Output data produced by a successful profile update.
 */
public final class UpdateProfileOutputData {

    private final String userId;

    /**
     * Constructs update-profile output data.
     *
     * @param userId the ID of the updated user
     */
    public UpdateProfileOutputData(final String userId) {
        this.userId = userId;
    }

    /**
     * @return the user ID
     */
    public String getUserId() {
        return this.userId;
    }
}
