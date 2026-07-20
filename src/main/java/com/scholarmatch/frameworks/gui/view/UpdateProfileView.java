package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.component.EducationEditorPanel;
import com.scholarmatch.frameworks.gui.component.PublicationEditorPanel;
import com.scholarmatch.frameworks.gui.component.ResearchInterestEditorPanel;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.LoadProfileController;
import com.scholarmatch.interface_adapter.controller.PaperLookupController;
import com.scholarmatch.interface_adapter.controller.UpdateProfileController;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * Profile-editing screen.
 *
 * <p>Loads the current user's full saved profile via LoadProfileController and
 * pre-fills every editable field with it, so this is a genuine edit-and-save form rather
 * than a blank one-way upload. Laid out as three side-by-side cards instead of one long
 * column so the form doesn't feel cramped: Basic Info (identity, academic profile,
 * descriptions), Research Interest (interest tags + publications), and Education (its own
 * modular add/remove card list). A single Save Profile button below submits all three.
 */
public final class UpdateProfileView extends JPanel {

    private static final int COLUMN_WIDTH = 300;
    private static final int COLUMN_PADDING = 20;
    private static final int COLUMN_OUTER_WIDTH = COLUMN_WIDTH + 2 * COLUMN_PADDING;
    private static final int COLUMN_GAP = 20;
    private static final int ROW_WIDTH = COLUMN_OUTER_WIDTH * 3 + COLUMN_GAP * 2;
    private static final int FIELD_HEIGHT = 34;

    /**
     * Constructs the UpdateProfileView.
     *
     * @param controller             forwards form data to the update-profile use case
     * @param loadProfileController  loads the current user's full saved profile
     * @param viewModel              observable profile-edit state
     * @param paperLookupController the controller that handles paper/author auto-fill searches
     * @param paperLookupViewModel   the observable state for paper/author auto-fill searches
     */
    public UpdateProfileView(
        final UpdateProfileController controller,
        final LoadProfileController loadProfileController,
        final UpdateProfileViewModel viewModel,
        final PaperLookupController paperLookupController,
        final PaperLookupViewModel paperLookupViewModel) {
        super(new BorderLayout());
        setBackground(Theme.BG_DEFAULT);

        final JLabel titleLabel = new JLabel("Edit Profile");
        titleLabel.setForeground(Theme.FG_DEFAULT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JTextField emailField = field("Email");

        final JComboBox<Institution> institutionCombo = new JComboBox<>(sortedInstitutions());
        styleInstitutionCombo(institutionCombo);

        final JComboBox<AcademicLevel> academicLevelCombo = new JComboBox<>(AcademicLevel.values());
        styleCombo(academicLevelCombo);

        final JComboBox<CollaborationType> lookingForCombo = new JComboBox<>(CollaborationType.values());
        styleCombo(lookingForCombo);

        final JComboBox<ResearchField> researchFieldCombo = new JComboBox<>(ResearchField.values());
        styleCombo(researchFieldCombo);

        final JComboBox<FundingStatus> fundingStatusCombo = new JComboBox<>(FundingStatus.values());
        styleCombo(fundingStatusCombo);

        final JTextField weeklyAvailabilityField = field("Weekly Availability (hours)");

        final JTextField phoneField = field("Phone Number");

        final JTextField hIndexField = field("h-index");
        final JTextField citationsField = field("Total Citations");

        final JTextArea collaborationArea = textArea();
        final JScrollPane collaborationScroll = scrollWrap(collaborationArea);

        final JTextArea researchArea = textArea();
        final JScrollPane researchScroll = scrollWrap(researchArea);

        final ResearchInterestEditorPanel researchInterestEditorPanel = new ResearchInterestEditorPanel(COLUMN_WIDTH);
        final PublicationEditorPanel publicationEditorPanel =
            new PublicationEditorPanel(paperLookupController, paperLookupViewModel, COLUMN_WIDTH);
        final EducationEditorPanel educationEditorPanel = new EducationEditorPanel(COLUMN_WIDTH);

        // Pre-fill every field once the current profile loads, instead of starting blank.
        viewModel.currentUserProperty().addListener(user -> {
            if (user == null) {
                return;
            }
            emailField.setText(user.getEmail());
            institutionCombo.setSelectedItem(user.getInstitution());
            academicLevelCombo.setSelectedItem(user.getAcademicLevel());
            lookingForCombo.setSelectedItem(user.getLookingFor());
            researchFieldCombo.setSelectedItem(user.getResearchField());
            fundingStatusCombo.setSelectedItem(user.getFundingStatus());
            weeklyAvailabilityField.setText(
                user.getWeeklyAvailabilityHours() == null ? "" : String.valueOf(user.getWeeklyAvailabilityHours()));
            phoneField.setText(user.getPhoneNumber());
            hIndexField.setText(user.gethIndex() == null ? "" : String.valueOf(user.gethIndex()));
            citationsField.setText(user.getTotalCitations() == null ? "" : String.valueOf(user.getTotalCitations()));
            collaborationArea.setText(user.getCollaborationDescription());
            researchArea.setText(user.getResearchDescription());
            researchInterestEditorPanel.setResearchInterests(user.getResearchInterests());
            educationEditorPanel.setEducations(user.getEducations());
            publicationEditorPanel.setPublications(user.getPublications());
        });

        viewModel.errorMessageProperty().addListener(message -> {
            if (message != null && !message.isBlank()) {
                JOptionPane.showMessageDialog(this, message, "Save Profile Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewModel.saveSuccessMessageProperty().addListener(message -> {
            if (message != null && !message.isBlank()) {
                JOptionPane.showMessageDialog(this, message, "Save Profile", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        final JButton submitButton = new JButton("Save Profile");
        Buttons.accent(submitButton);
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.setPreferredSize(new Dimension(ROW_WIDTH, 38));
        submitButton.setMaximumSize(new Dimension(ROW_WIDTH, 38));
        submitButton.addActionListener(evt -> {
            if (!isBlankOrNonNegativeInt(hIndexField.getText())
                || !isBlankOrNonNegativeInt(citationsField.getText())
                || !isBlankOrNonNegativeInt(weeklyAvailabilityField.getText())) {
                JOptionPane.showMessageDialog(this,
                    "h-index, Total Citations, and Weekly Availability must be whole numbers.",
                    "Save Profile Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            final Integer hIndex = blankToNull(hIndexField.getText());
            final Integer totalCitations = blankToNull(citationsField.getText());
            final Integer weeklyAvailabilityHours = blankToNull(weeklyAvailabilityField.getText());
            final Institution selectedInstitution = (Institution) institutionCombo.getSelectedItem();
            final AcademicLevel selectedLevel = (AcademicLevel) academicLevelCombo.getSelectedItem();
            final CollaborationType selectedLookingFor = (CollaborationType) lookingForCombo.getSelectedItem();
            final ResearchField selectedResearchField = (ResearchField) researchFieldCombo.getSelectedItem();
            final FundingStatus selectedFundingStatus = (FundingStatus) fundingStatusCombo.getSelectedItem();
            // updateProfile() blocks on a network call — run it off the EDT so the form
            // doesn't freeze for the duration of the request.
            submitButton.setEnabled(false);
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    controller.updateProfile(
                        emailField.getText().trim(),
                        selectedInstitution == null ? null : selectedInstitution.name(),
                        selectedLevel == null ? null : selectedLevel.name(),
                        selectedResearchField == null ? null : selectedResearchField.name(),
                        selectedLookingFor == null ? null : selectedLookingFor.name(),
                        collaborationArea.getText(),
                        researchArea.getText(),
                        weeklyAvailabilityHours,
                        selectedFundingStatus == null ? null : selectedFundingStatus.name(),
                        researchInterestEditorPanel.getResearchInterests(),
                        phoneField.getText(),
                        hIndex,
                        totalCitations,
                        educationEditorPanel.getEducations(),
                        publicationEditorPanel.getPublications()
                    );
                    return null;
                }

                @Override
                protected void done() {
                    submitButton.setEnabled(true);
                }
            }.execute();
        });

        final RoundedPanel basicInfoCard = buildColumnCard("Basic Info",
            labeled("Email"), emailField, strut(),
            labeled("Institution"), institutionCombo, strut(),
            labeled("Academic Level"), academicLevelCombo, strut(),
            labeled("Research Field"), researchFieldCombo, strut(),
            labeled("Looking For"), lookingForCombo, strut(),
            labeled("Funding Status"), fundingStatusCombo, strut(),
            labeled("Weekly Availability (hours)"), weeklyAvailabilityField, strut(),
            labeled("Phone Number"), phoneField, strut(),
            labeled("h-index"), hIndexField, strut(),
            labeled("Total Citations"), citationsField, strut(),
            labeled("Collaboration Description"), collaborationScroll, strut(),
            labeled("Research Description"), researchScroll);

        final RoundedPanel researchInterestCard = buildColumnCard("Research Interest",
            labeled("Research Interests"), researchInterestEditorPanel, strut(),
            labeled("Publications"), publicationEditorPanel);

        final RoundedPanel educationCard = buildColumnCard("Education", educationEditorPanel);

        final JPanel columnsRow = new JPanel();
        columnsRow.setLayout(new BoxLayout(columnsRow, BoxLayout.X_AXIS));
        columnsRow.setOpaque(false);
        columnsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        basicInfoCard.setAlignmentY(Component.TOP_ALIGNMENT);
        researchInterestCard.setAlignmentY(Component.TOP_ALIGNMENT);
        educationCard.setAlignmentY(Component.TOP_ALIGNMENT);
        columnsRow.add(basicInfoCard);
        columnsRow.add(Box.createHorizontalStrut(COLUMN_GAP));
        columnsRow.add(researchInterestCard);
        columnsRow.add(Box.createHorizontalStrut(COLUMN_GAP));
        columnsRow.add(educationCard);

        final JPanel formRoot = new JPanel();
        formRoot.setLayout(new BoxLayout(formRoot, BoxLayout.Y_AXIS));
        formRoot.setOpaque(false);
        addAll(formRoot, titleLabel, strut(), columnsRow, strut(), submitButton);

        final CenteringScrollPanel centeringPanel = new CenteringScrollPanel(formRoot);
        centeringPanel.setBorder(new EmptyBorder(24, 0, 24, 0));
        final JScrollPane scrollPane = new JScrollPane(centeringPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.BG_DEFAULT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        loadProfileController.execute();
    }

    /**
     * Builds one of the three top-level profile columns: a titled card containing the
     * given children, stacked vertically.
     */
    private RoundedPanel buildColumnCard(final String title, final Component... children) {
        final RoundedPanel card = new RoundedPanel(Theme.CARD_RADIUS, COLUMN_PADDING);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Theme.FG_DEFAULT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 15f));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        card.add(titleLabel);
        for (final Component child : children) {
            card.add(child);
        }

        // Height must be read after children are added — an empty card's preferred height
        // is ~0, which was locking every card to a near-zero height and clipping the form.
        card.setPreferredSize(new Dimension(COLUMN_OUTER_WIDTH, card.getPreferredSize().height));
        card.setMaximumSize(new Dimension(COLUMN_OUTER_WIDTH, Integer.MAX_VALUE));

        return card;
    }

    private String formatEnum(final Enum<?> value) {
        if (value == null) {
            return "";
        }
        final String[] words = value.name().split("_");
        final StringBuilder result = new StringBuilder();
        for (final String word : words) {
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(word.charAt(0)).append(word.substring(1).toLowerCase(Locale.ROOT));
        }
        return result.toString();
    }

    private boolean isBlankOrNonNegativeInt(final String text) {
        final String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return true;
        }
        try {
            return Integer.parseInt(trimmed) >= 0;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    private Integer blankToNull(final String text) {
        final String trimmed = text.trim();
        return trimmed.isEmpty() ? null : Integer.parseInt(trimmed);
    }

    private void styleCombo(final JComboBox<?> combo) {
        combo.setPreferredSize(new Dimension(COLUMN_WIDTH, FIELD_HEIGHT));
        combo.setMaximumSize(new Dimension(COLUMN_WIDTH, FIELD_HEIGHT));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                final JList<?> list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
                final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Enum<?> enumValue) {
                    setText(formatEnum(enumValue));
                }
                return c;
            }
        });
    }

    /**
     * Returns every {@link Institution}, sorted alphabetically by
     * {@link Institution#getDisplayName()} for the dropdown — the enum's declared order is
     * QS-rank order (see the class doc), which isn't a useful order for a user scanning a
     * 600-entry list for their own school. {@link Institution#OTHER} is pinned last as the
     * fallback choice rather than sorted in around "O".
     */
    private static Institution[] sortedInstitutions() {
        final Institution[] sorted = Institution.values().clone();
        Arrays.sort(sorted, Comparator
            .comparing((Institution institution) -> institution == Institution.OTHER)
            .thenComparing(Institution::getDisplayName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    /**
     * Institution's display text comes from {@link Institution#getDisplayName()} (e.g. "MIT" -&gt;
     * "Massachusetts Institute of Technology") rather than the enum constant name, so it needs
     * its own renderer instead of {@link #styleCombo}'s generic underscore-splitting one.
     */
    private void styleInstitutionCombo(final JComboBox<Institution> combo) {
        combo.setPreferredSize(new Dimension(COLUMN_WIDTH, FIELD_HEIGHT));
        combo.setMaximumSize(new Dimension(COLUMN_WIDTH, FIELD_HEIGHT));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                final JList<?> list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
                final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Institution institution) {
                    setText(institution.getDisplayName());
                }
                return c;
            }
        });
    }

    private JTextField field(final String placeholder) {
        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(COLUMN_WIDTH, FIELD_HEIGHT));
        textField.setMaximumSize(new Dimension(COLUMN_WIDTH, FIELD_HEIGHT));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        return textField;
    }

    private JTextArea textArea() {
        final JTextArea textArea = new JTextArea(3, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Theme.BG_INSET);
        textArea.setForeground(Theme.FG_DEFAULT);
        textArea.setCaretColor(Theme.FG_DEFAULT);
        textArea.setBorder(new EmptyBorder(6, 8, 6, 8));
        return textArea;
    }

    private JScrollPane scrollWrap(final JComponent component) {
        final JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setPreferredSize(new Dimension(COLUMN_WIDTH, 80));
        scrollPane.setMaximumSize(new Dimension(COLUMN_WIDTH, 80));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        return scrollPane;
    }

    private JLabel labeled(final String text) {
        final JLabel label = new JLabel(text.toUpperCase(Locale.ROOT));
        label.setForeground(Theme.FG_SUBTLE);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 10f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private static Component strut() {
        return Box.createVerticalStrut(4);
    }

    private static void addAll(final JPanel panel, final Component... components) {
        for (final Component component : components) {
            panel.add(component);
        }
    }
}
