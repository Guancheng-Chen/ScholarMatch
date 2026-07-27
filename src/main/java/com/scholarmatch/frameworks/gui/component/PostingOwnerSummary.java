package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Theme;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Font;

/**
 * Read-only posting owner name and university-email verification summary.
 */
public final class PostingOwnerSummary extends JPanel {

    /**
     * Constructs a text-only owner summary.
     *
     * @param ownerName posting owner display name
     * @param academicEmailVerified whether the owner verified a university email
     */
    public PostingOwnerSummary(
            final String ownerName,
            final boolean academicEmailVerified) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        final String displayName = ownerName == null || ownerName.isBlank()
                ? "Unknown user" : ownerName.trim();
        final JLabel owner = new JLabel("Posted by " + displayName);
        owner.setName("postingOwner");
        owner.setForeground(Theme.FG_MUTED);
        owner.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(owner);

        if (academicEmailVerified) {
            add(Box.createVerticalStrut(5));
            final JLabel badge = new JLabel("Verified university email");
            badge.setName("academicVerificationBadge");
            badge.setForeground(Theme.ACCENT_FG);
            badge.setFont(badge.getFont().deriveFont(Font.BOLD));
            badge.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(badge);
        }
    }
}
