package com.scholarmatch.interface_adapter.view_model;


import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.UserData;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Observable ViewModel for the discover-users (recommend) screen.
 *
 * <p>The first element of cardStack is the card currently displayed.
 *
 * <p>Passing or connecting is recorded on the server (see the pass and connect
 * use cases), but the recommendation endpoint may not immediately reflect a
 * just-recorded decision on the very next fetch. excludedUserIds
 * remembers every user the current user has already decided on for the
 * lifetime of this ViewModel (which is a singleton reused across screen
 * switches), so a fresh fetch never resurfaces a card the user just dismissed.
 */
public final class RecommendViewModel {


    private final ObservableListModel<UserData> cardStack = new ObservableListModel<>();
    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final Set<String> excludedUserIds = new HashSet<>();


    /**
     * Returns the observable card stack. @return the card stack
     */
    public ObservableListModel<UserData> getCardStack() {
        return this.cardStack;
    }


    /**
     * Returns the error message property. @return the error message property
     */
    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }


    /**
     * Replaces the card stack, filtering out any user already passed or connected
     * with in this session. @param users the ranked recommendations
     */
    public void setCardStack(final List<UserData> users) {
        final List<UserData> filtered = new ArrayList<>();
        for (final UserData user : users) {
            if (!this.excludedUserIds.contains(user.getUserId())) {
                filtered.add(user);
            }
        }
        this.cardStack.setAll(filtered);
    }


    /**
     * Marks a user as decided (passed or connected) so future card-stack refreshes
     * never show them again, even after navigating away and back.
     *
     * @param userId the decided-on user's ID
     */
    public void excludeUser(final String userId) {
        this.excludedUserIds.add(userId);
    }


    /**
     * Sets the error message. @param message the error text
     */
    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }
}
