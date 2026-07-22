package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.close_posting.ClosePostingInputBoundary;
import com.scholarmatch.usecase.close_posting.ClosePostingInputData;

public final class ClosePostingController {
    private final ClosePostingInputBoundary interactor;

    public ClosePostingController(final ClosePostingInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void closePosting(final String postingId) {
        this.interactor.execute(new ClosePostingInputData(postingId));
    }
}
