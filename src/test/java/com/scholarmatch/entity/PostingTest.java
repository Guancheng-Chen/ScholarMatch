package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
