package com.scholarmatch.frameworks.gui;

import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.gui.component.MatchToastOverlay;
import com.scholarmatch.frameworks.gui.component.NavigationBar;
import com.scholarmatch.frameworks.gui.style.Icons;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.frameworks.gui.view.ChatView;
import com.scholarmatch.frameworks.gui.view.LoadMatchesView;
import com.scholarmatch.frameworks.gui.view.RecommendView;
import com.scholarmatch.frameworks.gui.view.UpdateProfileView;
import com.scholarmatch.frameworks.gui.view.OpportunitiesView;
import com.scholarmatch.frameworks.gui.view.MyPostingsView;
import com.scholarmatch.frameworks.gui.view.MyApplicationsView;
import com.scholarmatch.frameworks.gui.view.AccountSettingsView;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import com.scholarmatch.interface_adapter.connect.ConnectController;
import com.scholarmatch.interface_adapter.delete_account.DeleteAccountController;
import com.scholarmatch.interface_adapter.dislike.DislikeController;
import com.scholarmatch.interface_adapter.load_matches.LoadMatchesController;
import com.scholarmatch.interface_adapter.load_message.LoadMessageController;
import com.scholarmatch.interface_adapter.load_profile.LoadProfileController;
import com.scholarmatch.interface_adapter.logout.LogoutController;
import com.scholarmatch.interface_adapter.paper_lookup.PaperLookupController;
import com.scholarmatch.interface_adapter.recommend.RecommendController;
import com.scholarmatch.interface_adapter.send_message.SendMessageController;
import com.scholarmatch.interface_adapter.skip.SkipController;
import com.scholarmatch.interface_adapter.update_profile.UpdateProfileController;
import com.scholarmatch.interface_adapter.create_posting.CreatePostingController;
import com.scholarmatch.interface_adapter.close_posting.ClosePostingController;
import com.scholarmatch.interface_adapter.load_postings.LoadPostingsController;
import com.scholarmatch.interface_adapter.apply_to_posting.ApplyToPostingController;
import com.scholarmatch.interface_adapter.accept_application.AcceptApplicationController;
import com.scholarmatch.interface_adapter.decline_application.DeclineApplicationController;
import com.scholarmatch.interface_adapter.load_my_applications.LoadMyApplicationsController;
import com.scholarmatch.interface_adapter.change_email.ChangeEmailController;
import com.scholarmatch.interface_adapter.change_password.ChangePasswordController;
import com.scholarmatch.interface_adapter.request_email_verification.RequestEmailVerificationController;
import com.scholarmatch.interface_adapter.view_model.chat.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.delete_account.DeleteAccountViewModel;
import com.scholarmatch.interface_adapter.view_model.load_matches.LoadMatchesViewModel;
import com.scholarmatch.interface_adapter.view_model.logout.LogoutViewModel;
import com.scholarmatch.interface_adapter.view_model.paper_lookup.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.recommend.RecommendViewModel;
import com.scholarmatch.interface_adapter.view_model.update_profile.UpdateProfileViewModel;
import com.scholarmatch.interface_adapter.view_model.opportunities.OpportunitiesViewModel;
import com.scholarmatch.interface_adapter.view_model.my_postings.MyPostingsViewModel;
import com.scholarmatch.interface_adapter.view_model.my_applications.MyApplicationsViewModel;
import com.scholarmatch.interface_adapter.view_model.account_settings.AccountSettingsViewModel;
import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Authenticated app layout.
 *
 * <p>NavigationBar as a left sidebar (Recommend / View Matched / Chat / Opportunities /
 * My Postings / My Applications / Profile / Account Settings / Logout) plus a center pane
 * that swaps between the corresponding view as the user navigates — Recommend is shown
 * first.
 *
 * <p>Calls the given onLoggedOut callback once logout succeeds — the session is already
 * cleared by LogoutInteractor at that point, so this view only needs to signal the
 * caller (MainView) to swap back to the logged-out shell, not manage the session itself.
 */
final class MainLayoutView extends JPanel {

    private final List<Runnable> listenerRemovers = new ArrayList<>();

    /**
     * Constructs the MainLayoutView.
     *
     * @param recommendController     loads connect-card recommendations
     * @param connectController       handles connect actions
     * @param dislikeController       handles dislike actions
     * @param skipController          handles skip actions
     * @param recommendViewModel      observable card-stack state
     * @param loadMatchesViewModel    observable mutual matches list
     * @param loadMatchesController   loads the current user's confirmed matches
     * @param sendMessageController   sends a chat message to a matched user
     * @param loadMessageController   loads the conversation history with a matched user
     * @param chatViewModel           observable state for the currently open conversation
     * @param updateProfileController handles profile-edit form submission
     * @param loadProfileController   loads the current user's full saved profile
     * @param updateProfileViewModel  observable profile-edit state
     * @param paperLookupController   handles paper/author auto-fill searches (profile editing)
     * @param paperLookupViewModel    observable paper/author auto-fill search state
     * @param logoutController        handles logout requests
     * @param logoutViewModel         observable logout state
     * @param deleteAccountController handles account-deletion confirmation
     * @param deleteAccountViewModel  observable state for a failed deletion attempt
     * @param currentUserProvider     the shared session (its user ID seeds chatViewModel so
     *                                ChatView can tell "mine" messages from "theirs")
     * @param onLoggedOut             invoked once logout succeeds
     */
    MainLayoutView(
        final RecommendController recommendController,
        final ConnectController connectController,
        final DislikeController dislikeController,
        final SkipController skipController,
        final RecommendViewModel recommendViewModel,
        final LoadMatchesViewModel loadMatchesViewModel,
        final LoadMatchesController loadMatchesController,
        final SendMessageController sendMessageController,
        final LoadMessageController loadMessageController,
        final ChatViewModel chatViewModel,
        final UpdateProfileController updateProfileController,
        final LoadProfileController loadProfileController,
        final UpdateProfileViewModel updateProfileViewModel,
        final RequestEmailVerificationController requestEmailChangeController,
        final ChangeEmailController changeEmailController,
        final ChangePasswordController changePasswordController,
        final AccountSettingsViewModel accountSettingsViewModel,
        final PaperLookupController paperLookupController,
        final PaperLookupViewModel paperLookupViewModel,
        final LogoutController logoutController,
        final LogoutViewModel logoutViewModel,
        final DeleteAccountController deleteAccountController,
        final DeleteAccountViewModel deleteAccountViewModel,
        final CreatePostingController createPostingController,
        final ClosePostingController closePostingController,
        final LoadPostingsController opportunitiesLoadPostingsController,
        final LoadPostingsController myPostingsLoadPostingsController,
        final ApplyToPostingController applyToPostingController,
        final AcceptApplicationController acceptApplicationController,
        final DeclineApplicationController declineApplicationController,
        final LoadMyApplicationsController loadMyApplicationsController,
        final OpportunitiesViewModel opportunitiesViewModel,
        final MyPostingsViewModel myPostingsViewModel,
        final MyApplicationsViewModel myApplicationsViewModel,
        final CurrentUserProvider currentUserProvider,
        final Runnable onLoggedOut) {
        super(new BorderLayout());
        setBackground(Theme.BG_DEFAULT);

        // MainLayoutView is only ever shown once a session is established (see MainView),
        // so the session's user ID is available here to hand off to the chat view model.
        chatViewModel.setCurrentUserId(currentUserProvider.getCurrentUserId());

        final JPanel centerHolder = new JPanel(new BorderLayout());
        centerHolder.setOpaque(false);

        // The logout use case is the one that clears the session (via LogoutInteractor);
        // this view only reacts once LogoutViewModel confirms it happened.
        listen(logoutViewModel.loggedOutProperty(), loggedOut -> {
            if (Boolean.TRUE.equals(loggedOut)) {
                SwingUtilities.invokeLater(onLoggedOut);
            }
        });

        final NavigationBar navBar = new NavigationBar(target -> {
            centerHolder.removeAll();
            switch (target) {
                case "recommend" -> centerHolder.add(
                    new RecommendView(recommendController, connectController, dislikeController, skipController, recommendViewModel),
                    BorderLayout.CENTER);
                case "matched" -> centerHolder.add(
                    new LoadMatchesView(loadMatchesController, loadMatchesViewModel), BorderLayout.CENTER);
                case "chat" -> centerHolder.add(
                    new ChatView(sendMessageController, loadMessageController, loadMatchesController,
                        chatViewModel, loadMatchesViewModel),
                    BorderLayout.CENTER);
                case "opportunities" -> centerHolder.add(
                    new OpportunitiesView(
                        opportunitiesLoadPostingsController, applyToPostingController,
                        opportunitiesViewModel),
                    BorderLayout.CENTER);
                case "my-postings" -> centerHolder.add(
                    new MyPostingsView(
                        createPostingController, myPostingsLoadPostingsController,
                        closePostingController,
                        acceptApplicationController, declineApplicationController,
                        myPostingsViewModel),
                    BorderLayout.CENTER);
                case "my-applications" -> centerHolder.add(
                    new MyApplicationsView(loadMyApplicationsController, myApplicationsViewModel),
                    BorderLayout.CENTER);
                case "profile" -> centerHolder.add(
                    new UpdateProfileView(updateProfileController, loadProfileController, updateProfileViewModel,
                        paperLookupController, paperLookupViewModel),
                    BorderLayout.CENTER);
                case "settings" -> centerHolder.add(
                    new AccountSettingsView(
                        requestEmailChangeController, changeEmailController,
                        changePasswordController, accountSettingsViewModel,
                        deleteAccountController, deleteAccountViewModel),
                    BorderLayout.CENTER);
                default -> { }
            }
            centerHolder.revalidate();
            centerHolder.repaint();
        }, logoutController::logout);

        final JPanel topBar = buildTopBar(updateProfileViewModel);
        loadProfileController.execute();
        final com.scholarmatch.usecase.dto.UserData currentUser =
            updateProfileViewModel.currentUserProperty().get();
        if (currentUser != null) {
            accountSettingsViewModel.setCurrentEmail(currentUser.getEmail());
        }
        listen(updateProfileViewModel.currentUserProperty(), user -> {
            if (user != null) {
                accountSettingsViewModel.setCurrentEmail(user.getEmail());
            }
        });

        final JPanel contentColumn = new JPanel(new BorderLayout());
        contentColumn.setOpaque(false);
        contentColumn.add(topBar, BorderLayout.NORTH);
        contentColumn.add(centerHolder, BorderLayout.CENTER);

        final JPanel shell = new JPanel(new BorderLayout());
        shell.setOpaque(false);
        shell.add(navBar, BorderLayout.WEST);
        shell.add(contentColumn, BorderLayout.CENTER);

        // Wrapped in a toast overlay (rather than adding navBar/contentColumn straight to
        // this panel) so a "You matched with X!" banner can float top-right regardless of
        // which tab is open — match formation happens on the Recommend tab, but
        // LoadMatchesView/ChatView (the views that actually list matches) only refresh
        // when the user navigates to them, so without this the user would have no
        // immediate feedback that a match just happened.
        final MatchToastOverlay toastOverlay = new MatchToastOverlay();
        toastOverlay.setContent(shell);
        listen(loadMatchesViewModel.matchNotificationProperty(), matchedUser -> {
            if (matchedUser != null) {
                toastOverlay.showToast(
                    "You matched with " + matchedUser.getFirstName() + " " + matchedUser.getLastName() + "!");
            }
        });

        centerHolder.add(
            new RecommendView(recommendController, connectController, dislikeController, skipController, recommendViewModel),
            BorderLayout.CENTER);
        add(toastOverlay, BorderLayout.CENTER);
    }

    /**
     * Builds the slim top bar showing the current user's name, right-aligned, aligned with
     * the sidebar logo row. Populated once com.scholarmatch.interface_adapter.load_profile.LoadProfileController
     * completes.
     */
    private JPanel buildTopBar(final UpdateProfileViewModel updateProfileViewModel) {
        final JLabel userLabel = new JLabel(
            "", Icons.of(FontAwesomeSolid.USER_CIRCLE, 16, Theme.FG_MUTED), SwingConstants.RIGHT);
        userLabel.setIconTextGap(8);
        userLabel.setForeground(Theme.FG_DEFAULT);
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 14f));

        listen(updateProfileViewModel.currentUserProperty(), user -> SwingUtilities.invokeLater(() -> {
            if (user != null) {
                userLabel.setText(user.getFirstName() + " " + user.getLastName());
            }
        }));

        final JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Theme.BG_SUBTLE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(0, 0, 0, 20)));
        topBar.setPreferredSize(new Dimension(0, 56));
        topBar.add(userLabel, BorderLayout.EAST);
        return topBar;
    }

    private <T> void listen(
            final ObservableValue<T> observable,
            final Consumer<T> listener) {
        observable.addListener(listener);
        this.listenerRemovers.add(() -> observable.removeListener(listener));
    }

    @Override
    public void removeNotify() {
        for (final Runnable removeListener : this.listenerRemovers) {
            removeListener.run();
        }
        this.listenerRemovers.clear();
        super.removeNotify();
    }
}
