package com.scholarmatch.usecase.connect;

/**
 * Output boundary (presenter interface) for the connect use case.
 */
public interface ConnectOutputBoundary {

    /**
     * Called when a connect action creates a mutual match.
     *
     * @param outputData contains the newly matched user
     */
    void prepareMatchFound(ConnectOutputData outputData);

    /**
     * Called when a connect action is recorded but no mutual match exists yet.
     */
    void prepareNoMatch();
}
