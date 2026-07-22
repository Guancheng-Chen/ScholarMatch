package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;

import java.time.LocalDateTime;
import java.util.List;

public record PostingData(
        String postingId,
        String posterUserId,
        String title,
        String description,
        ResearchField researchField,
        CollaborationType collaborationType,
        Integer capacity,
        int applicantCount,
        int acceptedCount,
        LocalDateTime createdAt,
        PostingStatus status,
        boolean full,
        boolean active,
        List<PostingApplicationData> applications) {

    public PostingData {
        applications = List.copyOf(applications);
    }

    public static PostingData from(
            final Posting posting,
            final List<PostingApplicationData> applications) {
        return new PostingData(
                posting.getPostingId(), posting.getPosterUserId(), posting.getTitle(),
                posting.getDescription(), posting.getResearchField(),
                posting.getCollaborationType(), posting.getCapacity(),
                posting.getApplicantCount(), posting.getAcceptedCount(), posting.getCreatedAt(),
                posting.getStatus(), posting.isFull(), posting.isActive(), applications);
    }

    public static PostingData from(final Posting posting) {
        return from(posting, List.of());
    }

    public static List<PostingData> fromAll(final List<Posting> postings) {
        return postings.stream().map(PostingData::from).toList();
    }

    public String getPostingId() {
        return postingId;
    }

    public String getPosterUserId() {
        return posterUserId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ResearchField getResearchField() {
        return researchField;
    }

    public CollaborationType getCollaborationType() {
        return collaborationType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public int getApplicantCount() {
        return applicantCount;
    }

    public int getAcceptedCount() {
        return acceptedCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PostingStatus getStatus() {
        return status;
    }

    public boolean isFull() {
        return full;
    }

    public boolean isActive() {
        return active;
    }

    public List<PostingApplicationData> getApplications() {
        return applications;
    }
}
