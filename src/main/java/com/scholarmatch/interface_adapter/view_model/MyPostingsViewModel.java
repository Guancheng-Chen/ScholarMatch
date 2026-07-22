package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.dto.PostingApplicationData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MyPostingsViewModel implements PostingsListViewModel {
    private final ObservableListModel<PostingData> postings = new ObservableListModel<>();
    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final ObservableValue<String> successMessage = new ObservableValue<>("");
    private final ObservableValue<Integer> refreshRequest = new ObservableValue<>(0);
    private Map<String, List<PostingApplicationData>> applicationsByPostingId = Map.of();

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
            final Map<String, List<PostingApplicationData>> newApplications) {
        final Map<String, List<PostingApplicationData>> copy = new LinkedHashMap<>();
        for (final Map.Entry<String, List<PostingApplicationData>> entry : newApplications.entrySet()) {
            copy.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        this.applicationsByPostingId = Map.copyOf(copy);
        this.postings.setAll(List.copyOf(this.postings));
    }

    public List<PostingApplicationData> getApplicationsFor(final String postingId) {
        return this.applicationsByPostingId.getOrDefault(postingId, List.of());
    }

    public void addPosting(final PostingData posting) {
        final List<PostingData> updated = new ArrayList<>();
        updated.add(posting);
        updated.addAll(this.postings);
        this.postings.setAll(updated);
    }

    public void replacePosting(final PostingData posting) {
        this.postings.setAll(this.postings.stream()
                .map(existing -> existing.getPostingId().equals(posting.getPostingId())
                        ? posting : existing)
                .toList());
    }

    public void updateApplicationStatus(final PostingApplicationData updatedApplication) {
        final Map<String, List<PostingApplicationData>> updated = new LinkedHashMap<>();
        for (final Map.Entry<String, List<PostingApplicationData>> entry
                : this.applicationsByPostingId.entrySet()) {
            updated.put(entry.getKey(), entry.getValue().stream()
                    .map(application -> application.getApplicationId()
                            .equals(updatedApplication.getApplicationId())
                                    ? updatedApplication : application)
                    .toList());
        }
        setApplicationsByPostingId(updated);
    }
}
