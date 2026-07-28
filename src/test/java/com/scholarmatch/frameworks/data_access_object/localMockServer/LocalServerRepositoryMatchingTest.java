package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.register.RegisterAccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalServerRepositoryMatchingTest {

    private CurrentUserProvider session;
    private LocalServerRepository repository;

    @BeforeEach
    void setUp() {
        this.session = new CurrentUserProvider();
        this.repository = new LocalServerRepository(this.session);
    }

    @Test
    void testSeedUsersRequireReciprocalConnect() {
        final AuthResult current = register("Current", "current@example.com");
        this.session.setCurrentUserId(current.userId());
        final List<User> recommendations = this.repository.getRecommendations();
        final User seedUser = recommendations.getFirst();

        assertFalse(this.repository.connect(seedUser.getUserId()));
        assertTrue(this.repository.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.repository.sendMessage(seedUser.getUserId(), "Too early"));
        assertThrows(InvalidRequestException.class,
                () -> this.repository.getConversation(seedUser.getUserId()));

        final AuthResult seed = this.repository.login(seedUser.getEmail(), "12345678");
        this.session.setCurrentUserId(seed.userId());
        assertTrue(this.repository.connect(current.userId()));

        this.session.setCurrentUserId(current.userId());
        assertEquals(List.of(seedUser), this.repository.getMatches());
        final Message message = this.repository.sendMessage(seedUser.getUserId(), "Hello");
        assertEquals(List.of(message),
                this.repository.getConversation(seedUser.getUserId()));
    }

    @Test
    void testSeedUserConnectingFirstStillMatchesInstantlyOnRealUsersTurn() {
        final AuthResult current = register("Current", "current@example.com");
        this.session.setCurrentUserId(current.userId());
        final User seedUser = this.repository.getRecommendations().getFirst();

        final AuthResult seed = this.repository.login(seedUser.getEmail(), "12345678");
        this.session.setCurrentUserId(seed.userId());
        assertFalse(this.repository.connect(current.userId()));

        this.session.setCurrentUserId(current.userId());
        assertTrue(this.repository.connect(seedUser.getUserId()));
        assertEquals(List.of(seedUser), this.repository.getMatches());
    }

    @Test
    void testDislikeRemovesUserFromRecommendations() {
        final AuthResult current = register("Current", "current@example.com");
        this.session.setCurrentUserId(current.userId());
        final User disliked = this.repository.getRecommendations().getFirst();

        this.repository.dislike(disliked.getUserId());

        assertFalse(this.repository.getRecommendations().contains(disliked));
    }

    @Test
    void testRegisteredUsersRequireReciprocalConnect() {
        final AuthResult first = register("First", "first@example.com");
        final AuthResult second = register("Second", "second@example.com");

        this.session.setCurrentUserId(first.userId());
        assertFalse(this.repository.connect(second.userId()));
        assertTrue(this.repository.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.repository.sendMessage(second.userId(), "Too early"));
        assertThrows(InvalidRequestException.class,
                () -> this.repository.getConversation(second.userId()));

        this.session.setCurrentUserId(second.userId());
        assertTrue(this.repository.connect(first.userId()));
        final Message reply = this.repository.sendMessage(first.userId(), "Now matched");

        this.session.setCurrentUserId(first.userId());
        assertEquals(List.of(reply), this.repository.getConversation(second.userId()));
        assertEquals(second.userId(), this.repository.getMatches().getFirst().getUserId());
    }

    @Test
    void testDislikeBlocksAnExistingMatch() {
        final AuthResult first = register("First", "first@example.com");
        final AuthResult second = register("Second", "second@example.com");

        this.session.setCurrentUserId(first.userId());
        assertFalse(this.repository.connect(second.userId()));
        this.session.setCurrentUserId(second.userId());
        assertTrue(this.repository.connect(first.userId()));
        this.repository.dislike(first.userId());

        assertTrue(this.repository.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.repository.sendMessage(first.userId(), "Blocked"));

        this.session.setCurrentUserId(first.userId());
        assertTrue(this.repository.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.repository.getConversation(second.userId()));
    }

    private AuthResult register(final String firstName, final String email) {
        return this.repository.register(new RegisterAccountData(
                firstName, "User", email, "password", "123456"));
    }
}
