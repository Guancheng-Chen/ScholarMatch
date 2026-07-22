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

    private PostingApplication application() {
        return new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Please consider me",
                PostingApplicationStatus.PENDING, LocalDateTime.now());
    }
}
