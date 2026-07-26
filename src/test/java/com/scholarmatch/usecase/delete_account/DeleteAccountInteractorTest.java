package com.scholarmatch.usecase.delete_account;

import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SessionClearerInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class DeleteAccountInteractorTest {

    @Test
    void testSuccessClearsSessionAndPresentsSuccess() {
        final DeleteAccountDataAccessInterface dao =
                mock(DeleteAccountDataAccessInterface.class);
        final SessionClearerInterface session = mock(SessionClearerInterface.class);
        final DeleteAccountOutputBoundary output = mock(DeleteAccountOutputBoundary.class);

        new DeleteAccountInteractor(dao, session, output).execute();

        verify(dao).deleteAccount();
        verify(session).clearSession();
        verify(output).prepareSuccessView();
    }

    @Test
    void testFailureKeepsSessionAndPresentsMessage() {
        final DeleteAccountDataAccessInterface dao =
                mock(DeleteAccountDataAccessInterface.class);
        final SessionClearerInterface session = mock(SessionClearerInterface.class);
        final DeleteAccountOutputBoundary output = mock(DeleteAccountOutputBoundary.class);
        doThrow(new InvalidRequestException("cannot delete")).when(dao).deleteAccount();

        new DeleteAccountInteractor(dao, session, output).execute();

        verify(session, never()).clearSession();
        verify(output).prepareFailView("cannot delete");
    }
}
