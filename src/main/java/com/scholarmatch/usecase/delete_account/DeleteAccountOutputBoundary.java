package com.scholarmatch.usecase.delete_account;

/**
 * Output boundary (presenter interface) for the delete-account use case.
 */
public interface DeleteAccountOutputBoundary {

    /**
     * Called once the account has been deleted and the session cleared.
     */
    void prepareSuccessView();

    /**
     * Called when the account could not be deleted.
     *
     * @param errorMessage the reason for failure
     */
    void prepareFailView(String errorMessage);
}
