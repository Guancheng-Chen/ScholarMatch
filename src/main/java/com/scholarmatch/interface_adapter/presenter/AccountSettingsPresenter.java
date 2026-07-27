package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.AccountSettingsViewModel;
import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;
import com.scholarmatch.usecase.change_email.ChangeEmailOutputBoundary;
import com.scholarmatch.usecase.change_email.ChangeEmailOutputData;
import com.scholarmatch.usecase.change_password.ChangePasswordOutputBoundary;
import com.scholarmatch.usecase.change_password.ChangePasswordOutputData;
import com.scholarmatch.usecase.request_email_change_verification.RequestEmailChangeVerificationOutputBoundary;
import com.scholarmatch.usecase.request_email_change_verification.RequestEmailChangeVerificationOutputData;

/**
 * Presenter for account email and password settings.
 */
public final class AccountSettingsPresenter
        implements RequestEmailChangeVerificationOutputBoundary,
        ChangeEmailOutputBoundary,
        ChangePasswordOutputBoundary {

    private final AccountSettingsViewModel viewModel;
    private final UpdateProfileViewModel profileViewModel;

    public AccountSettingsPresenter(
            final AccountSettingsViewModel viewModel,
            final UpdateProfileViewModel profileViewModel) {
        this.viewModel = viewModel;
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void prepareSuccessView(
            final RequestEmailChangeVerificationOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSuccessMessage(
                "Verification code sent to " + outputData.email() + ".");
    }

    @Override
    public void prepareSuccessView(final ChangeEmailOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setCurrentEmail(outputData.user().getEmail());
        this.profileViewModel.setCurrentUser(outputData.user());
        this.viewModel.setSuccessMessage("Email changed successfully.");
    }

    @Override
    public void prepareSuccessView(final ChangePasswordOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSuccessMessage("Password changed successfully.");
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
    }
}
