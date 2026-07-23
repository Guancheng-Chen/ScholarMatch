package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.register.RegisterAccountData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthGatewayTest {

    private HttpTestServer fakeServer;
    private AuthGateway gateway;

    @BeforeEach
    void setUp() throws IOException {
        this.fakeServer = new HttpTestServer();
        final CurrentUserProviderInterface session = mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        this.gateway = new AuthGateway(new ServerHttpClient(this.fakeServer.baseUrl(), session));
    }

    @AfterEach
    void tearDown() {
        this.fakeServer.stop();
    }

    @Test
    void testLoginParsesAuthResultAndSendsUnauthenticatedRequest() {
        this.fakeServer.bodyToReturn().set("{\"token\": \"jwt-1\", \"scholarId\": \"u-1\", \"name\": \"Ada Lovelace\"}");

        final AuthResult result = this.gateway.login("ada@example.com", "pw");

        assertEquals("jwt-1", result.token());
        assertEquals("u-1", result.userId());
        assertEquals("Ada Lovelace", result.displayName());
        assertEquals("POST", this.fakeServer.lastMethod().get());
        assertEquals(null, this.fakeServer.lastAuthHeader().get());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("ada@example.com"));
    }

    @Test
    void testRegisterParsesAuthResultAndForwardsVerificationCode() {
        this.fakeServer.bodyToReturn().set("{\"token\": \"jwt-2\", \"scholarId\": \"u-2\", \"name\": \"Jane Doe\"}");

        final AuthResult result = this.gateway.register(new RegisterAccountData(
                "Jane", "Doe", "jane@mit.edu", "pw123456", "123456"));

        assertEquals("jwt-2", result.token());
        assertEquals("u-2", result.userId());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("jane@mit.edu"));
        assertTrue(this.fakeServer.lastRequestBody().get().contains("\"code\":\"123456\""));
    }

    @Test
    void testLoginThrowsInvalidRequestOn400WithErrorField() {
        this.fakeServer.statusToReturn().set(400);
        this.fakeServer.bodyToReturn().set("{\"error\": \"Invalid credentials\"}");

        final InvalidRequestException thrown = assertThrows(
                InvalidRequestException.class, () -> this.gateway.login("a@example.com", "wrong"));
        assertEquals("Invalid credentials", thrown.getMessage());
    }
}
package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.register.RegisterAccountData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthGatewayTest {

    private HttpTestServer fakeServer;
    private AuthGateway gateway;

    @BeforeEach
    void setUp() throws IOException {
        this.fakeServer = new HttpTestServer();
        final CurrentUserProviderInterface session = mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        this.gateway = new AuthGateway(new ServerHttpClient(this.fakeServer.baseUrl(), session));
    }

    @AfterEach
    void tearDown() {
        this.fakeServer.stop();
    }

    @Test
    void testLoginParsesAuthResultAndSendsUnauthenticatedRequest() {
        this.fakeServer.bodyToReturn().set("{\"token\": \"jwt-1\", \"scholarId\": \"u-1\", \"name\": \"Ada Lovelace\"}");

        final AuthResult result = this.gateway.login("ada@example.com", "pw");

        assertEquals("jwt-1", result.token());
        assertEquals("u-1", result.userId());
        assertEquals("Ada Lovelace", result.displayName());
        assertEquals("POST", this.fakeServer.lastMethod().get());
        assertEquals(null, this.fakeServer.lastAuthHeader().get());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("ada@example.com"));
    }

    @Test
    void testRegisterParsesAuthResultAndForwardsVerificationCode() {
        this.fakeServer.bodyToReturn().set("{\"token\": \"jwt-2\", \"scholarId\": \"u-2\", \"name\": \"Jane Doe\"}");

        final AuthResult result = this.gateway.register(new RegisterAccountData(
                "Jane", "Doe", "jane@mit.edu", "pw123456", "123456"));

        assertEquals("jwt-2", result.token());
        assertEquals("u-2", result.userId());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("jane@mit.edu"));
        assertTrue(this.fakeServer.lastRequestBody().get().contains("\"code\":\"123456\""));
    }

    @Test
    void testLoginThrowsInvalidRequestOn400WithErrorField() {
        this.fakeServer.statusToReturn().set(400);
        this.fakeServer.bodyToReturn().set("{\"error\": \"Invalid credentials\"}");

        final InvalidRequestException thrown = assertThrows(
                InvalidRequestException.class, () -> this.gateway.login("a@example.com", "wrong"));
        assertEquals("Invalid credentials", thrown.getMessage());
    }
}
