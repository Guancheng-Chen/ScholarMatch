package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.EmailVerificationChallenge;
import com.scholarmatch.entity.EmailVerificationOutcome;
import com.scholarmatch.entity.EmailVerificationResult;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Message;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AcademicEmailDomainDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.ChangeEmailDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ChangePasswordDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.EmailChangeCodeDeliveryDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.EmailVerificationChallengeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationCodeGeneratorInterface;
import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CreatePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadPostingsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ApplyToPostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.AcceptApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DeclineApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMyApplicationsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ClosePostingDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.register.RegisterAccountData;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;
import com.scholarmatch.usecase.load_postings.PostingScope;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
        VerificationEmailSenderDataAccessInterface,
        ChangeEmailDataAccessInterface,
        ChangePasswordDataAccessInterface,
        DeleteAccountDataAccessInterface,
        SendMessageDataAccessInterface,
        LoadMessageDataAccessInterface,
        CreatePostingDataAccessInterface,
        LoadPostingsDataAccessInterface,
        ApplyToPostingDataAccessInterface,
        AcceptApplicationDataAccessInterface,
        DeclineApplicationDataAccessInterface,
        LoadMyApplicationsDataAccessInterface,
        ClosePostingDataAccessInterface {

    private final Map<String, User> usersById = new LinkedHashMap<>();
    private final Set<String> seedUserIds = new HashSet<>();
    private final Set<String> recordedConnections = new HashSet<>();
    private final Set<String> recordedDislikes = new HashSet<>();
    private final List<Message> messages = new ArrayList<>();
    private final Map<String, Posting> postingsById = new LinkedHashMap<>();
    private final Map<String, PostingApplication> applicationsById = new LinkedHashMap<>();
    private final CurrentUserProviderInterface session;
    private final InstitutionCatalogDataAccessInterface institutionCatalog;
    private final EmailVerificationChallengeDataAccessInterface emailChallenges;
    private final VerificationCodeGeneratorInterface codeGenerator;
    private final EmailChangeCodeDeliveryDataAccessInterface codeDelivery;
    private final AcademicEmailDomainDataAccessInterface academicEmailDomains;
    private final Clock clock;

    /**
     * Constructs a LocalServerRepository pre-seeded with a few demo users.
     *
     * @param session provides the ID of the currently authenticated user
     */
    public LocalServerRepository(
            final CurrentUserProviderInterface session) {
        this(session, new ClasspathInstitutionCatalogRepository());
    }

    public LocalServerRepository(
            final CurrentUserProviderInterface session,
            final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this(
                session,
                institutionCatalog,
                new InMemoryEmailVerificationChallengeRepository(),
                new SecureVerificationCodeGenerator(),
                (email, code) -> { },
                new ClasspathAcademicEmailDomainRepository(),
                Clock.systemUTC());
    }

    public LocalServerRepository(
            final CurrentUserProviderInterface session,
            final InstitutionCatalogDataAccessInterface institutionCatalog,
            final EmailVerificationChallengeDataAccessInterface emailChallenges,
            final VerificationCodeGeneratorInterface codeGenerator,
            final EmailChangeCodeDeliveryDataAccessInterface codeDelivery,
            final AcademicEmailDomainDataAccessInterface academicEmailDomains,
            final Clock clock) {
        this.session = session;
        this.institutionCatalog = institutionCatalog;
        this.emailChallenges = emailChallenges;
        this.codeGenerator = codeGenerator;
        this.codeDelivery = codeDelivery;
        this.academicEmailDomains = academicEmailDomains;
        this.clock = clock;
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
    public AuthResult register(final RegisterAccountData data) {
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
                data.getPassword(),
                // Offline mode has no server to check the verification code against, so it can't
                // legitimately claim ACADEMIC status either — that's the server's call to make.
                EmailAccountType.REGULAR);
        this.usersById.put(user.getUserId(), user);
        return toAuthResult(user);
    }

    @Override
    public synchronized Posting createPosting(
            final String title,
            final String description,
            final ResearchField researchField,
            final CollaborationType collaborationType,
            final Integer capacity) {
        if (capacity != null && capacity <= 0) {
            throw new InvalidRequestException("Team capacity must be greater than zero");
        }
        final String currentUserId = this.session.getCurrentUserId();
        final User poster = this.usersById.get(currentUserId);
        final String posterName = poster == null ? currentUserId : poster.getFullName();
        final boolean academicEmailVerified = poster != null
                && poster.getEmailAccountType() == EmailAccountType.ACADEMIC;
        final Posting posting = new Posting(
                UUID.randomUUID().toString(), currentUserId,
                posterName,
                academicEmailVerified,
                title, description,
                researchField, collaborationType, capacity, 0, 0,
                PostingStatus.OPEN, LocalDateTime.now());
        this.postingsById.put(posting.getPostingId(), posting);
        return posting;
    }

    @Override
    public synchronized Posting closePosting(final String postingId) {
        final Posting posting = this.postingsById.get(postingId);
        if (posting == null) {
            throw new InvalidRequestException("Posting not found");
        }
        if (!posting.getPosterUserId().equals(this.session.getCurrentUserId())) {
            throw new InvalidRequestException("You are not the poster of this posting");
        }
        if (posting.getStatus() == PostingStatus.CLOSED) {
            throw new InvalidRequestException("Posting is already closed");
        }
        posting.close();
        return posting;
    }

    @Override
    public synchronized List<Posting> loadPostings(final PostingScope scope) {
        final String currentId = this.session.getCurrentUserId();
        final List<Posting> result = new ArrayList<>();
        for (final Posting posting : this.postingsById.values()) {
            final boolean mine = posting.getPosterUserId().equals(currentId);
            if (scope == PostingScope.MINE ? mine : !mine && posting.isActive()) {
                result.add(posting);
            }
        }
        return result;
    }

    @Override
    public synchronized Map<String, List<PostingApplication>> loadApplicationsForOwnedPostings(
            final PostingScope scope,
            final List<Posting> postings) {
        if (scope != PostingScope.MINE) {
            return Map.of();
        }
        final String currentId = this.session.getCurrentUserId();
        final Map<String, List<PostingApplication>> result = new LinkedHashMap<>();
        for (final Posting posting : postings) {
            if (!posting.getPosterUserId().equals(currentId)) {
                continue;
            }
            final List<PostingApplication> applications = this.applicationsById.values().stream()
                    .filter(application -> application.getPostingId().equals(posting.getPostingId()))
                    .toList();
            result.put(posting.getPostingId(), applications);
        }
        return result;
    }

    @Override
    public synchronized PostingApplication applyToPosting(
            final String postingId,
            final String message) {
        final Posting posting = this.postingsById.get(postingId);
        if (posting == null) {
            throw new InvalidRequestException("Posting not found");
        }
        final String currentId = this.session.getCurrentUserId();
        if (posting.getPosterUserId().equals(currentId)) {
            throw new InvalidRequestException("You cannot apply to your own posting");
        }
        if (!posting.isActive()) {
            throw new InvalidRequestException(
                    posting.isFull() ? "This posting is full" : "This posting is closed");
        }
        final boolean duplicate = this.applicationsById.values().stream().anyMatch(application ->
                application.getPostingId().equals(postingId)
                        && application.getApplicantUserId().equals(currentId));
        if (duplicate) {
            throw new InvalidRequestException("You have already applied to this posting");
        }
        final PostingApplication application = new PostingApplication(
                UUID.randomUUID().toString(), postingId, currentId,
                message == null ? "" : message, PostingApplicationStatus.PENDING,
                LocalDateTime.now(), posting.getTitle(), displayName(currentId),
                posting.getPosterUserId(),
                posting.getPosterName(),
                posting.isPosterAcademicEmailVerified());
        this.applicationsById.put(application.getApplicationId(), application);
        posting.setApplicantCount(posting.getApplicantCount() + 1);
        return application;
    }

    @Override
    public synchronized PostingApplication acceptApplication(final String applicationId) {
        return reviewApplication(applicationId, PostingApplicationStatus.ACCEPTED);
    }

    @Override
    public synchronized PostingApplication declineApplication(final String applicationId) {
        return reviewApplication(applicationId, PostingApplicationStatus.REJECTED);
    }

    @Override
    public synchronized List<PostingApplication> getMyApplications() {
        final String currentId = this.session.getCurrentUserId();
        return this.applicationsById.values().stream()
                .filter(application -> application.getApplicantUserId().equals(currentId))
                .toList();
    }

    private PostingApplication reviewApplication(
            final String applicationId,
            final PostingApplicationStatus newStatus) {
        final PostingApplication application = this.applicationsById.get(applicationId);
        if (application == null) {
            throw new InvalidRequestException("Application not found");
        }
        final Posting posting = this.postingsById.get(application.getPostingId());
        if (posting == null || !posting.getPosterUserId().equals(this.session.getCurrentUserId())) {
            throw new InvalidRequestException("You are not the poster of this posting");
        }
        if (application.getStatus() != PostingApplicationStatus.PENDING) {
            throw new InvalidRequestException("Application has already been reviewed");
        }
        if (newStatus == PostingApplicationStatus.ACCEPTED && !posting.isActive()) {
            throw new InvalidRequestException(
                    posting.isFull() ? "This posting is full" : "This posting is closed");
        }
        application.setStatus(newStatus);
        if (newStatus == PostingApplicationStatus.ACCEPTED) {
            posting.recordAcceptedApplication();
        }
        return application;
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
        if (this.seedUserIds.contains(connectedUserId)
                && this.recordedConnections.contains(connectedUserId + "->" + currentId)) {
            // Seed users always "connect back" instantly — record both directions so
            // this match also shows up later via getMatches(), not just in this moment.
            this.recordedConnections.add(currentId + "->" + connectedUserId);
            this.recordedConnections.add(connectedUserId + "->" + currentId);
            return true;
        }
        this.recordedConnections.add(currentId + "->" + connectedUserId);
        return hasMutualConnection(currentId, connectedUserId);
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
                    && hasMutualConnection(currentId, otherId);
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
    public void requestVerificationCode(final String email) {
        final String normalizedEmail = normalizeEmail(email);
        final User existing = findByEmail(normalizedEmail);
        if (existing != null
                && !existing.getUserId().equals(this.session.getCurrentUserId())) {
            throw new InvalidRequestException("Email is already registered");
        }
        final String code = this.codeGenerator.generateCode();
        final EmailVerificationChallenge challenge =
                new EmailVerificationChallenge(
                        normalizedEmail,
                        code,
                        Instant.now(this.clock).plus(Duration.ofMinutes(10)));
        this.emailChallenges.save(challenge);
        try {
            this.codeDelivery.sendCode(normalizedEmail, code);
        } catch (final RuntimeException exception) {
            this.emailChallenges.deleteByEmail(normalizedEmail);
            throw exception;
        }
    }

    @Override
    public User changeEmail(
            final String email,
            final String currentPassword,
            final String verificationCode) {
        final User user = getProfile();
        if (!user.getPasswordHash().equals(currentPassword)) {
            throw new InvalidRequestException("Current password is incorrect");
        }
        final String normalizedEmail = normalizeEmail(email);
        final User existing = findByEmail(normalizedEmail);
        if (existing != null && !existing.getUserId().equals(user.getUserId())) {
            throw new InvalidRequestException("Email is already registered");
        }
        final EmailVerificationChallenge challenge = this.emailChallenges
                .findByEmail(normalizedEmail)
                .orElseThrow(() -> new InvalidRequestException(
                        "Request a verification code for this email first"));
        final EmailVerificationResult result =
                challenge.verify(verificationCode, Instant.now(this.clock));
        if (result.outcome() != EmailVerificationOutcome.VERIFIED) {
            throw new InvalidRequestException(verificationFailureMessage(result));
        }
        user.setEmail(normalizedEmail);
        user.setEmailAccountType(
                this.academicEmailDomains.isAcademicEmail(normalizedEmail)
                        ? EmailAccountType.ACADEMIC : EmailAccountType.REGULAR);
        this.emailChallenges.deleteByEmail(normalizedEmail);
        return user;
    }

    @Override
    public void changePassword(
            final String currentPassword,
            final String newPassword) {
        final User user = getProfile();
        if (!user.getPasswordHash().equals(currentPassword)) {
            throw new InvalidRequestException("Current password is incorrect");
        }
        user.setPasswordHash(newPassword);
    }

    @Override
    public void deleteAccount() {
        final String currentId = this.session.getCurrentUserId();
        this.usersById.remove(currentId);
        this.recordedConnections.removeIf(key -> key.contains(currentId));
        this.recordedDislikes.removeIf(key -> key.contains(currentId));
        this.messages.removeIf(message ->
                message.getSenderId().equals(currentId) || message.getReceiverId().equals(currentId));
        final Set<String> removedPostingIds = new HashSet<>();
        this.postingsById.values().removeIf(posting -> {
            final boolean remove = posting.getPosterUserId().equals(currentId);
            if (remove) {
                removedPostingIds.add(posting.getPostingId());
            }
            return remove;
        });
        for (final PostingApplication application : this.applicationsById.values()) {
            if (application.getApplicantUserId().equals(currentId)
                    && !removedPostingIds.contains(application.getPostingId())) {
                final Posting posting = this.postingsById.get(application.getPostingId());
                if (posting != null) {
                    posting.setApplicantCount(Math.max(0, posting.getApplicantCount() - 1));
                }
            }
        }
        this.applicationsById.values().removeIf(application ->
                application.getApplicantUserId().equals(currentId)
                        || removedPostingIds.contains(application.getPostingId()));
    }

    // ── Messages ─────────────────────────────────────────────────────────────

    @Override
    public Message sendMessage(final String receiverId, final String content) {
        final String currentId = this.session.getCurrentUserId();
        if (!hasMutualConnection(currentId, receiverId)) {
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
        if (!hasMutualConnection(currentId, otherUserId)) {
            throw new InvalidRequestException("You can only message users you have matched with");
        }
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
        addSeedUser("Ada", "Lovelace", "ada@demo.local",
                this.institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
                AcademicLevel.FACULTY, ResearchField.MATHEMATICS, CollaborationType.INTEREST_SHARING,
                "Looking for collaborators interested in the history and theory of computation.",
                "Mathematical foundations of computing and algorithmic analysis.",
                5, FundingStatus.INSTITUTIONAL_FUNDING);
        addSeedUser("Alan", "Turing", "alan@demo.local",
                this.institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
                AcademicLevel.FACULTY, ResearchField.COMPUTER_SCIENCE, CollaborationType.CO_AUTHOR,
                "Interested in co-authoring on computability and machine intelligence.",
                "Computability theory, cryptography, and artificial intelligence.",
                10, FundingStatus.GOVERNMENT_GRANT);
        addSeedUser("Grace", "Hopper", "grace@demo.local",
                this.institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
                AcademicLevel.INDUSTRY_RESEARCHER, ResearchField.SOFTWARE_ENGINEERING, CollaborationType.MENTORSHIP,
                "Open to mentoring and joint work on programming language design.",
                "Compilers, programming languages, and software engineering practice.",
                8, FundingStatus.INDUSTRY_SPONSORED);
        addSeedUser("Demo", "Student", "demo.student@utoronto.ca",
                this.institutionCatalog.findById("UNIVERSITY_OF_TORONTO"),
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

    // ── Helpers ──────────────────────────────────────────────────────────────

    private User findByEmail(final String email) {
        for (final User user : this.usersById.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    private String displayName(final String userId) {
        final User user = this.usersById.get(userId);
        return user == null ? "" : user.getFullName();
    }

    private String normalizeEmail(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private String verificationFailureMessage(
            final EmailVerificationResult result) {
        return switch (result.outcome()) {
            case EXPIRED -> "Verification code has expired";
            case ATTEMPTS_EXHAUSTED ->
                "Verification failed after three incorrect attempts";
            case INVALID_CODE -> "Verification code is incorrect. "
                    + result.attemptsRemaining() + " attempts remaining";
            case VERIFIED -> "";
        };
    }

    private boolean hasMutualConnection(
            final String firstUserId,
            final String secondUserId) {
        return this.recordedConnections.contains(firstUserId + "->" + secondUserId)
                && this.recordedConnections.contains(secondUserId + "->" + firstUserId)
                && !this.recordedDislikes.contains(firstUserId + "->" + secondUserId)
                && !this.recordedDislikes.contains(secondUserId + "->" + firstUserId);
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
        return this.institutionCatalog.findById(value);
    }
}
