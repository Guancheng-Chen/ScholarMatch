package com.scholarmatch.usecase.paper_lookup;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Coordinates the two-step author search and paper import flow.
 */
public final class PaperLookupInteractor implements PaperLookupInputBoundary {

    private static final String EMPTY_QUERY_MESSAGE = "Enter an author name before searching.";
    private static final String UNKNOWN_AUTHOR_MESSAGE = "Select an author from the current search results.";
    private static final int MAX_AUTHOR_CANDIDATES = 20;

    private final UserAPIGatewayInterface userApiGateway;
    private final PaperLookupOutputBoundary outputBoundary;
    private final Map<String, AuthorCandidateData> candidatesById = new HashMap<>();

    /**
     * Constructs a paper lookup interactor.
     *
     * @param userApiGateway the external scholarly-data port
     * @param outputBoundary the presenter boundary
     */
    public PaperLookupInteractor(
            final UserAPIGatewayInterface userApiGateway,
            final PaperLookupOutputBoundary outputBoundary) {
        this.userApiGateway = userApiGateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void searchAuthors(final SearchAuthorsInputData inputData) {
        this.candidatesById.clear();
        if (inputData.getAuthorName() == null || inputData.getAuthorName().isBlank()) {
            this.outputBoundary.prepareFailView(EMPTY_QUERY_MESSAGE);
            return;
        }

        try {
            final String normalizedName = normalizeName(inputData.getAuthorName());
            final List<AuthorCandidateData> candidates = rankCandidates(
                    this.userApiGateway.searchAuthors(normalizedName),
                    normalizedName);
            for (final AuthorCandidateData candidate : candidates) {
                this.candidatesById.put(candidate.getAuthorId(), candidate);
            }
            this.outputBoundary.prepareAuthorCandidatesView(candidates);
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }

    @Override
    public void selectAuthor(final SelectAuthorInputData inputData) {
        final AuthorCandidateData selectedAuthor = this.candidatesById.get(inputData.getAuthorId());
        if (selectedAuthor == null) {
            this.outputBoundary.prepareFailView(UNKNOWN_AUTHOR_MESSAGE);
            return;
        }

        try {
            final List<Publication> publications = List.copyOf(
                    this.userApiGateway.getAuthorPapers(inputData.getAuthorId()));
            this.outputBoundary.prepareAuthorImportView(selectedAuthor, publications);
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }

    private static List<AuthorCandidateData> rankCandidates(
            final List<AuthorCandidateData> candidates,
            final String query) {
        final List<String> queryTokens = sortedNameTokens(query);
        return candidates.stream()
                .sorted((first, second) -> compareCandidates(first, second, queryTokens))
                .limit(MAX_AUTHOR_CANDIDATES)
                .toList();
    }

    private static int compareCandidates(
            final AuthorCandidateData first,
            final AuthorCandidateData second,
            final List<String> queryTokens) {
        final boolean firstNameMatches = sortedNameTokens(first.getName()).equals(queryTokens);
        final boolean secondNameMatches = sortedNameTokens(second.getName()).equals(queryTokens);
        if (firstNameMatches != secondNameMatches) {
            return firstNameMatches ? -1 : 1;
        }
        if (firstNameMatches) {
            return Integer.compare(citationCount(second), citationCount(first));
        }
        return 0;
    }

    private static int citationCount(final AuthorCandidateData candidate) {
        return candidate.getCitationCount() == null ? 0 : candidate.getCitationCount();
    }

    private static List<String> sortedNameTokens(final String name) {
        return Arrays.stream(normalizeName(name).toLowerCase(Locale.ROOT).split(" "))
                .sorted()
                .toList();
    }

    private static String normalizeName(final String name) {
        return name.trim().replace('-', ' ').replaceAll("\\s+", " ");
    }
}
