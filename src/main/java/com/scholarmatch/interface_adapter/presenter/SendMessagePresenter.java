package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.usecase.send_message.SendMessageOutputBoundary;
import com.scholarmatch.usecase.send_message.SendMessageOutputData;

/**
 * Presenter for the send-message use case.
 */
public final class SendMessagePresenter implements SendMessageOutputBoundary {

    private final ChatViewModel viewModel;

    /**
     * Constructs a SendMessagePresenter.
     *
     * @param viewModel the shared view model for the chat panel
     */
    public SendMessagePresenter(final ChatViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(final SendMessageOutputData outputData) {
        this.viewModel.getMessages().add(outputData.getMessage());
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        this.viewModel.setErrorMessage(errorMessage);
    }
}
