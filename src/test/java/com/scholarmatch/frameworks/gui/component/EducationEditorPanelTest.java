package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EducationEditorPanelTest {

    @Test
    void testAddedBlankCardIsSkippedByGetEducations() {
        final EducationEditorPanel panel = new EducationEditorPanel(600);

        addButton(panel).doClick();

        assertTrue(panel.getEducations().isEmpty());
    }

    @Test
    void testFillingInInstitutionAndStartMonthProducesAnEntry() {
        final EducationEditorPanel panel = new EducationEditorPanel(600);
        addButton(panel).doClick();

        SwingTestSupport.find(panel, JTextField.class, 0).setText("Cambridge");
        setComboSelection(SwingTestSupport.find(panel, JComboBox.class, 1), Month.SEPTEMBER);

        final List<Education> educations = panel.getEducations();
        assertEquals(1, educations.size());
        assertEquals("Cambridge", educations.get(0).getInstitution());
        assertEquals(Month.SEPTEMBER, educations.get(0).getStartMonth());
    }

    @Test
    void testCheckingCurrentlyEnrolledDisablesEndDateControls() {
        final EducationEditorPanel panel = new EducationEditorPanel(600);
        addButton(panel).doClick();
        final JSpinner endYearSpinner = SwingTestSupport.find(panel, JSpinner.class, 1);
        final JComboBox<?> endMonthCombo = SwingTestSupport.find(panel, JComboBox.class, 2);
        assertTrue(endYearSpinner.isEnabled());
        assertTrue(endMonthCombo.isEnabled());

        SwingTestSupport.find(panel, JCheckBox.class, 0).doClick();

        assertFalse(endYearSpinner.isEnabled());
        assertFalse(endMonthCombo.isEnabled());
    }

    @Test
    void testCurrentlyEnrolledEntryHasNoEndDate() {
        final EducationEditorPanel panel = new EducationEditorPanel(600);
        addButton(panel).doClick();
        SwingTestSupport.find(panel, JTextField.class, 0).setText("Cambridge");
        setComboSelection(SwingTestSupport.find(panel, JComboBox.class, 1), Month.SEPTEMBER);

        SwingTestSupport.find(panel, JCheckBox.class, 0).doClick();

        final Education education = panel.getEducations().get(0);
        assertTrue(education.isOngoing());
        assertNull(education.getEndYear());
    }

    @Test
    void testSetEducationsPopulatesACardPerEntryAndRoundTrips() {
        final EducationEditorPanel panel = new EducationEditorPanel(600);
        final Education saved = new Education("MIT", DegreeType.MASTER, 2015, Month.JANUARY, 2017, Month.MAY);

        panel.setEducations(List.of(saved));

        final List<Education> result = panel.getEducations();
        assertEquals(1, result.size());
        assertEquals("MIT", result.get(0).getInstitution());
        assertEquals(DegreeType.MASTER, result.get(0).getDegreeType());
        assertEquals(2015, result.get(0).getStartYear());
        assertEquals(Month.JANUARY, result.get(0).getStartMonth());
        assertEquals(2017, result.get(0).getEndYear());
        assertEquals(Month.MAY, result.get(0).getEndMonth());
    }

    @Test
    void testRemoveButtonDropsTheCardFromGetEducations() {
        final EducationEditorPanel panel = new EducationEditorPanel(600);
        addButton(panel).doClick();
        SwingTestSupport.find(panel, JTextField.class, 0).setText("Cambridge");
        setComboSelection(SwingTestSupport.find(panel, JComboBox.class, 1), Month.SEPTEMBER);
        assertEquals(1, panel.getEducations().size());

        removeButton(panel).doClick();

        assertTrue(panel.getEducations().isEmpty());
    }

    /**
     * The panel's JScrollPane creates its vertical scrollbar (with its own arrow JButtons)
     * up front, so a plain index-based JButton search can hit a scrollbar arrow instead of
     * the real button — look up by label text instead.
     */
    private JButton addButton(final EducationEditorPanel panel) {
        return buttonWithText(panel, "+ Add Education");
    }

    private JButton removeButton(final EducationEditorPanel panel) {
        return buttonWithText(panel, "Remove");
    }

    private JButton buttonWithText(final EducationEditorPanel panel, final String text) {
        for (final JButton button : SwingTestSupport.findAll(panel, JButton.class)) {
            if (text.equals(button.getText())) {
                return button;
            }
        }
        throw new IllegalStateException("No button with text: " + text);
    }

    @SuppressWarnings("unchecked")
    private void setComboSelection(final JComboBox<?> combo, final Object value) {
        ((JComboBox<Object>) combo).setSelectedItem(value);
    }
}
