package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.PostingApplicationData;

/**
 * View model for the current user's posting applications.
 */
public final class MyApplicationsViewModel {
    private final ObservableListModel<PostingApplicationData> applications = new ObservableListModel<>();
    private final ObservableValue<String> errorMessage = new ObservableValue<>("");

    /**
     * Returns the observable list of applications.
     *
     * @return the applications list model
     */
    public ObservableListModel<PostingApplicationData> getApplications() {
        return this.applications;
    }

    /**
     * Returns the observable error message property.
     *
     * @return the error message property
     */
    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }

    /**
     * Updates the error message.
     *
     * @param message the new error message
     */
    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }

    /**
     * Replaces the current applications with a new list.
     *
     * @param newApplications the new applications
     */
    public void setApplications(final java.util.List<PostingApplicationData> newApplications) {
        this.applications.setAll(newApplications);
    }
}
