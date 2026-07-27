package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Reflowable;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.usecase.dto.PostingData;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.BiConsumer;

/**
 * Responsive opportunity card that presents posting details and an apply action.
 */
public final class PostingCard extends RoundedPanel implements Reflowable {

    private static final int MAX_WIDTH = 860;
    private static final int MIN_WIDTH = 280;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    private final JPanel header = new JPanel();

    /**
     * Constructs an opportunity card.
     *
     * @param posting posting data to display
     * @param onApply callback receiving the posting identifier and application message
     */
    public PostingCard(
            final PostingData posting,
            final BiConsumer<String, String> onApply) {
        this(posting, onApply, ApplyToPostingDialog::showDialog);
    }

    PostingCard(
            final PostingData posting,
            final BiConsumer<String, String> onApply,
            final ApplicationDialogLauncher dialogLauncher) {
        super(Theme.CARD_RADIUS, 24);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel title = new JLabel(posting.getTitle());
        title.setName("postingTitle");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setForeground(Theme.FG_DEFAULT);

        final JLabel status = new JLabel(format(posting.getStatus().name()));
        status.setName("postingStatus");
        status.setForeground(posting.isActive() ? Theme.ACCENT_FG : Theme.FG_MUTED);
        status.setFont(status.getFont().deriveFont(Font.BOLD));

        this.header.setOpaque(false);
        this.header.setLayout(new BorderLayout(12, 4));
        this.header.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.header.add(title, BorderLayout.CENTER);
        this.header.add(status, BorderLayout.EAST);

        final JLabel owner = new JLabel("Posted by " + posting.getPosterUserId()
                + "  •  " + posting.getCreatedAt().format(DATE_FORMAT));
        owner.setName("postingOwner");
        owner.setForeground(Theme.FG_MUTED);
        owner.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JTextArea description = bodyText(posting.getDescription());
        description.setName("postingDescription");

        final JLabel metadata = new JLabel(
                format(posting.getResearchField().name()) + "  •  "
                        + format(posting.getCollaborationType().name()) + "  •  "
                        + capacity(posting));
        metadata.setName("postingMetadata");
        metadata.setForeground(Theme.FG_MUTED);

        final JButton applyButton = new JButton("Apply");
        applyButton.setName("applyButton");
        Buttons.accent(applyButton);
        if (!posting.isActive()) {
            applyButton.setText(posting.isFull() ? "Full" : "Closed");
            applyButton.setEnabled(false);
        } else {
            applyButton.addActionListener(event ->
                    dialogLauncher.open(this, posting, onApply));
        }
        final JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actions.setOpaque(false);
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        actions.add(applyButton);

        add(this.header);
        add(Box.createVerticalStrut(6));
        add(owner);
        add(Box.createVerticalStrut(16));
        add(description);
        add(Box.createVerticalStrut(16));
        add(metadata);
        add(Box.createVerticalStrut(18));
        add(actions);
        reflow(MAX_WIDTH);
    }

    @Override
    public void reflow(final int width) {
        final int cardWidth = Math.max(MIN_WIDTH, Math.min(MAX_WIDTH, width));
        setPreferredSize(new Dimension(cardWidth, getPreferredSize().height));
        setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
        revalidate();
    }

    private static JTextArea bodyText(final String value) {
        final JTextArea text = new JTextArea(value);
        text.setEditable(false);
        text.setFocusable(false);
        text.setOpaque(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setForeground(Theme.FG_DEFAULT);
        text.setFont(new JLabel().getFont().deriveFont(14f));
        text.setBorder(BorderFactory.createEmptyBorder());
        text.setAlignmentX(Component.LEFT_ALIGNMENT);
        return text;
    }

    private static String capacity(final PostingData posting) {
        if (posting.getCapacity() == null) {
            return posting.getAcceptedCount() + " accepted / unlimited";
        }
        return posting.getAcceptedCount() + " of " + posting.getCapacity() + " accepted";
    }

    private static String format(final String value) {
        final String lower = value.toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    @FunctionalInterface
    interface ApplicationDialogLauncher {
        void open(
                Component parent,
                PostingData posting,
                BiConsumer<String, String> onSubmit);
    }
}
