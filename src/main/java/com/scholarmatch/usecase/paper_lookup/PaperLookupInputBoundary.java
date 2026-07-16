package com.scholarmatch.usecase.paper_lookup;

/**
 * Input boundary for searching and selecting Semantic Scholar authors.
 */
public interface PaperLookupInputBoundary {

    /**
     * Searches for authors whose names match the supplied query.
     *
     * @param inputData the author-name query
     */
    void searchAuthors(SearchAuthorsInputData inputData);

    /**
     * Selects one author and imports that author's publication data.
     *
     * @param inputData the selected Semantic Scholar author ID
     */
    void selectAuthor(SelectAuthorInputData inputData);
}
