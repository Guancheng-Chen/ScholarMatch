package com.scholarmatch.frameworks.gui;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.frameworks.gui.view.LoginView;
import com.scholarmatch.frameworks.gui.view.RegisterView;
import com.scholarmatch.interface_adapter.controller.LoginController;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.util.function.Consumer;

/**
 * Shell shown before the user is authenticated.
 *
 * <p>Same top bar as the authenticated layout (logo + a single "Login / Register" entry
 * point in the top-left corner) with an empty content area — nothing loads until the user
 * picks Login or Register from the menu, each of which opens as its own closable modal
 * dialog rather than being embedded in the shell.
 *
 * <p>Calls the given onAuthenticated callback once login or registration succeeds — the
 * session is already set by the interactor at that point (see LoginInteractor /
 * RegisterInteractor), so this view only needs to signal the caller (MainView) to
 * swap to the authenticated layout, not manage the session itself.
 */
final class AuthShellView extends JPanel {

    /**
     * Constructs the AuthShellView.
     *
     * @param loginController        handles login form submission
     * @param loginViewModel         observable login state
     * @param registerController     handles registration form submission
     * @param registerViewModel      observable registration state
     * @param onAuthenticated        invoked once login or registration succeeds
     */
    AuthShellView(
        final LoginController loginController,
        final LoginViewModel loginViewModel,
        final RegisterController registerController,
        final RegisterViewModel registerViewModel,
        final Runnable onAuthenticated) {
        super(new BorderLayout());
        setBackground(Theme.BG_DEFAULT);

        final JLabel logoLabel = new JLabel("ScholarMatch");
        logoLabel.setFont(logoLabel.getFont().deriveFont(Font.BOLD, 18f));
        logoLabel.setForeground(Theme.FG_DEFAULT);

        final JButton authButton = new JButton("Login / Register");
        Buttons.accent(authButton);

        final JPanel centerArea = new JPanel(new BorderLayout());
        centerArea.setOpaque(false);

        final JPopupMenu authMenu = new JPopupMenu();
        final JMenuItem loginItem = new JMenuItem("Login");
        final JMenuItem registerItem = new JMenuItem("Register");
        loginItem.addActionListener(evt -> showLoginDialog(loginController, loginViewModel, authButton));
        registerItem.addActionListener(evt -> showRegisterDialog(registerController, registerViewModel, authButton));
        authMenu.add(loginItem);
        authMenu.add(registerItem);
        authButton.addActionListener(evt -> authMenu.show(authButton, 0, authButton.getHeight()));

        final JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.BG_SUBTLE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(14, 24, 14, 24)));
        topBar.add(authButton, BorderLayout.WEST);
        topBar.add(logoLabel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
        add(centerArea, BorderLayout.CENTER);

        // Session is already set by the interactor on success — just notify the caller.
        loginViewModel.loggedInUserIdProperty().addListener(newId -> {
            if (newId != null && !newId.isEmpty()) {
                SwingUtilities.invokeLater(onAuthenticated);
            }
        });
        registerViewModel.registrationSucceededProperty().addListener(succeeded -> {
            if (Boolean.TRUE.equals(succeeded)) {
                SwingUtilities.invokeLater(onAuthenticated);
            }
        });
    }

    /**
     * Opens the login form as a small, fixed-size, closable popup window instead of
     * embedding it in the shell — the user can dismiss it (native close button) without
     * logging in, and it closes itself automatically once login succeeds.
     */
    private void showLoginDialog(
        final LoginController loginController,
        final LoginViewModel loginViewModel,
        final Component ownerComponent) {
        final LoginView loginView = new LoginView(loginController, loginViewModel);

        final Window ownerWindow = SwingUtilities.getWindowAncestor(ownerComponent);
        final JDialog dialog = new JDialog(ownerWindow, "Login", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.getContentPane().add(loginView);
        dialog.getContentPane().setBackground(Theme.BG_DEFAULT);
        dialog.pack();
        dialog.setLocationRelativeTo(ownerWindow);

        final Consumer<String>[] closeOnSuccess = new Consumer[1];
        closeOnSuccess[0] = newId -> {
            if (newId != null && !newId.isEmpty()) {
                SwingUtilities.invokeLater(dialog::dispose);
                loginViewModel.loggedInUserIdProperty().removeListener(closeOnSuccess[0]);
            }
        };
        loginViewModel.loggedInUserIdProperty().addListener(closeOnSuccess[0]);

        dialog.setVisible(true);
    }

    /**
     * Opens the registration form as its own closable popup window instead of embedding it
     * in the shell — mirrors #showLoginDialog, so Login and Register are consistently
     * separate screens reached from the same entry point.
     */
    private void showRegisterDialog(
        final RegisterController registerController,
        final RegisterViewModel registerViewModel,
        final Component ownerComponent) {
        final RegisterView registerView = new RegisterView(registerController, registerViewModel);

        final Window ownerWindow = SwingUtilities.getWindowAncestor(ownerComponent);
        final JDialog dialog = new JDialog(ownerWindow, "Register", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.getContentPane().add(registerView);
        dialog.getContentPane().setBackground(Theme.BG_DEFAULT);
        dialog.pack();
        dialog.setLocationRelativeTo(ownerWindow);

        final Consumer<Boolean>[] closeOnSuccess = new Consumer[1];
        closeOnSuccess[0] = succeeded -> {
            if (Boolean.TRUE.equals(succeeded)) {
                SwingUtilities.invokeLater(dialog::dispose);
                registerViewModel.registrationSucceededProperty().removeListener(closeOnSuccess[0]);
            }
        };
        registerViewModel.registrationSucceededProperty().addListener(closeOnSuccess[0]);

        dialog.setVisible(true);
    }
}
