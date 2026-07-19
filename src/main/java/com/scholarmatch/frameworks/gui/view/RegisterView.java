package com.scholarmatch.frameworks.gui.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.Icons;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Registration screen allowing new users to create an account.
 *
 * <p>Only collects the account-creation essentials — first name, last name, email, and
 * password. Everything else (institution, research field, publications, etc.) is filled
 * in later from the Edit Profile screen once the user is inside the app.
 *
 * <p>Observes RegisterViewModel for success/error feedback and delegates button events
 * to RegisterController.
 */
public final class RegisterView extends JPanel {

    private static final int CARD_WIDTH = 360;
    private static final int FIELD_HEIGHT = 34;

    private final RegisterController controller;
    private final RegisterViewModel viewModel;

    /**
     * Constructs the RegisterView.
     *
     * @param controller the controller that handles form submission
     * @param viewModel  the observable state for this view
     */
    public RegisterView(
            final RegisterController controller,
            final RegisterViewModel viewModel) {
        super(new BorderLayout());
        this.controller = controller;
        this.viewModel = viewModel;
        setBackground(Theme.BG_DEFAULT);

        final JLabel titleLabel = title("Register");

        final JTextField firstNameField = field("First Name", FontAwesomeSolid.USER);
        final JTextField lastNameField = field("Last Name", FontAwesomeSolid.USER);
        final JTextField emailField = field("Email", FontAwesomeSolid.ENVELOPE);
        final JPasswordField passwordField = passwordField("Password");
        final JPasswordField confirmPasswordField = passwordField("Confirm Password");

        final JButton submitButton = new JButton(
                "Register", Icons.of(FontAwesomeSolid.USER_PLUS, 15, Theme.FG_EMPHASIS));
        submitButton.setIconTextGap(8);
        Buttons.accent(submitButton);
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.setPreferredSize(new Dimension(CARD_WIDTH, 38));
        submitButton.setMaximumSize(new Dimension(CARD_WIDTH, 38));

        final RoundedPanel card = new RoundedPanel(Theme.CARD_RADIUS, 24);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(CARD_WIDTH + 48, Integer.MAX_VALUE));
        addAll(card, titleLabel, strut(),
                firstNameField, strut(), lastNameField, strut(), emailField, strut(),
                passwordField, strut(), confirmPasswordField, strut(),
                submitButton);

        final CenteringScrollPanel centeringPanel = new CenteringScrollPanel(card);
        centeringPanel.setBorder(new EmptyBorder(24, 0, 24, 0));
        final JScrollPane scrollPane = new JScrollPane(centeringPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.BG_DEFAULT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel title(final String text) {
        final JLabel label = new JLabel(text);
        label.setForeground(Theme.FG_DEFAULT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 20f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField field(final String placeholder, final Ikon leadingGlyph) {
        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(CARD_WIDTH, FIELD_HEIGHT));
        textField.setMaximumSize(new Dimension(CARD_WIDTH, FIELD_HEIGHT));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        textField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                Icons.of(leadingGlyph, 14, Theme.FG_SUBTLE));
        return textField;
    }

    private JPasswordField passwordField(final String placeholder) {
        final JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(CARD_WIDTH, FIELD_HEIGHT));
        passwordField.setMaximumSize(new Dimension(CARD_WIDTH, FIELD_HEIGHT));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.putClientProperty("JTextField.placeholderText", placeholder);
        passwordField.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON,
                Icons.of(FontAwesomeSolid.LOCK, 14, Theme.FG_SUBTLE));
        return passwordField;
    }

    private static Component strut() {
        return Box.createVerticalStrut(10);
    }

    private static void addAll(final JPanel panel, final Component... components) {
        for (final Component component : components) {
            panel.add(component);
        }
    }
}
