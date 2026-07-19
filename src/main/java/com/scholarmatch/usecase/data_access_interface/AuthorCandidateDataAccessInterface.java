package com.scholarmatch.usecase.data_access_interface;

import java.util.List;

/**
 * Data access interface for a candidate author returned by the Semantic Scholar name search.
 *
 * <p>Implemented by the framework-layer DTO that maps raw API response fields.
 */
public interface AuthorCandidateDataAccessInterface {

    /**
     * @return the Semantic Scholar author ID
     */
    String getAuthorId();

    /**
     * @return the author's display name
     */
    String getName();

    /**
     * @return the author's listed institutional affiliations
     */
    List<String> getAffiliations();

    /**
     * @return the number of papers indexed for this author, or null if unavailable
     */
    Integer getPaperCount();

    /**
     * @return the author's h-index, or null if Semantic Scholar doesn't report one
     */
    Integer getHIndex();

    /**
     * @return the author's total citation count, or null if Semantic Scholar
     *     doesn't report one
     */
    Integer getCitationCount();
}
