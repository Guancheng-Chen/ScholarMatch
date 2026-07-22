package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.usecase.accept_application.AcceptApplicationOutputBoundary;
import com.scholarmatch.usecase.accept_application.AcceptApplicationOutputData;

public final class AcceptApplicationPresenter implements AcceptApplicationOutputBoundary {
    private final MyPostingsViewModel viewModel;

    public AcceptApplicationPresenter(final MyPostingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final AcceptApplicationOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSuccessMessage("Application accepted.");
        this.viewModel.updateApplicationStatus(outputData.application());
        this.viewModel.requestRefresh();
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setErrorMessage(error);
    }
}
