package com.scholarmatch.usecase.register;

/**
 * Output data produced by the register use case upon success.
 *
 * <p>Only ever constructed on the success path — RegisterOutputBoundary#prepareFailView(String)
 * carries failures instead, so there is no separate failure flag here.
 */
public final class RegisterOutputData {
    private final String userId;
    private final String name;

    /**
     * Constructs successful registration output.
     *
     * @param userId the newly assigned user ID
     * @param name   the registered name
     */
    public RegisterOutputData(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    /**
     * Returns the new user ID. @return the user ID
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Returns the registered name. @return the name
     */
    public String getName() {
        return this.name;
    }
}
