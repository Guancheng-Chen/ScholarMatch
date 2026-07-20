package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.usecase.exception.ExternalServiceException;

/**
 * Port for recording a connect decision on the server and detecting a mutual match.
 *
 * <p>Kept separate from DislikeDataAccessInterface and
 * LoadMatchesDataAccessInterface — the connect use case has no reason to
 * depend on dislike or load-matches capabilities just because a single server-facing class
 * happens to implement all three ports.
 */
public interface ConnectDataAccessInterface {

    /**
     * Records that the current user connected right on the given user.
     *
     * @param connectedUserId the ID of the user who was connected on
     * @return true if this connect created a mutual match
     * @throws ExternalServiceException if the request fails
     */
    boolean connect(String connectedUserId);
}
