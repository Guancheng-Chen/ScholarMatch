package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.controller.RequestEmailVerificationController;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.usecase.register.RegisterInputBoundary;
import com.scholarmatch.usecase.register.RegisterInputData;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInputBoundary;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class RegisterViewTest {

    @Test
    void testMismatchedPasswordsShowsErrorDialogAndDoesNotCallController() {
        final RegisterInputBoundary interactor = mock(RegisterInputBoundary.class);
        final RegisterView view = new RegisterView(
                new RegisterController(interactor),
                new RequestEmailVerificationController(mock(RequestEmailVerificationInputBoundary.class)),
                new RegisterViewModel());

        fillForm(view, "Ada", "Lovelace", "ada@example.com", "hunter2", "different-password");

        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            findButton(view, "Register").doClick();

            optionPane.verify(() -> JOptionPane.showMessageDialog(
                    any(), eq("Passwords do not match"), eq("Register Failed"), eq(JOptionPane.ERROR_MESSAGE)));
        }
        verify(interactor, never()).execute(any());
    }

    @Test
    void testMatchingPasswordsSubmitRegistrationWithTrimmedFields() {
        final RegisterInputBoundary interactor = mock(RegisterInputBoundary.class);
        final RegisterView view = new RegisterView(
                new RegisterController(interactor),
                new RequestEmailVerificationController(mock(RequestEmailVerificationInputBoundary.class)),
                new RegisterViewModel());

        fillForm(view, "  Ada  ", "  Lovelace  ", "  ada@example.com  ", "hunter2", "hunter2");

        findButton(view, "Register").doClick();

        final ArgumentCaptor<RegisterInputData> captor = ArgumentCaptor.forClass(RegisterInputData.class);
        verify(interactor, timeout(2000)).execute(captor.capture());
        assertEquals("Ada", captor.getValue().getFirstName());
        assertEquals("Lovelace", captor.getValue().getLastName());
        assertEquals("ada@example.com", captor.getValue().getEmail());
        assertEquals("hunter2", captor.getValue().getPassword());
    }

    @Test
    void testSendCodeMessagesAndListenerRemoval() throws Exception {
        final RequestEmailVerificationInputBoundary verification =
                mock(RequestEmailVerificationInputBoundary.class);
        final RegisterViewModel viewModel = new RegisterViewModel();
        final RegisterView[] holder = new RegisterView[1];
        SwingUtilities.invokeAndWait(() -> {
            holder[0] = new RegisterView(
                    new RegisterController(mock(RegisterInputBoundary.class)),
                    new RequestEmailVerificationController(verification), viewModel);
            SwingTestSupport.find(holder[0], JTextField.class, 2).setText("  ada@example.com  ");
            findButton(holder[0], "Send Code").doClick();
        });
        verify(verification, timeout(2000)).execute(
                org.mockito.ArgumentMatchers.argThat(data ->
                        "ada@example.com".equals(data.email())));
        SwingUtilities.invokeAndWait(() -> {
            try (MockedStatic<JOptionPane> dialogs = mockStatic(JOptionPane.class)) {
                viewModel.setErrorMessage(null);
                viewModel.setErrorMessage(" ");
                viewModel.setVerificationMessage(null);
                viewModel.setVerificationMessage(" ");
                viewModel.setVerificationError(null);
                viewModel.setVerificationError(" ");
                viewModel.setErrorMessage("register error");
                viewModel.setVerificationMessage("code sent");
                viewModel.setVerificationError("send failed");
                dialogs.verify(() -> JOptionPane.showMessageDialog(
                        any(), eq("register error"), eq("Register Failed"),
                        eq(JOptionPane.ERROR_MESSAGE)));
                dialogs.verify(() -> JOptionPane.showMessageDialog(
                        any(), eq("code sent"), eq("Verification Code"),
                        eq(JOptionPane.INFORMATION_MESSAGE)));
                dialogs.verify(() -> JOptionPane.showMessageDialog(
                        any(), eq("send failed"), eq("Send Code Failed"),
                        eq(JOptionPane.ERROR_MESSAGE)));
            }
            holder[0].removeNotify();
        });
    }

    private JButton findButton(final RegisterView view, final String text) {
        return SwingTestSupport.findAll(view, JButton.class).stream()
                .filter(button -> text.equals(button.getText()))
                .findFirst()
                .orElseThrow();
    }

    private void fillForm(
            final RegisterView view,
            final String firstName,
            final String lastName,
            final String email,
            final String password,
            final String confirmPassword) {
        SwingTestSupport.find(view, JTextField.class, 0).setText(firstName);
        SwingTestSupport.find(view, JTextField.class, 1).setText(lastName);
        SwingTestSupport.find(view, JTextField.class, 2).setText(email);
        SwingTestSupport.find(view, JPasswordField.class, 0).setText(password);
        SwingTestSupport.find(view, JPasswordField.class, 1).setText(confirmPassword);
    }
}
