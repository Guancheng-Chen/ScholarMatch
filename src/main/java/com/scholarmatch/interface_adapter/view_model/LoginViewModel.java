package com.scholarmatch.interface_adapter.view_model;


import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;


/**
 * Observable ViewModel for the login screen.
 */
public final class LoginViewModel {


    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final ObservableValue<String> loggedInUserId = new ObservableValue<>("");


    /**
     * @return the error message property
     */
    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }


    /**
     * @param message the error text to display
     */
    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }


    /**
     * @return the logged-in user ID property (non-empty on success)
     */
    public ObservableValue<String> loggedInUserIdProperty() {
        return this.loggedInUserId;
    }


    /**
     * @param userId the authenticated user's ID
     */
    public void setLoggedInUserId(final String userId) {
        this.loggedInUserId.set(userId);
    }
}

