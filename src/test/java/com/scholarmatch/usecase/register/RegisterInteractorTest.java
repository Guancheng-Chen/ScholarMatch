package com.scholarmatch.usecase.register;

import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.EmailVerificationChallenge;
import com.scholarmatch.frameworks.data_access_object.InMemoryEmailVerificationChallengeRepository;
import com.scholarmatch.usecase.data_access_interface.AcademicEmailDomainDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SessionWriterInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterInteractorTest {

    private RegisterDataAccessInterface gateway;
    private SessionWriterInterface session;
    private RegisterOutputBoundary outputBoundary;
    private AcademicEmailDomainDataAccessInterface academicEmailDomains;
    private RegisterInteractor interactor;
    private InMemoryEmailVerificationChallengeRepository challenges;
    private Clock clock;

    @BeforeEach
    void setUp() {
        gateway = mock(RegisterDataAccessInterface.class);
        session = mock(SessionWriterInterface.class);
        outputBoundary = mock(RegisterOutputBoundary.class);
        academicEmailDomains = mock(AcademicEmailDomainDataAccessInterface.class);
        challenges = new InMemoryEmailVerificationChallengeRepository();
        clock = Clock.fixed(Instant.parse("2026-07-21T12:00:00Z"), ZoneOffset.UTC);
        interactor = new RegisterInteractor(
                gateway, session, outputBoundary, academicEmailDomains, challenges, clock);
        when(gateway.register(any())).thenReturn(new AuthResult("token-1", "user-1", "Ada Lovelace"));
    }

    @Test
    void testAcademicEmailCreatesAcademicAccount() {
        when(academicEmailDomains.isAcademicEmail("ada@mit.edu")).thenReturn(true);
        saveChallenge("ada@mit.edu");

        interactor.execute(new RegisterInputData(
                "Ada", "Lovelace", "ada@mit.edu", "password1", "123456"));

        assertEquals(EmailAccountType.ACADEMIC, captureAccountData().getEmailAccountType());
        verify(session).setCurrentUserId("user-1");
        verify(session).setToken("token-1");
        verify(outputBoundary).prepareSuccessView(any());
    }

    @Test
    void testUnknownDomainCreatesRegularAccount() {
        when(academicEmailDomains.isAcademicEmail("ada@example.com")).thenReturn(false);
        saveChallenge("ada@example.com");

        interactor.execute(new RegisterInputData(
                "Ada", "Lovelace", "ada@example.com", "password1", "123456"));

        assertEquals(EmailAccountType.REGULAR, captureAccountData().getEmailAccountType());
    }

    @Test
    void testInvalidEmailIsNotClassifiedOrRegistered() {
        interactor.execute(new RegisterInputData(
                "Ada", "Lovelace", "invalid", "password1", ""));

        verify(academicEmailDomains, never()).isAcademicEmail(any());
        verify(gateway, never()).register(any());
        verify(outputBoundary).prepareFailView(any());
    }

    @Test
    void testMissingChallengeCannotRegister() {
        interactor.execute(new RegisterInputData(
                "Ada", "Lovelace", "ada@example.com", "password1", "123456"));

        verify(gateway, never()).register(any());
        verify(outputBoundary).prepareFailView(
                "Request a verification code for this email before registering.");
    }

    @Test
    void testThirdWrongCodeShowsRegistrationFailure() {
        saveChallenge("ada@example.com");
        final RegisterInputData input = new RegisterInputData(
                "Ada", "Lovelace", "ada@example.com", "password1", "000000");

        interactor.execute(input);
        interactor.execute(input);
        interactor.execute(input);

        verify(gateway, never()).register(any());
        verify(outputBoundary).prepareFailView(
                "Registration failed: verification code is incorrect. "
                        + "No attempts remaining. Request a new code.");
    }

    private RegisterAccountData captureAccountData() {
        final ArgumentCaptor<RegisterAccountData> captor = ArgumentCaptor.forClass(RegisterAccountData.class);
        verify(gateway).register(captor.capture());
        return captor.getValue();
    }

    private void saveChallenge(final String email) {
        challenges.save(new EmailVerificationChallenge(
                email, "123456", clock.instant().plusSeconds(600)));
    }
}
