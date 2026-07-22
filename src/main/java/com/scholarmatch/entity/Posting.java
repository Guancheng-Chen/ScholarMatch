package com.scholarmatch.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A time-bounded collaboration posting created by one ScholarMatch user.
 */
public final class Posting {

    private final String postingId;
    private final String posterUserId;
    private final String title;
    private final String description;
    private final ResearchField researchField;
    private final CollaborationType collaborationType;
    private final Integer capacity;
    private final LocalDateTime createdAt;
    private int applicantCount;
    private int acceptedCount;
    private PostingStatus status;

    /**
     * Creates a posting snapshot.
     */
    public Posting(
            final String postingId,
            final String posterUserId,
            final String title,
            final String description,
            final ResearchField researchField,
            final CollaborationType collaborationType,
            final Integer capacity,
            final int applicantCount,
            final int acceptedCount,
            final PostingStatus status,
            final LocalDateTime createdAt) {
        if (capacity != null && capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        if (applicantCount < 0 || acceptedCount < 0 || acceptedCount > applicantCount) {
            throw new IllegalArgumentException("Posting counts are inconsistent");
        }
        this.postingId = Objects.requireNonNull(postingId);
        this.posterUserId = Objects.requireNonNull(posterUserId);
        this.title = Objects.requireNonNull(title);
        this.description = Objects.requireNonNull(description);
        this.researchField = Objects.requireNonNull(researchField);
        this.collaborationType = Objects.requireNonNull(collaborationType);
        this.capacity = capacity;
        this.applicantCount = applicantCount;
        this.acceptedCount = acceptedCount;
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public boolean isFull() {
        return this.capacity != null && this.acceptedCount >= this.capacity;
    }

    public boolean isActive() {
        return this.status == PostingStatus.OPEN && !isFull();
    }

    public void recordApplication() {
        this.applicantCount++;
    }

    public void setApplicantCount(final int applicantCount) {
        this.applicantCount = applicantCount;
    }

    public void recordAcceptedApplication() {
        this.acceptedCount++;
        if (isFull()) {
            close();
        }
    }

    public void close() {
        this.status = PostingStatus.CLOSED;
    }

    public String getPostingId() {
        return this.postingId;
    }

    public String getPosterUserId() {
        return this.posterUserId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public ResearchField getResearchField() {
        return this.researchField;
    }

    public CollaborationType getCollaborationType() {
        return this.collaborationType;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public int getApplicantCount() {
        return this.applicantCount;
    }

    public int getAcceptedCount() {
        return this.acceptedCount;
    }

    public PostingStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
