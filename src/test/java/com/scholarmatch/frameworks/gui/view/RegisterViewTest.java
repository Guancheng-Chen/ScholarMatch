package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.usecase.register.RegisterInputBoundary;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class RegisterViewTest {

    @Test
    void testMismatchedPasswordsShowsErrorDialogAndDoesNotCallController() {
        final RegisterInputBoundary interactor = mock(RegisterInputBoundary.class);
        final RegisterView view = new RegisterView(
                new RegisterController(interactor),
                new RegisterViewModel()
        );

        fillForm(
                view,
                "Ada",
                "Lovelace",
                "ada@example.com",
                "hunter2",
                "different-password"
        );

        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            SwingTestSupport.find(view, JButton.class, 0).doClick();

            optionPane.verify(() -> JOptionPane.showMessageDialog(
                    any(),
                    eq("Passwords do not match"),
                    eq("Register Failed"),
                    eq(JOptionPane.ERROR_MESSAGE)
            ));
        }

        verify(interactor, never()).execute(any());
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
