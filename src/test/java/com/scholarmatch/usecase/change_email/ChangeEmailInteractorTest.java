package com.scholarmatch.usecase.change_email;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.ChangeEmailDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ChangeEmailInteractorTest {

    @Test
    void testValidInputChangesNormalizedEmail() {
        final AtomicReference<String> email = new AtomicReference<>();
        final ChangeEmailDataAccessInterface dataAccess =
                (value, password, code) -> {
                    email.set(value);
                    return user(value);
                };
        final Output output = new Output();

        new ChangeEmailInteractor(dataAccess, output).execute(
                new ChangeEmailInputData(
                        " NEW@EXAMPLE.COM ", "password", "123456"));

        assertEquals("new@example.com", email.get());
        assertEquals("new@example.com", output.data.user().getEmail());
        assertNull(output.error);
    }

    @Test
    void testRejectsMissingFieldsAndAdapterFailure() {
        final Output invalidEmail = new Output();
        new ChangeEmailInteractor(
                (email, password, code) -> user(email), invalidEmail)
                .execute(new ChangeEmailInputData(
                        "invalid", "password", "123456"));
        assertEquals("Enter a valid new email address.", invalidEmail.error);

        final Output missingPassword = new Output();
        new ChangeEmailInteractor(
                (email, password, code) -> user(email), missingPassword)
                .execute(new ChangeEmailInputData(
                        "new@example.com", "", "123456"));
        assertEquals("Current password is required.", missingPassword.error);

        final Output missingCode = new Output();
        new ChangeEmailInteractor(
                (email, password, code) -> user(email), missingCode)
                .execute(new ChangeEmailInputData(
                        "new@example.com", "password", ""));
        assertEquals("Verification code is required.", missingCode.error);

        final Output rejected = new Output();
        new ChangeEmailInteractor(
                (email, password, code) -> {
                    throw new InvalidRequestException(
                            "Verification code has expired");
                }, rejected)
                .execute(new ChangeEmailInputData(
                        "new@example.com", "password", "123456"));
        assertEquals("Verification code has expired", rejected.error);

        final Output nullEmail = new Output();
        new ChangeEmailInteractor(
                (email, password, code) -> user(email), nullEmail)
                .execute(new ChangeEmailInputData(null, "password", "123456"));
        assertEquals("Enter a valid new email address.", nullEmail.error);

        final Output nullPassword = new Output();
        new ChangeEmailInteractor(
                (email, password, code) -> user(email), nullPassword)
                .execute(new ChangeEmailInputData("new@example.com", null, "123456"));
        assertEquals("Current password is required.", nullPassword.error);
    }

    private static User user(final String email) {
        return new User(
                "u-1", "Ada", "Lovelace", email, "", null, null,
                null, null, "", "", null, null, "password");
    }

    private static final class Output implements ChangeEmailOutputBoundary {

        private ChangeEmailOutputData data;
        private String error;

        @Override
        public void prepareSuccessView(final ChangeEmailOutputData outputData) {
            this.data = outputData;
        }

        @Override
        public void prepareFailView(final String errorMessage) {
            this.error = errorMessage;
        }
    }
}
