package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.PostingsListViewModel;
import com.scholarmatch.usecase.load_postings.LoadPostingsOutputBoundary;
import com.scholarmatch.usecase.load_postings.LoadPostingsOutputData;

public final class LoadPostingsPresenter implements LoadPostingsOutputBoundary {
    private final PostingsListViewModel viewModel;

    public LoadPostingsPresenter(final PostingsListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final LoadPostingsOutputData outputData) {
        this.viewModel.setPostings(outputData.postings());
        this.viewModel.setApplicationsByPostingId(outputData.applicationsByPostingId());
        this.viewModel.setErrorMessage("");
    }

    @Override
    public void prepareFailView(final String error) {
        this.viewModel.setErrorMessage(error);
    }
}
