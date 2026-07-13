package com.scholarmatch.usecase.recommend;

import com.scholarmatch.usecase.dto.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Output data for the get-recommendations use case.
 */
public final class RecommendOutputData {
    private final List<UserData> recommendations;
    private final boolean useCaseFailed;

    /**
     * Constructs recommend output.
     *
     * @param recommendations the ranked list of recommended collaborators
     * @param useCaseFailed   true if the use case failed
     */
    public RecommendOutputData(List<UserData> recommendations, boolean useCaseFailed) {
        this.recommendations = new ArrayList<>(recommendations);
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * @return the recommended users.
     */
    public List<UserData> getRecommendations() {
        return new ArrayList<>(this.recommendations);
    }

    /**
     *
     * @return true if the use case failed.
     */
    public boolean isUseCaseFailed() {
        return this.useCaseFailed;
    }
}
