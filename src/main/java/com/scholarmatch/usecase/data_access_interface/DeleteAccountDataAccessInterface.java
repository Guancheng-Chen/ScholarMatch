package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.usecase.exception.ExternalServiceException;

/**
 * Port for permanently deleting the current user's account via the server.
 *
 * <p>Kept separate from LoadProfileDataAccessInterface and
 * UpdateProfileDataAccessInterface so that the delete-account use case doesn't
 * pull in read or update concerns it doesn't need, and so those two ports don't carry an
 * irreversible operation neither of them uses.
 */
public interface DeleteAccountDataAccessInterface {

    /**
     * Permanently deletes the current user's account and all associated data on the server
     * (profile, education, publications, matches, and messages). Irreversible — using the
     * app again afterward requires registering a new account.
     *
     * @throws ExternalServiceException if the request fails
     */
    void deleteAccount();
}
