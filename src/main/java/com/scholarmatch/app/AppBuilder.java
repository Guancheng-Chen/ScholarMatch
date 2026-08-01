package com.scholarmatch.app;

import com.scholarmatch.frameworks.data_access_object.server.AuthGateway;
import com.scholarmatch.frameworks.data_access_object.FallbackUserApiGateway;
import com.scholarmatch.frameworks.data_access_object.LocalUserApiGateway;
import com.scholarmatch.frameworks.data_access_object.localMockServer.LocalAccountSettingsRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.LocalAuthRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.LocalMatchingRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.LocalMessagingRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.LocalPostingRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.LocalProfileRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.LocalServerState;
import com.scholarmatch.frameworks.data_access_object.server.MatchingGateway;
import com.scholarmatch.frameworks.data_access_object.server.MessagingGateway;
import com.scholarmatch.frameworks.data_access_object.server.PostingGateway;
import com.scholarmatch.frameworks.data_access_object.server.ProfileGateway;
import com.scholarmatch.frameworks.data_access_object.SemanticScholarGateway;
import com.scholarmatch.frameworks.data_access_object.server.ServerHttpClient;
import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.ClasspathAcademicEmailDomainRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.InMemoryEmailVerificationChallengeRepository;
import com.scholarmatch.frameworks.data_access_object.localMockServer.SecureVerificationCodeGenerator;
import com.scholarmatch.frameworks.data_access_object.server.AccountSettingsGateway;
import com.scholarmatch.frameworks.data_access_object.server.RemoteVerificationEmailSender;
import com.scholarmatch.frameworks.gui.MainView;
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
import com.scholarmatch.interface_adapter.delete_account.DeleteAccountPresenter;
import com.scholarmatch.interface_adapter.load_matches.LoadMatchesPresenter;
import com.scholarmatch.interface_adapter.load_message.LoadMessagePresenter;
import com.scholarmatch.interface_adapter.load_profile.LoadProfilePresenter;
import com.scholarmatch.interface_adapter.login.LoginPresenter;
import com.scholarmatch.interface_adapter.logout.LogoutPresenter;
import com.scholarmatch.interface_adapter.dislike.DislikePresenter;
import com.scholarmatch.interface_adapter.recommend.RecommendPresenter;
import com.scholarmatch.interface_adapter.paper_lookup.PaperLookupPresenter;
import com.scholarmatch.interface_adapter.register.RegisterPresenter;
import com.scholarmatch.interface_adapter.request_email_verification.RequestEmailVerificationPresenter;
import com.scholarmatch.interface_adapter.connect.ConnectPresenter;
import com.scholarmatch.interface_adapter.send_message.SendMessagePresenter;
import com.scholarmatch.interface_adapter.skip.SkipPresenter;
import com.scholarmatch.interface_adapter.update_profile.UpdateProfilePresenter;
import com.scholarmatch.interface_adapter.create_posting.CreatePostingPresenter;
import com.scholarmatch.interface_adapter.close_posting.ClosePostingPresenter;
import com.scholarmatch.interface_adapter.load_postings.LoadPostingsPresenter;
import com.scholarmatch.interface_adapter.apply_to_posting.ApplyToPostingPresenter;
import com.scholarmatch.interface_adapter.accept_application.AcceptApplicationPresenter;
import com.scholarmatch.interface_adapter.decline_application.DeclineApplicationPresenter;
import com.scholarmatch.interface_adapter.load_my_applications.LoadMyApplicationsPresenter;
import com.scholarmatch.interface_adapter.account_settings.AccountSettingsPresenter;
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
import com.scholarmatch.usecase.data_access_interface.ChangeEmailDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ChangePasswordDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.SendMessageDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CreatePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ClosePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadPostingsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ApplyToPostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.AcceptApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.DeclineApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMyApplicationsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;
import com.scholarmatch.usecase.change_email.ChangeEmailInteractor;
import com.scholarmatch.usecase.change_password.ChangePasswordInteractor;
import com.scholarmatch.usecase.delete_account.DeleteAccountInteractor;
import com.scholarmatch.usecase.load_profile.LoadProfileInteractor;
import com.scholarmatch.usecase.login.LoginInteractor;
import com.scholarmatch.usecase.logout.LogoutInteractor;
import com.scholarmatch.usecase.dislike.DislikeInteractor;
import com.scholarmatch.usecase.load_message.LoadMessageInteractor;
import com.scholarmatch.usecase.recommend.RecommendInteractor;
import com.scholarmatch.usecase.load_matches.LoadMatchesInteractor;
import com.scholarmatch.usecase.paper_lookup.PaperLookupInteractor;
import com.scholarmatch.usecase.register.RegisterInteractor;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationInteractor;
import com.scholarmatch.usecase.connect.ConnectInteractor;
import com.scholarmatch.usecase.send_message.SendMessageInteractor;
import com.scholarmatch.usecase.skip.SkipInteractor;
import com.scholarmatch.usecase.update_profile.UpdateProfileInteractor;
import com.scholarmatch.usecase.create_posting.CreatePostingInteractor;
import com.scholarmatch.usecase.close_posting.ClosePostingInteractor;
import com.scholarmatch.usecase.load_postings.LoadPostingsInteractor;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingInteractor;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInteractor;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInteractor;
import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsInteractor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Clock;
import java.util.function.BooleanSupplier;

/**
 * Composition root — the only class that instantiates concrete types across layers.
 *
 * <p>Builder pattern: each {@code addX()} step wires one architectural layer and stores the
 * result in instance fields for later steps to consume, instead of one long procedural method.
 * Steps must run in order (session → repositories → view models → presenters → interactors →
 * controllers) since each layer's wiring depends on objects built by the previous one;
 * {@link #build()} checks every step ran and fails fast with a specific message if one was
 * skipped, rather than a {@code NullPointerException} deep inside whichever controller
 * happened to need the missing piece.
 *
 * <p>Recognized environment variables:
 * <ul>
 *   <li>SERVER_URL — ScholarMatch REST API base URL
 *       (defaults to the Railway production URL if not set)</li>
 *   <li>OFFLINE_MODE — if set to true, skips the live server entirely and
 *       uses the offline Local*Repository classes for auth/profile/match/connect,
 *       for demo control</li>
 * </ul>
 *
 * <p>If OFFLINE_MODE is not set, {@link #addRepositories()} pings SERVER_URL's health
 * endpoint once at startup; if it is unreachable, it falls back to the offline
 * Local*Repository classes automatically so a live demo is not derailed by the server
 * being down. Paper lookup
 * (UserAPIGatewayInterface) instead falls back per-call, since it is stateless —
 * see FallbackUserApiGateway.
 */
public final class AppBuilder {

    private static final String SERVER_URL = System.getenv()
            .getOrDefault("SERVER_URL", "https://scholarmatch-server-production.up.railway.app");
    private static final Duration HEALTH_CHECK_TIMEOUT = Duration.ofSeconds(3);
    // Railway's free tier puts the server to sleep after inactivity; the first request after
    // that has to wait for a cold start and often gets a 502 from the edge proxy before the
    // container is ready. A single probe misreads that as "server down" for the rest of the
    // process, so retry a few times with a short gap instead of judging on one attempt.
    private static final int HEALTH_CHECK_ATTEMPTS = 3;
    private static final Duration HEALTH_CHECK_RETRY_DELAY = Duration.ofSeconds(2);
    private final Boolean offlineOverride;

    // ── Session ───────────────────────────────────────────────────────────────
    private CurrentUserProvider currentUserProvider;

    // ── Repositories (layer 4) ───────────────────────────────────────────────
    private InstitutionCatalogDataAccessInterface institutionCatalog;
    private LoginDataAccessInterface loginDataAccessObject;
    private RegisterDataAccessInterface registerDataAccessObject;
    private RecommendDataAccessInterface recommendDataAccessObject;
    private ConnectDataAccessInterface connectDataAccessObject;
    private DislikeDataAccessInterface dislikeDataAccessObject;
    private LoadMatchesDataAccessInterface loadMatchesDataAccessObject;
    private LoadProfileDataAccessInterface loadProfileDataAccessObject;
    private UpdateProfileDataAccessInterface updateProfileDataAccessObject;
    private VerificationEmailSenderDataAccessInterface registerVerificationDataAccessObject;
    private VerificationEmailSenderDataAccessInterface requestEmailChangeDataAccessObject;
    private ChangeEmailDataAccessInterface changeEmailDataAccessObject;
    private ChangePasswordDataAccessInterface changePasswordDataAccessObject;
    private DeleteAccountDataAccessInterface deleteAccountDataAccessObject;
    private SendMessageDataAccessInterface sendMessageDataAccessObject;
    private LoadMessageDataAccessInterface loadMessageDataAccessObject;
    private CreatePostingDataAccessInterface createPostingDataAccessObject;
    private ClosePostingDataAccessInterface closePostingDataAccessObject;
    private LoadPostingsDataAccessInterface loadPostingsDataAccessObject;
    private ApplyToPostingDataAccessInterface applyToPostingDataAccessObject;
    private AcceptApplicationDataAccessInterface acceptApplicationDataAccessObject;
    private DeclineApplicationDataAccessInterface declineApplicationDataAccessObject;
    private LoadMyApplicationsDataAccessInterface loadMyApplicationsDataAccessObject;
    private UserAPIGatewayInterface userApiGateway;

    // ── ViewModels (layer 3) ─────────────────────────────────────────────────
    private LoginViewModel loginViewModel;
    private LogoutViewModel logoutViewModel;
    private DeleteAccountViewModel deleteAccountViewModel;
    private RegisterViewModel registerViewModel;
    private PaperLookupViewModel paperLookupViewModel;
    private RecommendViewModel recommendViewModel;
    private LoadMatchesViewModel loadMatchesViewModel;
    private ChatViewModel chatViewModel;
    private UpdateProfileViewModel updateProfileViewModel;
    private OpportunitiesViewModel opportunitiesViewModel;
    private MyPostingsViewModel myPostingsViewModel;
    private MyApplicationsViewModel myApplicationsViewModel;
    private AccountSettingsViewModel accountSettingsViewModel;

    // ── Presenters (layer 3) ─────────────────────────────────────────────────
    private LoginPresenter loginPresenter;
    private LogoutPresenter logoutPresenter;
    private DeleteAccountPresenter deleteAccountPresenter;
    private RegisterPresenter registerPresenter;
    private RequestEmailVerificationPresenter verificationPresenter;
    private PaperLookupPresenter paperLookupPresenter;
    private RecommendPresenter recommendPresenter;
    private ConnectPresenter connectPresenter;
    private DislikePresenter dislikePresenter;
    private SkipPresenter skipPresenter;
    private LoadMatchesPresenter loadMatchesPresenter;
    private SendMessagePresenter sendMessagePresenter;
    private LoadMessagePresenter loadMessagePresenter;
    private UpdateProfilePresenter updateProfilePresenter;
    private LoadProfilePresenter loadProfilePresenter;
    private CreatePostingPresenter createPostingPresenter;
    private ClosePostingPresenter closePostingPresenter;
    private LoadPostingsPresenter opportunitiesLoadPostingsPresenter;
    private LoadPostingsPresenter myPostingsLoadPostingsPresenter;
    private ApplyToPostingPresenter applyToPostingPresenter;
    private AcceptApplicationPresenter acceptApplicationPresenter;
    private DeclineApplicationPresenter declineApplicationPresenter;
    private LoadMyApplicationsPresenter loadMyApplicationsPresenter;
    private AccountSettingsPresenter accountSettingsPresenter;

    // ── Interactors (layer 2) ────────────────────────────────────────────────
    private LoginInteractor loginInteractor;
    private LogoutInteractor logoutInteractor;
    private DeleteAccountInteractor deleteAccountInteractor;
    private RegisterInteractor registerInteractor;
    private RequestEmailVerificationInteractor verificationInteractor;
    private PaperLookupInteractor paperLookupInteractor;
    private RecommendInteractor recommendInteractor;
    private ConnectInteractor connectInteractor;
    private DislikeInteractor dislikeInteractor;
    private SkipInteractor skipInteractor;
    private LoadMatchesInteractor loadMatchesInteractor;
    private SendMessageInteractor sendMessageInteractor;
    private LoadMessageInteractor loadMessageInteractor;
    private UpdateProfileInteractor updateProfileInteractor;
    private LoadProfileInteractor loadProfileInteractor;
    private CreatePostingInteractor createPostingInteractor;
    private ClosePostingInteractor closePostingInteractor;
    private LoadPostingsInteractor opportunitiesLoadPostingsInteractor;
    private LoadPostingsInteractor myPostingsLoadPostingsInteractor;
    private ApplyToPostingInteractor applyToPostingInteractor;
    private AcceptApplicationInteractor acceptApplicationInteractor;
    private DeclineApplicationInteractor declineApplicationInteractor;
    private LoadMyApplicationsInteractor loadMyApplicationsInteractor;
    private RequestEmailVerificationInteractor requestEmailChangeInteractor;
    private ChangeEmailInteractor changeEmailInteractor;
    private ChangePasswordInteractor changePasswordInteractor;

    // ── Controllers (layer 3) ────────────────────────────────────────────────
    private LoginController loginController;
    private LogoutController logoutController;
    private DeleteAccountController deleteAccountController;
    private RegisterController registerController;
    private RequestEmailVerificationController requestEmailVerificationController;
    private PaperLookupController paperLookupController;
    private RecommendController recommendController;
    private ConnectController connectController;
    private DislikeController dislikeController;
    private SkipController skipController;
    private LoadMatchesController loadMatchesController;
    private SendMessageController sendMessageController;
    private LoadMessageController loadMessageController;
    private UpdateProfileController updateProfileController;
    private LoadProfileController loadProfileController;
    private CreatePostingController createPostingController;
    private ClosePostingController closePostingController;
    private LoadPostingsController opportunitiesLoadPostingsController;
    private LoadPostingsController myPostingsLoadPostingsController;
    private ApplyToPostingController applyToPostingController;
    private AcceptApplicationController acceptApplicationController;
    private DeclineApplicationController declineApplicationController;
    private LoadMyApplicationsController loadMyApplicationsController;
    private RequestEmailVerificationController requestEmailChangeController;
    private ChangeEmailController changeEmailController;
    private ChangePasswordController changePasswordController;

    private boolean controllersAdded;

    /**
     * Constructs a builder using environment and health-check based repository selection.
     */
    public AppBuilder() {
        this(null);
    }

    AppBuilder(final Boolean offlineOverride) {
        this.offlineOverride = offlineOverride;
    }

    /**
     * Step 1: creates the shared session object every later step depends on.
     *
     * @return this, for chaining
     */
    public AppBuilder addSession() {
        this.currentUserProvider = new CurrentUserProvider();
        return this;
    }

    /**
     * Step 2: chooses online vs. offline data access for every feature, once, at startup.
     *
     * @return this, for chaining
     */
    public AppBuilder addRepositories() {
        requireStep(this.currentUserProvider != null, "addSession");

        final boolean offline = this.offlineOverride != null
                ? this.offlineOverride
                : selectOffline(
                        System.getenv("OFFLINE_MODE"),
                        () -> isServerReachable(SERVER_URL));
        this.institutionCatalog = new ClasspathInstitutionCatalogRepository();

        final ServerHttpClient httpClient = new ServerHttpClient(SERVER_URL, this.currentUserProvider);
        final AuthGateway authGateway = new AuthGateway(httpClient);
        final ProfileGateway profileGateway = new ProfileGateway(httpClient, this.institutionCatalog);
        final AccountSettingsGateway accountSettingsGateway =
                new AccountSettingsGateway(httpClient, this.institutionCatalog);
        final MatchingGateway matchingGateway = new MatchingGateway(httpClient, this.institutionCatalog);
        final MessagingGateway messagingGateway = new MessagingGateway(httpClient);
        final PostingGateway postingGateway = new PostingGateway(httpClient);
        final LocalServerState localState = new LocalServerState(this.institutionCatalog);
        final LocalAuthRepository localAuthRepo = new LocalAuthRepository(localState);
        final LocalProfileRepository localProfileRepo = new LocalProfileRepository(
                localState, this.currentUserProvider, this.institutionCatalog);
        final LocalAccountSettingsRepository localAccountSettingsRepo = new LocalAccountSettingsRepository(
                localState,
                this.currentUserProvider,
                new InMemoryEmailVerificationChallengeRepository(),
                new SecureVerificationCodeGenerator(),
                (email, code) -> System.out.println(
                        "[Offline demo] Verification code for " + email + ": " + code),
                new ClasspathAcademicEmailDomainRepository(),
                Clock.systemUTC());
        final LocalMatchingRepository localMatchingRepo =
                new LocalMatchingRepository(localState, this.currentUserProvider);
        final LocalMessagingRepository localMessagingRepo =
                new LocalMessagingRepository(localState, this.currentUserProvider);
        final LocalPostingRepository localPostingRepo =
                new LocalPostingRepository(localState, this.currentUserProvider);

        this.loginDataAccessObject = offline ? localAuthRepo : authGateway;
        this.registerDataAccessObject = offline ? localAuthRepo : authGateway;
        this.registerVerificationDataAccessObject =
                offline ? localAccountSettingsRepo : new RemoteVerificationEmailSender(SERVER_URL);
        this.recommendDataAccessObject = offline ? localMatchingRepo : matchingGateway;
        this.connectDataAccessObject = offline ? localMatchingRepo : matchingGateway;
        this.dislikeDataAccessObject = offline ? localMatchingRepo : matchingGateway;
        this.loadMatchesDataAccessObject = offline ? localMatchingRepo : matchingGateway;
        this.loadProfileDataAccessObject = offline ? localProfileRepo : profileGateway;
        this.updateProfileDataAccessObject = offline ? localProfileRepo : profileGateway;
        this.requestEmailChangeDataAccessObject =
                offline ? localAccountSettingsRepo : accountSettingsGateway;
        this.changeEmailDataAccessObject =
                offline ? localAccountSettingsRepo : accountSettingsGateway;
        this.changePasswordDataAccessObject =
                offline ? localAccountSettingsRepo : accountSettingsGateway;
        this.deleteAccountDataAccessObject = offline ? localProfileRepo : profileGateway;
        this.sendMessageDataAccessObject = offline ? localMessagingRepo : messagingGateway;
        this.loadMessageDataAccessObject = offline ? localMessagingRepo : messagingGateway;
        this.createPostingDataAccessObject = offline ? localPostingRepo : postingGateway;
        this.closePostingDataAccessObject = offline ? localPostingRepo : postingGateway;
        this.loadPostingsDataAccessObject = offline ? localPostingRepo : postingGateway;
        this.applyToPostingDataAccessObject = offline ? localPostingRepo : postingGateway;
        this.acceptApplicationDataAccessObject = offline ? localPostingRepo : postingGateway;
        this.declineApplicationDataAccessObject = offline ? localPostingRepo : postingGateway;
        this.loadMyApplicationsDataAccessObject = offline ? localPostingRepo : postingGateway;
        // Falls back to a small offline dataset per-call if the live Semantic User API is
        // rate limited or unreachable, so a demo isn't derailed by a third-party outage.
        this.userApiGateway = new FallbackUserApiGateway(new SemanticScholarGateway(), new LocalUserApiGateway());
        return this;
    }

    /**
     * Step 3: creates every screen's observable state.
     *
     * @return this, for chaining
     */
    public AppBuilder addViewModels() {
        requireStep(this.institutionCatalog != null, "addRepositories");

        this.loginViewModel = new LoginViewModel();
        this.logoutViewModel = new LogoutViewModel();
        this.deleteAccountViewModel = new DeleteAccountViewModel();
        this.registerViewModel = new RegisterViewModel();
        this.paperLookupViewModel = new PaperLookupViewModel();
        this.recommendViewModel = new RecommendViewModel();
        this.loadMatchesViewModel = new LoadMatchesViewModel();
        this.chatViewModel = new ChatViewModel();
        this.updateProfileViewModel = new UpdateProfileViewModel();
        this.updateProfileViewModel.setInstitutions(this.institutionCatalog.getAllInstitutions());
        this.opportunitiesViewModel = new OpportunitiesViewModel();
        this.myPostingsViewModel = new MyPostingsViewModel();
        this.myApplicationsViewModel = new MyApplicationsViewModel();
        this.accountSettingsViewModel = new AccountSettingsViewModel();
        return this;
    }

    /**
     * Step 4: creates every use case's output boundary, writing into the view models built
     * in the previous step.
     *
     * @return this, for chaining
     */
    public AppBuilder addPresenters() {
        requireStep(this.loginViewModel != null, "addViewModels");

        this.loginPresenter = new LoginPresenter(this.loginViewModel);
        this.logoutPresenter = new LogoutPresenter(this.logoutViewModel);
        this.deleteAccountPresenter =
                new DeleteAccountPresenter(this.logoutViewModel, this.deleteAccountViewModel);
        this.registerPresenter = new RegisterPresenter(this.registerViewModel);
        this.verificationPresenter = new RequestEmailVerificationPresenter(this.registerViewModel);
        this.paperLookupPresenter = new PaperLookupPresenter(this.paperLookupViewModel);
        this.recommendPresenter = new RecommendPresenter(this.recommendViewModel);
        this.connectPresenter = new ConnectPresenter(this.loadMatchesViewModel);
        this.dislikePresenter = new DislikePresenter();
        this.skipPresenter = new SkipPresenter();
        this.loadMatchesPresenter = new LoadMatchesPresenter(this.loadMatchesViewModel);
        this.sendMessagePresenter = new SendMessagePresenter(this.chatViewModel);
        this.loadMessagePresenter = new LoadMessagePresenter(this.chatViewModel);
        this.updateProfilePresenter = new UpdateProfilePresenter(this.updateProfileViewModel);
        this.loadProfilePresenter = new LoadProfilePresenter(this.updateProfileViewModel);
        this.createPostingPresenter = new CreatePostingPresenter(this.myPostingsViewModel);
        this.closePostingPresenter = new ClosePostingPresenter(this.myPostingsViewModel);
        this.opportunitiesLoadPostingsPresenter = new LoadPostingsPresenter(this.opportunitiesViewModel);
        this.myPostingsLoadPostingsPresenter = new LoadPostingsPresenter(this.myPostingsViewModel);
        this.applyToPostingPresenter = new ApplyToPostingPresenter(this.opportunitiesViewModel);
        this.acceptApplicationPresenter = new AcceptApplicationPresenter(this.myPostingsViewModel);
        this.declineApplicationPresenter = new DeclineApplicationPresenter(this.myPostingsViewModel);
        this.loadMyApplicationsPresenter = new LoadMyApplicationsPresenter(this.myApplicationsViewModel);
        this.accountSettingsPresenter = new AccountSettingsPresenter(
                this.accountSettingsViewModel, this.updateProfileViewModel);
        return this;
    }

    /**
     * Step 5: creates every use case, wiring the repositories from step 2 to the presenters
     * from step 4.
     *
     * @return this, for chaining
     */
    public AppBuilder addInteractors() {
        requireStep(this.loginPresenter != null, "addPresenters");

        this.loginInteractor =
                new LoginInteractor(this.loginDataAccessObject, this.currentUserProvider, this.loginPresenter);
        this.logoutInteractor = new LogoutInteractor(this.currentUserProvider, this.logoutPresenter);
        this.deleteAccountInteractor = new DeleteAccountInteractor(
                this.deleteAccountDataAccessObject, this.currentUserProvider, this.deleteAccountPresenter);
        this.registerInteractor = new RegisterInteractor(
                this.registerDataAccessObject, this.currentUserProvider, this.registerPresenter);
        this.verificationInteractor = new RequestEmailVerificationInteractor(
                this.registerVerificationDataAccessObject, this.verificationPresenter);
        this.paperLookupInteractor = new PaperLookupInteractor(this.userApiGateway, this.paperLookupPresenter);
        this.recommendInteractor =
                new RecommendInteractor(this.recommendDataAccessObject, this.recommendPresenter);
        this.connectInteractor = new ConnectInteractor(this.connectDataAccessObject, this.connectPresenter);
        this.dislikeInteractor = new DislikeInteractor(this.dislikeDataAccessObject, this.dislikePresenter);
        this.skipInteractor = new SkipInteractor(this.skipPresenter);
        this.loadMatchesInteractor =
                new LoadMatchesInteractor(this.loadMatchesDataAccessObject, this.loadMatchesPresenter);
        this.sendMessageInteractor =
                new SendMessageInteractor(this.sendMessageDataAccessObject, this.sendMessagePresenter);
        this.loadMessageInteractor =
                new LoadMessageInteractor(this.loadMessageDataAccessObject, this.loadMessagePresenter);
        this.updateProfileInteractor =
                new UpdateProfileInteractor(this.updateProfileDataAccessObject, this.updateProfilePresenter);
        this.loadProfileInteractor =
                new LoadProfileInteractor(this.loadProfileDataAccessObject, this.loadProfilePresenter);
        this.createPostingInteractor =
                new CreatePostingInteractor(this.createPostingDataAccessObject, this.createPostingPresenter);
        this.closePostingInteractor =
                new ClosePostingInteractor(this.closePostingDataAccessObject, this.closePostingPresenter);
        this.opportunitiesLoadPostingsInteractor = new LoadPostingsInteractor(
                this.loadPostingsDataAccessObject, this.opportunitiesLoadPostingsPresenter);
        this.myPostingsLoadPostingsInteractor = new LoadPostingsInteractor(
                this.loadPostingsDataAccessObject, this.myPostingsLoadPostingsPresenter);
        this.applyToPostingInteractor = new ApplyToPostingInteractor(
                this.applyToPostingDataAccessObject, this.applyToPostingPresenter);
        this.acceptApplicationInteractor = new AcceptApplicationInteractor(
                this.acceptApplicationDataAccessObject, this.acceptApplicationPresenter);
        this.declineApplicationInteractor = new DeclineApplicationInteractor(
                this.declineApplicationDataAccessObject, this.declineApplicationPresenter);
        this.loadMyApplicationsInteractor = new LoadMyApplicationsInteractor(
                this.loadMyApplicationsDataAccessObject, this.loadMyApplicationsPresenter);
        this.requestEmailChangeInteractor =
                new RequestEmailVerificationInteractor(
                        this.requestEmailChangeDataAccessObject,
                        this.accountSettingsPresenter);
        this.changeEmailInteractor = new ChangeEmailInteractor(
                this.changeEmailDataAccessObject, this.accountSettingsPresenter);
        this.changePasswordInteractor = new ChangePasswordInteractor(
                this.changePasswordDataAccessObject, this.accountSettingsPresenter);
        return this;
    }

    /**
     * Step 6: creates every controller the UI will call into.
     *
     * @return this, for chaining
     */
    public AppBuilder addControllers() {
        requireStep(this.loginInteractor != null, "addInteractors");

        this.loginController = new LoginController(this.loginInteractor);
        this.logoutController = new LogoutController(this.logoutInteractor);
        this.deleteAccountController = new DeleteAccountController(this.deleteAccountInteractor);
        this.registerController = new RegisterController(this.registerInteractor);
        this.requestEmailVerificationController =
                new RequestEmailVerificationController(this.verificationInteractor);
        this.paperLookupController = new PaperLookupController(this.paperLookupInteractor);
        this.recommendController = new RecommendController(this.recommendInteractor);
        this.connectController = new ConnectController(this.connectInteractor);
        this.dislikeController = new DislikeController(this.dislikeInteractor);
        this.skipController = new SkipController(this.skipInteractor);
        this.loadMatchesController = new LoadMatchesController(this.loadMatchesInteractor);
        this.sendMessageController = new SendMessageController(this.sendMessageInteractor);
        this.loadMessageController = new LoadMessageController(this.loadMessageInteractor);
        this.updateProfileController = new UpdateProfileController(this.updateProfileInteractor);
        this.loadProfileController = new LoadProfileController(this.loadProfileInteractor);
        this.createPostingController = new CreatePostingController(this.createPostingInteractor);
        this.closePostingController = new ClosePostingController(this.closePostingInteractor);
        this.opportunitiesLoadPostingsController =
                new LoadPostingsController(this.opportunitiesLoadPostingsInteractor);
        this.myPostingsLoadPostingsController =
                new LoadPostingsController(this.myPostingsLoadPostingsInteractor);
        this.applyToPostingController = new ApplyToPostingController(this.applyToPostingInteractor);
        this.acceptApplicationController = new AcceptApplicationController(this.acceptApplicationInteractor);
        this.declineApplicationController = new DeclineApplicationController(this.declineApplicationInteractor);
        this.loadMyApplicationsController = new LoadMyApplicationsController(this.loadMyApplicationsInteractor);
        this.requestEmailChangeController =
                new RequestEmailVerificationController(
                        this.requestEmailChangeInteractor);
        this.changeEmailController =
                new ChangeEmailController(this.changeEmailInteractor);
        this.changePasswordController =
                new ChangePasswordController(this.changePasswordInteractor);
        this.controllersAdded = true;
        return this;
    }

    /**
     * Final step: assembles the root view from everything the previous steps built.
     *
     * @return the fully configured root panel to attach to the main window
     * @throws IllegalStateException if an earlier step was skipped
     */
    public MainView build() {
        requireStep(this.controllersAdded, "addControllers");

        return new MainView(
                this.loginController, this.loginViewModel,
                this.logoutController, this.logoutViewModel,
                this.deleteAccountController, this.deleteAccountViewModel,
                this.registerController, this.requestEmailVerificationController, this.registerViewModel,
                this.paperLookupController, this.paperLookupViewModel,
                this.recommendController, this.connectController, this.dislikeController, this.skipController,
                this.recommendViewModel,
                this.loadMatchesViewModel, this.loadMatchesController,
                this.sendMessageController, this.loadMessageController, this.chatViewModel,
                this.updateProfileController, this.loadProfileController, this.updateProfileViewModel,
                this.requestEmailChangeController, this.changeEmailController,
                this.changePasswordController, this.accountSettingsViewModel,
                this.createPostingController,
                this.closePostingController,
                this.opportunitiesLoadPostingsController, this.myPostingsLoadPostingsController,
                this.applyToPostingController, this.acceptApplicationController, this.declineApplicationController,
                this.loadMyApplicationsController,
                this.opportunitiesViewModel, this.myPostingsViewModel, this.myApplicationsViewModel,
                this.currentUserProvider);
    }

    /**
     * Exposes the wired offline/online verification email sender so a test can exercise it
     * directly, without driving the full GUI to reach the same call.
     */
    VerificationEmailSenderDataAccessInterface registerVerificationDataAccessObject() {
        return this.registerVerificationDataAccessObject;
    }

    private void requireStep(final boolean stepAlreadyRan, final String missingStepName) {
        if (!stepAlreadyRan) {
            throw new IllegalStateException(
                    "AppBuilder." + missingStepName + "() must be called before this step.");
        }
    }

    static boolean selectOffline(final String offlineMode, final BooleanSupplier serverReachable) {
        return "true".equalsIgnoreCase(offlineMode) || !serverReachable.getAsBoolean();
    }

    private boolean isServerReachable(final String baseUrl) {
        for (int attempt = 1; attempt <= HEALTH_CHECK_ATTEMPTS; attempt++) {
            if (pingHealth(baseUrl)) {
                return true;
            }
            if (attempt < HEALTH_CHECK_ATTEMPTS) {
                sleep(HEALTH_CHECK_RETRY_DELAY);
            }
        }
        return false;
    }

    private boolean pingHealth(final String baseUrl) {
        try {
            final HttpClient http = HttpClient.newBuilder()
                    .connectTimeout(HEALTH_CHECK_TIMEOUT)
                    .build();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/health"))
                    .timeout(HEALTH_CHECK_TIMEOUT)
                    .GET()
                    .build();
            final HttpResponse<Void> response = http.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() == 200;
        } catch (final Exception e) {
            return false;
        }
    }

    private void sleep(final Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
