package com.scholarmatch.usecase.change_password;

import com.scholarmatch.usecase.data_access_interface.ChangePasswordDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ChangePasswordInteractorTest {

    @Test
    void testValidInputChangesPassword() {
        final AtomicReference<String> changed = new AtomicReference<>();
        final Output output = new Output();
        new ChangePasswordInteractor(
                (current, replacement) -> changed.set(replacement), output)
                .execute(new ChangePasswordInputData(
                        "12345678", "abcdefgh", "abcdefgh"));

        assertEquals("abcdefgh", changed.get());
        assertNotNull(output.data);
        assertNull(output.error);
    }

    @Test
    void testRejectsInvalidInputsAndAdapterFailure() {
        final ChangePasswordDataAccessInterface accepted =
                (current, replacement) -> { };

        final Output missingCurrent = new Output();
        new ChangePasswordInteractor(accepted, missingCurrent).execute(
                new ChangePasswordInputData("", "abcdefgh", "abcdefgh"));
        assertEquals("Current password is required.", missingCurrent.error);

        final Output shortPassword = new Output();
        new ChangePasswordInteractor(accepted, shortPassword).execute(
                new ChangePasswordInputData("12345678", "short", "short"));
        assertEquals(
                "New password must be between 8 and 64 characters.",
                shortPassword.error);

        final Output mismatch = new Output();
        new ChangePasswordInteractor(accepted, mismatch).execute(
                new ChangePasswordInputData(
                        "12345678", "abcdefgh", "different"));
        assertEquals("New passwords do not match.", mismatch.error);

        final Output rejected = new Output();
        new ChangePasswordInteractor(
                (current, replacement) -> {
                    throw new InvalidRequestException(
                            "Current password is incorrect");
                }, rejected)
                .execute(new ChangePasswordInputData(
                        "wrongpass", "abcdefgh", "abcdefgh"));
        assertEquals("Current password is incorrect", rejected.error);
    }

    private static final class Output implements ChangePasswordOutputBoundary {

        private ChangePasswordOutputData data;
        private String error;

        @Override
        public void prepareSuccessView(
                final ChangePasswordOutputData outputData) {
            this.data = outputData;
        }

        @Override
        public void prepareFailView(final String errorMessage) {
            this.error = errorMessage;
        }
    }
}
