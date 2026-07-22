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
    List<Posting> loadPostings(PostingScope scope);

    Map<String, List<PostingApplication>> loadApplicationsForOwnedPostings(
            PostingScope scope, List<Posting> postings);
}
