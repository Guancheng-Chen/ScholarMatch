package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;
import com.scholarmatch.usecase.update_profile.UpdateProfileOutputBoundary;
import com.scholarmatch.usecase.update_profile.UpdateProfileOutputData;

/**
 * Presenter for the update-profile use case.
 */
public final class UpdateProfilePresenter implements UpdateProfileOutputBoundary {

    private final UpdateProfileViewModel viewModel;

    /**
     * Constructs an UpdateProfilePresenter.
     *
     * @param viewModel the view model this presenter updates
     */
    public UpdateProfilePresenter(final UpdateProfileViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final UpdateProfileOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSaveSuccessMessage("Profile saved successfully.");
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
    }
}
