package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    @SuppressWarnings("unchecked")
    void mapsAuthorSearchResponse() throws Exception {
        final HttpClient httpClient = mock(HttpClient.class);
        final HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
                {"data":[{"authorId":"1695689","name":"Geoffrey E. Hinton",
                "affiliations":["Google","University of Toronto"],"paperCount":467,
                "hIndex":162,"citationCount":578042}]}
                """);
        when(httpClient.<String>send(any(), any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpClient,
                new ObjectMapper());

        final var candidates = gateway.searchAuthors("Geoffrey Hinton");

        assertEquals(1, candidates.size());
        assertEquals("1695689", candidates.getFirst().getAuthorId());
        assertEquals("Geoffrey E. Hinton", candidates.getFirst().getName());
        assertEquals(162, candidates.getFirst().getHIndex());
    }

    @Test
    @SuppressWarnings("unchecked")
    void mapsPaperWithoutDoi() throws Exception {
        final HttpClient httpClient = mock(HttpClient.class);
        final HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
                {"data":[{"title":"The Forward-Forward Algorithm","year":2022,
                "citationCount":441,"externalIds":{}}]}
                """);
        when(httpClient.<String>send(any(), any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpClient,
                new ObjectMapper());

        final var papers = gateway.getAuthorPapers("1695689");

        assertEquals(1, papers.size());
        assertEquals("", papers.getFirst().getDoi());
        assertEquals("The Forward-Forward Algorithm", papers.getFirst().getTitle());
        assertEquals(2022, papers.getFirst().getYear());
        assertEquals(441, papers.getFirst().getCitationCount());
    }

    @Test
    @SuppressWarnings("unchecked")
    void requestsTwentyAuthorCandidates() throws Exception {
        final HttpClient httpClient = mock(HttpClient.class);
        final HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\"data\":[]}");
        when(httpClient.<String>send(any(), any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpClient,
                new ObjectMapper());

        gateway.searchAuthors("Geoffrey Hinton");

        final ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient).send(requestCaptor.capture(), any());
        assertTrue(requestCaptor.getValue().uri().getQuery().contains("limit=20"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void retriesOnceWhenRateLimited() throws Exception {
        final HttpClient httpClient = mock(HttpClient.class);
        final HttpResponse<String> rateLimitedResponse = mock(HttpResponse.class);
        final HttpResponse<String> successResponse = mock(HttpResponse.class);
        when(rateLimitedResponse.statusCode()).thenReturn(429);
        when(successResponse.statusCode()).thenReturn(200);
        when(successResponse.body()).thenReturn("{\"data\":[]}");
        when(httpClient.<String>send(any(), any()))
                .thenReturn(rateLimitedResponse, successResponse);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpClient,
                new ObjectMapper());

        gateway.searchAuthors("Geoffrey Hinton");

        verify(httpClient, times(2)).send(any(), any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void failsAfterSecondRateLimitResponse() throws Exception {
        final HttpClient httpClient = mock(HttpClient.class);
        final HttpResponse<String> rateLimitedResponse = mock(HttpResponse.class);
        when(rateLimitedResponse.statusCode()).thenReturn(429);
        when(httpClient.<String>send(any(), any())).thenReturn(rateLimitedResponse);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpClient,
                new ObjectMapper());

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> gateway.searchAuthors("Geoffrey Hinton"));

        assertEquals("Semantic Scholar returned HTTP 429.", exception.getMessage());
        verify(httpClient, times(2)).send(any(), any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void sendsConfiguredApiKey() throws Exception {
        final HttpClient httpClient = mock(HttpClient.class);
        final HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\"data\":[]}");
        when(httpClient.<String>send(any(), any())).thenReturn(response);
        final SemanticScholarGateway gateway = new SemanticScholarGateway(
                httpClient,
                new ObjectMapper(),
                "test-api-key");

        gateway.searchAuthors("Geoffrey Hinton");

        final ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient).send(requestCaptor.capture(), any());
        assertEquals(
                "test-api-key",
                requestCaptor.getValue().headers().firstValue("x-api-key").orElseThrow());
    }
}
