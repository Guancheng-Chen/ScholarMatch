package com.scholarmatch.usecase.update_profile;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;

import java.util.ArrayList;
import java.util.List;

/**
 * Edited values submitted by the current user from the profile form.
 *
 * <p>Identity and authentication fields are excluded because the profile use case does not
 * edit the user ID, name, or password. Bibliometric values are boxed so an unknown value can
 * remain null rather than being converted to zero.
 */
public final class UpdateProfileInputData {

    private final String email;
    private final String phoneNumber;
    private final Institution institution;
    private final AcademicLevel academicLevel;
    private final ResearchField researchField;
    private final CollaborationType lookingFor;
    private final String collaborationDescription;
    private final String researchDescription;
    private final Integer weeklyAvailabilityHours;
    private final FundingStatus fundingStatus;
    private final List<String> researchInterests;
    private final List<Education> educations;
    private final List<Publication> publications;
    private final Integer hIndex;
    private final Integer totalCitations;

    /**
     * Constructs profile update input.
     *
     * @param email                    the edited email address
     * @param phoneNumber              the edited phone number
     * @param institution              the edited institution
     * @param academicLevel            the edited academic level
     * @param researchField            the edited research field
     * @param lookingFor               the edited collaboration preference
     * @param collaborationDescription the edited collaborator description
     * @param researchDescription      the edited research description
     * @param weeklyAvailabilityHours  the edited weekly availability
     * @param fundingStatus            the edited funding status
     * @param researchInterests        the edited research interests
     * @param educations               the edited education history
     * @param publications             the edited publication list
     * @param hIndex                   the edited h-index, or null when unknown
     * @param totalCitations           the edited citation total, or null when unknown
     */
    public UpdateProfileInputData(
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
            final List<String> researchInterests,
            final List<Education> educations,
            final List<Publication> publications,
            final Integer hIndex,
            final Integer totalCitations) {
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
        this.researchInterests = new ArrayList<>(researchInterests);
        this.educations = new ArrayList<>(educations);
        this.publications = new ArrayList<>(publications);
        this.hIndex = hIndex;
        this.totalCitations = totalCitations;
    }

    /** @return the edited email address */
    public String getEmail() {
        return this.email;
    }

    /** @return the edited phone number */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /** @return the edited institution */
    public Institution getInstitution() {
        return this.institution;
    }

    /** @return the edited academic level */
    public AcademicLevel getAcademicLevel() {
        return this.academicLevel;
    }

    /** @return the edited research field */
    public ResearchField getResearchField() {
        return this.researchField;
    }

    /** @return the edited collaboration preference */
    public CollaborationType getLookingFor() {
        return this.lookingFor;
    }

    /** @return the edited collaborator description */
    public String getCollaborationDescription() {
        return this.collaborationDescription;
    }

    /** @return the edited research description */
    public String getResearchDescription() {
        return this.researchDescription;
    }

    /** @return the edited weekly availability */
    public Integer getWeeklyAvailabilityHours() {
        return this.weeklyAvailabilityHours;
    }

    /** @return the edited funding status */
    public FundingStatus getFundingStatus() {
        return this.fundingStatus;
    }

    /** @return a copy of the edited research interests */
    public List<String> getResearchInterests() {
        return new ArrayList<>(this.researchInterests);
    }

    /** @return a copy of the edited education history */
    public List<Education> getEducations() {
        return new ArrayList<>(this.educations);
    }

    /** @return a copy of the edited publication list */
    public List<Publication> getPublications() {
        return new ArrayList<>(this.publications);
    }

    /** @return the edited h-index, or null when unknown */
    public Integer getHIndex() {
        return this.hIndex;
    }

    /** @return the edited citation total, or null when unknown */
    public Integer getTotalCitations() {
        return this.totalCitations;
    }
}
