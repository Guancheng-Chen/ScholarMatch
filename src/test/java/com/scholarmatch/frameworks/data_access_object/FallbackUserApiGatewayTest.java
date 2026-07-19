package com.scholarmatch.frameworks.data_access_object;

import java.util.List;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.AuthorCandidateDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import com.scholarmatch.usecase.exception.DataAccessException;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FallbackUserApiGatewayTest {

    @Test
    void searchAuthorsReturnsPrimaryResultWithoutCallingFallback() {
        final List<AuthorCandidateDataAccessInterface> primaryResult = List.of(authorCandidate("primary"));
        final RecordingGateway primary = new RecordingGateway();
        final RecordingGateway fallback = new RecordingGateway();
        primary.authorResult = primaryResult;
        fallback.authorResult = List.of(authorCandidate("fallback"));
        final FallbackUserApiGateway gateway = new FallbackUserApiGateway(primary, fallback);

        final List<AuthorCandidateDataAccessInterface> actual = gateway.searchAuthors("Ada Lovelace");

        assertSame(primaryResult, actual);
        assertEquals(1, primary.searchAuthorsCallCount);
        assertEquals("Ada Lovelace", primary.lastAuthorName);
        assertEquals(0, fallback.searchAuthorsCallCount);
    }

    @Test
    void searchAuthorsReturnsFallbackResultWhenPrimaryThrows() {
        final DataAccessException primaryError = new ExternalServiceException("primary failed");
        final List<AuthorCandidateDataAccessInterface> fallbackResult = List.of(authorCandidate("fallback"));
        final RecordingGateway primary = new RecordingGateway();
        final RecordingGateway fallback = new RecordingGateway();
        primary.authorError = primaryError;
        fallback.authorResult = fallbackResult;
        final FallbackUserApiGateway gateway = new FallbackUserApiGateway(primary, fallback);

        final List<AuthorCandidateDataAccessInterface> actual = gateway.searchAuthors("Grace Hopper");

        assertSame(fallbackResult, actual);
        assertEquals(1, primary.searchAuthorsCallCount);
        assertEquals(1, fallback.searchAuthorsCallCount);
        assertEquals("Grace Hopper", fallback.lastAuthorName);
    }

    @Test
    void searchAuthorsPropagatesPrimaryErrorWhenBothGatewaysThrow() {
        final DataAccessException primaryError = new ExternalServiceException("primary failed");
        final DataAccessException fallbackError = new ExternalServiceException("fallback failed");
        final RecordingGateway primary = new RecordingGateway();
        final RecordingGateway fallback = new RecordingGateway();
        primary.authorError = primaryError;
        fallback.authorError = fallbackError;
        final FallbackUserApiGateway gateway = new FallbackUserApiGateway(primary, fallback);

        final DataAccessException actual = assertThrows(
                DataAccessException.class,
                () -> gateway.searchAuthors("Katherine Johnson"));

        assertSame(primaryError, actual);
        assertEquals(1, primary.searchAuthorsCallCount);
        assertEquals(1, fallback.searchAuthorsCallCount);
    }

    @Test
    void getAuthorPapersReturnsPrimaryResultWithoutCallingFallback() {
        final List<Publication> primaryResult = List.of(publication("primary"));
        final RecordingGateway primary = new RecordingGateway();
        final RecordingGateway fallback = new RecordingGateway();
        primary.paperResult = primaryResult;
        fallback.paperResult = List.of(publication("fallback"));
        final FallbackUserApiGateway gateway = new FallbackUserApiGateway(primary, fallback);

        final List<Publication> actual = gateway.getAuthorPapers("author-1");

        assertSame(primaryResult, actual);
        assertEquals(1, primary.getAuthorPapersCallCount);
        assertEquals("author-1", primary.lastAuthorId);
        assertEquals(0, fallback.getAuthorPapersCallCount);
    }

    @Test
    void getAuthorPapersReturnsFallbackResultWhenPrimaryThrows() {
        final DataAccessException primaryError = new ExternalServiceException("primary failed");
        final List<Publication> fallbackResult = List.of(publication("fallback"));
        final RecordingGateway primary = new RecordingGateway();
        final RecordingGateway fallback = new RecordingGateway();
        primary.paperError = primaryError;
        fallback.paperResult = fallbackResult;
        final FallbackUserApiGateway gateway = new FallbackUserApiGateway(primary, fallback);

        final List<Publication> actual = gateway.getAuthorPapers("author-2");

        assertSame(fallbackResult, actual);
        assertEquals(1, primary.getAuthorPapersCallCount);
        assertEquals(1, fallback.getAuthorPapersCallCount);
        assertEquals("author-2", fallback.lastAuthorId);
    }

    @Test
    void getAuthorPapersPropagatesPrimaryErrorWhenBothGatewaysThrow() {
        final DataAccessException primaryError = new ExternalServiceException("primary failed");
        final DataAccessException fallbackError = new ExternalServiceException("fallback failed");
        final RecordingGateway primary = new RecordingGateway();
        final RecordingGateway fallback = new RecordingGateway();
        primary.paperError = primaryError;
        fallback.paperError = fallbackError;
        final FallbackUserApiGateway gateway = new FallbackUserApiGateway(primary, fallback);

        final DataAccessException actual = assertThrows(
                DataAccessException.class,
                () -> gateway.getAuthorPapers("author-3"));

        assertSame(primaryError, actual);
        assertEquals(1, primary.getAuthorPapersCallCount);
        assertEquals(1, fallback.getAuthorPapersCallCount);
    }

    private static AuthorCandidateDataAccessInterface authorCandidate(final String id) {
        return new AuthorCandidateDto(id, "Test Author", List.of("Test University"), 1, 1, 1);
    }

    private static Publication publication(final String suffix) {
        return new Publication("10.0000/" + suffix, "Test Publication", 2024, 1);
    }

    private static final class RecordingGateway implements UserAPIGatewayInterface {

        private List<AuthorCandidateDataAccessInterface> authorResult;
        private List<Publication> paperResult;
        private DataAccessException authorError;
        private DataAccessException paperError;
        private int searchAuthorsCallCount;
        private int getAuthorPapersCallCount;
        private String lastAuthorName;
        private String lastAuthorId;

        @Override
        public List<AuthorCandidateDataAccessInterface> searchAuthors(final String authorName) {
            this.searchAuthorsCallCount++;
            this.lastAuthorName = authorName;
            if (this.authorError != null) {
                throw this.authorError;
            }
            return this.authorResult;
        }

        @Override
        public AuthorCandidateDataAccessInterface getAuthor(final String authorId) {
            this.lastAuthorId = authorId;
            if (this.authorError != null) {
                throw this.authorError;
            }
            return this.authorResult.getFirst();
        }

        @Override
        public List<Publication> getAuthorPapers(final String authorId) {
            this.getAuthorPapersCallCount++;
            this.lastAuthorId = authorId;
            if (this.paperError != null) {
                throw this.paperError;
            }
            return this.paperResult;
        }
    }
}
