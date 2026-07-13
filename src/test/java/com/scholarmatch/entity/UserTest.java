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
}
