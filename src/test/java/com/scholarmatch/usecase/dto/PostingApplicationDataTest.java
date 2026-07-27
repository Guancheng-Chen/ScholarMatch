package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostingApplicationDataTest {

    @Test
    void testShortConstructorAndGetters() {
        final LocalDateTime appliedAt = LocalDateTime.of(2026, 7, 26, 12, 0);
        final PostingApplicationData data = new PostingApplicationData(
                "application-1", "posting-1", "applicant-1", "Hello",
                PostingApplicationStatus.PENDING, appliedAt);

        assertEquals("application-1", data.getApplicationId());
        assertEquals("posting-1", data.getPostingId());
        assertEquals("applicant-1", data.getApplicantUserId());
        assertEquals("Hello", data.getMessage());
        assertEquals(PostingApplicationStatus.PENDING, data.getStatus());
        assertEquals(appliedAt, data.getAppliedAt());
        assertEquals("", data.getPostingTitle());
        assertEquals("", data.getApplicantName());
        assertEquals("", data.getPosterUserId());
        assertEquals("", data.getPosterName());
        assertEquals("Unknown user", data.getPosterDisplayName());
        assertFalse(data.isPosterAcademicEmailVerified());
    }

    @Test
    void testFromAndFromAllCopyEntityFields() {
        final PostingApplication application = new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Hello",
                PostingApplicationStatus.ACCEPTED, LocalDateTime.now(),
                "Posting Title", "Ada Lovelace", "poster-1",
                "Grace Hopper", true);

        final PostingApplicationData data = PostingApplicationData.from(application);

        assertEquals("Posting Title", data.getPostingTitle());
        assertEquals("Ada Lovelace", data.getApplicantName());
        assertEquals("poster-1", data.getPosterUserId());
        assertEquals("Grace Hopper", data.getPosterName());
        assertEquals("Grace Hopper", data.getPosterDisplayName());
        assertTrue(data.isPosterAcademicEmailVerified());
        assertEquals(List.of(data), PostingApplicationData.fromAll(List.of(application)));
    }

    @Test
    void testOwnerSummaryTrimsProvidedIdentity() {
        final PostingApplicationData data = new PostingApplicationData(
                "application-1", "posting-1", "applicant-1", "Hello",
                PostingApplicationStatus.PENDING, LocalDateTime.now(),
                "Posting Title", "Ada Lovelace", " poster-1 ",
                " Grace Hopper ", true);

        assertEquals("poster-1", data.getPosterUserId());
        assertEquals("Grace Hopper", data.getPosterName());
        assertEquals("Grace Hopper", data.getPosterDisplayName());
        assertTrue(data.isPosterAcademicEmailVerified());
    }
}
