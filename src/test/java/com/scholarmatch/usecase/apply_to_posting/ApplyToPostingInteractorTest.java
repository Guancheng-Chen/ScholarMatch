package com.scholarmatch.usecase.apply_to_posting;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.usecase.data_access_interface.ApplyToPostingDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApplyToPostingInteractorTest {

    @Test
    void testSuccessAndDaoFailure() {
        final ApplyToPostingDataAccessInterface dao = mock(ApplyToPostingDataAccessInterface.class);
        final ApplyToPostingOutputBoundary output = mock(ApplyToPostingOutputBoundary.class);
        when(dao.applyToPosting("posting-1", "Message")).thenReturn(application());
        final ApplyToPostingInteractor interactor = new ApplyToPostingInteractor(dao, output);

        interactor.execute(new ApplyToPostingInputData("posting-1", "Message"));

        verify(output).prepareSuccessView(any());

        when(dao.applyToPosting("posting-1", "again"))
                .thenThrow(new InvalidRequestException("You have already applied"));
        interactor.execute(new ApplyToPostingInputData("posting-1", "again"));
        verify(output).prepareFailView("You have already applied");
    }

    private PostingApplication application() {
        return new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Message",
                PostingApplicationStatus.PENDING, LocalDateTime.now());
    }
}
