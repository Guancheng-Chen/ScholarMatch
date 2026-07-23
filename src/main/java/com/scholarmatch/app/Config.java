package com.scholarmatch.app;

import com.scholarmatch.frameworks.data_access_object.FallbackUserApiGateway;
import com.scholarmatch.frameworks.data_access_object.LocalUserApiGateway;
import com.scholarmatch.frameworks.data_access_object.LocalServerRepository;
import com.scholarmatch.frameworks.data_access_object.SemanticScholarGateway;
import com.scholarmatch.frameworks.data_access_object.ServerRepository;
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
 * <p>Recognized environment variables:
 * <ul>
 *   <li>SERVER_URL — ScholarMatch REST API base URL
 *       (defaults to the Railway production URL if not set)</li>
 *   <li>OFFLINE_MODE — if set to true, skips the live server entirely and
 *       uses LocalServerRepository for auth/profile/match/connect, for demo control</li>
 * </ul>
 *
 * <p>If OFFLINE_MODE is not set, #build() pings SERVER_URL's health
 * endpoint once at startup; if it is unreachable, it falls back to LocalServerRepository
 * automatically so a live demo is not derailed by the server being down. Paper lookup
 * (UserAPIGatewayInterface) instead falls back per-call, since it is stateless —
 * see FallbackUserApiGateway.
 */
public final class Config {

    private static final String SERVER_URL = System.getenv()
            .getOrDefault("SERVER_URL", "https://scholarmatch-server-production.up.railway.app");
    private static final Duration HEALTH_CHECK_TIMEOUT = Duration.ofSeconds(3);
    // Railway's free tier puts the server to sleep after inactivity; the first request after
    // that has to wait for a cold start and often gets a 502 from the edge proxy before the
    // container is ready. A single probe misreads that as "server down" for the rest of the
    // process, so retry a few times with a short gap instead of judging on one attempt.
    private static final int HEALTH_CHECK_ATTEMPTS = 3;
    private static final Duration HEALTH_CHECK_RETRY_DELAY = Duration.ofSeconds(2);

    /**
     * Constructs a Config.
     */
    public Config() {
    }

    /**
     * Wires all layers and returns the root view.
     *
     * @return the fully configured root panel to attach to the main window
     */
    public MainView build() {
        // ── Session ───────────────────────────────────────────────────────────
        final CurrentUserProvider currentUserProvider = new CurrentUserProvider();

        // ── Layer 4: server-facing repositories, chosen once at startup ────────
        final boolean offline = "true".equalsIgnoreCase(System.getenv("OFFLINE_MODE"))
                || !isServerReachable(SERVER_URL);
        final InstitutionCatalogDataAccessInterface institutionCatalog =
                new ClasspathInstitutionCatalogRepository();
        final ServerRepository serverRepo =
                new ServerRepository(SERVER_URL, currentUserProvider, institutionCatalog);
        final LocalServerRepository localRepo =
                new LocalServerRepository(currentUserProvider, institutionCatalog);
        final LoginDataAccessInterface loginDataAccessObject = offline ? localRepo : serverRepo;
        final RegisterDataAccessInterface registerDataAccessObject = offline ? localRepo : serverRepo;
        final RecommendDataAccessInterface recommendDataAccessObject = offline ? localRepo : serverRepo;
        final ConnectDataAccessInterface connectDataAccessObject = offline ? localRepo : serverRepo;
        final DislikeDataAccessInterface dislikeDataAccessObject = offline ? localRepo : serverRepo;
        final LoadMatchesDataAccessInterface loadMatchesDataAccessObject = offline ? localRepo : serverRepo;
        final LoadProfileDataAccessInterface loadProfileDataAccessObject = offline ? localRepo : serverRepo;
        final UpdateProfileDataAccessInterface updateProfileDataAccessObject = offline ? localRepo : serverRepo;
        final DeleteAccountDataAccessInterface deleteAccountDataAccessObject = offline ? localRepo : serverRepo;
        final SendMessageDataAccessInterface sendMessageDataAccessObject = offline ? localRepo : serverRepo;
        final LoadMessageDataAccessInterface loadMessageDataAccessObject = offline ? localRepo : serverRepo;
        final CreatePostingDataAccessInterface createPostingDataAccessObject = offline ? localRepo : serverRepo;
        final ClosePostingDataAccessInterface closePostingDataAccessObject = offline ? localRepo : serverRepo;
        final LoadPostingsDataAccessInterface loadPostingsDataAccessObject = offline ? localRepo : serverRepo;
        final ApplyToPostingDataAccessInterface applyToPostingDataAccessObject = offline ? localRepo : serverRepo;
        final AcceptApplicationDataAccessInterface acceptApplicationDataAccessObject = offline ? localRepo : serverRepo;
        final DeclineApplicationDataAccessInterface declineApplicationDataAccessObject = offline ? localRepo : serverRepo;
        final LoadMyApplicationsDataAccessInterface loadMyApplicationsDataAccessObject =
                offline ? localRepo : serverRepo;
        // Falls back to a small offline dataset per-call if the live Semantic User API is
        // rate limited or unreachable, so a demo isn't derailed by a third-party outage.
        final UserAPIGatewayInterface userApiGateway =
                new FallbackUserApiGateway(new SemanticScholarGateway(), new LocalUserApiGateway());

        // ── Layer 3: ViewModels ───────────────────────────────────────────────
        final LoginViewModel loginViewModel = new LoginViewModel();
        final LogoutViewModel logoutViewModel = new LogoutViewModel();
        final DeleteAccountViewModel deleteAccountViewModel = new DeleteAccountViewModel();
        final RegisterViewModel registerViewModel = new RegisterViewModel();
        final PaperLookupViewModel paperLookupViewModel = new PaperLookupViewModel();
        final RecommendViewModel recommendViewModel = new RecommendViewModel();
        final LoadMatchesViewModel loadMatchesViewModel = new LoadMatchesViewModel();
        final ChatViewModel chatViewModel = new ChatViewModel();
        final UpdateProfileViewModel updateProfileViewModel = new UpdateProfileViewModel();
        updateProfileViewModel.setInstitutions(institutionCatalog.getAllInstitutions());
        final OpportunitiesViewModel opportunitiesViewModel = new OpportunitiesViewModel();
        final MyPostingsViewModel myPostingsViewModel = new MyPostingsViewModel();
        final MyApplicationsViewModel myApplicationsViewModel = new MyApplicationsViewModel();

        // ── Layer 3: Presenters ───────────────────────────────────────────────
        final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
        final LogoutPresenter logoutPresenter = new LogoutPresenter(logoutViewModel);
        final DeleteAccountPresenter deleteAccountPresenter =
                new DeleteAccountPresenter(logoutViewModel, deleteAccountViewModel);
        final RegisterPresenter registerPresenter = new RegisterPresenter(registerViewModel);
        final RequestEmailVerificationPresenter verificationPresenter =
                new RequestEmailVerificationPresenter(registerViewModel);
        final PaperLookupPresenter paperLookupPresenter = new PaperLookupPresenter(paperLookupViewModel);
        final RecommendPresenter recommendPresenter = new RecommendPresenter(recommendViewModel);
        final ConnectPresenter connectPresenter = new ConnectPresenter(loadMatchesViewModel);
        final DislikePresenter dislikePresenter = new DislikePresenter();
        final SkipPresenter skipPresenter = new SkipPresenter();
        final LoadMatchesPresenter loadMatchesPresenter = new LoadMatchesPresenter(loadMatchesViewModel);
        final SendMessagePresenter sendMessagePresenter = new SendMessagePresenter(chatViewModel);
        final LoadMessagePresenter loadMessagePresenter = new LoadMessagePresenter(chatViewModel);
        final UpdateProfilePresenter updateProfilePresenter =
                new UpdateProfilePresenter(updateProfileViewModel);
        final LoadProfilePresenter loadProfilePresenter = new LoadProfilePresenter(updateProfileViewModel);
        final CreatePostingPresenter createPostingPresenter =
                new CreatePostingPresenter(myPostingsViewModel);
        final ClosePostingPresenter closePostingPresenter =
                new ClosePostingPresenter(myPostingsViewModel);
        final LoadPostingsPresenter opportunitiesLoadPostingsPresenter =
                new LoadPostingsPresenter(opportunitiesViewModel);
        final LoadPostingsPresenter myPostingsLoadPostingsPresenter =
                new LoadPostingsPresenter(myPostingsViewModel);
        final ApplyToPostingPresenter applyToPostingPresenter =
                new ApplyToPostingPresenter(opportunitiesViewModel);
        final AcceptApplicationPresenter acceptApplicationPresenter =
                new AcceptApplicationPresenter(myPostingsViewModel);
        final DeclineApplicationPresenter declineApplicationPresenter =
                new DeclineApplicationPresenter(myPostingsViewModel);
        final LoadMyApplicationsPresenter loadMyApplicationsPresenter =
                new LoadMyApplicationsPresenter(myApplicationsViewModel);

        // ── Layer 2: Interactors ──────────────────────────────────────────────
        final LoginInteractor loginInteractor =
                new LoginInteractor(loginDataAccessObject, currentUserProvider, loginPresenter);
        final LogoutInteractor logoutInteractor =
                new LogoutInteractor(currentUserProvider, logoutPresenter);
        final DeleteAccountInteractor deleteAccountInteractor =
                new DeleteAccountInteractor(deleteAccountDataAccessObject, currentUserProvider, deleteAccountPresenter);
        final RegisterInteractor registerInteractor =
                new RegisterInteractor(registerDataAccessObject, currentUserProvider, registerPresenter);
        final RequestEmailVerificationInteractor verificationInteractor =
                new RequestEmailVerificationInteractor(
                        new RemoteVerificationEmailSender(SERVER_URL),
                        verificationPresenter);
        final PaperLookupInteractor paperLookupInteractor =
                new PaperLookupInteractor(userApiGateway, paperLookupPresenter);
        final RecommendInteractor recommendInteractor =
                new RecommendInteractor(recommendDataAccessObject, recommendPresenter);
        final ConnectInteractor connectInteractor =
                new ConnectInteractor(connectDataAccessObject, connectPresenter);
        final DislikeInteractor dislikeInteractor =
                new DislikeInteractor(dislikeDataAccessObject, dislikePresenter);
        final SkipInteractor skipInteractor =
                new SkipInteractor(skipPresenter);
        final LoadMatchesInteractor loadMatchesInteractor =
                new LoadMatchesInteractor(loadMatchesDataAccessObject, loadMatchesPresenter);
        final SendMessageInteractor sendMessageInteractor =
                new SendMessageInteractor(sendMessageDataAccessObject, sendMessagePresenter);
        final LoadMessageInteractor loadMessageInteractor =
                new LoadMessageInteractor(loadMessageDataAccessObject, loadMessagePresenter);
        final UpdateProfileInteractor updateProfileInteractor =
                new UpdateProfileInteractor(updateProfileDataAccessObject, updateProfilePresenter);
        final LoadProfileInteractor loadProfileInteractor =
                new LoadProfileInteractor(loadProfileDataAccessObject, loadProfilePresenter);
        final CreatePostingInteractor createPostingInteractor =
                new CreatePostingInteractor(createPostingDataAccessObject, createPostingPresenter);
        final ClosePostingInteractor closePostingInteractor =
                new ClosePostingInteractor(closePostingDataAccessObject, closePostingPresenter);
        final LoadPostingsInteractor opportunitiesLoadPostingsInteractor =
                new LoadPostingsInteractor(
                        loadPostingsDataAccessObject, opportunitiesLoadPostingsPresenter);
        final LoadPostingsInteractor myPostingsLoadPostingsInteractor =
                new LoadPostingsInteractor(
                        loadPostingsDataAccessObject, myPostingsLoadPostingsPresenter);
        final ApplyToPostingInteractor applyToPostingInteractor =
                new ApplyToPostingInteractor(applyToPostingDataAccessObject, applyToPostingPresenter);
        final AcceptApplicationInteractor acceptApplicationInteractor =
                new AcceptApplicationInteractor(
                        acceptApplicationDataAccessObject, acceptApplicationPresenter);
        final DeclineApplicationInteractor declineApplicationInteractor =
                new DeclineApplicationInteractor(
                        declineApplicationDataAccessObject, declineApplicationPresenter);
        final LoadMyApplicationsInteractor loadMyApplicationsInteractor =
                new LoadMyApplicationsInteractor(
                        loadMyApplicationsDataAccessObject, loadMyApplicationsPresenter);

        // ── Layer 3: Controllers ──────────────────────────────────────────────
        final LoginController loginController = new LoginController(loginInteractor);
        final LogoutController logoutController = new LogoutController(logoutInteractor);
        final DeleteAccountController deleteAccountController =
                new DeleteAccountController(deleteAccountInteractor);
        final RegisterController registerController = new RegisterController(registerInteractor);
        final RequestEmailVerificationController requestEmailVerificationController =
                new RequestEmailVerificationController(verificationInteractor);
        final PaperLookupController paperLookupController = new PaperLookupController(paperLookupInteractor);
        final RecommendController recommendController = new RecommendController(recommendInteractor);
        final ConnectController connectController = new ConnectController(connectInteractor);
        final DislikeController dislikeController = new DislikeController(dislikeInteractor);
        final SkipController skipController = new SkipController(skipInteractor);
        final LoadMatchesController loadMatchesController = new LoadMatchesController(loadMatchesInteractor);
        final SendMessageController sendMessageController = new SendMessageController(sendMessageInteractor);
        final LoadMessageController loadMessageController = new LoadMessageController(loadMessageInteractor);
        final UpdateProfileController updateProfileController =
                new UpdateProfileController(updateProfileInteractor);
        final LoadProfileController loadProfileController = new LoadProfileController(loadProfileInteractor);
        final CreatePostingController createPostingController =
                new CreatePostingController(createPostingInteractor);
        final ClosePostingController closePostingController =
                new ClosePostingController(closePostingInteractor);
        final LoadPostingsController opportunitiesLoadPostingsController =
                new LoadPostingsController(opportunitiesLoadPostingsInteractor);
        final LoadPostingsController myPostingsLoadPostingsController =
                new LoadPostingsController(myPostingsLoadPostingsInteractor);
        final ApplyToPostingController applyToPostingController =
                new ApplyToPostingController(applyToPostingInteractor);
        final AcceptApplicationController acceptApplicationController =
                new AcceptApplicationController(acceptApplicationInteractor);
        final DeclineApplicationController declineApplicationController =
                new DeclineApplicationController(declineApplicationInteractor);
        final LoadMyApplicationsController loadMyApplicationsController =
                new LoadMyApplicationsController(loadMyApplicationsInteractor);

        // ── Layer 4: UI ───────────────────────────────────────────────────────
        final MainView mainView = new MainView(
                loginController, loginViewModel,
                logoutController, logoutViewModel,
                deleteAccountController, deleteAccountViewModel,
                registerController, requestEmailVerificationController, registerViewModel,
                paperLookupController, paperLookupViewModel,
                recommendController, connectController, dislikeController, skipController, recommendViewModel,
                loadMatchesViewModel, loadMatchesController,
                sendMessageController, loadMessageController, chatViewModel,
                updateProfileController, loadProfileController, updateProfileViewModel,
                createPostingController,
                closePostingController,
                opportunitiesLoadPostingsController, myPostingsLoadPostingsController,
                applyToPostingController, acceptApplicationController, declineApplicationController,
                loadMyApplicationsController,
                opportunitiesViewModel, myPostingsViewModel, myApplicationsViewModel,
                currentUserProvider);

        return mainView;
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
