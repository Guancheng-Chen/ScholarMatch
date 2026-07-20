package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Theme;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Editable list of a user's free-form research interest keywords.
 *
 * <p>Entries are typed one at a time and added to a list, removed individually by
 * selection. Used by profile editing.
 */
public final class ResearchInterestEditorPanel extends JPanel {

    private static final int FIELD_HEIGHT = 34;

    private final int cardWidth;
    private final DefaultListModel<String> interestsModel = new DefaultListModel<>();

    /**
     * Constructs a ResearchInterestEditorPanel.
     *
     * @param width the width in pixels this panel's fields and list should occupy
     */
    public ResearchInterestEditorPanel(final int width) {
        super();
        this.cardWidth = width;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(width, Integer.MAX_VALUE));

        final JTextField interestField = field("e.g. Reinforcement Learning");
        final JButton addButton = smallButton("Add");

        final JList<String> interestList = new JList<>(this.interestsModel);
        interestList.setVisibleRowCount(3);
        interestList.setBackground(Theme.BG_INSET);
        interestList.setForeground(Theme.FG_DEFAULT);
        interestList.setPreferredSize(new Dimension(width, 70));
        interestList.setMaximumSize(new Dimension(width, 70));
        interestList.setAlignmentX(Component.LEFT_ALIGNMENT);
        interestList.setVisible(false);

        final JButton removeButton = smallButton("Remove Selected");
        removeButton.setForeground(Theme.DANGER_FG);
        removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeButton.setVisible(false);

        addButton.addActionListener(evt -> {
            final String text = interestField.getText().trim();
            if (!text.isEmpty() && !this.interestsModel.contains(text)) {
                this.interestsModel.addElement(text);
                interestField.setText("");
            }
        });
        interestField.addActionListener(evt -> addButton.doClick());

        removeButton.addActionListener(evt -> {
            final String selected = interestList.getSelectedValue();
            if (selected != null) {
                this.interestsModel.removeElement(selected);
            }
        });

        this.interestsModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(final ListDataEvent e) {
                update();
            }

            @Override
            public void intervalRemoved(final ListDataEvent e) {
                update();
            }

            @Override
            public void contentsChanged(final ListDataEvent e) {
                update();
            }

            private void update() {
                final boolean hasInterests = !ResearchInterestEditorPanel.this.interestsModel.isEmpty();
                interestList.setVisible(hasInterests);
                removeButton.setVisible(hasInterests);
                interestList.revalidate();
                interestList.getParent().revalidate();
                interestList.getParent().repaint();
            }
        });

        addAll(this, row(interestField, addButton), strut(), interestList, removeButton);
    }

    /**
     * Replaces the panel's contents with the given interests, e.g. when a saved profile loads.
     *
     * @param interests the research interests to display
     */
    public void setResearchInterests(final List<String> interests) {
        this.interestsModel.clear();
        for (final String interest : interests) {
            this.interestsModel.addElement(interest);
        }
    }

    /**
     * Returns the current research interest list, reflecting any additions and removals
     * made in this panel.
     *
     * @return a copy of the current research interest list
     */
    public List<String> getResearchInterests() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < this.interestsModel.size(); i++) {
            list.add(this.interestsModel.get(i));
        }
        return list;
    }

    private JPanel row(final JTextField textField, final JButton button) {
        final JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setPreferredSize(new Dimension(this.cardWidth, FIELD_HEIGHT));
        row.setMaximumSize(new Dimension(this.cardWidth, FIELD_HEIGHT));
        row.add(textField, BorderLayout.CENTER);
        row.add(button, BorderLayout.EAST);
        return row;
    }

    private JTextField field(final String placeholder) {
        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(this.cardWidth, FIELD_HEIGHT));
        textField.setMaximumSize(new Dimension(this.cardWidth, FIELD_HEIGHT));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        return textField;
    }

    private JButton smallButton(final String text) {
        final JButton button = new JButton(text);
        Buttons.outlined(button);
        button.setFont(button.getFont().deriveFont(11f));
        return button;
    }

    private static Component strut() {
        return Box.createVerticalStrut(6);
    }

    private static void addAll(final JPanel panel, final Component... components) {
        for (final Component component : components) {
            panel.add(component);
        }
    }
}
