package com.scholarmatch.usecase.recommend;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;
import com.scholarmatch.usecase.exception.ExternalServiceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecommendInteractorTest {

    private RecommendDataAccessInterface dataAccessObject;
    private RecommendOutputBoundary outputBoundary;
    private RecommendInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccessObject = mock(RecommendDataAccessInterface.class);
        outputBoundary = mock(RecommendOutputBoundary.class);
        interactor = new RecommendInteractor(dataAccessObject, outputBoundary);
    }

    private User completeProfileUser() {
        return new User(
                "current-user",
                "Jane",
                "Doe",
                "jane@example.com",
                "555-1234",
                Institution.MIT,
                AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR,
                "Looking for collaborators",
                "Deep learning research",
                10,
                FundingStatus.UNFUNDED,
                "hash");
    }

    private User incompleteProfileUser() {
        return new User(
                "current-user",
                "Jane",
                "Doe",
                "jane@example.com",
                "",
                null,
                null,
                null,
                null,
                "",
                "",
                null,
                null,
                "hash");
    }

    @Test
    void testExecuteReturnsRecommendationsWhenProfileComplete() {
        when(dataAccessObject.getProfile()).thenReturn(completeProfileUser());
        when(dataAccessObject.getRecommendations()).thenReturn(List.of(completeProfileUser()));

        interactor.execute();

        final ArgumentCaptor<RecommendOutputData> captor = ArgumentCaptor.forClass(RecommendOutputData.class);
        verify(outputBoundary).prepareSuccessView(captor.capture());
        assertEquals(1, captor.getValue().getRecommendations().size());
        assertEquals("current-user", captor.getValue().getRecommendations().get(0).getUserId());
        verify(outputBoundary, never()).prepareFailView(anyString());
    }

    @Test
    void testExecuteFailsWhenProfileIncomplete() {
        when(dataAccessObject.getProfile()).thenReturn(incompleteProfileUser());

        interactor.execute();

        verify(outputBoundary).prepareFailView(RecommendInteractor.INCOMPLETE_PROFILE_MESSAGE);
        verify(dataAccessObject, never()).getRecommendations();
    }

    @Test
    void testExecuteFailsWhenDataAccessThrows() {
        when(dataAccessObject.getProfile()).thenThrow(new ExternalServiceException("Server unreachable"));

        interactor.execute();

        verify(outputBoundary).prepareFailView("Server unreachable");
    }
}
