package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostingApplicationTest {

    @Test
    void testPendingApplicationCanBeAccepted() {
        final PostingApplication application = application();

        application.accept();

        assertEquals(PostingApplicationStatus.ACCEPTED, application.getStatus());
        assertThrows(IllegalStateException.class, application::reject);
    }

    @Test
    void testPendingApplicationCanBeRejected() {
        final PostingApplication application = application();

        application.reject();

        assertEquals(PostingApplicationStatus.REJECTED, application.getStatus());
        assertThrows(IllegalStateException.class, application::accept);
    }

    @Test
    void testExposesSnapshotAndDefaultsNullDisplayMetadata() {
        final LocalDateTime appliedAt = LocalDateTime.of(2026, 7, 26, 12, 0);
        final PostingApplication application = new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Please consider me",
                PostingApplicationStatus.PENDING, appliedAt, null, null);

        application.setStatus(PostingApplicationStatus.ACCEPTED);

        assertEquals("application-1", application.getApplicationId());
        assertEquals("posting-1", application.getPostingId());
        assertEquals("applicant-1", application.getApplicantUserId());
        assertEquals("Please consider me", application.getMessage());
        assertEquals(PostingApplicationStatus.ACCEPTED, application.getStatus());
        assertEquals(appliedAt, application.getAppliedAt());
        assertEquals("", application.getPostingTitle());
        assertEquals("", application.getApplicantName());
    }

    @Test
    void testFullConstructorDefaultsNullPosterFieldsToEmptyString() {
        final PostingApplication application = new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Please consider me",
                PostingApplicationStatus.PENDING, LocalDateTime.now(), "Title", "Applicant",
                null, null, false);

        assertEquals("", application.getPosterUserId());
        assertEquals("", application.getPosterName());
    }

    private PostingApplication application() {
        return new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Please consider me",
                PostingApplicationStatus.PENDING, LocalDateTime.now());
    }
}
