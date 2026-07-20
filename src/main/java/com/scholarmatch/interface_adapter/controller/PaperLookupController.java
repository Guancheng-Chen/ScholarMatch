package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.paper_lookup.PaperLookupInputBoundary;
import com.scholarmatch.usecase.paper_lookup.SearchAuthorsInputData;

/**
 * Controller for the paper lookup use case.
 */
public final class PaperLookupController {

    private final PaperLookupInputBoundary paperLookupInteractor;

    /**
     * Constructs a PaperLookupController.
     *
     * @param paperLookupInteractor the paper lookup use case
     */
    public PaperLookupController(final PaperLookupInputBoundary paperLookupInteractor) {
        this.paperLookupInteractor = paperLookupInteractor;
    }

    /**
     * Searches for authors by name.
     *
     * @param name the author name
     */
    public void searchAuthors(final String name) {
        this.paperLookupInteractor.searchAuthors(new SearchAuthorsInputData(name));
    }
}
