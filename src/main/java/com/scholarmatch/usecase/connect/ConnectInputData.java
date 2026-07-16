package com.scholarmatch.usecase.connect;

import com.scholarmatch.usecase.dto.UserData;

/**
 * Input data for a connect action.
 *
 * <p>Carries both the connected user's ID (sent to the server) and a snapshot of their
 * profile (used locally to populate the match notification if a mutual match is detected,
 * without an extra network round-trip).
 */
public final class ConnectInputData {

    private final String connectedUserId;
    private final UserData connectedUser;

    /**
     * Constructs connect input data.
     *
     * @param connectedUserId the ID of the user shown on the card
     * @param connectedUser   the profile snapshot of the user shown on the card
     */
    public ConnectInputData(final String connectedUserId, final UserData connectedUser) {
        this.connectedUserId = connectedUserId;
        this.connectedUser = connectedUser;
    }

    /**
     * Returns the connected user's ID.
     *
     * @return the connected user's ID
     */
    public String getConnectedUserId() {
        return this.connectedUserId;
    }

    /**
     * Returns the profile snapshot for the connected card.
     *
     * @return the connected user
     */
    public UserData getConnectedUser() {
        return this.connectedUser;
    }
}
