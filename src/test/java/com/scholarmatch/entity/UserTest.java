package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {
    @Test
    void testAddResearchInterest() {
        final User user = new User(
                "id1",
                "Alice",
                "Zhang",
                "alice@example.com",
                "",
                "UofT",
                AcademicLevel.FACULTY,
                CollaborationType.INTEREST_SHARING,
                "",
                "",
                "hash"
        );

        user.addResearchInterest("machine learning");

        assertTrue(user.getResearchInterests().contains("machine learning"));
    }

    @Test
    void testRemoveResearchInterest() {
        final User user = new User(
                "id1",
                "Alice",
                "Zhang",
                "alice@example.com",
                "",
                "UofT",
                AcademicLevel.FACULTY,
                CollaborationType.INTEREST_SHARING,
                "",
                "",
                "hash"
        );

        user.addResearchInterest("NLP");
        final boolean removed = user.removeResearchInterest("NLP");

        assertTrue(removed);
        assertEquals(0, user.getResearchInterests().size());
    }
}
