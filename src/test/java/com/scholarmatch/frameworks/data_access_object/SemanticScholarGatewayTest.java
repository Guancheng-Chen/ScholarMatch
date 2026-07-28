package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholarmatch.frameworks.data_access_object.http.HttpSender;
import com.scholarmatch.frameworks.data_access_object.http.HttpSenderResponse;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.http.HttpRequest;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SemanticScholarGatewayTest {

    @Test
    void mapsAuthorSearchResponse() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final HttpSenderResponse response = new HttpSenderResponse(200, """
                {"data":[{"authorId":"1695689","name":"Geoffrey E. Hinton",
                "affiliations":["Google","University of Toronto"],"paperCount":467,
                "hIndex":162,"citationCount":578042}]}
                """);
        when(httpSender.send(any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpSender,
                new ObjectMapper());

        final var candidates = gateway.searchAuthors("Geoffrey Hinton");

        assertEquals(1, candidates.size());
        assertEquals("1695689", candidates.getFirst().getAuthorId());
        assertEquals("Geoffrey E. Hinton", candidates.getFirst().getName());
        assertEquals(162, candidates.getFirst().getHIndex());
    }

    @Test
    void getsAuthorById() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final HttpSenderResponse response = new HttpSenderResponse(200, """
                {"authorId":"1695689","name":"Geoffrey E. Hinton","affiliations":[],
                "paperCount":467,"hIndex":162,"citationCount":578042}
                """);
        when(httpSender.send(any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpSender,
                new ObjectMapper());

        final var author = gateway.getAuthor("1695689");

        assertEquals("1695689", author.getAuthorId());
        assertEquals("Geoffrey E. Hinton", author.getName());
        assertEquals(467, author.getPaperCount());
    }

    @Test
    void mapsPaperWithoutDoi() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final HttpSenderResponse response = new HttpSenderResponse(200, """
                {"data":[{"title":"The Forward-Forward Algorithm","year":2022,
                "citationCount":441,"externalIds":{}}]}
                """);
        when(httpSender.send(any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpSender,
                new ObjectMapper());

        final var papers = gateway.getAuthorPapers("1695689");

        assertEquals(1, papers.size());
        assertEquals("", papers.getFirst().getDoi());
        assertEquals("The Forward-Forward Algorithm", papers.getFirst().getTitle());
        assertEquals(2022, papers.getFirst().getYear());
        assertEquals(441, papers.getFirst().getCitationCount());
    }

    @Test
    void requestsEnoughAuthorsForLocalRanking() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final HttpSenderResponse response = new HttpSenderResponse(200, "{\"data\":[]}");
        when(httpSender.send(any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpSender,
                new ObjectMapper());

        gateway.searchAuthors("Geoffrey Hinton");

        final ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpSender).send(requestCaptor.capture());
        assertTrue(requestCaptor.getValue().uri().getQuery().contains("limit=200"));
    }

    @Test
    void retriesOnceWhenRateLimited() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final HttpSenderResponse rateLimitedResponse = new HttpSenderResponse(429, "");
        final HttpSenderResponse successResponse = new HttpSenderResponse(200, "{\"data\":[]}");
        when(httpSender.send(any()))
                .thenReturn(rateLimitedResponse, successResponse);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpSender,
                new ObjectMapper());

        gateway.searchAuthors("Geoffrey Hinton");

        verify(httpSender, times(2)).send(any());
    }

    @Test
    void failsAfterSecondRateLimitResponse() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final HttpSenderResponse rateLimitedResponse = new HttpSenderResponse(429, "");
        when(httpSender.send(any())).thenReturn(rateLimitedResponse);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpSender,
                new ObjectMapper());

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> gateway.searchAuthors("Geoffrey Hinton"));

        assertEquals("Semantic Scholar returned HTTP 429.", exception.getMessage());
        verify(httpSender, times(2)).send(any());
    }

    @Test
    void sendsConfiguredApiKey() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final HttpSenderResponse response = new HttpSenderResponse(200, "{\"data\":[]}");
        when(httpSender.send(any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpSender,
                new ObjectMapper(),
                "test-api-key");

        gateway.searchAuthors("Geoffrey Hinton");

        final ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpSender).send(requestCaptor.capture());
        assertEquals(
                "test-api-key",
                requestCaptor.getValue().headers().firstValue("x-api-key").orElseThrow());
    }

    @Test
    void missingAuthorIsReported() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{}"));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        assertThrows(ResourceNotFoundException.class, () -> gateway.getAuthor("missing"));
    }

    @Test
    void notFoundSearchReturnsEmptyResults() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(404, "{}"));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        assertEquals(List.of(), gateway.searchAuthors("Missing Author"));
    }

    @Test
    void mapsNullAndMissingAuthorMetrics() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, """
                {"authorId":"1","name":"Ada","paperCount":null,"citationCount":4}
                """));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        final var author = gateway.getAuthor("1");

        assertEquals(null, author.getPaperCount());
        assertEquals(null, author.getHIndex());
    }

    @Test
    void mapsExplicitlyNullDoi() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, """
                {"data":[{"title":"Paper","externalIds":{"DOI":null}}]}
                """));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        assertEquals("", gateway.getAuthorPapers("1").getFirst().getDoi());
    }

    @Test
    void mapsPresentDoi() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, """
                {"data":[{"title":"Paper","externalIds":{"DOI":"10.1/example"}}]}
                """));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        assertEquals("10.1/example", gateway.getAuthorPapers("1").getFirst().getDoi());
    }

    @Test
    void productionConstructorCanBeCreated() {
        assertTrue(new SemanticScholarGateway() instanceof SemanticScholarGateway);
    }

    @Test
    void sendsApiKeyHeaderWhenConfigured() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{\"data\":[]}"));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper(), "secret-key");

        gateway.searchAuthors("Ada Lovelace");

        final ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpSender).send(requestCaptor.capture());
        assertEquals(List.of("secret-key"), requestCaptor.getValue().headers().allValues("x-api-key"));
    }

    @Test
    void serverErrorStatusIsTranslated() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(500, ""));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        assertThrows(ExternalServiceException.class,
                () -> gateway.searchAuthors("Ada Lovelace"));
    }

    @Test
    void informationalStatusBelow200IsTranslated() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(100, ""));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        assertThrows(ExternalServiceException.class,
                () -> gateway.searchAuthors("Ada Lovelace"));
    }

    @Test
    void blankApiKeyDoesNotSendHeader() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{\"data\":[]}"));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper(), "   ");

        gateway.searchAuthors("Ada Lovelace");

        final ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpSender).send(requestCaptor.capture());
        assertTrue(requestCaptor.getValue().headers().allValues("x-api-key").isEmpty());
    }

    @Test
    void interruptedRequestIsTranslated() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenThrow(new InterruptedException("interrupted"));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        try {
            assertThrows(ExternalServiceException.class,
                    () -> gateway.searchAuthors("Ada Lovelace"));
            assertTrue(Thread.currentThread().isInterrupted());
        } finally {
            Thread.interrupted();
        }
    }

    @Test
    void ioFailureIsTranslated() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenThrow(new IOException("offline"));
        final SemanticScholarGateway gateway =
                new SemanticScholarGateway(httpSender, new ObjectMapper());

        assertThrows(ExternalServiceException.class,
                () -> gateway.searchAuthors("Ada Lovelace"));
    }
}
