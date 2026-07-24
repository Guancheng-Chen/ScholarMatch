package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.PaperLookupController;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;
import com.scholarmatch.usecase.paper_lookup.PaperLookupInputBoundary;
import com.scholarmatch.usecase.paper_lookup.SearchAuthorsInputData;
import com.scholarmatch.usecase.paper_lookup.SelectAuthorInputData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

            assertFalse(candidates.isVisible());
            assertFalse(importButton.isVisible());
            assertFalse(papers.isVisible());
            assertFalse(removeButton.isVisible());

            authorField.setText("  Ada Lovelace  ");
            searchButton.doClick();
            importButton.doClick();
            removeButton.doClick();
            verify(boundary, never()).selectAuthor(org.mockito.ArgumentMatchers.any());

            viewModel.getAuthorCandidates().setAll(List.of(candidate));
            assertTrue(candidates.isVisible());
            assertTrue(importButton.isVisible());
            candidates.setSelectedIndex(0);
            importButton.doClick();
            assertEquals(3, hIndex.get());
            assertEquals(120, citations.get());

            viewModel.getAuthorPapersFound().setAll(List.of(paper));
            assertEquals(List.of(paper), panel.getPublications());
            assertTrue(papers.isVisible());
            assertTrue(removeButton.isVisible());

            panel.setPublications(List.of(paper));
            ((DefaultListModel<Publication>) papers.getModel()).set(0, paper);
            papers.setSelectedIndex(0);
            removeButton.doClick();
            assertTrue(panel.getPublications().isEmpty());
            assertFalse(papers.isVisible());
            assertFalse(removeButton.isVisible());

            viewModel.getAuthorCandidates().clear();
            assertFalse(candidates.isVisible());
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
