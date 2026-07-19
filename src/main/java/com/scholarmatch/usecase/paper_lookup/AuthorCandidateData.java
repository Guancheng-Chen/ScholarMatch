package com.scholarmatch.usecase.paper_lookup;

import com.scholarmatch.usecase.data_access_interface.AuthorCandidateDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Read-only author summary shown for one Semantic Scholar search result.
 *
 * <p>Metrics use boxed integers because Semantic Scholar may omit them or return null.
 */
public final class AuthorCandidateData {

    private final String authorId;
    private final String name;
    private final List<String> affiliations;
    private final Integer paperCount;
    private final Integer hIndex;
    private final Integer citationCount;

    /**
     * Constructs an author candidate.
     *
     * @param authorId      the Semantic Scholar author ID
     * @param name          the author's display name
     * @param affiliations  the author's listed affiliations
     * @param paperCount    the number of papers, or null when unknown
     * @param hIndex        the author's h-index, or null when unknown
     * @param citationCount the author's citation count, or null when unknown
     */
    public AuthorCandidateData(
            final String authorId,
            final String name,
            final List<String> affiliations,
            final Integer paperCount,
            final Integer hIndex,
            final Integer citationCount) {
        this.authorId = authorId;
        this.name = name;
        this.affiliations = new ArrayList<>(affiliations);
        this.paperCount = paperCount;
        this.hIndex = hIndex;
        this.citationCount = citationCount;
    }

    /**
     * Creates use-case output data from an author returned by the data gateway.
     *
     * @param candidate the gateway author data
     * @return author data suitable for the paper lookup output boundary
     */
    public static AuthorCandidateData from(final AuthorCandidateDataAccessInterface candidate) {
        return new AuthorCandidateData(
                candidate.getAuthorId(),
                candidate.getName(),
                candidate.getAffiliations(),
                candidate.getPaperCount(),
                candidate.getHIndex(),
                candidate.getCitationCount());
    }

    /** @return the Semantic Scholar author ID */
    public String getAuthorId() {
        return this.authorId;
    }

    /** @return the author's display name */
    public String getName() {
        return this.name;
    }

    /** @return a copy of the author's listed affiliations */
    public List<String> getAffiliations() {
        return new ArrayList<>(this.affiliations);
    }

    /** @return the paper count, or null when unknown */
    public Integer getPaperCount() {
        return this.paperCount;
    }

    /** @return the h-index, or null when unknown */
    public Integer getHIndex() {
        return this.hIndex;
    }

    /** @return the citation count, or null when unknown */
    public Integer getCitationCount() {
        return this.citationCount;
    }
}
