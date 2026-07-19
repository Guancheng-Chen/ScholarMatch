package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.AuthorCandidateDataAccessInterface;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalUserApiGatewayTest {

    private LocalUserApiGateway gateway;

    @BeforeEach
    void setUp() {
        gateway = new LocalUserApiGateway();
    }

    @Test
    void testSearchAuthorsMatchesCaseInsensitiveSubstring() {
        final List<AuthorCandidateDataAccessInterface> results = gateway.searchAuthors("hinton");

        assertEquals(1, results.size());
        assertEquals("Geoffrey Hinton", results.get(0).getName());
    }

    @Test
    void testSearchAuthorsMatchesMixedCaseAndTrimsWhitespace() {
        final List<AuthorCandidateDataAccessInterface> results = gateway.searchAuthors("  VASWANI  ");

        assertEquals(1, results.size());
        assertEquals("Ashish Vaswani", results.get(0).getName());
    }

    @Test
    void testSearchAuthorsReturnsEmptyListWhenNoMatch() {
        final List<AuthorCandidateDataAccessInterface> results = gateway.searchAuthors("Nonexistent Person");

        assertTrue(results.isEmpty());
    }

    @Test
    void testFetchPapersByAuthorIdReturnsKnownAuthorPapers() {
        final List<Publication> papers = gateway.fetchPapersByAuthorId("he-local");

        assertEquals(1, papers.size());
        assertEquals("Deep Residual Learning for Image Recognition", papers.get(0).getTitle());
    }

    @Test
    void testFetchPapersByAuthorIdThrowsForUnknownId() {
        assertThrows(ResourceNotFoundException.class, () -> gateway.fetchPapersByAuthorId("unknown-id"));
    }
}
