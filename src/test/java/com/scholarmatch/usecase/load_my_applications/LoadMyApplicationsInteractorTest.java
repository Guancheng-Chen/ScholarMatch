package com.scholarmatch.usecase.load_my_applications;

import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.usecase.data_access_interface.LoadMyApplicationsDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoadMyApplicationsInteractorTest {

    @Test
    void testSuccessAndDaoFailure() {
        final LoadMyApplicationsDataAccessInterface dao =
                mock(LoadMyApplicationsDataAccessInterface.class);
        final LoadMyApplicationsOutputBoundary output =
                mock(LoadMyApplicationsOutputBoundary.class);
        when(dao.getMyApplications()).thenReturn(List.of(application()));
        final LoadMyApplicationsInteractor interactor =
                new LoadMyApplicationsInteractor(dao, output);

        interactor.execute();

        verify(output).prepareSuccessView(any());
        when(dao.getMyApplications()).thenThrow(new InvalidRequestException("load failed"));
        interactor.execute();
        verify(output).prepareFailView("load failed");
    }

    private PostingApplication application() {
        return new PostingApplication(
                "application-1", "posting-1", "applicant-1", "Message",
                PostingApplicationStatus.PENDING, LocalDateTime.now());
    }
}
