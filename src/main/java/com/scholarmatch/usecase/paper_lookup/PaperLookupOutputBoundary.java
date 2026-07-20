package com.scholarmatch.usecase.paper_lookup;

import com.scholarmatch.entity.Publication;

import java.util.List;

/**
 * Output boundary receiving the results of paper and author lookups.
 */
public interface PaperLookupOutputBoundary {

    /**
     * Reports the author candidates found for a name search.
     *
     * @param candidates up to five matching authors; empty if none found
     */
    void prepareAuthorCandidates(List<AuthorCandidateData> candidates);

    /**
     * Reports the papers bulk-imported for a selected author.
     *
     * @param papers the author's papers; DOI is empty for any Semantic User didn't report one for
     */
    void prepareAuthorPapersFound(List<Publication> papers);

    /**
     * Reports that a lookup failed due to a network or server error.
     *
     * @param message a human-readable description of the failure
     */
    void prepareError(String message);
}
