package com.scholarmatch.frameworks.gui.component;

import com.formdev.flatlaf.FlatClientProperties;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Icons;
import com.scholarmatch.frameworks.gui.style.Theme;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Left-hand navigation sidebar shown once the user is logged in.
 *
 * <p>Contains a vertical, full-width segmented group of toggle buttons — Recommend,
 * View Matched, Chat, Opportunities, My Postings, My Applications, Profile, and Account
 * Settings — each laid out icon-left/text-right, plus a Logout button anchored to the
 * bottom. Selecting a nav button invokes the provided NavSelectionListener, which causes
 * the center pane of MainView to switch its displayed view. Account deletion lives on the
 * Account Settings screen rather than here.
 */
public final class NavigationBar extends JPanel {

    private static final int SIDEBAR_WIDTH = 220;
    private static final int NAV_ICON_SIZE = 16;
    private static final int TRAILING_ICON_SIZE = 13;
    private static final int LOGO_ICON_SIZE = 35;

    private static final String TOGGLE_STYLE =
        "arc:8;selectedBackground:#e2f3ee;selectedForeground:#0c6e58;focusWidth:0;borderWidth:0;"
            + "background:#ffffff;hoverBackground:#f3f4f6;foreground:#1c2624";

    /**
     * Callback interface for screen-selection events raised by this bar.
     */
    public interface NavSelectionListener {
        /**
         * Invoked when the user selects a navigation destination.
         *
         * @param target the view identifier (e.g. "recommend", "profile")
         */
        void onSelected(String target);
    }

    /**
     * Constructs a NavigationBar.
     *
     * @param listener  the callback invoked when a nav item is selected
     * @param onLogout  the callback invoked when the user clicks Logout
     */
    public NavigationBar(
        final NavSelectionListener listener,
        final Runnable onLogout) {
        super();
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
        setBackground(Theme.BG_SUBTLE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(16, 14, 16, 14)));
        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        setMaximumSize(new Dimension(SIDEBAR_WIDTH, Integer.MAX_VALUE));
        setMinimumSize(new Dimension(SIDEBAR_WIDTH, 0));

        final JLabel logoLabel = new JLabel(
            "ScholarMatch", Icons.fromResource("/images/logo.png", LOGO_ICON_SIZE), SwingConstants.LEFT);
        logoLabel.setFont(logoLabel.getFont().deriveFont(Font.BOLD, 17f));
        logoLabel.setForeground(Theme.FG_DEFAULT);
        logoLabel.setIconTextGap(8);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 16, 0));

        final ButtonGroup navGroup = new ButtonGroup();

        final JToggleButton recommendButton = navToggle("Recommend", FontAwesomeSolid.COMPASS);
        final JToggleButton matchedButton = navToggle("View Matched", FontAwesomeSolid.USERS);
        final JToggleButton chatButton = navToggle("Chat", FontAwesomeSolid.COMMENT_DOTS);
        final JToggleButton opportunitiesButton = navToggle("Opportunities", FontAwesomeSolid.BRIEFCASE);
        final JToggleButton myPostingsButton = navToggle("My Postings", FontAwesomeSolid.CLIPBOARD_LIST);
        final JToggleButton myApplicationsButton = navToggle("My Applications", FontAwesomeSolid.INBOX);
        final JToggleButton profileButton = navToggle("Profile", FontAwesomeSolid.USER_CIRCLE);
        final JToggleButton settingsButton = navToggle("Account Settings", FontAwesomeSolid.COG);

        for (final JToggleButton button
            : new JToggleButton[] {recommendButton, matchedButton, chatButton, opportunitiesButton,
                myPostingsButton, myApplicationsButton, profileButton, settingsButton}) {
            navGroup.add(button);
            button.putClientProperty(FlatClientProperties.STYLE, TOGGLE_STYLE);
        }
        recommendButton.setSelected(true);
        recommendButton.addActionListener(evt -> listener.onSelected("recommend"));
        matchedButton.addActionListener(evt -> listener.onSelected("matched"));
        chatButton.addActionListener(evt -> listener.onSelected("chat"));
        opportunitiesButton.addActionListener(evt -> listener.onSelected("opportunities"));
        myPostingsButton.addActionListener(evt -> listener.onSelected("my-postings"));
        myApplicationsButton.addActionListener(evt -> listener.onSelected("my-applications"));
        profileButton.addActionListener(evt -> listener.onSelected("profile"));
        settingsButton.addActionListener(evt -> listener.onSelected("settings"));

        final JButton logoutButton = new JButton(
            "Logout", Icons.of(FontAwesomeSolid.SIGN_OUT_ALT, TRAILING_ICON_SIZE, Theme.FG_DEFAULT));
        Buttons.outlined(logoutButton);
        logoutButton.setIconTextGap(8);
        styleSidebarButton(logoutButton);
        logoutButton.addActionListener(evt -> onLogout.run());

        add(logoLabel);
        add(recommendButton);
        add(Box.createVerticalStrut(4));
        add(matchedButton);
        add(Box.createVerticalStrut(4));
        add(chatButton);
        add(Box.createVerticalStrut(4));
        add(opportunitiesButton);
        add(Box.createVerticalStrut(4));
        add(myPostingsButton);
        add(Box.createVerticalStrut(4));
        add(myApplicationsButton);
        add(Box.createVerticalStrut(4));
        add(profileButton);
        add(Box.createVerticalStrut(4));
        add(settingsButton);
        add(Box.createVerticalGlue());
        add(logoutButton);
    }

    /**
     * Builds a full-width sidebar nav toggle button (icon left, text right) whose icon
     * switches to the accent-teal selected color, matching {@link #TOGGLE_STYLE}'s
     * light-tint selectedBackground.
     */
    private JToggleButton navToggle(final String text, final Ikon glyph) {
        final Icon unselectedIcon = Icons.of(glyph, NAV_ICON_SIZE, Theme.FG_MUTED);
        final Icon selectedIcon = Icons.of(glyph, NAV_ICON_SIZE, Theme.ACCENT_FG);
        final JToggleButton button = new JToggleButton(text, unselectedIcon);
        button.setIconTextGap(10);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(button.getFont().deriveFont(14f));
        button.addItemListener(evt ->
            button.setIcon(button.isSelected() ? selectedIcon : unselectedIcon));
        styleSidebarButton(button);
        return button;
    }

    /** Stretches a button to the sidebar's full width as a left-aligned row. */
    private void styleSidebarButton(final javax.swing.AbstractButton button) {
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        final Dimension size = new Dimension(SIDEBAR_WIDTH - 28, 40);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
    }
}
