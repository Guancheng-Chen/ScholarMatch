package com.scholarmatch.usecase.data_access_interface;

/**
 * Data access interface for the Dislike use case.
 */
public interface DislikeDataAccessInterface {

    /**
     * Records a one-directional Dislike decision for the selected scholar.
     *
     * @param dislikedScholarId the ID of the scholar being disliked
     */
    void dislike(final String dislikedScholarId);
}
