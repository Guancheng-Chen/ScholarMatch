package com.scholarmatch.frameworks.data_access_object.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;

import java.time.Month;

/**
 * Maps a ScholarDto JSON response to a User entity. Shared by ProfileGateway (getProfile,
 * updateProfile) and MatchingGateway (getRecommendations, getMatches) — the only two features
 * that actually need a full User, unlike AuthGateway which only ever sees token/scholarId/name.
 */
final class ScholarUserMapper {

    private final InstitutionCatalogDataAccessInterface institutionCatalog;

    ScholarUserMapper(final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this.institutionCatalog = institutionCatalog;
    }

    User fromJson(final JsonNode node) {
        final AcademicLevel academicLevel = JsonEnumSupport.safeParseEnum(
                AcademicLevel.class,
                node.has("academicLevel") ? node.get("academicLevel").asText(null) : null,
                AcademicLevel.GRADUATE_STUDENT);

        final CollaborationType lookingFor = JsonEnumSupport.safeParseEnum(
                CollaborationType.class,
                node.has("lookingFor") ? node.get("lookingFor").asText(null) : null,
                CollaborationType.INTEREST_SHARING);

        final ResearchField researchField = JsonEnumSupport.safeParseEnum(
                ResearchField.class,
                node.has("researchField") ? node.get("researchField").asText(null) : null,
                ResearchField.OTHER);

        final FundingStatus fundingStatus = JsonEnumSupport.safeParseEnum(
                FundingStatus.class,
                node.has("fundingStatus") ? node.get("fundingStatus").asText(null) : null,
                FundingStatus.OTHER);

        final Integer weeklyAvailabilityHours =
                node.has("weeklyAvailabilityHours") && !node.get("weeklyAvailabilityHours").isNull()
                        ? node.get("weeklyAvailabilityHours").asInt()
                        : null;

        final Institution institution = this.institutionCatalog.findById(
                node.has("institution") ? node.get("institution").asText(null) : null);

        final String email = node.has("email") ? node.get("email").asText("") : "";
        final boolean academicEmailVerified = node.has("academicEmailVerified")
                ? node.get("academicEmailVerified").asBoolean()
                : false;

        final User user = new User(
                node.get("scholarId").asText(),
                node.get("firstName").asText(),
                node.get("lastName").asText(),
                email,
                node.has("phoneNumber") ? node.get("phoneNumber").asText("") : "",
                institution,
                academicLevel,
                researchField,
                lookingFor,
                node.has("collaborationDescription") ? node.get("collaborationDescription").asText("") : "",
                node.has("researchDescription") ? node.get("researchDescription").asText("") : "",
                weeklyAvailabilityHours,
                fundingStatus,
                "",
                academicEmailVerified ? EmailAccountType.ACADEMIC : EmailAccountType.REGULAR);

        if (node.has("hIndex") && !node.get("hIndex").isNull()) {
            user.sethIndex(node.get("hIndex").asInt());
        }
        if (node.has("totalCitations") && !node.get("totalCitations").isNull()) {
            user.setTotalCitations(node.get("totalCitations").asInt());
        }

        if (node.has("researchInterests")) {
            for (final JsonNode interest : node.get("researchInterests")) {
                user.addResearchInterest(interest.asText());
            }
        }

        if (node.has("papers")) {
            for (final JsonNode paper : node.get("papers")) {
                user.addPublication(new Publication(
                        paper.has("doi") ? paper.get("doi").asText("") : "",
                        paper.has("title") ? paper.get("title").asText("") : "",
                        0, 0));
            }
        }

        if (node.has("educations")) {
            for (final JsonNode ed : node.get("educations")) {
                final DegreeType degree = JsonEnumSupport.safeParseEnum(
                        DegreeType.class,
                        ed.has("degree") ? ed.get("degree").asText(null) : null,
                        DegreeType.BACHELOR);
                // Server doesn't send year/month fields yet; placeholder until that's wired up.
                user.addEducation(new Education(
                        ed.has("school") ? ed.get("school").asText("") : "",
                        degree, 0, Month.JANUARY, null, null));
            }
        }

        return user;
    }
}
