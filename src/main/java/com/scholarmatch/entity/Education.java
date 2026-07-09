package com.scholarmatch.entity;

/**
 * Represents one entry in a user's educational history.
 *
 * <p>Start and end dates are stored as separate year and month integers.
 * An {@code endYear} of {@code 0} means the user is currently enrolled.
 */
public final class Education {
    private final String institution;
    private final DegreeType degreeType;
    private final int startYear;
    private final int startMonth;
    private final int endYear;
    private final int endMonth;

    /**
     * Constructs an {@code Education} entry.
     *
     * @param institution the name of the school or university
     * @param degreeType the degree or qualification obtained
     * @param startYear the year enrolment began
     * @param startMonth the month enrolment began, where 1 is January and 12 is December
     * @param endYear the year the degree was completed, or {@code 0} if ongoing
     * @param endMonth the month the degree was completed, or {@code 0} if ongoing
     */
    public Education(final String institution, final DegreeType degreeType, final int startYear,
                     final int startMonth, final int endYear, final int endMonth) {
        this.institution = institution;
        this.degreeType = degreeType;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.endYear = endYear;
        this.endMonth = endMonth;
    }

    /**
     * Returns the name of the school or university.
     *
     * @return the institution name
     */
    public String getInstitution() {
        return this.institution;
    }

    /**
     * Returns the degree type for this education entry.
     *
     * @return the {@link DegreeType}
     */
    public DegreeType getDegreeType() {
        return this.degreeType;
    }

    /**
     * Returns the year enrolment began.
     *
     * @return the start year
     */
    public int getStartYear() {
        return this.startYear;
    }

    /**
     * Returns the month enrolment began.
     *
     * @return the start month
     */
    public int getStartMonth() {
        return this.startMonth;
    }

    /**
     * Returns the year the degree was completed, or {@code 0} if still ongoing.
     *
     * @return the end year, or {@code 0}
     */
    public int getEndYear() {
        return this.endYear;
    }

    /**
     * Returns the month the degree was completed, or {@code 0} if still ongoing.
     *
     * @return the end month, or {@code 0}
     */
    public int getEndMonth() {
        return this.endMonth;
    }

    /**
     * Returns {@code true} if this education entry is still in progress.
     *
     * @return {@code true} when {@code endYear == 0}
     */
    public boolean isOngoing() {
        return this.endYear == 0;
    }
}
