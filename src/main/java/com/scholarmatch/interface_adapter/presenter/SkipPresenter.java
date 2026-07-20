package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.usecase.skip.SkipOutputBoundary;

/**
 * Presenter for the skip use case.
 *
 * <p>No view model update is needed — the card is already removed from the stack on
 * the client the moment Skip is clicked, mirroring DislikePresenter.
 */
public final class SkipPresenter implements SkipOutputBoundary {

    @Override
    public void prepareSuccessView() {
        // no UI update needed — card already dismissed in the view
    }
}
