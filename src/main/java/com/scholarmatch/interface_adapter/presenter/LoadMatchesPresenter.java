package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.usecase.load_matches.LoadMatchesOutputBoundary;
import com.scholarmatch.usecase.load_matches.LoadMatchesOutputData;

/**
 * Presenter for the load-confirmed-matches use case.
 */
public final class LoadMatchesPresenter implements LoadMatchesOutputBoundary {

    private final LoadMatchesViewModel viewModel;

    /**
     * Constructs a LoadMatchesPresenter.
     *
     * @param viewModel the shared view model for the matched users panel
     */
    public LoadMatchesPresenter(final LoadMatchesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final LoadMatchesOutputData outputData) {
        this.viewModel.getMatchedUsers().setAll(outputData.getMatches());
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
    }
}
