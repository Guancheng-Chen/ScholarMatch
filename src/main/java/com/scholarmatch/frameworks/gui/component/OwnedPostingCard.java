package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Reflowable;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.AcceptApplicationController;
import com.scholarmatch.interface_adapter.controller.DeclineApplicationController;
import com.scholarmatch.usecase.dto.PostingApplicationData;
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
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Responsive owner posting card containing posting details and received applications.
 */
public final class OwnedPostingCard extends RoundedPanel implements Reflowable {

    private static final int MAX_WIDTH = 900;
    private static final int MIN_WIDTH = 280;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    private final List<PostingApplicationRow> applicationRows;

    /**
     * Constructs an owner posting card.
     *
     * @param posting posting to display
     * @param applications applications received for the posting
     * @param onClose callback used to request posting closure
     * @param acceptController controller for accepting applications
     * @param declineController controller for declining applications
     */
    public OwnedPostingCard(
            final PostingData posting,
            final List<PostingApplicationData> applications,
            final Consumer<PostingData> onClose,
            final AcceptApplicationController acceptController,
            final DeclineApplicationController declineController) {
        super(Theme.CARD_RADIUS, 24);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        final JPanel header = new JPanel(new BorderLayout(12, 4));
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JLabel title = new JLabel(posting.getTitle());
        title.setName("postingTitle");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setForeground(Theme.FG_DEFAULT);
        final JLabel status = new JLabel(format(posting.getStatus().name()));
        status.setName("postingStatus");
        status.setFont(status.getFont().deriveFont(Font.BOLD));
        status.setForeground(posting.isActive() ? Theme.ACCENT_FG : Theme.FG_MUTED);
        header.add(title, BorderLayout.CENTER);
        header.add(status, BorderLayout.EAST);

        final JLabel meta = new JLabel(posting.getCreatedAt().format(DATE_FORMAT) + "  •  "
                + format(posting.getResearchField().name()) + "  •  "
                + format(posting.getCollaborationType().name()));
        meta.setName("postingMetadata");
        meta.setForeground(Theme.FG_MUTED);
        meta.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JTextArea description = new JTextArea(posting.getDescription());
        description.setName("postingDescription");
        description.setEditable(false);
        description.setFocusable(false);
        description.setOpaque(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setForeground(Theme.FG_DEFAULT);
        description.setBorder(BorderFactory.createEmptyBorder());
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        final String capacity = posting.getCapacity() == null
                ? posting.getAcceptedCount() + " accepted / unlimited capacity"
                : posting.getAcceptedCount() + " of " + posting.getCapacity() + " accepted";
        final JLabel counts = new JLabel(
                posting.getApplicantCount() + " applicants  •  " + capacity);
        counts.setName("postingCounts");
        counts.setForeground(Theme.FG_MUTED);

        final JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        controls.setOpaque(false);
        controls.setAlignmentX(Component.LEFT_ALIGNMENT);
        controls.add(counts);
        if (posting.isActive()) {
            final JButton closeButton = new JButton("Close Posting");
            closeButton.setName("closePostingButton");
            Buttons.outlined(closeButton);
            closeButton.addActionListener(event -> onClose.accept(posting));
            final JPanel close = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            close.setOpaque(false);
            close.add(closeButton);
            final JPanel controlsWithAction = new JPanel(new BorderLayout(12, 0));
            controlsWithAction.setOpaque(false);
            controlsWithAction.setAlignmentX(Component.LEFT_ALIGNMENT);
            controlsWithAction.add(counts, BorderLayout.CENTER);
            controlsWithAction.add(close, BorderLayout.EAST);
            controls.removeAll();
            controls.setLayout(new BorderLayout());
            controls.add(controlsWithAction);
        }

        final JLabel applicationsTitle = new JLabel(
                applications.isEmpty() ? "Applications" : "Applications (" + applications.size() + ")");
        applicationsTitle.setName("applicationsTitle");
        applicationsTitle.setFont(applicationsTitle.getFont().deriveFont(Font.BOLD, 16f));
        applicationsTitle.setForeground(Theme.FG_DEFAULT);
        applicationsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(header);
        add(Box.createVerticalStrut(6));
        add(meta);
        add(Box.createVerticalStrut(14));
        add(description);
        add(Box.createVerticalStrut(16));
        add(controls);
        add(Box.createVerticalStrut(20));
        add(applicationsTitle);

        this.applicationRows = applications.stream()
                .map(application -> new PostingApplicationRow(
                        application, acceptController, declineController))
                .toList();
        if (this.applicationRows.isEmpty()) {
            final JLabel empty = new JLabel("No applications received yet.");
            empty.setName("emptyApplications");
            empty.setForeground(Theme.FG_MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(Box.createVerticalStrut(10));
            add(empty);
        } else {
            for (final PostingApplicationRow row : this.applicationRows) {
                row.setAlignmentX(Component.LEFT_ALIGNMENT);
                add(row);
            }
        }
        reflow(MAX_WIDTH);
    }

    @Override
    public void reflow(final int width) {
        final int cardWidth = Math.max(MIN_WIDTH, Math.min(MAX_WIDTH, width));
        setPreferredSize(new Dimension(cardWidth, getPreferredSize().height));
        setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
        for (final PostingApplicationRow row : this.applicationRows) {
            row.reflow(cardWidth - 48);
        }
        revalidate();
    }

    private static String format(final String value) {
        final String lower = value.toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
