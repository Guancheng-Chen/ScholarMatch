package com.scholarmatch.usecase.register;

import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SessionWriterInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterInteractorTest {

    @Test
    void testSuccessStoresSessionAndPreparesSuccessView() {
        final RegisterDataAccessInterface dao = mock(RegisterDataAccessInterface.class);
        final SessionWriterInterface sessionManager = mock(SessionWriterInterface.class);
        final RegisterOutputBoundary output = mock(RegisterOutputBoundary.class);
        when(dao.register(any())).thenReturn(new AuthResult("jwt-token", "user-1", "Ada Lovelace"));

        new RegisterInteractor(dao, sessionManager, output).execute(input());

        verify(sessionManager).setCurrentUserId("user-1");
        verify(sessionManager).setToken("jwt-token");
        verify(output).prepareSuccessView(any());
    }

    @Test
    void testDaoFailureIsPresentedAndSessionIsNotWritten() {
        final RegisterDataAccessInterface dao = mock(RegisterDataAccessInterface.class);
        final SessionWriterInterface sessionManager = mock(SessionWriterInterface.class);
        final RegisterOutputBoundary output = mock(RegisterOutputBoundary.class);
        doThrow(new InvalidRequestException("email already registered")).when(dao).register(any());

        new RegisterInteractor(dao, sessionManager, output).execute(input());

        verify(output).prepareFailView("email already registered");
        verify(sessionManager, never()).setCurrentUserId(any());
        verify(sessionManager, never()).setToken(any());
    }

    @Test
    void testBlankNamesFailValidationWithoutCallingDao() {
        final RegisterDataAccessInterface dao = mock(RegisterDataAccessInterface.class);
        final SessionWriterInterface sessionManager = mock(SessionWriterInterface.class);
        final RegisterOutputBoundary output = mock(RegisterOutputBoundary.class);

        new RegisterInteractor(dao, sessionManager, output).execute(
                new RegisterInputData("", "", "ada@example.com", "supersecret", "123456"));

        verify(output).prepareFailView("First name is required.\nLast name is required.");
        verify(dao, never()).register(any());
    }

    @Test
    void testInvalidEmailFormatFailsValidation() {
        final RegisterDataAccessInterface dao = mock(RegisterDataAccessInterface.class);
        final SessionWriterInterface sessionManager = mock(SessionWriterInterface.class);
        final RegisterOutputBoundary output = mock(RegisterOutputBoundary.class);

        new RegisterInteractor(dao, sessionManager, output).execute(
                new RegisterInputData("Ada", "Lovelace", "not-an-email", "supersecret", "123456"));

        verify(output).prepareFailView("Email format is invalid, e.g. name@example.com.");
        verify(dao, never()).register(any());
    }

    @Test
    void testPasswordTooShortFailsValidation() {
        final RegisterDataAccessInterface dao = mock(RegisterDataAccessInterface.class);
        final SessionWriterInterface sessionManager = mock(SessionWriterInterface.class);
        final RegisterOutputBoundary output = mock(RegisterOutputBoundary.class);

        new RegisterInteractor(dao, sessionManager, output).execute(
                new RegisterInputData("Ada", "Lovelace", "ada@example.com", "short", "123456"));

        verify(output).prepareFailView("Password must be between 8 and 64 characters (currently 5).");
        verify(dao, never()).register(any());
    }

    @Test
    void testBlankPasswordFailsValidation() {
        final RegisterDataAccessInterface dao = mock(RegisterDataAccessInterface.class);
        final SessionWriterInterface sessionManager = mock(SessionWriterInterface.class);
        final RegisterOutputBoundary output = mock(RegisterOutputBoundary.class);

        new RegisterInteractor(dao, sessionManager, output).execute(
                new RegisterInputData("Ada", "Lovelace", "ada@example.com", "", "123456"));

        verify(output).prepareFailView("Password is required.");
        verify(dao, never()).register(any());
    }

    private RegisterInputData input() {
        return new RegisterInputData("Ada", "Lovelace", "ada@example.com", "supersecret", "123456");
    }
}
