package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;

import java.util.List;

/**
 * Port used by paper lookup to search authors and retrieve their publications.
 */
public interface UserAPIGatewayInterface {

    /**
     * Searches the external scholarly data source for matching authors.
     *
     * @param authorName the author name query
     * @return matching author candidates
     */
    List<AuthorCandidateData> searchAuthors(String authorName);

    /**
     * Retrieves one author by Semantic Scholar author ID.
     *
     * @param authorId the Semantic Scholar author ID
     * @return the matching author
     */
    AuthorCandidateData getAuthor(String authorId);

    /**
     * Retrieves publications for one external author ID.
     *
     * @param authorId the external author ID
     * @return the author's publications
     */
    List<Publication> getAuthorPapers(String authorId);
}
