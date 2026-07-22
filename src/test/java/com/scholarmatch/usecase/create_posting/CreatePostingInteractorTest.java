package com.scholarmatch.usecase.create_posting;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.usecase.data_access_interface.CreatePostingDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreatePostingInteractorTest {

    @Test
    void testSuccessReturnsPostingData() {
        final CreatePostingDataAccessInterface dao = mock(CreatePostingDataAccessInterface.class);
        final CreatePostingOutputBoundary output = mock(CreatePostingOutputBoundary.class);
        when(dao.createPosting(any(), any(), any(), any(), any()))
                .thenReturn(posting());

        new CreatePostingInteractor(dao, output).execute(input());

        verify(output).prepareSuccessView(any());
    }

    @Test
    void testDaoFailureIsPresented() {
        final CreatePostingDataAccessInterface dao = mock(CreatePostingDataAccessInterface.class);
        final CreatePostingOutputBoundary output = mock(CreatePostingOutputBoundary.class);
        doThrow(new InvalidRequestException("invalid posting"))
                .when(dao).createPosting(any(), any(), any(), any(), any());

        new CreatePostingInteractor(dao, output).execute(input());

        verify(output).prepareFailView("invalid posting");
    }

    private CreatePostingInputData input() {
        return new CreatePostingInputData(
                "Title", "Description", ResearchField.COMPUTER_SCIENCE,
                CollaborationType.CO_AUTHOR, 3);
    }

    private Posting posting() {
        return new Posting(
                "posting-1", "poster-1", "Title", "Description",
                ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                3, 0, 0, PostingStatus.OPEN, LocalDateTime.now());
    }
}
