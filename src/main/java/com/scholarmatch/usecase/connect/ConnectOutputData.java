package com.scholarmatch.usecase.connect;

import com.scholarmatch.usecase.dto.UserData;

/**
 * Output data produced when a connect action creates a mutual match.
 */
public final class ConnectOutputData {

    private final UserData matchedUser;

    /**
     * Constructs connect output data for a mutual match.
     *
     * @param matchedUser the user who mutually matched with the current user
     */
    public ConnectOutputData(final UserData matchedUser) {
        this.matchedUser = matchedUser;
    }

    /**
     * @return the newly matched user
     */
    public UserData getMatchedUser() {
        return this.matchedUser;
    }
}
