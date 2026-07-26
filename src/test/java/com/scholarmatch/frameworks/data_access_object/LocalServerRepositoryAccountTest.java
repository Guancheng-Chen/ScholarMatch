package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.register.RegisterAccountData;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalServerRepositoryAccountTest {

    private CurrentUserProvider session;
    private LocalServerRepository repository;

    @BeforeEach
    void setUp() {
        this.session = new CurrentUserProvider();
        this.repository = new LocalServerRepository(this.session);
    }

    @Test
    void testRegisterLoginAndDuplicateValidation() {
        final AuthResult registration = register("Ada", "ada@example.com");

        assertEquals("Ada User", registration.displayName());
        assertEquals(registration.userId(),
                this.repository.login("ADA@example.com", "password").userId());
        assertThrows(InvalidRequestException.class,
                () -> this.repository.login("ada@example.com", "wrong"));
        assertThrows(InvalidRequestException.class,
                () -> this.repository.login("missing@example.com", "password"));
        assertThrows(InvalidRequestException.class,
                () -> register("Other", "ada@example.com"));
    }

    @Test
    void testProfileNotFoundAndDuplicateEmailAreReported() {
        this.session.setCurrentUserId("missing-user");
        assertThrows(ResourceNotFoundException.class, this.repository::getProfile);

        final AuthResult first = register("Ada", "ada@example.com");
        register("Grace", "grace@example.com");
        this.session.setCurrentUserId(first.userId());

        assertThrows(InvalidRequestException.class,
                () -> this.repository.updateProfile(input(
                        "grace@example.com", "FACULTY", "COMPUTER_SCIENCE",
                        "CO_AUTHOR", "SELF_FUNDED")));
    }

    @Test
    void testUpdateProfileMapsAllFieldsAndResetsEmailClassification() {
        final AuthResult registration = register("Ada", "ada@example.com");
        this.session.setCurrentUserId(registration.userId());
        final User user = this.repository.getProfile();
        user.addResearchInterest("old interest");
        user.setEmailAccountType(EmailAccountType.ACADEMIC);

        final User updated = this.repository.updateProfile(input(
                "new@example.com", "FACULTY", "COMPUTER_SCIENCE",
                "CO_AUTHOR", "SELF_FUNDED"));

        assertEquals("new@example.com", updated.getEmail());
        assertEquals(EmailAccountType.REGULAR, updated.getEmailAccountType());
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

        final User updated = this.repository.updateProfile(input(
                "ada@example.com", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));

        assertEquals(AcademicLevel.UNDERGRADUATE, updated.getAcademicLevel());
        assertEquals(ResearchField.OTHER, updated.getResearchField());
        assertEquals(CollaborationType.INTEREST_SHARING, updated.getLookingFor());
        assertEquals(FundingStatus.OTHER, updated.getFundingStatus());
    }

    private AuthResult register(final String firstName, final String email) {
        return this.repository.register(new RegisterAccountData(
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
}
