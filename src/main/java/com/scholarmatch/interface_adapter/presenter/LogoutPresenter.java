package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.LogoutViewModel;
import com.scholarmatch.usecase.logout.LogoutOutputBoundary;
import com.scholarmatch.usecase.logout.LogoutOutputData;

/**
 * Presenter for the logout use case.
 */
public final class LogoutPresenter implements LogoutOutputBoundary {

    private final LogoutViewModel viewModel;

    /**
     * Constructs a LogoutPresenter.
     *
     * @param viewModel the view model this presenter updates
     */
    public LogoutPresenter(final LogoutViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final LogoutOutputData outputData) {
        this.viewModel.setLoggedOut();
    }
}
