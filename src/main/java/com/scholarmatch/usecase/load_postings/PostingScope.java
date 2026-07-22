package com.scholarmatch.usecase.load_postings;

/**
 * Available scopes for loading postings.
 */
public enum PostingScope {

    /**
     * All active postings available to users.
     */
    ALL_ACTIVE,

    /**
     * Postings owned by the current user.
     */
    MINE
}
