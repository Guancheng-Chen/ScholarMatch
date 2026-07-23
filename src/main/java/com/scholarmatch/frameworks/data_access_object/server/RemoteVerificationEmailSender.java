package com.scholarmatch.frameworks.data_access_object.server;

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
 * Asks the ScholarMatch server to generate and email a registration verification code. The
 * server owns code generation, storage, expiry, and comparison — the desktop client never
 * sees the Resend credentials or the code itself before the user types it back in.
 */
public final class RemoteVerificationEmailSender
        implements VerificationEmailSenderDataAccessInterface {

    private static final String REQUEST_VERIFICATION_CODE_PATH = "/api/auth/request-verification-code";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(20);

    private final HttpSender httpSender;
    private final ObjectMapper objectMapper;
    private final URI requestVerificationCodeUri;

    /**
     * Creates the production sender that posts to the ScholarMatch server.
     *
     * @param serverBaseUrl the ScholarMatch REST API base URL
     */
    public RemoteVerificationEmailSender(final String serverBaseUrl) {
        this(
                new JdkHttpSender(HttpClient.newBuilder().connectTimeout(REQUEST_TIMEOUT).build()),
                new ObjectMapper(),
                serverBaseUrl);
    }

    RemoteVerificationEmailSender(
            final HttpSender httpSender,
            final ObjectMapper objectMapper,
            final String serverBaseUrl) {
        this.httpSender = httpSender;
        this.objectMapper = objectMapper;
        this.requestVerificationCodeUri =
                URI.create(serverBaseUrl.replaceAll("/$", "") + REQUEST_VERIFICATION_CODE_PATH);
    }

    @Override
    public void requestVerificationCode(final String email) {
        final ObjectNode body = this.objectMapper.createObjectNode();
        body.put("email", email);
        try {
            final HttpRequest request = HttpRequest.newBuilder(this.requestVerificationCodeUri)
                    .timeout(REQUEST_TIMEOUT)
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
            throw new ExternalServiceException("Unable to build the verification request.", exception);
        } catch (final IOException exception) {
            throw new ExternalServiceException("Unable to contact the email service.", exception);
        }
    }
}
