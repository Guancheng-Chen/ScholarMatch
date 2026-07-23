package com.scholarmatch.frameworks.data_access_object.server;

import com.scholarmatch.frameworks.data_access_object.http.HttpSender;
import com.scholarmatch.frameworks.data_access_object.http.HttpSenderResponse;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.exception.DataAccessException;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.ConnectException;
import java.net.http.HttpRequest;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the transport-layer behaviour that used to be tested indirectly through whichever
 * ServerRepository method happened to be handy (getProfile, login, deleteAccount, ...) — now
 * tested directly against the class that actually owns it, mocking HttpSender the same way
 * SemanticScholarGatewayTest and RemoteVerificationEmailSenderTest do rather than standing up a
 * real server. Each gateway's own test class keeps at most one "surfaces a server error" case
 * as a sanity check that it's wired to ServerHttpClient correctly, without re-proving every
 * status code again.
 */
class ServerHttpClientTest {

    private HttpSender httpSender;
    private CurrentUserProviderInterface session;
    private ServerHttpClient http;

    @BeforeEach
    void setUp() {
        this.httpSender = mock(HttpSender.class);
        this.session = mock(CurrentUserProviderInterface.class);
        when(this.session.getToken()).thenReturn("test-token");
        this.http = new ServerHttpClient("http://example.test", this.session, this.httpSender);
    }

    // ── request shape ────────────────────────────────────────────────────────

    @Test
    void testGetSendsAuthenticatedRequest() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{}"));

        this.http.get("/api/whatever");

        final HttpRequest request = capturedRequest();
        assertEquals("GET", request.method());
        assertEquals("Bearer test-token", request.headers().firstValue("Authorization").orElseThrow());
    }

    @Test
    void testPostSendsUnauthenticatedRequestWhenNotAuthenticated() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{}"));

        this.http.post("/api/whatever", "{\"a\":1}", false);

        final HttpRequest request = capturedRequest();
        assertEquals("POST", request.method());
        assertTrue(request.headers().firstValue("Authorization").isEmpty());
        assertTrue(request.bodyPublisher().isPresent());
    }

    @Test
    void testPostSendsAuthenticatedRequestWhenAuthenticated() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{}"));

        this.http.post("/api/whatever", "{}", true);

        assertEquals("Bearer test-token", capturedRequest().headers().firstValue("Authorization").orElseThrow());
    }

    @Test
    void testPutSendsAuthenticatedRequest() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{}"));

        this.http.put("/api/whatever", "{}");

        final HttpRequest request = capturedRequest();
        assertEquals("PUT", request.method());
        assertEquals("Bearer test-token", request.headers().firstValue("Authorization").orElseThrow());
    }

    // ── generic error translation ────────────────────────────────────────────

    @Test
    void testThrowsResourceNotFoundOn404WithErrorField() throws Exception {
        when(this.httpSender.send(any())).thenReturn(
                new HttpSenderResponse(404, "{\"error\": \"Profile not found\"}"));

        final ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> this.http.get("/api/profile"));
        assertEquals("Profile not found", thrown.getMessage());
    }

    @Test
    void testThrowsExternalServiceOn500WithoutErrorField() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(500, "{}"));

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> this.http.get("/api/profile"));
        assertTrue(thrown.getMessage().contains("server ran into a problem"));
    }

    @Test
    void testThrowsInvalidRequestOn400WithErrorField() throws Exception {
        when(this.httpSender.send(any())).thenReturn(
                new HttpSenderResponse(400, "{\"error\": \"Invalid credentials\"}"));

        final InvalidRequestException thrown =
                assertThrows(InvalidRequestException.class, () -> this.http.post("/api/auth/login", "{}", false));
        assertEquals("Invalid credentials", thrown.getMessage());
    }

    @Test
    void testThrowsExternalServiceWhenResponseBodyIsNotJson() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "not json at all {{{"));

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> this.http.get("/api/profile"));
        assertTrue(thrown.getMessage().contains("unexpected response"));
    }

    @Test
    void testDescribeStatusCodeCoversEveryBranch() throws Exception {
        final Map<Integer, String> expectedSubstringByStatus = Map.of(
                401, "log in again",
                403, "don't have permission",
                408, "timed out",
                429, "Too many requests",
                402, "Something went wrong (error 402)",
                503, "server ran into a problem");

        for (final Map.Entry<Integer, String> entry : expectedSubstringByStatus.entrySet()) {
            when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(entry.getKey(), "{}"));

            final DataAccessException thrown =
                    assertThrows(DataAccessException.class, () -> this.http.get("/api/profile"));
            assertTrue(thrown.getMessage().contains(entry.getValue()),
                    "status " + entry.getKey() + " message was: " + thrown.getMessage());
        }
    }

    // ── DELETE-specific error handling (204 success, discards body, custom parsing) ────

    @Test
    void testDeleteSucceedsOn204NoContent() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(204, ""));

        this.http.delete("/api/profile");

        assertEquals("DELETE", capturedRequest().method());
    }

    @Test
    void testDeleteThrowsInvalidRequestOn400WithErrorField() throws Exception {
        when(this.httpSender.send(any())).thenReturn(
                new HttpSenderResponse(400, "{\"error\": \"Bad delete request\"}"));

        final InvalidRequestException thrown =
                assertThrows(InvalidRequestException.class, () -> this.http.delete("/api/profile"));
        assertEquals("Bad delete request", thrown.getMessage());
    }

    @Test
    void testDeleteThrowsExternalServiceOn500WithoutErrorField() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(500, "{}"));

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> this.http.delete("/api/profile"));
        assertTrue(thrown.getMessage().contains("server ran into a problem"));
    }

    @Test
    void testDeleteFallsBackToStatusCodeWhenBodyIsBlank() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(400, ""));

        final InvalidRequestException thrown =
                assertThrows(InvalidRequestException.class, () -> this.http.delete("/api/profile"));
        assertTrue(thrown.getMessage().contains("request was invalid"));
    }

    @Test
    void testDeleteFallsBackToStatusCodeWhenBodyIsNotJson() throws Exception {
        when(this.httpSender.send(any())).thenReturn(new HttpSenderResponse(404, "<html>gateway error</html>"));

        final ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> this.http.delete("/api/profile"));
        assertTrue(thrown.getMessage().contains("wasn't found"));
    }

    // ── network failure ──────────────────────────────────────────────────────

    @Test
    void testGetTranslatesConnectionFailure() throws Exception {
        when(this.httpSender.send(any())).thenThrow(new ConnectException("Connection refused"));

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> this.http.get("/api/profile"));
        assertTrue(thrown.getMessage().contains("Could not connect to the server"));
        assertInstanceOf(ConnectException.class, thrown.getCause());
    }

    @Test
    void testPostTranslatesConnectionFailureUnauthenticated() throws Exception {
        when(this.httpSender.send(any())).thenThrow(new ConnectException("Connection refused"));

        assertThrows(ExternalServiceException.class, () -> this.http.post("/api/auth/login", "{}", false));
    }

    @Test
    void testPostTranslatesConnectionFailureAuthenticated() throws Exception {
        when(this.httpSender.send(any())).thenThrow(new ConnectException("Connection refused"));

        assertThrows(ExternalServiceException.class, () -> this.http.post("/api/connect", "{}", true));
    }

    @Test
    void testPutTranslatesConnectionFailure() throws Exception {
        when(this.httpSender.send(any())).thenThrow(new ConnectException("Connection refused"));

        assertThrows(ExternalServiceException.class, () -> this.http.put("/api/profile", "{}"));
    }

    @Test
    void testDeleteTranslatesConnectionFailure() throws Exception {
        when(this.httpSender.send(any())).thenThrow(new ConnectException("Connection refused"));

        assertThrows(ExternalServiceException.class, () -> this.http.delete("/api/profile"));
    }

    @Test
    void testNetworkFailureFallsBackToGenericMessageForNonIoFailures() {
        // A space in the host is invalid, so URI.create() inside get() throws
        // IllegalArgumentException — not an IOException subtype, and before the mocked
        // HttpSender is ever invoked — which exercises describeNetworkFailure's final
        // fallback branch instead of any of the specific ones.
        final ServerHttpClient malformed =
                new ServerHttpClient("http://bad host name", this.session, this.httpSender);

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> malformed.get("/api/profile"));
        assertEquals("Unable to reach the server. Please try again.", thrown.getMessage());
    }

    @Test
    void testToJsonSerializesObject() {
        assertEquals("{\"a\":\"b\"}", this.http.toJson(Map.of("a", "b")));
    }

    private HttpRequest capturedRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(this.httpSender).send(captor.capture());
        return captor.getValue();
    }
}
