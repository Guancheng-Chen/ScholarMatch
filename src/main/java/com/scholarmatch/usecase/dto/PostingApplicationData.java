package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Immutable application DTO exposed outside the use-case layer.
 *
 * @param applicationId application identifier
 * @param postingId posting identifier
 * @param applicantUserId applicant identifier
 * @param message applicant message
 * @param status review status
 * @param appliedAt submission time
 * @param postingTitle posting display title
 * @param applicantName applicant display name
 */
@SuppressWarnings("JavadocType")
public record PostingApplicationData(
        String applicationId,
        String postingId,
        String applicantUserId,
        String message,
        PostingApplicationStatus status,
        LocalDateTime appliedAt,
        String postingTitle,
        String applicantName,
        String posterUserId,
        String posterName,
        boolean posterAcademicEmailVerified) {

    public PostingApplicationData {
        posterUserId = posterUserId == null ? "" : posterUserId.trim();
        posterName = posterName == null ? "" : posterName.trim();
    }

    public PostingApplicationData(
            final String applicationId,
            final String postingId,
            final String applicantUserId,
            final String message,
            final PostingApplicationStatus status,
            final LocalDateTime appliedAt) {
        this(
                applicationId, postingId, applicantUserId, message, status,
                appliedAt, "", "", "", "", false);
    }

    public PostingApplicationData(
            final String applicationId,
            final String postingId,
            final String applicantUserId,
            final String message,
            final PostingApplicationStatus status,
            final LocalDateTime appliedAt,
            final String postingTitle,
            final String applicantName) {
        this(
                applicationId, postingId, applicantUserId, message, status,
                appliedAt, postingTitle, applicantName, "", "", false);
    }

    public PostingApplicationData(
            final String applicationId,
            final String postingId,
            final String applicantUserId,
            final String message,
            final PostingApplicationStatus status,
            final LocalDateTime appliedAt,
            final String postingTitle,
            final String applicantName,
            final String posterName,
            final boolean posterAcademicEmailVerified) {
        this(
                applicationId, postingId, applicantUserId, message, status,
                appliedAt, postingTitle, applicantName, "", posterName,
                posterAcademicEmailVerified);
    }

    public static PostingApplicationData from(final PostingApplication application) {
        return new PostingApplicationData(
                application.getApplicationId(),
                application.getPostingId(),
                application.getApplicantUserId(),
                application.getMessage(),
                application.getStatus(),
                application.getAppliedAt(),
                application.getPostingTitle(),
                application.getApplicantName(),
                application.getPosterUserId(),
                application.getPosterName(),
                application.isPosterAcademicEmailVerified());
    }

    public static List<PostingApplicationData> fromAll(
            final List<PostingApplication> applications) {
        return applications.stream().map(PostingApplicationData::from).toList();
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getPostingId() {
        return postingId;
    }

    public String getApplicantUserId() {
        return applicantUserId;
    }

    public String getMessage() {
        return message;
    }

    public PostingApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public String getPostingTitle() {
        return postingTitle;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getPosterUserId() {
        return posterUserId;
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
