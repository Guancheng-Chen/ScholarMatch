package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.usecase.decline_application.DeclineApplicationOutputBoundary;
import com.scholarmatch.usecase.decline_application.DeclineApplicationOutputData;

public final class DeclineApplicationPresenter implements DeclineApplicationOutputBoundary {
    private final MyPostingsViewModel viewModel;

    public DeclineApplicationPresenter(final MyPostingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final DeclineApplicationOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSuccessMessage("Application declined.");
        this.viewModel.updateApplicationStatus(outputData.application());
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setErrorMessage(error);
    }
}
