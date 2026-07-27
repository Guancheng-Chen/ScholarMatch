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
        String posterName,
        boolean posterAcademicEmailVerified,
        List<PostingApplicationData> applications) {

    public PostingData {
        posterUserId = posterUserId == null ? "" : posterUserId.trim();
        posterName = posterName == null ? "" : posterName.trim();
        applications = List.copyOf(applications);
    }

    public PostingData(
            final String postingId,
            final String posterUserId,
            final String title,
            final String description,
            final ResearchField researchField,
            final CollaborationType collaborationType,
            final Integer capacity,
            final int applicantCount,
            final int acceptedCount,
            final LocalDateTime createdAt,
            final PostingStatus status,
            final boolean full,
            final boolean active,
            final List<PostingApplicationData> applications) {
        this(
                postingId, posterUserId, title, description, researchField,
                collaborationType, capacity, applicantCount, acceptedCount,
                createdAt, status, full, active, "", false, applications);
    }

    public static PostingData from(
            final Posting posting,
            final List<PostingApplicationData> applications) {
        return new PostingData(
                posting.getPostingId(), posting.getPosterUserId(), posting.getTitle(),
                posting.getDescription(), posting.getResearchField(),
                posting.getCollaborationType(), posting.getCapacity(),
                posting.getApplicantCount(), posting.getAcceptedCount(), posting.getCreatedAt(),
                posting.getStatus(), posting.isFull(), posting.isActive(),
                posting.getPosterName(), posting.isPosterAcademicEmailVerified(),
                applications);
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

    public String getPosterName() {
        return posterName;
    }

    public String getPosterDisplayName() {
        return posterName.isBlank() ? "Unknown user" : posterName;
    }

    public boolean isPosterAcademicEmailVerified() {
        return posterAcademicEmailVerified;
    }
}
