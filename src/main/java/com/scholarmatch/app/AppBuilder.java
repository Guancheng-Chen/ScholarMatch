package com.scholarmatch.app;

import com.scholarmatch.frameworks.data_access_object.AuthGateway;
import com.scholarmatch.frameworks.data_access_object.FallbackUserApiGateway;
import com.scholarmatch.frameworks.data_access_object.LocalUserApiGateway;
import com.scholarmatch.frameworks.data_access_object.LocalServerRepository;
import com.scholarmatch.frameworks.data_access_object.MatchingGateway;
import com.scholarmatch.frameworks.data_access_object.MessagingGateway;
import com.scholarmatch.frameworks.data_access_object.PostingGateway;
import com.scholarmatch.frameworks.data_access_object.ProfileGateway;
import com.scholarmatch.frameworks.data_access_object.SemanticScholarGateway;
import com.scholarmatch.frameworks.data_access_object.ServerHttpClient;
import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import com.scholarmatch.frameworks.data_access_object.RemoteVerificationEmailSender;
import com.scholarmatch.frameworks.gui.MainView;
import com.scholarmatch.interface_adapter.controller.DeleteAccountController;
import com.scholarmatch.interface_adapter.controller.LoadMatchesController;
import com.scholarmatch.interface_adapter.controller.LoadMessageController;
import com.scholarmatch.interface_adapter.controller.LoadProfileController;
import com.scholarmatch.interface_adapter.controller.LoginController;
import com.scholarmatch.interface_adapter.controller.LogoutController;
import com.scholarmatch.interface_adapter.controller.DislikeController;
import com.scholarmatch.interface_adapter.controller.RecommendController;
import com.scholarmatch.interface_adapter.controller.PaperLookupController;
import com.scholarmatch.interface_adapter.controller.RegisterController;
import com.scholarmatch.interface_adapter.controller.RequestEmailVerificationController;
import com.scholarmatch.interface_adapter.controller.ConnectController;
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
import com.scholarmatch.interface_adapter.presenter.DeleteAccountPresenter;
import com.scholarmatch.interface_adapter.presenter.LoadMatchesPresenter;
import com.scholarmatch.interface_adapter.presenter.LoadMessagePresenter;
import com.scholarmatch.interface_adapter.presenter.LoadProfilePresenter;
import com.scholarmatch.interface_adapter.presenter.LoginPresenter;
import com.scholarmatch.interface_adapter.presenter.LogoutPresenter;
import com.scholarmatch.interface_adapter.presenter.DislikePresenter;
import com.scholarmatch.interface_adapter.presenter.RecommendPresenter;
import com.scholarmatch.interface_adapter.presenter.PaperLookupPresenter;
import com.scholarmatch.interface_adapter.presenter.RegisterPresenter;
import com.scholarmatch.interface_adapter.presenter.RequestEmailVerificationPresenter;
import com.scholarmatch.interface_adapter.presenter.ConnectPresenter;
import com.scholarmatch.interface_adapter.presenter.SendMessagePresenter;
import com.scholarmatch.interface_adapter.presenter.SkipPresenter;
import com.scholarmatch.interface_adapter.presenter.UpdateProfilePresenter;
import com.scholarmatch.interface_adapter.presenter.CreatePostingPresenter;
import com.scholarmatch.interface_adapter.presenter.ClosePostingPresenter;
import com.scholarmatch.interface_adapter.presenter.LoadPostingsPresenter;
import com.scholarmatch.interface_adapter.presenter.ApplyToPostingPresenter;
import com.scholarmatch.interface_adapter.presenter.AcceptApplicationPresenter;
import com.scholarmatch.interface_adapter.presenter.DeclineApplicationPresenter;
import com.scholarmatch.interface_adapter.presenter.LoadMyApplicationsPresenter;
import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.DeleteAccountViewModel;
import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import com.scholarmatch.interface_adapter.view_model.LogoutViewModel;
import com.scholarmatch.interface_adapter.view_model.RecommendViewModel;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;
import com.scholarmatch.interface_adapter.view_model.OpportunitiesViewModel;
import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.interface_adapter.view_model.MyApplicationsViewModel;
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
 *       uses LocalServerRepository for auth/profile/match/connect, for demo control</li>
 * </ul>
 *
 * <p>If OFFLINE_MODE is not set, {@link #addRepositories()} pings SERVER_URL's health
 * endpoint once at startup; if it is unreachable, it falls back to LocalServerRepository
 * automatically so a live demo is not derailed by the server being down. Paper lookup
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

    private boolean controllersAdded;

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

        final boolean offline = "true".equalsIgnoreCase(System.getenv("OFFLINE_MODE"))
                || !isServerReachable(SERVER_URL);
        this.institutionCatalog = new ClasspathInstitutionCatalogRepository();

        final ServerHttpClient httpClient = new ServerHttpClient(SERVER_URL, this.currentUserProvider);
        final AuthGateway authGateway = new AuthGateway(httpClient);
        final ProfileGateway profileGateway = new ProfileGateway(httpClient, this.institutionCatalog);
        final MatchingGateway matchingGateway = new MatchingGateway(httpClient, this.institutionCatalog);
        final MessagingGateway messagingGateway = new MessagingGateway(httpClient);
        final PostingGateway postingGateway = new PostingGateway(httpClient);
        final LocalServerRepository localRepo =
                new LocalServerRepository(this.currentUserProvider, this.institutionCatalog);

        this.loginDataAccessObject = offline ? localRepo : authGateway;
        this.registerDataAccessObject = offline ? localRepo : authGateway;
        this.recommendDataAccessObject = offline ? localRepo : matchingGateway;
        this.connectDataAccessObject = offline ? localRepo : matchingGateway;
        this.dislikeDataAccessObject = offline ? localRepo : matchingGateway;
        this.loadMatchesDataAccessObject = offline ? localRepo : matchingGateway;
        this.loadProfileDataAccessObject = offline ? localRepo : profileGateway;
        this.updateProfileDataAccessObject = offline ? localRepo : profileGateway;
        this.deleteAccountDataAccessObject = offline ? localRepo : profileGateway;
        this.sendMessageDataAccessObject = offline ? localRepo : messagingGateway;
        this.loadMessageDataAccessObject = offline ? localRepo : messagingGateway;
        this.createPostingDataAccessObject = offline ? localRepo : postingGateway;
        this.closePostingDataAccessObject = offline ? localRepo : postingGateway;
        this.loadPostingsDataAccessObject = offline ? localRepo : postingGateway;
        this.applyToPostingDataAccessObject = offline ? localRepo : postingGateway;
        this.acceptApplicationDataAccessObject = offline ? localRepo : postingGateway;
        this.declineApplicationDataAccessObject = offline ? localRepo : postingGateway;
        this.loadMyApplicationsDataAccessObject = offline ? localRepo : postingGateway;
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
                new RemoteVerificationEmailSender(SERVER_URL), this.verificationPresenter);
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
                this.createPostingController,
                this.closePostingController,
                this.opportunitiesLoadPostingsController, this.myPostingsLoadPostingsController,
                this.applyToPostingController, this.acceptApplicationController, this.declineApplicationController,
                this.loadMyApplicationsController,
                this.opportunitiesViewModel, this.myPostingsViewModel, this.myApplicationsViewModel,
                this.currentUserProvider);
    }

    private void requireStep(final boolean stepAlreadyRan, final String missingStepName) {
        if (!stepAlreadyRan) {
            throw new IllegalStateException(
                    "AppBuilder." + missingStepName + "() must be called before this step.");
        }
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
