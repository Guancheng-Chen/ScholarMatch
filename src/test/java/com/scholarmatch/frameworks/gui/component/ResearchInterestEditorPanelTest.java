package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResearchInterestEditorPanelTest {

    @Test
    void testAddButtonAddsANonBlankInterest() {
        final ResearchInterestEditorPanel panel = new ResearchInterestEditorPanel(400);

        SwingTestSupport.find(panel, JTextField.class, 0).setText("Reinforcement Learning");
        SwingTestSupport.find(panel, JButton.class, 0).doClick();

        assertEquals(List.of("Reinforcement Learning"), panel.getResearchInterests());
    }

    @Test
    void testAddButtonClearsTheFieldAfterAdding() {
        final ResearchInterestEditorPanel panel = new ResearchInterestEditorPanel(400);
        final JTextField field = SwingTestSupport.find(panel, JTextField.class, 0);
        field.setText("Reinforcement Learning");

        SwingTestSupport.find(panel, JButton.class, 0).doClick();

        assertEquals("", field.getText());
    }

    @Test
    void testAddButtonIgnoresBlankInput() {
        final ResearchInterestEditorPanel panel = new ResearchInterestEditorPanel(400);

        SwingTestSupport.find(panel, JTextField.class, 0).setText("   ");
        SwingTestSupport.find(panel, JButton.class, 0).doClick();

        assertEquals(List.of(), panel.getResearchInterests());
    }

    @Test
    void testAddButtonIgnoresADuplicateInterest() {
        final ResearchInterestEditorPanel panel = new ResearchInterestEditorPanel(400);
        final JTextField field = SwingTestSupport.find(panel, JTextField.class, 0);
        final JButton addButton = SwingTestSupport.find(panel, JButton.class, 0);
        field.setText("Reinforcement Learning");
        addButton.doClick();

        field.setText("Reinforcement Learning");
        addButton.doClick();

        assertEquals(List.of("Reinforcement Learning"), panel.getResearchInterests());
    }

    @Test
    void testEnterKeyInTheFieldBehavesLikeClickingAdd() {
        final ResearchInterestEditorPanel panel = new ResearchInterestEditorPanel(400);
        final JTextField field = SwingTestSupport.find(panel, JTextField.class, 0);
        field.setText("Reinforcement Learning");

        for (final var listener : field.getActionListeners()) {
            listener.actionPerformed(null);
        }

        assertEquals(List.of("Reinforcement Learning"), panel.getResearchInterests());
    }

    @Test
    void testSetResearchInterestsReplacesExistingContents() {
        final ResearchInterestEditorPanel panel = new ResearchInterestEditorPanel(400);
        panel.setResearchInterests(List.of("Old Interest"));

        panel.setResearchInterests(List.of("New Interest A", "New Interest B"));

        assertEquals(List.of("New Interest A", "New Interest B"), panel.getResearchInterests());
    }
}
