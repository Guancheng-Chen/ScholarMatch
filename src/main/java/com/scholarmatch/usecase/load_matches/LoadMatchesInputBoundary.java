package com.scholarmatch.usecase.load_matches;

/**
 * Input port (boundary) for the load-confirmed-matches use case.
 */
public interface LoadMatchesInputBoundary {

    /**
     * Executes the use case for the currently authenticated user.
     */
    void execute();
}
