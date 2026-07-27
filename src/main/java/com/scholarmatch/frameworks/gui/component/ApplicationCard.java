package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Reflowable;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.usecase.dto.PostingApplicationData;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Read-only responsive card for an application submitted by the current user.
 */
public final class ApplicationCard extends RoundedPanel implements Reflowable {

    private static final int MAX_WIDTH = 860;
    private static final int MIN_WIDTH = 280;
    private static final int STACK_BREAKPOINT = 480;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    private final JPanel header = new JPanel(new BorderLayout(12, 4));
    private final JLabel title;
    private final JLabel status;

    /**
     * Constructs a submitted-application card.
     *
     * @param application application data to display
     */
    public ApplicationCard(final PostingApplicationData application) {
        super(Theme.CARD_RADIUS, 24);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        final String displayTitle = application.getPostingTitle().isBlank()
                ? "Posting " + application.getPostingId() : application.getPostingTitle();
        this.title = new JLabel(displayTitle);
        this.title.setName("postingTitle");
        this.title.setFont(this.title.getFont().deriveFont(Font.BOLD, 19f));
        this.title.setForeground(Theme.FG_DEFAULT);
        this.status = new JLabel(format(application.getStatus().name()));
        this.status.setName("applicationStatus");
        this.status.setFont(this.status.getFont().deriveFont(Font.BOLD));
        this.status.setForeground(Theme.ACCENT_FG);
        this.header.setOpaque(false);
        this.header.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.header.add(this.title, BorderLayout.CENTER);
        this.header.add(this.status, BorderLayout.EAST);

        final JLabel date = new JLabel("Applied "
                + application.getAppliedAt().format(DATE_FORMAT));
        date.setName("applicationDate");
        date.setForeground(Theme.FG_MUTED);
        date.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel messageLabel = new JLabel("Application message");
        messageLabel.setFont(messageLabel.getFont().deriveFont(Font.BOLD));
        messageLabel.setForeground(Theme.FG_MUTED);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JTextArea message = new JTextArea(application.getMessage());
        message.setName("applicationMessage");
        message.setEditable(false);
        message.setFocusable(false);
        message.setOpaque(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setForeground(Theme.FG_DEFAULT);
        message.setBorder(BorderFactory.createEmptyBorder());
        message.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(this.header);
        add(Box.createVerticalStrut(6));
        add(date);
        add(Box.createVerticalStrut(16));
        add(messageLabel);
        add(Box.createVerticalStrut(6));
        add(message);
        reflow(MAX_WIDTH);
    }

    @Override
    public void reflow(final int width) {
        final int cardWidth = Math.max(MIN_WIDTH, Math.min(MAX_WIDTH, width));
        setPreferredSize(new Dimension(cardWidth, getPreferredSize().height));
        setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
        this.header.removeAll();
        if (width < STACK_BREAKPOINT) {
            this.header.setLayout(new BoxLayout(this.header, BoxLayout.Y_AXIS));
            this.header.add(this.title);
            this.header.add(Box.createVerticalStrut(4));
            this.header.add(this.status);
        } else {
            this.header.setLayout(new BorderLayout(12, 4));
            this.header.add(this.title, BorderLayout.CENTER);
            this.header.add(this.status, BorderLayout.EAST);
        }
        revalidate();
    }

    private static String format(final String value) {
        final String lower = value.toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
