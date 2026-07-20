package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;
import com.scholarmatch.usecase.paper_lookup.PaperLookupOutputBoundary;

import java.util.List;

/**
 * Presenter for the paper/author lookup use case.
 */
public final class PaperLookupPresenter implements PaperLookupOutputBoundary {

    private final PaperLookupViewModel viewModel;

    /**
     * Constructs a PaperLookupPresenter.
     *
     * @param viewModel the shared view model for the paper lookup panel
     */
    public PaperLookupPresenter(final PaperLookupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareAuthorCandidates(final List<AuthorCandidateData> candidates) {
        this.viewModel.setStatusMessage(candidates.isEmpty() ? "No authors found." : "");
        this.viewModel.getAuthorCandidates().setAll(candidates);
    }

    @Override
    public void prepareAuthorPapersFound(final List<Publication> papers) {
        this.viewModel.setStatusMessage(papers.isEmpty() ? "No papers found for this author." : "");
        this.viewModel.getAuthorPapersFound().setAll(papers);
    }

    @Override
    public void prepareError(final String message) {
        this.viewModel.setStatusMessage("Lookup failed: " + message + " Please wait a moment and try again.");
    }
}
