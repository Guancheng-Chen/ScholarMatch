package com.scholarmatch.interface_adapter.view_model;


import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;


/**
 * Observable ViewModel for the registration screen.
 *
 * <p>Written by RegisterPresenter and observed by RegisterView.
 */
public final class RegisterViewModel {


    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final ObservableValue<String> successMessage = new ObservableValue<>("");
    private final ObservableValue<Boolean> registrationSucceeded = new ObservableValue<>(false);
    private final ObservableValue<String> verificationMessage = new ObservableValue<>("");
    private final ObservableValue<String> verificationError = new ObservableValue<>("");


    /**
     * Returns the error message property. @return the error message property
     */
    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }


    /**
     * Returns the success message property. @return the success message property
     */
    public ObservableValue<String> successMessageProperty() {
        return this.successMessage;
    }


    /**
     * Returns the registration-succeeded flag property. @return the boolean property
     */
    public ObservableValue<Boolean> registrationSucceededProperty() {
        return this.registrationSucceeded;
    }


    /**
     * Sets the error message. @param message the error text
     */
    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }


    /**
     * Sets the success message. @param message the success text
     */
    public void setSuccessMessage(final String message) {
        this.successMessage.set(message);
    }


    /**
     * Marks registration as succeeded or failed. @param succeeded the flag value
     */
    public void setRegistrationSucceeded(final boolean succeeded) {
        this.registrationSucceeded.set(succeeded);
    }

    public ObservableValue<String> verificationMessageProperty() {
        return this.verificationMessage;
    }

    public ObservableValue<String> verificationErrorProperty() {
        return this.verificationError;
    }

    public void setVerificationMessage(final String message) {
        this.verificationMessage.set(message);
    }

    public void setVerificationError(final String message) {
        this.verificationError.set(message);
    }
}

