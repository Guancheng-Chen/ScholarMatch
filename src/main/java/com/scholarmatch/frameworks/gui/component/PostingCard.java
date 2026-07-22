package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.usecase.dto.PostingData;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Locale;
import java.util.function.BiConsumer;

/**
 * Opportunity list card with a single apply action.
 */
public final class PostingCard extends RoundedPanel {

    private static final int CARD_WIDTH = 760;

    public PostingCard(
            final PostingData posting,
            final BiConsumer<String, String> onApply) {
        super(Theme.CARD_RADIUS, 20);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(CARD_WIDTH, Integer.MAX_VALUE));

        final JLabel title = new JLabel(posting.getTitle());
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setForeground(Theme.FG_DEFAULT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel details = new JLabel(
                "Field: " + format(posting.getResearchField().name())
                        + "   Type: " + format(posting.getCollaborationType().name()));
        details.setForeground(Theme.FG_MUTED);
        details.setAlignmentX(Component.LEFT_ALIGNMENT);

        final String capacity = posting.getCapacity() == null
                ? posting.getAcceptedCount() + " accepted / unlimited capacity"
                : posting.getAcceptedCount() + " / " + posting.getCapacity() + " accepted";
        final JLabel applicantCount = new JLabel(capacity);
        applicantCount.setForeground(Theme.FG_MUTED);
        applicantCount.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JButton applyButton = new JButton("Apply");
        Buttons.accent(applyButton);
        applyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (!posting.isActive()) {
            applyButton.setText(posting.isFull() ? "Full" : "Closed");
            applyButton.setEnabled(false);
        } else {
            applyButton.addActionListener(event -> {
                final String message = JOptionPane.showInputDialog(
                        this, "Application message", "Apply", JOptionPane.PLAIN_MESSAGE);
                if (message != null) {
                    onApply.accept(posting.getPostingId(), message);
                }
            });
        }

        add(title);
        add(Box.createVerticalStrut(8));
        add(details);
        add(Box.createVerticalStrut(5));
        add(applicantCount);
        add(Box.createVerticalStrut(12));
        add(applyButton);
    }

    private static String format(final String value) {
        final String lower = value.toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
