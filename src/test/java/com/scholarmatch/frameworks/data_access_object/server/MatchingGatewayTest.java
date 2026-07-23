package com.scholarmatch.frameworks.data_access_object.server;

import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import com.scholarmatch.frameworks.data_access_object.server.HttpTestServer;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MatchingGatewayTest {

    private HttpTestServer fakeServer;
    private MatchingGateway gateway;

    @BeforeEach
    void setUp() throws IOException {
        this.fakeServer = new HttpTestServer();
        final CurrentUserProviderInterface session = mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        this.gateway = new MatchingGateway(
                new ServerHttpClient(this.fakeServer.baseUrl(), session),
                new ClasspathInstitutionCatalogRepository());
    }

    @AfterEach
    void tearDown() {
        this.fakeServer.stop();
    }

    @Test
    void testGetRecommendationsParsesUserArray() {
        this.fakeServer.bodyToReturn().set("[{\"scholarId\": \"u-1\", \"firstName\": \"Ada\", \"lastName\": \"Lovelace\"}]");

        final List<User> results = this.gateway.getRecommendations();

        assertEquals(1, results.size());
        assertEquals("Ada", results.get(0).getFirstName());
        assertEquals("GET", this.fakeServer.lastMethod().get());
        assertEquals("Bearer test-token", this.fakeServer.lastAuthHeader().get());
    }

    @Test
    void testConnectReturnsMatchedFlagAndSendsAuthenticatedRequest() {
        this.fakeServer.bodyToReturn().set("{\"matched\": true}");

        final boolean matched = this.gateway.connect("u-2");

        assertTrue(matched);
        assertEquals("Bearer test-token", this.fakeServer.lastAuthHeader().get());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("u-2"));
    }

    @Test
    void testDislikeSendsAuthenticatedRequest() {
        this.fakeServer.bodyToReturn().set("{}");

        this.gateway.dislike("u-3");

        assertEquals("Bearer test-token", this.fakeServer.lastAuthHeader().get());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("u-3"));
    }

    @Test
    void testGetMatchesDeduplicatesByUserId() {
        this.fakeServer.bodyToReturn().set("""
            [{"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace"},
             {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace"}]""");

        final List<User> matches = this.gateway.getMatches();

        assertEquals(1, matches.size());
    }
}
