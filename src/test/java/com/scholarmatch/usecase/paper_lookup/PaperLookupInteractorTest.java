package com.scholarmatch.usecase.paper_lookup;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.frameworks.data_access_object.AuthorCandidateDto;
import com.scholarmatch.usecase.data_access_interface.AuthorCandidateDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
    void ranksMatchingNamesByCitationsAndReturnsTwentyCandidates() {
        final List<AuthorCandidateDataAccessInterface> candidates = new ArrayList<>(IntStream.range(0, 20)
                .mapToObj(index -> new AuthorCandidateDto(
                        "other-" + index,
                        "F. Li",
                        List.of(),
                        10,
                        3,
                        100))
                .toList());
        candidates.add(new AuthorCandidateDto(
                "split-profile",
                "Fei-Fei Li",
                List.of(),
                8,
                7,
                1000));
        candidates.add(new AuthorCandidateDto(
                "48004138",
                "Li Fei-Fei",
                List.of(),
                607,
                143,
                247196));
        this.gateway.authorCandidates = candidates;

        this.interactor.searchAuthors(new SearchAuthorsInputData("Fei Fei Li"));

        assertEquals(20, this.presenter.candidates.size());
        assertEquals("48004138", this.presenter.candidates.getFirst().getAuthorId());
        assertEquals("split-profile", this.presenter.candidates.get(1).getAuthorId());
    }

    @Test
    void searchesBySemanticScholarAuthorId() {
        this.interactor.searchAuthors(new SearchAuthorsInputData("1695689"));

        assertEquals("1695689", this.gateway.lastAuthorId);
        assertEquals("1695689", this.presenter.candidates.getFirst().getAuthorId());
    }

    @Test
    void clearsPreviousCandidatesWhenANewSearchFailsValidation() {
        this.interactor.searchAuthors(new SearchAuthorsInputData("Zhijie Yuan"));
        this.interactor.searchAuthors(new SearchAuthorsInputData("  "));
        this.interactor.selectAuthor(new SelectAuthorInputData("2112339906"));

        assertNull(this.presenter.publications);
        assertEquals("Select an author from the current search results.", this.presenter.errorMessage);
    }

    private static final class FakeGateway implements UserAPIGatewayInterface {

        private String lastQuery;
        private String lastAuthorId;
        private List<AuthorCandidateDataAccessInterface> authorCandidates = List.of(new AuthorCandidateDto(
                "2112339906",
                "Zhijie Yuan",
                List.of(),
                6,
                2,
                14));

        @Override
        public List<AuthorCandidateDataAccessInterface> searchAuthors(final String authorName) {
            this.lastQuery = authorName;
            return this.authorCandidates;
        }

        @Override
        public AuthorCandidateDataAccessInterface getAuthor(final String authorId) {
            this.lastAuthorId = authorId;
            return new AuthorCandidateDto(
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
        private List<Publication> publications;
        private String errorMessage;

        @Override
        public void prepareAuthorCandidates(final List<AuthorCandidateData> authorCandidates) {
            this.candidates = authorCandidates;
        }

        @Override
        public void prepareAuthorPapersFound(final List<Publication> importedPublications) {
            this.publications = importedPublications;
        }

        @Override
        public void prepareError(final String message) {
            this.errorMessage = message;
        }
    }
}
