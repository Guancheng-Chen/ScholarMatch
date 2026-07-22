package com.scholarmatch.usecase.accept_application;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.usecase.data_access_interface.AcceptApplicationDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AcceptApplicationInteractorTest {

    @Test
    void testSuccessAndDaoFailure() {
        final AcceptApplicationDataAccessInterface dao = mock(AcceptApplicationDataAccessInterface.class);
        final AcceptApplicationOutputBoundary output = mock(AcceptApplicationOutputBoundary.class);
        when(dao.acceptApplication("application-1")).thenReturn(application());
        final AcceptApplicationInteractor interactor = new AcceptApplicationInteractor(dao, output);

        interactor.execute(new AcceptApplicationInputData("application-1"));

        verify(output).prepareSuccessView(any());
        when(dao.acceptApplication("application-2"))
                .thenThrow(new InvalidRequestException("not the poster"));
        interactor.execute(new AcceptApplicationInputData("application-2"));
        verify(output).prepareFailView("not the poster");
    }

    private PostingApplication application() {
        return new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Message",
                PostingApplicationStatus.ACCEPTED, LocalDateTime.now());
    }
}
