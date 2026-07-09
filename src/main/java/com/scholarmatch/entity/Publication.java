package com.scholarmatch.entity;

/**
 * Represents a single academic publication on a User's profile.
 */
public final class Publication {

    /**
     * The Digital Object Identifier of the publication.
     */
    private final String doi;

    /**
     * The full title of the publication.
     */
    private final String title;

    /**
     * The year the publication was published.
     */
    private final int year;

    /**
     * The total number of citations recorded by the API.
     */
    private final int citationCount;

    /**
     * Constructs a Publication with the given metadata.
     *
     * @param theDoi           the Digital Object Identifier
     * @param theTitle         the full title of the publication
     * @param theYear          the publication year
     * @param theCitationCount the number of citations recorded by the API
     */
    public Publication(
            final String theDoi,
            final String theTitle,
            final int theYear,
            final int theCitationCount) {
        this.doi = theDoi;
        this.title = theTitle;
        this.year = theYear;
        this.citationCount = theCitationCount;
    }

    /**
     * Returns the DOI of this publication.
     *
     * @return the DOI string
     */
    public String getDoi() {
        return this.doi;
    }

    /**
     * Returns the title of the publication.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the year the publication was published.
     *
     * @return the publication year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the number of citations this publication has received.
     *
     * @return the citation count
     */
    public int getCitationCount() {
        return this.citationCount;
    }
}
