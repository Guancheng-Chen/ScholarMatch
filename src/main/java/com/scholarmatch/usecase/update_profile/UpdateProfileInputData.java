package com.scholarmatch.usecase.update_profile;

import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Publication;

import java.util.ArrayList;
import java.util.List;

/**
 * Input data for the update-profile use case.
 *
 * <p>Carries the full updated state of the user's mutable profile fields.
 * All list fields replace the user's existing lists entirely.
 */
public final class UpdateProfileInputData {

    private final String email;
    private final String institution;
    private final String academicLevel;
    private final String researchField;
    private final String lookingFor;
    private final String collaborationDescription;
    private final String researchDescription;
    private final Integer weeklyAvailabilityHours;
    private final String fundingStatus;
    private final List<String> researchInterests;
    private final String phoneNumber;
    private final Integer hIndex;
    private final Integer totalCitations;
    private final List<Education> educations;
    private final List<Publication> publications;

    /**
     * Constructs the update-profile input data.
     *
     * <p>The user's identity is resolved server-side from the request's auth token — the
     * interactor itself has no session dependency.
     *
     * @param email                    updated email address; the server rejects this if
     *                                  another account already uses it
     * @param institution              updated institution name
     * @param academicLevel            updated academic level name
     * @param researchField            updated research field name
     * @param lookingFor               updated collaboration type name
     * @param collaborationDescription updated collaboration preference text
     * @param researchDescription      updated research domain description
     * @param weeklyAvailabilityHours  updated weekly availability in hours
     * @param fundingStatus            updated funding status name
     * @param researchInterests        updated research interest tags
     * @param phoneNumber              updated phone number
     * @param hIndex                   manually-entered h-index, or null if left blank
     * @param totalCitations           manually-entered total citations, or null if left blank
     * @param educations               updated education history (replaces existing)
     * @param publications             updated publication list (replaces existing)
     */
    public UpdateProfileInputData(
            final String email,
            final String institution,
            final String academicLevel,
            final String researchField,
            final String lookingFor,
            final String collaborationDescription,
            final String researchDescription,
            final Integer weeklyAvailabilityHours,
            final String fundingStatus,
            final List<String> researchInterests,
            final String phoneNumber,
            final Integer hIndex,
            final Integer totalCitations,
            final List<Education> educations,
            final List<Publication> publications) {
        this.email = email;
        this.institution = institution;
        this.academicLevel = academicLevel;
        this.researchField = researchField;
        this.lookingFor = lookingFor;
        this.collaborationDescription = collaborationDescription;
        this.researchDescription = researchDescription;
        this.weeklyAvailabilityHours = weeklyAvailabilityHours;
        this.fundingStatus = fundingStatus;
        this.researchInterests = new ArrayList<>(researchInterests);
        this.phoneNumber = phoneNumber;
        this.hIndex = hIndex;
        this.totalCitations = totalCitations;
        this.educations = new ArrayList<>(educations);
        this.publications = new ArrayList<>(publications);
    }

    /**
     * @return the updated email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return the institution
     */
    public String getInstitution() {
        return this.institution;
    }

    /**
     * @return the academic level name
     */
    public String getAcademicLevel() {
        return this.academicLevel;
    }

    /**
     * @return the research field name
     */
    public String getResearchField() {
        return this.researchField;
    }

    /**
     * @return the collaboration type name
     */
    public String getLookingFor() {
        return this.lookingFor;
    }

    /**
     * @return the collaboration preference description
     */
    public String getCollaborationDescription() {
        return this.collaborationDescription;
    }

    /**
     * @return the research domain description
     */
    public String getResearchDescription() {
        return this.researchDescription;
    }

    /**
     * @return updated weekly availability in hours
     */
    public Integer getWeeklyAvailabilityHours() {
        return this.weeklyAvailabilityHours;
    }

    /**
     * @return the funding status name
     */
    public String getFundingStatus() {
        return this.fundingStatus;
    }

    /**
     * @return a copy of the research interest tags
     */
    public List<String> getResearchInterests() {
        return new ArrayList<>(this.researchInterests);
    }

    /**
     * @return the phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @return the manually-entered h-index, or null if left blank
     */
    public Integer getHIndex() {
        return this.hIndex;
    }

    /**
     * @return the manually-entered total citations, or null if left blank
     */
    public Integer getTotalCitations() {
        return this.totalCitations;
    }

    /**
     * @return a copy of the education history
     */
    public List<Education> getEducations() {
        return new ArrayList<>(this.educations);
    }

    /**
     * @return a copy of the publication list
     */
    public List<Publication> getPublications() {
        return new ArrayList<>(this.publications);
    }
}

