package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.usecase.close_posting.ClosePostingOutputBoundary;
import com.scholarmatch.usecase.close_posting.ClosePostingOutputData;

public final class ClosePostingPresenter implements ClosePostingOutputBoundary {
    private final MyPostingsViewModel viewModel;

    public ClosePostingPresenter(final MyPostingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final ClosePostingOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSuccessMessage("Posting closed.");
        this.viewModel.replacePosting(outputData.posting());
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setErrorMessage(error);
    }
}
