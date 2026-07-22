package com.scholarmatch.usecase.load_postings;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.usecase.data_access_interface.LoadPostingsDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the load postings interactor.
 */
class LoadPostingsInteractorTest {

    @Test
    void testAllActiveDoesNotExposeApplicantMap() {
        final LoadPostingsDataAccessInterface dao = mock(LoadPostingsDataAccessInterface.class);
        final LoadPostingsOutputBoundary output = mock(LoadPostingsOutputBoundary.class);
        when(dao.loadPostings(PostingScope.ALL_ACTIVE)).thenReturn(List.of(posting()));
        when(dao.loadApplicationsForOwnedPostings(any(), any())).thenReturn(Map.of());

        new LoadPostingsInteractor(dao, output)
                .execute(new LoadPostingsInputData(PostingScope.ALL_ACTIVE));

        final ArgumentCaptor<LoadPostingsOutputData> captor =
                ArgumentCaptor.forClass(LoadPostingsOutputData.class);
        verify(output).prepareSuccessView(captor.capture());
        assertTrue(captor.getValue().applicationsByPostingId().isEmpty());
    }

    @Test
    void testMineIncludesApplicantMap() {
        final LoadPostingsDataAccessInterface dao = mock(LoadPostingsDataAccessInterface.class);
        final LoadPostingsOutputBoundary output = mock(LoadPostingsOutputBoundary.class);
        final Posting posting = posting();
        when(dao.loadPostings(PostingScope.MINE)).thenReturn(List.of(posting));
        when(dao.loadApplicationsForOwnedPostings(PostingScope.MINE, List.of(posting)))
                .thenReturn(Map.of("posting-1", List.of(application())));

        new LoadPostingsInteractor(dao, output)
                .execute(new LoadPostingsInputData(PostingScope.MINE));

        verify(output).prepareSuccessView(any());
    }

    @Test
    void testDaoFailureIsPresented() {
        final LoadPostingsDataAccessInterface dao = mock(LoadPostingsDataAccessInterface.class);
        final LoadPostingsOutputBoundary output = mock(LoadPostingsOutputBoundary.class);
        when(dao.loadPostings(PostingScope.MINE))
                .thenThrow(new InvalidRequestException("load failed"));

        new LoadPostingsInteractor(dao, output)
                .execute(new LoadPostingsInputData(PostingScope.MINE));

        verify(output).prepareFailView("load failed");
    }

    /**
     * Creates a posting used by the interactor tests.
     *
     * @return a test posting
     */
    private Posting posting() {
        return new Posting(
                "posting-1", "poster-1", "Title", "Description",
                ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                null, 1, 0, PostingStatus.OPEN, LocalDateTime.now());
    }

    /**
     * Creates a posting application used by the interactor tests.
     *
     * @return a test posting application
     */
    private PostingApplication application() {
        return new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Message",
                PostingApplicationStatus.PENDING, LocalDateTime.now());
    }
}
