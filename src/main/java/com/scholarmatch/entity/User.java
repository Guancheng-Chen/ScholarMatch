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
}
