package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.usecase.dislike.DislikeOutputBoundary;

/**
 * Presenter for the dislike use case.
 *
 * <p>No view model update is needed either way — the card is already removed
 * from the stack on the client the moment Dislike is clicked, mirroring how
 * ConnectPresenter#prepareNoMatch() is also a no-op.
 */
public final class DislikePresenter implements DislikeOutputBoundary {

    @Override
    public void prepareSuccessView() {
        // no UI update needed — card already dismissed in the view
    }

    @Override
    public void prepareFailView(final String errorMessage) {
        // no UI update needed — card already dismissed in the view
    }
}
