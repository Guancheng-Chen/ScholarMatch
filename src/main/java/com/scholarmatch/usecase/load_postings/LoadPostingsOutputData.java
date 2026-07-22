package com.scholarmatch.usecase.load_postings;

import com.scholarmatch.usecase.dto.PostingData;

import java.util.List;
import java.util.Map;
import com.scholarmatch.usecase.dto.PostingApplicationData;

public record LoadPostingsOutputData(
        List<PostingData> postings,
        Map<String, List<PostingApplicationData>> applicationsByPostingId) {
    public LoadPostingsOutputData {
        postings = List.copyOf(postings);
        applicationsByPostingId = Map.copyOf(applicationsByPostingId);
    }
}
