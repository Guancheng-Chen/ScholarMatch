package com.scholarmatch.interface_adapter.view_model.account_settings;

import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;

/**
 * Observable state for the Account Settings screen.
 */
public final class AccountSettingsViewModel {

    private final ObservableValue<String> successMessage =
            new ObservableValue<>("");
    private final ObservableValue<String> errorMessage =
            new ObservableValue<>("");
    private final ObservableValue<String> currentEmail =
            new ObservableValue<>("");

    public ObservableValue<String> successMessageProperty() {
        return this.successMessage;
    }

    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }

    public ObservableValue<String> currentEmailProperty() {
        return this.currentEmail;
    }

    public void setSuccessMessage(final String message) {
        this.successMessage.set(message);
    }

    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }

    public void setCurrentEmail(final String email) {
        this.currentEmail.set(email);
    }
}
