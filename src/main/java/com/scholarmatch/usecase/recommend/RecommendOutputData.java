package com.scholarmatch.usecase.recommend;

import com.scholarmatch.usecase.dto.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Output data for the get-recommendations use case.
 *
 * <p>Only ever constructed on the success path — RecommendOutputBoundary#prepareFailView(String)
 * carries failures instead, so there is no separate failure flag here.
 */
public final class RecommendOutputData {
    private final List<UserData> recommendations;

    /**
     * Constructs recommend output.
     *
     * @param recommendations the ranked list of recommended collaborators
     */
    public RecommendOutputData(List<UserData> recommendations) {
        this.recommendations = new ArrayList<>(recommendations);
    }

    /**
     * @return the recommended users. @return the recommendations list
     */
    public List<UserData> getRecommendations() {
        return new ArrayList<>(this.recommendations);
    }
}
