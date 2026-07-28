package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private User buildCompleteUser() {
        return new User(
                "id1",
                "Alice",
                "Zhang",
                "alice@example.com",
                "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO,
                AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING,
                CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper",
                "Deep learning for computer vision",
                10,
                FundingStatus.INSTITUTIONAL_FUNDING,
                "hash"
        );
    }

    @Test
    void testAddResearchInterest() {
        final User user = buildCompleteUser();
        user.addResearchInterest("machine learning");
        assertTrue(user.getResearchInterests().contains("machine learning"));
    }

    @Test
    void testRemoveResearchInterest() {
        final User user = buildCompleteUser();
        user.addResearchInterest("NLP");
        final boolean removed = user.removeResearchInterest("NLP");
        assertTrue(removed);
        assertEquals(0, user.getResearchInterests().size());
    }

    @Test
    void testIsProfileCompleteTrueWhenAllFieldsSet() {
        final User user = buildCompleteUser();
        assertTrue(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenNewFieldsMissing() {
        final User user = new User(
                "id1",
                "Alice",
                "Zhang",
                "alice@example.com",
                "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO,
                AcademicLevel.FACULTY,
                null,
                CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper",
                "Deep learning for computer vision",
                null,
                null,
                "hash"
        );
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenFirstNameNull() {
        final User user = new User(
                "id1", null, "Zhang", "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenLastNameNull() {
        final User user = new User(
                "id1", "Alice", null, "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenEmailNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", null, "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenPhoneNumberNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", null,
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenCollaborationDescriptionNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                null, "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenResearchDescriptionBlank() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "   ",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenInstitutionNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", "123-456-7890",
                null, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenAcademicLevelNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, null,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenLookingForNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, null,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenWeeklyAvailabilityHoursNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                null, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testIsProfileCompleteFalseWhenFundingStatusNull() {
        final User user = new User(
                "id1", "Alice", "Zhang", "alice@example.com", "123-456-7890",
                Institution.UNIVERSITY_OF_TORONTO, AcademicLevel.FACULTY,
                ResearchField.MACHINE_LEARNING, CollaborationType.INTEREST_SHARING,
                "Looking for someone to co-author a paper", "Deep learning for computer vision",
                10, null, "hash");
        assertFalse(user.isProfileComplete());
    }

    @Test
    void testMutableProfileFieldsAndPublications() {
        final User user = buildCompleteUser();
        final Publication publication = new Publication(
                "10.1000/example", "Example Paper", 2026, 12);

        user.addPublication(publication);
        assertTrue(user.removePublication("10.1000/example"));
        assertFalse(user.removePublication("missing-doi"));

        user.setEmailAccountType(EmailAccountType.ACADEMIC);
        user.setEmail("new@example.edu");
        user.setPhoneNumber("987-654-3210");
        user.setInstitution(Institution.MIT);
        user.setAcademicLevel(AcademicLevel.GRADUATE_STUDENT);
        user.setResearchField(ResearchField.COMPUTER_SCIENCE);
        user.setLookingFor(CollaborationType.CO_AUTHOR);
        user.setCollaborationDescription("New collaboration description");
        user.setResearchDescription("New research description");
        user.setWeeklyAvailabilityHours(20);
        user.setFundingStatus(FundingStatus.SELF_FUNDED);
        user.setPasswordHash("new-hash");

        assertEquals(EmailAccountType.ACADEMIC, user.getEmailAccountType());
        assertEquals("new@example.edu", user.getEmail());
        assertEquals("987-654-3210", user.getPhoneNumber());
        assertEquals(Institution.MIT, user.getInstitution());
        assertEquals(AcademicLevel.GRADUATE_STUDENT, user.getAcademicLevel());
        assertEquals(ResearchField.COMPUTER_SCIENCE, user.getResearchField());
        assertEquals(CollaborationType.CO_AUTHOR, user.getLookingFor());
        assertEquals("New collaboration description", user.getCollaborationDescription());
        assertEquals("New research description", user.getResearchDescription());
        assertEquals(20, user.getWeeklyAvailabilityHours());
        assertEquals(FundingStatus.SELF_FUNDED, user.getFundingStatus());
        assertEquals("new-hash", user.getPasswordHash());
    }
}
