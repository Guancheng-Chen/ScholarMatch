package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProfileGatewayTest {

    private HttpTestServer fakeServer;
    private ProfileGateway gateway;

    @BeforeEach
    void setUp() throws IOException {
        this.fakeServer = new HttpTestServer();
        final CurrentUserProviderInterface session = mock(CurrentUserProviderInterface.class);
        when(session.getToken()).thenReturn("test-token");
        this.gateway = new ProfileGateway(
                new ServerHttpClient(this.fakeServer.baseUrl(), session),
                new ClasspathInstitutionCatalogRepository());
    }

    @AfterEach
    void tearDown() {
        this.fakeServer.stop();
    }

    @Test
    void testGetProfileParsesAllOptionalFields() {
        this.fakeServer.bodyToReturn().set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "email": "ada@example.com", "phoneNumber": "555-1", "institution": "UNIVERSITY_OF_TORONTO",
             "academicLevel": "FACULTY", "researchField": "MATHEMATICS", "lookingFor": "CO_AUTHOR",
             "collaborationDescription": "collab", "researchDescription": "research",
             "weeklyAvailabilityHours": 10, "fundingStatus": "SELF_FUNDED",
             "hIndex": 40, "totalCitations": 500,
             "researchInterests": ["nlp", "vision"],
             "papers": [{"doi": "10.1/x", "title": "Paper X"}],
             "educations": [{"school": "MIT", "degree": "PHD"}]}""");

        final User user = this.gateway.getProfile();

        assertEquals("u-1", user.getUserId());
        assertEquals("ada@example.com", user.getEmail());
        assertEquals("555-1", user.getPhoneNumber());
        assertEquals(com.scholarmatch.entity.Institution.UNIVERSITY_OF_TORONTO, user.getInstitution());
        assertEquals(com.scholarmatch.entity.AcademicLevel.FACULTY, user.getAcademicLevel());
        assertEquals(com.scholarmatch.entity.ResearchField.MATHEMATICS, user.getResearchField());
        assertEquals(com.scholarmatch.entity.CollaborationType.CO_AUTHOR, user.getLookingFor());
        assertEquals("collab", user.getCollaborationDescription());
        assertEquals("research", user.getResearchDescription());
        assertEquals(10, user.getWeeklyAvailabilityHours());
        assertEquals(com.scholarmatch.entity.FundingStatus.SELF_FUNDED, user.getFundingStatus());
        assertEquals(40, user.gethIndex());
        assertEquals(500, user.getTotalCitations());
        assertTrue(user.getResearchInterests().containsAll(List.of("nlp", "vision")));
        assertEquals(1, user.getPublications().size());
        assertEquals("Paper X", user.getPublications().get(0).getTitle());
        assertEquals(1, user.getEducations().size());
        assertEquals("MIT", user.getEducations().get(0).getInstitution());
        assertEquals(DegreeType.PHD, user.getEducations().get(0).getDegreeType());
    }

    @Test
    void testGetProfileFallsBackToDefaultsWhenOptionalFieldsMissingOrInvalid() {
        this.fakeServer.bodyToReturn().set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "academicLevel": "NOT_REAL", "educations": [{"school": "MIT", "degree": "NOT_REAL"}]}""");

        final User user = this.gateway.getProfile();

        assertEquals("", user.getEmail());
        assertEquals("", user.getPhoneNumber());
        assertEquals(com.scholarmatch.entity.AcademicLevel.GRADUATE_STUDENT, user.getAcademicLevel());
        assertEquals(com.scholarmatch.entity.CollaborationType.INTEREST_SHARING, user.getLookingFor());
        assertEquals(com.scholarmatch.entity.ResearchField.OTHER, user.getResearchField());
        assertEquals(com.scholarmatch.entity.FundingStatus.OTHER, user.getFundingStatus());
        assertEquals(com.scholarmatch.entity.Institution.OTHER, user.getInstitution());
        assertEquals(null, user.getWeeklyAvailabilityHours());
        assertEquals(DegreeType.BACHELOR, user.getEducations().get(0).getDegreeType());
    }

    @Test
    void testGetProfileDoesNotAssumeVerificationWhenServerOmitsFlag() {
        this.fakeServer.bodyToReturn().set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "email": "ada@mit.edu"}""");

        final User user = this.gateway.getProfile();

        assertEquals(EmailAccountType.REGULAR, user.getEmailAccountType());
    }

    @Test
    void testGetProfileUsesServerClassificationWhenPresent() {
        this.fakeServer.bodyToReturn().set("""
            {"scholarId": "u-1", "firstName": "Ada", "lastName": "Lovelace",
             "email": "ada@mit.edu", "academicEmailVerified": false}""");

        final User user = this.gateway.getProfile();

        assertEquals(EmailAccountType.REGULAR, user.getEmailAccountType());
    }

    @Test
    void testGetProfileThrowsResourceNotFoundOn404WithErrorField() {
        this.fakeServer.statusToReturn().set(404);
        this.fakeServer.bodyToReturn().set("{\"error\": \"Profile not found\"}");

        final ResourceNotFoundException thrown =
                assertThrows(ResourceNotFoundException.class, () -> this.gateway.getProfile());
        assertEquals("Profile not found", thrown.getMessage());
    }

    @Test
    void testUpdateProfileSendsAllProvidedFieldsIncludingPapersAndEducations() {
        this.fakeServer.bodyToReturn().set("{\"scholarId\": \"u-1\", \"firstName\": \"Ada\", \"lastName\": \"Lovelace\"}");

        final User result = this.gateway.updateProfile(new UpdateProfileInputData(
                "new@example.com", "UNIVERSITY_OF_TORONTO", "FACULTY", "MATHEMATICS", "CO_AUTHOR",
                "collab", "research", 10, "SELF_FUNDED", List.of("nlp"), "555-1", 40, 500,
                List.of(new Education("MIT", DegreeType.PHD, 2010, Month.JANUARY, 2015, Month.MAY)),
                List.of(new Publication("10.1/x", "Paper X", 2020, 5))));

        assertEquals("u-1", result.getUserId());
        assertEquals("PUT", this.fakeServer.lastMethod().get());
        assertTrue(this.fakeServer.lastRequestBody().get().contains("new@example.com"));
        assertTrue(this.fakeServer.lastRequestBody().get().contains("Paper X"));
        assertTrue(this.fakeServer.lastRequestBody().get().contains("MIT"));
        assertTrue(this.fakeServer.lastRequestBody().get().contains("PHD"));
    }

    @Test
    void testUpdateProfileOmitsNullFieldsFromRequestBody() {
        this.fakeServer.bodyToReturn().set("{\"scholarId\": \"u-1\", \"firstName\": \"Ada\", \"lastName\": \"Lovelace\"}");

        this.gateway.updateProfile(new UpdateProfileInputData(
                null, null, null, null, null, null, null, null, null,
                List.of(), null, null, null, List.of(), List.of()));

        assertFalse(this.fakeServer.lastRequestBody().get().contains("institution"));
        assertFalse(this.fakeServer.lastRequestBody().get().contains("hIndex"));
    }

    @Test
    void testUpdateProfileThrowsInvalidRequestOn400WithErrorField() {
        this.fakeServer.statusToReturn().set(400);
        this.fakeServer.bodyToReturn().set("{\"error\": \"Bad update request\"}");

        final InvalidRequestException thrown = assertThrows(InvalidRequestException.class,
                () -> this.gateway.updateProfile(new UpdateProfileInputData(
                        null, null, null, null, null, null, null, null, null,
                        List.of(), null, null, null, List.of(), List.of())));
        assertEquals("Bad update request", thrown.getMessage());
    }

    @Test
    void testDeleteAccountSendsDeleteRequest() {
        this.fakeServer.statusToReturn().set(204);
        this.fakeServer.bodyToReturn().set(null);

        this.gateway.deleteAccount();

        assertEquals("DELETE", this.fakeServer.lastMethod().get());
    }
}
