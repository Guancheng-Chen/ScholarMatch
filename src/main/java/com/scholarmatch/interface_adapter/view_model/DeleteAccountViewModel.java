package com.scholarmatch.interface_adapter.view_model;


import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;


/**
 * Observable ViewModel for the delete-account action.
 *
 * <p>Only carries a failure signal — success is signaled via the shared
 * LogoutViewModel#loggedOutProperty(), since a successful deletion has exactly the
 * same effect on the shell as a normal logout (return to the logged-out shell).
 */
public final class DeleteAccountViewModel {


    private final ObservableValue<String> errorMessage = new ObservableValue<>("");


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
