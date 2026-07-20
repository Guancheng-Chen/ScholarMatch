package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.delete_account.DeleteAccountInputBoundary;

/**
 * Controller for the delete-account use case.
 */
public final class DeleteAccountController {

    private final DeleteAccountInputBoundary interactor;

    /**
     * Constructs a DeleteAccountController.
     *
     * @param interactor the delete-account use case
     */
    public DeleteAccountController(
            final DeleteAccountInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Sends a delete-account request to the use case.
     */
    public void deleteAccount() {
        this.interactor.execute();
    }
}
