package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.usecase.register.RegisterOutputBoundary;
import com.scholarmatch.usecase.register.RegisterOutputData;

/**
 * Presenter for the register use case.
 *
 * <p>Implements RegisterOutputBoundary and writes results
 * into RegisterViewModel for the view to observe.
 */
public final class RegisterPresenter implements RegisterOutputBoundary {

    private final RegisterViewModel viewModel;

    /**
     * Constructs a RegisterPresenter.
     *
     * @param viewModel the view model this presenter updates
     */
    public RegisterPresenter(final RegisterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final RegisterOutputData outputData) {
        this.viewModel.setSuccessMessage("Welcome, " + outputData.getName() + "!");
        this.viewModel.setRegistrationSucceeded(true);
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
        this.viewModel.setRegistrationSucceeded(false);
    }
}
