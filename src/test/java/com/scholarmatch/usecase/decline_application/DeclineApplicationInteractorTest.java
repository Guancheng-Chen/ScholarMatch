package com.scholarmatch.usecase.decline_application;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.usecase.data_access_interface.DeclineApplicationDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeclineApplicationInteractorTest {

    @Test
    void testSuccessAndDaoFailure() {
        final DeclineApplicationDataAccessInterface dao = mock(DeclineApplicationDataAccessInterface.class);
        final DeclineApplicationOutputBoundary output = mock(DeclineApplicationOutputBoundary.class);
        when(dao.declineApplication("application-1")).thenReturn(application());
        final DeclineApplicationInteractor interactor = new DeclineApplicationInteractor(dao, output);

        interactor.execute(new DeclineApplicationInputData("application-1"));

        verify(output).prepareSuccessView(any());
        when(dao.declineApplication("application-2"))
                .thenThrow(new InvalidRequestException("already reviewed"));
        interactor.execute(new DeclineApplicationInputData("application-2"));
        verify(output).prepareFailView("already reviewed");
    }

    private PostingApplication application() {
        return new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Message",
                PostingApplicationStatus.REJECTED, LocalDateTime.now());
    }
}
