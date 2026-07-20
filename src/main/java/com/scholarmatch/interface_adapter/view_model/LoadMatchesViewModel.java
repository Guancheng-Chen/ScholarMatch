package com.scholarmatch.interface_adapter.view_model;


import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.UserData;


/**
 * Observable ViewModel for the matched users panel.
 *
 * <p>Holds the list of users who have mutually connected right with the
 * current user. Populated by LoadMatchesInteractor (via LoadMatchesController),
 * which both LoadMatchesView and ChatView trigger a fresh fetch of on construction —
 * ConnectPresenter deliberately does not push into this list itself, since a match
 * is already committed server-side by the time it fires, so the next fetch is always
 * authoritative and a local add() would just be overwritten by it.
 */
public final class LoadMatchesViewModel {


    private final ObservableListModel<UserData> matchedUsers = new ObservableListModel<>();
    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final ObservableValue<UserData> matchNotification = new ObservableValue<>(null);


    /**
     * Returns the live list of matched users.
     *
     * @return the observable list bound to the matched-users views
     */
    public ObservableListModel<UserData> getMatchedUsers() {
        return this.matchedUsers;
    }


    /**
     * Fires once per newly formed match, set live by ConnectPresenter — independent of
     * #getMatchedUsers(), which nothing writes to outside of LoadMatchesInteractor's
     * server fetch.
     *
     * <p>Observed by the always-mounted MainLayoutView (not by any per-tab view, which
     * only exist while their tab is open) so a "You matched with X!" toast can appear
     * immediately regardless of which tab is currently open, instead of waiting for the
     * user to happen to navigate to a tab that re-pulls the match list from the server.
     *
     * @return the observable property holding the most recently matched user
     */
    public ObservableValue<UserData> matchNotificationProperty() {
        return this.matchNotification;
    }


    /**
     * Returns the error message property.
     *
     * @return the error message string property
     */
    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }


    /**
     * Sets the error message.
     *
     * @param message the error text to display
     */
    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }
}

