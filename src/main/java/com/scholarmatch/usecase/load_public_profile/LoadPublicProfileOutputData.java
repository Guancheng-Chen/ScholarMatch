package com.scholarmatch.usecase.load_public_profile;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;

import java.util.List;

public record LoadPublicProfileOutputData(
        String userId,
        String firstName,
        String lastName,
        Institution institution,
        AcademicLevel academicLevel,
        ResearchField researchField,
        CollaborationType lookingFor,
        String collaborationDescription,
        String researchDescription,
        Integer weeklyAvailabilityHours,
        FundingStatus fundingStatus,
        List<String> researchInterests,
        List<Education> educations,
        List<Publication> publications,
        Integer hIndex,
        Integer totalCitations,
        boolean academicEmailVerified) {

    public LoadPublicProfileOutputData {
        userId = safe(userId);
        firstName = safe(firstName);
        lastName = safe(lastName);
        collaborationDescription = safe(collaborationDescription);
        researchDescription = safe(researchDescription);
        researchInterests = List.copyOf(researchInterests);
        educations = List.copyOf(educations);
        publications = List.copyOf(publications);
    }

    public static LoadPublicProfileOutputData from(final User user) {
        return new LoadPublicProfileOutputData(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
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
                user.getTotalCitations(),
                user.getEmailAccountType() == EmailAccountType.ACADEMIC);
    }

    private static String safe(final String value) {
        return value == null ? "" : value;
    }
}
