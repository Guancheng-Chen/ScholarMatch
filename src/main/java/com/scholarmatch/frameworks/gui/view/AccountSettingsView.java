package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.change_email.ChangeEmailController;
import com.scholarmatch.interface_adapter.change_password.ChangePasswordController;
import com.scholarmatch.interface_adapter.delete_account.DeleteAccountController;
import com.scholarmatch.interface_adapter.request_email_verification.RequestEmailVerificationController;
import com.scholarmatch.interface_adapter.view_model.account_settings.AccountSettingsViewModel;
import com.scholarmatch.interface_adapter.view_model.delete_account.DeleteAccountViewModel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Consumer;

/**
 * Screen for changing the authenticated account's email and password, and for
 * permanently deleting the account.
 */
public final class AccountSettingsView extends JPanel {

    private static final int FIELD_WIDTH = 420;
    private static final int FIELD_HEIGHT = 36;

    private static final String DELETE_ACCOUNT_WARNING =
            "This will permanently delete your account, password, and personal information "
                    + "from ScholarMatch. To use the app again, you will need to register a new "
                    + "account. This action cannot be undone.";

    private final Consumer<String> successListener;
    private final Consumer<String> errorListener;
    private final Consumer<String> currentEmailListener;
    private final Consumer<String> deleteErrorListener;
    private final AccountSettingsViewModel viewModel;
    private final DeleteAccountViewModel deleteAccountViewModel;

    public AccountSettingsView(
            final RequestEmailVerificationController requestController,
            final ChangeEmailController changeEmailController,
            final ChangePasswordController changePasswordController,
            final AccountSettingsViewModel viewModel,
            final DeleteAccountController deleteAccountController,
            final DeleteAccountViewModel deleteAccountViewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;
        this.deleteAccountViewModel = deleteAccountViewModel;
        this.successListener = message -> show(
                message, "Account Settings", JOptionPane.INFORMATION_MESSAGE);
        this.errorListener = message -> show(
                message, "Account Settings Failed", JOptionPane.ERROR_MESSAGE);
        this.deleteErrorListener = message -> show(
                message, "Delete Account Failed", JOptionPane.ERROR_MESSAGE);
        setBackground(Theme.BG_DEFAULT);
        setBorder(BorderFactory.createEmptyBorder(28, 36, 28, 36));

        final JTextField emailField = field("New Email");
        final JTextField codeField = field("Verification Code");
        final JPasswordField emailPasswordField =
                passwordField("Current Password");
        final JPasswordField currentPasswordField =
                passwordField("Current Password");
        final JPasswordField newPasswordField =
                passwordField("New Password");
        final JPasswordField confirmPasswordField =
                passwordField("Confirm New Password");

        final JButton sendCodeButton = new JButton("Send Code");
        Buttons.outlined(sendCodeButton);
        sendCodeButton.addActionListener(event -> run(
                sendCodeButton,
                () -> requestController.sendVerificationCode(emailField.getText().trim())));

        final JButton changeEmailButton = new JButton("Change Email");
        Buttons.accent(changeEmailButton);
        changeEmailButton.addActionListener(event -> run(
                changeEmailButton,
                () -> changeEmailController.execute(
                        emailField.getText().trim(),
                        new String(emailPasswordField.getPassword()),
                        codeField.getText().trim())));

        final JButton changePasswordButton = new JButton("Change Password");
        Buttons.accent(changePasswordButton);
        changePasswordButton.addActionListener(event -> run(
                changePasswordButton,
                () -> changePasswordController.execute(
                        new String(currentPasswordField.getPassword()),
                        new String(newPasswordField.getPassword()),
                        new String(confirmPasswordField.getPassword()))));

        final JButton deleteAccountButton = new JButton("Delete Account");
        Buttons.danger(deleteAccountButton);
        deleteAccountButton.addActionListener(event -> {
            final int choice = JOptionPane.showConfirmDialog(
                    this,
                    DELETE_ACCOUNT_WARNING,
                    "Delete Account?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                deleteAccountController.deleteAccount();
            }
        });

        final RoundedPanel content = new RoundedPanel(Theme.CARD_RADIUS, 24);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setMaximumSize(new Dimension(FIELD_WIDTH + 48, Integer.MAX_VALUE));
        content.add(title("Account Settings"));
        content.add(Box.createVerticalStrut(22));
        content.add(section("Change Email"));
        content.add(labeled("Current Email"));
        final JLabel currentEmailLabel = new JLabel(viewModel.currentEmailProperty().get());
        currentEmailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(currentEmailLabel);
        content.add(Box.createVerticalStrut(10));
        addField(content, emailField);
        addField(content, sendCodeButton);
        addField(content, codeField);
        addField(content, emailPasswordField);
        addField(content, changeEmailButton);
        content.add(Box.createVerticalStrut(24));
        content.add(section("Change Password"));
        addField(content, currentPasswordField);
        addField(content, newPasswordField);
        addField(content, confirmPasswordField);
        addField(content, changePasswordButton);
        content.add(Box.createVerticalStrut(24));
        content.add(section("Delete Account"));
        content.add(labeled("Permanently delete your account and all associated data."));
        addField(content, deleteAccountButton);

        this.currentEmailListener = currentEmailLabel::setText;
        viewModel.currentEmailProperty().addListener(this.currentEmailListener);
        viewModel.successMessageProperty().addListener(this.successListener);
        viewModel.errorMessageProperty().addListener(this.errorListener);
        deleteAccountViewModel.errorMessageProperty().addListener(this.deleteErrorListener);

        final JPanel holder = new JPanel();
        holder.setOpaque(false);
        holder.add(content);
        add(holder, BorderLayout.NORTH);
    }

    private JLabel title(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 22f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel section(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel labeled(final String text) {
        final JLabel label = new JLabel(text);
        label.setForeground(Theme.FG_MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField field(final String placeholder) {
        final JTextField field = new JTextField();
        styleField(field);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        return field;
    }

    private JPasswordField passwordField(final String placeholder) {
        final JPasswordField field = new JPasswordField();
        styleField(field);
        field.putClientProperty("JTextField.placeholderText", placeholder);
        return field;
    }

    private void styleField(final JTextField field) {
        final Dimension size = new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
        field.setPreferredSize(size);
        field.setMaximumSize(size);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void addField(final JPanel panel, final JComponent component) {
        component.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        component.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(component);
    }

    private void run(final JButton button, final Runnable action) {
        button.setEnabled(false);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                action.run();
                return null;
            }

            @Override
            protected void done() {
                button.setEnabled(true);
            }
        }.execute();
    }

    private void show(
            final String message,
            final String title,
            final int messageType) {
        if (message != null && !message.isBlank()) {
            JOptionPane.showMessageDialog(this, message, title, messageType);
        }
    }

    @Override
    public void removeNotify() {
        this.viewModel.successMessageProperty().removeListener(this.successListener);
        this.viewModel.errorMessageProperty().removeListener(this.errorListener);
        this.viewModel.currentEmailProperty().removeListener(this.currentEmailListener);
        this.deleteAccountViewModel.errorMessageProperty().removeListener(this.deleteErrorListener);
        super.removeNotify();
    }
}
