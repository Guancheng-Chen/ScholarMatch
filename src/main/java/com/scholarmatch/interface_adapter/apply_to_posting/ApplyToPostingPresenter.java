package com.scholarmatch.interface_adapter.apply_to_posting;

import com.scholarmatch.interface_adapter.view_model.opportunities.OpportunitiesViewModel;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingOutputBoundary;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingOutputData;

public final class ApplyToPostingPresenter implements ApplyToPostingOutputBoundary {
    private final OpportunitiesViewModel viewModel;

    public ApplyToPostingPresenter(final OpportunitiesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final ApplyToPostingOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSuccessMessage("Application submitted.");
        this.viewModel.removePosting(outputData.application().getPostingId());
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setErrorMessage(error);
    }
}
