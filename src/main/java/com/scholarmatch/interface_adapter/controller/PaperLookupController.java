package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.paper_lookup.PaperLookupInputBoundary;
import com.scholarmatch.usecase.paper_lookup.SearchAuthorsInputData;
import com.scholarmatch.usecase.paper_lookup.SelectAuthorInputData;

/**
 * Controller for the paper/author lookup use case.
 *
 * <p>Forwards search requests from the registration and profile-editing views
 * to the PaperLookupInputBoundary. Controllers in CA never call
 * presenters directly.
 */
public final class PaperLookupController {

    private final PaperLookupInputBoundary paperLookupInteractor;

    /**
     * Constructs a PaperLookupController.
     *
     * @param paperLookupInteractor the input boundary to invoke
     */
    public PaperLookupController(final PaperLookupInputBoundary paperLookupInteractor) {
        this.paperLookupInteractor = paperLookupInteractor;
    }

    /**
     * Searches for author candidates by name.
     *
     * @param name the author name to search for
     */
    public void searchAuthors(final String name) {
        this.paperLookupInteractor.searchAuthors(new SearchAuthorsInputData(name));
    }

    /**
     * Fetches all DOI-bearing papers for the selected author.
     *
     * @param authorId the Semantic User author ID of the selected candidate
     */
    public void selectAuthor(final String authorId) {
        this.paperLookupInteractor.selectAuthor(new SelectAuthorInputData(authorId));
    }
}
