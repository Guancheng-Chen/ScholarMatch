package com.scholarmatch.frameworks.data_access_object.localMockServer;

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

class ResendEmailChangeCodeSenderTest {

    @Test
    void testProductionConstructorCanBeCreated() {
        assertTrue(new ResendEmailChangeCodeSender("re_key", "from@example.com")
                instanceof ResendEmailChangeCodeSender);
    }

    @Test
    void testPostsJsonToResendWithSubjectAndCode() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(200, "{}"));
        final ResendEmailChangeCodeSender sender = new ResendEmailChangeCodeSender(
                httpSender, new ObjectMapper(), "re_key", "from@example.com");

        sender.sendCode("ada@example.com", "123456");

        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpSender).send(captor.capture());
        assertEquals("https://api.resend.com/emails", captor.getValue().uri().toString());
        assertTrue(captor.getValue().bodyPublisher().isPresent());
    }

    @Test
    void testMissingApiKeyFailsWithoutContactingResend() {
        final HttpSender httpSender = mock(HttpSender.class);
        final ResendEmailChangeCodeSender sender = new ResendEmailChangeCodeSender(
                httpSender, new ObjectMapper(), " ", "from@example.com");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.sendCode("ada@example.com", "123456"));

        assertTrue(exception.getMessage().contains("RESEND_API_KEY"));
    }

    @Test
    void testMissingFromEmailFailsWithoutContactingResend() {
        final HttpSender httpSender = mock(HttpSender.class);
        final ResendEmailChangeCodeSender sender = new ResendEmailChangeCodeSender(
                httpSender, new ObjectMapper(), "re_key", null);

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.sendCode("ada@example.com", "123456"));

        assertTrue(exception.getMessage().contains("RESEND_FROM_EMAIL"));
    }

    @Test
    void testNonSuccessResponseFails() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenReturn(new HttpSenderResponse(500, "{}"));
        final ResendEmailChangeCodeSender sender = new ResendEmailChangeCodeSender(
                httpSender, new ObjectMapper(), "re_key", "from@example.com");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.sendCode("ada@example.com", "123456"));

        assertTrue(exception.getMessage().contains("HTTP 500"));
    }

    @Test
    void testInterruptedDeliveryIsTranslatedAndInterruptFlagRestored() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenThrow(new InterruptedException("interrupted"));
        final ResendEmailChangeCodeSender sender = new ResendEmailChangeCodeSender(
                httpSender, new ObjectMapper(), "re_key", "from@example.com");

        try {
            final ExternalServiceException exception = assertThrows(
                    ExternalServiceException.class,
                    () -> sender.sendCode("ada@example.com", "123456"));
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
        final ResendEmailChangeCodeSender sender = new ResendEmailChangeCodeSender(
                httpSender, objectMapper, "re_key", "from@example.com");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.sendCode("ada@example.com", "123456"));

        assertEquals("Unable to build the verification email.", exception.getMessage());
    }

    @Test
    void testIoFailureIsTranslated() throws Exception {
        final HttpSender httpSender = mock(HttpSender.class);
        when(httpSender.send(any())).thenThrow(new IOException("offline"));
        final ResendEmailChangeCodeSender sender = new ResendEmailChangeCodeSender(
                httpSender, new ObjectMapper(), "re_key", "from@example.com");

        final ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> sender.sendCode("ada@example.com", "123456"));

        assertEquals("Unable to contact the email service.", exception.getMessage());
    }
}
