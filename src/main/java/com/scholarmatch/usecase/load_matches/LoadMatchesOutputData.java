package com.scholarmatch.usecase.load_matches;

import com.scholarmatch.usecase.dto.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Output data for the load-confirmed-matches use case.
 */
public final class LoadMatchesOutputData {

    private final List<UserData> matches;

    /**
     * Constructs load-matches output.
     *
     * @param matches the current user's confirmed mutual matches
     */
    public LoadMatchesOutputData(final List<UserData> matches) {
        this.matches = new ArrayList<>(matches);
    }

    /**
     * Returns the confirmed matches.
     *
     * @return the matches list
     */
    public List<UserData> getMatches() {
        return new ArrayList<>(this.matches);
    }
}
