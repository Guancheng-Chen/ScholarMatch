package com.scholarmatch.usecase.recommend;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.util.List;

/**
 * Interactor implementing the user recommendation use case.
 *
 * <p>Fetches a ranked list of similar users from the server. The server
 * handles embedding and pgvector similarity search; the client only manages
 * what to display (caching the card stack in
 * com.scholarmatch.interface_adapter.view_model.RecommendViewModel).
 *
 * <p>Before fetching recommendations, checks that the current user's own profile
 * is complete (User#isProfileComplete()) — an incomplete profile (e.g. a
 * blank research description) produces inaccurate matches, so recommendations are
 * withheld until every core field is filled in.
 */
public final class RecommendInteractor implements RecommendInputBoundary {

    /**
     * Shown on the recommend screen when the current user's profile is incomplete.
     */
    static final String INCOMPLETE_PROFILE_MESSAGE =
            "Your profile is incomplete. Please fill in all profile fields before viewing recommendations.";

    private final RecommendDataAccessInterface recommendDataAccessObject;
    private final RecommendOutputBoundary outputBoundary;

    /**
     * Constructs a RecommendInteractor.
     *
     * @param recommendDataAccessObject server gateway for recommendations and profile
     *                                   completeness checks
     * @param outputBoundary            the presenter that will receive the result
     */
    public RecommendInteractor(
            final RecommendDataAccessInterface recommendDataAccessObject,
            final RecommendOutputBoundary outputBoundary) {
        this.recommendDataAccessObject = recommendDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute() {
        try {
            final User currentUser = recommendDataAccessObject.getProfile();
            if (!currentUser.isProfileComplete()) {
                outputBoundary.prepareFailView(INCOMPLETE_PROFILE_MESSAGE);
                return;
            }
            final List<User> recommendations = recommendDataAccessObject.getRecommendations();
            outputBoundary.prepareSuccessView(new RecommendOutputData(UserData.fromAll(recommendations), false));
        } catch (final DataAccessException e) {
            outputBoundary.prepareFailView(e.getMessage());
        }
    }
}
