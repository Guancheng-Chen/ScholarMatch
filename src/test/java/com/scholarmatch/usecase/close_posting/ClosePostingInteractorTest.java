package com.scholarmatch.usecase.close_posting;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.usecase.data_access_interface.ClosePostingDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClosePostingInteractorTest {

    @Test
    void testSuccessAndDaoFailure() {
        final ClosePostingDataAccessInterface dao = mock(ClosePostingDataAccessInterface.class);
        final ClosePostingOutputBoundary output = mock(ClosePostingOutputBoundary.class);
        when(dao.closePosting("posting-1")).thenReturn(posting());
        final ClosePostingInteractor interactor = new ClosePostingInteractor(dao, output);

        interactor.execute(new ClosePostingInputData("posting-1"));

        verify(output).prepareSuccessView(any());
        when(dao.closePosting("posting-2"))
                .thenThrow(new InvalidRequestException("not allowed"));
        interactor.execute(new ClosePostingInputData("posting-2"));
        verify(output).prepareFailView("not allowed");
    }

    private Posting posting() {
        return new Posting(
                "posting-1", "poster-1", "Title", "Description",
                ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                3, 0, 0, PostingStatus.CLOSED, LocalDateTime.now());
    }
}
