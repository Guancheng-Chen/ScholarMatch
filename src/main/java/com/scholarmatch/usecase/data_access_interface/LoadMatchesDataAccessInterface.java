package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.exception.ExternalServiceException;

import java.util.List;

/**
 * Port for fetching the current user's confirmed mutual matches from the server.
 *
 * <p>Kept separate from ConnectDataAccessInterface and
 * DislikeDataAccessInterface — the load-matches use case only reads matches,
 * it has no reason to depend on the connect or dislike write operations just because a
 * single server-facing class happens to implement all three ports.
 */
public interface LoadMatchesDataAccessInterface {

    /**
     * Returns every user the current user has already mutually matched with —
     * including matches confirmed in a previous session, not just ones detected
     * live by ConnectDataAccessInterface#connect(String) during this run.
     *
     * @return the current user's confirmed mutual matches
     * @throws ExternalServiceException if the request fails
     */
    List<User> getMatches();
}
