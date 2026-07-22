package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;

import java.util.List;
import java.util.Map;
import com.scholarmatch.usecase.load_postings.PostingScope;

/**
 * Query boundary for posting lists and owner-only applicant details.
 */
public interface LoadPostingsDataAccessInterface {

    /**
     * Loads postings for the requested scope.
     *
     * @param scope the posting scope to load
     * @return the postings matching the requested scope
     */
    List<Posting> loadPostings(PostingScope scope);

    /**
     * Loads applications for postings owned by the current user.
     *
     * @param scope the posting scope
     * @param postings the postings whose applications should be loaded
     * @return applications grouped by posting identifier
     */
    Map<String, List<PostingApplication>> loadApplicationsForOwnedPostings(
            PostingScope scope, List<Posting> postings);
}
