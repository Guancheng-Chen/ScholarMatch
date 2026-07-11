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

/**
 * Plain, read-only snapshot of a User's profile for use in Input/OutputData DTOs and
 * everything downstream of them (presenters, view models, views).
 *
 * <p>User is an Entity that carries setters and business rules (e.g.
 * isProfileComplete()); those only make sense inside the use case layer. Once a
 * user's profile needs to cross an output boundary purely for display (connect, recommend,
 * load-matches, load-profile), it's converted to this DTO via #from(User) instead of
 * being passed through directly. The password hash is intentionally omitted — display code
 * never needs it.
 */
public final class UserData  {
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

    /**
     * Builds a UserData snapshot from a User entity.
     *
     * @param user the entity to snapshot
     * @return the equivalent read-only DTO
     */
    public static UserData from(final User user) {
        return new UserData(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getInstitution(),
                user.getAcademicLevel(),
                user.getResearchField(),
                user.getLookingFor(),
                user.getCollaborationDescription(),
                user.getResearchDescription(),
                user.getWeeklyAvailabilityHours(),
                user.getFundingStatus(),
                user.getResearchInterests(),
                user.getEducations(),
                user.getPublications(),
                user.gethIndex(),
                user.getTotalCitations());
    }

    /**
     * Builds a list of UserData snapshots from a list of User entities.
     *
     * @param users the entities to snapshot
     * @return the equivalent read-only DTOs, in the same order
     */
    public static List<UserData> fromAll(final List<User> users) {
        return users.stream().map(UserData::from).collect(Collectors.toList());
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
     * Returns the user's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Returns the institution of this user.
     *
     * @return the Institution
     */
    public Institution getInstitution() {
        return this.institution;
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
     * Returns the broad research discipline of this user.
     *
     * @return the ResearchField
     */
    public ResearchField getResearchField() {
        return this.researchField;
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
     * Returns the freeform text describing the ideal collaborator.
     *
     * @return the collaboration description
     */
    public String getCollaborationDescription() {
        return this.collaborationDescription;
    }

    /**
     * Returns the freeform research domain description.
     *
     * @return the research description
     */
    public String getResearchDescription() {
        return this.researchDescription;
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
     * Returns how this user's research is currently funded.
     *
     * @return the FundingStatus
     */
    public FundingStatus getFundingStatus() {
        return this.fundingStatus;
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
     * Returns a copy of the education history list.
     *
     * @return the list of education entries
     */
    public List<Education> getEducations() {
        return new ArrayList<>(this.educations);
    }

    /**
     * Returns a copy of the publication list.
     *
     * @return the list of publications
     */
    public List<Publication> getPublications() {
        return new ArrayList<>(this.publications);
    }

    /**
     * Returns the h-index of this user.
     *
     * @return the h-index, or null if unknown
     */
    public Integer gethIndex() {
        return this.hIndex;
    }

    /**
     * Returns the total citation count across all publications.
     *
     * @return the total citation count, or null if unknown
     */
    public Integer getTotalCitations() {
        return this.totalCitations;
    }
}
