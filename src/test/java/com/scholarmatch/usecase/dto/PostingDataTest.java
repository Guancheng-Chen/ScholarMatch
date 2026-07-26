package com.scholarmatch.usecase.dto;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostingDataTest {

    @Test
    void testFromCopiesPostingFieldsAndApplications() {
        final LocalDateTime createdAt = LocalDateTime.of(2026, 7, 26, 12, 0);
        final Posting posting = posting("posting-1", createdAt);
        final List<PostingApplicationData> applications = new ArrayList<>();

        final PostingData data = PostingData.from(posting, applications);
        applications.add(null);

        assertEquals("posting-1", data.getPostingId());
        assertEquals("poster-1", data.getPosterUserId());
        assertEquals("Title", data.getTitle());
        assertEquals("Description", data.getDescription());
        assertEquals(ResearchField.COMPUTER_SCIENCE, data.getResearchField());
        assertEquals(CollaborationType.CO_AUTHOR, data.getCollaborationType());
        assertEquals(2, data.getCapacity());
        assertEquals(1, data.getApplicantCount());
        assertEquals(0, data.getAcceptedCount());
        assertEquals(createdAt, data.getCreatedAt());
        assertEquals(PostingStatus.OPEN, data.getStatus());
        assertFalse(data.isFull());
        assertFalse(!data.isActive());
        assertEquals(List.of(), data.getApplications());
        assertThrows(UnsupportedOperationException.class,
                () -> data.getApplications().add(null));
    }

    @Test
    void testFromWithoutApplicationsAndFromAll() {
        final Posting first = posting("posting-1", LocalDateTime.now());
        final Posting second = posting("posting-2", LocalDateTime.now());

        assertEquals(List.of(), PostingData.from(first).getApplications());
        assertEquals(List.of("posting-1", "posting-2"),
                PostingData.fromAll(List.of(first, second)).stream()
                        .map(PostingData::getPostingId)
                        .toList());
    }

    private Posting posting(final String id, final LocalDateTime createdAt) {
        return new Posting(
                id, "poster-1", "Title", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2, 1, 0, PostingStatus.OPEN, createdAt);
    }
}
