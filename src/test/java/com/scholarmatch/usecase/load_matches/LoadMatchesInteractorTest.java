package com.scholarmatch.usecase.load_matches;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.exception.ExternalServiceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoadMatchesInteractorTest {

    private LoadMatchesDataAccessInterface dataAccessObject;
    private LoadMatchesOutputBoundary outputBoundary;
    private LoadMatchesInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccessObject = mock(LoadMatchesDataAccessInterface.class);
        outputBoundary = mock(LoadMatchesOutputBoundary.class);
        interactor = new LoadMatchesInteractor(dataAccessObject, outputBoundary);
    }

    private User matchedUser(final String userId) {
        return new User(
                userId,
                "Ada",
                "Lovelace",
                "ada@example.com",
                "",
                Institution.UNIVERSITY_OF_CAMBRIDGE,
                AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR,
                "",
                "",
                null,
                FundingStatus.UNFUNDED,
                "hash");
    }

    @Test
    void testExecuteReturnsConfirmedMatchesInOrder() {
        when(dataAccessObject.getMatches()).thenReturn(List.of(matchedUser("m1"), matchedUser("m2")));
        interactor.execute();
        final ArgumentCaptor<LoadMatchesOutputData> captor = ArgumentCaptor.forClass(LoadMatchesOutputData.class);
        verify(outputBoundary).prepareSuccessView(captor.capture());
        assertEquals(2, captor.getValue().getMatches().size());
        assertEquals("m1", captor.getValue().getMatches().get(0).getUserId());
        assertEquals("m2", captor.getValue().getMatches().get(1).getUserId());
    }

    @Test
    void testExecuteReturnsEmptyListWhenNoMatches() {
        when(dataAccessObject.getMatches()).thenReturn(List.of());
        interactor.execute();
        final ArgumentCaptor<LoadMatchesOutputData> captor = ArgumentCaptor.forClass(LoadMatchesOutputData.class);
        verify(outputBoundary).prepareSuccessView(captor.capture());
        assertEquals(0, captor.getValue().getMatches().size());
    }

    @Test
    void testExecuteFailsWhenDataAccessThrows() {
        when(dataAccessObject.getMatches()).thenThrow(new ExternalServiceException("Server unreachable"));
        interactor.execute();
        verify(outputBoundary).prepareFailView("Server unreachable");
    }
}
