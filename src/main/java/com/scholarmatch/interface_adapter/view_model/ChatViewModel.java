package com.scholarmatch.interface_adapter.view_model;


import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.MessageData;


/**
 * Observable ViewModel for the chat panel.
 *
 * <p>Holds the message history for whichever conversation is currently open. The view
 * is responsible for tracking which matched user is selected and reloading this list
 * (via LoadMessageController) when the selection changes.
 */
public final class ChatViewModel {


    private final ObservableListModel<MessageData> messages = new ObservableListModel<>();
    private final ObservableValue<String> errorMessage = new ObservableValue<>("");


    /**
     * Returns the live list of messages in the currently open conversation.
     *
     * @return the observable list bound to the chat view
     */
    public ObservableListModel<MessageData> getMessages() {
        return this.messages;
    }


    /**
     * Returns the error message property.
     *
     * @return the error message string property
     */
    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }


    /**
     * Sets the error message.
     *
     * @param message the error text to display
     */
    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }
}
