package com.scholarmatch.frameworks.gui;

import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.interface_adapter.controller.ConnectController;
import com.scholarmatch.interface_adapter.controller.DeleteAccountController;
import com.scholarmatch.interface_adapter.controller.DislikeController;
import com.scholarmatch.interface_adapter.controller.LoadMatchesController;
import com.scholarmatch.interface_adapter.controller.LoadMessageController;
import com.scholarmatch.interface_adapter.controller.LoadProfileController;
import com.scholarmatch.interface_adapter.controller.LoginController;
import com.scholarmatch.interface_adapter.controller.LogoutController;
import com.scholarmatch.interface_adapter.controller.PaperLookupController;
import com.scholarmatch.interface_adapter.controller.RecommendController;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.controller.RequestEmailVerificationController;
import com.scholarmatch.interface_adapter.controller.SendMessageController;
import com.scholarmatch.interface_adapter.controller.SkipController;
import com.scholarmatch.interface_adapter.controller.UpdateProfileController;
import com.scholarmatch.interface_adapter.controller.CreatePostingController;
import com.scholarmatch.interface_adapter.controller.ClosePostingController;
import com.scholarmatch.interface_adapter.controller.LoadPostingsController;
import com.scholarmatch.interface_adapter.controller.ApplyToPostingController;
import com.scholarmatch.interface_adapter.controller.AcceptApplicationController;
import com.scholarmatch.interface_adapter.controller.DeclineApplicationController;
import com.scholarmatch.interface_adapter.controller.LoadMyApplicationsController;
import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.DeleteAccountViewModel;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import com.scholarmatch.interface_adapter.view_model.LogoutViewModel;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.RecommendViewModel;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;
import com.scholarmatch.interface_adapter.view_model.OpportunitiesViewModel;
import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.interface_adapter.view_model.MyApplicationsViewModel;
import com.scholarmatch.usecase.connect.ConnectInputBoundary;
import com.scholarmatch.usecase.delete_account.DeleteAccountInputBoundary;
import com.scholarmatch.usecase.dislike.DislikeInputBoundary;
import com.scholarmatch.usecase.load_matches.LoadMatchesInputBoundary;
import com.scholarmatch.usecase.load_message.LoadMessageInputBoundary;
import com.scholarmatch.usecase.load_profile.LoadProfileInputBoundary;
import com.scholarmatch.usecase.login.LoginInputBoundary;
import com.scholarmatch.usecase.logout.LogoutInputBoundary;
import com.scholarmatch.usecase.paper_lookup.PaperLookupInputBoundary;
import com.scholarmatch.usecase.recommend.RecommendInputBoundary;
import com.scholarmatch.usecase.register.RegisterInputBoundary;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInputBoundary;
import com.scholarmatch.usecase.send_message.SendMessageInputBoundary;
import com.scholarmatch.usecase.skip.SkipInputBoundary;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputBoundary;
import com.scholarmatch.usecase.create_posting.CreatePostingInputBoundary;
import com.scholarmatch.usecase.close_posting.ClosePostingInputBoundary;
import com.scholarmatch.usecase.load_postings.LoadPostingsInputBoundary;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingInputBoundary;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInputBoundary;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInputBoundary;
import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsInputBoundary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class MainViewTest {

    @Test
    void testShowsAuthShellWhenNoSessionIsActive() {
        final CurrentUserProvider session = new CurrentUserProvider();

        final MainView view = buildMainView(session);

        assertEquals(1, view.getComponentCount());
        assertTrue(view.getComponent(0) instanceof AuthShellView);
    }

    @Test
    void testShowsMainLayoutWhenASessionIsAlreadyActive() {
        final CurrentUserProvider session = new CurrentUserProvider();
        session.setCurrentUserId("existing-user-id");
        session.setToken("existing-token");

        final MainView view = buildMainView(session);

        assertEquals(1, view.getComponentCount());
        assertTrue(view.getComponent(0) instanceof MainLayoutView);
    }

    /**
     * Builds a fully-wired MainView backed by mocked use-case input boundaries (so no real
     * interactor logic runs) and real, empty ViewModels — enough to exercise MainView's own
     * only responsibility: picking the initial shell based on {@code session.isLoggedIn()}.
     */
    private MainView buildMainView(final CurrentUserProvider session) {
        return new MainView(
                new LoginController(mock(LoginInputBoundary.class)),
                new LoginViewModel(),
                new LogoutController(mock(LogoutInputBoundary.class)),
                new LogoutViewModel(),
                new DeleteAccountController(mock(DeleteAccountInputBoundary.class)),
                new DeleteAccountViewModel(),
                new RegisterController(mock(RegisterInputBoundary.class)),
                new RequestEmailVerificationController(mock(RequestEmailVerificationInputBoundary.class)),
                new RegisterViewModel(),
                new PaperLookupController(mock(PaperLookupInputBoundary.class)),
                new PaperLookupViewModel(),
                new RecommendController(mock(RecommendInputBoundary.class)),
                new ConnectController(mock(ConnectInputBoundary.class)),
                new DislikeController(mock(DislikeInputBoundary.class)),
                new SkipController(mock(SkipInputBoundary.class)),
                new RecommendViewModel(),
                new LoadMatchesViewModel(),
                new LoadMatchesController(mock(LoadMatchesInputBoundary.class)),
                new SendMessageController(mock(SendMessageInputBoundary.class)),
                new LoadMessageController(mock(LoadMessageInputBoundary.class)),
                new ChatViewModel(),
                new UpdateProfileController(mock(UpdateProfileInputBoundary.class)),
                new LoadProfileController(mock(LoadProfileInputBoundary.class)),
                new UpdateProfileViewModel(),
                new CreatePostingController(mock(CreatePostingInputBoundary.class)),
                new ClosePostingController(mock(ClosePostingInputBoundary.class)),
                new LoadPostingsController(mock(LoadPostingsInputBoundary.class)),
                new LoadPostingsController(mock(LoadPostingsInputBoundary.class)),
                new ApplyToPostingController(mock(ApplyToPostingInputBoundary.class)),
                new AcceptApplicationController(mock(AcceptApplicationInputBoundary.class)),
                new DeclineApplicationController(mock(DeclineApplicationInputBoundary.class)),
                new LoadMyApplicationsController(mock(LoadMyApplicationsInputBoundary.class)),
                new OpportunitiesViewModel(),
                new MyPostingsViewModel(),
                new MyApplicationsViewModel(),
                session);
    }
}
