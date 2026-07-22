package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.usecase.load_postings.LoadPostingsInputBoundary;
import com.scholarmatch.usecase.load_postings.LoadPostingsInputData;
import com.scholarmatch.usecase.load_postings.PostingScope;

public final class LoadPostingsController {
    private final LoadPostingsInputBoundary interactor;

    public LoadPostingsController(final LoadPostingsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(final PostingScope scope) {
        this.interactor.execute(new LoadPostingsInputData(scope));
    }

    public void loadPostings(final PostingScope scope) {
        execute(scope);
    }
}
