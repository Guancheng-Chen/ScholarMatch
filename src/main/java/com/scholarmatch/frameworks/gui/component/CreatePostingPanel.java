package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.function.Consumer;

/**
 * Reusable, headless-testable form for creating a posting.
 */
public final class CreatePostingPanel extends JPanel {

    private final Consumer<Submission> onSubmit;
    private final Runnable onCancel;
    private final JTextField titleField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea(8, 48);
    private final JComboBox<ResearchField> researchField =
            new JComboBox<>(ResearchField.values());
    private final JComboBox<CollaborationType> collaborationType =
            new JComboBox<>(CollaborationType.values());
    private final JTextField capacityField = new JTextField();
    private final JLabel validationMessage = new JLabel(" ");

    /**
     * Creates the posting form.
     *
     * @param onSubmit valid posting callback
     * @param onCancel cancellation callback
     */
    public CreatePostingPanel(
            final Consumer<Submission> onSubmit,
            final Runnable onCancel) {
        super(new BorderLayout());
        this.onSubmit = onSubmit;
        this.onCancel = onCancel;
        setName("createPostingPanel");
        setBackground(Theme.BG_DEFAULT);
        setBorder(BorderFactory.createEmptyBorder(24, 26, 22, 26));

        final JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        final JLabel heading = new JLabel("Create a Posting");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 25f));
        heading.setForeground(Theme.FG_DEFAULT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JLabel intro = new JLabel(
                "Share the collaboration opportunity and what you need from applicants.");
        intro.setForeground(Theme.FG_MUTED);
        intro.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(heading);
        content.add(Box.createVerticalStrut(7));
        content.add(intro);
        content.add(Box.createVerticalStrut(20));
        content.add(fields());

        final JScrollPane scroll = new JScrollPane(content);
        scroll.setName("createPostingFormScroll");
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
        add(footer(), BorderLayout.SOUTH);
    }

    private JPanel fields() {
        final RoundedPanel fields = new RoundedPanel(Theme.CARD_RADIUS, 20);
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        fields.setAlignmentX(Component.LEFT_ALIGNMENT);
        configure(this.titleField, "postingTitleInput");
        configure(this.capacityField, "postingCapacityInput");
        this.descriptionArea.setName("postingDescriptionInput");
        this.descriptionArea.setLineWrap(true);
        this.descriptionArea.setWrapStyleWord(true);
        this.descriptionArea.setFont(this.descriptionArea.getFont().deriveFont(14f));
        this.descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        final JScrollPane descriptionScroll = new JScrollPane(this.descriptionArea);
        descriptionScroll.setPreferredSize(new Dimension(560, 190));
        descriptionScroll.setMinimumSize(new Dimension(280, 150));
        descriptionScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.researchField.setName("postingResearchFieldInput");
        this.collaborationType.setName("postingCollaborationTypeInput");

        addField(fields, "Posting title", this.titleField);
        addField(fields, "Description", descriptionScroll);
        addField(fields, "Research field", this.researchField);
        addField(fields, "Collaboration type", this.collaborationType);
        addField(fields, "Team capacity (leave blank for unlimited)", this.capacityField);
        this.validationMessage.setName("createPostingValidation");
        this.validationMessage.setForeground(Theme.DANGER_FG);
        this.validationMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
        fields.add(this.validationMessage);
        return fields;
    }

    private JPanel footer() {
        final JButton cancel = new JButton("Cancel");
        cancel.setName("cancelPostingButton");
        Buttons.outlined(cancel);
        cancel.addActionListener(event -> this.onCancel.run());
        final JButton create = new JButton("Create Posting");
        create.setName("createPostingButton");
        Buttons.accent(create);
        create.addActionListener(event -> submit());
        final JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 16));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER_MUTED));
        footer.add(cancel);
        footer.add(create);
        return footer;
    }

    private void submit() {
        if (this.titleField.getText().isBlank()) {
            fail("Posting title is required.", this.titleField);
            return;
        }
        if (this.descriptionArea.getText().isBlank()) {
            fail("Description is required.", this.descriptionArea);
            return;
        }
        final Integer capacity;
        try {
            capacity = this.capacityField.getText().isBlank()
                    ? null : Integer.valueOf(this.capacityField.getText().trim());
        } catch (final NumberFormatException exception) {
            fail("Team capacity must be a positive whole number.", this.capacityField);
            return;
        }
        if (capacity != null && capacity <= 0) {
            fail("Team capacity must be a positive whole number.", this.capacityField);
            return;
        }
        this.validationMessage.setText(" ");
        this.onSubmit.accept(new Submission(
                this.titleField.getText().trim(),
                this.descriptionArea.getText().trim(),
                (ResearchField) this.researchField.getSelectedItem(),
                (CollaborationType) this.collaborationType.getSelectedItem(),
                capacity));
    }

    private void fail(final String message, final Component focus) {
        this.validationMessage.setText(message);
        focus.requestFocusInWindow();
    }

    private static void addField(
            final JPanel panel,
            final String text,
            final JComponent field) {
        final JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setForeground(Theme.FG_DEFAULT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(7));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                field.getPreferredSize().height));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    private static void configure(final JTextField field, final String name) {
        field.setName(name);
        field.setColumns(40);
        field.setPreferredSize(new Dimension(560, 38));
    }

    /**
     * Validated posting data passed to the existing create controller.
     *
     * @param title posting title
     * @param description posting description
     * @param researchField research field
     * @param collaborationType collaboration type
     * @param capacity optional capacity
     */
    public record Submission(
            String title,
            String description,
            ResearchField researchField,
            CollaborationType collaborationType,
            Integer capacity) {
    }
}
