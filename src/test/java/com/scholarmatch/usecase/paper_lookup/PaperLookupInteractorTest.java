package com.scholarmatch.usecase.paper_lookup;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaperLookupInteractorTest {

    private FakeGateway gateway;
    private RecordingPresenter presenter;
    private PaperLookupInteractor interactor;

    @BeforeEach
    void setUp() {
        this.gateway = new FakeGateway();
        this.presenter = new RecordingPresenter();
        this.interactor = new PaperLookupInteractor(this.gateway, this.presenter);
    }

    @Test
    void searchesThenImportsSelectedAuthor() {
        this.interactor.searchAuthors(new SearchAuthorsInputData("Zhijie Yuan"));
        this.interactor.selectAuthor(new SelectAuthorInputData("2112339906"));

        assertEquals("Zhijie Yuan", this.gateway.lastQuery);
        assertEquals(1, this.presenter.candidates.size());
        assertEquals("Zhijie Yuan", this.presenter.selectedAuthor.getName());
        assertEquals("A Verification Paper", this.presenter.publications.getFirst().getTitle());
        assertNull(this.presenter.errorMessage);
    }

    @Test
    void rejectsBlankSearchWithoutCallingGateway() {
        this.interactor.searchAuthors(new SearchAuthorsInputData("  "));

        assertNull(this.gateway.lastQuery);
        assertEquals("Enter an author name before searching.", this.presenter.errorMessage);
    }

    @Test
    void rejectsAuthorOutsideCurrentResults() {
        this.interactor.selectAuthor(new SelectAuthorInputData("unknown"));

        assertEquals("Select an author from the current search results.", this.presenter.errorMessage);
    }

    @Test
    void normalizesAuthorNameBeforeSearching() {
        this.interactor.searchAuthors(new SearchAuthorsInputData("  Fei-Fei   Li  "));

        assertEquals("Fei Fei Li", this.gateway.lastQuery);
    }

    @Test
    void clearsPreviousCandidatesWhenANewSearchFailsValidation() {
        this.interactor.searchAuthors(new SearchAuthorsInputData("Zhijie Yuan"));
        this.interactor.searchAuthors(new SearchAuthorsInputData("  "));
        this.interactor.selectAuthor(new SelectAuthorInputData("2112339906"));

        assertNull(this.presenter.selectedAuthor);
        assertEquals("Select an author from the current search results.", this.presenter.errorMessage);
    }

    private static final class FakeGateway implements UserAPIGatewayInterface {

        private String lastQuery;

        @Override
        public List<AuthorCandidateData> searchAuthors(final String authorName) {
            this.lastQuery = authorName;
            return List.of(new AuthorCandidateData(
                    "2112339906",
                    "Zhijie Yuan",
                    List.of(),
                    6,
                    2,
                    14));
        }

        @Override
        public AuthorCandidateData getAuthor(final String authorId) {
            return new AuthorCandidateData(
                    authorId,
                    "Zhijie Yuan",
                    List.of(),
                    6,
                    2,
                    14);
        }

        @Override
        public List<Publication> getAuthorPapers(final String authorId) {
            return List.of(new Publication(
                    "10.0000/verification",
                    "A Verification Paper",
                    2021,
                    6));
        }
    }

    private static final class RecordingPresenter implements PaperLookupOutputBoundary {

        private List<AuthorCandidateData> candidates;
        private AuthorCandidateData selectedAuthor;
        private List<Publication> publications;
        private String errorMessage;

        @Override
        public void prepareAuthorCandidatesView(final List<AuthorCandidateData> authorCandidates) {
            this.candidates = authorCandidates;
        }

        @Override
        public void prepareAuthorImportView(
                final AuthorCandidateData author,
                final List<Publication> importedPublications) {
            this.selectedAuthor = author;
            this.publications = importedPublications;
        }

        @Override
        public void prepareFailView(final String message) {
            this.errorMessage = message;
        }
    }
}
