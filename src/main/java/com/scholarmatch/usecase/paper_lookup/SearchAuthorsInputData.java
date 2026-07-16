package com.scholarmatch.usecase.paper_lookup;

/**
 * Input data for searching authors by name.
 */
public final class SearchAuthorsInputData {

    private final String authorName;

    /**
     * Constructs an author search query.
     *
     * @param authorName the author name entered by the user
     */
    public SearchAuthorsInputData(final String authorName) {
        this.authorName = authorName;
    }

    /** @return the author name entered by the user */
    public String getAuthorName() {
        return this.authorName;
    }
}
