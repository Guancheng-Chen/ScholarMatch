package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.ChangeEmailController;
import com.scholarmatch.interface_adapter.controller.ChangePasswordController;
import com.scholarmatch.interface_adapter.controller.RequestEmailChangeVerificationController;
import com.scholarmatch.interface_adapter.view_model.AccountSettingsViewModel;
import com.scholarmatch.usecase.change_email.ChangeEmailInputBoundary;
import com.scholarmatch.usecase.change_email.ChangeEmailInputData;
import com.scholarmatch.usecase.change_password.ChangePasswordInputBoundary;
import com.scholarmatch.usecase.change_password.ChangePasswordInputData;
import com.scholarmatch.usecase.request_email_change_verification.RequestEmailChangeVerificationInputBoundary;
import com.scholarmatch.usecase.request_email_change_verification.RequestEmailChangeVerificationInputData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class AccountSettingsViewTest {

    @Test
    void testButtonsSubmitTrimmedSettingsData() throws Exception {
        final RequestEmailChangeVerificationInputBoundary request =
                mock(RequestEmailChangeVerificationInputBoundary.class);
        final ChangeEmailInputBoundary changeEmail = mock(ChangeEmailInputBoundary.class);
        final ChangePasswordInputBoundary changePassword = mock(ChangePasswordInputBoundary.class);
        final AccountSettingsView[] holder = new AccountSettingsView[1];

        SwingUtilities.invokeAndWait(() -> {
            holder[0] = view(request, changeEmail, changePassword, new AccountSettingsViewModel());
            final var textFields = SwingTestSupport.findAll(holder[0], JTextField.class);
            final var passwords = SwingTestSupport.findAll(holder[0], JPasswordField.class);
            textFields.get(0).setText("  new@example.com  ");
            textFields.get(1).setText("  123456  ");
            passwords.get(0).setText("email-password");
            passwords.get(1).setText("old-password");
            passwords.get(2).setText("new-password");
            passwords.get(3).setText("new-password");
            button(holder[0], "Send Code").doClick();
            button(holder[0], "Change Email").doClick();
            button(holder[0], "Change Password").doClick();
        });

        final ArgumentCaptor<RequestEmailChangeVerificationInputData> requestData =
                ArgumentCaptor.forClass(RequestEmailChangeVerificationInputData.class);
        verify(request, timeout(2000)).execute(requestData.capture());
        assertEquals("new@example.com", requestData.getValue().email());

        final ArgumentCaptor<ChangeEmailInputData> emailData =
                ArgumentCaptor.forClass(ChangeEmailInputData.class);
        verify(changeEmail, timeout(2000)).execute(emailData.capture());
        assertEquals("new@example.com", emailData.getValue().email());
        assertEquals("email-password", emailData.getValue().currentPassword());
        assertEquals("123456", emailData.getValue().verificationCode());

        final ArgumentCaptor<ChangePasswordInputData> passwordData =
                ArgumentCaptor.forClass(ChangePasswordInputData.class);
        verify(changePassword, timeout(2000)).execute(passwordData.capture());
        assertEquals("old-password", passwordData.getValue().currentPassword());
        assertEquals("new-password", passwordData.getValue().newPassword());
        assertEquals("new-password", passwordData.getValue().confirmPassword());
    }

    @Test
    void testCurrentEmailAndMessagesFollowViewModel() throws Exception {
        final AccountSettingsViewModel viewModel = new AccountSettingsViewModel();
        final AccountSettingsView[] holder = new AccountSettingsView[1];

        SwingUtilities.invokeAndWait(() -> {
            holder[0] = view(
                    mock(RequestEmailChangeVerificationInputBoundary.class),
                    mock(ChangeEmailInputBoundary.class),
                    mock(ChangePasswordInputBoundary.class),
                    viewModel);
            try (MockedStatic<JOptionPane> dialogs = mockStatic(JOptionPane.class)) {
                viewModel.setCurrentEmail("current@example.com");
                viewModel.setSuccessMessage("saved");
                viewModel.setErrorMessage("failed");
                dialogs.verify(() -> JOptionPane.showMessageDialog(
                        any(), eq("saved"), eq("Account Settings"),
                        eq(JOptionPane.INFORMATION_MESSAGE)));
                dialogs.verify(() -> JOptionPane.showMessageDialog(
                        any(), eq("failed"), eq("Account Settings Failed"),
                        eq(JOptionPane.ERROR_MESSAGE)));
            }
        });

        assertEquals("current@example.com",
                SwingTestSupport.findAll(holder[0], JLabel.class).stream()
                        .filter(label -> "current@example.com".equals(label.getText()))
                        .findFirst()
                        .orElseThrow()
                        .getText());
        SwingUtilities.invokeAndWait(holder[0]::removeNotify);
    }

    private AccountSettingsView view(
            final RequestEmailChangeVerificationInputBoundary request,
            final ChangeEmailInputBoundary changeEmail,
            final ChangePasswordInputBoundary changePassword,
            final AccountSettingsViewModel viewModel) {
        return new AccountSettingsView(
                new RequestEmailChangeVerificationController(request),
                new ChangeEmailController(changeEmail),
                new ChangePasswordController(changePassword),
                viewModel);
    }

    private JButton button(final AccountSettingsView view, final String text) {
        return SwingTestSupport.findAll(view, JButton.class).stream()
                .filter(button -> text.equals(button.getText()))
                .findFirst()
                .orElseThrow();
    }
}
