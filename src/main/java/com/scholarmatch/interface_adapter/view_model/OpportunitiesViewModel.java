package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.PostingData;

import com.scholarmatch.usecase.dto.PostingApplicationData;

import java.util.List;
import java.util.Map;

public final class OpportunitiesViewModel implements PostingsListViewModel {
    private final ObservableListModel<PostingData> postings = new ObservableListModel<>();
    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final ObservableValue<String> successMessage = new ObservableValue<>("");
    private final ObservableValue<Integer> refreshRequest = new ObservableValue<>(0);

    public ObservableListModel<PostingData> getPostings() {
        return this.postings;
    }

    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }

    public ObservableValue<String> successMessageProperty() {
        return this.successMessage;
    }

    public ObservableValue<Integer> refreshRequestProperty() {
        return this.refreshRequest;
    }

    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }

    public void setSuccessMessage(final String message) {
        this.successMessage.set(message);
    }

    public void requestRefresh() {
        this.refreshRequest.set(this.refreshRequest.get() + 1);
    }

    @Override
    public void setPostings(final List<PostingData> newPostings) {
        this.postings.setAll(newPostings);
    }

    @Override
    public void setApplicationsByPostingId(
            final Map<String, List<PostingApplicationData>> applicationsByPostingId) {
    }

    public void removePosting(final String postingId) {
        this.postings.setAll(this.postings.stream()
                .filter(posting -> !posting.getPostingId().equals(postingId)).toList());
    }
}
