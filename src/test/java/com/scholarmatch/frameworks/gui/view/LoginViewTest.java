package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.LoginController;
import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import com.scholarmatch.usecase.login.LoginInputBoundary;
import com.scholarmatch.usecase.login.LoginInputData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class LoginViewTest {

    @Test
    void testEmptyEmailShowsValidationErrorAndDoesNotCallController() {
        final LoginInputBoundary interactor = mock(LoginInputBoundary.class);
        final LoginView view = new LoginView(new LoginController(interactor), new LoginViewModel());

        SwingTestSupport.find(view, JTextField.class, 0).setText("");
        SwingTestSupport.find(view, JPasswordField.class, 0).setText("somepassword");
        SwingTestSupport.find(view, JButton.class, 0).doClick();

        final JLabel validationLabel = SwingTestSupport.find(view, JLabel.class, 0);
        assertTrue(validationLabel.isVisible());
        assertEquals("Please enter an email address", validationLabel.getText());
        verify(interactor, never()).execute(any());
    }

    @Test
    void testMalformedEmailShowsValidationErrorAndDoesNotCallController() {
        final LoginInputBoundary interactor = mock(LoginInputBoundary.class);
        final LoginView view = new LoginView(new LoginController(interactor), new LoginViewModel());

        SwingTestSupport.find(view, JTextField.class, 0).setText("not-an-email");
        SwingTestSupport.find(view, JPasswordField.class, 0).setText("somepassword");
        SwingTestSupport.find(view, JButton.class, 0).doClick();

        final JLabel validationLabel = SwingTestSupport.find(view, JLabel.class, 0);
        assertTrue(validationLabel.isVisible());
        assertEquals("Email format is invalid, e.g. name@example.com", validationLabel.getText());
        verify(interactor, never()).execute(any());
    }

    @Test
    void testEmptyPasswordShowsValidationErrorAndDoesNotCallController() {
        final LoginInputBoundary interactor = mock(LoginInputBoundary.class);
        final LoginView view = new LoginView(new LoginController(interactor), new LoginViewModel());

        SwingTestSupport.find(view, JTextField.class, 0).setText("person@example.com");
        SwingTestSupport.find(view, JPasswordField.class, 0).setText("");
        SwingTestSupport.find(view, JButton.class, 0).doClick();

        final JLabel validationLabel = SwingTestSupport.find(view, JLabel.class, 0);
        assertTrue(validationLabel.isVisible());
        assertEquals("Please enter a password", validationLabel.getText());
        verify(interactor, never()).execute(any());
    }

    @Test
    void testValidCredentialsSubmitLoginWithTrimmedEmail() {
        final LoginInputBoundary interactor = mock(LoginInputBoundary.class);
        final LoginView view = new LoginView(new LoginController(interactor), new LoginViewModel());

        SwingTestSupport.find(view, JTextField.class, 0).setText("  person@example.com  ");
        SwingTestSupport.find(view, JPasswordField.class, 0).setText("hunter2");
        SwingTestSupport.find(view, JButton.class, 0).doClick();

        final ArgumentCaptor<LoginInputData> captor = ArgumentCaptor.forClass(LoginInputData.class);
        verify(interactor, timeout(2000)).execute(captor.capture());
        assertEquals("person@example.com", captor.getValue().getEmail());
        assertEquals("hunter2", captor.getValue().getPassword());
    }
}
