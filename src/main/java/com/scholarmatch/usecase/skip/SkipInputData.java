package com.scholarmatch.usecase.skip;

/**
 * Input data for the skip use case.
 *
 * <p>The skipped user's ID is carried for symmetry with every other card-decision use
 * case and in case a future variant needs it (e.g. per-user skip analytics), even
 * though today's interactor does nothing with it — skip has nothing to persist.
 */
public final class SkipInputData {

    private final String skippedUserId;

    /**
     * Constructs SkipInputData.
     *
     * @param skippedUserId the ID of the user who was skipped
     */
    public SkipInputData(final String skippedUserId) {
        this.skippedUserId = skippedUserId;
    }

    /**
     * @return the ID of the user who was skipped
     */
    public String getSkippedUserId() {
        return this.skippedUserId;
    }
}
