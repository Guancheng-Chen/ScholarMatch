package com.scholarmatch.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * One user's application to a posting.
 */
public final class PostingApplication {

    private final String applicationId;
    private final String postingId;
    private final String applicantUserId;
    private final String message;
    private final LocalDateTime appliedAt;
    private final String postingTitle;
    private final String applicantName;
    private PostingApplicationStatus status;

    /**
     * Creates an application snapshot.
     */
    public PostingApplication(
            final String applicationId,
            final String postingId,
            final String applicantUserId,
            final String message,
            final PostingApplicationStatus status,
            final LocalDateTime appliedAt) {
        this(applicationId, postingId, applicantUserId, message, status, appliedAt, "", "");
    }

    public PostingApplication(
            final String applicationId,
            final String postingId,
            final String applicantUserId,
            final String message,
            final PostingApplicationStatus status,
            final LocalDateTime appliedAt,
            final String postingTitle,
            final String applicantName) {
        this.applicationId = Objects.requireNonNull(applicationId);
        this.postingId = Objects.requireNonNull(postingId);
        this.applicantUserId = Objects.requireNonNull(applicantUserId);
        this.message = Objects.requireNonNull(message);
        this.status = Objects.requireNonNull(status);
        this.appliedAt = Objects.requireNonNull(appliedAt);
        this.postingTitle = postingTitle == null ? "" : postingTitle;
        this.applicantName = applicantName == null ? "" : applicantName;
    }

    public void accept() {
        requirePending();
        this.status = PostingApplicationStatus.ACCEPTED;
    }

    public void reject() {
        requirePending();
        this.status = PostingApplicationStatus.REJECTED;
    }

    public void setStatus(final PostingApplicationStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    private void requirePending() {
        if (this.status != PostingApplicationStatus.PENDING) {
            throw new IllegalStateException("Only pending applications can be decided.");
        }
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public String getPostingId() {
        return this.postingId;
    }

    public String getApplicantUserId() {
        return this.applicantUserId;
    }

    public String getMessage() {
        return this.message;
    }

    public PostingApplicationStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getAppliedAt() {
        return this.appliedAt;
    }

    public String getPostingTitle() {
        return this.postingTitle;
    }

    public String getApplicantName() {
        return this.applicantName;
    }
}
