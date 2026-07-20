package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.recommend.RecommendInputBoundary;

/**
 * Controller for the user-recommendation use case.
 */
public final class RecommendController {

    private final RecommendInputBoundary recommendInteractor;

    /**
     * Constructs a RecommendController.
     *
     * @param recommendInteractor the input boundary to invoke
     */
    public RecommendController(final RecommendInputBoundary recommendInteractor) {
        this.recommendInteractor = recommendInteractor;
    }

    /**
     * Requests a fresh recommendation list for the currently authenticated user.
     */
    public void execute() {
        this.recommendInteractor.execute();
    }
}
