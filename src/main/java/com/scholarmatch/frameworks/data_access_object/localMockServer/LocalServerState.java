package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Shared in-memory data for every {@code Local*Repository} class, plus the handful of lookups
 * (find-by-email, mutual-connection check) that span more than one feature domain.
 *
 * <p>Split out of what used to be one 746-line {@code LocalServerRepository} implementing 22
 * data access interfaces at once. Several offline operations — most notably deleting an account —
 * legitimately need to touch users, connections, dislikes, messages, postings, and applications
 * together, so that shared state lives here once and each feature-scoped repository is
 * constructed with a reference to it, rather than every feature reimplementing its own copy.
 */
public final class LocalServerState {

    private final Map<String, User> usersById = new LinkedHashMap<>();
    private final Set<String> seedUserIds = new HashSet<>();
    private final Set<String> recordedConnections = new HashSet<>();
    private final Set<String> recordedDislikes = new HashSet<>();
    private final List<Message> messages = new ArrayList<>();
    private final Map<String, Posting> postingsById = new LinkedHashMap<>();
    private final Map<String, PostingApplication> applicationsById = new LinkedHashMap<>();

    public LocalServerState(final InstitutionCatalogDataAccessInterface institutionCatalog) {
        seedDemoUsers(institutionCatalog);
    }

    Map<String, User> usersById() {
        return this.usersById;
    }

    Set<String> seedUserIds() {
        return this.seedUserIds;
    }

    Set<String> recordedConnections() {
        return this.recordedConnections;
    }

    Set<String> recordedDislikes() {
        return this.recordedDislikes;
    }

    List<Message> messages() {
        return this.messages;
    }

    Map<String, Posting> postingsById() {
        return this.postingsById;
    }

    Map<String, PostingApplication> applicationsById() {
        return this.applicationsById;
    }

    User findByEmail(final String email) {
        for (final User user : this.usersById.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    String displayName(final String userId) {
        final User user = this.usersById.get(userId);
        return user == null ? "" : user.getFullName();
    }

    boolean hasMutualConnection(
            final String firstUserId,
            final String secondUserId) {
        return this.recordedConnections.contains(firstUserId + "->" + secondUserId)
                && this.recordedConnections.contains(secondUserId + "->" + firstUserId)
                && !this.recordedDislikes.contains(firstUserId + "->" + secondUserId)
                && !this.recordedDislikes.contains(secondUserId + "->" + firstUserId);
    }

    private void seedDemoUsers(final InstitutionCatalogDataAccessInterface institutionCatalog) {
        addSeedUser("Ada", "Lovelace", "ada@demo.local",
                institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
                AcademicLevel.FACULTY, ResearchField.MATHEMATICS, CollaborationType.INTEREST_SHARING,
                "Looking for collaborators interested in the history and theory of computation.",
                "Mathematical foundations of computing and algorithmic analysis.",
                5, FundingStatus.INSTITUTIONAL_FUNDING);
        addSeedUser("Alan", "Turing", "alan@demo.local",
                institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
                AcademicLevel.FACULTY, ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                "Interested in co-authoring on computability and machine intelligence.",
                "Computability theory, cryptography, and artificial intelligence.",
                10, FundingStatus.GOVERNMENT_GRANT);
        addSeedUser("Grace", "Hopper", "grace@demo.local",
                institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
                AcademicLevel.INDUSTRY_RESEARCHER, ResearchField.SOFTWARE_ENGINEERING, CollaborationType.MENTORSHIP,
                "Open to mentoring and joint work on programming language design.",
                "Compilers, programming languages, and software engineering practice.",
                8, FundingStatus.INDUSTRY_SPONSORED);
        addSeedUser("Demo", "Student", "demo.student@utoronto.ca",
                institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
                AcademicLevel.UNDERGRADUATE, ResearchField.COMPUTER_SCIENCE,
                CollaborationType.RESEARCH_GROUP,
                "Looking for University of Toronto classmates to build course projects.",
                "Interested in software engineering and collaborative student projects.",
                6, FundingStatus.OTHER, "12345678", EmailAccountType.ACADEMIC);
        final String posterId = this.usersById.values().iterator().next().getUserId();
        final User poster = this.usersById.get(posterId);
        final Posting posting = new Posting(
                UUID.randomUUID().toString(), posterId,
                poster.getFullName(),
                poster.getEmailAccountType() == EmailAccountType.ACADEMIC,
                "Foundations of trustworthy computing",
                "Seeking collaborators for a short research project on reliable computation.",
                ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                4, 0, 0, PostingStatus.OPEN, LocalDateTime.now());
        this.postingsById.put(posting.getPostingId(), posting);
    }

    private void addSeedUser(
            final String firstName,
            final String lastName,
            final String email,
            final Institution institution,
            final AcademicLevel academicLevel,
            final ResearchField researchField,
            final CollaborationType lookingFor,
            final String collaborationDescription,
            final String researchDescription,
            final Integer weeklyAvailabilityHours,
            final FundingStatus fundingStatus) {
        final User user = new User(
                UUID.randomUUID().toString(), firstName, lastName, email, "",
                institution, academicLevel, researchField, lookingFor, collaborationDescription,
                researchDescription, weeklyAvailabilityHours, fundingStatus, "12345678");
        this.usersById.put(user.getUserId(), user);
        this.seedUserIds.add(user.getUserId());
    }

    private void addSeedUser(
            final String firstName,
            final String lastName,
            final String email,
            final Institution institution,
            final AcademicLevel academicLevel,
            final ResearchField researchField,
            final CollaborationType lookingFor,
            final String collaborationDescription,
            final String researchDescription,
            final Integer weeklyAvailabilityHours,
            final FundingStatus fundingStatus,
            final String password,
            final EmailAccountType emailAccountType) {
        final User user = new User(
                UUID.randomUUID().toString(), firstName, lastName, email, "",
                institution, academicLevel, researchField, lookingFor, collaborationDescription,
                researchDescription, weeklyAvailabilityHours, fundingStatus, password,
                emailAccountType);
        this.usersById.put(user.getUserId(), user);
        this.seedUserIds.add(user.getUserId());
    }
}
