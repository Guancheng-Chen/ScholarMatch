package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.connect.ConnectInputBoundary;
import com.scholarmatch.usecase.connect.ConnectInputData;
import com.scholarmatch.usecase.dto.UserData;

/**
 * Controller that forwards connect actions to the connect use case.
 */
public final class ConnectController {

    private final ConnectInputBoundary interactor;

    /**
     * Constructs a ConnectController.
     *
     * @param interactor the connect use case
     */
    public ConnectController(final ConnectInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the current user connects right on a user card.
     *
     * @param connectedUserId the ID of the user shown on the card
     * @param connectedUser   the profile snapshot of the user shown on the card
     */
    public void connect(final String connectedUserId, final UserData connectedUser) {
        this.interactor.execute(new ConnectInputData(connectedUserId, connectedUser));
    }
}
