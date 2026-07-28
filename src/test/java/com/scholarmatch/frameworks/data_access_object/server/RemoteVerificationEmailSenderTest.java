package com.scholarmatch.frameworks.data_access_object.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.scholarmatch.frameworks.data_access_object.http.HttpSender;
import com.scholarmatch.frameworks.data_access_object.http.HttpSenderResponse;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.http.HttpRequest;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RemoteVerificationEmailSenderTest {

    @Test
    void testProductionConstructorCanBeCreated() {
        assertTrue(new RemoteVerificationEmailSender("https://example.test")
                instanceof RemoteVerificationEmailSender);
    }

    @Test
    void testPostsJsonToServerVerificationEndpoint() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{}"));
        final RemoteVerificationEmailSender sender = new RemoteVerificationEmailSender(
                httpSender, new ObjectMapper(), "https://scholarmatch-server-production.up.railway.app");

        sender.requestVerificationCode("ada@example.com");

        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpSender).send(captor.capture());
        assertEquals(
                "https://scholarmatch-server-production.up.railway.app/api/auth/request-verification-code",
                captor.getValue().uri().toString());
        assertTrue(captor.getValue().bodyPublisher().isPresent());
    }

    @Test
    void testNonSuccessResponseFails() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(500, "{}"));
        final RemoteVerificationEmailSender sender = new RemoteVerificationEmailSender(
                httpSender, new ObjectMapper(), "https://scholarmatch-server-production.up.railway.app");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.requestVerificationCode("ada@example.com"));

        assertTrue(exception.getMessage().contains("HTTP 500"));
    }

    @Test
    void testBelow200StatusCodeFails() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(100, "{}"));
        final RemoteVerificationEmailSender sender = new RemoteVerificationEmailSender(
                httpSender, new ObjectMapper(), "https://scholarmatch-server-production.up.railway.app");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.requestVerificationCode("ada@example.com"));

        assertTrue(exception.getMessage().contains("HTTP 100"));
    }

    @Test
    void testInterruptedDeliveryIsTranslatedAndInterruptFlagRestored() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenThrow(new InterruptedException("interrupted"));
        final RemoteVerificationEmailSender sender = new RemoteVerificationEmailSender(
                httpSender, new ObjectMapper(), "https://example.test");

        try {
            final ExternalServiceException exception = assertThrows(
                    ExternalServiceException.class,
                    () -> sender.requestVerificationCode("ada@example.com"));
            assertEquals("Email delivery was interrupted.", exception.getMessage());
            assertTrue(Thread.currentThread().isInterrupted());
        } finally {
            Thread.interrupted();
        }
    }

    @Test
    void testJsonFailureIsTranslated() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.createObjectNode()).thenReturn(new ObjectMapper().createObjectNode());
        when(objectMapper.writeValueAsString(any())).thenThrow(
                new JsonProcessingException("bad json") { });
        final RemoteVerificationEmailSender sender = new RemoteVerificationEmailSender(
                httpSender, objectMapper, "https://example.test");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.requestVerificationCode("ada@example.com"));

        assertEquals("Unable to build the verification request.", exception.getMessage());
    }

    @Test
    void testIoFailureIsTranslated() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenThrow(new IOException("offline"));
        final RemoteVerificationEmailSender sender = new RemoteVerificationEmailSender(
                httpSender, new ObjectMapper(), "https://example.test");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.requestVerificationCode("ada@example.com"));

        assertEquals("Unable to contact the email service.", exception.getMessage());
    }
}
