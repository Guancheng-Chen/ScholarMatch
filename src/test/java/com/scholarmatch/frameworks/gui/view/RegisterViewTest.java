package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.usecase.register.RegisterInputBoundary;
import com.scholarmatch.usecase.register.RegisterInputData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
        final RegisterView view = new RegisterView(new RegisterController(interactor), new RegisterViewModel());

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
        final RegisterView view = new RegisterView(new RegisterController(interactor), new RegisterViewModel());

        fillForm(view, "  Ada  ", "  Lovelace  ", "  ada@example.com  ", "hunter2", "hunter2");

        findButton(view, "Register").doClick();

        final ArgumentCaptor<RegisterInputData> captor = ArgumentCaptor.forClass(RegisterInputData.class);
        verify(interactor, timeout(2000)).execute(captor.capture());
        assertEquals("Ada", captor.getValue().getFirstName());
        assertEquals("Lovelace", captor.getValue().getLastName());
        assertEquals("ada@example.com", captor.getValue().getEmail());
        assertEquals("hunter2", captor.getValue().getPassword());
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
