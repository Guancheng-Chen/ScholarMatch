package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

/**
 * Port for updating the current user's profile via the server.
 *
 * <p>Kept separate from LoadProfileDataAccessInterface and
 * DeleteAccountDataAccessInterface so that only the update-profile use case
 * needs to depend on UpdateProfileInputData — a reader like recommend or
 * load-profile has no reason to be coupled to the update-profile use case's input type.
 */
public interface UpdateProfileDataAccessInterface {

    /**
     * Sends updated profile fields to the server.
     *
     * <p>The server handles re-embedding after fields change.
     *
     * @param data the new field values; null fields are left unchanged
     * @return the updated user profile returned by the server
     * @throws InvalidRequestException  if the update data fails server-side validation
     * @throws ExternalServiceException if the request fails
     */
    User updateProfile(UpdateProfileInputData data);
}
