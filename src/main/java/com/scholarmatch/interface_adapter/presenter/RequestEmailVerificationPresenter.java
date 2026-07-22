package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationOutputBoundary;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationOutputData;

/**
 * Presenter for registration verification-code delivery.
 */
public final class RequestEmailVerificationPresenter
        implements RequestEmailVerificationOutputBoundary {

    private final RegisterViewModel viewModel;

    /**
     * @param viewModel registration screen state
     */
    public RequestEmailVerificationPresenter(final RegisterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final RequestEmailVerificationOutputData outputData) {
        this.viewModel.setVerificationError("");
        this.viewModel.setVerificationMessage(
                "Verification code sent to " + outputData.email() + ".");
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setVerificationMessage("");
        this.viewModel.setVerificationError(error);
    }
}
