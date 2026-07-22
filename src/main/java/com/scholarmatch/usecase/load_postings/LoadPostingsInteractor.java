package com.scholarmatch.usecase.load_postings;

import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.usecase.data_access_interface.LoadPostingsDataAccessInterface;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/**
 * Interactor for loading postings and owner-visible application data.
 */
public final class LoadPostingsInteractor implements LoadPostingsInputBoundary {

    private final LoadPostingsDataAccessInterface dataAccessObject;
    private final LoadPostingsOutputBoundary outputBoundary;

    /**
     * Constructs the load postings interactor.
     *
     * @param dataAccessObject the posting data access object
     * @param outputBoundary the output boundary for presenting results
     */
    public LoadPostingsInteractor(
            final LoadPostingsDataAccessInterface dataAccessObject,
            final LoadPostingsOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Loads postings for the requested scope and prepares the corresponding view.
     *
     * @param inputData the load postings input data
     */
    @Override
    public void execute(final LoadPostingsInputData inputData) {
        try {
            final List<Posting> postings = this.dataAccessObject.loadPostings(inputData.scope());
            final Map<String, List<PostingApplication>> applications = this.dataAccessObject
                    .loadApplicationsForOwnedPostings(inputData.scope(), postings);
            final Map<String, List<PostingApplicationData>> applicationData = new LinkedHashMap<>();
            for (final Map.Entry<String, List<PostingApplication>> entry : applications.entrySet()) {
                applicationData.put(entry.getKey(), PostingApplicationData.fromAll(entry.getValue()));
            }
            this.outputBoundary.prepareSuccessView(
                    new LoadPostingsOutputData(PostingData.fromAll(postings), applicationData));
        } catch (final DataAccessException exception) {
            this.outputBoundary.prepareFailView(exception.getMessage());
        }
    }
}
