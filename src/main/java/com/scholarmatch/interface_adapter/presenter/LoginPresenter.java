package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import com.scholarmatch.usecase.login.LoginOutputBoundary;
import com.scholarmatch.usecase.login.LoginOutputData;

/**
 * Presenter for the login use case.
 */
public final class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel viewModel;

    /**
     * Constructs a LoginPresenter.
     *
     * @param viewModel the view model this presenter updates
     */
    public LoginPresenter(final LoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final LoginOutputData outputData) {
        this.viewModel.setLoggedInUserId(outputData.getUserId());
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
    }
}
