package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * HTTP implementation of author and paper lookup using the Semantic Scholar Graph API.
 */
public final class SemanticScholarGateway implements UserAPIGatewayInterface {

    private static final String API_BASE_URL = "https://api.semanticscholar.org/graph/v1";
    private static final String AUTHOR_FIELDS = "authorId,name,affiliations,paperCount,hIndex,citationCount";
    private static final String PAPER_FIELDS = "title,year,citationCount,externalIds";
    private static final String API_KEY_ENVIRONMENT_VARIABLE = "SEMANTIC_SCHOLAR_API_KEY";
    private static final int MAX_AUTHOR_CANDIDATES = 20;
    private static final int MAX_PAPERS = 50;
    private static final long RATE_LIMIT_RETRY_DELAY_MILLIS = 1_000;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    /**
     * Constructs a Semantic Scholar gateway with standard HTTP and JSON clients.
     */
    public SemanticScholarGateway() {
        this(
                HttpClient.newBuilder().connectTimeout(REQUEST_TIMEOUT).build(),
                new ObjectMapper(),
                System.getenv(API_KEY_ENVIRONMENT_VARIABLE));
    }

    SemanticScholarGateway(final HttpClient httpClient, final ObjectMapper objectMapper) {
        this(httpClient, objectMapper, null);
    }

    SemanticScholarGateway(
            final HttpClient httpClient,
            final ObjectMapper objectMapper,
            final String apiKey) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
    }

    @Override
    public List<AuthorCandidateData> searchAuthors(final String authorName) {
        final String encodedName = URLEncoder.encode(authorName, StandardCharsets.UTF_8);
        final String uri = API_BASE_URL + "/author/search?query=" + encodedName
                + "&limit=" + MAX_AUTHOR_CANDIDATES + "&fields=" + AUTHOR_FIELDS;
        final JsonNode root = this.sendGet(uri);
        final List<AuthorCandidateData> candidates = new ArrayList<>();
        for (final JsonNode node : root.path("data")) {
            candidates.add(new AuthorCandidateData(
                    node.path("authorId").asText(),
                    node.path("name").asText(),
                    this.readStringList(node.path("affiliations")),
                    this.readNullableInteger(node, "paperCount"),
                    this.readNullableInteger(node, "hIndex"),
                    this.readNullableInteger(node, "citationCount")));
        }
        return candidates;
    }

    @Override
    public List<Publication> getAuthorPapers(final String authorId) {
        final String encodedAuthorId = URLEncoder.encode(authorId, StandardCharsets.UTF_8);
        final String uri = API_BASE_URL + "/author/" + encodedAuthorId + "/papers?limit=" + MAX_PAPERS
                + "&fields=" + PAPER_FIELDS;
        final JsonNode root = this.sendGet(uri);
        final List<Publication> publications = new ArrayList<>();
        for (final JsonNode node : root.path("data")) {
            publications.add(new Publication(
                    this.readDoi(node),
                    node.path("title").asText(),
                    node.path("year").asInt(),
                    node.path("citationCount").asInt()));
        }
        return publications;
    }

    private JsonNode sendGet(final String uri) {
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(URI.create(uri))
                .timeout(REQUEST_TIMEOUT)
                .header("Accept", "application/json");
        if (this.apiKey != null && !this.apiKey.isBlank()) {
            requestBuilder.header("x-api-key", this.apiKey);
        }
        final HttpRequest request = requestBuilder.GET().build();
        try {
            HttpResponse<String> response = this.httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 429) {
                Thread.sleep(RATE_LIMIT_RETRY_DELAY_MILLIS);
                response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }
            if (response.statusCode() == 404) {
                final ObjectNode emptyResponse = this.objectMapper.createObjectNode();
                emptyResponse.putArray("data");
                return emptyResponse;
            }
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new ExternalServiceException(
                        "Semantic Scholar returned HTTP " + response.statusCode() + ".");
            }
            return this.objectMapper.readTree(response.body());
        } catch (final InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new ExternalServiceException("Semantic Scholar request was interrupted.", exception);
        } catch (final IOException exception) {
            throw new ExternalServiceException("Unable to read the Semantic Scholar response.", exception);
        }
    }

    private List<String> readStringList(final JsonNode arrayNode) {
        final List<String> values = new ArrayList<>();
        if (arrayNode.isArray()) {
            for (final JsonNode value : arrayNode) {
                values.add(value.asText());
            }
        }
        return values;
    }

    private Integer readNullableInteger(final JsonNode node, final String fieldName) {
        final JsonNode value = node.get(fieldName);
        return value == null || value.isNull() ? null : value.asInt();
    }

    private String readDoi(final JsonNode paperNode) {
        final JsonNode doi = paperNode.path("externalIds").get("DOI");
        return doi == null || doi.isNull() ? "" : doi.asText();
    }
}
