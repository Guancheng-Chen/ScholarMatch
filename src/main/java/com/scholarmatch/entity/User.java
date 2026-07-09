package com.scholarmatch.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Core domain entity representing a registered user of ScholarMatch.
 *
 * <p>Identity and preference fields are persisted server-side and accessed by
 * the client through {@code ProfileDataAccessInterface} and
 * {@code AuthDataAccessInterface}. The user's research text
 * (interests + paper titles) is separately embedded by the server and stored
 * as a vector column for similarity-based recommendations.
 */
public final class User {
    private final String userId;
    private final String firstName;
    private final String lastName;
    private String email;
    private String phoneNumber;
    private String institution;
    private AcademicLevel academicLevel;
    private CollaborationType lookingFor;
    private String collaborationDescription;
    private String researchDescription;
    private String passwordHash;
    private final List<String> researchInterests;
    private final List<Education> educations;
    private final List<Paper> papers;
    private final List<Publication> publications;
    private Integer hIndex;
    private Integer totalCitations;

    /**
     * Constructs a new {@code User} with mandatory registration fields.
     *
     * @param userId the unique identifier assigned by the system
     * @param firstName the user's given name
     * @param lastName the user's family name
     * @param email the user's email address (used for login; must be unique)
     * @param phoneNumber the user's phone number, or {@code null} if not provided
     * @param institution the university or research institution
     * @param academicLevel the user's career stage
     * @param lookingFor the type of collaboration this user is seeking
     * @param collaborationDescription freeform text describing the ideal collaborator
     * @param researchDescription freeform text describing research domain;
     *                            used as input to the embedding model
     * @param passwordHash the BCrypt hash of the user's password
     */
    public User(
            final String userId,
            final String firstName,
            final String lastName,
            final String email,
            final String phoneNumber,
            final String institution,
            final AcademicLevel academicLevel,
            final CollaborationType lookingFor,
            final String collaborationDescription,
            final String researchDescription,
            final String passwordHash) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.institution = institution;
        this.academicLevel = academicLevel;
        this.lookingFor = lookingFor;
        this.collaborationDescription = collaborationDescription;
        this.researchDescription = researchDescription;
        this.passwordHash = passwordHash;
        this.researchInterests = new ArrayList<>();
        this.educations = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.publications = new ArrayList<>();
        this.hIndex = null;
        this.totalCitations = null;
    }

    /**
     * Returns the full display name ({@code firstName + " " + lastName}).
     *
     * @return the user's full name
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Adds a research interest keyword to this user's profile.
     *
     * @param interest the keyword to add (e.g. "machine learning")
     */
    public void addResearchInterest(final String interest) {
        this.researchInterests.add(interest);
    }

    /**
     * Removes a research interest keyword from this user's profile.
     *
     * @param interest the keyword to remove
     * @return {@code true} if the interest was present and removed
     */
    public boolean removeResearchInterest(final String interest) {
        return this.researchInterests.remove(interest);
    }

    /**
     * Adds an education entry to this user's history.
     *
     * @param education the education entry to add
     */
    public void addEducation(final Education education) {
        this.educations.add(education);
    }

    /**
     * Returns a copy of the education history list, ordered as added.
     *
     * @return the list of education entries
     */
    public List<Education> getEducations() {
        return new ArrayList<>(this.educations);
    }

    /**
     * Adds a paper to this user's publication list.
     *
     * @param paper the paper to add
     */
    public void addPaper(final Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Removes a paper from this user's publication list by DOI.
     *
     * @param doi the DOI of the paper to remove
     * @return {@code true} if the paper was present and removed
     */
    public boolean removePaper(final String doi) {
        return this.papers.removeIf(paper -> paper.getDoi().equals(doi));
    }

    /**
     * Adds a publication to this user's output list.
     *
     * @param publication the publication to add
     */
    public void addPublication(final Publication publication) {
        this.publications.add(publication);
    }

    /**
     * Removes a publication from this user's output list by DOI.
     *
     * @param doi the DOI of the publication to remove
     * @return {@code true} if the publication was present and removed
     */
    public boolean removePublication(final String doi) {
        return this.publications.removeIf(pub -> pub.getDoi().equals(doi));
    }

    /**
     * Returns a copy of the publication list.
     *
     * @return the list of non-paper outputs on this user's profile
     */
    public List<Publication> getPublications() {
        return new ArrayList<>(this.publications);
    }

    /**
     * Returns the system-assigned unique identifier for this user.
     *
     * @return the user ID
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Returns the user's given name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the user's family name.
     *
     * @return the last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Updates the user's email address.
     *
     * @param email the new email address
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Returns the user's phone number, or {@code null} if not set.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Updates the user's phone number.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the institution of this user.
     *
     * @return the institution name
     */
    public String getInstitution() {
        return this.institution;
    }

    /**
     * Updates the institution of this user.
     *
     * @param institution the new institution name
     */
    public void setInstitution(final String institution) {
        this.institution = institution;
    }

    /**
     * Returns the academic level of this user.
     *
     * @return the {@link AcademicLevel}
     */
    public AcademicLevel getAcademicLevel() {
        return this.academicLevel;
    }

    /**
     * Updates the academic level of this user.
     *
     * @param academicLevel the new academic level
     */
    public void setAcademicLevel(final AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    /**
     * Returns the collaboration type this user is looking for.
     *
     * @return the {@link CollaborationType}
     */
    public CollaborationType getLookingFor() {
        return this.lookingFor;
    }

    /**
     * Updates the collaboration type this user is looking for.
     *
     * @param lookingFor the new collaboration preference
     */
    public void setLookingFor(final CollaborationType lookingFor) {
        this.lookingFor = lookingFor;
    }

    /**
     * Returns the freeform text describing the ideal collaborator.
     *
     * @return the collaboration description
     */
    public String getCollaborationDescription() {
        return this.collaborationDescription;
    }

    /**
     * Updates the freeform text describing the ideal collaborator.
     *
     * @param collaborationDescription the new description
     */
    public void setCollaborationDescription(final String collaborationDescription) {
        this.collaborationDescription = collaborationDescription;
    }

    /**
     * Returns the freeform research domain description used as embedding input.
     *
     * @return the research description
     */
    public String getResearchDescription() {
        return this.researchDescription;
    }

    /**
     * Updates the research domain description.
     *
     * @param researchDescription the new description
     */
    public void setResearchDescription(final String researchDescription) {
        this.researchDescription = researchDescription;
    }

    /**
     * Returns a copy of the research interest list.
     *
     * @return the list of research interest keywords
     */
    public List<String> getResearchInterests() {
        return new ArrayList<>(this.researchInterests);
    }

    /**
     * Returns a copy of the publication list.
     *
     * @return the list of papers on this user's profile
     */
    public List<Paper> getPapers() {
        return new ArrayList<>(this.papers);
    }

    /**
     * Returns the h-index of this user.
     *
     * @return the h-index, or {@code null} if unknown (never looked up or entered)
     */
    public Integer gethIndex() {
        return this.hIndex;
    }

    /**
     * Sets the h-index.
     *
     * @param hIndex the h-index to store, or {@code null} if unknown
     */
    public void sethIndex(final Integer hIndex) {
        this.hIndex = hIndex;
    }

    /**
     * Returns the total citation count across all publications.
     *
     * @return the total citation count, or {@code null} if unknown (never looked up or entered)
     */
    public Integer getTotalCitations() {
        return this.totalCitations;
    }

    /**
     * Sets the total citation count.
     *
     * @param totalCitations the total citations to store, or {@code null} if unknown
     */
    public void setTotalCitations(final Integer totalCitations) {
        this.totalCitations = totalCitations;
    }

    /**
     * Returns the BCrypt password hash for this user.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return this.passwordHash;
    }

    /**
     * Updates the password hash after a password-change operation.
     *
     * @param passwordHash the new BCrypt hash
     */
    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
