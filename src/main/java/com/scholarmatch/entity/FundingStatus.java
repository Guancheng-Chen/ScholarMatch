package com.scholarmatch.entity;

/**
 * Describes how a User's research work is currently financially supported.
 *
 * <p>Shown on the user's profile so a prospective collaborator can gauge whether resources
 * (e.g. funding for joint travel, equipment, or a shared grant application) are already
 * available before reaching out.
 */
public enum FundingStatus {

    /**
     * Funds their own research out of pocket, with no institutional or external support.
     */
    SELF_FUNDED,

    /**
     * Supported by internal funding from their university or research institution.
     */
    INSTITUTIONAL_FUNDING,

    /**
     * Supported by a government or national research council grant
     * (e.g. NSF, NSERC, NSFC, NIH).
     */
    GOVERNMENT_GRANT,

    /**
     * Supported by industry sponsorship or a corporate research partnership.
     */
    INDUSTRY_SPONSORED,

    /**
     * Supported by a scholarship or fellowship award.
     */
    SCHOLARSHIP_FELLOWSHIP,

    /**
     * Currently has no funding available for their research.
     */
    UNFUNDED,

    /**
     * A funding arrangement that doesn't fit any of the other categories.
     */
    OTHER,
}
