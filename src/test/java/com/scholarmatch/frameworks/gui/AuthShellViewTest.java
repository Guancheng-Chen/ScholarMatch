package com.scholarmatch.frameworks.gui;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.LoginController;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.controller.RequestEmailVerificationController;
import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.usecase.login.LoginInputBoundary;
import com.scholarmatch.usecase.register.RegisterInputBoundary;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInputBoundary;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.ArgumentCaptor;

import java.util.concurrent.atomic.AtomicInteger;
import java.lang.reflect.Method;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class AuthShellViewTest {

    @Test
    void testAuthenticationListenersDialogsAndRemoval() throws Exception {
        final LoginViewModel loginViewModel = new LoginViewModel();
        final RegisterViewModel registerViewModel = new RegisterViewModel();
        final AtomicInteger authenticated = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            final AuthShellView shell = new AuthShellView(
                    new LoginController(mock(LoginInputBoundary.class)), loginViewModel,
                    new RegisterController(mock(RegisterInputBoundary.class)),
                    new RequestEmailVerificationController(
                            mock(RequestEmailVerificationInputBoundary.class)),
                    registerViewModel, authenticated::incrementAndGet);
            loginViewModel.setLoggedInUserId(null);
            loginViewModel.setLoggedInUserId("");
            registerViewModel.registrationSucceededProperty().set(null);
            registerViewModel.setRegistrationSucceeded(false);
            loginViewModel.setLoggedInUserId("user-1");
            registerViewModel.setRegistrationSucceeded(true);

            try (MockedConstruction<JDialog> dialogs = mockConstruction(
                    JDialog.class, (dialog, context) -> configure(
                            dialog, context.getCount(), loginViewModel, registerViewModel))) {
                invokeDialog(shell, "showLoginDialog",
                        new LoginController(mock(LoginInputBoundary.class)),
                        SwingTestSupport.find(shell, JButton.class, 0));
                loginViewModel.setLoggedInUserId(null);
                loginViewModel.setLoggedInUserId("");
                loginViewModel.setLoggedInUserId("user-2");
                invokeDialog(shell, "showRegisterDialog",
                        new RegisterController(mock(RegisterInputBoundary.class)),
                        new RequestEmailVerificationController(
                                mock(RequestEmailVerificationInputBoundary.class)),
                        SwingTestSupport.find(shell, JButton.class, 0));
                registerViewModel.registrationSucceededProperty().set(null);
                registerViewModel.setRegistrationSucceeded(false);
                registerViewModel.setRegistrationSucceeded(true);
                assertEquals(2, dialogs.constructed().size());
            }
            shell.removeNotify();
            loginViewModel.setLoggedInUserId("detached");
            registerViewModel.setRegistrationSucceeded(true);
        });
        SwingUtilities.invokeAndWait(() -> { });
        assertEquals(6, authenticated.get());
    }

    @Test
    void testMenuActionLambdas() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try (MockedConstruction<JPopupMenu> menus = mockConstruction(JPopupMenu.class);
                 MockedConstruction<JDialog> dialogs = mockConstruction(
                         JDialog.class, (dialog, context) -> configure(
                                 dialog, context.getCount(), new LoginViewModel(),
                                 new RegisterViewModel()))) {
                final AuthShellView shell = new AuthShellView(
                        new LoginController(mock(LoginInputBoundary.class)), new LoginViewModel(),
                        new RegisterController(mock(RegisterInputBoundary.class)),
                        new RequestEmailVerificationController(
                                mock(RequestEmailVerificationInputBoundary.class)),
                        new RegisterViewModel(), () -> { });
                final JPopupMenu menu = menus.constructed().get(0);
                final ArgumentCaptor<JMenuItem> items = ArgumentCaptor.forClass(JMenuItem.class);
                verify(menu, times(2)).add(items.capture());
                items.getAllValues().forEach(JMenuItem::doClick);
                SwingTestSupport.find(shell, JButton.class, 0).doClick();
                assertEquals(2, dialogs.constructed().size());
            }
        });
    }

    private static void configure(
            final JDialog dialog,
            final int count,
            final LoginViewModel loginViewModel,
            final RegisterViewModel registerViewModel) {
        when(dialog.getContentPane()).thenReturn(new JPanel());
        doAnswer(invocation -> {
            if (count == 1) {
                loginViewModel.setLoggedInUserId(null);
                loginViewModel.setLoggedInUserId("");
                loginViewModel.setLoggedInUserId("dialog-user");
            } else {
                registerViewModel.registrationSucceededProperty().set(null);
                registerViewModel.setRegistrationSucceeded(false);
                registerViewModel.setRegistrationSucceeded(true);
            }
            return null;
        }).when(dialog).setVisible(true);
    }

    private static void invokeDialog(
            final AuthShellView shell, final String name, final Object... arguments) {
        try {
            final Class<?>[] types = java.util.Arrays.stream(arguments)
                    .map(argument -> argument instanceof java.awt.Component
                            ? java.awt.Component.class : argument.getClass())
                    .toArray(Class<?>[]::new);
            final Method method = AuthShellView.class.getDeclaredMethod(name, types);
            method.setAccessible(true);
            method.invoke(shell, arguments);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
