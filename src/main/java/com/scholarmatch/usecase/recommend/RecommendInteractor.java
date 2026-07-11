package com.scholarmatch.usecase.recommend;

/**
 * Interactor implementing the user recommendation use case.
 *
 * <p>Fetches a ranked list of similar users from the server. The server
 * handles embedding and pgvector similarity search; the client only manages
 * what to display (caching the card stack in
 * com.scholarmatch.interface_adapter.view_model.RecommendViewModel).
 *
 * <p>Before fetching recommendations, checks that the current user's own profile
 * is complete (User#isProfileComplete()) — an incomplete profile (e.g. a
 * blank research description) produces inaccurate matches, so recommendations are
 * withheld until every core field is filled in.
 */
public class RecommendInteractor {
}
