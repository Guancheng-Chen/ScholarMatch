package com.scholarmatch.frameworks.data_access_object.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholarmatch.frameworks.data_access_object.http.HttpSender;
import com.scholarmatch.frameworks.data_access_object.http.HttpSenderResponse;
import com.scholarmatch.frameworks.data_access_object.http.JdkHttpSender;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.exception.DataAccessException;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

/**
 * Shared HTTP transport for every server-facing gateway (AuthGateway, ProfileGateway,
 * MatchingGateway, MessagingGateway, PostingGateway) — the base URL, bearer-token attachment,
 * and JSON error translation are the same regardless of which feature is calling the server,
 * so this is the one piece of the old ServerRepository god class that legitimately stays
 * shared rather than being split per feature.
 *
 * <p>Depends on {@link HttpSender} rather than {@link HttpClient} directly — the same seam
 * SemanticScholarGateway and RemoteVerificationEmailSender already use — so tests can mock the
 * transport instead of standing up a real server.
 */
public final class ServerHttpClient {

    private final String baseUrl;
    private final CurrentUserProviderInterface session;
    private final HttpSender httpSender;
    private final ObjectMapper mapper;

    public ServerHttpClient(final String baseUrl, final CurrentUserProviderInterface session) {
        this(baseUrl, session, new JdkHttpSender(HttpClient.newHttpClient()));
    }

    ServerHttpClient(final String baseUrl, final CurrentUserProviderInterface session, final HttpSender httpSender) {
        this.baseUrl = baseUrl.replaceAll("/$", "");
        this.session = session;
        this.httpSender = httpSender;
        this.mapper = new ObjectMapper();
    }

    public JsonNode get(final String path) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseUrl + path))
                    .header("Authorization", "Bearer " + this.session.getToken())
                    .GET()
                    .build();
            return parseResponse(this.httpSender.send(request));
        } catch (final DataAccessException e) {
            throw e;
        } catch (final Exception e) {
            throw new ExternalServiceException(describeNetworkFailure(e), e);
        }
    }

    public JsonNode post(final String path, final String body, final boolean authenticated) {
        try {
            final HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseUrl + path))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body));
            if (authenticated) {
                builder.header("Authorization", "Bearer " + this.session.getToken());
            }
            return parseResponse(this.httpSender.send(builder.build()));
        } catch (final DataAccessException e) {
            throw e;
        } catch (final Exception e) {
            throw new ExternalServiceException(describeNetworkFailure(e), e);
        }
    }

    public JsonNode put(final String path, final String body) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseUrl + path))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this.session.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            return parseResponse(this.httpSender.send(request));
        } catch (final DataAccessException e) {
            throw e;
        } catch (final Exception e) {
            throw new ExternalServiceException(describeNetworkFailure(e), e);
        }
    }

    /**
     * Sends an authenticated DELETE and discards the response body — used for endpoints
     * (like account deletion) that reply 204 No Content on success, which parseResponse
     * cannot handle since it always tries to parse the body as JSON.
     */
    public void delete(final String path) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.baseUrl + path))
                    .header("Authorization", "Bearer " + this.session.getToken())
                    .DELETE()
                    .build();
            final HttpSenderResponse response = this.httpSender.send(request);
            final int statusCode = response.statusCode();
            if (statusCode >= 400) {
                String error = null;
                final String responseBody = response.body();
                if (responseBody != null && !responseBody.isBlank()) {
                    try {
                        final JsonNode node = this.mapper.readTree(responseBody);
                        if (node.has("error")) {
                            error = node.get("error").asText();
                        }
                    } catch (final Exception ignored) {
                        // Body wasn't JSON (e.g. a gateway error page) — fall back below.
                    }
                }
                if (error == null) {
                    error = describeStatusCode(statusCode);
                }
                if (statusCode == 404) {
                    throw new ResourceNotFoundException(error);
                }
                if (statusCode >= 500) {
                    throw new ExternalServiceException(error);
                }
                throw new InvalidRequestException(error);
            }
        } catch (final DataAccessException e) {
            throw e;
        } catch (final Exception e) {
            throw new ExternalServiceException(describeNetworkFailure(e), e);
        }
    }

    public String toJson(final Object obj) {
        try {
            return this.mapper.writeValueAsString(obj);
        } catch (final Exception e) {
            throw new ExternalServiceException("JSON serialization failed", e);
        }
    }

    /**
     * Translates a low-level transport failure (a thrown Exception from HttpSender#send)
     * into a message a user can actually act on, instead of a raw Java exception message
     * (e.g. "nodename nor servname provided, or not known") or a bare status code.
     */
    private String describeNetworkFailure(final Exception e) {
        if (e instanceof java.net.UnknownHostException) {
            return "Could not reach the server — check your internet connection.";
        }
        if (e instanceof java.net.ConnectException) {
            return "Could not connect to the server. Check your internet connection and try again.";
        }
        if (e instanceof java.net.http.HttpTimeoutException || e instanceof java.net.SocketTimeoutException) {
            return "The request timed out. Check your internet connection and try again.";
        }
        if (e instanceof InterruptedException) {
            return "The request was interrupted. Please try again.";
        }
        if (e instanceof java.io.IOException) {
            return "A network error occurred. Check your internet connection and try again.";
        }
        return "Unable to reach the server. Please try again.";
    }

    /**
     * Translates an HTTP status code into a message a user can act on, for the (uncommon)
     * case where the server's error response has no "error" field to use verbatim.
     */
    private String describeStatusCode(final int statusCode) {
        return switch (statusCode) {
            case 400 -> "The request was invalid.";
            case 401 -> "You need to log in again.";
            case 403 -> "You don't have permission to do that.";
            case 404 -> "That wasn't found.";
            case 408 -> "The request timed out. Check your internet connection and try again.";
            case 429 -> "Too many requests — please wait a moment and try again.";
            default -> statusCode >= 500
                    ? "The server ran into a problem. Please try again later."
                    : "Something went wrong (error " + statusCode + ").";
        };
    }

    private JsonNode parseResponse(final HttpSenderResponse response) {
        try {
            final JsonNode node = this.mapper.readTree(response.body());
            final int statusCode = response.statusCode();
            if (statusCode >= 400) {
                final String error = node.has("error")
                        ? node.get("error").asText()
                        : describeStatusCode(statusCode);
                if (statusCode == 404) {
                    throw new ResourceNotFoundException(error);
                }
                if (statusCode >= 500) {
                    throw new ExternalServiceException(error);
                }
                throw new InvalidRequestException(error);
            }
            return node;
        } catch (final DataAccessException e) {
            throw e;
        } catch (final Exception e) {
            throw new ExternalServiceException(
                    "The server sent back an unexpected response. Please try again later.", e);
        }
    }
}
