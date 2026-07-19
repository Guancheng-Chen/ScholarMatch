package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.usecase.data_access_interface.AuthorCandidateDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Framework DTO for one author returned by Semantic Scholar.
 */
public final class AuthorCandidateDto implements AuthorCandidateDataAccessInterface {

    private final String authorId;
    private final String name;
    private final List<String> affiliations;
    private final Integer paperCount;
    private final Integer hIndex;
    private final Integer citationCount;

    /**
     * Constructs an author candidate DTO.
     *
     * @param authorId the Semantic Scholar author ID
     * @param name the author's display name
     * @param affiliations the author's listed affiliations
     * @param paperCount the number of papers, or null when unknown
     * @param hIndex the author's h-index, or null when unknown
     * @param citationCount the author's citation count, or null when unknown
     */
    public AuthorCandidateDto(
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

    @Override
    public String getAuthorId() {
        return this.authorId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> getAffiliations() {
        return new ArrayList<>(this.affiliations);
    }

    @Override
    public Integer getPaperCount() {
        return this.paperCount;
    }

    @Override
    public Integer getHIndex() {
        return this.hIndex;
    }

    @Override
    public Integer getCitationCount() {
        return this.citationCount;
    }
}
