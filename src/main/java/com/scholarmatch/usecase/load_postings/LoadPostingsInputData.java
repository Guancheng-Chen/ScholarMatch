package com.scholarmatch.usecase.load_postings;

/**
 * Input data for loading postings.
 *
 * @param scope the requested posting scope
 */
public record LoadPostingsInputData(PostingScope scope) {
}
