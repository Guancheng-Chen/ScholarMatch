package com.scholarmatch.usecase.request_email_verification;

import com.scholarmatch.frameworks.data_access_object.InMemoryEmailVerificationChallengeRepository;
import com.scholarmatch.usecase.data_access_interface.VerificationCodeGeneratorInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RequestEmailVerificationInteractorTest {

    private VerificationEmailSenderDataAccessInterface sender;
    private RequestEmailVerificationOutputBoundary outputBoundary;
    private InMemoryEmailVerificationChallengeRepository repository;
    private RequestEmailVerificationInteractor interactor;

    @BeforeEach
    void setUp() {
        sender = mock(VerificationEmailSenderDataAccessInterface.class);
        outputBoundary = mock(RequestEmailVerificationOutputBoundary.class);
        repository = new InMemoryEmailVerificationChallengeRepository();
        final VerificationCodeGeneratorInterface generator = mock(VerificationCodeGeneratorInterface.class);
        when(generator.generateCode()).thenReturn("123456");
        interactor = new RequestEmailVerificationInteractor(
                sender, repository, generator, outputBoundary,
                Clock.fixed(Instant.parse("2026-07-21T12:00:00Z"), ZoneOffset.UTC));
    }

    @Test
    void testSendsAndStoresChallenge() {
        interactor.execute(new RequestEmailVerificationInputData(" Ada@Example.com "));

        verify(sender).sendVerificationCode("ada@example.com", "123456");
        verify(outputBoundary).prepareSuccessView(
                new RequestEmailVerificationOutputData("ada@example.com"));
        assertTrue(repository.findByEmail("ada@example.com").isPresent());
    }

    @Test
    void testInvalidEmailDoesNotSend() {
        interactor.execute(new RequestEmailVerificationInputData("invalid"));

        verify(sender, never()).sendVerificationCode(org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any());
        verify(outputBoundary).prepareFailView(
                "Enter a valid email address before requesting a code.");
    }

    @Test
    void testDeliveryFailureDoesNotStoreChallenge() {
        doThrow(new ExternalServiceException("mail unavailable"))
                .when(sender).sendVerificationCode("ada@example.com", "123456");

        interactor.execute(new RequestEmailVerificationInputData("ada@example.com"));

        verify(outputBoundary).prepareFailView("mail unavailable");
        assertTrue(repository.findByEmail("ada@example.com").isEmpty());
    }
}
