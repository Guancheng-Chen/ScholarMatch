package com.scholarmatch.usecase.paper_lookup;

/**
 * Input data for selecting one author from search results.
 */
public final class SelectAuthorInputData {

    private final String authorId;

    /**
     * Constructs selected-author input.
     *
     * @param authorId the Semantic Scholar author ID
     */
    public SelectAuthorInputData(final String authorId) {
        this.authorId = authorId;
    }

    /** @return the Semantic Scholar author ID */
    public String getAuthorId() {
        return this.authorId;
    }
}
