package com.scholarmatch.frameworks.gui.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Icons;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.LoginController;
import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.regex.Pattern;

/**
 * Content of the login popup: just an email field, a password field, and a
 * single, prominent login button. Meant to be hosted in a small, fixed-size,
 * closable dialog window (see MainView#showLoginDialog) rather than
 * embedded in the main shell.
 *
 * <p>On submit, calls LoginController#login(String, String).
 * Observes LoginViewModel#loggedInUserIdProperty() to detect
 * success and trigger navigation to the main view.
 *
 * <p>Validates the email format locally before sending the request: the live
 * server responds to a malformed email with a bare, unhelpful HTTP 403 (a
 * server-side bug in how it handles validation failures) instead of a normal
 * "invalid email" error, so this is caught client-side to avoid ever hitting it.
 */
public final class LoginView extends JPanel {

    private static final int DIALOG_WIDTH = 320;
    private static final int FIELD_HEIGHT = 34;
    private static final int BUTTON_HEIGHT = 46;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final LoginController controller;
    private final LoginViewModel viewModel;

    /**
     * Constructs the LoginView.
     *
     * @param controller forwards credentials to the login use case
     * @param viewModel  observable login state
     */
    public LoginView(final LoginController controller, final LoginViewModel viewModel) {
        super();
        this.controller = controller;
        this.viewModel = viewModel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Theme.BG_DEFAULT);
        setBorder(new EmptyBorder(28, 28, 28, 28));

        final JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(DIALOG_WIDTH, FIELD_HEIGHT));
        emailField.setMaximumSize(new Dimension(DIALOG_WIDTH, FIELD_HEIGHT));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.putClientProperty("JTextField.placeholderText", "Email address");
        emailField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
            Icons.of(FontAwesomeSolid.ENVELOPE, 14, Theme.FG_SUBTLE));

        final JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(DIALOG_WIDTH, FIELD_HEIGHT));
        passwordField.setMaximumSize(new Dimension(DIALOG_WIDTH, FIELD_HEIGHT));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.putClientProperty("JTextField.placeholderText", "Password");
        passwordField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
            Icons.of(FontAwesomeSolid.LOCK, 14, Theme.FG_SUBTLE));

        final JLabel validationLabel = new JLabel(" ");
        validationLabel.setForeground(Theme.DANGER_FG);
        validationLabel.setFont(validationLabel.getFont().deriveFont(11f));
        validationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        validationLabel.setVisible(false);

        final JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Theme.DANGER_FG);
        errorLabel.setFont(errorLabel.getFont().deriveFont(11f));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JButton loginButton = new JButton(
            "Login", Icons.of(FontAwesomeSolid.SIGN_IN_ALT, 15, Theme.FG_EMPHASIS));
        loginButton.setIconTextGap(8);
        Buttons.accent(loginButton);
        loginButton.setFont(loginButton.getFont().deriveFont(Font.BOLD, 15f));
        loginButton.setPreferredSize(new Dimension(DIALOG_WIDTH, BUTTON_HEIGHT));
        loginButton.setMaximumSize(new Dimension(DIALOG_WIDTH, BUTTON_HEIGHT));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(evt -> {
            final String email = emailField.getText().trim();
            final String password = new String(passwordField.getPassword());
            final String validationError = validate(email, password);
            if (validationError != null) {
                validationLabel.setText(validationError);
                validationLabel.setVisible(true);
                return;
            }
            validationLabel.setVisible(false);
            // login() blocks on a network call — run it off the EDT so the dialog (and its
            // fields) doesn't freeze for the duration of the request.
            loginButton.setEnabled(false);
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    controller.login(email, password);
                    return null;
                }

                @Override
                protected void done() {
                    loginButton.setEnabled(true);
                }
            }.execute();
        });
        // Deferred until this panel is attached to its dialog's root pane, so Enter submits.
        javax.swing.SwingUtilities.invokeLater(() -> {
            final javax.swing.JRootPane rootPane = javax.swing.SwingUtilities.getRootPane(loginButton);
            if (rootPane != null) {
                rootPane.setDefaultButton(loginButton);
            }
        });

        add(emailField);
        add(Box.createVerticalStrut(14));
        add(passwordField);
        add(Box.createVerticalStrut(14));
        add(loginButton);
        add(Box.createVerticalStrut(10));
        add(validationLabel);
        add(errorLabel);

        viewModel.errorMessageProperty().addListener(errorLabel::setText);
    }

    private String validate(final String email, final String password) {
        if (email.isEmpty()) {
            return "Please enter an email address";
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "Email format is invalid, e.g. name@example.com";
        }
        if (password.isEmpty()) {
            return "Please enter a password";
        }
        return null;
    }
}
