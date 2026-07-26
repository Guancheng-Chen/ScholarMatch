package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.RecommendViewModel;
import com.scholarmatch.usecase.connect.ConnectOutputData;
import com.scholarmatch.usecase.dto.MessageData;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.load_matches.LoadMatchesOutputData;
import com.scholarmatch.usecase.load_message.LoadMessageOutputData;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;
import com.scholarmatch.usecase.recommend.RecommendOutputData;
import com.scholarmatch.usecase.send_message.SendMessageOutputData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CollaborationPresenterTest {

    @Test
    void testConnectReportsMatchAndNoMatch() {
        final LoadMatchesViewModel viewModel = new LoadMatchesViewModel();
        final ConnectPresenter presenter = new ConnectPresenter(viewModel);
        final UserData user = mock(UserData.class);

        presenter.prepareNoMatch();
        presenter.prepareMatchFound(new ConnectOutputData(user));

        assertSame(user, viewModel.matchNotificationProperty().get());
    }

    @Test
    void testNoOpCardPresentersCanHandleOutcomes() {
        final DislikePresenter dislikePresenter = new DislikePresenter();
        dislikePresenter.prepareSuccessView();
        dislikePresenter.prepareFailView("ignored");

        new SkipPresenter().prepareSuccessView();
    }

    @Test
    void testLoadMatchesReportsSuccessAndFailure() {
        final LoadMatchesViewModel viewModel = new LoadMatchesViewModel();
        final LoadMatchesPresenter presenter = new LoadMatchesPresenter(viewModel);
        final UserData user = mock(UserData.class);

        presenter.prepareSuccessView(new LoadMatchesOutputData(List.of(user)));
        presenter.prepareFailView("cannot load matches");

        assertEquals(List.of(user), viewModel.getMatchedUsers());
        assertEquals("cannot load matches", viewModel.errorMessageProperty().get());
    }

    @Test
    void testMessagePresentersReportOutcomes() {
        final ChatViewModel viewModel = new ChatViewModel();
        final MessageData first = message("message-1");
        final MessageData second = message("message-2");

        final LoadMessagePresenter loadPresenter = new LoadMessagePresenter(viewModel);
        loadPresenter.prepareSuccessView(new LoadMessageOutputData(List.of(first)));
        loadPresenter.prepareFailView("cannot load messages");

        final SendMessagePresenter sendPresenter = new SendMessagePresenter(viewModel);
        sendPresenter.prepareSuccessView(new SendMessageOutputData(second));
        sendPresenter.prepareFailView("cannot send message");

        assertEquals(List.of(first, second), viewModel.getMessages());
        assertEquals("cannot send message", viewModel.errorMessageProperty().get());
    }

    @Test
    void testPaperLookupReportsCandidatesPapersAndErrors() {
        final PaperLookupViewModel viewModel = new PaperLookupViewModel();
        final PaperLookupPresenter presenter = new PaperLookupPresenter(viewModel);
        final AuthorCandidateData candidate = mock(AuthorCandidateData.class);
        final Publication paper = new Publication("10.1000/example", "Paper", 2026, 1);

        presenter.prepareAuthorCandidates(List.of());
        assertEquals("No authors found.", viewModel.statusMessageProperty().get());
        presenter.prepareAuthorCandidates(List.of(candidate));
        assertEquals("", viewModel.statusMessageProperty().get());
        assertEquals(List.of(candidate), viewModel.getAuthorCandidates());

        presenter.prepareAuthorPapersFound(List.of());
        assertEquals("No papers found for this author.", viewModel.statusMessageProperty().get());
        presenter.prepareAuthorPapersFound(List.of(paper));
        assertEquals("", viewModel.statusMessageProperty().get());
        assertEquals(List.of(paper), viewModel.getAuthorPapersFound());

        presenter.prepareError("service unavailable");
        assertEquals("Lookup failed: service unavailable Please wait a moment and try again.",
                viewModel.statusMessageProperty().get());
    }

    @Test
    void testRecommendReportsSuccessAndFailure() {
        final RecommendViewModel viewModel = new RecommendViewModel();
        final RecommendPresenter presenter = new RecommendPresenter(viewModel);
        final UserData user = mock(UserData.class);
        when(user.getUserId()).thenReturn("user-2");

        presenter.prepareSuccessView(new RecommendOutputData(List.of(user)));
        assertEquals(List.of(user), viewModel.getCardStack());
        assertEquals("", viewModel.errorMessageProperty().get());

        presenter.prepareFailView("cannot recommend");
        assertEquals(List.of(), viewModel.getCardStack());
        assertEquals("cannot recommend", viewModel.errorMessageProperty().get());
    }

    private MessageData message(final String messageId) {
        return new MessageData(
                messageId, "sender-1", "receiver-1", "Hello", LocalDateTime.now());
    }
}
