package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SemanticScholarGatewayTest {

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
}
