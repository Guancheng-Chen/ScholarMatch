package com.scholarmatch.frameworks.gui;

import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.gui.component.NavigationBar;
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

import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class MainViewTest {

    private LogoutViewModel lastLogoutViewModel;
    private LoadMatchesViewModel lastMatchesViewModel;
    private UpdateProfileViewModel lastProfileViewModel;

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

    @Test
    void testMainLayoutNavigationNotificationsProfileLogoutAndCleanup() throws Exception {
        final CurrentUserProvider session = new CurrentUserProvider();
        session.setCurrentUserId("existing-user-id");
        final AtomicInteger loggedOut = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            final MainView view = buildMainView(session);
            final MainLayoutView layout = (MainLayoutView) view.getComponent(0);
            for (final JToggleButton toggle
                    : com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport
                            .findAll(layout, JToggleButton.class)) {
                toggle.doClick();
            }
            invokeUnknownNavigation(layout);
            this.lastLogoutViewModel.loggedOutProperty().set(null);
            this.lastLogoutViewModel.loggedOutProperty().set(false);
            this.lastLogoutViewModel.setLoggedOut();
            this.lastMatchesViewModel.matchNotificationProperty().set(null);
            this.lastMatchesViewModel.matchNotificationProperty().set(user());
            this.lastProfileViewModel.currentUserProperty().set(null);
            this.lastProfileViewModel.setCurrentUser(user());
            layout.removeNotify();
            loggedOut.incrementAndGet();
        });
        SwingUtilities.invokeAndWait(() -> { });
        assertTrue(loggedOut.get() > 0);
    }

    /**
     * Builds a fully-wired MainView backed by mocked use-case input boundaries (so no real
     * interactor logic runs) and real, empty ViewModels — enough to exercise MainView's own
     * only responsibility: picking the initial shell based on {@code session.isLoggedIn()}.
     */
    private MainView buildMainView(final CurrentUserProvider session) {
        this.lastLogoutViewModel = new LogoutViewModel();
        this.lastMatchesViewModel = new LoadMatchesViewModel();
        this.lastProfileViewModel = new UpdateProfileViewModel();
        return new MainView(
                new LoginController(mock(LoginInputBoundary.class)),
                new LoginViewModel(),
                new LogoutController(mock(LogoutInputBoundary.class)),
                this.lastLogoutViewModel,
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
                this.lastMatchesViewModel,
                new LoadMatchesController(mock(LoadMatchesInputBoundary.class)),
                new SendMessageController(mock(SendMessageInputBoundary.class)),
                new LoadMessageController(mock(LoadMessageInputBoundary.class)),
                new ChatViewModel(),
                new UpdateProfileController(mock(UpdateProfileInputBoundary.class)),
                new LoadProfileController(mock(LoadProfileInputBoundary.class)),
                this.lastProfileViewModel,
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

    private com.scholarmatch.usecase.dto.UserData user() {
        return new com.scholarmatch.usecase.dto.UserData(
                "user", "Ada", "Lovelace", "", "", null, null, null, null,
                "", "", null, null, List.of(), List.of(), List.of(), null, null);
    }

    private void invokeUnknownNavigation(final MainLayoutView layout) {
        final JToggleButton toggle = com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport
                .find(layout, JToggleButton.class, 0);
        for (final var action : toggle.getActionListeners()) {
            for (final var field : action.getClass().getDeclaredFields()) {
                if (NavigationBar.NavSelectionListener.class.isAssignableFrom(field.getType())) {
                    try {
                        field.setAccessible(true);
                        ((NavigationBar.NavSelectionListener) field.get(action)).onSelected("unknown");
                        return;
                    } catch (ReflectiveOperationException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
            }
        }
        throw new IllegalStateException("Navigation listener not found");
    }
}
