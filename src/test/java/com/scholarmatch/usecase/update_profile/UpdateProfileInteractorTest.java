package com.scholarmatch.usecase.update_profile;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateProfileInteractorTest {

    private UpdateProfileDataAccessInterface dataAccessObject;
    private UpdateProfileOutputBoundary outputBoundary;
    private UpdateProfileInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccessObject = mock(UpdateProfileDataAccessInterface.class);
        outputBoundary = mock(UpdateProfileOutputBoundary.class);
        interactor = new UpdateProfileInteractor(dataAccessObject, outputBoundary);
    }

    /**
     * Mutable holder for every UpdateProfileInputData field, defaulted to a fully valid
     * profile. Each test overrides only the field(s) it cares about before calling
     * {@link #build()}, instead of repeating all fifteen constructor arguments per test.
     */
    private static final class Params {
        private String email = "jane@example.com";
        private String institution = "MIT";
        private String academicLevel = "FACULTY";
        private String researchField = "MACHINE_LEARNING";
        private String lookingFor = "CO_AUTHOR";
        private String collaborationDescription = "Looking for collaborators";
        private String researchDescription = "Deep learning research";
        private Integer weeklyAvailabilityHours = 20;
        private String fundingStatus = "UNFUNDED";
        private List<String> researchInterests = new ArrayList<>(List.of("machine learning"));
        private String phoneNumber = "555-1234";
        private Integer hIndex = 5;
        private Integer totalCitations = 100;
        private List<Education> educations = new ArrayList<>();
        private List<Publication> publications = new ArrayList<>();

        UpdateProfileInputData build() {
            return new UpdateProfileInputData(
                    email, institution, academicLevel, researchField, lookingFor,
                    collaborationDescription, researchDescription, weeklyAvailabilityHours, fundingStatus,
                    researchInterests, phoneNumber, hIndex, totalCitations, educations, publications);
        }
    }

    private String captureFailMessage(final UpdateProfileInputData inputData) {
        interactor.execute(inputData);
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(outputBoundary).prepareFailView(captor.capture());
        return captor.getValue();
    }

    /**
     * {@link User} is final, so Mockito can't mock it — build a real instance instead for
     * stubbing the DAO's return value on the success path.
     */
    private User updatedUser() {
        return new User(
                "user-1", "Jane", "Doe", "jane@example.com", "555-1234",
                Institution.MIT, AcademicLevel.FACULTY, ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR, "desc", "desc", 20, FundingStatus.UNFUNDED, "hash");
    }

    // ----- success path -----

    @Test
    void testExecuteSucceedsWithFullyValidProfile() {
        when(dataAccessObject.updateProfile(any())).thenReturn(updatedUser());

        interactor.execute(new Params().build());

        verify(dataAccessObject).updateProfile(any());
        verify(outputBoundary).prepareSuccessView(any());
        verify(outputBoundary, never()).prepareFailView(anyString());
    }

    @Test
    void testValidationFailurePreventsDataAccessCall() {
        final Params params = new Params();
        params.email = "";

        interactor.execute(params.build());

        verify(dataAccessObject, never()).updateProfile(any());
        verify(outputBoundary, never()).prepareSuccessView(any());
    }

    @Test
    void testExecuteFailsWhenServerRejectsUpdate() {
        when(dataAccessObject.updateProfile(any())).thenThrow(new InvalidRequestException("Email already in use"));

        interactor.execute(new Params().build());

        verify(outputBoundary).prepareFailView("Email already in use");
    }

    // ----- required-field validation -----

    @Test
    void testFailsWhenEmailBlank() {
        final Params params = new Params();
        params.email = "";

        assertTrue(captureFailMessage(params.build()).contains("Email is required."));
    }

    @Test
    void testFailsWhenEmailFormatInvalid() {
        final Params params = new Params();
        params.email = "not-an-email";

        assertTrue(captureFailMessage(params.build()).contains("Email format is invalid"));
    }

    @Test
    void testFailsWhenPhoneNumberBlank() {
        final Params params = new Params();
        params.phoneNumber = "  ";

        assertTrue(captureFailMessage(params.build()).contains("Phone number is required."));
    }

    @Test
    void testFailsWhenInstitutionMissing() {
        final Params params = new Params();
        params.institution = null;

        assertTrue(captureFailMessage(params.build()).contains("Institution is required."));
    }

    @Test
    void testFailsWhenAcademicLevelMissing() {
        final Params params = new Params();
        params.academicLevel = null;

        assertTrue(captureFailMessage(params.build()).contains("Academic level is required."));
    }

    @Test
    void testFailsWhenResearchFieldMissing() {
        final Params params = new Params();
        params.researchField = null;

        assertTrue(captureFailMessage(params.build()).contains("Research field is required."));
    }

    @Test
    void testFailsWhenLookingForMissing() {
        final Params params = new Params();
        params.lookingFor = null;

        assertTrue(captureFailMessage(params.build()).contains("Looking for is required."));
    }

    @Test
    void testFailsWhenFundingStatusMissing() {
        final Params params = new Params();
        params.fundingStatus = null;

        assertTrue(captureFailMessage(params.build()).contains("Funding status is required."));
    }

    @Test
    void testFailsWhenCollaborationDescriptionBlank() {
        final Params params = new Params();
        params.collaborationDescription = "";

        assertTrue(captureFailMessage(params.build()).contains("Collaboration description is required."));
    }

    @Test
    void testFailsWhenResearchDescriptionBlank() {
        final Params params = new Params();
        params.researchDescription = "";

        assertTrue(captureFailMessage(params.build()).contains("Research description is required."));
    }

    @Test
    void testFailsWhenWeeklyAvailabilityMissing() {
        final Params params = new Params();
        params.weeklyAvailabilityHours = null;

        assertTrue(captureFailMessage(params.build()).contains("Weekly availability is required."));
    }

    @Test
    void testFailsWhenHIndexMissing() {
        final Params params = new Params();
        params.hIndex = null;

        assertTrue(captureFailMessage(params.build()).contains("h-index is required."));
    }

    @Test
    void testFailsWhenTotalCitationsMissing() {
        final Params params = new Params();
        params.totalCitations = null;

        assertTrue(captureFailMessage(params.build()).contains("Total citations is required."));
    }

    @Test
    void testFailsWhenResearchInterestsEmpty() {
        final Params params = new Params();
        params.researchInterests = new ArrayList<>();

        assertTrue(captureFailMessage(params.build()).contains("At least one research interest is required."));
    }

    @Test
    void testSucceedsWithEmptyEducationsAndPublications() {
        when(dataAccessObject.updateProfile(any())).thenReturn(updatedUser());

        // Params defaults already leave educations/publications empty — this is the
        // "student with no history yet" case, which must be allowed to save.
        interactor.execute(new Params().build());

        verify(dataAccessObject).updateProfile(any());
        verify(outputBoundary, never()).prepareFailView(anyString());
    }

    // ----- length limits -----

    @Test
    void testFailsWhenCollaborationDescriptionTooLong() {
        final Params params = new Params();
        params.collaborationDescription = "x".repeat(2001);

        assertTrue(captureFailMessage(params.build())
                .contains("Collaboration description must be at most 2000 characters"));
    }

    @Test
    void testFailsWhenResearchDescriptionTooLong() {
        final Params params = new Params();
        params.researchDescription = "x".repeat(2001);

        assertTrue(captureFailMessage(params.build())
                .contains("Research description must be at most 2000 characters"));
    }

    @Test
    void testFailsWhenResearchInterestTooLong() {
        final Params params = new Params();
        params.researchInterests = new ArrayList<>(List.of("x".repeat(61)));

        assertTrue(captureFailMessage(params.build()).contains("must be at most 60 characters"));
    }

    // ----- education date validation -----

    private Education validOngoingEducation() {
        return new Education("MIT", DegreeType.MASTER, Year.now().getValue(), Month.SEPTEMBER, null, null);
    }

    @Test
    void testSucceedsWithOngoingEducationEntry() {
        when(dataAccessObject.updateProfile(any())).thenReturn(updatedUser());
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(validOngoingEducation()));

        interactor.execute(params.build());

        verify(dataAccessObject).updateProfile(any());
        verify(outputBoundary, never()).prepareFailView(anyString());
    }

    @Test
    void testSucceedsWithCompletedEducationEntry() {
        when(dataAccessObject.updateProfile(any())).thenReturn(updatedUser());
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.BACHELOR, 2018, Month.SEPTEMBER, 2022, Month.JUNE)));

        interactor.execute(params.build());

        verify(dataAccessObject).updateProfile(any());
        verify(outputBoundary, never()).prepareFailView(anyString());
    }

    @Test
    void testFailsWhenStartYearBelowMinimum() {
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.BACHELOR, 1800, Month.SEPTEMBER, null, null)));

        assertTrue(captureFailMessage(params.build()).contains("start year 1800 is out of range"));
    }

    @Test
    void testFailsWhenStartYearTooFarInFuture() {
        final int wayFuture = Year.now().getValue() + 50;
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.BACHELOR, wayFuture, Month.SEPTEMBER, null, null)));

        assertTrue(captureFailMessage(params.build()).contains("start year " + wayFuture + " is out of range"));
    }

    @Test
    void testFailsWhenEndYearBelowMinimum() {
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.BACHELOR, 1950, Month.SEPTEMBER, 1800, Month.JUNE)));

        assertTrue(captureFailMessage(params.build()).contains("end year 1800 is out of range"));
    }

    @Test
    void testFailsWhenEndYearTooFarInFuture() {
        final int wayFuture = Year.now().getValue() + 50;
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.BACHELOR, 2018, Month.SEPTEMBER, wayFuture, Month.JUNE)));

        assertTrue(captureFailMessage(params.build()).contains("end year " + wayFuture + " is out of range"));
    }

    @Test
    void testFailsWhenNotOngoingAndEndMonthMissing() {
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.BACHELOR, 2018, Month.SEPTEMBER, 2022, null)));

        assertTrue(captureFailMessage(params.build())
                .contains("end month is required, or mark this entry as currently enrolled"));
    }

    @Test
    void testFailsWhenEndYearBeforeStartYear() {
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.BACHELOR, 2025, Month.SEPTEMBER, 2021, Month.JUNE)));

        assertTrue(captureFailMessage(params.build()).contains("end date cannot be before the start date"));
    }

    @Test
    void testFailsWhenSameYearButEndMonthBeforeStartMonth() {
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                new Education("MIT", DegreeType.MASTER, 2023, Month.SEPTEMBER, 2023, Month.JANUARY)));

        assertTrue(captureFailMessage(params.build()).contains("end date cannot be before the start date"));
    }

    @Test
    void testEducationErrorLabelIncludesIndexAndInstitution() {
        final Params params = new Params();
        params.educations = new ArrayList<>(List.of(
                validOngoingEducation(),
                new Education("Cambridge", DegreeType.BACHELOR, 1800, Month.SEPTEMBER, null, null)));

        assertTrue(captureFailMessage(params.build()).contains("Education #2 (Cambridge)"));
    }

    // ----- multiple errors reported together -----

    @Test
    void testAllViolationsAreReportedInOneFailMessage() {
        final Params params = new Params();
        params.email = "";
        params.phoneNumber = "";
        params.hIndex = null;
        params.researchInterests = new ArrayList<>();

        final String message = captureFailMessage(params.build());

        assertTrue(message.contains("Email is required."));
        assertTrue(message.contains("Phone number is required."));
        assertTrue(message.contains("h-index is required."));
        assertTrue(message.contains("At least one research interest is required."));
    }
}

