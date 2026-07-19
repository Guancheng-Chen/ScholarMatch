package com.scholarmatch.usecase.data_access_interface;

/**
 * Port through which use cases and gateways read the identity of the currently
 * authenticated user.
 *
 * <p>Kept separate from SessionWriterInterface (write) and
 * SessionClearerInterface (clear) — a reader like ServerRepository (which
 * attaches the current user's token to every authenticated request) has no reason to depend
 * on session-writing or session-clearing capability just because a single session object
 * happens to implement all three.
 *
 * <p>Defined in the use-case layer so that interactors depend only on this abstraction.
 * The concrete implementation (CurrentUserProvider) lives in the outer framework
 * layer and is injected at startup via the composition root.
 */
public interface CurrentUserProviderInterface {

    /**
     * Returns the ID of the currently authenticated user.
     *
     * @return the current user's ID
     * @throws IllegalStateException if no user is authenticated
     */
    String getCurrentUserId();

    /**
     * Returns the JWT token for the current session, or null if not authenticated.
     *
     * @return the Bearer token
     */
    String getToken();
}
