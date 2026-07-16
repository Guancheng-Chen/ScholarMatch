package com.scholarmatch.usecase.login;

/**
 * Output data produced by a successful login.
 */
public final class LoginOutputData {

    private final String userId;
    private final String fullName;

    /**
     * Constructs login output data.
     *
     * @param userId   the authenticated user's ID (stored via
     *                 com.scholarmatch.usecase.data_access_interface.SessionWriterInterface)
     * @param fullName the user's display name
     */
    public LoginOutputData(final String userId, final String fullName) {
        this.userId = userId;
        this.fullName = fullName;
    }

    /**
     * @return the user ID
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @return the full display name
     */
    public String getFullName() {
        return this.fullName;
    }
}
