package com.scholarmatch.usecase.load_postings;

import com.scholarmatch.usecase.dto.PostingData;

import java.util.List;
import java.util.Map;
import com.scholarmatch.usecase.dto.PostingApplicationData;

/**
 * Output data containing postings and their application details.
 *
 * @param postings the loaded postings
 * @param applicationsByPostingId applications grouped by posting identifier
 */
public record LoadPostingsOutputData(
        List<PostingData> postings,
        Map<String, List<PostingApplicationData>> applicationsByPostingId) {

    /**
     * Creates immutable copies of the output collections.
     */
    public LoadPostingsOutputData {
        postings = List.copyOf(postings);
        applicationsByPostingId = Map.copyOf(applicationsByPostingId);
    }
}
