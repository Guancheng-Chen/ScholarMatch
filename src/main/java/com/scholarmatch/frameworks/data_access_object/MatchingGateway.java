package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP implementation of recommendations, connect/dislike, and confirmed matches — the
 * "matching" actor's slice of what used to be ServerRepository.
 */
public final class MatchingGateway
        implements RecommendDataAccessInterface, ConnectDataAccessInterface,
        DislikeDataAccessInterface, LoadMatchesDataAccessInterface {

    private final ServerHttpClient http;
    private final ScholarUserMapper userMapper;

    public MatchingGateway(final ServerHttpClient http, final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this.http = http;
        this.userMapper = new ScholarUserMapper(institutionCatalog);
    }

    @Override
    public List<User> getRecommendations() {
        final JsonNode array = this.http.get("/api/recommend");
        final List<User> result = new ArrayList<>();
        for (final JsonNode node : array) {
            result.add(this.userMapper.fromJson(node));
        }
        return result;
    }

    // RecommendDataAccessInterface#getProfile has the exact same signature as
    // LoadProfileDataAccessInterface#getProfile (see that interface's javadoc for why they're
    // deliberately not shared) — the recommend use case needs the current user's own profile
    // to check completeness before fetching recommendations.
    @Override
    public User getProfile() {
        return this.userMapper.fromJson(this.http.get("/api/profile"));
    }

    @Override
    public boolean connect(final String connectedUserId) {
        final String body = this.http.toJson(Map.of("connectedScholarId", connectedUserId));
        final JsonNode node = this.http.post("/api/connect", body, true);
        return node.get("matched").asBoolean();
    }

    @Override
    public void dislike(final String dislikedUserId) {
        final String body = this.http.toJson(Map.of("dislikedScholarId", dislikedUserId));
        this.http.post("/api/dislike", body, true);
    }

    @Override
    public List<User> getMatches() {
        final JsonNode array = this.http.get("/api/matches");
        // Deduplicate by userId: the endpoint has been observed to list the same
        // matched user more than once (e.g. one row per side of the connection).
        final Map<String, User> matchesById = new LinkedHashMap<>();
        for (final JsonNode node : array) {
            final User user = this.userMapper.fromJson(node);
            matchesById.put(user.getUserId(), user);
        }
        return new ArrayList<>(matchesById.values());
    }
}
