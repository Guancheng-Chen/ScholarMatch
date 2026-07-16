package com.scholarmatch.usecase.data_access_interface;

/**
 * Data access interface for a candidate author returned by the Semantic User name search.
 *
 * <p>Implemented by the framework-layer DTO that maps raw API response fields.
 */
public interface AuthorCandidateDataAccessInterface {

    /**
     * @return the Semantic User author ID
     */
    String getAuthorId();

    /**
     * @return the author's display name
     */
    String getName();

    /**
     * @return the primary institutional affiliation, or empty string if unavailable
     */
    String getAffiliation();

    /**
     * @return the number of papers indexed for this author
     */
    int getPaperCount();

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
