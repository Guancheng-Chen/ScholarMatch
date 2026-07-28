package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
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
    private LocalAuthRepository authRepo;
    private LocalMatchingRepository matchingRepo;
    private LocalMessagingRepository messagingRepo;

    @BeforeEach
    void setUp() {
        this.session = new CurrentUserProvider();
        final LocalServerState state =
                new LocalServerState(new ClasspathInstitutionCatalogRepository());
        this.authRepo = new LocalAuthRepository(state);
        this.matchingRepo = new LocalMatchingRepository(state, this.session);
        this.messagingRepo = new LocalMessagingRepository(state, this.session);
    }

    @Test
    void testSeedUsersRequireReciprocalConnect() {
        final AuthResult current = register("Current", "current@example.com");
        this.session.setCurrentUserId(current.userId());
        final List<User> recommendations = this.matchingRepo.getRecommendations();
        final User seedUser = recommendations.getFirst();

        assertFalse(this.matchingRepo.connect(seedUser.getUserId()));
        assertTrue(this.matchingRepo.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.messagingRepo.sendMessage(seedUser.getUserId(), "Too early"));
        assertThrows(InvalidRequestException.class,
                () -> this.messagingRepo.getConversation(seedUser.getUserId()));

        final AuthResult seed = this.authRepo.login(seedUser.getEmail(), "12345678");
        this.session.setCurrentUserId(seed.userId());
        assertTrue(this.matchingRepo.connect(current.userId()));

        this.session.setCurrentUserId(current.userId());
        assertEquals(List.of(seedUser), this.matchingRepo.getMatches());
        final Message message = this.messagingRepo.sendMessage(seedUser.getUserId(), "Hello");
        assertEquals(List.of(message),
                this.messagingRepo.getConversation(seedUser.getUserId()));
    }

    @Test
    void testSeedUserConnectingFirstStillMatchesInstantlyOnRealUsersTurn() {
        final AuthResult current = register("Current", "current@example.com");
        this.session.setCurrentUserId(current.userId());
        final User seedUser = this.matchingRepo.getRecommendations().getFirst();

        final AuthResult seed = this.authRepo.login(seedUser.getEmail(), "12345678");
        this.session.setCurrentUserId(seed.userId());
        assertFalse(this.matchingRepo.connect(current.userId()));

        this.session.setCurrentUserId(current.userId());
        assertTrue(this.matchingRepo.connect(seedUser.getUserId()));
        assertEquals(List.of(seedUser), this.matchingRepo.getMatches());
    }

    @Test
    void testDislikeRemovesUserFromRecommendations() {
        final AuthResult current = register("Current", "current@example.com");
        this.session.setCurrentUserId(current.userId());
        final User disliked = this.matchingRepo.getRecommendations().getFirst();

        this.matchingRepo.dislike(disliked.getUserId());

        assertFalse(this.matchingRepo.getRecommendations().contains(disliked));
    }

    @Test
    void testRegisteredUsersRequireReciprocalConnect() {
        final AuthResult first = register("First", "first@example.com");
        final AuthResult second = register("Second", "second@example.com");

        this.session.setCurrentUserId(first.userId());
        assertFalse(this.matchingRepo.connect(second.userId()));
        assertTrue(this.matchingRepo.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.messagingRepo.sendMessage(second.userId(), "Too early"));
        assertThrows(InvalidRequestException.class,
                () -> this.messagingRepo.getConversation(second.userId()));

        this.session.setCurrentUserId(second.userId());
        assertTrue(this.matchingRepo.connect(first.userId()));
        final Message reply = this.messagingRepo.sendMessage(first.userId(), "Now matched");

        this.session.setCurrentUserId(first.userId());
        assertEquals(List.of(reply), this.messagingRepo.getConversation(second.userId()));
        assertEquals(second.userId(), this.matchingRepo.getMatches().getFirst().getUserId());
    }

    @Test
    void testDislikeBlocksAnExistingMatch() {
        final AuthResult first = register("First", "first@example.com");
        final AuthResult second = register("Second", "second@example.com");

        this.session.setCurrentUserId(first.userId());
        assertFalse(this.matchingRepo.connect(second.userId()));
        this.session.setCurrentUserId(second.userId());
        assertTrue(this.matchingRepo.connect(first.userId()));
        this.matchingRepo.dislike(first.userId());

        assertTrue(this.matchingRepo.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.messagingRepo.sendMessage(first.userId(), "Blocked"));

        this.session.setCurrentUserId(first.userId());
        assertTrue(this.matchingRepo.getMatches().isEmpty());
        assertThrows(InvalidRequestException.class,
                () -> this.messagingRepo.getConversation(second.userId()));
    }

    private AuthResult register(final String firstName, final String email) {
        return this.authRepo.register(new RegisterAccountData(
                firstName, "User", email, "password", "123456"));
    }
}
