package com.scholarmatch.entity;

/**
 * Describes what kind of academic connection a User is looking for on ScholarMatch.
 *
 * <p>Stored on the user's profile (not on individual requests) and displayed
 * on their card so other users can immediately understand their intent —
 * analogous to relationship-type preferences on social matching platforms.
 */
public enum CollaborationType {

    /**
     * Looking for a co-author to jointly write a paper or book chapter.
     */
    CO_AUTHOR,

    /**
     * Looking to join or form a shared research group or lab.
     */
    RESEARCH_GROUP,

    /**
     * Looking for someone to exchange peer review on drafts or grant proposals.
     */
    PEER_REVIEW,

    /**
     * Looking for a mentor, or willing to mentor a junior user.
     */
    MENTORSHIP,

    /**
     * Casually looking for like-minded users with overlapping research interests,
     * no specific collaboration goal.
     */
    INTEREST_SHARING,
}
