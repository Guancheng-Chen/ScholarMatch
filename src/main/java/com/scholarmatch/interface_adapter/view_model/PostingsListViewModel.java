package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;

import java.util.List;
import java.util.Map;

/**
 * Minimal presentation contract shared by both posting-list screens.
 */
public interface PostingsListViewModel {
    void setPostings(List<PostingData> postings);

    void setApplicationsByPostingId(
            Map<String, List<PostingApplicationData>> applicationsByPostingId);

    void setErrorMessage(String message);
}
