package com.scholarmatch.frameworks.data_access_object.server;

import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountSettingsGatewayTest {

    private HttpTestServer fakeServer;
    private AccountSettingsGateway gateway;

    @BeforeEach
    void setUp() throws IOException {
        this.fakeServer = new HttpTestServer();
        final CurrentUserProviderInterface session =
                mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        this.gateway = new AccountSettingsGateway(
                new ServerHttpClient(this.fakeServer.baseUrl(), session),
                new ClasspathInstitutionCatalogRepository());
    }

    @AfterEach
    void tearDown() {
        this.fakeServer.stop();
    }

    @Test
    void testRequestEmailChangeCodeUsesAuthenticatedEndpoint() {
        this.fakeServer.bodyToReturn().set("{}");

        this.gateway.requestEmailChangeVerification("new@example.com");

        assertEquals("POST", this.fakeServer.lastMethod().get());
        assertEquals(
                "/api/account/email-change/request-code",
                this.fakeServer.lastPath().get());
        assertTrue(this.fakeServer.lastRequestBody().get()
                .contains("new@example.com"));
    }

    @Test
    void testChangeEmailMapsUpdatedAccount() {
        this.fakeServer.bodyToReturn().set("""
                {"scholarId":"u-1","firstName":"Ada","lastName":"Lovelace",
                 "email":"new@mit.edu","academicEmailVerified":true}
                """);

        final User user = this.gateway.changeEmail(
                "new@mit.edu", "current-password", "123456");

        assertEquals("PUT", this.fakeServer.lastMethod().get());
        assertEquals("/api/account/email", this.fakeServer.lastPath().get());
        assertEquals("new@mit.edu", user.getEmail());
        assertEquals(EmailAccountType.ACADEMIC, user.getEmailAccountType());
        assertTrue(this.fakeServer.lastRequestBody().get()
                .contains("current-password"));
        assertTrue(this.fakeServer.lastRequestBody().get().contains("123456"));
    }

    @Test
    void testChangePasswordUsesAuthenticatedEndpoint() {
        this.fakeServer.bodyToReturn().set("{}");

        this.gateway.changePassword("current-password", "new-password");

        assertEquals("PUT", this.fakeServer.lastMethod().get());
        assertEquals("/api/account/password", this.fakeServer.lastPath().get());
        assertTrue(this.fakeServer.lastRequestBody().get()
                .contains("new-password"));
    }
}
