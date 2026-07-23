package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP implementation of loading, editing, and deleting the current user's own profile — the
 * "profile" actor's slice of what used to be ServerRepository.
 */
public final class ProfileGateway
        implements LoadProfileDataAccessInterface, UpdateProfileDataAccessInterface, DeleteAccountDataAccessInterface {

    private final ServerHttpClient http;
    private final ScholarUserMapper userMapper;

    public ProfileGateway(final ServerHttpClient http, final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this.http = http;
        this.userMapper = new ScholarUserMapper(institutionCatalog);
    }

    @Override
    public User getProfile() {
        return this.userMapper.fromJson(this.http.get("/api/profile"));
    }

    @Override
    public User updateProfile(final UpdateProfileInputData data) {
        final Map<String, Object> body = new HashMap<>();
        if (data.getEmail() != null) {
            body.put("email", data.getEmail());
        }
        if (data.getInstitution() != null) {
            body.put("institution", data.getInstitution());
        }
        if (data.getAcademicLevel() != null) {
            body.put("academicLevel", data.getAcademicLevel());
        }
        if (data.getResearchField() != null) {
            body.put("researchField", data.getResearchField());
        }
        if (data.getLookingFor() != null) {
            body.put("lookingFor", data.getLookingFor());
        }
        if (data.getCollaborationDescription() != null) {
            body.put("collaborationDescription", data.getCollaborationDescription());
        }
        if (data.getResearchDescription() != null) {
            body.put("researchDescription", data.getResearchDescription());
        }
        if (data.getWeeklyAvailabilityHours() != null) {
            body.put("weeklyAvailabilityHours", data.getWeeklyAvailabilityHours());
        }
        if (data.getFundingStatus() != null) {
            body.put("fundingStatus", data.getFundingStatus());
        }
        if (data.getPhoneNumber() != null) {
            body.put("phoneNumber", data.getPhoneNumber());
        }
        if (data.getResearchInterests() != null) {
            body.put("researchInterests", data.getResearchInterests());
        }
        if (data.getHIndex() != null) {
            body.put("hIndex", data.getHIndex());
        }
        if (data.getTotalCitations() != null) {
            body.put("totalCitations", data.getTotalCitations());
        }

        final List<Map<String, String>> papers = new ArrayList<>();
        for (final Publication p : data.getPublications()) {
            papers.add(Map.of("title", p.getTitle(), "doi", p.getDoi()));
        }
        body.put("papers", papers);

        final List<Map<String, String>> educations = new ArrayList<>();
        for (final Education ed : data.getEducations()) {
            educations.add(Map.of(
                    "school", ed.getInstitution(),
                    "degree", ed.getDegreeType().name(),
                    "field", ""));
        }
        body.put("educations", educations);

        return this.userMapper.fromJson(this.http.put("/api/profile", this.http.toJson(body)));
    }

    @Override
    public void deleteAccount() {
        this.http.delete("/api/profile");
    }
}
