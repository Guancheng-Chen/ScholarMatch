package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.usecase.load_message.LoadMessageOutputBoundary;
import com.scholarmatch.usecase.load_message.LoadMessageOutputData;

/**
 * Presenter for the load-message use case.
 */
public final class LoadMessagePresenter implements LoadMessageOutputBoundary {

    private final ChatViewModel viewModel;

    /**
     * Constructs a LoadMessagePresenter.
     *
     * @param viewModel the shared view model for the chat panel
     */
    public LoadMessagePresenter(final ChatViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final LoadMessageOutputData outputData) {
        this.viewModel.getMessages().setAll(outputData.getMessages());
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
    }
}
