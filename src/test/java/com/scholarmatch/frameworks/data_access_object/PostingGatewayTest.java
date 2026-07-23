package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.load_postings.PostingScope;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Baseline coverage for PostingGateway — the recruitment feature had no direct HTTP-gateway
 * tests before this split (ServerRepositoryTest never exercised the postings endpoints), so
 * these are new rather than ported.
 */
class PostingGatewayTest {

    private static final String POSTING_JSON = """
            {"postingId": "p-1", "posterUserId": "u-1", "title": "Title",
             "description": "Desc", "researchField": "COMPUTER_SCIENCE",
             "collaborationType": "CO_AUTHOR", "capacity": 3, "applicantCount": 0,
             "status": "OPEN", "createdAt": "2024-01-01T10:00:00"}""";

    private static final String APPLICATION_JSON = """
            {"applicationId": "a-1", "postingId": "p-1", "applicantUserId": "u-2",
             "message": "hi", "status": "PENDING", "appliedAt": "2024-01-01T10:00:00"}""";

    private HttpTestServer fakeServer;
    private PostingGateway gateway;

    @BeforeEach
    void setUp() throws IOException {
        this.fakeServer = new HttpTestServer();
        final CurrentUserProviderInterface session = mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        this.gateway = new PostingGateway(new ServerHttpClient(this.fakeServer.baseUrl(), session));
    }

    @AfterEach
    void tearDown() {
        this.fakeServer.stop();
    }

    @Test
    void testCreatePostingSendsFieldsAndParsesPosting() {
        this.fakeServer.bodyToReturn().set(POSTING_JSON);

        final Posting posting = this.gateway.createPosting(
                "Title", "Desc", ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR, 3);

        assertEquals("p-1", posting.getPostingId());
        assertEquals(PostingStatus.OPEN, posting.getStatus());
        assertEquals("POST", this.fakeServer.lastMethod().get());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("Title"));
    }

    @Test
    void testClosePostingParsesPosting() {
        this.fakeServer.bodyToReturn().set(POSTING_JSON.replace("\"status\": \"OPEN\"", "\"status\": \"CLOSED\""));

        final Posting posting = this.gateway.closePosting("p-1");

        assertEquals(PostingStatus.CLOSED, posting.getStatus());
    }

    @Test
    void testLoadPostingsParsesArrayForRequestedScope() {
        this.fakeServer.bodyToReturn().set("[" + POSTING_JSON + "]");

        final List<Posting> postings = this.gateway.loadPostings(PostingScope.MINE);

        assertEquals(1, postings.size());
        assertTrue(this.fakeServer.lastPath().get().contains("scope=MINE"));
    }

    @Test
    void testApplyToPostingParsesApplication() {
        this.fakeServer.bodyToReturn().set(APPLICATION_JSON);

        final PostingApplication application = this.gateway.applyToPosting("p-1", "hi");

        assertEquals("a-1", application.getApplicationId());
        assertEquals(PostingApplicationStatus.PENDING, application.getStatus());
    }

    @Test
    void testAcceptApplicationParsesApplication() {
        this.fakeServer.bodyToReturn().set(APPLICATION_JSON.replace("\"status\": \"PENDING\"", "\"status\": \"ACCEPTED\""));

        final PostingApplication application = this.gateway.acceptApplication("a-1");

        assertEquals(PostingApplicationStatus.ACCEPTED, application.getStatus());
    }

    @Test
    void testDeclineApplicationParsesApplication() {
        this.fakeServer.bodyToReturn().set(APPLICATION_JSON.replace("\"status\": \"PENDING\"", "\"status\": \"REJECTED\""));

        final PostingApplication application = this.gateway.declineApplication("a-1");

        assertEquals(PostingApplicationStatus.REJECTED, application.getStatus());
    }

    @Test
    void testGetMyApplicationsParsesArray() {
        this.fakeServer.bodyToReturn().set("[" + APPLICATION_JSON + "]");

        final List<PostingApplication> applications = this.gateway.getMyApplications();

        assertEquals(1, applications.size());
        assertEquals("a-1", applications.get(0).getApplicationId());
    }

    @Test
    void testLoadApplicationsForOwnedPostingsReturnsEmptyForNonMineScope() {
        final var result = this.gateway.loadApplicationsForOwnedPostings(PostingScope.ALL_ACTIVE, List.of());

        assertEquals(0, result.size());
    }
}
