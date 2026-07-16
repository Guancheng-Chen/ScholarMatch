package com.scholarmatch.entity;

import java.time.Month;

/**
 * Represents one entry in a user's educational history.
 *
 * <p>The start month and (if present) end month are represented by {@link Month} rather
 * than a raw integer, so an out-of-range value like "13" can never be constructed. A null
 * {@code endYear} means the user is currently enrolled ("ongoing"); {@code endMonth} is
 * independent of {@code endYear} and may be null even when {@code endYear} is present
 * (end year known, exact month not).
 */
public final class Education {

    private final String institution;
    private final DegreeType degreeType;
    private final int startYear;
    private final Month startMonth;
    private final Integer endYear;
    private final Month endMonth;

    /**
     * Constructs an Education entry.
     *
     * @param institution the name of the school or university
     * @param degreeType  the degree or qualification obtained
     * @param startYear   the year enrolment began
     * @param startMonth  the month enrolment began
     * @param endYear     the year the degree was completed, or null if ongoing
     * @param endMonth    the month the degree was completed, or null if unknown/ongoing
     */
    public Education(
            final String institution,
            final DegreeType degreeType,
            final int startYear,
            final Month startMonth,
            final Integer endYear,
            final Month endMonth) {
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
     * @return the DegreeType
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
    public Month getStartMonth() {
        return this.startMonth;
    }

    /**
     * Returns the year the degree was completed, or null if still ongoing.
     *
     * @return the end year, or null
     */
    public Integer getEndYear() {
        return this.endYear;
    }

    /**
     * Returns the month the degree was completed, or null if unknown or ongoing.
     *
     * @return the end month, or null
     */
    public Month getEndMonth() {
        return this.endMonth;
    }

    /**
     * Returns true if this education entry is still in progress.
     *
     * @return true when endYear is null
     */
    public boolean isOngoing() {
        return this.endYear == null;
    }
}
