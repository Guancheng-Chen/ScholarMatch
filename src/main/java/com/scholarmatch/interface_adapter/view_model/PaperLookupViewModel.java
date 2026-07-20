package com.scholarmatch.interface_adapter.view_model;


import com.scholarmatch.entity.Publication;
import com.scholarmatch.interface_adapter.view_model.support.ObservableListModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;


/**
 * Observable ViewModel for the paper/author lookup panel used during registration
 * and profile editing.
 */
public final class PaperLookupViewModel {


    private final ObservableListModel<AuthorCandidateData> authorCandidates = new ObservableListModel<>();
    private final ObservableListModel<Publication> authorPapersFound = new ObservableListModel<>();
    private final ObservableValue<String> statusMessage = new ObservableValue<>("");


    /**
     * Returns the live list of author candidates from the last name search.
     *
     * @return the observable author candidate list
     */
    public ObservableListModel<AuthorCandidateData> getAuthorCandidates() {
        return this.authorCandidates;
    }


    /**
     * Returns the live list of papers bulk-imported for the last selected author.
     *
     * @return the observable list of imported papers
     */
    public ObservableListModel<Publication> getAuthorPapersFound() {
        return this.authorPapersFound;
    }


    /**
     * Returns the status/error message property.
     *
     * @return the status message string property
     */
    public ObservableValue<String> statusMessageProperty() {
        return this.statusMessage;
    }


    /**
     * Sets the status/error message.
     *
     * @param message the text to display
     */
    public void setStatusMessage(final String message) {
        this.statusMessage.set(message);
    }
}
