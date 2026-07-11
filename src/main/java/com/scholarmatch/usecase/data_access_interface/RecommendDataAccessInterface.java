package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.exception.ExternalServiceException;

import java.util.List;

/**
 * Port for fetching user recommendations from the server.
 *
 * <p>Replaces the three-step pipeline (embed → vector search → fetch users)
 * that was previously done on the client. The server now handles all of that
 * and returns a ranked list directly.
 */
public interface RecommendDataAccessInterface {

    /**
     * Returns a ranked list of users most similar to the current user.
     *
     * @return up to 20 recommended users ordered by embedding similarity
     * @throws ExternalServiceException if the request fails
     */
    List<User> getRecommendations();
}
