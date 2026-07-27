package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.dto.MessageData;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ViewModelTest {

    @Test
    void testChatViewModelExposesState() {
        final ChatViewModel viewModel = new ChatViewModel();
        final MessageData message = mock(MessageData.class);

        viewModel.getMessages().add(message);
        viewModel.setErrorMessage("chat error");

        assertEquals(List.of(message), viewModel.getMessages());
        assertEquals("chat error", viewModel.errorMessageProperty().get());
    }

    @Test
    void testLoadMatchesViewModelExposesState() {
        final LoadMatchesViewModel viewModel = new LoadMatchesViewModel();
        final UserData user = mock(UserData.class);

        viewModel.getMatchedUsers().add(user);
        viewModel.matchNotificationProperty().set(user);
        viewModel.setErrorMessage("match error");

        assertEquals(List.of(user), viewModel.getMatchedUsers());
        assertSame(user, viewModel.matchNotificationProperty().get());
        assertEquals("match error", viewModel.errorMessageProperty().get());
    }

    @Test
    void testLoginAndLogoutViewModelsExposeState() {
        final LoginViewModel login = new LoginViewModel();
        login.setErrorMessage("login error");
        login.setLoggedInUserId("user-1");

        assertEquals("login error", login.errorMessageProperty().get());
        assertEquals("user-1", login.loggedInUserIdProperty().get());

        final LogoutViewModel logout = new LogoutViewModel();
        logout.setLoggedOut();
        assertTrue(logout.loggedOutProperty().get());
    }

    @Test
    void testMyApplicationsViewModelExposesState() {
        final MyApplicationsViewModel viewModel = new MyApplicationsViewModel();
        final PostingApplicationData application = mock(PostingApplicationData.class);

        viewModel.setApplications(List.of(application));
        viewModel.setErrorMessage("application error");

        assertEquals(List.of(application), viewModel.getApplications());
        assertEquals("application error", viewModel.errorMessageProperty().get());
    }

    @Test
    void testMyPostingsViewModelManagesPostingsAndApplications() {
        final MyPostingsViewModel viewModel = new MyPostingsViewModel();
        final PostingData first = posting("posting-1");
        final PostingData second = posting("posting-2");
        final PostingData replacement = posting("posting-1");
        final PostingData added = posting("posting-3");
        final PostingApplicationData target = application("application-1");
        final PostingApplicationData other = application("application-2");
        final PostingApplicationData updated = application("application-1");

        viewModel.setPostings(List.of(first, second));
        viewModel.setApplicationsByPostingId(
                Map.of("posting-1", List.of(target, other), "posting-2", List.of(other)));
        assertEquals(List.of(), viewModel.getApplicationsFor("missing"));

        viewModel.replacePosting(replacement);
        viewModel.updateApplicationStatus(updated);
        viewModel.addPosting(added);
        viewModel.setErrorMessage("posting error");
        viewModel.setSuccessMessage("posting success");
        viewModel.requestRefresh();

        assertEquals(List.of(added, replacement, second), viewModel.getPostings());
        assertEquals(List.of(updated, other), viewModel.getApplicationsFor("posting-1"));
        assertEquals(List.of(other), viewModel.getApplicationsFor("posting-2"));
        assertEquals("posting error", viewModel.errorMessageProperty().get());
        assertEquals("posting success", viewModel.successMessageProperty().get());
        assertEquals(1, viewModel.refreshRequestProperty().get());
    }

    @Test
    void testOpportunitiesViewModelManagesPostings() {
        final OpportunitiesViewModel viewModel = new OpportunitiesViewModel();
        final PostingData retained = posting("posting-1");
        final PostingData removed = posting("posting-2");

        viewModel.setPostings(List.of(retained, removed));
        viewModel.setApplicationsByPostingId(Map.of());
        viewModel.removePosting("posting-2");
        viewModel.setErrorMessage("opportunity error");
        viewModel.setSuccessMessage("opportunity success");
        viewModel.requestRefresh();

        assertEquals(List.of(retained), viewModel.getPostings());
        assertEquals("opportunity error", viewModel.errorMessageProperty().get());
        assertEquals("opportunity success", viewModel.successMessageProperty().get());
        assertEquals(1, viewModel.refreshRequestProperty().get());
    }

    @Test
    void testPaperLookupViewModelExposesState() {
        final PaperLookupViewModel viewModel = new PaperLookupViewModel();
        final AuthorCandidateData candidate = mock(AuthorCandidateData.class);
        final Publication publication = mock(Publication.class);

        viewModel.getAuthorCandidates().add(candidate);
        viewModel.getAuthorPapersFound().add(publication);
        viewModel.setStatusMessage("lookup status");

        assertEquals(List.of(candidate), viewModel.getAuthorCandidates());
        assertEquals(List.of(publication), viewModel.getAuthorPapersFound());
        assertEquals("lookup status", viewModel.statusMessageProperty().get());
    }

    @Test
    void testRegisterViewModelExposesState() {
        final RegisterViewModel viewModel = new RegisterViewModel();

        viewModel.setErrorMessage("register error");
        viewModel.setSuccessMessage("register success");
        viewModel.setRegistrationSucceeded(true);
        viewModel.setVerificationMessage("code sent");
        viewModel.setVerificationError("verification error");

        assertEquals("register error", viewModel.errorMessageProperty().get());
        assertEquals("register success", viewModel.successMessageProperty().get());
        assertTrue(viewModel.registrationSucceededProperty().get());
        assertEquals("code sent", viewModel.verificationMessageProperty().get());
        assertEquals("verification error", viewModel.verificationErrorProperty().get());
    }

    @Test
    void testUpdateProfileViewModelExposesDefensiveState() {
        final UpdateProfileViewModel viewModel = new UpdateProfileViewModel();
        final UserData user = mock(UserData.class);
        final List<Institution> institutions = new ArrayList<>(List.of(Institution.MIT));

        viewModel.setCurrentUser(user);
        viewModel.setErrorMessage("profile error");
        viewModel.setSaveSuccessMessage("profile saved");
        viewModel.setInstitutions(institutions);
        institutions.clear();

        assertSame(user, viewModel.currentUserProperty().get());
        assertEquals("profile error", viewModel.errorMessageProperty().get());
        assertEquals("profile saved", viewModel.saveSuccessMessageProperty().get());
        assertEquals(List.of(Institution.MIT), viewModel.getInstitutions());
    }

    private PostingData posting(final String postingId) {
        final PostingData posting = mock(PostingData.class);
        when(posting.getPostingId()).thenReturn(postingId);
        return posting;
    }

    private PostingApplicationData application(final String applicationId) {
        final PostingApplicationData application = mock(PostingApplicationData.class);
        when(application.getApplicationId()).thenReturn(applicationId);
        return application;
    }
}
