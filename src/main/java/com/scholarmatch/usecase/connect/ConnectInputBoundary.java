package com.scholarmatch.usecase.connect;

/**
 * Input boundary for the connect use case.
 */
public interface ConnectInputBoundary {

    /**
     * Records a connect action and checks for a mutual match.
     *
     * @param inputData the current user and the user they are connecting with
     */
    void execute(ConnectInputData inputData);
}
