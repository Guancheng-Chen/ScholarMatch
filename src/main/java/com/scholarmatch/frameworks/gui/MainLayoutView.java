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
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import com.scholarmatch.interface_adapter.controller.ConnectController;
import com.scholarmatch.interface_adapter.controller.DeleteAccountController;
import com.scholarmatch.interface_adapter.controller.DislikeController;
import com.scholarmatch.interface_adapter.controller.LoadMatchesController;
import com.scholarmatch.interface_adapter.controller.LoadMessageController;
import com.scholarmatch.interface_adapter.controller.LoadProfileController;
import com.scholarmatch.interface_adapter.controller.LogoutController;
import com.scholarmatch.interface_adapter.controller.PaperLookupController;
import com.scholarmatch.interface_adapter.controller.RecommendController;
import com.scholarmatch.interface_adapter.controller.SendMessageController;
import com.scholarmatch.interface_adapter.controller.SkipController;
import com.scholarmatch.interface_adapter.controller.UpdateProfileController;
import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.DeleteAccountViewModel;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.interface_adapter.view_model.LogoutViewModel;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.RecommendViewModel;
import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Authenticated app layout.
 *
 * <p>NavigationBar as a left sidebar (Recommend / View Matched / Chat / Profile / Delete
 * Account / Logout) plus a center pane that swaps between the corresponding view as the
 * user navigates — Recommend is shown first.
 *
 * <p>Calls the given onLoggedOut callback once logout succeeds — the session is already
 * cleared by LogoutInteractor at that point, so this view only needs to signal the
 * caller (MainView) to swap back to the logged-out shell, not manage the session itself.
 */
final class MainLayoutView extends JPanel {

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
     * @param currentUserProvider     the shared session (used by ChatView to tell "mine" from "theirs")
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
        final PaperLookupController paperLookupController,
        final PaperLookupViewModel paperLookupViewModel,
        final LogoutController logoutController,
        final LogoutViewModel logoutViewModel,
        final DeleteAccountController deleteAccountController,
        final DeleteAccountViewModel deleteAccountViewModel,
        final CurrentUserProvider currentUserProvider,
        final Runnable onLoggedOut) {
        super(new BorderLayout());
        setBackground(Theme.BG_DEFAULT);

        final JPanel centerHolder = new JPanel(new BorderLayout());
        centerHolder.setOpaque(false);

        // The logout use case is the one that clears the session (via LogoutInteractor);
        // this view only reacts once LogoutViewModel confirms it happened.
        logoutViewModel.loggedOutProperty().addListener(loggedOut -> {
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
                        chatViewModel, loadMatchesViewModel, currentUserProvider),
                    BorderLayout.CENTER);
                case "profile" -> centerHolder.add(
                    new UpdateProfileView(updateProfileController, loadProfileController, updateProfileViewModel,
                        paperLookupController, paperLookupViewModel),
                    BorderLayout.CENTER);
                default -> { }
            }
            centerHolder.revalidate();
            centerHolder.repaint();
        }, logoutController::logout, deleteAccountController, deleteAccountViewModel);

        final JPanel topBar = buildTopBar(updateProfileViewModel);
        loadProfileController.execute();

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
        loadMatchesViewModel.matchNotificationProperty().addListener(matchedUser -> {
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
     * the sidebar logo row. Populated once com.scholarmatch.interface_adapter.controller.LoadProfileController
     * completes.
     */
    private JPanel buildTopBar(final UpdateProfileViewModel updateProfileViewModel) {
        final JLabel userLabel = new JLabel(
            "", Icons.of(FontAwesomeSolid.USER_CIRCLE, 16, Theme.FG_MUTED), SwingConstants.RIGHT);
        userLabel.setIconTextGap(8);
        userLabel.setForeground(Theme.FG_DEFAULT);
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 14f));

        updateProfileViewModel.currentUserProperty().addListener(user -> SwingUtilities.invokeLater(() -> {
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
}
