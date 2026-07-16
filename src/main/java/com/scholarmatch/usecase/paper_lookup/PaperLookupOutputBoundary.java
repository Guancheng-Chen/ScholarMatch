package com.scholarmatch.usecase.paper_lookup;

import com.scholarmatch.entity.Publication;

import java.util.List;

/**
 * Output boundary for author search and paper import results.
 */
public interface PaperLookupOutputBoundary {

    /**
     * Presents matching author candidates after an author search.
     *
     * @param candidates the matching authors
     */
    void prepareAuthorCandidatesView(List<AuthorCandidateData> candidates);

    /**
     * Presents the author and papers selected for profile import.
     *
     * @param author       the selected author
     * @param publications the selected author's papers
     */
    void prepareAuthorImportView(AuthorCandidateData author, List<Publication> publications);

    /**
     * Presents a paper lookup failure.
     *
     * @param errorMessage the reason the lookup could not be completed
     */
    void prepareFailView(String errorMessage);
}
