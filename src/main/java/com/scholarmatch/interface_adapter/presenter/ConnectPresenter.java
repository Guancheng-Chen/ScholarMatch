package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.usecase.connect.ConnectOutputBoundary;
import com.scholarmatch.usecase.connect.ConnectOutputData;

/**
 * Presenter for the connect use case.
 */
public final class ConnectPresenter implements ConnectOutputBoundary {

    private final LoadMatchesViewModel loadMatchesViewModel;

    /**
     * Constructs a ConnectPresenter.
     *
     * @param loadMatchesViewModel the shared view model for the matched users panel
     */
    public ConnectPresenter(final LoadMatchesViewModel loadMatchesViewModel) {
        this.loadMatchesViewModel = loadMatchesViewModel;
    }

    @Override
    public void prepareMatchFound(final ConnectOutputData outputData) {
        // Deliberately doesn't push into loadMatchesViewModel.getMatchedUsers() here —
        // LoadMatchesView/ChatView both re-fetch the authoritative list from the server
        // (via LoadMatchesController) on construction, so a local add() would just get
        // overwritten by the next setAll() and never be visible to anyone in the meantime.
        // matchNotificationProperty is the one piece of state this presenter is actually
        // responsible for: the immediate "You matched with X!" toast.
        this.loadMatchesViewModel.matchNotificationProperty().set(outputData.getMatchedUser());
    }

    @Override
    public void prepareNoMatch() {
        // no UI update needed — card already dismissed in the view
    }
}
