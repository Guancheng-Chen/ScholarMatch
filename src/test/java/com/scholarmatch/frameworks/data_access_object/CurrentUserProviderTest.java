package com.scholarmatch.frameworks.data_access_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrentUserProviderTest {

    private CurrentUserProvider provider;

    @BeforeEach
    void setUp() {
        provider = new CurrentUserProvider();
    }

    @Test
    void testFreshProviderIsNotLoggedIn() {
        assertFalse(provider.isLoggedIn());
        assertNull(provider.getToken());
    }

    @Test
    void testGetCurrentUserIdThrowsWhenUnauthenticated() {
        assertThrows(IllegalStateException.class, () -> provider.getCurrentUserId());
    }

    @Test
    void testSetAndGetCurrentUserId() {
        provider.setCurrentUserId("user-123");

        assertEquals("user-123", provider.getCurrentUserId());
        assertTrue(provider.isLoggedIn());
    }

    @Test
    void testSetAndGetToken() {
        provider.setToken("jwt-token");

        assertEquals("jwt-token", provider.getToken());
    }

    @Test
    void testClearSessionResetsUserIdAndToken() {
        provider.setCurrentUserId("user-123");
        provider.setToken("jwt-token");

        provider.clearSession();

        assertFalse(provider.isLoggedIn());
        assertNull(provider.getToken());
        assertThrows(IllegalStateException.class, () -> provider.getCurrentUserId());
    }
}
