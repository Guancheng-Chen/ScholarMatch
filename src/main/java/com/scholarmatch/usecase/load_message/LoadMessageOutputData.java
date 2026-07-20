package com.scholarmatch.usecase.load_message;


import com.scholarmatch.usecase.dto.MessageData;


import java.util.ArrayList;
import java.util.List;


/**
 * Output data for the load-message (conversation history) use case.
 */
public final class LoadMessageOutputData {


    private final List<MessageData> messages;


    /**
     * Constructs load-message output.
     *
     * @param messages the conversation history, oldest to newest
     */
    public LoadMessageOutputData(final List<MessageData> messages) {
        this.messages = new ArrayList<>(messages);
    }


    /**
     * Returns the conversation history.
     *
     * @return the messages, oldest to newest
     */
    public List<MessageData> getMessages() {
        return new ArrayList<>(this.messages);
    }
}
