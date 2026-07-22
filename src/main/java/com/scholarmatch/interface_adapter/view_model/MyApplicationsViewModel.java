package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.PostingApplicationData;

public final class MyApplicationsViewModel {
    private final ObservableListModel<PostingApplicationData> applications = new ObservableListModel<>();
    private final ObservableValue<String> errorMessage = new ObservableValue<>("");

    public ObservableListModel<PostingApplicationData> getApplications() {
        return this.applications;
    }

    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }

    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }

    public void setApplications(final java.util.List<PostingApplicationData> newApplications) {
        this.applications.setAll(newApplications);
    }
}
