package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;

/**
 * Observable ViewModel for the logout action.
 *
 * <p>Written by LogoutPresenter and observed by MainView, which swaps back
 * to the logged-out shell whenever #loggedOutProperty() fires — mirroring how
 * LoginViewModel#loggedInUserIdProperty() drives the switch the other way.
 */
public final class LogoutViewModel {

    private final ObservableValue<Boolean> loggedOut = new ObservableValue<>(false);

    /**
     * @return the logged-out flag property
     */
    public ObservableValue<Boolean> loggedOutProperty() {
        return this.loggedOut;
    }

    /**
     * Marks logout as completed, notifying observers.
     */
    public void setLoggedOut() {
        this.loggedOut.set(true);
    }
}
