package com.scholarmatch.usecase.load_message;


import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.dto.MessageData;
import com.scholarmatch.usecase.exception.DataAccessException;


/**
 * Interactor implementing the load-message use case.
 *
 * <p>Fetches the full conversation between the current user and another scholar, so the
 * chat view can be populated (or refreshed) without depending on messages sent live during
 * the current run.
 */
public final class LoadMessageInteractor implements LoadMessageInputBoundary {


    private final LoadMessageDataAccessInterface messageDataAccessObject;
    private final LoadMessageOutputBoundary outputBoundary;


    /**
     * Constructs a LoadMessageInteractor.
     *
     * @param messageDataAccessObject server gateway for fetching conversation history
     * @param outputBoundary          the presenter that receives the result
     */
    public LoadMessageInteractor(
            final LoadMessageDataAccessInterface messageDataAccessObject,
            final LoadMessageOutputBoundary outputBoundary) {
        this.messageDataAccessObject = messageDataAccessObject;
        this.outputBoundary = outputBoundary;
    }


    @Override
    public void execute(final LoadMessageInputData inputData) {
        try {
            this.outputBoundary.prepareSuccessView(new LoadMessageOutputData(
                    MessageData.fromAll(this.messageDataAccessObject.getConversation(inputData.getOtherUserId()))));
        } catch (final DataAccessException e) {
            this.outputBoundary.prepareFailView(e.getMessage());
        }
    }
}
