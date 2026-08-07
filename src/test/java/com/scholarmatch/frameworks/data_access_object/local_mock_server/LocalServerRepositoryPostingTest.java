package com.scholarmatch.frameworks.data_access_object.local_mock_server;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.load_postings.PostingScope;
import com.scholarmatch.usecase.register.RegisterAccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalServerRepositoryPostingTest {

    private CurrentUserProvider session;
    private LocalAuthRepository authRepo;
    private InMemoryEmailVerificationChallengeRepository emailChallenges;
    private LocalProfileRepository profileRepo;
    private LocalMatchingRepository matchingRepo;
    private LocalMessagingRepository messagingRepo;
    private LocalPostingRepository repository;

    @BeforeEach
    void setUp() {
        session = new CurrentUserProvider();
        final ClasspathInstitutionCatalogRepository institutions =
                new ClasspathInstitutionCatalogRepository();
        final LocalServerState state = new LocalServerState(institutions);
        emailChallenges = new InMemoryEmailVerificationChallengeRepository();
        authRepo = new LocalAuthRepository(state, emailChallenges, Clock.systemUTC());
        profileRepo = new LocalProfileRepository(state, session, institutions);
        matchingRepo = new LocalMatchingRepository(state, session);
        messagingRepo = new LocalMessagingRepository(state, session);
        repository = new LocalPostingRepository(state, session);
    }

    @Test
    void testCreateApplyAndReviewDoNotCascade() {
        session.setCurrentUserId("poster-1");
        final Posting posting = repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, null);

        session.setCurrentUserId("applicant-1");
        final PostingApplication first = repository.applyToPosting(posting.getPostingId(), "First");
        assertEquals("poster-1", first.getPosterUserId());
        assertEquals("poster-1", first.getPosterName());
        session.setCurrentUserId("applicant-2");
        final PostingApplication second = repository.applyToPosting(posting.getPostingId(), "Second");

        session.setCurrentUserId("poster-1");
        repository.acceptApplication(first.getApplicationId());

        assertEquals(PostingApplicationStatus.ACCEPTED, first.getStatus());
        assertEquals(PostingApplicationStatus.PENDING, second.getStatus());
        assertEquals(2, posting.getApplicantCount());
        assertEquals(1, posting.getAcceptedCount());
        assertEquals(2, repository.loadApplicationsForOwnedPostings(
                PostingScope.MINE, List.of(posting)).get(posting.getPostingId()).size());
    }

    @Test
    void testApplicationsRemainOpenUntilSomeoneIsAccepted() {
        session.setCurrentUserId("poster-1");
        final Posting posting = repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 1);

        assertThrows(
                InvalidRequestException.class,
                () -> repository.applyToPosting(posting.getPostingId(), "Own"));

        session.setCurrentUserId("applicant-1");
        final PostingApplication first = repository.applyToPosting(posting.getPostingId(), "First");
        assertThrows(
                InvalidRequestException.class,
                () -> repository.applyToPosting(posting.getPostingId(), "Duplicate"));

        session.setCurrentUserId("applicant-2");
        repository.applyToPosting(posting.getPostingId(), "Second");
        assertTrue(posting.isActive());

        session.setCurrentUserId("poster-1");
        repository.acceptApplication(first.getApplicationId());
        assertEquals(1, posting.getAcceptedCount());
        assertTrue(posting.isFull());
        assertEquals(PostingStatus.CLOSED, posting.getStatus());

        session.setCurrentUserId("applicant-3");
        final InvalidRequestException full = assertThrows(
                InvalidRequestException.class,
                () -> repository.applyToPosting(posting.getPostingId(), "Third"));
        assertTrue(full.getMessage().contains("full"));
    }

    @Test
    void testOnlyPosterCanClosePosting() {
        session.setCurrentUserId("poster-1");
        final Posting posting = repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);

        session.setCurrentUserId("other-user");
        assertThrows(
                InvalidRequestException.class,
                () -> repository.closePosting(posting.getPostingId()));

        session.setCurrentUserId("poster-1");
        repository.closePosting(posting.getPostingId());

        assertEquals(PostingStatus.CLOSED, posting.getStatus());
        assertFalse(posting.isActive());
    }

    @Test
    void testAllSeededAccountsUseStandardDemoPassword() {
        for (final String email : List.of(
                "ada@demo.local",
                "alan@demo.local",
                "grace@demo.local",
                "demo.student@utoronto.ca")) {
            authRepo.login(email, "12345678");
        }
        final AuthResult result = authRepo.login(
                "demo.student@utoronto.ca", "12345678");
        session.setCurrentUserId(result.userId());

        assertEquals(EmailAccountType.ACADEMIC, profileRepo.getProfile().getEmailAccountType());
        assertEquals("UNIVERSITY_OF_TORONTO", profileRepo.getProfile().getInstitution().name());
        final Posting posting = repository.createPosting(
                "Verified poster", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 1);
        assertEquals("Demo Student", posting.getPosterName());
        assertTrue(posting.isPosterAcademicEmailVerified());
    }

    @Test
    void testAllActiveNeverReturnsApplicantIdentityMap() {
        session.setCurrentUserId("viewer-1");
        final List<Posting> postings = repository.loadPostings(PostingScope.ALL_ACTIVE);

        assertTrue(repository.loadApplicationsForOwnedPostings(
                PostingScope.ALL_ACTIVE, postings).isEmpty());
    }

    @Test
    void testPostingCreationAndCloseValidation() {
        session.setCurrentUserId("poster-1");
        assertThrows(InvalidRequestException.class, () -> repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 0));
        assertThrows(InvalidRequestException.class,
                () -> repository.closePosting("missing"));

        final Posting posting = repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);
        repository.closePosting(posting.getPostingId());

        assertThrows(InvalidRequestException.class,
                () -> repository.closePosting(posting.getPostingId()));
    }

    @Test
    void testPostingScopesAndOwnedApplicationFiltering() {
        session.setCurrentUserId("poster-1");
        final Posting mine = repository.createPosting(
                "Mine", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);
        session.setCurrentUserId("poster-2");
        final Posting other = repository.createPosting(
                "Other", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);

        session.setCurrentUserId("poster-1");
        assertTrue(repository.loadPostings(PostingScope.MINE).contains(mine));
        assertFalse(repository.loadPostings(PostingScope.MINE).contains(other));
        assertTrue(repository.loadPostings(PostingScope.ALL_ACTIVE).contains(other));
        assertFalse(repository.loadPostings(PostingScope.ALL_ACTIVE).contains(mine));
        assertEquals(1, repository.loadApplicationsForOwnedPostings(
                PostingScope.MINE, List.of(mine, other)).size());

        session.setCurrentUserId("poster-2");
        repository.closePosting(other.getPostingId());
        session.setCurrentUserId("poster-1");
        assertFalse(repository.loadPostings(PostingScope.ALL_ACTIVE).contains(other));
    }

    @Test
    void testApplicationValidationAndReviewStates() {
        session.setCurrentUserId("poster-1");
        final Posting posting = repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);
        assertThrows(InvalidRequestException.class,
                () -> repository.applyToPosting("missing", "message"));

        session.setCurrentUserId("applicant-1");
        final PostingApplication application =
                repository.applyToPosting(posting.getPostingId(), null);
        assertEquals("", application.getMessage());
        assertEquals(1, repository.getMyApplications().size());
        assertThrows(InvalidRequestException.class,
                () -> repository.acceptApplication(application.getApplicationId()));

        session.setCurrentUserId("poster-1");
        assertThrows(InvalidRequestException.class,
                () -> repository.acceptApplication("missing"));
        repository.declineApplication(application.getApplicationId());
        assertThrows(InvalidRequestException.class,
                () -> repository.declineApplication(application.getApplicationId()));
    }

    @Test
    void testClosedAndFullPostingsRejectAcceptance() {
        session.setCurrentUserId("poster-1");
        final Posting closed = repository.createPosting(
                "Closed", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);
        session.setCurrentUserId("applicant-1");
        final PostingApplication closedApplication =
                repository.applyToPosting(closed.getPostingId(), "message");
        session.setCurrentUserId("poster-1");
        repository.closePosting(closed.getPostingId());
        session.setCurrentUserId("applicant-2");
        final InvalidRequestException applyClosedError = assertThrows(
                InvalidRequestException.class,
                () -> repository.applyToPosting(closed.getPostingId(), "late"));
        assertTrue(applyClosedError.getMessage().contains("closed"));
        session.setCurrentUserId("poster-1");
        final InvalidRequestException closedError = assertThrows(
                InvalidRequestException.class,
                () -> repository.acceptApplication(closedApplication.getApplicationId()));
        assertTrue(closedError.getMessage().contains("closed"));

        final Posting full = repository.createPosting(
                "Full", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 1);
        session.setCurrentUserId("applicant-1");
        final PostingApplication accepted = repository.applyToPosting(full.getPostingId(), "first");
        session.setCurrentUserId("applicant-2");
        final PostingApplication pending = repository.applyToPosting(full.getPostingId(), "second");
        session.setCurrentUserId("poster-1");
        repository.acceptApplication(accepted.getApplicationId());

        final InvalidRequestException fullError = assertThrows(
                InvalidRequestException.class,
                () -> repository.acceptApplication(pending.getApplicationId()));
        assertTrue(fullError.getMessage().contains("full"));
    }

    @Test
    void testDeletingApplicantAndPosterCleansRelatedData() {
        final AuthResult poster = register("Poster", "poster@example.com");
        final AuthResult applicant = register("Applicant", "applicant@example.com");
        session.setCurrentUserId(poster.userId());
        final Posting posting = repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);

        session.setCurrentUserId(applicant.userId());
        repository.applyToPosting(posting.getPostingId(), "message");
        final String seedId = matchingRepo.getRecommendations().getFirst().getUserId();
        assertFalse(matchingRepo.connect(seedId));
        matchingRepo.dislike(seedId);

        session.setCurrentUserId(poster.userId());
        assertFalse(matchingRepo.connect(applicant.userId()));
        session.setCurrentUserId(applicant.userId());
        assertTrue(matchingRepo.connect(poster.userId()));
        session.setCurrentUserId(poster.userId());
        messagingRepo.sendMessage(applicant.userId(), "Received by applicant");
        session.setCurrentUserId(applicant.userId());
        messagingRepo.sendMessage(poster.userId(), "Reply from applicant");

        final AuthResult unrelatedPoster = register("Unrelated", "unrelated@example.com");
        final AuthResult unrelatedApplicant = register("Third", "third@example.com");
        session.setCurrentUserId(unrelatedPoster.userId());
        final Posting unrelatedPosting = repository.createPosting(
                "Unrelated", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 2);
        session.setCurrentUserId(unrelatedApplicant.userId());
        repository.applyToPosting(unrelatedPosting.getPostingId(), "unrelated application");
        assertFalse(matchingRepo.connect(unrelatedPoster.userId()));
        session.setCurrentUserId(unrelatedPoster.userId());
        assertTrue(matchingRepo.connect(unrelatedApplicant.userId()));
        messagingRepo.sendMessage(unrelatedApplicant.userId(), "Unrelated message");

        session.setCurrentUserId(applicant.userId());
        profileRepo.deleteAccount();

        session.setCurrentUserId(poster.userId());
        assertEquals(0, posting.getApplicantCount());
        assertTrue(repository.loadApplicationsForOwnedPostings(
                PostingScope.MINE, List.of(posting)).get(posting.getPostingId()).isEmpty());

        final AuthResult secondApplicant = register("Second", "second@example.com");
        session.setCurrentUserId(secondApplicant.userId());
        repository.applyToPosting(posting.getPostingId(), "message");
        session.setCurrentUserId(poster.userId());
        profileRepo.deleteAccount();
        assertTrue(repository.loadPostings(PostingScope.MINE).isEmpty());
    }

    private AuthResult register(final String firstName, final String email) {
        emailChallenges.save(new EmailVerificationChallenge(email, "123456", Instant.MAX));
        return authRepo.register(new RegisterAccountData(
                firstName, "User", email, "password", "123456"));
    }
}
