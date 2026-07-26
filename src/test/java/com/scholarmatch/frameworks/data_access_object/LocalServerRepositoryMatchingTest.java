package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.User;
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
    void testSeedUsersMatchImmediatelyAndCanExchangeMessages() {
        final AuthResult current = register("Current", "current@example.com");
        this.session.setCurrentUserId(current.userId());
        final User seedUser = this.repository.getRecommendations().getFirst();

        assertTrue(this.repository.connect(seedUser.getUserId()));
        assertEquals(List.of(seedUser), this.repository.getMatches());

        final Message message = this.repository.sendMessage(seedUser.getUserId(), "Hello");
        assertEquals("Hello", message.getContent());
        assertEquals(List.of(message),
                this.repository.getConversation(seedUser.getUserId()));
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
        assertThrows(InvalidRequestException.class,
                () -> this.repository.sendMessage(second.userId(), "Too early"));

        this.session.setCurrentUserId(second.userId());
        assertTrue(this.repository.connect(first.userId()));
        final Message reply = this.repository.sendMessage(first.userId(), "Now matched");

        this.session.setCurrentUserId(first.userId());
        assertEquals(List.of(reply), this.repository.getConversation(second.userId()));
        assertEquals(second.userId(), this.repository.getMatches().getFirst().getUserId());
    }

    private AuthResult register(final String firstName, final String email) {
        return this.repository.register(new RegisterAccountData(
                firstName, "User", email, "password", "123456"));
    }
}
