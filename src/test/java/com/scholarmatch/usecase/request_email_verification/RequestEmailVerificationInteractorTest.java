package com.scholarmatch.usecase.request_email_verification;

import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.exception.ExternalServiceException;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class RequestEmailVerificationInteractorTest {

    @Test
    void testValidEmailSendsCodeAndPreparesSuccessView() {
        final VerificationEmailSenderDataAccessInterface emailSender =
                mock(VerificationEmailSenderDataAccessInterface.class);
        final RequestEmailVerificationOutputBoundary output =
                mock(RequestEmailVerificationOutputBoundary.class);

        new RequestEmailVerificationInteractor(emailSender, output)
                .execute(new RequestEmailVerificationInputData("ada@example.com"));

        verify(emailSender).requestVerificationCode("ada@example.com");
        verify(output).prepareSuccessView(new RequestEmailVerificationOutputData("ada@example.com"));
    }

    @Test
    void testEmailIsTrimmedAndLowercasedBeforeSending() {
        final VerificationEmailSenderDataAccessInterface emailSender =
                mock(VerificationEmailSenderDataAccessInterface.class);
        final RequestEmailVerificationOutputBoundary output =
                mock(RequestEmailVerificationOutputBoundary.class);

        new RequestEmailVerificationInteractor(emailSender, output)
                .execute(new RequestEmailVerificationInputData("  Ada@Example.com  "));

        verify(emailSender).requestVerificationCode("ada@example.com");
        verify(output).prepareSuccessView(new RequestEmailVerificationOutputData("ada@example.com"));
    }

    @Test
    void testInvalidEmailFormatFailsWithoutContactingSender() {
        final VerificationEmailSenderDataAccessInterface emailSender =
                mock(VerificationEmailSenderDataAccessInterface.class);
        final RequestEmailVerificationOutputBoundary output =
                mock(RequestEmailVerificationOutputBoundary.class);

        new RequestEmailVerificationInteractor(emailSender, output)
                .execute(new RequestEmailVerificationInputData("not-an-email"));

        verify(output).prepareFailView("Enter a valid email address before requesting a code.");
        verify(emailSender, never()).requestVerificationCode(any());
    }

    @Test
    void testNullEmailFailsWithoutContactingSender() {
        final VerificationEmailSenderDataAccessInterface emailSender =
                mock(VerificationEmailSenderDataAccessInterface.class);
        final RequestEmailVerificationOutputBoundary output =
                mock(RequestEmailVerificationOutputBoundary.class);

        new RequestEmailVerificationInteractor(emailSender, output)
                .execute(new RequestEmailVerificationInputData(null));

        verify(output).prepareFailView("Enter a valid email address before requesting a code.");
        verify(emailSender, never()).requestVerificationCode(any());
    }

    @Test
    void testSenderFailureIsPresented() {
        final VerificationEmailSenderDataAccessInterface emailSender =
                mock(VerificationEmailSenderDataAccessInterface.class);
        final RequestEmailVerificationOutputBoundary output =
                mock(RequestEmailVerificationOutputBoundary.class);
        doThrow(new ExternalServiceException("email service unavailable"))
                .when(emailSender).requestVerificationCode(any());

        new RequestEmailVerificationInteractor(emailSender, output)
                .execute(new RequestEmailVerificationInputData("ada@example.com"));

        verify(output).prepareFailView("email service unavailable");
    }
}
