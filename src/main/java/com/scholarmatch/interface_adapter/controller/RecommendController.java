package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.recommend.RecommendInputBoundary;

/**
 * Controller for the recommendation use case.
 */
public final class RecommendController {

    private final RecommendInputBoundary recommendInteractor;

    /**
     * Constructs a RecommendController.
     *
     * @param recommendInteractor the recommendation use case
     */
    public RecommendController(final RecommendInputBoundary recommendInteractor) {
        this.recommendInteractor = recommendInteractor;
    }
}
