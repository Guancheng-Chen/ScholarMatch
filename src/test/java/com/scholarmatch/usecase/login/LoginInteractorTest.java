package com.scholarmatch.usecase.login;

import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SessionWriterInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginInteractorTest {

    @Test
    void testSuccessStoresSessionAndPresentsIdentity() {
        final LoginDataAccessInterface dao = mock(LoginDataAccessInterface.class);
        final SessionWriterInterface session = mock(SessionWriterInterface.class);
        final LoginOutputBoundary output = mock(LoginOutputBoundary.class);
        when(dao.login("ada@example.edu", "password"))
                .thenReturn(new AuthResult("token", "user-1", "Ada Lovelace"));

        new LoginInteractor(dao, session, output).execute(
                new LoginInputData("ada@example.edu", "password"));

        verify(session).setCurrentUserId("user-1");
        verify(session).setToken("token");
        verify(output).prepareSuccessView(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void testFailureDoesNotWriteSession() {
        final LoginDataAccessInterface dao = mock(LoginDataAccessInterface.class);
        final SessionWriterInterface session = mock(SessionWriterInterface.class);
        final LoginOutputBoundary output = mock(LoginOutputBoundary.class);
        when(dao.login("ada@example.edu", "wrong"))
                .thenThrow(new InvalidRequestException("invalid credentials"));

        new LoginInteractor(dao, session, output).execute(
                new LoginInputData("ada@example.edu", "wrong"));

        verify(session, never()).setCurrentUserId(org.mockito.ArgumentMatchers.any());
        verify(session, never()).setToken(org.mockito.ArgumentMatchers.any());
        verify(output).prepareFailView("invalid credentials");
    }
}
