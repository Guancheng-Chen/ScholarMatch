package com.scholarmatch.interface_adapter.logout;

import com.scholarmatch.interface_adapter.view_model.logout.LogoutViewModel;
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
