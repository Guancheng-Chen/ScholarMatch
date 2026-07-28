package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.register.RegisterAccountData;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalServerRepositoryAccountTest {

    private CurrentUserProvider session;
    private LocalAuthRepository authRepo;
    private LocalProfileRepository profileRepo;
    private LocalAccountSettingsRepository accountSettingsRepo;

    @BeforeEach
    void setUp() {
        this.session = new CurrentUserProvider();
        final ClasspathInstitutionCatalogRepository institutions =
                new ClasspathInstitutionCatalogRepository();
        final LocalServerState state = new LocalServerState(institutions);
        this.authRepo = new LocalAuthRepository(state);
        this.profileRepo = new LocalProfileRepository(state, this.session, institutions);
        this.accountSettingsRepo = new LocalAccountSettingsRepository(
                state,
                this.session,
                new InMemoryEmailVerificationChallengeRepository(),
                new SecureVerificationCodeGenerator(),
                (email, code) -> { },
                new ClasspathAcademicEmailDomainRepository(),
                Clock.systemUTC());
    }

    @Test
    void testRegisterLoginAndDuplicateValidation() {
        final AuthResult registration = register("Ada", "ada@example.com");

        assertEquals("Ada User", registration.displayName());
        assertEquals(registration.userId(),
                this.authRepo.login("ADA@example.com", "password").userId());
        assertThrows(InvalidRequestException.class,
                () -> this.authRepo.login("ada@example.com", "wrong"));
        assertThrows(InvalidRequestException.class,
                () -> this.authRepo.login("missing@example.com", "password"));
        assertThrows(InvalidRequestException.class,
                () -> register("Other", "ada@example.com"));
    }

    @Test
    void testProfileNotFoundAndProfileUpdateCannotChangeEmail() {
        this.session.setCurrentUserId("missing-user");
        assertThrows(ResourceNotFoundException.class, this.profileRepo::getProfile);

        final AuthResult first = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(first.userId());

        final User updated = this.profileRepo.updateProfile(input(
                "grace@example.com", "FACULTY", "COMPUTER_SCIENCE",
                "CO_AUTHOR", "SELF_FUNDED"));

        assertEquals("ada@example.com", updated.getEmail());
    }

    @Test
    void testUpdateProfileMapsAllFieldsWithoutChangingAccountEmail() {
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());
        final User user = this.profileRepo.getProfile();
        user.addResearchInterest("old interest");
        user.setEmailAccountType(EmailAccountType.ACADEMIC);

        final User updated = this.profileRepo.updateProfile(input(
                "new@example.com", "FACULTY", "COMPUTER_SCIENCE",
                "CO_AUTHOR", "SELF_FUNDED"));

        assertEquals("ada@example.com", updated.getEmail());
        assertEquals(EmailAccountType.ACADEMIC, updated.getEmailAccountType());
        assertEquals(Institution.MIT, updated.getInstitution());
        assertEquals(AcademicLevel.FACULTY, updated.getAcademicLevel());
        assertEquals(ResearchField.COMPUTER_SCIENCE, updated.getResearchField());
        assertEquals(CollaborationType.CO_AUTHOR, updated.getLookingFor());
        assertEquals("Collaboration", updated.getCollaborationDescription());
        assertEquals("Research", updated.getResearchDescription());
        assertEquals(12, updated.getWeeklyAvailabilityHours());
        assertEquals(FundingStatus.SELF_FUNDED, updated.getFundingStatus());
        assertEquals("555-1234", updated.getPhoneNumber());
        assertEquals(5, updated.gethIndex());
        assertEquals(100, updated.getTotalCitations());
        assertEquals(List.of("new interest"), updated.getResearchInterests());
    }

    @Test
    void testUpdateProfileLeavesFieldsUnchangedWhenNull() {
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());
        this.profileRepo.updateProfile(input(
                "ada@example.com", "FACULTY", "COMPUTER_SCIENCE",
                "CO_AUTHOR", "SELF_FUNDED"));

        final User updated = this.profileRepo.updateProfile(new UpdateProfileInputData(
                null, null, null, null, null,
                null, null, null, null,
                List.of("new interest"), null, null, null, List.of(), List.of()));

        assertEquals(Institution.MIT, updated.getInstitution());
        assertEquals(AcademicLevel.FACULTY, updated.getAcademicLevel());
        assertEquals(ResearchField.COMPUTER_SCIENCE, updated.getResearchField());
        assertEquals(CollaborationType.CO_AUTHOR, updated.getLookingFor());
        assertEquals("Collaboration", updated.getCollaborationDescription());
        assertEquals("Research", updated.getResearchDescription());
        assertEquals(12, updated.getWeeklyAvailabilityHours());
        assertEquals(FundingStatus.SELF_FUNDED, updated.getFundingStatus());
        assertEquals("555-1234", updated.getPhoneNumber());
        assertEquals(5, updated.gethIndex());
        assertEquals(100, updated.getTotalCitations());
        assertEquals(List.of("new interest"), updated.getResearchInterests());
    }

    @Test
    void testUpdateProfileFallsBackForUnknownEnums() {
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());

        final User updated = this.profileRepo.updateProfile(input(
                "ada@example.com", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));

        assertEquals(AcademicLevel.UNDERGRADUATE, updated.getAcademicLevel());
        assertEquals(ResearchField.OTHER, updated.getResearchField());
        assertEquals(CollaborationType.INTEREST_SHARING, updated.getLookingFor());
        assertEquals(FundingStatus.OTHER, updated.getFundingStatus());
    }

    @Test
    void testChangePasswordThrowsWhenCurrentUserHasNoProfile() {
        this.session.setCurrentUserId("missing-user");

        assertThrows(ResourceNotFoundException.class, () ->
                this.accountSettingsRepo.changePassword("password", "new-password"));
    }

    @Test
    void testRequestVerificationCodeSucceedsWithDefaultNoOpDelivery() {
        register("Ada", "ada@example.com");

        this.accountSettingsRepo.requestVerificationCode("new@example.com");
    }

    @Test
    void testRequestVerificationCodeNormalizesNullEmailToEmptyString() {
        register("Ada", "ada@example.com");

        this.accountSettingsRepo.requestVerificationCode(null);
    }

    @Test
    void testRequestVerificationCodeAllowsReRequestingOwnCurrentEmail() {
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());

        this.accountSettingsRepo.requestVerificationCode("ada@example.com");
    }

    @Test
    void testRequestVerificationCodeRejectsEmailAlreadyRegisteredToAnotherUser() {
        register("Ada", "ada@example.com");
        final AuthResult other = register("Grace", "grace@example.com");
        this.session.setCurrentUserId(other.userId());

        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.requestVerificationCode("ada@example.com"));
    }

    @Test
    void testEmailAndPasswordChangesUseVerifiedAccountSettingsFlow() {
        final AtomicReference<String> deliveredCode = new AtomicReference<>();
        final MutableClock clock = new MutableClock(
                Instant.parse("2026-07-26T12:00:00Z"));
        useCustomAccountSettings(
                new InMemoryEmailVerificationChallengeRepository(),
                () -> "123456",
                (email, code) -> deliveredCode.set(code),
                email -> email.endsWith("@mit.edu"),
                clock);
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());

        this.accountSettingsRepo.requestVerificationCode("new@mit.edu");
        assertEquals("123456", deliveredCode.get());
        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail(
                        "new@mit.edu", "wrong", "123456"));

        final User updated = this.accountSettingsRepo.changeEmail(
                "new@mit.edu", "password", "123456");
        assertEquals("new@mit.edu", updated.getEmail());
        assertEquals(EmailAccountType.ACADEMIC, updated.getEmailAccountType());
        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail(
                        "new@mit.edu", "password", "123456"));

        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changePassword("wrong", "new-password"));
        this.accountSettingsRepo.changePassword("password", "new-password");
        assertEquals(registration.userId(),
                this.authRepo.login("new@mit.edu", "new-password").userId());
        assertThrows(InvalidRequestException.class, () ->
                this.authRepo.login("new@mit.edu", "password"));
    }

    @Test
    void testChangeEmailToNonAcademicDomainSetsRegularAccountType() {
        useCustomAccountSettings(
                new InMemoryEmailVerificationChallengeRepository(),
                () -> "123456",
                (email, code) -> { },
                email -> email.endsWith("@mit.edu"),
                Clock.systemUTC());
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());
        this.profileRepo.getProfile().setEmailAccountType(EmailAccountType.ACADEMIC);

        this.accountSettingsRepo.requestVerificationCode("new@other.com");
        final User updated = this.accountSettingsRepo.changeEmail(
                "new@other.com", "password", "123456");

        assertEquals(EmailAccountType.REGULAR, updated.getEmailAccountType());
    }

    @Test
    void testFailedCodeDeliveryDeletesTheChallengeAndPropagates() {
        final InMemoryEmailVerificationChallengeRepository emailChallenges =
                new InMemoryEmailVerificationChallengeRepository();
        useCustomAccountSettings(
                emailChallenges,
                () -> "123456",
                (email, code) -> {
                    throw new RuntimeException("delivery failed");
                },
                email -> false,
                Clock.systemUTC());
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());

        assertThrows(RuntimeException.class, () ->
                this.accountSettingsRepo.requestVerificationCode("new@example.com"));

        assertEquals(java.util.Optional.empty(), emailChallenges.findByEmail("new@example.com"));
    }

    @Test
    void testChangeEmailRejectsEmailAlreadyRegisteredToAnotherUser() {
        register("Grace", "grace@example.com");
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());

        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail("grace@example.com", "password", "000000"));
    }

    @Test
    void testEmailCodeExpiresAndStopsAfterThreeInvalidAttempts() {
        final MutableClock clock = new MutableClock(
                Instant.parse("2026-07-26T12:00:00Z"));
        useCustomAccountSettings(
                new InMemoryEmailVerificationChallengeRepository(),
                () -> "123456",
                (email, code) -> { },
                email -> false,
                clock);
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());

        this.accountSettingsRepo.requestVerificationCode("first@example.com");
        clock.advance(Duration.ofMinutes(10));
        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail(
                        "first@example.com", "password", "123456"));

        this.accountSettingsRepo.requestVerificationCode("second@example.com");
        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail(
                        "second@example.com", "password", "000000"));
        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail(
                        "second@example.com", "password", "000000"));
        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail(
                        "second@example.com", "password", "000000"));
        assertThrows(InvalidRequestException.class, () ->
                this.accountSettingsRepo.changeEmail(
                        "second@example.com", "password", "123456"));
    }

    /**
     * Rebuilds every repository around a fresh {@link LocalServerState} with the given
     * verification collaborators, so a test can control the code/clock/delivery/domain-check
     * behavior without disturbing the other test methods' default setup.
     */
    private void useCustomAccountSettings(
            final InMemoryEmailVerificationChallengeRepository emailChallenges,
            final VerificationCodeGeneratorInterface codeGenerator,
            final EmailChangeCodeDeliveryDataAccessInterface codeDelivery,
            final AcademicEmailDomainDataAccessInterface academicEmailDomains,
            final Clock clock) {
        final ClasspathInstitutionCatalogRepository institutions =
                new ClasspathInstitutionCatalogRepository();
        final LocalServerState state = new LocalServerState(institutions);
        this.authRepo = new LocalAuthRepository(state);
        this.profileRepo = new LocalProfileRepository(state, this.session, institutions);
        this.accountSettingsRepo = new LocalAccountSettingsRepository(
                state,
                this.session,
                emailChallenges,
                codeGenerator,
                codeDelivery,
                academicEmailDomains,
                clock);
    }

    private AuthResult register(final String firstName, final String email) {
        return this.authRepo.register(new RegisterAccountData(
                firstName, "User", email, "password", "123456"));
    }

    private UpdateProfileInputData input(
            final String email,
            final String academicLevel,
            final String researchField,
            final String lookingFor,
            final String fundingStatus) {
        return new UpdateProfileInputData(
                email, "MIT", academicLevel, researchField, lookingFor,
                "Collaboration", "Research", 12, fundingStatus,
                List.of("new interest"), "555-1234", 5, 100, List.of(), List.of());
    }

    private static final class MutableClock extends Clock {

        private Instant instant;

        MutableClock(final Instant instant) {
            this.instant = instant;
        }

        void advance(final Duration duration) {
            this.instant = this.instant.plus(duration);
        }

        @Override
        public ZoneId getZone() {
            return ZoneId.of("UTC");
        }

        @Override
        public Clock withZone(final ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return this.instant;
        }
    }
}
