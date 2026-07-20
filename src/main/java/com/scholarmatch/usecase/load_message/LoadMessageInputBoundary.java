package com.scholarmatch.usecase.load_message;


/**
 * Input boundary for the load-message (conversation history) use case.
 */
public interface LoadMessageInputBoundary {


    /**
     * Loads the conversation between the current user and another scholar.
     *
     * @param inputData the other participant in the conversation
     */
    void execute(LoadMessageInputData inputData);
}
