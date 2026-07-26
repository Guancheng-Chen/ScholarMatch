package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.MyApplicationsViewModel;
import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.interface_adapter.view_model.OpportunitiesViewModel;
import com.scholarmatch.usecase.accept_application.AcceptApplicationOutputData;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingOutputData;
import com.scholarmatch.usecase.close_posting.ClosePostingOutputData;
import com.scholarmatch.usecase.create_posting.CreatePostingOutputData;
import com.scholarmatch.usecase.decline_application.DeclineApplicationOutputData;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsOutputData;
import com.scholarmatch.usecase.load_postings.LoadPostingsOutputData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostingPresenterTest {

    @Test
    void testAcceptApplicationUpdatesStatusAndRequestsRefresh() {
        final MyPostingsViewModel viewModel = new MyPostingsViewModel();
        final PostingApplicationData oldApplication = application("application-1", "posting-1");
        final PostingApplicationData updatedApplication = application("application-1", "posting-1");
        viewModel.setApplicationsByPostingId(Map.of("posting-1", List.of(oldApplication)));

        final AcceptApplicationPresenter presenter = new AcceptApplicationPresenter(viewModel);
        presenter.prepareFailView("failed");
        presenter.prepareSuccessView(new AcceptApplicationOutputData(updatedApplication));

        assertEquals("", viewModel.errorMessageProperty().get());
        assertEquals("Application accepted.", viewModel.successMessageProperty().get());
        assertEquals(List.of(updatedApplication), viewModel.getApplicationsFor("posting-1"));
        assertEquals(1, viewModel.refreshRequestProperty().get());
    }

    @Test
    void testApplyRemovesPostingAndReportsOutcomes() {
        final OpportunitiesViewModel viewModel = new OpportunitiesViewModel();
        final PostingData posting = posting("posting-1");
        viewModel.setPostings(List.of(posting));
        final ApplyToPostingPresenter presenter = new ApplyToPostingPresenter(viewModel);

        presenter.prepareFailView("failed");
        presenter.prepareSuccessView(new ApplyToPostingOutputData(
                application("application-1", "posting-1")));

        assertEquals(List.of(), viewModel.getPostings());
        assertEquals("", viewModel.errorMessageProperty().get());
        assertEquals("Application submitted.", viewModel.successMessageProperty().get());
    }

    @Test
    void testCloseReplacesPostingAndReportsOutcomes() {
        final MyPostingsViewModel viewModel = new MyPostingsViewModel();
        final PostingData oldPosting = posting("posting-1");
        final PostingData closedPosting = posting("posting-1");
        viewModel.setPostings(List.of(oldPosting));
        final ClosePostingPresenter presenter = new ClosePostingPresenter(viewModel);

        presenter.prepareFailView("failed");
        presenter.prepareSuccessView(new ClosePostingOutputData(closedPosting));

        assertEquals(List.of(closedPosting), viewModel.getPostings());
        assertEquals("", viewModel.errorMessageProperty().get());
        assertEquals("Posting closed.", viewModel.successMessageProperty().get());
    }

    @Test
    void testCreatePrependsPostingAndReportsOutcomes() {
        final MyPostingsViewModel viewModel = new MyPostingsViewModel();
        final PostingData oldPosting = posting("posting-1");
        final PostingData newPosting = posting("posting-2");
        viewModel.setPostings(List.of(oldPosting));
        final CreatePostingPresenter presenter = new CreatePostingPresenter(viewModel);

        presenter.prepareFailView("failed");
        presenter.prepareSuccessView(new CreatePostingOutputData(newPosting));

        assertEquals(List.of(newPosting, oldPosting), viewModel.getPostings());
        assertEquals("", viewModel.errorMessageProperty().get());
        assertEquals("Posting created.", viewModel.successMessageProperty().get());
    }

    @Test
    void testDeclineUpdatesApplicationAndReportsOutcomes() {
        final MyPostingsViewModel viewModel = new MyPostingsViewModel();
        final PostingApplicationData oldApplication = application("application-1", "posting-1");
        final PostingApplicationData updatedApplication = application("application-1", "posting-1");
        viewModel.setApplicationsByPostingId(Map.of("posting-1", List.of(oldApplication)));
        final DeclineApplicationPresenter presenter = new DeclineApplicationPresenter(viewModel);

        presenter.prepareFailView("failed");
        presenter.prepareSuccessView(new DeclineApplicationOutputData(updatedApplication));

        assertEquals(List.of(updatedApplication), viewModel.getApplicationsFor("posting-1"));
        assertEquals("", viewModel.errorMessageProperty().get());
        assertEquals("Application declined.", viewModel.successMessageProperty().get());
    }

    @Test
    void testLoadMyApplicationsReportsOutcomes() {
        final MyApplicationsViewModel viewModel = new MyApplicationsViewModel();
        final PostingApplicationData application = application("application-1", "posting-1");
        final LoadMyApplicationsPresenter presenter =
                new LoadMyApplicationsPresenter(viewModel);

        presenter.prepareFailView("failed");
        presenter.prepareSuccessView(new LoadMyApplicationsOutputData(List.of(application)));

        assertEquals(List.of(application), viewModel.getApplications());
        assertEquals("", viewModel.errorMessageProperty().get());
    }

    @Test
    void testLoadPostingsReportsOutcomes() {
        final OpportunitiesViewModel viewModel = new OpportunitiesViewModel();
        final PostingData posting = posting("posting-1");
        final LoadPostingsPresenter presenter = new LoadPostingsPresenter(viewModel);

        presenter.prepareFailView("failed");
        presenter.prepareSuccessView(new LoadPostingsOutputData(List.of(posting), Map.of()));

        assertEquals(List.of(posting), viewModel.getPostings());
        assertEquals("", viewModel.errorMessageProperty().get());
    }

    private PostingData posting(final String postingId) {
        final PostingData posting = mock(PostingData.class);
        when(posting.getPostingId()).thenReturn(postingId);
        return posting;
    }

    private PostingApplicationData application(
            final String applicationId,
            final String postingId) {
        final PostingApplicationData application = mock(PostingApplicationData.class);
        when(application.getApplicationId()).thenReturn(applicationId);
        when(application.getPostingId()).thenReturn(postingId);
        return application;
    }
}
