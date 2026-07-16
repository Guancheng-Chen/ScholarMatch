package com.scholarmatch.usecase.data_access_interface;

/**
 * Port through which logout and account deletion end the session.
 *
 * <p>Kept separate from CurrentUserProviderInterface (read) and
 * SessionWriterInterface (write) — the logout and delete-account use cases
 * only ever clear the session, they never read or write it, so they have no reason to depend
 * on those capabilities just because a single session object happens to implement all three.
 */
public interface SessionClearerInterface {

    /**
     * Clears the session, effectively logging the user out.
     */
    void clearSession();
}
