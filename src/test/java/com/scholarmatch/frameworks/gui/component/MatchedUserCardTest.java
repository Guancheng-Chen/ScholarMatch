package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.usecase.dto.UserData;

import org.junit.jupiter.api.Test;

import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchedUserCardTest {

    @Test
    void testReflowClampsBelowTheMinimumCardWidth() {
        final MatchedUserCard card = new MatchedUserCard(sampleUser("Ada", "Lovelace"));

        card.reflow(100);

        assertEquals(360, card.getMaximumSize().width);
    }

    @Test
    void testReflowClampsAboveTheMaximumCardWidth() {
        final MatchedUserCard card = new MatchedUserCard(sampleUser("Ada", "Lovelace"));

        card.reflow(5000);

        assertEquals(1040, card.getMaximumSize().width);
    }

    @Test
    void testReflowWithinRangeUsesTheGivenWidth() {
        final MatchedUserCard card = new MatchedUserCard(sampleUser("Ada", "Lovelace"));

        card.reflow(700);

        assertEquals(700, card.getMaximumSize().width);
    }

    @Test
    void testShowsContactInfoUnlikeTheRecommendCard() {
        final MatchedUserCard card = new MatchedUserCard(sampleUser("Ada", "Lovelace"));

        final boolean showsContactInfo = SwingTestSupport.findAll(card, JLabel.class).stream()
                .anyMatch(label -> label.getText() != null && label.getText().contains("Email:"));

        assertTrue(showsContactInfo);
    }

    @Test
    void testBlankNameFallsBackToQuestionMarkInitialInsteadOfThrowing() {
        final MatchedUserCard card = new MatchedUserCard(sampleUser("", ""));

        assertTrue(SwingTestSupport.findAll(card, JLabel.class).size() > 0);
    }

    private UserData sampleUser(final String firstName, final String lastName) {
        final User user = new User(
                "user-1", firstName, lastName, "ada@example.com", "555-0000",
                Institution.UNIVERSITY_OF_CAMBRIDGE, AcademicLevel.FACULTY, ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR, "Looking for co-authors", "Analytical engines and algorithms",
                8, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        return UserData.from(user);
    }
}

