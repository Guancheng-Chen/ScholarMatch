package com.scholarmatch.usecase.delete_account;

/**
 * Input boundary for the delete-account use case.
 */
public interface DeleteAccountInputBoundary {

    /**
     * Permanently deletes the current user's account and logs them out.
     */
    void execute();
}
