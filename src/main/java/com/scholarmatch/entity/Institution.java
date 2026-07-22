package com.scholarmatch.entity;

import java.util.Objects;

/**
 * A well-known university or non-university research institute a {@link User} may
 * belong to.
 *
 * <p>Replaces what used to be a free-text {@code institution} field. Free text could not
 * support reliable filtering later — the same school gets typed as "MIT", "Massachusetts
 * Institute of Technology", or "M.I.T.", so substring matching on it misses real matches —
 * so this is a closed set selected from a dropdown instead of typed freehand.
 *
 * <p>Universities are sourced from the QS World University Rankings 2025 (the June 2024
 * edition). Research institutes are a curated census across major umbrella organizations
 * (Max Planck Society, Fraunhofer-Gesellschaft, Helmholtz Association, Leibniz Association,
 * Chinese Academy of Sciences, RIKEN, CNRS, US DOE national labs, NASA, NIH, NOAA, CGIAR,
 * intergovernmental bodies, UK Research and Innovation units, and other independent
 * biomedical/science institutes) rather than a single ranked list, since no canonical
 * "top N research institutes" ranking exists the way QS exists for universities.
 * Corporate research labs (e.g. Bell Labs, IBM Research) are deliberately excluded — they
 * are companies, not independent research institutes.
 *
 * <p>{@link #OTHER} covers every institution not in this list.
 */
public final class Institution {

    public static final Institution MIT =
            new Institution("MIT", "Massachusetts Institute of Technology");
    public static final Institution UNIVERSITY_OF_TORONTO =
            new Institution("UNIVERSITY_OF_TORONTO", "University of Toronto");
    public static final Institution UNIVERSITY_OF_CAMBRIDGE =
            new Institution("UNIVERSITY_OF_CAMBRIDGE", "University of Cambridge");
    public static final Institution OTHER = new Institution("OTHER", "Other");

    // ==================================================================================
    // Universities — QS World University Rankings 2025, in rank order
    // ==================================================================================


    // ==================================================================================
    // US National Lab (DOE) (17)
    // ==================================================================================


    // ==================================================================================
    // NASA Center (10)
    // ==================================================================================


    // ==================================================================================
    // NIH Institute/Center (25)
    // ==================================================================================


    // ==================================================================================
    // NOAA Research Laboratory (10)
    // ==================================================================================


    // ==================================================================================
    // Max Planck Institute (85)
    // ==================================================================================


    // ==================================================================================
    // Fraunhofer Institute (75)
    // ==================================================================================


    // ==================================================================================
    // Helmholtz Association (18)
    // ==================================================================================


    // ==================================================================================
    // Leibniz Association (95)
    // ==================================================================================


    // ==================================================================================
    // CNRS Institute (France) (10)
    // ==================================================================================


    // ==================================================================================
    // French National Research Agency (10)
    // ==================================================================================


    // ==================================================================================
    // CAS Institute (China) (107)
    // ==================================================================================


    // ==================================================================================
    // RIKEN Center (Japan) (11)
    // ==================================================================================


    // ==================================================================================
    // CGIAR / International Agricultural Research (13)
    // ==================================================================================


    // ==================================================================================
    // Intergovernmental / International (7)
    // ==================================================================================


    // ==================================================================================
    // UK Research Institute (26)
    // ==================================================================================


    // ==================================================================================
    // Independent Biomedical/Science Institute (12)
    // ==================================================================================


    // ==================================================================================
    // Fallback
    // ==================================================================================

    /**
     * An institution not covered by any constant above.
     */

    private final String institutionId;
    private final String displayName;

    public Institution(final String institutionId, final String displayName) {
        this.institutionId = Objects.requireNonNull(institutionId);
        this.displayName = displayName;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public String name() {
        return this.institutionId;
    }

    /**
     * Returns the official display name for this institution, e.g. "Massachusetts
     * Institute of Technology" for {@code MIT}.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Institution institution
                && this.institutionId.equals(institution.institutionId);
    }

    @Override
    public int hashCode() {
        return this.institutionId.hashCode();
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
