package com.scholarmatch.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Core domain entity representing a registered user of ScholarMatch.
 *
 * <p>Identity and preference fields are persisted server-side and accessed by
 * the client through ProfileDataAccessInterface and
 * AuthDataAccessInterface. The user's research text
 * (interests + publication titles) is separately embedded by the server and stored
 * as a vector column for similarity-based recommendations.
 */
public final class User {

    private final String userId;
    private final String firstName;
    private final String lastName;
    private String email;
    private String phoneNumber;
    private Institution institution;
    private AcademicLevel academicLevel;
    private ResearchField researchField;
    private CollaborationType lookingFor;
    private String collaborationDescription;
    private String researchDescription;
    private Integer weeklyAvailabilityHours;
    private FundingStatus fundingStatus;
    private String passwordHash;
    private final List<String> researchInterests;
    private final List<Education> educations;
    private final List<Publication> publications;
    private Integer hIndex;
    private Integer totalCitations;
    private EmailAccountType emailAccountType;

    /**
     * Constructs a new User with mandatory registration fields.
     *
     * @param userId                   the unique identifier assigned by the system
     * @param firstName                the user's given name
     * @param lastName                 the user's family name
     * @param email                    the user's email address (used for login; must be unique)
     * @param phoneNumber              the user's phone number, or null if not provided
     * @param institution              the university or research institution
     * @param academicLevel            the user's career stage
     * @param researchField            the broad discipline of the user's research
     * @param lookingFor               the type of collaboration this user is seeking
     * @param collaborationDescription freeform text describing the ideal collaborator
     * @param researchDescription      freeform text describing research domain;
     *                                 used as input to the embedding model
     * @param weeklyAvailabilityHours  hours per week the user can commit to collaboration
     * @param fundingStatus            how the user's research is currently funded
     * @param passwordHash             the BCrypt hash of the user's password
     */
    public User(
            final String userId,
            final String firstName,
            final String lastName,
            final String email,
            final String phoneNumber,
            final Institution institution,
            final AcademicLevel academicLevel,
            final ResearchField researchField,
            final CollaborationType lookingFor,
            final String collaborationDescription,
            final String researchDescription,
            final Integer weeklyAvailabilityHours,
            final FundingStatus fundingStatus,
            final String passwordHash) {
        this(userId, firstName, lastName, email, phoneNumber, institution, academicLevel,
                researchField, lookingFor, collaborationDescription, researchDescription,
                weeklyAvailabilityHours, fundingStatus, passwordHash, EmailAccountType.REGULAR);
    }

    /**
     * Constructs a user with an explicitly classified email account.
     *
     * @param userId                   the unique identifier assigned by the system
     * @param firstName                the user's given name
     * @param lastName                 the user's family name
     * @param email                    the user's email address
     * @param phoneNumber              the user's phone number
     * @param institution              the university or research institution
     * @param academicLevel            the user's career stage
     * @param researchField            the user's research discipline
     * @param lookingFor               the collaboration type being sought
     * @param collaborationDescription description of the ideal collaboration
     * @param researchDescription      description of the user's research
     * @param weeklyAvailabilityHours  weekly availability in hours
     * @param fundingStatus            the user's funding status
     * @param passwordHash             the stored password hash
     * @param emailAccountType         classification of the registered email domain
     */
    public User(
            final String userId,
            final String firstName,
            final String lastName,
            final String email,
            final String phoneNumber,
            final Institution institution,
            final AcademicLevel academicLevel,
            final ResearchField researchField,
            final CollaborationType lookingFor,
            final String collaborationDescription,
            final String researchDescription,
            final Integer weeklyAvailabilityHours,
            final FundingStatus fundingStatus,
            final String passwordHash,
            final EmailAccountType emailAccountType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.institution = institution;
        this.academicLevel = academicLevel;
        this.researchField = researchField;
        this.lookingFor = lookingFor;
        this.collaborationDescription = collaborationDescription;
        this.researchDescription = researchDescription;
        this.weeklyAvailabilityHours = weeklyAvailabilityHours;
        this.fundingStatus = fundingStatus;
        this.passwordHash = passwordHash;
        this.researchInterests = new ArrayList<>();
        this.educations = new ArrayList<>();
        this.publications = new ArrayList<>();
        this.hIndex = null;
        this.totalCitations = null;
        this.emailAccountType = emailAccountType;
    }

    /**
     * Returns the full display name (firstName + " " + lastName).
     *
     * @return the user's full name
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Checks whether every core profile field has been filled in.
     *
     * <p>An incomplete profile (e.g. a blank researchDescription) produces
     * inaccurate recommendations, so the match use case gates on this before fetching
     * recommendations. h-index and total citations are intentionally excluded — those
     * are allowed to stay unknown (null, displayed as "N/A") and never block matching.
     *
     * @return true if every field below is non-null and non-blank
     */
    public boolean isProfileComplete() {
        return isNotBlank(this.firstName)
                && isNotBlank(this.lastName)
                && isNotBlank(this.email)
                && isNotBlank(this.phoneNumber)
                && this.institution != null
                && this.academicLevel != null
                && this.researchField != null
                && this.lookingFor != null
                && isNotBlank(this.collaborationDescription)
                && isNotBlank(this.researchDescription)
                && this.weeklyAvailabilityHours != null
                && this.fundingStatus != null;
    }

    private static boolean isNotBlank(final String value) {
        return value != null && !value.isBlank();
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
     * @return true if the interest was present and removed
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
     * Adds a publication to this user's profile.
     *
     * @param publication the publication to add
     */
    public void addPublication(final Publication publication) {
        this.publications.add(publication);
    }

    /**
     * Removes a publication from this user's profile by DOI.
     *
     * @param doi the DOI of the publication to remove
     * @return true if the publication was present and removed
     */
    public boolean removePublication(final String doi) {
        return this.publications.removeIf(publication -> publication.getDoi().equals(doi));
    }

    /**
     * Returns a copy of the publication list.
     *
     * @return the list of publications on this user's profile
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
     * Returns the user's phone number, or null if not set.
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
    public Institution getInstitution() {
        return this.institution;
    }

    /**
     * Updates the institution of this user.
     *
     * @param institution the new institution name
     */
    public void setInstitution(final Institution institution) {
        this.institution = institution;
    }

    /**
     * Returns the academic level of this user.
     *
     * @return the AcademicLevel
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
     * Returns the broad research discipline of this user.
     *
     * @return the ResearchField
     */
    public ResearchField getResearchField() {
        return this.researchField;
    }

    /**
     * Updates the broad research discipline of this user.
     *
     * @param researchField the new research field
     */
    public void setResearchField(final ResearchField researchField) {
        this.researchField = researchField;
    }

    /**
     * Returns the collaboration type this user is looking for.
     *
     * @return the CollaborationType
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
     * Returns how many hours per week this user can commit to collaboration.
     *
     * @return the weekly availability in hours
     */
    public Integer getWeeklyAvailabilityHours() {
        return this.weeklyAvailabilityHours;
    }

    /**
     * Updates how many hours per week this user can commit to collaboration.
     *
     * @param weeklyAvailabilityHours the new weekly availability in hours
     */
    public void setWeeklyAvailabilityHours(final Integer weeklyAvailabilityHours) {
        this.weeklyAvailabilityHours = weeklyAvailabilityHours;
    }

    /**
     * Returns how this user's research is currently funded.
     *
     * @return the FundingStatus
     */
    public FundingStatus getFundingStatus() {
        return this.fundingStatus;
    }

    /**
     * Updates how this user's research is currently funded.
     *
     * @param fundingStatus the new funding status
     */
    public void setFundingStatus(final FundingStatus fundingStatus) {
        this.fundingStatus = fundingStatus;
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
     * Returns the h-index of this user.
     *
     * @return the h-index, or null if unknown (never looked up or entered)
     */
    public Integer gethIndex() {
        return this.hIndex;
    }

    /**
     * Sets the h-index.
     *
     * @param hIndex the h-index to store, or null if unknown
     */
    public void sethIndex(final Integer hIndex) {
        this.hIndex = hIndex;
    }

    /**
     * Returns the total citation count across all publications.
     *
     * @return the total citation count, or null if unknown (never looked up or entered)
     */
    public Integer getTotalCitations() {
        return this.totalCitations;
    }

    /**
     * Sets the total citation count.
     *
     * @param totalCitations the total citations to store, or null if unknown
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
