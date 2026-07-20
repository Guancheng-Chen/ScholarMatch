package com.scholarmatch.usecase.delete_account;

import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SessionClearerInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

/**
 * Interactor implementing the delete-account use case.
 *
 * <p>Deletes the account on the server first; only clears the local session once the
 * server confirms the deletion, so a failed request leaves the user still logged in
 * rather than stranding them in a logged-out shell for an account that still exists.
 */
public final class DeleteAccountInteractor implements DeleteAccountInputBoundary {

    private final DeleteAccountDataAccessInterface profileDataAccessObject;
    private final SessionClearerInterface sessionManager;
    private final DeleteAccountOutputBoundary outputBoundary;

    /**
     * Constructs a DeleteAccountInteractor.
     *
     * @param profileDataAccessObject server gateway for deleting the account
     * @param sessionManager          cleared once the account is deleted
     * @param outputBoundary          the presenter that receives the result
     */
    public DeleteAccountInteractor(
        final DeleteAccountDataAccessInterface profileDataAccessObject,
        final SessionClearerInterface sessionManager,
        final DeleteAccountOutputBoundary outputBoundary) {
        this.profileDataAccessObject = profileDataAccessObject;
        this.sessionManager = sessionManager;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute() {
        try {
            this.profileDataAccessObject.deleteAccount();
            this.sessionManager.clearSession();
            this.outputBoundary.prepareSuccessView();
        } catch (final DataAccessException e) {
            this.outputBoundary.prepareFailView(e.getMessage());
        }
    }
}
