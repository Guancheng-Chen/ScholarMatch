// Runtime flows checked against the current controllers, interactors, presenters, and AppBuilder wiring.
(function () {
    function define(name, options = {}) {
        return {
            name,
            view: options.view || `${name}View`,
            controller: options.controller === false ? "" : (options.controller || `${name}Controller`),
            entry: options.entry || "execute()",
            inputBoundary: options.inputBoundary === false ? "" : (options.inputBoundary || `${name}InputBoundary`),
            interactor: options.interactor || `${name}Interactor`,
            request: options.request || "execute(inputData)",
            inputData: options.inputData === false ? "" : (options.inputData || `${name}InputData`),
            dataCalls: options.dataCalls || [],
            outputBoundary: options.outputBoundary === false ? "" : (options.outputBoundary || `${name}OutputBoundary`),
            presenter: options.presenter === false ? "" : (options.presenter || `${name}Presenter`),
            viewModel: options.viewModel || "",
            outputData: options.outputData === false ? "" : (options.outputData || `${name}OutputData`),
            success: options.success || "prepareSuccessView(outputData)",
            viewUpdate: options.viewUpdate || "update view model",
            failure: options.failure === false ? "" : (options.failure || "prepareFailView(errorMessage)"),
            failureViewUpdate: options.failureViewUpdate || "setErrorMessage(errorMessage)",
            validation: options.validation || "",
            note: options.note || ""
        };
    }

    window.SEQUENCE_SPECS = [
        define("AcceptApplication", {
            view: "OwnedPostingCard / MyPostingsView",
            entry: "accept(applicationId)",
            dataCalls: [{
                port: "AcceptApplicationDataAccessInterface",
                call: "acceptApplication(applicationId)",
                result: "PostingApplication",
                online: "PostingGateway",
                offline: "LocalPostingRepository"
            }],
            viewModel: "MyPostingsViewModel",
            viewUpdate: "updateApplicationStatus(); requestRefresh()"
        }),
        define("ApplyToPosting", {
            view: "ApplyToPostingPanel / OpportunitiesView",
            entry: "apply(postingId, message)",
            dataCalls: [{
                port: "ApplyToPostingDataAccessInterface",
                call: "applyToPosting(postingId, message)",
                result: "PostingApplication",
                online: "PostingGateway",
                offline: "LocalPostingRepository"
            }],
            viewModel: "OpportunitiesViewModel",
            viewUpdate: "removePosting(postingId); setSuccessMessage()"
        }),
        define("ChangeEmail", {
            view: "AccountSettingsView",
            entry: "execute(email, currentPassword, code)",
            dataCalls: [{
                port: "ChangeEmailDataAccessInterface",
                call: "changeEmail(email, currentPassword, code)",
                result: "User",
                online: "AccountSettingsGateway",
                offline: "LocalAccountSettingsRepository"
            }],
            presenter: "AccountSettingsPresenter",
            viewModel: "AccountSettingsViewModel / UpdateProfileViewModel",
            validation: "validate email, current password, and verification code",
            viewUpdate: "setCurrentEmail(); setCurrentUser(); setSuccessMessage()"
        }),
        define("ChangePassword", {
            view: "AccountSettingsView",
            entry: "execute(currentPassword, newPassword, confirmPassword)",
            dataCalls: [{
                port: "ChangePasswordDataAccessInterface",
                call: "changePassword(currentPassword, newPassword)",
                result: "void",
                online: "AccountSettingsGateway",
                offline: "LocalAccountSettingsRepository"
            }],
            presenter: "AccountSettingsPresenter",
            viewModel: "AccountSettingsViewModel",
            validation: "require current password; enforce 8–72 chars; confirm passwords match",
            viewUpdate: "setSuccessMessage()"
        }),
        define("ClosePosting", {
            view: "OwnedPostingCard / MyPostingsView",
            entry: "closePosting(postingId)",
            dataCalls: [{
                port: "ClosePostingDataAccessInterface",
                call: "closePosting(postingId)",
                result: "Posting",
                online: "PostingGateway",
                offline: "LocalPostingRepository"
            }],
            viewModel: "MyPostingsViewModel",
            viewUpdate: "replacePosting(); setSuccessMessage()"
        }),
        define("Connect", {
            view: "RecommendView",
            entry: "connect(userId, userData)",
            dataCalls: [{
                port: "ConnectDataAccessInterface",
                call: "connect(connectedUserId)",
                result: "boolean matched",
                online: "MatchingGateway",
                offline: "LocalMatchingRepository"
            }],
            viewModel: "LoadMatchesViewModel",
            success: "prepareMatchFound(outputData) / prepareNoMatch()",
            viewUpdate: "matchNotificationProperty().set(user) / no-op",
            failure: "prepareNoMatch()",
            failureViewUpdate: "no-op"
        }),
        define("CreatePosting", {
            view: "CreatePostingPanel / MyPostingsView",
            entry: "createPosting(fields)",
            dataCalls: [{
                port: "CreatePostingDataAccessInterface",
                call: "createPosting(title, description, field, type, capacity)",
                result: "Posting",
                online: "PostingGateway",
                offline: "LocalPostingRepository"
            }],
            viewModel: "MyPostingsViewModel",
            validation: "when present, capacity must be greater than zero",
            viewUpdate: "addPosting(); setSuccessMessage()"
        }),
        define("DeclineApplication", {
            view: "OwnedPostingCard / MyPostingsView",
            entry: "decline(applicationId)",
            dataCalls: [{
                port: "DeclineApplicationDataAccessInterface",
                call: "declineApplication(applicationId)",
                result: "PostingApplication",
                online: "PostingGateway",
                offline: "LocalPostingRepository"
            }],
            viewModel: "MyPostingsViewModel",
            viewUpdate: "updateApplicationStatus(); setSuccessMessage()"
        }),
        define("DeleteAccount", {
            view: "AccountSettingsView",
            entry: "deleteAccount()",
            inputData: false,
            outputData: false,
            dataCalls: [
                {
                    port: "DeleteAccountDataAccessInterface",
                    call: "deleteAccount()",
                    result: "void",
                    online: "ProfileGateway",
                    offline: "LocalProfileRepository"
                },
                {
                    port: "SessionClearerInterface",
                    call: "clearSession()",
                    result: "void",
                    online: "CurrentUserProvider",
                    offline: "CurrentUserProvider"
                }
            ],
            viewModel: "LogoutViewModel / DeleteAccountViewModel",
            success: "prepareSuccessView()",
            viewUpdate: "setLoggedOut()",
            failureViewUpdate: "setErrorMessage(errorMessage)"
        }),
        define("Dislike", {
            view: "RecommendView",
            entry: "dislike(userId)",
            outputData: false,
            dataCalls: [{
                port: "DislikeDataAccessInterface",
                call: "dislike(dislikedUserId)",
                result: "void",
                online: "MatchingGateway",
                offline: "LocalMatchingRepository"
            }],
            viewModel: "",
            success: "prepareSuccessView()",
            viewUpdate: "no-op presenter",
            failureViewUpdate: "no-op presenter"
        }),
        define("LoadMatches", {
            view: "LoadMatchesView / ChatView",
            entry: "execute()",
            inputData: false,
            dataCalls: [{
                port: "LoadMatchesDataAccessInterface",
                call: "getMatches()",
                result: "List<User>",
                online: "MatchingGateway",
                offline: "LocalMatchingRepository"
            }],
            viewModel: "LoadMatchesViewModel",
            viewUpdate: "matchedUsers.setAll(users)"
        }),
        define("LoadMessage", {
            view: "ChatView",
            entry: "loadMessages(otherUserId)",
            dataCalls: [{
                port: "LoadMessageDataAccessInterface",
                call: "getConversation(otherUserId)",
                result: "List<Message>",
                online: "MessagingGateway",
                offline: "LocalMessagingRepository"
            }],
            viewModel: "ChatViewModel",
            viewUpdate: "messages.setAll(messageData)"
        }),
        define("LoadMyApplications", {
            view: "MyApplicationsView",
            entry: "loadMyApplications()",
            inputData: false,
            dataCalls: [{
                port: "LoadMyApplicationsDataAccessInterface",
                call: "getMyApplications()",
                result: "List<PostingApplication>",
                online: "PostingGateway",
                offline: "LocalPostingRepository"
            }],
            viewModel: "MyApplicationsViewModel",
            viewUpdate: "setApplications(applicationData)"
        }),
        define("LoadPostings", {
            view: "OpportunitiesView / MyPostingsView",
            entry: "loadPostings(scope)",
            dataCalls: [
                {
                    port: "LoadPostingsDataAccessInterface",
                    call: "loadPostings(scope)",
                    result: "List<Posting>",
                    online: "PostingGateway",
                    offline: "LocalPostingRepository"
                },
                {
                    port: "LoadPostingsDataAccessInterface",
                    call: "loadApplicationsForOwnedPostings(scope, postings)",
                    result: "Map<String, List<PostingApplication>>",
                    online: "PostingGateway",
                    offline: "LocalPostingRepository"
                }
            ],
            viewModel: "PostingsListViewModel",
            viewUpdate: "setPostings(); setApplicationsByPostingId()"
        }),
        define("LoadProfile", {
            view: "UpdateProfileView",
            entry: "execute()",
            inputData: false,
            dataCalls: [{
                port: "LoadProfileDataAccessInterface",
                call: "getProfile()",
                result: "User",
                online: "ProfileGateway",
                offline: "LocalProfileRepository"
            }],
            viewModel: "UpdateProfileViewModel",
            viewUpdate: "setCurrentUser(userData)"
        }),
        define("Login", {
            view: "LoginView",
            entry: "login(email, password)",
            dataCalls: [
                {
                    port: "LoginDataAccessInterface",
                    call: "login(email, password)",
                    result: "AuthResult",
                    online: "AuthGateway",
                    offline: "LocalAuthRepository"
                },
                {
                    port: "SessionWriterInterface",
                    call: "setCurrentUserId(); setToken()",
                    result: "void",
                    online: "CurrentUserProvider",
                    offline: "CurrentUserProvider"
                }
            ],
            viewModel: "LoginViewModel",
            viewUpdate: "setLoggedInUserId(userId)"
        }),
        define("Logout", {
            view: "MainLayoutView / MainView",
            entry: "logout()",
            dataCalls: [{
                port: "SessionClearerInterface",
                call: "clearSession()",
                result: "void",
                online: "CurrentUserProvider",
                offline: "CurrentUserProvider"
            }],
            viewModel: "LogoutViewModel",
            failure: false,
            viewUpdate: "setLoggedOut()"
        }),
        define("PaperLookup", {
            view: "PublicationEditorPanel / UpdateProfileView",
            entry: "searchAuthors(name) / selectAuthor(authorId)",
            inputBoundary: "PaperLookupInputBoundary",
            interactor: "PaperLookupInteractor",
            request: "searchAuthors(input) / selectAuthor(input)",
            inputData: "SearchAuthorsInputData / SelectAuthorInputData",
            dataCalls: [
                {
                    port: "UserAPIGatewayInterface",
                    call: "searchAuthors(name) / getAuthor(id)",
                    result: "List<AuthorCandidate>",
                    online: "FallbackUserApiGateway → SemanticScholarGateway",
                    offline: "FallbackUserApiGateway → LocalUserApiGateway"
                },
                {
                    port: "UserAPIGatewayInterface",
                    call: "getAuthorPapers(authorId)",
                    result: "List<Publication>",
                    online: "FallbackUserApiGateway → SemanticScholarGateway",
                    offline: "FallbackUserApiGateway → LocalUserApiGateway"
                }
            ],
            outputBoundary: "PaperLookupOutputBoundary",
            presenter: "PaperLookupPresenter",
            viewModel: "PaperLookupViewModel",
            outputData: "AuthorCandidateData / Publication",
            validation: "reject a blank author query or an author id not present in the cached candidates",
            success: "prepareAuthorCandidates() / prepareAuthorPapersFound()",
            failure: "prepareError(message)",
            viewUpdate: "set author candidates / papers",
            failureViewUpdate: "setStatusMessage()",
            note: "The fallback adapter tries Semantic Scholar first and switches to the local dataset per failed call."
        }),
        define("Recommend", {
            view: "RecommendView",
            entry: "execute()",
            inputData: false,
            dataCalls: [
                {
                    port: "RecommendDataAccessInterface",
                    call: "getProfile()",
                    result: "User",
                    online: "MatchingGateway",
                    offline: "LocalMatchingRepository"
                },
                {
                    port: "RecommendDataAccessInterface",
                    call: "getRecommendations()",
                    result: "List<User>",
                    online: "MatchingGateway",
                    offline: "LocalMatchingRepository"
                }
            ],
            viewModel: "RecommendViewModel",
            validation: "stop when the current profile is incomplete",
            viewUpdate: "setCardStack(userData)"
        }),
        define("Register", {
            view: "RegisterView",
            entry: "execute(firstName, lastName, email, password, code)",
            dataCalls: [
                {
                    port: "RegisterDataAccessInterface",
                    call: "register(RegisterAccountData)",
                    result: "AuthResult",
                    online: "AuthGateway",
                    offline: "LocalAuthRepository"
                },
                {
                    port: "SessionWriterInterface",
                    call: "setCurrentUserId(); setToken()",
                    result: "void",
                    online: "CurrentUserProvider",
                    offline: "CurrentUserProvider"
                }
            ],
            viewModel: "RegisterViewModel",
            validation: "require names; validate email format; enforce password length; pass verification code",
            viewUpdate: "setSuccessMessage(); setRegistrationSucceeded(true)"
        }),
        define("RequestEmailVerification", {
            view: "RegisterView / AccountSettingsView",
            entry: "sendVerificationCode(email)",
            dataCalls: [{
                port: "VerificationEmailSenderDataAccessInterface",
                call: "requestVerificationCode(normalizedEmail)",
                result: "void",
                online: "RemoteVerificationEmailSender (registration) / AccountSettingsGateway (email change)",
                offline: "LocalAccountSettingsRepository"
            }],
            presenter: "RequestEmailVerificationPresenter / AccountSettingsPresenter",
            viewModel: "RegisterViewModel / AccountSettingsViewModel",
            validation: "normalize and validate the email address",
            viewUpdate: "setVerificationMessage() / setSuccessMessage()",
            note: "AppBuilder creates separate registration and email-change interactors that share the same input boundary."
        }),
        define("SendMessage", {
            view: "ChatView",
            entry: "sendMessage(receiverId, content)",
            dataCalls: [{
                port: "SendMessageDataAccessInterface",
                call: "sendMessage(receiverId, content)",
                result: "Message",
                online: "MessagingGateway",
                offline: "LocalMessagingRepository"
            }],
            viewModel: "ChatViewModel",
            validation: "require receiver and non-blank content of at most 2000 characters",
            viewUpdate: "messages.add(messageData)"
        }),
        define("Skip", {
            view: "RecommendView",
            entry: "skip(userId)",
            dataCalls: [],
            outputData: false,
            presenter: "SkipPresenter",
            viewModel: "",
            success: "prepareSuccessView()",
            failure: false,
            viewUpdate: "no-op presenter",
            note: "Skip is intentionally local to the card stack; the interactor performs no data-access call."
        }),
        define("UpdateProfile", {
            view: "UpdateProfileView",
            entry: "updateProfile(fields)",
            dataCalls: [{
                port: "UpdateProfileDataAccessInterface",
                call: "updateProfile(inputData)",
                result: "User",
                online: "ProfileGateway",
                offline: "LocalProfileRepository"
            }],
            viewModel: "UpdateProfileViewModel",
            validation: "validate required fields, lengths, research interests, availability, metrics, and education dates",
            viewUpdate: "setSaveSuccessMessage()"
        })
    ];
}());
