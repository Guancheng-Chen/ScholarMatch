package com.scholarmatch.frameworks.data_access_object;


import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.SessionClearerInterface;
import com.scholarmatch.usecase.data_access_interface.SessionWriterInterface;


/**
 * In-memory implementation of the session read/write/clear ports
 * (CurrentUserProviderInterface, SessionWriterInterface,
 * SessionClearerInterface).
 *
 * <p>Instantiated once in Config and injected into every interactor
 * that needs to know who the current user is. Not persisted — cleared on logout
 * or application restart.
 */
public final class CurrentUserProvider
        implements CurrentUserProviderInterface, SessionWriterInterface, SessionClearerInterface {


    private String currentUserId;
    private String token;


    /**
     * Constructs an empty (unauthenticated) CurrentUserProvider.
     */
    public CurrentUserProvider() {
        this.currentUserId = null;
        this.token = null;
    }


    @Override
    public String getCurrentUserId() {
        if (this.currentUserId == null) {
            throw new IllegalStateException("No user is currently authenticated");
        }
        return this.currentUserId;
    }


    @Override
    public void setCurrentUserId(final String userId) {
        this.currentUserId = userId;
    }


    @Override
    public void clearSession() {
        this.currentUserId = null;
        this.token = null;
    }


    @Override
    public void setToken(final String token) {
        this.token = token;
    }


    @Override
    public String getToken() {
        return this.token;
    }


    /**
     * Returns true if a user is currently authenticated.
     *
     * @return true when a user ID is set
     */
    public boolean isLoggedIn() {
        return this.currentUserId != null;
    }
}
