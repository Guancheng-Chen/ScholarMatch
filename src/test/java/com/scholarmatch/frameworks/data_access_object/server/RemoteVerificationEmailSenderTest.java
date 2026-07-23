package com.scholarmatch.frameworks.data_access_object.server;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RemoteVerificationEmailSenderTest {

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
}
