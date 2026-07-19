package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.AuthorCandidateDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Offline implementation of UserAPIGatewayInterface backed by a small, curated,
 * hardcoded dataset of well-known papers and authors.
 *
 * <p>This exists purely as a demo-day safety net for when the live Semantic User API is
 * unreachable (rate limited or no network) — see FallbackUserApiGateway. The dataset
 * is intentionally small and only meant to keep the paper-lookup feature demonstrable, not to
 * replace the real API.
 */
public final class LocalUserApiGateway implements UserAPIGatewayInterface {

    private static final List<Publication> PAPERS = List.of(
        new Publication("10.48550/arXiv.1706.03762", "Attention Is All You Need", 2017, 100000),
        new Publication("10.1109/CVPR.2016.90", "Deep Residual Learning for Image Recognition", 2016, 190000),
        new Publication("10.1038/nature14539", "Deep Learning", 2015, 50000),
        new Publication(
            "10.48550/arXiv.1810.04805",
            "BERT: Pre-training of Deep Bidirectional Transformers for Language Understanding",
            2018, 90000),
        new Publication(
            "10.1145/3065386", "ImageNet Classification with Deep Convolutional Neural Networks", 2012, 120000));

    private static final Map<String, List<Publication>> PAPERS_BY_AUTHOR_ID = Map.of(
        "hinton-local", List.of(PAPERS.get(2), PAPERS.get(4)),
        "vaswani-local", List.of(PAPERS.get(0)),
        "he-local", List.of(PAPERS.get(1)));

    private static final List<AuthorCandidateDataAccessInterface> AUTHORS = List.of(
        new AuthorCandidateDto(
                "hinton-local", "Geoffrey Hinton", List.of("University of Toronto"), 466, 135, 900000),
        new AuthorCandidateDto(
                "vaswani-local", "Ashish Vaswani", List.of("Google Brain"), 50, 45, 150000),
        new AuthorCandidateDto(
                "he-local", "Kaiming He", List.of("Meta AI"), 300, 105, 500000));

    @Override
    public List<AuthorCandidateDataAccessInterface> searchAuthors(final String name) {
        final String needle = name.toLowerCase(Locale.ROOT).trim();
        final List<AuthorCandidateDataAccessInterface> matches = new ArrayList<>();
        for (final AuthorCandidateDataAccessInterface author : AUTHORS) {
            if (author.getName().toLowerCase(Locale.ROOT).contains(needle)) {
                matches.add(author);
            }
        }
        return matches;
    }

    @Override
    public AuthorCandidateDataAccessInterface getAuthor(final String authorId) {
        for (final AuthorCandidateDataAccessInterface author : AUTHORS) {
            if (author.getAuthorId().equals(authorId)) {
                return author;
            }
        }
        throw new ResourceNotFoundException("No offline sample data available for author ID: " + authorId);
    }

    @Override
    public List<Publication> getAuthorPapers(final String authorId) {
        // Only 3 curated demo IDs have paper data here. Any other ID must have come from the
        // real API's search results (e.g. selected before the follow-up "import" call got rate
        // limited), which this offline dataset has no way to serve — so this must throw rather
        // than silently return an empty list, or the caller would misread "we have no local
        // data for this ID" as "this author genuinely has zero papers."
        if (!PAPERS_BY_AUTHOR_ID.containsKey(authorId)) {
            throw new ResourceNotFoundException("No offline sample data available for author ID: " + authorId);
        }
        return PAPERS_BY_AUTHOR_ID.get(authorId);
    }
}
