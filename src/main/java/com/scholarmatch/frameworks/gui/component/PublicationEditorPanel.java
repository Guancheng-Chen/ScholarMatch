package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.PaperLookupController;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Editable list of a user's Publications, backed by Semantic User author search.
 *
 * <p>Entries are added by searching for an author by name and importing their papers in
 * bulk (Semantic User has no single-title lookup), and removed individually by selection.
 * Since Publication is an immutable value object, editing here means swapping the
 * whole list held by this panel — never mutating an existing Publication in place.
 * Used by both the registration flow and profile editing.
 */
public final class PublicationEditorPanel extends JPanel {

    private static final int DEFAULT_WIDTH = 460;
    private static final int FIELD_HEIGHT = 34;

    private final int cardWidth;
    private final DefaultListModel<Publication> papersModel = new DefaultListModel<>();
    private BiConsumer<Integer, Integer> onAuthorMetadata = (hIndex, citationCount) -> { };

    /**
     * Constructs a PublicationEditorPanel at the default width.
     *
     * @param paperLookupController the controller that handles paper/author auto-fill searches
     * @param paperLookupViewModel  the observable state for paper/author auto-fill searches
     */
    public PublicationEditorPanel(
        final PaperLookupController paperLookupController,
        final PaperLookupViewModel paperLookupViewModel) {
        this(paperLookupController, paperLookupViewModel, DEFAULT_WIDTH);
    }

    /**
     * Constructs a PublicationEditorPanel.
     *
     * @param paperLookupController the controller that handles paper/author auto-fill searches
     * @param paperLookupViewModel  the observable state for paper/author auto-fill searches
     * @param width                 the width in pixels this panel's fields and lists should occupy
     */
    public PublicationEditorPanel(
        final PaperLookupController paperLookupController,
        final PaperLookupViewModel paperLookupViewModel,
        final int width) {
        super();
        this.cardWidth = width;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(12, 0, 0, 0),
            BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, Theme.BORDER_MUTED),
                new EmptyBorder(12, 0, 0, 0))));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(width, Integer.MAX_VALUE));

        final JTextField authorField = field("Your name (author search)");
        final JButton searchAuthorButton = smallButton("Search Author");
        final JLabel authorExampleLabel = exampleLabel("e.g. Geoffrey Hinton");

        final DefaultListModel<AuthorCandidateData> candidatesModel = new DefaultListModel<>();
        final JList<AuthorCandidateData> candidateList = new JList<>(candidatesModel);
        candidateList.setVisibleRowCount(3);
        candidateList.setBackground(Theme.BG_INSET);
        candidateList.setForeground(Theme.FG_DEFAULT);
        candidateList.setPreferredSize(new Dimension(width, 70));
        candidateList.setMaximumSize(new Dimension(width, 70));
        candidateList.setAlignmentX(Component.LEFT_ALIGNMENT);
        candidateList.setVisible(false);
        candidateList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                final JList<?> list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
                final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof AuthorCandidateData candidate) {
                    setText(candidate.getName() + " — " + candidate.getAffiliations()
                        + " (" + candidate.getPaperCount() + " papers)");
                }
                return c;
            }
        });
        final JButton selectAuthorButton = smallButton("Import This Author's Papers");
        selectAuthorButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectAuthorButton.setVisible(false);

        paperLookupViewModel.getAuthorCandidates().addListener(() -> {
            candidatesModel.clear();
            candidatesModel.addAll(paperLookupViewModel.getAuthorCandidates());
            final boolean hasCandidates = !candidatesModel.isEmpty();
            candidateList.setVisible(hasCandidates);
            selectAuthorButton.setVisible(hasCandidates);
            candidateList.revalidate();
            candidateList.getParent().revalidate();
            candidateList.getParent().repaint();
        });
        searchAuthorButton.addActionListener(evt -> paperLookupController.searchAuthors(authorField.getText()));

        selectAuthorButton.addActionListener(evt -> {
            final AuthorCandidateData selected = candidateList.getSelectedValue();
            if (selected != null) {
                paperLookupController.selectAuthor(selected.getAuthorId());
                this.onAuthorMetadata.accept(selected.getHIndex(), selected.getCitationCount());
            }
        });

        final JList<Publication> papersListView = new JList<>(this.papersModel);
        papersListView.setVisibleRowCount(3);
        papersListView.setBackground(Theme.BG_INSET);
        papersListView.setForeground(Theme.FG_DEFAULT);
        papersListView.setPreferredSize(new Dimension(width, 70));
        papersListView.setMaximumSize(new Dimension(width, 70));
        papersListView.setAlignmentX(Component.LEFT_ALIGNMENT);
        papersListView.setVisible(false);
        papersListView.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                final JList<?> list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
                final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Publication paper) {
                    setText(paper.getTitle() + " (" + paper.getYear() + ")");
                }
                return c;
            }
        });
        final JButton removeButton = smallButton("Remove Selected Paper");
        removeButton.setForeground(Theme.DANGER_FG);
        removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeButton.setVisible(false);
        removeButton.addActionListener(evt -> {
            final Publication selected = papersListView.getSelectedValue();
            if (selected != null) {
                this.papersModel.removeElement(selected);
            }
        });
        this.papersModel.addListDataListener(new ListDataListener() {
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
                final boolean hasPapers = !PublicationEditorPanel.this.papersModel.isEmpty();
                papersListView.setVisible(hasPapers);
                removeButton.setVisible(hasPapers);
                papersListView.revalidate();
                papersListView.getParent().revalidate();
                papersListView.getParent().repaint();
            }
        });

        final JLabel statusLabel = new JLabel(" ");
        statusLabel.setForeground(Theme.FG_MUTED);
        statusLabel.setFont(statusLabel.getFont().deriveFont(11f));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        paperLookupViewModel.statusMessageProperty().addListener(statusLabel::setText);

        paperLookupViewModel.getAuthorPapersFound().addListener(
            () -> this.papersModel.addAll(paperLookupViewModel.getAuthorPapersFound()));

        final JLabel sectionLabel = new JLabel("Papers (auto-filled from Semantic User)");
        sectionLabel.setForeground(Theme.FG_MUTED);
        sectionLabel.setFont(sectionLabel.getFont().deriveFont(Font.BOLD, 11f));
        sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel fallbackNoticeLabel = new JLabel("<html><body style='width:340px'>"
            + "Note: Semantic User's free API is rate-limited. If it's temporarily unavailable, "
            + "search falls back to a small built-in sample (5 classic papers, e.g. \"Attention Is "
            + "All You Need\", \"Deep Residual Learning for Image Recognition\") — other queries "
            + "may return nothing until the live API is available again.</body></html>");
        fallbackNoticeLabel.setForeground(Theme.FG_MUTED);
        fallbackNoticeLabel.setFont(fallbackNoticeLabel.getFont().deriveFont(11f));
        fallbackNoticeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        addAll(this, sectionLabel, strut(),
            row(authorField, searchAuthorButton), authorExampleLabel, strut(),
            candidateList, selectAuthorButton, strut(),
            statusLabel, strut(),
            papersListView, removeButton, strut(),
            fallbackNoticeLabel);
    }

    /**
     * Replaces the panel's contents with the given publications, e.g. when a saved profile loads.
     *
     * @param publications the publications to display
     */
    public void setPublications(final List<Publication> publications) {
        this.papersModel.clear();
        this.papersModel.addAll(publications);
    }

    /**
     * Returns the current publication list, reflecting any imports and removals made in this panel.
     *
     * @return a copy of the current publication list
     */
    public List<Publication> getPublications() {
        final List<Publication> list = new ArrayList<>();
        for (int i = 0; i < this.papersModel.size(); i++) {
            list.add(this.papersModel.get(i));
        }
        return list;
    }

    /**
     * Registers a callback invoked with an author's h-index and citation count when their
     * papers are imported, so a containing form can pre-fill those fields too.
     *
     * @param callback receives (hIndex, citationCount) from the selected author
     */
    public void setOnAuthorMetadata(final BiConsumer<Integer, Integer> callback) {
        this.onAuthorMetadata = callback;
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

    private JLabel exampleLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setForeground(Theme.FG_SUBTLE);
        label.setFont(label.getFont().deriveFont(11f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private static Component strut() {
        return javax.swing.Box.createVerticalStrut(10);
    }

    private static void addAll(final JPanel panel, final Component... components) {
        for (final Component component : components) {
            panel.add(component);
        }
    }
}
