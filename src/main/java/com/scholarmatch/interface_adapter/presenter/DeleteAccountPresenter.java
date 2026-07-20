package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.DeleteAccountViewModel;
import com.scholarmatch.interface_adapter.view_model.LogoutViewModel;
import com.scholarmatch.usecase.delete_account.DeleteAccountOutputBoundary;

/**
 * Presenter for the delete-account use case.
 */
public final class DeleteAccountPresenter implements DeleteAccountOutputBoundary {

    private final LogoutViewModel logoutViewModel;
    private final DeleteAccountViewModel deleteAccountViewModel;

    /**
     * Constructs a DeleteAccountPresenter.
     *
     * @param logoutViewModel        the shared view model MainView watches to swap back
     *                                to the logged-out shell — reused here since a
     *                                successful deletion has the same effect as logout
     * @param deleteAccountViewModel carries the failure message, if any
     */
    public DeleteAccountPresenter(
            final LogoutViewModel logoutViewModel,
            final DeleteAccountViewModel deleteAccountViewModel) {
        this.logoutViewModel = logoutViewModel;
        this.deleteAccountViewModel = deleteAccountViewModel;
    }

    @Override
    public void prepareSuccessView() {
        this.logoutViewModel.setLoggedOut();
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.deleteAccountViewModel.setErrorMessage(errorMessage);
    }
}
