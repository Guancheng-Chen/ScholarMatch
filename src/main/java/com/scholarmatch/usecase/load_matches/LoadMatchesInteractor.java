package com.scholarmatch.usecase.load_matches;

import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.exception.DataAccessException;

/**
 * Interactor implementing the load-confirmed-matches use case.
 *
 * <p>Fetches the users the current user has already mutually matched with,
 * including matches confirmed in a previous session — unlike the live match
 * notification fired by the connect use case, which only covers matches detected
 * during the current run.
 */
public final class LoadMatchesInteractor implements LoadMatchesInputBoundary {

    private final LoadMatchesDataAccessInterface loadMatchesDataAccessObject;
    private final LoadMatchesOutputBoundary outputBoundary;

    /**
     * Constructs a LoadMatchesInteractor.
     *
     * @param loadMatchesDataAccessObject server gateway for fetching confirmed matches
     * @param outputBoundary               the presenter that receives the result
     */
    public LoadMatchesInteractor(
            final LoadMatchesDataAccessInterface loadMatchesDataAccessObject,
            final LoadMatchesOutputBoundary outputBoundary) {
        this.loadMatchesDataAccessObject = loadMatchesDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute() {
        try {
            this.outputBoundary.prepareSuccessView(
                    new LoadMatchesOutputData(UserData.fromAll(this.loadMatchesDataAccessObject.getMatches())));
        } catch (final DataAccessException e) {
            this.outputBoundary.prepareFailView(e.getMessage());
        }
    }
}
