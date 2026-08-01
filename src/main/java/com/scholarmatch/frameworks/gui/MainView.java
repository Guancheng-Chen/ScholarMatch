package com.scholarmatch.frameworks.gui;

import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.delete_account.DeleteAccountController;
import com.scholarmatch.interface_adapter.load_matches.LoadMatchesController;
import com.scholarmatch.interface_adapter.load_message.LoadMessageController;
import com.scholarmatch.interface_adapter.load_profile.LoadProfileController;
import com.scholarmatch.interface_adapter.login.LoginController;
import com.scholarmatch.interface_adapter.logout.LogoutController;
import com.scholarmatch.interface_adapter.dislike.DislikeController;
import com.scholarmatch.interface_adapter.recommend.RecommendController;
import com.scholarmatch.interface_adapter.paper_lookup.PaperLookupController;
import com.scholarmatch.interface_adapter.register.RegisterController;
import com.scholarmatch.interface_adapter.request_email_verification.RequestEmailVerificationController;
import com.scholarmatch.interface_adapter.connect.ConnectController;
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
import com.scholarmatch.interface_adapter.view_model.chat.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.delete_account.DeleteAccountViewModel;
import com.scholarmatch.interface_adapter.view_model.login.LoginViewModel;
import com.scholarmatch.interface_adapter.view_model.logout.LogoutViewModel;
import com.scholarmatch.interface_adapter.view_model.recommend.RecommendViewModel;
import com.scholarmatch.interface_adapter.view_model.load_matches.LoadMatchesViewModel;
import com.scholarmatch.interface_adapter.view_model.paper_lookup.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.register.RegisterViewModel;
import com.scholarmatch.interface_adapter.view_model.update_profile.UpdateProfileViewModel;
import com.scholarmatch.interface_adapter.view_model.opportunities.OpportunitiesViewModel;
import com.scholarmatch.interface_adapter.view_model.my_postings.MyPostingsViewModel;
import com.scholarmatch.interface_adapter.view_model.my_applications.MyApplicationsViewModel;
import com.scholarmatch.interface_adapter.view_model.account_settings.AccountSettingsViewModel;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Root view of the ScholarMatch application.
 *
 * <p>Its only responsibility is deciding which top-level shell to show — the pre-auth
 * AuthShellView or the post-auth MainLayoutView — and swapping between them
 * when one signals a state transition (login/registration succeeded, or logout succeeded).
 * The shells themselves own their own UI construction; this class does not build any Swing
 * components directly.
 */
public final class MainView extends JPanel {

    private final LoginController loginController;
    private final LoginViewModel loginViewModel;
    private final LogoutController logoutController;
    private final LogoutViewModel logoutViewModel;
    private final DeleteAccountController deleteAccountController;
    private final DeleteAccountViewModel deleteAccountViewModel;
    private final RegisterController registerController;
    private final RequestEmailVerificationController verificationController;
    private final RegisterViewModel registerViewModel;
    private final PaperLookupController paperLookupController;
    private final PaperLookupViewModel paperLookupViewModel;
    private final RecommendController recommendController;
    private final ConnectController connectController;
    private final DislikeController dislikeController;
    private final SkipController skipController;
    private final RecommendViewModel recommendViewModel;
    private final LoadMatchesViewModel loadMatchesViewModel;
    private final LoadMatchesController loadMatchesController;
    private final SendMessageController sendMessageController;
    private final LoadMessageController loadMessageController;
    private final ChatViewModel chatViewModel;
    private final UpdateProfileController updateProfileController;
    private final LoadProfileController loadProfileController;
    private final UpdateProfileViewModel updateProfileViewModel;
    private final RequestEmailVerificationController requestEmailChangeController;
    private final ChangeEmailController changeEmailController;
    private final ChangePasswordController changePasswordController;
    private final AccountSettingsViewModel accountSettingsViewModel;
    private final CurrentUserProvider currentUserProvider;
    private final CreatePostingController createPostingController;
    private final ClosePostingController closePostingController;
    private final LoadPostingsController opportunitiesLoadPostingsController;
    private final LoadPostingsController myPostingsLoadPostingsController;
    private final ApplyToPostingController applyToPostingController;
    private final AcceptApplicationController acceptApplicationController;
    private final DeclineApplicationController declineApplicationController;
    private final LoadMyApplicationsController loadMyApplicationsController;
    private final OpportunitiesViewModel opportunitiesViewModel;
    private final MyPostingsViewModel myPostingsViewModel;
    private final MyApplicationsViewModel myApplicationsViewModel;

    /**
     * Constructs the MainView and shows the shell matching the current session state.
     *
     * @param loginController         handles login form submission
     * @param loginViewModel          observable login state
     * @param logoutController        handles logout requests
     * @param logoutViewModel         observable logout state
     * @param deleteAccountController handles account-deletion confirmation
     * @param deleteAccountViewModel  observable state for a failed deletion attempt
     * @param registerController      handles registration form submission
     * @param verificationController  handles the registration screen's "Send Code" button
     * @param registerViewModel       observable registration state
     * @param paperLookupController   handles paper/author auto-fill searches
     * @param paperLookupViewModel    observable paper/author auto-fill search state
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
     * @param currentUserProvider     the shared session (used to check login state)
     */
    public MainView(
            final LoginController loginController,
            final LoginViewModel loginViewModel,
            final LogoutController logoutController,
            final LogoutViewModel logoutViewModel,
            final DeleteAccountController deleteAccountController,
            final DeleteAccountViewModel deleteAccountViewModel,
            final RegisterController registerController,
            final RequestEmailVerificationController verificationController,
            final RegisterViewModel registerViewModel,
            final PaperLookupController paperLookupController,
            final PaperLookupViewModel paperLookupViewModel,
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
            final CurrentUserProvider currentUserProvider) {
        super(new BorderLayout());
        setBackground(Theme.BG_DEFAULT);

        this.loginController = loginController;
        this.loginViewModel = loginViewModel;
        this.logoutController = logoutController;
        this.logoutViewModel = logoutViewModel;
        this.deleteAccountController = deleteAccountController;
        this.deleteAccountViewModel = deleteAccountViewModel;
        this.registerController = registerController;
        this.verificationController = verificationController;
        this.registerViewModel = registerViewModel;
        this.paperLookupController = paperLookupController;
        this.paperLookupViewModel = paperLookupViewModel;
        this.recommendController = recommendController;
        this.connectController = connectController;
        this.dislikeController = dislikeController;
        this.skipController = skipController;
        this.recommendViewModel = recommendViewModel;
        this.loadMatchesViewModel = loadMatchesViewModel;
        this.loadMatchesController = loadMatchesController;
        this.sendMessageController = sendMessageController;
        this.loadMessageController = loadMessageController;
        this.chatViewModel = chatViewModel;
        this.updateProfileController = updateProfileController;
        this.loadProfileController = loadProfileController;
        this.updateProfileViewModel = updateProfileViewModel;
        this.requestEmailChangeController = requestEmailChangeController;
        this.changeEmailController = changeEmailController;
        this.changePasswordController = changePasswordController;
        this.accountSettingsViewModel = accountSettingsViewModel;
        this.createPostingController = createPostingController;
        this.closePostingController = closePostingController;
        this.opportunitiesLoadPostingsController = opportunitiesLoadPostingsController;
        this.myPostingsLoadPostingsController = myPostingsLoadPostingsController;
        this.applyToPostingController = applyToPostingController;
        this.acceptApplicationController = acceptApplicationController;
        this.declineApplicationController = declineApplicationController;
        this.loadMyApplicationsController = loadMyApplicationsController;
        this.opportunitiesViewModel = opportunitiesViewModel;
        this.myPostingsViewModel = myPostingsViewModel;
        this.myApplicationsViewModel = myApplicationsViewModel;
        this.currentUserProvider = currentUserProvider;

        if (currentUserProvider.isLoggedIn()) {
            showMainLayout();
        } else {
            showAuthShell();
        }
    }

    private void showAuthShell() {
        removeAll();
        add(new AuthShellView(
                        loginController, loginViewModel,
                        registerController, verificationController, registerViewModel,
                        this::showMainLayout),
                BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showMainLayout() {
        removeAll();
        add(new MainLayoutView(
                        recommendController, connectController, dislikeController, skipController, recommendViewModel,
                        loadMatchesViewModel, loadMatchesController,
                        sendMessageController, loadMessageController, chatViewModel,
                        updateProfileController, loadProfileController, updateProfileViewModel,
                        requestEmailChangeController, changeEmailController,
                        changePasswordController, accountSettingsViewModel,
                        paperLookupController, paperLookupViewModel,
                        logoutController, logoutViewModel,
                        deleteAccountController, deleteAccountViewModel,
                        createPostingController,
                        closePostingController,
                        opportunitiesLoadPostingsController, myPostingsLoadPostingsController,
                        applyToPostingController, acceptApplicationController, declineApplicationController,
                        loadMyApplicationsController,
                        opportunitiesViewModel, myPostingsViewModel, myApplicationsViewModel,
                        currentUserProvider,
                        this::showAuthShell),
                BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
