package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.usecase.dto.PostingData;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Locale;
import java.util.function.BiConsumer;

/**
 * Reusable, headless-testable form for applying to a posting.
 */
public final class ApplyToPostingPanel extends JPanel {

    private static final int MESSAGE_ROWS = 10;
    private static final int MESSAGE_COLUMNS = 52;

    private final PostingData posting;
    private final BiConsumer<String, String> onSubmit;
    private final Runnable onCancel;
    private final JTextArea messageArea = new JTextArea(MESSAGE_ROWS, MESSAGE_COLUMNS);
    private final JLabel characterCount = new JLabel("0 characters");
    private final JLabel validationMessage = new JLabel(" ");

    /**
     * Creates the application form.
     *
     * @param posting posting being applied to
     * @param onSubmit valid submission callback
     * @param onCancel cancellation callback
     */
    public ApplyToPostingPanel(
            final PostingData posting,
            final BiConsumer<String, String> onSubmit,
            final Runnable onCancel) {
        super(new BorderLayout());
        this.posting = posting;
        this.onSubmit = onSubmit;
        this.onCancel = onCancel;
        setName("applyToPostingPanel");
        setBackground(Theme.BG_DEFAULT);
        setBorder(BorderFactory.createEmptyBorder(24, 26, 22, 26));

        final JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(header());
        content.add(Box.createVerticalStrut(18));
        content.add(postingSummary());
        content.add(Box.createVerticalStrut(20));
        content.add(applicationSection());

        final JScrollPane formScroll = new JScrollPane(content);
        formScroll.setName("applicationFormScroll");
        formScroll.setBorder(null);
        formScroll.setOpaque(false);
        formScroll.getViewport().setOpaque(false);
        formScroll.getVerticalScrollBar().setUnitIncrement(16);
        add(formScroll, BorderLayout.CENTER);
        add(footer(), BorderLayout.SOUTH);
    }

    private JPanel header() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel heading = new JLabel("Apply to Posting");
        heading.setName("applyHeading");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 25f));
        heading.setForeground(Theme.FG_DEFAULT);
        final JLabel title = new JLabel(this.posting.getTitle());
        title.setName("applyPostingTitle");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setForeground(Theme.ACCENT_FG);
        final JLabel owner = new JLabel("Posted by " + this.posting.getPosterUserId());
        owner.setName("applyPostingOwner");
        owner.setForeground(Theme.FG_MUTED);
        final JLabel metadata = new JLabel(format(this.posting.getResearchField().name())
                + "  •  " + format(this.posting.getCollaborationType().name())
                + "  •  " + capacity(this.posting));
        metadata.setName("applyPostingMetadata");
        metadata.setForeground(Theme.FG_MUTED);

        panel.add(heading);
        panel.add(Box.createVerticalStrut(8));
        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(owner);
        panel.add(Box.createVerticalStrut(5));
        panel.add(metadata);
        return panel;
    }

    private JPanel postingSummary() {
        final RoundedPanel panel = new RoundedPanel(Theme.CARD_RADIUS, 18);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JLabel label = new JLabel("Posting summary");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 15f));
        label.setForeground(Theme.FG_DEFAULT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JTextArea description = textArea(this.posting.getDescription());
        description.setName("applyPostingDescription");
        description.setEditable(false);
        description.setFocusable(false);
        description.setRows(5);
        description.setBackground(Theme.BG_SUBTLE);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(description);
        return panel;
    }

    private JPanel applicationSection() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JLabel label = new JLabel("Application message");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        label.setForeground(Theme.FG_DEFAULT);
        final JLabel help = new JLabel(
                "Introduce yourself, explain your relevant interests or experience, "
                        + "and describe why you would like to collaborate.");
        help.setName("applicationInstructions");
        help.setForeground(Theme.FG_MUTED);

        this.messageArea.setName("applicationMessageInput");
        this.messageArea.setLineWrap(true);
        this.messageArea.setWrapStyleWord(true);
        this.messageArea.setFont(this.messageArea.getFont().deriveFont(14f));
        this.messageArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        this.messageArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent event) {
                updateCount();
            }

            @Override
            public void removeUpdate(final DocumentEvent event) {
                updateCount();
            }

            @Override
            public void changedUpdate(final DocumentEvent event) {
                updateCount();
            }
        });
        final JScrollPane messageScroll = new JScrollPane(this.messageArea);
        messageScroll.setName("applicationMessageScroll");
        messageScroll.setPreferredSize(new Dimension(590, 210));
        messageScroll.setMinimumSize(new Dimension(300, 180));
        messageScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.characterCount.setName("applicationCharacterCount");
        this.characterCount.setForeground(Theme.FG_SUBTLE);
        this.validationMessage.setName("applicationValidation");
        this.validationMessage.setForeground(Theme.DANGER_FG);

        final JPanel feedback = new JPanel(new BorderLayout());
        feedback.setOpaque(false);
        feedback.setAlignmentX(Component.LEFT_ALIGNMENT);
        feedback.add(this.validationMessage, BorderLayout.WEST);
        feedback.add(this.characterCount, BorderLayout.EAST);

        panel.add(label);
        panel.add(Box.createVerticalStrut(6));
        panel.add(help);
        panel.add(Box.createVerticalStrut(10));
        panel.add(messageScroll);
        panel.add(Box.createVerticalStrut(7));
        panel.add(feedback);
        return panel;
    }

    private JPanel footer() {
        final JButton cancel = new JButton("Cancel");
        cancel.setName("cancelApplicationButton");
        Buttons.outlined(cancel);
        cancel.addActionListener(event -> this.onCancel.run());
        final JButton submit = new JButton("Submit Application");
        submit.setName("submitApplicationButton");
        Buttons.accent(submit);
        submit.addActionListener(event -> submit());

        final JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 16));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER_MUTED));
        footer.add(cancel);
        footer.add(submit);
        return footer;
    }

    private void submit() {
        final String message = this.messageArea.getText();
        if (message.isBlank()) {
            this.validationMessage.setText("Please write an application message before submitting.");
            this.messageArea.requestFocusInWindow();
            return;
        }
        this.validationMessage.setText(" ");
        this.onSubmit.accept(this.posting.getPostingId(), message);
    }

    private void updateCount() {
        this.characterCount.setText(this.messageArea.getText().length() + " characters");
        if (!this.messageArea.getText().isBlank()) {
            this.validationMessage.setText(" ");
        }
    }

    private static JTextArea textArea(final String text) {
        final JTextArea area = new JTextArea(text);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setForeground(Theme.FG_DEFAULT);
        area.setFont(area.getFont().deriveFont(14f));
        area.setBorder(BorderFactory.createEmptyBorder());
        return area;
    }

    private static String capacity(final PostingData posting) {
        if (posting.getCapacity() == null) {
            return posting.getAcceptedCount() + " accepted / unlimited capacity";
        }
        return posting.getAcceptedCount() + " of " + posting.getCapacity() + " accepted";
    }

    private static String format(final String value) {
        final String lower = value.toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}
