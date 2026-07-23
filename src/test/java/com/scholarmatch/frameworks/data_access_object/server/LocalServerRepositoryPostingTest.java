package com.scholarmatch.frameworks.data_access_object.server;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.data_access_object.LocalServerRepository;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.load_postings.PostingScope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalServerRepositoryPostingTest {

    private CurrentUserProvider session;
    private LocalServerRepository repository;

    @BeforeEach
    void setUp() {
        session = new CurrentUserProvider();
        final ClasspathInstitutionCatalogRepository institutions =
                new ClasspathInstitutionCatalogRepository();
        repository = new LocalServerRepository(session, institutions);
    }

    @Test
    void testCreateApplyAndReviewDoNotCascade() {
        session.setCurrentUserId("poster-1");
        final Posting posting = repository.createPosting(
                "Project", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, null);

        session.setCurrentUserId("applicant-1");
        final PostingApplication first = repository.applyToPosting(posting.getPostingId(), "First");
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
    void testSeededAcademicAccountCanLogIn() {
        final AuthResult result = repository.login(
                "demo.student@utoronto.ca", "DemoPass123!");
        session.setCurrentUserId(result.userId());

        assertEquals(EmailAccountType.ACADEMIC, repository.getProfile().getEmailAccountType());
        assertEquals("UNIVERSITY_OF_TORONTO", repository.getProfile().getInstitution().name());
    }

    @Test
    void testAllActiveNeverReturnsApplicantIdentityMap() {
        session.setCurrentUserId("viewer-1");
        final List<Posting> postings = repository.loadPostings(PostingScope.ALL_ACTIVE);

        assertTrue(repository.loadApplicationsForOwnedPostings(
                PostingScope.ALL_ACTIVE, postings).isEmpty());
    }
}
