package com.scholarmatch.usecase.dislike;

/**
 * Input data for the dislike use case.
 */
public final class DislikeInputData {

    private final String dislikedUserId;

    /**
     * Constructs DislikeInputData.
     *
     * @param dislikedUserId the ID of the user who was disliked
     */
    public DislikeInputData(final String dislikedUserId) {
        this.dislikedUserId = dislikedUserId;
    }

    /**
     * @return the ID of the user who was disliked
     */
    public String getDislikedUserId() {
        return this.dislikedUserId;
    }
}
