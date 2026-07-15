package com.scholarmatch.usecase.skip;

/**
 * Input data for the skip use case.
 */
public final class SkipInputData {

    private final String candidateUserId;

    /**
     * Constructs skip input data.
     *
     * @param candidateUserId the ID of the candidate being skipped
     */
    public SkipInputData(final String candidateUserId) {
        this.candidateUserId = candidateUserId;
    }

    /**
     * Returns the skipped candidate's ID.
     *
     * @return the candidate ID
     */
    public String getCandidateUserId() {
        return candidateUserId;
    }
}
