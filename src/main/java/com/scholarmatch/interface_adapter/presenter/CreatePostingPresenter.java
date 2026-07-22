package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.usecase.create_posting.CreatePostingOutputBoundary;
import com.scholarmatch.usecase.create_posting.CreatePostingOutputData;

public final class CreatePostingPresenter implements CreatePostingOutputBoundary {
    private final MyPostingsViewModel viewModel;

    public CreatePostingPresenter(final MyPostingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final CreatePostingOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setSuccessMessage("Posting created.");
        this.viewModel.addPosting(outputData.posting());
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setErrorMessage(error);
    }
}
