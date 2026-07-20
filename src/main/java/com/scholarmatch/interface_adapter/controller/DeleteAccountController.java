package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.delete_account.DeleteAccountInputBoundary;

/**
 * Controller that forwards delete-account requests to the delete-account use case.
 */
public final class DeleteAccountController {

    private final DeleteAccountInputBoundary interactor;

    /**
     * Constructs a DeleteAccountController.
     *
     * @param interactor the delete-account use case
     */
    public DeleteAccountController(final DeleteAccountInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the user confirms they want to permanently delete their account.
     */
    public void deleteAccount() {
        this.interactor.execute();
    }
}
