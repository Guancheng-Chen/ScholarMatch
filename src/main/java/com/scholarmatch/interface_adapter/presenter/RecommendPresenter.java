package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.RecommendViewModel;
import com.scholarmatch.usecase.recommend.RecommendOutputBoundary;
import com.scholarmatch.usecase.recommend.RecommendOutputData;

/**
 * Presenter for the user-recommendation use case.
 */
public final class RecommendPresenter implements RecommendOutputBoundary {

    private final RecommendViewModel viewModel;

    /**
     * Constructs a RecommendPresenter.
     *
     * @param viewModel the view model this presenter updates
     */
    public RecommendPresenter(final RecommendViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final RecommendOutputData outputData) {
        this.viewModel.setErrorMessage("");
        this.viewModel.setCardStack(outputData.getRecommendations());
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setCardStack(java.util.List.of());
        this.viewModel.setErrorMessage(errorMessage);
    }
}
