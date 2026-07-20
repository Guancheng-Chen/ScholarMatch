package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;
import com.scholarmatch.usecase.load_profile.LoadProfileOutputBoundary;
import com.scholarmatch.usecase.load_profile.LoadProfileOutputData;

/**
 * Presenter for the load-current-profile use case.
 */
public final class LoadProfilePresenter implements LoadProfileOutputBoundary {

    private final UpdateProfileViewModel viewModel;

    /**
     * Constructs a LoadProfilePresenter.
     *
     * @param viewModel the view model this presenter updates
     */
    public LoadProfilePresenter(final UpdateProfileViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final LoadProfileOutputData outputData) {
        this.viewModel.setCurrentUser(outputData.getUser());
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
    }
}
