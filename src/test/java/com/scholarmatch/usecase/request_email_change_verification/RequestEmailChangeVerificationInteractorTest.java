package com.scholarmatch.usecase.request_email_change_verification;

import com.scholarmatch.usecase.data_access_interface.RequestEmailChangeVerificationDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestEmailChangeVerificationInteractorTest {

    @Test
    void testNormalizesValidEmailAndPresentsSuccess() {
        final AtomicReference<String> requested = new AtomicReference<>();
        final Output output = new Output();
        new RequestEmailChangeVerificationInteractor(
                requested::set, output)
                .execute(new RequestEmailChangeVerificationInputData(
                        "  ADA@EXAMPLE.COM "));

        assertEquals("ada@example.com", requested.get());
        assertEquals("ada@example.com", output.data.email());
        assertNull(output.error);
    }

    @Test
    void testRejectsInvalidEmailAndDataAccessFailure() {
        final Output invalidOutput = new Output();
        new RequestEmailChangeVerificationInteractor(
                email -> { }, invalidOutput)
                .execute(new RequestEmailChangeVerificationInputData("invalid"));
        assertEquals("Enter a valid new email address.", invalidOutput.error);

        final RequestEmailChangeVerificationDataAccessInterface rejected =
                email -> {
                    throw new InvalidRequestException("Email is already registered");
                };
        final Output rejectedOutput = new Output();
        new RequestEmailChangeVerificationInteractor(rejected, rejectedOutput)
                .execute(new RequestEmailChangeVerificationInputData(
                        "ada@example.com"));
        assertEquals("Email is already registered", rejectedOutput.error);
    }

    private static final class Output
            implements RequestEmailChangeVerificationOutputBoundary {

        private RequestEmailChangeVerificationOutputData data;
        private String error;

        @Override
        public void prepareSuccessView(
                final RequestEmailChangeVerificationOutputData outputData) {
            this.data = outputData;
        }

        @Override
        public void prepareFailView(final String errorMessage) {
            this.error = errorMessage;
        }
    }
}
