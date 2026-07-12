package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Port for fetching user recommendations from the server.
 *
 * <p>Replaces the three-step pipeline (embed → vector search → fetch users)
 * that was previously done on the client. The server now handles all of that
 * and returns a ranked list directly.
 *
 * <p>Also carries #getProfile() — the recommend use case needs the current user's own
 * profile to check completeness before fetching recommendations. That method has the exact
 * same signature as LoadProfileDataAccessInterface#getProfile(), but is declared
 * here instead of shared with it: the two use cases happening to need an identical call isn't
 * a reason for recommend to depend on a port owned by (and named after) load-profile.
 */
public interface RecommendDataAccessInterface {

    /**
     * Returns a ranked list of users most similar to the current user.
     *
     * @return up to 20 recommended users ordered by embedding similarity
     * @throws ExternalServiceException if the request fails
     */
    List<User> getRecommendations();

    /**
     * Fetches the current user's full profile, used to check completeness before
     * fetching recommendations.
     *
     * @return the user's profile as a domain entity
     * @throws ResourceNotFoundException if no profile exists for the current user
     * @throws ExternalServiceException  if the request fails
     */
    User getProfile();
}
