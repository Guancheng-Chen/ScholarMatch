package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CreatePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadPostingsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ApplyToPostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.AcceptApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DeclineApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMyApplicationsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ClosePostingDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.register.RegisterAccountData;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;
import com.scholarmatch.usecase.load_postings.PostingScope;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP implementation of all server-facing data access interfaces.
 *
 * <p>All external API calls (embedding, Cloudinary, DB) are handled by the
 * server. This class is the single point of contact with the REST API.
 */
public final class ServerRepository
        implements LoginDataAccessInterface,
        RegisterDataAccessInterface,
        RecommendDataAccessInterface,
        ConnectDataAccessInterface,
        DislikeDataAccessInterface,
        LoadMatchesDataAccessInterface,
        LoadProfileDataAccessInterface,
        UpdateProfileDataAccessInterface,
        DeleteAccountDataAccessInterface,
        SendMessageDataAccessInterface,
        LoadMessageDataAccessInterface,
        CreatePostingDataAccessInterface,
        LoadPostingsDataAccessInterface,
        ApplyToPostingDataAccessInterface,
        AcceptApplicationDataAccessInterface,
        DeclineApplicationDataAccessInterface,
        LoadMyApplicationsDataAccessInterface,
        ClosePostingDataAccessInterface {

    private final String baseUrl;
    private final CurrentUserProviderInterface session;
    private final HttpClient http;
    private final ObjectMapper mapper;
    private final InstitutionCatalogDataAccessInterface institutionCatalog;

    /**
     * Constructs a ServerRepository.
     *
     * @param baseUrl the server base URL (e.g. https://scholarmatch-server-production.up.railway.app)
     * @param session provides the JWT token for authenticated requests
     */
    public ServerRepository(
            final String baseUrl,
            final CurrentUserProviderInterface session) {
        this(baseUrl, session, new ClasspathInstitutionCatalogRepository());
    }

    public ServerRepository(
            final String baseUrl,
            final CurrentUserProviderInterface session,
            final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this.baseUrl = baseUrl.replaceAll("/$", "");
        this.session = session;
        this.http = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.institutionCatalog = institutionCatalog;
    }

    // ── Auth ─────────────────────────────────────────────────────────────────

    @Override
    public AuthResult login(final String email, final String password) {
        final String body = toJson(Map.of("email", email, "password", password));
        final JsonNode node = post("/api/auth/login", body, false);
        return new AuthResult(
                node.get("token").asText(),
                node.get("scholarId").asText(),
                node.get("name").asText());
    }

    @Override
    public AuthResult register(final RegisterAccountData data) {
        // Registration only collects the account-creation essentials; every other profile
        // field is filled in later from the Edit Profile screen once the user is in the app.
        final Map<String, Object> body = new HashMap<>();
        body.put("firstName", data.getFirstName());
        body.put("lastName", data.getLastName());
        body.put("email", data.getEmail());
        body.put("password", data.getPassword());
        body.put("code", data.getVerificationCode());

        final JsonNode node = post("/api/auth/register", toJson(body), false);
        return new AuthResult(
                node.get("token").asText(),
                node.get("scholarId").asText(),
                node.get("name").asText());
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
        return postingFromJson(post("/api/postings", toJson(body), true));
    }

    @Override
    public Posting closePosting(final String postingId) {
        return postingFromJson(post("/api/postings/" + postingId + "/close", "{}", true));
    }

    @Override
    public List<Posting> loadPostings(final PostingScope scope) {
        final JsonNode array = get("/api/postings?scope=" + scope.name());
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
        final JsonNode array = get("/api/postings?scope=MINE");
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
        final String body = toJson(Map.of("message", message == null ? "" : message));
        return applicationFromJson(post("/api/postings/" + postingId + "/apply", body, true));
    }

    @Override
    public PostingApplication acceptApplication(final String applicationId) {
        return applicationFromJson(post(
                "/api/postings/applications/" + applicationId + "/accept", "{}", true));
    }

    @Override
    public PostingApplication declineApplication(final String applicationId) {
        return applicationFromJson(post(
                "/api/postings/applications/" + applicationId + "/decline", "{}", true));
    }

    @Override
    public List<PostingApplication> getMyApplications() {
        final JsonNode array = get("/api/postings/applications/mine");
        final List<PostingApplication> result = new ArrayList<>();
        for (final JsonNode node : array) {
            result.add(applicationFromJson(node));
        }
        return result;
    }

    // ── Recommend ─────────────────────────────────────────────────────────────

    @Override
    public List<User> getRecommendations() {
        final JsonNode array = get("/api/recommend");
        final List<User> result = new ArrayList<>();
        for (final JsonNode node : array) {
            result.add(userFromJson(node));
        }
        return result;
    }

    // ── Connect ─────────────────────────────────────────────────────────────────

    @Override
    public boolean connect(final String connectedUserId) {
        final String body = toJson(Map.of("connectedScholarId", connectedUserId));
        final JsonNode node = post("/api/connect", body, true);
        return node.get("matched").asBoolean();
    }

    @Override
    public void dislike(final String dislikedUserId) {
        final String body = toJson(Map.of("dislikedScholarId", dislikedUserId));
        post("/api/dislike", body, true);
    }

    @Override
    public List<User> getMatches() {
        final JsonNode array = get("/api/matches");
        // Deduplicate by userId: the endpoint has been observed to list the same
        // matched user more than once (e.g. one row per side of the connection).
        final Map<String, User> matchesById = new LinkedHashMap<>();
        for (final JsonNode node : array) {
            final User user = userFromJson(node);
            matchesById.put(user.getUserId(), user);
        }
        return new ArrayList<>(matchesById.values());
    }

    // ── Profile ───────────────────────────────────────────────────────────────

    @Override
    public User getProfile() {
        return userFromJson(get("/api/profile"));
    }

    @Override
    public User updateProfile(final UpdateProfileInputData data) {
        final Map<String, Object> body = new HashMap<>();
        if (data.getEmail() != null) {
            body.put("email", data.getEmail());
        }
        if (data.getInstitution() != null) {
            body.put("institution", data.getInstitution());
        }
        if (data.getAcademicLevel() != null) {
            body.put("academicLevel", data.getAcademicLevel());
        }
        if (data.getResearchField() != null) {
            body.put("researchField", data.getResearchField());
        }
        if (data.getLookingFor() != null) {
            body.put("lookingFor", data.getLookingFor());
        }
        if (data.getCollaborationDescription() != null) {
            body.put("collaborationDescription", data.getCollaborationDescription());
        }
        if (data.getResearchDescription() != null) {
            body.put("researchDescription", data.getResearchDescription());
        }
        if (data.getWeeklyAvailabilityHours() != null) {
            body.put("weeklyAvailabilityHours", data.getWeeklyAvailabilityHours());
        }
        if (data.getFundingStatus() != null) {
            body.put("fundingStatus", data.getFundingStatus());
        }
        if (data.getPhoneNumber() != null) {
            body.put("phoneNumber", data.getPhoneNumber());
        }
        if (data.getResearchInterests() != null) {
            body.put("researchInterests", data.getResearchInterests());
        }
        if (data.getHIndex() != null) {
            body.put("hIndex", data.getHIndex());
        }
        if (data.getTotalCitations() != null) {
            body.put("totalCitations", data.getTotalCitations());
        }

        final List<Map<String, String>> papers = new ArrayList<>();
        for (final Publication p : data.getPublications()) {
            papers.add(Map.of("title", p.getTitle(), "doi", p.getDoi()));
        }
        body.put("papers", papers);

        final List<Map<String, String>> educations = new ArrayList<>();
        for (final Education ed : data.getEducations()) {
            educations.add(Map.of(
                    "school", ed.getInstitution(),
                    "degree", ed.getDegreeType().name(),
                    "field", ""));
        }
        body.put("educations", educations);

        return userFromJson(put("/api/profile", toJson(body)));
    }

    @Override
    public void deleteAccount() {
        delete("/api/profile");
    }

    // ── Messages ──────────────────────────────────────────────────────────────

    @Override
    public Message sendMessage(final String receiverId, final String content) {
        final String body = toJson(Map.of("receiverId", receiverId, "content", content));
        return messageFromJson(post("/api/messages", body, true));
    }

    @Override
    public List<Message> getConversation(final String otherUserId) {
        final JsonNode array = get("/api/messages/" + otherUserId);
        final List<Message> result = new ArrayList<>();
        for (final JsonNode node : array) {
            result.add(messageFromJson(node));
        }
        return result;
    }

    private Posting postingFromJson(final JsonNode node) {
        final ResearchField researchField = safeParseEnum(
                ResearchField.class,
                node.has("researchField") ? node.get("researchField").asText(null) : null,
                ResearchField.OTHER);
        final CollaborationType collaborationType = safeParseEnum(
                CollaborationType.class,
                node.has("collaborationType")
                        ? node.get("collaborationType").asText(null) : null,
                CollaborationType.INTEREST_SHARING);
        final JsonNode capacityNode = node.has("capacity")
                ? node.get("capacity") : node.get("maxApplicants");
        final Integer capacity = capacityNode == null || capacityNode.isNull()
                ? null : capacityNode.asInt();
        final PostingStatus status = safeParseEnum(
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
        final PostingApplicationStatus status = safeParseEnum(
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

    private Message messageFromJson(final JsonNode node) {
        return new Message(
                node.get("messageId").asText(),
                node.get("senderId").asText(),
                node.get("receiverId").asText(),
                node.get("content").asText(),
                LocalDateTime.parse(node.get("sentAt").asText()));
    }

    // ── HTTP helpers ──────────────────────────────────────────────────────────

    private JsonNode get(final String path) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Authorization", "Bearer " + session.getToken())
                    .GET()
                    .build();
            return parseResponse(http.send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (final DataAccessException e) {
            throw e;
        } catch (final Exception e) {
            throw new ExternalServiceException(describeNetworkFailure(e), e);
        }
    }

    private JsonNode post(final String path, final String body, final boolean authenticated) {
        try {
            final HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body));
            if (authenticated) {
                builder.header("Authorization", "Bearer " + session.getToken());
            }
            return parseResponse(http.send(builder.build(), HttpResponse.BodyHandlers.ofString()));
        } catch (final DataAccessException e) {
            throw e;
        } catch (final Exception e) {
            throw new ExternalServiceException(describeNetworkFailure(e), e);
        }
    }

    private JsonNode put(final String path, final String body) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + session.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            return parseResponse(http.send(request, HttpResponse.BodyHandlers.ofString()));
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
    private void delete(final String path) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .header("Authorization", "Bearer " + session.getToken())
                    .DELETE()
                    .build();
            final HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            final int statusCode = response.statusCode();
            if (statusCode >= 400) {
                String error = null;
                final String responseBody = response.body();
                if (responseBody != null && !responseBody.isBlank()) {
                    try {
                        final JsonNode node = mapper.readTree(responseBody);
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

    /**
     * Translates a low-level transport failure (a thrown Exception from HttpClient#send)
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

    private JsonNode parseResponse(final HttpResponse<String> response) {
        try {
            final JsonNode node = mapper.readTree(response.body());
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

    private String toJson(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (final Exception e) {
            throw new ExternalServiceException("JSON serialization failed", e);
        }
    }

    // ── UserDto → User mapping ──────────────────────────────────────────

    private User userFromJson(final JsonNode node) {
        final AcademicLevel academicLevel = safeParseEnum(
                AcademicLevel.class,
                node.has("academicLevel") ? node.get("academicLevel").asText(null) : null,
                AcademicLevel.GRADUATE_STUDENT);

        final CollaborationType lookingFor = safeParseEnum(
                CollaborationType.class,
                node.has("lookingFor") ? node.get("lookingFor").asText(null) : null,
                CollaborationType.INTEREST_SHARING);

        final ResearchField researchField = safeParseEnum(
                ResearchField.class,
                node.has("researchField") ? node.get("researchField").asText(null) : null,
                ResearchField.OTHER);

        final FundingStatus fundingStatus = safeParseEnum(
                FundingStatus.class,
                node.has("fundingStatus") ? node.get("fundingStatus").asText(null) : null,
                FundingStatus.OTHER);

        final Integer weeklyAvailabilityHours =
                node.has("weeklyAvailabilityHours") && !node.get("weeklyAvailabilityHours").isNull()
                        ? node.get("weeklyAvailabilityHours").asInt()
                        : null;

        final Institution institution = this.institutionCatalog.findById(
                node.has("institution") ? node.get("institution").asText(null) : null);

        final String email = node.has("email") ? node.get("email").asText("") : "";
        final boolean academicEmailVerified = node.has("academicEmailVerified")
                ? node.get("academicEmailVerified").asBoolean()
                : false;

        final User user = new User(
                node.get("scholarId").asText(),
                node.get("firstName").asText(),
                node.get("lastName").asText(),
                email,
                node.has("phoneNumber") ? node.get("phoneNumber").asText("") : "",
                institution,
                academicLevel,
                researchField,
                lookingFor,
                node.has("collaborationDescription") ? node.get("collaborationDescription").asText("") : "",
                node.has("researchDescription") ? node.get("researchDescription").asText("") : "",
                weeklyAvailabilityHours,
                fundingStatus,
                "",
                academicEmailVerified ? EmailAccountType.ACADEMIC : EmailAccountType.REGULAR);

        if (node.has("hIndex") && !node.get("hIndex").isNull()) {
            user.sethIndex(node.get("hIndex").asInt());
        }
        if (node.has("totalCitations") && !node.get("totalCitations").isNull()) {
            user.setTotalCitations(node.get("totalCitations").asInt());
        }

        if (node.has("researchInterests")) {
            for (final JsonNode interest : node.get("researchInterests")) {
                user.addResearchInterest(interest.asText());
            }
        }

        if (node.has("papers")) {
            for (final JsonNode paper : node.get("papers")) {
                user.addPublication(new Publication(
                        paper.has("doi") ? paper.get("doi").asText("") : "",
                        paper.has("title") ? paper.get("title").asText("") : "",
                        0, 0));
            }
        }

        if (node.has("educations")) {
            for (final JsonNode ed : node.get("educations")) {
                final DegreeType degree = safeParseEnum(
                        DegreeType.class,
                        ed.has("degree") ? ed.get("degree").asText(null) : null,
                        DegreeType.BACHELOR);
                // Server doesn't send year/month fields yet; placeholder until that's wired up.
                user.addEducation(new Education(
                        ed.has("school") ? ed.get("school").asText("") : "",
                        degree, 0, Month.JANUARY, null, null));
            }
        }

        return user;
    }

    private <T extends Enum<T>> T safeParseEnum(
            final Class<T> enumClass, final String value, final T defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Enum.valueOf(enumClass, value);
        } catch (final IllegalArgumentException ignored) {
            return defaultValue;
        }
    }
}
