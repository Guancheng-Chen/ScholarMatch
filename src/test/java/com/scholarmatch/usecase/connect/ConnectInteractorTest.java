package com.scholarmatch.usecase.connect;

import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConnectInteractorTest {

    @Test
    void testMatchIsPresentedWithConnectedUser() {
        final ConnectDataAccessInterface dao = mock(ConnectDataAccessInterface.class);
        final ConnectOutputBoundary output = mock(ConnectOutputBoundary.class);
        final UserData user = mock(UserData.class);
        when(dao.connect("user-2")).thenReturn(true);

        new ConnectInteractor(dao, output).execute(new ConnectInputData("user-2", user));

        verify(output).prepareMatchFound(any(ConnectOutputData.class));
    }

    @Test
    void testNoMatchIsPresented() {
        final ConnectDataAccessInterface dao = mock(ConnectDataAccessInterface.class);
        final ConnectOutputBoundary output = mock(ConnectOutputBoundary.class);

        new ConnectInteractor(dao, output).execute(
                new ConnectInputData("user-2", mock(UserData.class)));

        verify(output).prepareNoMatch();
    }

    @Test
    void testDataAccessFailureIsPresentedAsNoMatch() {
        final ConnectDataAccessInterface dao = mock(ConnectDataAccessInterface.class);
        final ConnectOutputBoundary output = mock(ConnectOutputBoundary.class);
        when(dao.connect("user-2")).thenThrow(new InvalidRequestException("unavailable"));

        new ConnectInteractor(dao, output).execute(
                new ConnectInputData("user-2", mock(UserData.class)));

        verify(output).prepareNoMatch();
    }
}
