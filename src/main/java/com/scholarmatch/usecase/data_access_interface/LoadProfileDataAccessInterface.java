package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

/**
 * Port for reading the current user's profile via the server.
 *
 * <p>Deliberately separate from UpdateProfileDataAccessInterface and
 * DeleteAccountDataAccessInterface — a read-only consumer like the recommend
 * use case (which only needs #getProfile() to check profile completeness) has no reason to
 * depend on the update-profile use case's input type, or on account deletion, just because
 * a single server-facing class happens to implement all three ports.
 */
public interface LoadProfileDataAccessInterface {

    /**
     * Fetches the current user's full profile.
     *
     * @return the user's profile as a domain entity
     * @throws ResourceNotFoundException if no profile exists for the current user
     * @throws ExternalServiceException  if the request fails
     */
    User getProfile();
}
