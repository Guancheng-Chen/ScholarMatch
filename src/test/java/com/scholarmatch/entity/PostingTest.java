package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostingTest {

    @Test
    void testAcceptedCapacityClosesPosting() {
        final Posting posting = posting(2, 2, 1);

        assertFalse(posting.isFull());
        assertTrue(posting.isActive());

        posting.recordAcceptedApplication();

        assertEquals(2, posting.getAcceptedCount());
        assertTrue(posting.isFull());
        assertFalse(posting.isActive());
        assertEquals(PostingStatus.CLOSED, posting.getStatus());
    }

    @Test
    void testApplicationsDoNotFillPosting() {
        final Posting posting = posting(1, 0, 0);

        posting.recordApplication();
        posting.recordApplication();

        assertEquals(2, posting.getApplicantCount());
        assertFalse(posting.isFull());
        assertTrue(posting.isActive());
    }

    @Test
    void testManualCloseAndUnlimitedCapacity() {
        final Posting posting = posting(null, 500, 100);

        assertFalse(posting.isFull());
        assertTrue(posting.isActive());

        posting.close();

        assertEquals(PostingStatus.CLOSED, posting.getStatus());
        assertFalse(posting.isActive());
    }

    @Test
    void testRejectsInvalidCapacityAndCounts() {
        assertThrows(IllegalArgumentException.class, () -> posting(0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> posting(1, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> posting(1, 1, -1));
        assertThrows(IllegalArgumentException.class, () -> posting(1, 1, 2));
    }

    @Test
    void testExposesSnapshotAndUpdatesApplicantCount() {
        final LocalDateTime createdAt = LocalDateTime.of(2026, 7, 26, 12, 0);
        final Posting posting = new Posting(
                "posting-1", "poster-1", "Title", "Description",
                ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                3, 1, 0, PostingStatus.OPEN, createdAt);

        posting.setApplicantCount(2);

        assertEquals("posting-1", posting.getPostingId());
        assertEquals("poster-1", posting.getPosterUserId());
        assertEquals("Title", posting.getTitle());
        assertEquals("Description", posting.getDescription());
        assertEquals(ResearchField.COMPUTER_SCIENCE, posting.getResearchField());
        assertEquals(CollaborationType.CO_AUTHOR, posting.getCollaborationType());
        assertEquals(3, posting.getCapacity());
        assertEquals(2, posting.getApplicantCount());
        assertEquals(0, posting.getAcceptedCount());
        assertEquals(PostingStatus.OPEN, posting.getStatus());
        assertEquals(createdAt, posting.getCreatedAt());
    }

    private Posting posting(
            final Integer capacity,
            final int applicantCount,
            final int acceptedCount) {
        return new Posting(
                "posting-1", "poster-1", "Title", "Description",
                ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                capacity, applicantCount, acceptedCount, PostingStatus.OPEN, LocalDateTime.now());
    }
}
