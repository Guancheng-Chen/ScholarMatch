package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Modular, card-per-entry editor for a user's Education history.
 *
 * <p>"+ Add Education" appends a blank card with its own institution/degree/date fields
 * and its own Remove button — there is no separate "confirm" step per entry. Reading the
 * current state (#getEducations()) re-parses every visible card's fields directly; a card
 * missing an institution name or start month is silently skipped rather than blocking save,
 * so an accidentally-added blank card never prevents saving the rest of the profile.
 *
 * <p>Years are entered via a bounded spinner and months via an enum-backed combo box, so a
 * mistyped year like "2000000" or an out-of-range month can never be constructed at the
 * widget level. The "Currently Enrolled" checkbox is the single source of truth for whether
 * an entry is ongoing, replacing the old "blank end year means ongoing" convention that could
 * silently end up with an end month set on an otherwise-ongoing entry.
 */
public final class EducationEditorPanel extends JPanel {

    private static final int CARD_PADDING = 14;
    private static final int FIELD_HEIGHT = 34;
    private static final int LIST_HEIGHT = 420;
    private static final int MIN_YEAR = 1900;
    private static final int MAX_FUTURE_YEARS = 10;

    private final int cardWidth;
    private final JPanel cardList = new JPanel();
    private final List<EducationCard> cards = new ArrayList<>();

    /**
     * Constructs an EducationEditorPanel with no entries.
     *
     * @param width the outer width in pixels each entry card (and this panel) should occupy
     */
    public EducationEditorPanel(final int width) {
        super();
        this.cardWidth = width;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(width, Integer.MAX_VALUE));

        this.cardList.setLayout(new BoxLayout(this.cardList, BoxLayout.Y_AXIS));
        this.cardList.setOpaque(false);
        this.cardList.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.cardList.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));

        // Grows to fill whatever leftover height the outer Education card has (it's stretched
        // to match its taller sibling columns), instead of leaving dead space above the
        // "+ Add Education" button. Still naturally bounded by that stretched height, so a long
        // education history scrolls internally rather than pushing the button off-page.
        final JScrollPane listScroll = new JScrollPane(this.cardList,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listScroll.setBorder(null);
        listScroll.getViewport().setOpaque(false);
        listScroll.setOpaque(false);
        listScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        listScroll.setPreferredSize(new Dimension(width, LIST_HEIGHT));
        listScroll.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
        listScroll.getVerticalScrollBar().setUnitIncrement(16);

        final JButton addButton = new JButton("+ Add Education");
        Buttons.outlined(addButton);
        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addButton.addActionListener(evt -> addCard(null));

        add(listScroll);
        add(Box.createVerticalStrut(10));
        add(addButton);
    }

    /**
     * Replaces the panel's contents with one card per given education entry, e.g. when a
     * saved profile loads.
     *
     * @param educations the education history to display
     */
    public void setEducations(final List<Education> educations) {
        this.cards.clear();
        this.cardList.removeAll();
        for (final Education education : educations) {
            addCard(education);
        }
        this.cardList.revalidate();
        this.cardList.repaint();
    }

    /**
     * Returns the current education list, reading every visible card's fields directly.
     * Cards missing an institution name or start month are skipped.
     *
     * @return the education entries currently filled in
     */
    public List<Education> getEducations() {
        final List<Education> result = new ArrayList<>();
        for (final EducationCard card : this.cards) {
            final Education education = card.toEducationOrNull();
            if (education != null) {
                result.add(education);
            }
        }
        return result;
    }

    private void addCard(final Education initial) {
        final EducationCard card = new EducationCard(this.cardWidth, initial, this::removeCard);
        this.cards.add(card);
        this.cardList.add(card);
        this.cardList.add(Box.createVerticalStrut(10));
        this.cardList.revalidate();
        this.cardList.repaint();
    }

    private void removeCard(final EducationCard card) {
        this.cards.remove(card);
        final int index = indexOfComponent(this.cardList, card);
        if (index >= 0) {
            this.cardList.remove(index);
            if (index < this.cardList.getComponentCount()) {
                this.cardList.remove(index);
            }
        }
        this.cardList.revalidate();
        this.cardList.repaint();
    }

    private static int indexOfComponent(final JPanel panel, final Component target) {
        final Component[] components = panel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * A single self-contained, removable Education entry card.
     */
    private static final class EducationCard extends RoundedPanel {

        private final int innerWidth;
        private final JTextField institutionField;
        private final JComboBox<DegreeType> degreeCombo;
        private final JSpinner startYearSpinner;
        private final JComboBox<Month> startMonthCombo;
        private final JCheckBox ongoingCheckBox;
        private final JSpinner endYearSpinner;
        private final JComboBox<Month> endMonthCombo;

        EducationCard(final int outerWidth, final Education initial, final Consumer<EducationCard> onRemove) {
            super(10, CARD_PADDING);
            this.innerWidth = outerWidth - 2 * CARD_PADDING;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(outerWidth, Integer.MAX_VALUE));

            final int maxYear = Year.now().getValue() + MAX_FUTURE_YEARS;

            this.institutionField = field("Institution");
            this.degreeCombo = new JComboBox<>(DegreeType.values());
            styleCombo(this.degreeCombo);
            this.startYearSpinner = yearSpinner(maxYear);
            this.startMonthCombo = monthComboBox();
            this.ongoingCheckBox = new JCheckBox("Currently Enrolled");
            this.ongoingCheckBox.setOpaque(false);
            this.ongoingCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.endYearSpinner = yearSpinner(maxYear);
            this.endMonthCombo = monthComboBox();

            this.ongoingCheckBox.addItemListener(evt -> updateOngoingState());

            if (initial != null) {
                this.institutionField.setText(initial.getInstitution());
                this.degreeCombo.setSelectedItem(initial.getDegreeType());
                this.startYearSpinner.setValue(clampYear(initial.getStartYear(), maxYear));
                this.startMonthCombo.setSelectedItem(initial.getStartMonth());
                if (initial.isOngoing()) {
                    this.ongoingCheckBox.setSelected(true);
                } else {
                    this.endYearSpinner.setValue(clampYear(initial.getEndYear(), maxYear));
                    this.endMonthCombo.setSelectedItem(initial.getEndMonth());
                }
            }
            updateOngoingState();

            final JPanel startRow = new JPanel(new GridLayout(1, 2, 6, 6));
            startRow.setOpaque(false);
            startRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            startRow.setPreferredSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            startRow.setMaximumSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            startRow.add(labeledField("Start Year", this.startYearSpinner));
            startRow.add(labeledField("Start Month", this.startMonthCombo));

            final JPanel endRow = new JPanel(new GridLayout(1, 2, 6, 6));
            endRow.setOpaque(false);
            endRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            endRow.setPreferredSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            endRow.setMaximumSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            endRow.add(labeledField("End Year", this.endYearSpinner));
            endRow.add(labeledField("End Month", this.endMonthCombo));

            final JLabel hintLabel = new JLabel(
                "<html><body style='width:" + this.innerWidth + "px'>"
                    + "Institution and Start Month are required to be saved. End Month is required "
                    + "unless Currently Enrolled is checked.</body></html>");
            hintLabel.setForeground(Theme.FG_SUBTLE);
            hintLabel.setFont(hintLabel.getFont().deriveFont(10f));
            hintLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            final JButton removeButton = new JButton("Remove");
            Buttons.outlined(removeButton);
            removeButton.setForeground(Theme.DANGER_FG);
            removeButton.setFont(removeButton.getFont().deriveFont(11f));
            removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            removeButton.addActionListener(evt -> onRemove.accept(this));

            add(this.institutionField);
            add(Box.createVerticalStrut(6));
            add(this.degreeCombo);
            add(Box.createVerticalStrut(6));
            add(startRow);
            add(Box.createVerticalStrut(6));
            add(this.ongoingCheckBox);
            add(Box.createVerticalStrut(6));
            add(endRow);
            add(Box.createVerticalStrut(4));
            add(hintLabel);
            add(Box.createVerticalStrut(6));
            add(removeButton);
        }

        private void updateOngoingState() {
            final boolean ongoing = this.ongoingCheckBox.isSelected();
            this.endYearSpinner.setEnabled(!ongoing);
            this.endMonthCombo.setEnabled(!ongoing);
        }

        private static int clampYear(final int year, final int maxYear) {
            return Math.max(MIN_YEAR, Math.min(maxYear, year));
        }

        Education toEducationOrNull() {
            final String institution = this.institutionField.getText().trim();
            final Month startMonth = (Month) this.startMonthCombo.getSelectedItem();
            if (institution.isEmpty() || startMonth == null) {
                return null;
            }
            final int startYear = (Integer) this.startYearSpinner.getValue();
            final DegreeType degree = (DegreeType) this.degreeCombo.getSelectedItem();
            final Integer endYear;
            final Month endMonth;
            if (this.ongoingCheckBox.isSelected()) {
                endYear = null;
                endMonth = null;
            } else {
                endYear = (Integer) this.endYearSpinner.getValue();
                endMonth = (Month) this.endMonthCombo.getSelectedItem();
            }
            return new Education(
                institution,
                degree == null ? DegreeType.BACHELOR : degree,
                startYear, startMonth,
                endYear, endMonth);
        }

        private JSpinner yearSpinner(final int maxYear) {
            final JSpinner spinner = new JSpinner(
                new SpinnerNumberModel(Year.now().getValue(), MIN_YEAR, maxYear, 1));
            spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
            spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
            return spinner;
        }

        private JComboBox<Month> monthComboBox() {
            final Month[] options = new Month[13];
            System.arraycopy(Month.values(), 0, options, 1, 12);
            final JComboBox<Month> combo = new JComboBox<>(options);
            combo.setSelectedItem(null);
            combo.setAlignmentX(Component.LEFT_ALIGNMENT);
            combo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                    final JList<?> list, final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                    final Component c =
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setText(value == null ? "Select month" : ((Month) value).getDisplayName(
                        TextStyle.FULL, Locale.ENGLISH));
                    return c;
                }
            });
            return combo;
        }

        private JPanel labeledField(final String label, final JComponent field) {
            final JPanel column = new JPanel();
            column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
            column.setOpaque(false);
            column.setAlignmentX(Component.LEFT_ALIGNMENT);
            final JLabel labelComponent = new JLabel(label);
            labelComponent.setForeground(Theme.FG_SUBTLE);
            labelComponent.setFont(labelComponent.getFont().deriveFont(9f));
            labelComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
            column.add(labelComponent);
            column.add(field);
            return column;
        }

        private void styleCombo(final JComboBox<?> combo) {
            combo.setPreferredSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            combo.setMaximumSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            combo.setAlignmentX(Component.LEFT_ALIGNMENT);
            combo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                    final JList<?> list, final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                    final Component c =
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Enum<?> enumValue) {
                        setText(formatEnum(enumValue));
                    }
                    return c;
                }
            });
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

        private JTextField field(final String placeholder) {
            final JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            textField.setMaximumSize(new Dimension(this.innerWidth, FIELD_HEIGHT));
            textField.setAlignmentX(Component.LEFT_ALIGNMENT);
            textField.putClientProperty("JTextField.placeholderText", placeholder);
            return textField;
        }
    }
}
