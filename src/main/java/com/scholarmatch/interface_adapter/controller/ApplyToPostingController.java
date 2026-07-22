package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingInputBoundary;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingInputData;

public final class ApplyToPostingController {
    private final ApplyToPostingInputBoundary interactor;

    public ApplyToPostingController(final ApplyToPostingInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(final String postingId, final String message) {
        this.interactor.execute(new ApplyToPostingInputData(postingId, message));
    }

    public void apply(final String postingId, final String message) {
        execute(postingId, message);
    }
}
