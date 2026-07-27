package com.scholarmatch.usecase.dislike;

import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DislikeInteractorTest {

    @Test
    void testSuccessIsPresented() {
        final DislikeDataAccessInterface dao = mock(DislikeDataAccessInterface.class);
        final DislikeOutputBoundary output = mock(DislikeOutputBoundary.class);

        new DislikeInteractor(dao, output).execute(new DislikeInputData("user-2"));

        verify(dao).dislike("user-2");
        verify(output).prepareSuccessView();
    }

    @Test
    void testFailureIsPresented() {
        final DislikeDataAccessInterface dao = mock(DislikeDataAccessInterface.class);
        final DislikeOutputBoundary output = mock(DislikeOutputBoundary.class);
        doThrow(new InvalidRequestException("cannot dislike")).when(dao).dislike("user-2");

        new DislikeInteractor(dao, output).execute(new DislikeInputData("user-2"));

        verify(output).prepareFailView("cannot dislike");
    }
}
