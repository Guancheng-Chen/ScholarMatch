package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.paper_lookup.PaperLookupController;
import com.scholarmatch.interface_adapter.view_model.paper_lookup.PaperLookupViewModel;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;
import com.scholarmatch.usecase.paper_lookup.PaperLookupInputBoundary;
import com.scholarmatch.usecase.paper_lookup.SearchAuthorsInputData;
import com.scholarmatch.usecase.paper_lookup.SelectAuthorInputData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PublicationEditorPanelTest {

    @Test
    void testSearchSelectionImportRemovalAndViewModelRendering() throws Exception {
        final PaperLookupInputBoundary boundary = mock(PaperLookupInputBoundary.class);
        final PaperLookupViewModel viewModel = new PaperLookupViewModel();
        final AtomicReference<Integer> hIndex = new AtomicReference<>();
        final AtomicReference<Integer> citations = new AtomicReference<>();
        final AuthorCandidateData candidate =
                new AuthorCandidateData("author-1", "Ada", List.of("Cambridge"), 4, 3, 120);
        final Publication paper = new Publication("10.1/test", "Analytical Engines", 1843, 12);

        SwingUtilities.invokeAndWait(() -> {
            final PublicationEditorPanel panel = new PublicationEditorPanel(
                    new PaperLookupController(boundary), viewModel, 500);
            panel.setOnAuthorMetadata((h, c) -> {
                hIndex.set(h);
                citations.set(c);
            });
            final JTextField authorField = SwingTestSupport.find(panel, JTextField.class, 0);
            final JButton searchButton = button(panel, "Search Author");
            final JButton importButton = button(panel, "Import This Author's Papers");
            final JButton removeButton = button(panel, "Remove Selected Paper");
            final List<JList> lists = SwingTestSupport.findAll(panel, JList.class);
            final JList<AuthorCandidateData> candidates = lists.get(0);
            final JList<Publication> papers = lists.get(1);
            final List<JScrollPane> scrollPanes = SwingTestSupport.findAll(panel, JScrollPane.class);
            final JScrollPane candidateScrollPane = scrollPanes.get(0);
            final JScrollPane papersScrollPane = scrollPanes.get(1);

            assertFalse(candidateScrollPane.isVisible());
            assertSame(candidates, candidateScrollPane.getViewport().getView());
            assertEquals(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    candidateScrollPane.getVerticalScrollBarPolicy());
            assertFalse(importButton.isVisible());
            assertFalse(papersScrollPane.isVisible());
            assertSame(papers, papersScrollPane.getViewport().getView());
            assertEquals(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    papersScrollPane.getVerticalScrollBarPolicy());
            assertFalse(removeButton.isVisible());

            authorField.setText("  Ada Lovelace  ");
            searchButton.doClick();
            importButton.doClick();
            removeButton.doClick();
            verify(boundary, never()).selectAuthor(org.mockito.ArgumentMatchers.any());

            viewModel.getAuthorCandidates().setAll(List.of(candidate));
            assertTrue(candidateScrollPane.isVisible());
            assertTrue(importButton.isVisible());
            candidates.setSelectedIndex(0);
            importButton.doClick();
            assertEquals(3, hIndex.get());
            assertEquals(120, citations.get());

            viewModel.getAuthorPapersFound().setAll(List.of(paper));
            assertEquals(List.of(paper), panel.getPublications());
            assertTrue(papersScrollPane.isVisible());
            assertTrue(removeButton.isVisible());

            panel.setPublications(List.of(paper));
            ((DefaultListModel<Publication>) papers.getModel()).set(0, paper);
            papers.setSelectedIndex(0);
            removeButton.doClick();
            assertTrue(panel.getPublications().isEmpty());
            assertFalse(papersScrollPane.isVisible());
            assertFalse(removeButton.isVisible());

            viewModel.getAuthorCandidates().clear();
            assertFalse(candidateScrollPane.isVisible());
            assertFalse(importButton.isVisible());

            viewModel.setStatusMessage("Found author");
            assertTrue(SwingTestSupport.findAll(panel, JLabel.class).stream()
                    .anyMatch(label -> "Found author".equals(label.getText())));

            assertRendererText(candidates, candidate, "Ada — [Cambridge] (4 papers)");
            assertRendererText(candidates, "not a candidate", "not a candidate");
            assertRendererText(papers, paper, "Analytical Engines (1843)");
            assertRendererText(papers, "not a paper", "not a paper");
        });

        final ArgumentCaptor<SearchAuthorsInputData> search =
                ArgumentCaptor.forClass(SearchAuthorsInputData.class);
        verify(boundary).searchAuthors(search.capture());
        assertEquals("  Ada Lovelace  ", search.getValue().getAuthorName());
        final ArgumentCaptor<SelectAuthorInputData> select =
                ArgumentCaptor.forClass(SelectAuthorInputData.class);
        verify(boundary).selectAuthor(select.capture());
        assertEquals("author-1", select.getValue().getAuthorId());
    }

    @Test
    void testDefaultWidthConstructorAndDefaultMetadataCallback() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final PaperLookupViewModel viewModel = new PaperLookupViewModel();
            final PublicationEditorPanel panel = new PublicationEditorPanel(
                    new PaperLookupController(mock(PaperLookupInputBoundary.class)), viewModel);
            final AuthorCandidateData candidate =
                    new AuthorCandidateData("id", "Name", List.of(), null, null, null);
            viewModel.getAuthorCandidates().setAll(List.of(candidate));
            final JList<?> list = SwingTestSupport.find(panel, JList.class, 0);
            list.setSelectedIndex(0);
            button(panel, "Import This Author's Papers").doClick();
            assertEquals(460, panel.getMaximumSize().width);
        });
    }

    @Test
    void testImportTruncatesToMaxPublicationsLimit() throws Exception {
        final PaperLookupViewModel viewModel = new PaperLookupViewModel();
        SwingUtilities.invokeAndWait(() -> {
            final PublicationEditorPanel panel = new PublicationEditorPanel(
                    new PaperLookupController(mock(PaperLookupInputBoundary.class)), viewModel, 500);

            final List<Publication> sixPapers = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                sixPapers.add(new Publication("10.1/doi-" + i, "Paper " + i, 2020, 1));
            }
            viewModel.getAuthorPapersFound().setAll(sixPapers);

            assertEquals(5, panel.getPublications().size());
            assertTrue(SwingTestSupport.findAll(panel, JLabel.class).stream()
                    .anyMatch(label -> label.getText() != null
                            && label.getText().contains("Only added 5 of 6 papers")));

            // Importing again while already at the cap adds nothing more and shows the cap message.
            viewModel.getAuthorPapersFound().setAll(
                    List.of(new Publication("10.1/doi-extra", "Extra", 2020, 1)));
            assertEquals(5, panel.getPublications().size());
            assertTrue(SwingTestSupport.findAll(panel, JLabel.class).stream()
                    .anyMatch(label -> label.getText() != null
                            && label.getText().contains("Already at the 5-publication limit")));
        });
    }

    private static JButton button(final PublicationEditorPanel panel, final String text) {
        return SwingTestSupport.findAll(panel, JButton.class).stream()
                .filter(button -> text.equals(button.getText()))
                .findFirst()
                .orElseThrow();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void assertRendererText(final JList list, final Object value, final String expected) {
        final ListCellRenderer renderer = list.getCellRenderer();
        final Component rendered = renderer.getListCellRendererComponent(list, value, 0, false, false);
        assertEquals(expected, ((JLabel) rendered).getText());
    }
}
