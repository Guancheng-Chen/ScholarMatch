package com.scholarmatch.usecase.load_message;


/**
 * Input data for the load-message (conversation history) use case.
 */
public final class LoadMessageInputData {


    private final String otherUserId;


    /**
     * Constructs load-message input data.
     *
     * @param otherUserId the ID of the other participant in the conversation
     */
    public LoadMessageInputData(final String otherUserId) {
        this.otherUserId = otherUserId;
    }


    /**
     * Returns the ID of the other participant in the conversation.
     *
     * @return the other user's ID
     */
    public String getOtherUserId() {
        return this.otherUserId;
    }
}
