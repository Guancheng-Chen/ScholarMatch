package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Reflowable;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.accept_application.AcceptApplicationController;
import com.scholarmatch.interface_adapter.decline_application.DeclineApplicationController;
import com.scholarmatch.usecase.dto.PostingApplicationData;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Responsive applicant panel inside an owner's posting card.
 */
public final class PostingApplicationRow extends JPanel implements Reflowable {

    private static final int STACK_BREAKPOINT = 560;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

    private final JPanel content = new JPanel();
    private final JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));

    /**
     * Constructs an applicant panel.
     *
     * @param application application to display
     * @param acceptController controller for the accept action
     * @param declineController controller for the decline action
     */
    public PostingApplicationRow(
            final PostingApplicationData application,
            final AcceptApplicationController acceptController,
            final DeclineApplicationController declineController) {
        super(new BorderLayout(16, 8));
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER_MUTED),
                BorderFactory.createEmptyBorder(16, 0, 4, 0)));

        final String applicant = application.getApplicantName().isBlank()
                ? application.getApplicantUserId() : application.getApplicantName();
        final JLabel name = new JLabel(applicant);
        name.setName("applicantName");
        name.setFont(name.getFont().deriveFont(Font.BOLD, 15f));
        name.setForeground(Theme.FG_DEFAULT);

        final JLabel details = new JLabel("Applied "
                + application.getAppliedAt().format(DATE_FORMAT) + "  •  "
                + format(application.getStatus().name()));
        details.setName("applicationStatus");
        details.setForeground(application.getStatus() == PostingApplicationStatus.PENDING
                ? Theme.ACCENT_FG : Theme.FG_MUTED);

        final JTextArea message = new JTextArea(application.getMessage());
        message.setName("applicationMessage");
        message.setEditable(false);
        message.setFocusable(false);
        message.setBackground(Theme.BG_INSET);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setForeground(Theme.FG_DEFAULT);
        message.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        message.setRows(6);
        message.setFont(message.getFont().deriveFont(14f));
        final JScrollPane messageScroll = new JScrollPane(message);
        messageScroll.setName("receivedApplicationMessageScroll");
        messageScroll.setPreferredSize(new Dimension(460, 130));
        messageScroll.setMinimumSize(new Dimension(240, 110));
        messageScroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER_DEFAULT));

        this.content.setOpaque(false);
        this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
        this.content.add(name);
        this.content.add(Box.createVerticalStrut(3));
        this.content.add(details);
        this.content.add(Box.createVerticalStrut(10));
        this.content.add(messageScroll);

        final JButton acceptButton = new JButton("Accept");
        acceptButton.setName("acceptButton");
        Buttons.success(acceptButton);
        final JButton declineButton = new JButton("Decline");
        declineButton.setName("declineButton");
        Buttons.danger(declineButton);
        final boolean pending = application.getStatus() == PostingApplicationStatus.PENDING;
        acceptButton.setEnabled(pending);
        declineButton.setEnabled(pending);
        acceptButton.addActionListener(event -> acceptController.accept(application.getApplicationId()));
        declineButton.addActionListener(event -> declineController.decline(application.getApplicationId()));

        this.actions.setOpaque(false);
        this.actions.add(acceptButton);
        this.actions.add(declineButton);
        add(this.content, BorderLayout.CENTER);
        add(this.actions, BorderLayout.EAST);
    }

    @Override
    public void reflow(final int width) {
        removeAll();
        if (width < STACK_BREAKPOINT) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.content.setAlignmentX(LEFT_ALIGNMENT);
            this.actions.setAlignmentX(LEFT_ALIGNMENT);
            this.actions.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                    this.actions.getPreferredSize().height));
            add(this.content);
            add(Box.createVerticalStrut(12));
            add(this.actions);
        } else {
            setLayout(new BorderLayout(16, 8));
            add(this.content, BorderLayout.CENTER);
            add(this.actions, BorderLayout.EAST);
        }
        revalidate();
    }

    private static String format(final String value) {
        final String lower = value.toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
