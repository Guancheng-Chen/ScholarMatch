package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    void testFromAndFromAllCopyEntityFields() {
        final PostingApplication application = new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Hello",
                PostingApplicationStatus.ACCEPTED, LocalDateTime.now(),
                "Posting Title", "Ada Lovelace");

        final PostingApplicationData data = PostingApplicationData.from(application);

        assertEquals("Posting Title", data.getPostingTitle());
        assertEquals("Ada Lovelace", data.getApplicantName());
        assertEquals(List.of(data), PostingApplicationData.fromAll(List.of(application)));
    }
}
