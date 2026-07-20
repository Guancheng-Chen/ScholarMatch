package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholarmatch.frameworks.data_access_object.http.HttpSender;
import com.scholarmatch.frameworks.data_access_object.http.HttpSenderResponse;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.http.HttpRequest;

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
}
