package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.register.RegisterInputData;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * In-memory offline implementation of all server-facing data access interfaces.
 *
 * <p>Demo-day safety net for when the live scholarmatch-server deployment is
 * unreachable. Seeded with a handful of demo users so recommendations and matching have
 * something to show; data lives only for the duration of the process and is never written to
 * disk. Passwords are stored as plain text — this class exists purely to keep the app
 * demonstrable when offline, and is never a substitute for the real server's BCrypt hashing.
 *
 * <p>Connecting with a seeded demo user always reports a mutual match, so the connect/match
 * flow can be demoed on demand without needing a second real account. Connecting with another
 * locally-registered account requires a real reciprocal connect, same as the live server.
 */
public final class LocalServerRepository
    implements LoginDataAccessInterface,
               RegisterDataAccessInterface,
               RecommendDataAccessInterface,
               ConnectDataAccessInterface,
               DislikeDataAccessInterface,
               LoadMatchesDataAccessInterface,
               LoadProfileDataAccessInterface,
               UpdateProfileDataAccessInterface,
               DeleteAccountDataAccessInterface,
               SendMessageDataAccessInterface,
               LoadMessageDataAccessInterface {

    private final Map<String, User> usersById = new LinkedHashMap<>();
    private final Set<String> seedUserIds = new HashSet<>();
    private final Set<String> recordedConnections = new HashSet<>();
    private final Set<String> recordedDislikes = new HashSet<>();
    private final List<Message> messages = new ArrayList<>();
    private final CurrentUserProviderInterface session;

    /**
     * Constructs a LocalServerRepository pre-seeded with a few demo users.
     *
     * @param session provides the ID of the currently authenticated user
     */
    public LocalServerRepository(final CurrentUserProviderInterface session) {
        this.session = session;
        seedDemoUsers();
    }

    // ── Auth ─────────────────────────────────────────────────────────────────

    @Override
    public AuthResult login(final String email, final String password) {
        final User user = findByEmail(email);
        if (user == null || !user.getPasswordHash().equals(password)) {
            throw new InvalidRequestException("Invalid email or password");
        }
        return toAuthResult(user);
    }

    @Override
    public AuthResult register(final RegisterInputData data) {
        if (findByEmail(data.getEmail()) != null) {
            throw new InvalidRequestException("Email is already registered");
        }
        // Registration only collects the account-creation essentials; every other profile
        // field starts blank/null and is filled in later from the Edit Profile screen —
        // see User#isProfileComplete(), which the recommend use case gates on.
        final User user = new User(
            UUID.randomUUID().toString(),
            data.getFirstName(),
            data.getLastName(),
            data.getEmail(),
            "",
            null,
            null,
            null,
            null,
            "",
            "",
            null,
            null,
            data.getPassword());
        this.usersById.put(user.getUserId(), user);
        return toAuthResult(user);
    }

    // ── Recommend ─────────────────────────────────────────────────────────────

    @Override
    public List<User> getRecommendations() {
        final String currentId = this.session.getCurrentUserId();
        final List<User> recommendations = new ArrayList<>();
        for (final User user : this.usersById.values()) {
            final String otherId = user.getUserId();
            final boolean alreadyDisliked = this.recordedDislikes.contains(currentId + "->" + otherId);
            if (!otherId.equals(currentId) && !alreadyDisliked) {
                recommendations.add(user);
            }
        }
        return recommendations;
    }

    // ── Connect ─────────────────────────────────────────────────────────────────

    @Override
    public boolean connect(final String connectedUserId) {
        final String currentId = this.session.getCurrentUserId();
        if (this.seedUserIds.contains(connectedUserId)) {
            // Seed users always "connect back" instantly — record both directions so
            // this match also shows up later via getMatches(), not just in this moment.
            this.recordedConnections.add(currentId + "->" + connectedUserId);
            this.recordedConnections.add(connectedUserId + "->" + currentId);
            return true;
        }
        this.recordedConnections.add(currentId + "->" + connectedUserId);
        return this.recordedConnections.contains(connectedUserId + "->" + currentId);
    }

    @Override
    public void dislike(final String dislikedUserId) {
        final String currentId = this.session.getCurrentUserId();
        this.recordedDislikes.add(currentId + "->" + dislikedUserId);
    }

    @Override
    public List<User> getMatches() {
        final String currentId = this.session.getCurrentUserId();
        final List<User> matches = new ArrayList<>();
        for (final User user : this.usersById.values()) {
            final String otherId = user.getUserId();
            final boolean mutualMatch = !otherId.equals(currentId)
                && this.recordedConnections.contains(currentId + "->" + otherId)
                && this.recordedConnections.contains(otherId + "->" + currentId);
            if (mutualMatch) {
                matches.add(user);
            }
        }
        return matches;
    }

    // ── Profile ───────────────────────────────────────────────────────────────

    @Override
    public User getProfile() {
        final User user = this.usersById.get(this.session.getCurrentUserId());
        if (user == null) {
            throw new ResourceNotFoundException("No profile found for the current user");
        }
        return user;
    }

    @Override
    public User updateProfile(final UpdateProfileInputData data) {
        final User user = getProfile();
        if (data.getEmail() != null && !data.getEmail().equalsIgnoreCase(user.getEmail())) {
            final User existing = findByEmail(data.getEmail());
            if (existing != null && !existing.getUserId().equals(user.getUserId())) {
                throw new InvalidRequestException("Email is already registered");
            }
            user.setEmail(data.getEmail());
        }
        if (data.getInstitution() != null) {
            user.setInstitution(parseInstitution(data.getInstitution()));
        }
        if (data.getAcademicLevel() != null) {
            user.setAcademicLevel(parseAcademicLevel(data.getAcademicLevel()));
        }
        if (data.getResearchField() != null) {
            user.setResearchField(parseResearchField(data.getResearchField()));
        }
        if (data.getLookingFor() != null) {
            user.setLookingFor(parseCollaborationType(data.getLookingFor()));
        }
        if (data.getCollaborationDescription() != null) {
            user.setCollaborationDescription(data.getCollaborationDescription());
        }
        if (data.getResearchDescription() != null) {
            user.setResearchDescription(data.getResearchDescription());
        }
        if (data.getWeeklyAvailabilityHours() != null) {
            user.setWeeklyAvailabilityHours(data.getWeeklyAvailabilityHours());
        }
        if (data.getFundingStatus() != null) {
            user.setFundingStatus(parseFundingStatus(data.getFundingStatus()));
        }
        if (data.getPhoneNumber() != null) {
            user.setPhoneNumber(data.getPhoneNumber());
        }
        if (data.getHIndex() != null) {
            user.sethIndex(data.getHIndex());
        }
        if (data.getTotalCitations() != null) {
            user.setTotalCitations(data.getTotalCitations());
        }
        for (final String interest : user.getResearchInterests()) {
            user.removeResearchInterest(interest);
        }
        for (final String interest : data.getResearchInterests()) {
            user.addResearchInterest(interest);
        }
        return user;
    }

    @Override
    public void deleteAccount() {
        final String currentId = this.session.getCurrentUserId();
        this.usersById.remove(currentId);
        this.recordedConnections.removeIf(key -> key.contains(currentId));
        this.recordedDislikes.removeIf(key -> key.contains(currentId));
        this.messages.removeIf(message ->
            message.getSenderId().equals(currentId) || message.getReceiverId().equals(currentId));
    }

    // ── Messages ─────────────────────────────────────────────────────────────

    @Override
    public Message sendMessage(final String receiverId, final String content) {
        final String currentId = this.session.getCurrentUserId();
        final boolean mutualMatch = this.recordedConnections.contains(currentId + "->" + receiverId)
            && this.recordedConnections.contains(receiverId + "->" + currentId);
        if (!mutualMatch) {
            throw new InvalidRequestException("You can only message users you have matched with");
        }
        final Message message = new Message(
            UUID.randomUUID().toString(), currentId, receiverId, content, LocalDateTime.now());
        this.messages.add(message);
        return message;
    }

    @Override
    public List<Message> getConversation(final String otherUserId) {
        final String currentId = this.session.getCurrentUserId();
        final List<Message> conversation = new ArrayList<>();
        for (final Message message : this.messages) {
            final boolean betweenTheseTwo =
                (message.getSenderId().equals(currentId) && message.getReceiverId().equals(otherUserId))
                || (message.getSenderId().equals(otherUserId) && message.getReceiverId().equals(currentId));
            if (betweenTheseTwo) {
                conversation.add(message);
            }
        }
        return conversation;
    }

    // ── Seed data ────────────────────────────────────────────────────────────

    private void seedDemoUsers() {
        addSeedUser("Ada", "Lovelace", "ada@demo.local", Institution.UNIVERSITY_OF_TORONTO,
            AcademicLevel.FACULTY, ResearchField.MATHEMATICS, CollaborationType.INTEREST_SHARING,
            "Looking for collaborators interested in the history and theory of computation.",
            "Mathematical foundations of computing and algorithmic analysis.",
            5, FundingStatus.INSTITUTIONAL_FUNDING);
        addSeedUser("Alan", "Turing", "alan@demo.local", Institution.UNIVERSITY_OF_TORONTO,
            AcademicLevel.FACULTY, ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
            "Interested in co-authoring on computability and machine intelligence.",
            "Computability theory, cryptography, and artificial intelligence.",
            10, FundingStatus.GOVERNMENT_GRANT);
        addSeedUser("Grace", "Hopper", "grace@demo.local", Institution.UNIVERSITY_OF_TORONTO,
            AcademicLevel.INDUSTRY_RESEARCHER, ResearchField.SOFTWARE_ENGINEERING, CollaborationType.MENTORSHIP,
            "Open to mentoring and joint work on programming language design.",
            "Compilers, programming languages, and software engineering practice.",
            8, FundingStatus.INDUSTRY_SPONSORED);
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
            researchDescription, weeklyAvailabilityHours, fundingStatus, "");
        this.usersById.put(user.getUserId(), user);
        this.seedUserIds.add(user.getUserId());
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private User findByEmail(final String email) {
        for (final User user : this.usersById.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    private AuthResult toAuthResult(final User user) {
        return new AuthResult(
            "local-token-" + user.getUserId(),
            user.getUserId(),
            user.getFullName());
    }

    private AcademicLevel parseAcademicLevel(final String value) {
        try {
            return AcademicLevel.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return AcademicLevel.UNDERGRADUATE;
        }
    }

    private CollaborationType parseCollaborationType(final String value) {
        try {
            return CollaborationType.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return CollaborationType.INTEREST_SHARING;
        }
    }

    private ResearchField parseResearchField(final String value) {
        try {
            return ResearchField.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return ResearchField.OTHER;
        }
    }

    private FundingStatus parseFundingStatus(final String value) {
        try {
            return FundingStatus.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return FundingStatus.OTHER;
        }
    }

    private Institution parseInstitution(final String value) {
        try {
            return Institution.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return Institution.OTHER;
        }
    }
}
