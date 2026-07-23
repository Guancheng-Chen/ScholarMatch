package com.scholarmatch.frameworks.data_access_object.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.usecase.data_access_interface.AcceptApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ApplyToPostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ClosePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CreatePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DeclineApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMyApplicationsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadPostingsDataAccessInterface;
import com.scholarmatch.usecase.load_postings.PostingScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP implementation of the recruitment feature (postings and applications) — the
 * "postings" actor's slice of what used to be ServerRepository. By far the biggest of the
 * five gateways because the recruitment feature itself has the most operations (create/close/
 * load postings, apply, accept/decline applications, load my applications) — still one
 * cohesive actor, unlike the god class it replaces.
 */
public final class PostingGateway
        implements CreatePostingDataAccessInterface, ClosePostingDataAccessInterface,
        LoadPostingsDataAccessInterface, ApplyToPostingDataAccessInterface,
        AcceptApplicationDataAccessInterface, DeclineApplicationDataAccessInterface,
        LoadMyApplicationsDataAccessInterface {

    private final ServerHttpClient http;

    public PostingGateway(final ServerHttpClient http) {
        this.http = http;
    }

    @Override
    public Posting createPosting(
            final String title,
            final String description,
            final ResearchField researchField,
            final CollaborationType collaborationType,
            final Integer capacity) {
        final Map<String, Object> body = new HashMap<>();
        body.put("title", title);
        body.put("description", description);
        if (researchField != null) {
            body.put("researchField", researchField.name());
        }
        if (collaborationType != null) {
            body.put("collaborationType", collaborationType.name());
        }
        body.put("capacity", capacity);
        return postingFromJson(this.http.post("/api/postings", this.http.toJson(body), true));
    }

    @Override
    public Posting closePosting(final String postingId) {
        return postingFromJson(this.http.post("/api/postings/" + postingId + "/close", "{}", true));
    }

    @Override
    public List<Posting> loadPostings(final PostingScope scope) {
        final JsonNode array = this.http.get("/api/postings?scope=" + scope.name());
        final List<Posting> result = new ArrayList<>();
        for (final JsonNode node : array) {
            result.add(postingFromJson(node));
        }
        return result;
    }

    @Override
    public Map<String, List<PostingApplication>> loadApplicationsForOwnedPostings(
            final PostingScope scope,
            final List<Posting> postings) {
        if (scope != PostingScope.MINE) {
            return Map.of();
        }
        final JsonNode array = this.http.get("/api/postings?scope=MINE");
        final Map<String, List<PostingApplication>> result = new LinkedHashMap<>();
        for (final JsonNode node : array) {
            final List<PostingApplication> applications = new ArrayList<>();
            if (node.has("applications") && node.get("applications").isArray()) {
                for (final JsonNode applicationNode : node.get("applications")) {
                    applications.add(applicationFromJson(applicationNode));
                }
            }
            result.put(node.get("postingId").asText(), applications);
        }
        return result;
    }

    @Override
    public PostingApplication applyToPosting(final String postingId, final String message) {
        final String body = this.http.toJson(Map.of("message", message == null ? "" : message));
        return applicationFromJson(this.http.post("/api/postings/" + postingId + "/apply", body, true));
    }

    @Override
    public PostingApplication acceptApplication(final String applicationId) {
        return applicationFromJson(this.http.post(
                "/api/postings/applications/" + applicationId + "/accept", "{}", true));
    }

    @Override
    public PostingApplication declineApplication(final String applicationId) {
        return applicationFromJson(this.http.post(
                "/api/postings/applications/" + applicationId + "/decline", "{}", true));
    }

    @Override
    public List<PostingApplication> getMyApplications() {
        final JsonNode array = this.http.get("/api/postings/applications/mine");
        final List<PostingApplication> result = new ArrayList<>();
        for (final JsonNode node : array) {
            result.add(applicationFromJson(node));
        }
        return result;
    }

    private Posting postingFromJson(final JsonNode node) {
        final ResearchField researchField = JsonEnumSupport.safeParseEnum(
                ResearchField.class,
                node.has("researchField") ? node.get("researchField").asText(null) : null,
                ResearchField.OTHER);
        final CollaborationType collaborationType = JsonEnumSupport.safeParseEnum(
                CollaborationType.class,
                node.has("collaborationType")
                        ? node.get("collaborationType").asText(null) : null,
                CollaborationType.INTEREST_SHARING);
        final JsonNode capacityNode = node.has("capacity")
                ? node.get("capacity") : node.get("maxApplicants");
        final Integer capacity = capacityNode == null || capacityNode.isNull()
                ? null : capacityNode.asInt();
        final PostingStatus status = JsonEnumSupport.safeParseEnum(
                PostingStatus.class,
                node.has("status") ? node.get("status").asText(null) : null,
                PostingStatus.OPEN);
        return new Posting(
                node.get("postingId").asText(),
                node.get("posterUserId").asText(),
                node.get("title").asText(),
                node.has("description") ? node.get("description").asText("") : "",
                researchField,
                collaborationType,
                capacity,
                node.get("applicantCount").asInt(),
                node.has("acceptedCount") ? node.get("acceptedCount").asInt() : 0,
                status,
                LocalDateTime.parse(node.get("createdAt").asText()));
    }

    private PostingApplication applicationFromJson(final JsonNode node) {
        final PostingApplicationStatus status = JsonEnumSupport.safeParseEnum(
                PostingApplicationStatus.class,
                node.has("status") ? node.get("status").asText(null) : null,
                PostingApplicationStatus.PENDING);
        return new PostingApplication(
                node.get("applicationId").asText(),
                node.get("postingId").asText(),
                node.get("applicantUserId").asText(),
                node.has("message") ? node.get("message").asText("") : "",
                status,
                LocalDateTime.parse(node.get("appliedAt").asText()),
                node.has("postingTitle") ? node.get("postingTitle").asText("") : "",
                node.has("applicantName") ? node.get("applicantName").asText("") : "");
    }
}
