package com.scholarmatch.usecase.data_access_interface;

/**
 * Port through which a successful login or registration establishes the session.
 *
 * <p>Kept separate from CurrentUserProviderInterface (read) and
 * SessionClearerInterface — the login and register use cases only ever write
 * the session, they never read it back or clear it, so they have no reason to depend on
 * those capabilities just because a single session object happens to implement all three.
 */
public interface SessionWriterInterface {

    /**
     * Records the user who has just authenticated.
     *
     * @param userId the authenticated user's ID
     */
    void setCurrentUserId(String userId);

    /**
     * Stores the JWT token issued after a successful login or registration.
     *
     * @param token the Bearer token returned by the server
     */
    void setToken(String token);
}
