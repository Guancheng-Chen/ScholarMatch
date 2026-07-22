package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.MyApplicationsViewModel;
import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsOutputBoundary;
import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsOutputData;

/**
 * Presenter for loading the current user's applications.
 */
public final class LoadMyApplicationsPresenter implements LoadMyApplicationsOutputBoundary {

    private final MyApplicationsViewModel viewModel;

    /**
     * Constructs a presenter for the My Applications view.
     *
     * @param viewModel the view model updated by this presenter
     */
    public LoadMyApplicationsPresenter(final MyApplicationsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final LoadMyApplicationsOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setApplications(outputData.applications());
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setErrorMessage(error);
    }
}
