package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scholarmatch.frameworks.data_access_object.http.HttpSender;
import com.scholarmatch.frameworks.data_access_object.http.HttpSenderResponse;
import com.scholarmatch.frameworks.data_access_object.http.JdkHttpSender;
import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.exception.ExternalServiceException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

/**
 * Resend HTTP adapter for registration verification messages.
 */
public final class ResendVerificationEmailSender
        implements VerificationEmailSenderDataAccessInterface {

    private static final URI SEND_EMAIL_URI = URI.create("https://api.resend.com/emails");
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(20);

    private final HttpSender httpSender;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String fromEmail;

    /**
     * Creates the production sender from environment-backed credentials.
     *
     * @param apiKey the Resend API key
     * @param fromEmail a verified Resend sender
     */
    public ResendVerificationEmailSender(final String apiKey, final String fromEmail) {
        this(
                new JdkHttpSender(HttpClient.newBuilder().connectTimeout(REQUEST_TIMEOUT).build()),
                new ObjectMapper(),
                apiKey,
                fromEmail);
    }

    ResendVerificationEmailSender(
            final HttpSender httpSender,
            final ObjectMapper objectMapper,
            final String apiKey,
            final String fromEmail) {
        this.httpSender = httpSender;
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.fromEmail = fromEmail;
    }

    @Override
    public void sendVerificationCode(final String email, final String code) {
        if (this.apiKey == null || this.apiKey.isBlank()
                || this.fromEmail == null || this.fromEmail.isBlank()) {
            throw new ExternalServiceException(
                    "Email verification is not configured. Set RESEND_API_KEY and RESEND_FROM_EMAIL.");
        }
        final ObjectNode body = this.objectMapper.createObjectNode();
        body.put("from", this.fromEmail);
        body.putArray("to").add(email);
        body.put("subject", "ScholarMatch registration verification code");
        body.put("text", "Your ScholarMatch verification code is " + code
                + ". It expires in 10 minutes.");
        try {
            final HttpRequest request = HttpRequest.newBuilder(SEND_EMAIL_URI)
                    .timeout(REQUEST_TIMEOUT)
                    .header("Authorization", "Bearer " + this.apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            this.objectMapper.writeValueAsString(body)))
                    .build();
            final HttpSenderResponse response = this.httpSender.send(request);
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new ExternalServiceException(
                        "Unable to send the verification email (HTTP "
                                + response.statusCode() + ").");
            }
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new ExternalServiceException("Email delivery was interrupted.", exception);
        } catch (final JsonProcessingException exception) {
            throw new ExternalServiceException("Unable to build the verification email.", exception);
        } catch (final IOException exception) {
            throw new ExternalServiceException("Unable to contact the email service.", exception);
        }
    }
}
