package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.exception.DataAccessException;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.register.RegisterAccountData;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import com.sun.net.httpserver.HttpServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServerRepositoryTest {

    private HttpServer server;
    private CurrentUserProviderInterface session;
    private ServerRepository repo;
    private final AtomicInteger statusToReturn = new AtomicInteger(200);
    private final AtomicReference<String> bodyToReturn = new AtomicReference<>("{}");
    private final AtomicReference<String> lastRequestBody = new AtomicReference<>();
    private final AtomicReference<String> lastAuthHeader = new AtomicReference<>();
    private final AtomicReference<String> lastMethod = new AtomicReference<>();
    private final AtomicReference<String> lastPath = new AtomicReference<>();

    @BeforeEach
    void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            lastMethod.set(exchange.getRequestMethod());
            lastPath.set(exchange.getRequestURI().toString());
            lastAuthHeader.set(exchange.getRequestHeaders().getFirst("Authorization"));
            lastRequestBody.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
            final String responseBody = bodyToReturn.get();
            if (responseBody == null) {
                exchange.sendResponseHeaders(statusToReturn.get(), -1);
            } else {
                final byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(statusToReturn.get(), bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        });
        server.start();
        session = mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        repo = new ServerRepository(
                "http://127.0.0.1:" + server.getAddress().getPort(), session);
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    // ── auth ─────────────────────────────────────────────────────────────────

    @Test
    void testLoginParsesAuthResultAndSendsUnauthenticatedRequest() {
        bodyToReturn.set("{\"token\": \"jwt-1\", \"scholarId\": \"u-1\", \"name\": \"Ada Lovelace\"}");

        final AuthResult result = repo.login("ada@example.com", "pw");

        assertEquals("jwt-1", result.token());
        assertEquals("u-1", result.userId());
        assertEquals("Ada Lovelace", result.displayName());
        assertEquals("POST", lastMethod.get());
        assertEquals(null, lastAuthHeader.get());
        assertTrue(lastRequestBody.get().contains("ada@example.com"));
    }

    @Test
    void testRegisterParsesAuthResult() {
        bodyToReturn.set("{\"token\": \"jwt-2\", \"scholarId\": \"u-2\", \"name\": \"Jane Doe\"}");

        final AuthResult result = repo.register(new RegisterAccountData(
                "Jane", "Doe", "jane@mit.edu", "pw123456", EmailAccountType.ACADEMIC));

        assertEquals("jwt-2", result.token());
        assertEquals("u-2", result.userId());
        assertTrue(lastRequestBody.get().contains("jane@mit.edu"));
        assertTrue(lastRequestBody.get().contains("\"academicEmailVerified\":true"));
    }

    // ── recommend / connect / dislike / matches ─────────────────────────────

    @Test
    void testGetRecommendationsParsesUserArray() {
        bodyToReturn.set("[{\"scholarId\": \"u-1\", \"firstName\": \"Ada\", \"lastName\": \"Lovelace\"}]");

        final List<User> results = repo.getRecommendations();

        assertEquals(1, results.size());
        assertEquals("Ada", results.get(0).getFirstName());
        assertEquals("GET", lastMethod.get());
        assertEquals("Bearer test-token", lastAuthHeader.get());
    }

    @Test
    void testConnectReturnsMatchedFlagAndSendsAuthenticatedRequest() {
        bodyToReturn.set("{\"matched\": true}");

        final boolean matched = repo.connect("u-2");

        assertTrue(matched);
        assertEquals("Bearer test-token", lastAuthHeader.get());
        assertTrue(lastRequestBody.get().contains("u-2"));
    }

    @Test
    void testDislikeSendsAuthenticatedRequest() {
        bodyToReturn.set("{}");

        repo.dislike("u-3");

        assertEquals("Bearer test-token", lastAuthHeader.get());
        assertTrue(lastRequestBody.get().contains("u-3"));
    }

    @Test
    void testGetMatchesDeduplicatesByUserId() {
        bodyToReturn.set("""
            [{"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace"},
             {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace"}]""");

        final List<User> matches = repo.getMatches();

        assertEquals(1, matches.size());
    }

    // ── profile ──────────────────────────────────────────────────────────────

    @Test
    void testGetProfileParsesAllOptionalFields() {
        bodyToReturn.set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "email": "ada@example.com", "phoneNumber": "555-1", "institution": "UNIVERSITY_OF_TORONTO",
             "academicLevel": "FACULTY", "researchField": "MATHEMATICS", "lookingFor": "CO_AUTHOR",
             "collaborationDescription": "collab", "researchDescription": "research",
             "weeklyAvailabilityHours": 10, "fundingStatus": "SELF_FUNDED",
             "hIndex": 40, "totalCitations": 500,
             "researchInterests": ["nlp", "vision"],
             "papers": [{"doi": "10.1/x", "title": "Paper X"}],
             "educations": [{"school": "MIT", "degree": "PHD"}]}""");

        final User user = repo.getProfile();

        assertEquals("u-1", user.getUserId());
        assertEquals("ada@example.com", user.getEmail());
        assertEquals("555-1", user.getPhoneNumber());
        assertEquals(com.scholarmatch.entity.Institution.UNIVERSITY_OF_TORONTO, user.getInstitution());
        assertEquals(com.scholarmatch.entity.AcademicLevel.FACULTY, user.getAcademicLevel());
        assertEquals(com.scholarmatch.entity.ResearchField.MATHEMATICS, user.getResearchField());
        assertEquals(com.scholarmatch.entity.CollaborationType.CO_AUTHOR, user.getLookingFor());
        assertEquals("collab", user.getCollaborationDescription());
        assertEquals("research", user.getResearchDescription());
        assertEquals(10, user.getWeeklyAvailabilityHours());
        assertEquals(com.scholarmatch.entity.FundingStatus.SELF_FUNDED, user.getFundingStatus());
        assertEquals(40, user.gethIndex());
        assertEquals(500, user.getTotalCitations());
        assertTrue(user.getResearchInterests().containsAll(List.of("nlp", "vision")));
        assertEquals(1, user.getPublications().size());
        assertEquals("Paper X", user.getPublications().get(0).getTitle());
        assertEquals(1, user.getEducations().size());
        assertEquals("MIT", user.getEducations().get(0).getInstitution());
        assertEquals(DegreeType.PHD, user.getEducations().get(0).getDegreeType());
    }

    @Test
    void testGetProfileFallsBackToDefaultsWhenOptionalFieldsMissingOrInvalid() {
        bodyToReturn.set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "academicLevel": "NOT_REAL", "educations": [{"school": "MIT", "degree": "NOT_REAL"}]}""");

        final User user = repo.getProfile();

        assertEquals("", user.getEmail());
        assertEquals("", user.getPhoneNumber());
        assertEquals(com.scholarmatch.entity.AcademicLevel.GRADUATE_STUDENT, user.getAcademicLevel());
        assertEquals(com.scholarmatch.entity.CollaborationType.INTEREST_SHARING, user.getLookingFor());
        assertEquals(com.scholarmatch.entity.ResearchField.OTHER, user.getResearchField());
        assertEquals(com.scholarmatch.entity.FundingStatus.OTHER, user.getFundingStatus());
        assertEquals(com.scholarmatch.entity.Institution.OTHER, user.getInstitution());
        assertEquals(null, user.getWeeklyAvailabilityHours());
        assertEquals(DegreeType.BACHELOR, user.getEducations().get(0).getDegreeType());
    }

    @Test
    void testGetProfileDoesNotAssumeVerificationWhenServerOmitsFlag() {
        bodyToReturn.set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "email": "ada@mit.edu"}""");

        final User user = repo.getProfile();

        assertEquals(EmailAccountType.REGULAR, user.getEmailAccountType());
    }

    @Test
    void testGetProfileUsesServerClassificationWhenPresent() {
        bodyToReturn.set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "email": "ada@mit.edu", "academicEmailVerified": false}""");

        final User user = repo.getProfile();

        assertEquals(EmailAccountType.REGULAR, user.getEmailAccountType());
    }

    @Test
    void testUpdateProfileSendsAllProvidedFieldsIncludingPapersAndEducations() {
        bodyToReturn.set("{\"scholarId\": \"u-1\", \"firstName\": \"Ada\", \"lastName\": \"Lovelace\"}");

        final User result = repo.updateProfile(new UpdateProfileInputData(
                "new@example.com", "UNIVERSITY_OF_TORONTO", "FACULTY", "MATHEMATICS", "CO_AUTHOR",
                "collab", "research", 10, "SELF_FUNDED", List.of("nlp"), "555-1", 40, 500,
                List.of(new Education("MIT", DegreeType.PHD, 2010, Month.JANUARY, 2015, Month.MAY)),
                List.of(new Publication("10.1/x", "Paper X", 2020, 5))));

        assertEquals("u-1", result.getUserId());
        assertEquals("PUT", lastMethod.get());
        assertTrue(lastRequestBody.get().contains("new@example.com"));
        assertTrue(lastRequestBody.get().contains("Paper X"));
        assertTrue(lastRequestBody.get().contains("MIT"));
        assertTrue(lastRequestBody.get().contains("PHD"));
    }

    @Test
    void testUpdateProfileOmitsNullFieldsFromRequestBody() {
        bodyToReturn.set("{\"scholarId\": \"u-1\", \"firstName\": \"Ada\", \"lastName\": \"Lovelace\"}");

        repo.updateProfile(new UpdateProfileInputData(
                null, null, null, null, null, null, null, null, null,
                List.of(), null, null, null, List.of(), List.of()));

        assertFalse(lastRequestBody.get().contains("institution"));
        assertFalse(lastRequestBody.get().contains("hIndex"));
    }

    @Test
    void testUpdateProfileThrowsInvalidRequestOn400WithErrorField() {
        statusToReturn.set(400);
        bodyToReturn.set("{\"error\": \"Bad update request\"}");

        final InvalidRequestException thrown = assertThrows(InvalidRequestException.class,
                () -> repo.updateProfile(new UpdateProfileInputData(
                        null, null, null, null, null, null, null, null, null,
                        List.of(), null, null, null, List.of(), List.of())));
        assertEquals("Bad update request", thrown.getMessage());
    }

    @Test
    void testDeleteAccountSucceedsOn204NoContent() {
        statusToReturn.set(204);
        bodyToReturn.set(null);

        repo.deleteAccount();

        assertEquals("DELETE", lastMethod.get());
    }

    // ── messages ─────────────────────────────────────────────────────────────

    @Test
    void testSendMessageParsesMessage() {
        bodyToReturn.set("""
            {"messageId": "m-1", "senderId": "u-1", "receiverId": "u-2",
             "content": "hi", "sentAt": "2024-01-01T10:00:00"}""");

        final Message message = repo.sendMessage("u-2", "hi");

        assertEquals("m-1", message.getMessageId());
        assertEquals("hi", message.getContent());
    }

    @Test
    void testGetConversationParsesMessageArray() {
        bodyToReturn.set("""
            [{"messageId": "m-1", "senderId": "u-1", "receiverId": "u-2",
              "content": "hi", "sentAt": "2024-01-01T10:00:00"}]""");

        final List<Message> conversation = repo.getConversation("u-2");

        assertEquals(1, conversation.size());
        assertEquals("m-1", conversation.get(0).getMessageId());
    }

    // ── error handling ───────────────────────────────────────────────────────

    @Test
    void testGetProfileThrowsResourceNotFoundOn404WithErrorField() {
        statusToReturn.set(404);
        bodyToReturn.set("{\"error\": \"Profile not found\"}");

        final ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> repo.getProfile());
        assertEquals("Profile not found", thrown.getMessage());
    }

    @Test
    void testGetProfileThrowsExternalServiceOn500WithoutErrorField() {
        statusToReturn.set(500);
        bodyToReturn.set("{}");

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> repo.getProfile());
        assertTrue(thrown.getMessage().contains("server ran into a problem"));
    }

    @Test
    void testLoginThrowsInvalidRequestOn400WithErrorField() {
        statusToReturn.set(400);
        bodyToReturn.set("{\"error\": \"Invalid credentials\"}");

        final InvalidRequestException thrown =
                assertThrows(InvalidRequestException.class, () -> repo.login("a@example.com", "wrong"));
        assertEquals("Invalid credentials", thrown.getMessage());
    }

    @Test
    void testGetProfileThrowsExternalServiceWhenResponseBodyIsNotJson() {
        statusToReturn.set(200);
        bodyToReturn.set("not json at all {{{");

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> repo.getProfile());
        assertTrue(thrown.getMessage().contains("unexpected response"));
    }

    @Test
    void testDescribeStatusCodeCoversEveryBranch() {
        final Map<Integer, String> expectedSubstringByStatus = Map.of(
                401, "log in again",
                403, "don't have permission",
                408, "timed out",
                429, "Too many requests",
                402, "Something went wrong (error 402)",
                503, "server ran into a problem");

        for (final Map.Entry<Integer, String> entry : expectedSubstringByStatus.entrySet()) {
            statusToReturn.set(entry.getKey());
            bodyToReturn.set("{}");

            final DataAccessException thrown = assertThrows(DataAccessException.class, () -> repo.getProfile());
            assertTrue(thrown.getMessage().contains(entry.getValue()),
                    "status " + entry.getKey() + " message was: " + thrown.getMessage());
        }
    }

    @Test
    void testDeleteAccountThrowsInvalidRequestOn400WithErrorField() {
        statusToReturn.set(400);
        bodyToReturn.set("{\"error\": \"Bad delete request\"}");

        final InvalidRequestException thrown =
                assertThrows(InvalidRequestException.class, () -> repo.deleteAccount());
        assertEquals("Bad delete request", thrown.getMessage());
    }

    @Test
    void testDeleteAccountThrowsExternalServiceOn500WithoutErrorField() {
        statusToReturn.set(500);
        bodyToReturn.set("{}");

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, () -> repo.deleteAccount());
        assertTrue(thrown.getMessage().contains("server ran into a problem"));
    }

    @Test
    void testDeleteAccountFallsBackToStatusCodeWhenBodyIsBlank() {
        statusToReturn.set(400);
        bodyToReturn.set("");

        final InvalidRequestException thrown =
                assertThrows(InvalidRequestException.class, () -> repo.deleteAccount());
        assertTrue(thrown.getMessage().contains("request was invalid"));
    }

    @Test
    void testDeleteAccountFallsBackToStatusCodeWhenBodyIsNotJson() {
        statusToReturn.set(404);
        bodyToReturn.set("<html>gateway error</html>");

        final ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> repo.deleteAccount());
        assertTrue(thrown.getMessage().contains("wasn't found"));
    }

    // ── network failure ──────────────────────────────────────────────────────

    @Test
    void testGetTranslatesConnectionFailureForUnauthenticatedAndAuthenticatedCalls() throws IOException {
        final ServerRepository unreachable = unreachableRepo();

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, unreachable::getProfile);
        assertTrue(thrown.getMessage().contains("Could not connect to the server"));
        assertInstanceOf(ConnectException.class, thrown.getCause());
    }

    @Test
    void testPostTranslatesConnectionFailureForUnauthenticatedCall() throws IOException {
        final ServerRepository unreachable = unreachableRepo();

        assertThrows(ExternalServiceException.class, () -> unreachable.login("a@example.com", "pw"));
    }

    @Test
    void testPostTranslatesConnectionFailureForAuthenticatedCall() throws IOException {
        final ServerRepository unreachable = unreachableRepo();

        assertThrows(ExternalServiceException.class, () -> unreachable.connect("u-1"));
    }

    @Test
    void testPutTranslatesConnectionFailure() throws IOException {
        final ServerRepository unreachable = unreachableRepo();

        assertThrows(ExternalServiceException.class, () -> unreachable.updateProfile(new UpdateProfileInputData(
                null, null, null, null, null, null, null, null, null,
                List.of(), null, null, null, List.of(), List.of())));
    }

    @Test
    void testDeleteTranslatesConnectionFailure() throws IOException {
        final ServerRepository unreachable = unreachableRepo();

        assertThrows(ExternalServiceException.class, unreachable::deleteAccount);
    }

    @Test
    void testNetworkFailureFallsBackToGenericMessageForNonIoFailures() {
        // A space in the host is invalid, so URI.create() inside get() throws
        // IllegalArgumentException — not an IOException subtype — which exercises
        // describeNetworkFailure's final fallback branch instead of any of the specific ones.
        final ServerRepository malformed = new ServerRepository("http://bad host name", session);

        final ExternalServiceException thrown =
                assertThrows(ExternalServiceException.class, malformed::getProfile);
        assertEquals("Unable to reach the server. Please try again.", thrown.getMessage());
    }

    private ServerRepository unreachableRepo() throws IOException {
        final int closedPort;
        try (ServerSocket probe = new ServerSocket(0)) {
            closedPort = probe.getLocalPort();
        }
        return new ServerRepository("http://127.0.0.1:" + closedPort, session);
    }
}
