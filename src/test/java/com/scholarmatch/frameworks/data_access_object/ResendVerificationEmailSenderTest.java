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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ResendVerificationEmailSenderTest {

    @Test
    void testSendsBearerAuthenticatedJsonRequest() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{\"id\":\"email-1\"}"));
        final ResendVerificationEmailSender sender = new ResendVerificationEmailSender(
                httpSender, new ObjectMapper(), "re_test", "ScholarMatch <verify@example.com>");

        sender.sendVerificationCode("ada@example.com", "123456");

        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpSender).send(captor.capture());
        assertEquals("https://api.resend.com/emails", captor.getValue().uri().toString());
        assertEquals("Bearer re_test", captor.getValue().headers()
                .firstValue("Authorization").orElseThrow());
        assertTrue(captor.getValue().bodyPublisher().isPresent());
    }

    @Test
    void testMissingConfigurationFailsBeforeHttp() {
        final ResendVerificationEmailSender sender = new ResendVerificationEmailSender(
                mock(HttpSender.class), new ObjectMapper(), "", "");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.sendVerificationCode("ada@example.com", "123456"));

        assertTrue(exception.getMessage().contains("RESEND_API_KEY"));
    }

    @Test
    void testNonSuccessResponseFails() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(403, "{}"));
        final ResendVerificationEmailSender sender = new ResendVerificationEmailSender(
                httpSender, new ObjectMapper(), "re_test", "verify@example.com");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.sendVerificationCode("ada@example.com", "123456"));

        assertTrue(exception.getMessage().contains("HTTP 403"));
    }
}
