package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.component.MatchedUserCard;
import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.LoadMatchesController;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.load_matches.LoadMatchesInputBoundary;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoadMatchesViewTest {

    @Test
    void testConstructionRefreshesConfirmedMatches() {
        final LoadMatchesInputBoundary interactor = mock(LoadMatchesInputBoundary.class);

        new LoadMatchesView(new LoadMatchesController(interactor), new LoadMatchesViewModel());

        verify(interactor).execute();
    }

    @Test
    void testStartsWithNoCardsWhenThereAreNoMatchesYet() {
        final LoadMatchesView view = new LoadMatchesView(
                new LoadMatchesController(mock(LoadMatchesInputBoundary.class)), new LoadMatchesViewModel());

        assertEquals(0, SwingTestSupport.findAll(view, MatchedUserCard.class).size());
    }

    @Test
    void testRebuildsCardsWhenTheMatchedUsersListChanges() {
        final LoadMatchesViewModel viewModel = new LoadMatchesViewModel();
        final LoadMatchesView view = new LoadMatchesView(
                new LoadMatchesController(mock(LoadMatchesInputBoundary.class)), viewModel);

        viewModel.getMatchedUsers().setAll(List.of(sampleUser("match-1"), sampleUser("match-2")));

        assertEquals(2, SwingTestSupport.findAll(view, MatchedUserCard.class).size());
    }

    @Test
    void testClearingTheMatchedUsersListRemovesTheCards() {
        final LoadMatchesViewModel viewModel = new LoadMatchesViewModel();
        final LoadMatchesView view = new LoadMatchesView(
                new LoadMatchesController(mock(LoadMatchesInputBoundary.class)), viewModel);
        viewModel.getMatchedUsers().setAll(List.of(sampleUser("match-1")));

        viewModel.getMatchedUsers().clear();

        assertEquals(0, SwingTestSupport.findAll(view, MatchedUserCard.class).size());
    }

    private UserData sampleUser(final String userId) {
        final User user = new User(
                userId, "Ada", "Lovelace", "ada@example.com", "555-0000",
                Institution.UNIVERSITY_OF_CAMBRIDGE, AcademicLevel.FACULTY, ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR, "Looking for co-authors", "Analytical engines and algorithms",
                8, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        return UserData.from(user);
    }
}
