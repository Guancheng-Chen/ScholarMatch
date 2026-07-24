package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.ConnectController;
import com.scholarmatch.interface_adapter.controller.DislikeController;
import com.scholarmatch.interface_adapter.controller.RecommendController;
import com.scholarmatch.interface_adapter.controller.SkipController;
import com.scholarmatch.interface_adapter.view_model.RecommendViewModel;
import com.scholarmatch.usecase.connect.ConnectInputBoundary;
import com.scholarmatch.usecase.dislike.DislikeInputBoundary;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.recommend.RecommendInputBoundary;
import com.scholarmatch.usecase.skip.SkipInputBoundary;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RecommendViewTest {

    @Test
    void testConstructionRefreshesRecommendations() {
        final RecommendInputBoundary interactor = mock(RecommendInputBoundary.class);

        new RecommendView(
                new RecommendController(interactor),
                new ConnectController(mock(ConnectInputBoundary.class)),
                new DislikeController(mock(DislikeInputBoundary.class)),
                new SkipController(mock(SkipInputBoundary.class)),
                new RecommendViewModel());

        verify(interactor).execute();
    }

    @Test
    void testErrorMessageTakesPrecedenceOverEmptyStateEvenWithNoCards() throws Exception {
        final RecommendViewModel viewModel = new RecommendViewModel();
        final RecommendView view = new RecommendView(
                new RecommendController(mock(RecommendInputBoundary.class)),
                new ConnectController(mock(ConnectInputBoundary.class)),
                new DislikeController(mock(DislikeInputBoundary.class)),
                new SkipController(mock(SkipInputBoundary.class)),
                viewModel);

        SwingUtilities.invokeAndWait(() -> viewModel.setErrorMessage("Could not load recommendations"));

        final JLabel label = SwingTestSupport.find(view, JLabel.class, 0);
        assertEquals("Could not load recommendations", label.getText());
    }

    @Test
    void testEmptyCardStackShowsEmptyStateWhenThereIsNoError() {
        final RecommendViewModel viewModel = new RecommendViewModel();
        final RecommendView view = new RecommendView(
                new RecommendController(mock(RecommendInputBoundary.class)),
                new ConnectController(mock(ConnectInputBoundary.class)),
                new DislikeController(mock(DislikeInputBoundary.class)),
                new SkipController(mock(SkipInputBoundary.class)),
                viewModel);
        // The constructor only registers listeners and kicks off a refresh — nothing renders
        // until the card-stack (or error) listener actually fires, so trigger it explicitly.
        viewModel.setCardStack(List.of());

        final JLabel label = SwingTestSupport.find(view, JLabel.class, 0);
        assertEquals("No more recommendations right now.", label.getText());
    }

    @Test
    void testNullErrorAlsoShowsEmptyState() throws Exception {
        final RecommendViewModel viewModel = new RecommendViewModel();
        final RecommendView view = new RecommendView(
                new RecommendController(mock(RecommendInputBoundary.class)),
                new ConnectController(mock(ConnectInputBoundary.class)),
                new DislikeController(mock(DislikeInputBoundary.class)),
                new SkipController(mock(SkipInputBoundary.class)), viewModel);
        SwingUtilities.invokeAndWait(() -> viewModel.errorMessageProperty().set(null));
        assertEquals("No more recommendations right now.",
                SwingTestSupport.find(view, JLabel.class, 0).getText());
    }

    @Test
    void testDislikeCallsDislikeControllerAndPermanentlyExcludesTheUser() {
        final DislikeInputBoundary interactor = mock(DislikeInputBoundary.class);
        final RecommendViewModel viewModel = new RecommendViewModel();
        final UserData user = sampleUser("dislike-me");
        final RecommendView view = new RecommendView(
                new RecommendController(mock(RecommendInputBoundary.class)),
                new ConnectController(mock(ConnectInputBoundary.class)),
                new DislikeController(interactor),
                new SkipController(mock(SkipInputBoundary.class)),
                viewModel);
        viewModel.setCardStack(List.of(user));

        SwingTestSupport.find(view, JButton.class, 0).doClick();

        verify(interactor).execute(argThatHasUserId("dislike-me"));
        assertTrue(viewModel.getCardStack().isEmpty());

        // A future fetch that still includes this user must not resurface them.
        viewModel.setCardStack(List.of(user));
        assertTrue(viewModel.getCardStack().isEmpty());
    }

    @Test
    void testSkipCallsSkipControllerButDoesNotExcludeTheUser() {
        final SkipInputBoundary interactor = mock(SkipInputBoundary.class);
        final RecommendViewModel viewModel = new RecommendViewModel();
        final UserData user = sampleUser("skip-me");
        final RecommendView view = new RecommendView(
                new RecommendController(mock(RecommendInputBoundary.class)),
                new ConnectController(mock(ConnectInputBoundary.class)),
                new DislikeController(mock(DislikeInputBoundary.class)),
                new SkipController(interactor),
                viewModel);
        viewModel.setCardStack(List.of(user));

        SwingTestSupport.find(view, JButton.class, 1).doClick();

        verify(interactor).execute(org.mockito.ArgumentMatchers.argThat(
                data -> data != null && "skip-me".equals(data.getSkippedUserId())));
        assertTrue(viewModel.getCardStack().isEmpty());

        // Skip is not persisted — a later fetch is free to resurface the same user.
        viewModel.setCardStack(List.of(user));
        assertEquals(1, viewModel.getCardStack().size());
    }

    @Test
    void testConnectCallsConnectControllerAndPermanentlyExcludesTheUser() {
        final ConnectInputBoundary interactor = mock(ConnectInputBoundary.class);
        final RecommendViewModel viewModel = new RecommendViewModel();
        final UserData user = sampleUser("connect-me");
        final RecommendView view = new RecommendView(
                new RecommendController(mock(RecommendInputBoundary.class)),
                new ConnectController(interactor),
                new DislikeController(mock(DislikeInputBoundary.class)),
                new SkipController(mock(SkipInputBoundary.class)),
                viewModel);
        viewModel.setCardStack(List.of(user));

        SwingTestSupport.find(view, JButton.class, 2).doClick();

        verify(interactor, times(1)).execute(org.mockito.ArgumentMatchers.argThat(
                data -> data != null && "connect-me".equals(data.getConnectedUserId())
                        && data.getConnectedUser() == user));
        assertTrue(viewModel.getCardStack().isEmpty());

        viewModel.setCardStack(List.of(user));
        assertTrue(viewModel.getCardStack().isEmpty());
    }

    private static com.scholarmatch.usecase.dislike.DislikeInputData argThatHasUserId(final String userId) {
        return org.mockito.ArgumentMatchers.argThat(data -> data != null && userId.equals(data.getDislikedUserId()));
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
