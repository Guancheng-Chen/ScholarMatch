package com.scholarmatch.usecase.recommend;

/**
 * Input port (boundary) for the get-recommendations use case.
 */
public interface RecommendInputBoundary {
    /**
     * Executes the recommend use case for the currently authenticated user.
     */
    void execute();
}
