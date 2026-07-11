package com.scholarmatch.DTO;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserData  {
    private final String userId;
    private final String firstName;
    private final String lastName;
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
     * Constructs a UserData snapshot.
     *
     * @param userId                   the unique identifier assigned by the system
     * @param firstName                the user's given name
     * @param lastName                 the user's family name
     * @param email                    the user's email address
     * @param phoneNumber              the user's phone number
     * @param institution              the university or research institution
     * @param academicLevel            the user's career stage
     * @param researchField            the broad discipline of the user's research
     * @param lookingFor               the type of collaboration this user is seeking
     * @param collaborationDescription freeform text describing the ideal collaborator
     * @param researchDescription     freeform text describing research domain
     * @param weeklyAvailabilityHours  hours per week the user can commit to collaboration
     * @param fundingStatus            how the user's research is currently funded
     * @param researchInterests        the user's research interest keywords
     * @param educations               the user's education history
     * @param publications             the user's publications
     * @param hIndex                   the h-index, or null if unknown
     * @param totalCitations           the total citation count, or null if unknown
     */
    public UserData(
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
            final List<String> researchInterests,
            final List<Education> educations,
            final List<Publication> publications,
            final Integer hIndex,
            final Integer totalCitations) {
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
        this.researchInterests = new ArrayList<>(researchInterests);
        this.educations = new ArrayList<>(educations);
        this.publications = new ArrayList<>(publications);
        this.hIndex = hIndex;
        this.totalCitations = totalCitations;
    }
}
