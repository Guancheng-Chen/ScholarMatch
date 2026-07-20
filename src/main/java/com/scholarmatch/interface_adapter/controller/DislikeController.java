package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.dislike.DislikeInputBoundary;
import com.scholarmatch.usecase.dislike.DislikeInputData;

/**
 * Controller that forwards dislike actions to the dislike use case.
 */
public final class DislikeController {

    private final DislikeInputBoundary interactor;

    /**
     * Constructs a DislikeController.
     *
     * @param interactor the dislike use case
     */
    public DislikeController(final DislikeInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the current user dislikes a user card.
     *
     * @param dislikedUserId the ID of the user shown on the card
     */
    public void dislike(final String dislikedUserId) {
        this.interactor.execute(new DislikeInputData(dislikedUserId));
    }
}
