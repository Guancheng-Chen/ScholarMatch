// Generated from the current src/main/java tree. Do not edit by hand.
window.UML_TYPES = {
  "AcademicEmailDomainDataAccessInterface": {
    "name": "AcademicEmailDomainDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/AcademicEmailDomainDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "isAcademicEmail",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AcademicLevel": {
    "name": "AcademicLevel",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/AcademicLevel.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "AcceptApplicationController": {
    "name": "AcceptApplicationController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.accept_application",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/accept_application/AcceptApplicationController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "AcceptApplicationInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AcceptApplicationController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "AcceptApplicationInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      },
      {
        "name": "accept",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AcceptApplicationDataAccessInterface": {
    "name": "AcceptApplicationDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/AcceptApplicationDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "acceptApplication",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AcceptApplicationInputBoundary": {
    "name": "AcceptApplicationInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.accept_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/accept_application/AcceptApplicationInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "AcceptApplicationInputData"
          }
        ]
      }
    ]
  },
  "AcceptApplicationInputData": {
    "name": "AcceptApplicationInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.accept_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/accept_application/AcceptApplicationInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "applicationId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "AcceptApplicationInteractor": {
    "name": "AcceptApplicationInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.accept_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/accept_application/AcceptApplicationInteractor.java",
    "extends": [],
    "implements": [
      "AcceptApplicationInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "AcceptApplicationDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "AcceptApplicationOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AcceptApplicationInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "AcceptApplicationDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "AcceptApplicationOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "AcceptApplicationInputData"
          }
        ]
      }
    ]
  },
  "AcceptApplicationOutputBoundary": {
    "name": "AcceptApplicationOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.accept_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/accept_application/AcceptApplicationOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "AcceptApplicationOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AcceptApplicationOutputData": {
    "name": "AcceptApplicationOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.accept_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/accept_application/AcceptApplicationOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "application",
        "type": "PostingApplicationData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "AcceptApplicationPresenter": {
    "name": "AcceptApplicationPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.accept_application",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/accept_application/AcceptApplicationPresenter.java",
    "extends": [],
    "implements": [
      "AcceptApplicationOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "MyPostingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AcceptApplicationPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "MyPostingsViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "AcceptApplicationOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AccountSettingsGateway": {
    "name": "AccountSettingsGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/AccountSettingsGateway.java",
    "extends": [],
    "implements": [
      "VerificationEmailSenderDataAccessInterface",
      "ChangeEmailDataAccessInterface",
      "ChangePasswordDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "http",
        "type": "ServerHttpClient",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "userMapper",
        "type": "ScholarUserMapper",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AccountSettingsGateway",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "http",
            "type": "ServerHttpClient"
          },
          {
            "name": "institutionCatalog",
            "type": "InstitutionCatalogDataAccessInterface"
          }
        ]
      },
      {
        "name": "requestVerificationCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "changeEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "verificationCode",
            "type": "String"
          }
        ]
      },
      {
        "name": "changePassword",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "newPassword",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AccountSettingsPresenter": {
    "name": "AccountSettingsPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.account_settings",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/account_settings/AccountSettingsPresenter.java",
    "extends": [],
    "implements": [
      "RequestEmailVerificationOutputBoundary",
      "ChangeEmailOutputBoundary",
      "ChangePasswordOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "AccountSettingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "profileViewModel",
        "type": "UpdateProfileViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AccountSettingsPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "AccountSettingsViewModel"
          },
          {
            "name": "profileViewModel",
            "type": "UpdateProfileViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "RequestEmailVerificationOutputData"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ChangeEmailOutputData"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ChangePasswordOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AccountSettingsView": {
    "name": "AccountSettingsView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/AccountSettingsView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "FIELD_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FIELD_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DELETE_ACCOUNT_WARNING",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "successListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "currentEmailListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "deleteErrorListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "viewModel",
        "type": "AccountSettingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "deleteAccountViewModel",
        "type": "DeleteAccountViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AccountSettingsView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "requestController",
            "type": "RequestEmailVerificationController"
          },
          {
            "name": "changeEmailController",
            "type": "ChangeEmailController"
          },
          {
            "name": "changePasswordController",
            "type": "ChangePasswordController"
          },
          {
            "name": "viewModel",
            "type": "AccountSettingsViewModel"
          },
          {
            "name": "deleteAccountController",
            "type": "DeleteAccountController"
          },
          {
            "name": "deleteAccountViewModel",
            "type": "DeleteAccountViewModel"
          }
        ]
      },
      {
        "name": "title",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "section",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "labeled",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "field",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextField",
        "parameters": [
          {
            "name": "placeholder",
            "type": "String"
          }
        ]
      },
      {
        "name": "passwordField",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPasswordField",
        "parameters": [
          {
            "name": "placeholder",
            "type": "String"
          }
        ]
      },
      {
        "name": "styleField",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "field",
            "type": "JTextField"
          }
        ]
      },
      {
        "name": "addField",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "panel",
            "type": "JPanel"
          },
          {
            "name": "component",
            "type": "JComponent"
          }
        ]
      },
      {
        "name": "run",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "button",
            "type": "JButton"
          },
          {
            "name": "action",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "show",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "messageType",
            "type": "int"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "AccountSettingsViewModel": {
    "name": "AccountSettingsViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.account_settings",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/account_settings/AccountSettingsViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "successMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "currentEmail",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "successMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "currentEmailProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setSuccessMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setCurrentEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AppBuilder": {
    "name": "AppBuilder",
    "kind": "class",
    "packageName": "com.scholarmatch.app",
    "sourcePath": "src/main/java/com/scholarmatch/app/AppBuilder.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "SERVER_URL",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "response",
        "type": "HttpResponse<Void>",
        "visibility": "~",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "statusCode",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "return response.",
        "parameters": []
      }
    ]
  },
  "ApplicationCard": {
    "name": "ApplicationCard",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/ApplicationCard.java",
    "extends": [
      "RoundedPanel"
    ],
    "implements": [
      "Reflowable"
    ],
    "attributes": [
      {
        "name": "MAX_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "STACK_BREAKPOINT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DATE_FORMAT",
        "type": "DateTimeFormatter",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "header",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "title",
        "type": "JLabel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "status",
        "type": "JLabel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ApplicationCard",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "application",
            "type": "PostingApplicationData"
          }
        ]
      },
      {
        "name": "reflow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "format",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ApplyToPostingController": {
    "name": "ApplyToPostingController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.apply_to_posting",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/apply_to_posting/ApplyToPostingController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "ApplyToPostingInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ApplyToPostingController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "ApplyToPostingInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "apply",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ApplyToPostingDataAccessInterface": {
    "name": "ApplyToPostingDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/ApplyToPostingDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "applyToPosting",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ApplyToPostingDialog": {
    "name": "ApplyToPostingDialog",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/ApplyToPostingDialog.java",
    "extends": [
      "JDialog"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "SIZE",
        "type": "Dimension",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MINIMUM_SIZE",
        "type": "Dimension",
        "visibility": "−",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ApplyToPostingDialog",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "owner",
            "type": "Window"
          },
          {
            "name": "posting",
            "type": "PostingData"
          },
          {
            "name": "onSubmit",
            "type": "BiConsumer<String, String>"
          }
        ]
      },
      {
        "name": "showDialog",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "parent",
            "type": "Component"
          },
          {
            "name": "posting",
            "type": "PostingData"
          },
          {
            "name": "onSubmit",
            "type": "BiConsumer<String, String>"
          }
        ]
      }
    ]
  },
  "ApplyToPostingInputBoundary": {
    "name": "ApplyToPostingInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.apply_to_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/apply_to_posting/ApplyToPostingInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ApplyToPostingInputData"
          }
        ]
      }
    ]
  },
  "ApplyToPostingInputData": {
    "name": "ApplyToPostingInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.apply_to_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/apply_to_posting/ApplyToPostingInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "postingId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "message",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ApplyToPostingInteractor": {
    "name": "ApplyToPostingInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.apply_to_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/apply_to_posting/ApplyToPostingInteractor.java",
    "extends": [],
    "implements": [
      "ApplyToPostingInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "ApplyToPostingDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "ApplyToPostingOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ApplyToPostingInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "ApplyToPostingDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "ApplyToPostingOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ApplyToPostingInputData"
          }
        ]
      }
    ]
  },
  "ApplyToPostingOutputBoundary": {
    "name": "ApplyToPostingOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.apply_to_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/apply_to_posting/ApplyToPostingOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ApplyToPostingOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ApplyToPostingOutputData": {
    "name": "ApplyToPostingOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.apply_to_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/apply_to_posting/ApplyToPostingOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "application",
        "type": "PostingApplicationData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ApplyToPostingPanel": {
    "name": "ApplyToPostingPanel",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/ApplyToPostingPanel.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "MESSAGE_ROWS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MESSAGE_COLUMNS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "posting",
        "type": "PostingData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "onSubmit",
        "type": "BiConsumer<String, String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "onCancel",
        "type": "Runnable",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "messageArea",
        "type": "JTextArea",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "characterCount",
        "type": "JLabel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "validationMessage",
        "type": "JLabel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ApplyToPostingPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          },
          {
            "name": "onSubmit",
            "type": "BiConsumer<String, String>"
          },
          {
            "name": "onCancel",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "header",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": []
      },
      {
        "name": "postingSummary",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": []
      },
      {
        "name": "applicationSection",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": []
      },
      {
        "name": "footer",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": []
      },
      {
        "name": "submit",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "updateCount",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "textArea",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextArea",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "capacity",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          }
        ]
      },
      {
        "name": "format",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ApplyToPostingPresenter": {
    "name": "ApplyToPostingPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.apply_to_posting",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/apply_to_posting/ApplyToPostingPresenter.java",
    "extends": [],
    "implements": [
      "ApplyToPostingOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "OpportunitiesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ApplyToPostingPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "OpportunitiesViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ApplyToPostingOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "AuthGateway": {
    "name": "AuthGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/AuthGateway.java",
    "extends": [],
    "implements": [
      "LoginDataAccessInterface",
      "RegisterDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "http",
        "type": "ServerHttpClient",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AuthGateway",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "http",
            "type": "ServerHttpClient"
          }
        ]
      },
      {
        "name": "login",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          }
        ]
      },
      {
        "name": "register",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "data",
            "type": "RegisterAccountData"
          }
        ]
      },
      {
        "name": "authResultFromJson",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "node",
            "type": "JsonNode"
          }
        ]
      }
    ]
  },
  "AuthorCandidateData": {
    "name": "AuthorCandidateData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/paper_lookup/AuthorCandidateData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "authorId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "name",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "affiliations",
        "type": "List<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "paperCount",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "hIndex",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "citationCount",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AuthorCandidateData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          },
          {
            "name": "name",
            "type": "String"
          },
          {
            "name": "affiliations",
            "type": "List<String>"
          },
          {
            "name": "paperCount",
            "type": "Integer"
          },
          {
            "name": "hIndex",
            "type": "Integer"
          },
          {
            "name": "citationCount",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "from",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthorCandidateData",
        "parameters": [
          {
            "name": "candidate",
            "type": "AuthorCandidateDataAccessInterface"
          }
        ]
      },
      {
        "name": "getAuthorId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getAffiliations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": []
      },
      {
        "name": "getPaperCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getHIndex",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getCitationCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      }
    ]
  },
  "AuthorCandidateDataAccessInterface": {
    "name": "AuthorCandidateDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/AuthorCandidateDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getAuthorId",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getName",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getAffiliations",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": []
      },
      {
        "name": "getPaperCount",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getHIndex",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getCitationCount",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      }
    ]
  },
  "AuthorCandidateDto": {
    "name": "AuthorCandidateDto",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/paper_lookup/AuthorCandidateDto.java",
    "extends": [],
    "implements": [
      "AuthorCandidateDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "authorId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "name",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "affiliations",
        "type": "List<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "paperCount",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "hIndex",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "citationCount",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AuthorCandidateDto",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          },
          {
            "name": "name",
            "type": "String"
          },
          {
            "name": "affiliations",
            "type": "List<String>"
          },
          {
            "name": "paperCount",
            "type": "Integer"
          },
          {
            "name": "hIndex",
            "type": "Integer"
          },
          {
            "name": "citationCount",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "getAuthorId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getAffiliations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": []
      },
      {
        "name": "getPaperCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getHIndex",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getCitationCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      }
    ]
  },
  "AuthResult": {
    "name": "AuthResult",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/AuthResult.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "token",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "userId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "displayName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "AuthShellView": {
    "name": "AuthShellView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/AuthShellView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "loginViewModel",
        "type": "LoginViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "registerViewModel",
        "type": "RegisterViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "authenticatedLoginListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "authenticatedRegisterListener",
        "type": "Consumer<Boolean>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "AuthShellView",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loginController",
            "type": "LoginController"
          },
          {
            "name": "loginViewModel",
            "type": "LoginViewModel"
          },
          {
            "name": "registerController",
            "type": "RegisterController"
          },
          {
            "name": "verificationController",
            "type": "RequestEmailVerificationController"
          },
          {
            "name": "registerViewModel",
            "type": "RegisterViewModel"
          },
          {
            "name": "onAuthenticated",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "showLoginDialog",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "loginController",
            "type": "LoginController"
          },
          {
            "name": "ownerComponent",
            "type": "Component"
          }
        ]
      },
      {
        "name": "showRegisterDialog",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "registerController",
            "type": "RegisterController"
          },
          {
            "name": "verificationController",
            "type": "RequestEmailVerificationController"
          },
          {
            "name": "ownerComponent",
            "type": "Component"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "Buttons": {
    "name": "Buttons",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.style",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/style/Buttons.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "ARC",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "Buttons",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "accent",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "button",
            "type": "JButton"
          }
        ]
      },
      {
        "name": "success",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "button",
            "type": "JButton"
          }
        ]
      },
      {
        "name": "danger",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "button",
            "type": "JButton"
          }
        ]
      },
      {
        "name": "outlined",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "button",
            "type": "JButton"
          }
        ]
      },
      {
        "name": "solid",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "button",
            "type": "JButton"
          },
          {
            "name": "base",
            "type": "Color"
          },
          {
            "name": "hover",
            "type": "Color"
          },
          {
            "name": "pressed",
            "type": "Color"
          },
          {
            "name": "foreground",
            "type": "Color"
          }
        ]
      }
    ]
  },
  "CenteringScrollPanel": {
    "name": "CenteringScrollPanel",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.style",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/style/CenteringScrollPanel.java",
    "extends": [
      "JPanel"
    ],
    "implements": [
      "Scrollable"
    ],
    "attributes": [
      {
        "name": "UNIT_INCREMENT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "CenteringScrollPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "child",
            "type": "JComponent"
          }
        ]
      },
      {
        "name": "setBounds",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "x",
            "type": "int"
          },
          {
            "name": "y",
            "type": "int"
          },
          {
            "name": "width",
            "type": "int"
          },
          {
            "name": "height",
            "type": "int"
          }
        ]
      },
      {
        "name": "reflowNow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "notifyReflowable",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "root",
            "type": "Container"
          }
        ]
      },
      {
        "name": "getPreferredScrollableViewportSize",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Dimension",
        "parameters": []
      },
      {
        "name": "getScrollableUnitIncrement",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": [
          {
            "name": "visibleRect",
            "type": "Rectangle"
          },
          {
            "name": "orientation",
            "type": "int"
          },
          {
            "name": "direction",
            "type": "int"
          }
        ]
      },
      {
        "name": "getScrollableBlockIncrement",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": [
          {
            "name": "visibleRect",
            "type": "Rectangle"
          },
          {
            "name": "orientation",
            "type": "int"
          },
          {
            "name": "direction",
            "type": "int"
          }
        ]
      },
      {
        "name": "getScrollableTracksViewportWidth",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "getScrollableTracksViewportHeight",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      }
    ]
  },
  "ChangeEmailController": {
    "name": "ChangeEmailController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.change_email",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/change_email/ChangeEmailController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "ChangeEmailInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ChangeEmailController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "ChangeEmailInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "verificationCode",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChangeEmailDataAccessInterface": {
    "name": "ChangeEmailDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/ChangeEmailDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "changeEmail",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "verificationCode",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChangeEmailInputBoundary": {
    "name": "ChangeEmailInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.change_email",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_email/ChangeEmailInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ChangeEmailInputData"
          }
        ]
      }
    ]
  },
  "ChangeEmailInputData": {
    "name": "ChangeEmailInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.change_email",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_email/ChangeEmailInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "currentPassword",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationCode",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ChangeEmailInteractor": {
    "name": "ChangeEmailInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.change_email",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_email/ChangeEmailInteractor.java",
    "extends": [],
    "implements": [
      "ChangeEmailInputBoundary"
    ],
    "attributes": [
      {
        "name": "EMAIL_PATTERN",
        "type": "Pattern",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "dataAccessObject",
        "type": "ChangeEmailDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "ChangeEmailOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ChangeEmailInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "ChangeEmailDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "ChangeEmailOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ChangeEmailInputData"
          }
        ]
      },
      {
        "name": "normalize",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "isBlank",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChangeEmailOutputBoundary": {
    "name": "ChangeEmailOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.change_email",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_email/ChangeEmailOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ChangeEmailOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChangeEmailOutputData": {
    "name": "ChangeEmailOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.change_email",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_email/ChangeEmailOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "user",
        "type": "UserData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ChangePasswordController": {
    "name": "ChangePasswordController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.change_password",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/change_password/ChangePasswordController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "ChangePasswordInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ChangePasswordController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "ChangePasswordInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "newPassword",
            "type": "String"
          },
          {
            "name": "confirmPassword",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChangePasswordDataAccessInterface": {
    "name": "ChangePasswordDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/ChangePasswordDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "changePassword",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "newPassword",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChangePasswordInputBoundary": {
    "name": "ChangePasswordInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.change_password",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_password/ChangePasswordInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ChangePasswordInputData"
          }
        ]
      }
    ]
  },
  "ChangePasswordInputData": {
    "name": "ChangePasswordInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.change_password",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_password/ChangePasswordInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "currentPassword",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "newPassword",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "confirmPassword",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ChangePasswordInteractor": {
    "name": "ChangePasswordInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.change_password",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_password/ChangePasswordInteractor.java",
    "extends": [],
    "implements": [
      "ChangePasswordInputBoundary"
    ],
    "attributes": [
      {
        "name": "MIN_PASSWORD_LENGTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_PASSWORD_LENGTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "dataAccessObject",
        "type": "ChangePasswordDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "ChangePasswordOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ChangePasswordInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "ChangePasswordDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "ChangePasswordOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ChangePasswordInputData"
          }
        ]
      }
    ]
  },
  "ChangePasswordOutputBoundary": {
    "name": "ChangePasswordOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.change_password",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_password/ChangePasswordOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ChangePasswordOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChangePasswordOutputData": {
    "name": "ChangePasswordOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.change_password",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/change_password/ChangePasswordOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "ChatView": {
    "name": "ChatView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/ChatView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "LIST_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "BUBBLE_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "POLL_INTERVAL_MS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "TIME_FORMAT",
        "type": "DateTimeFormatter",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "sendMessageController",
        "type": "SendMessageController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadMessageController",
        "type": "LoadMessageController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "chatViewModel",
        "type": "ChatViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadMatchesViewModel",
        "type": "LoadMatchesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "currentUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "pollTimer",
        "type": "Timer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "matchList",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "messageList",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "messageScroll",
        "type": "JScrollPane",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "conversationTitle",
        "type": "JLabel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "messageField",
        "type": "JTextField",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "sendButton",
        "type": "JButton",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "selectedPartner",
        "type": "UserData",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "renderedMessageIds",
        "type": "List<String>",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "ChatView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "sendMessageController",
            "type": "SendMessageController"
          },
          {
            "name": "loadMessageController",
            "type": "LoadMessageController"
          },
          {
            "name": "loadMatchesController",
            "type": "LoadMatchesController"
          },
          {
            "name": "chatViewModel",
            "type": "ChatViewModel"
          },
          {
            "name": "loadMatchesViewModel",
            "type": "LoadMatchesViewModel"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "rebuildMatchList",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "buildMatchRow",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JComponent",
        "parameters": [
          {
            "name": "match",
            "type": "UserData"
          }
        ]
      },
      {
        "name": "selectConversation",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "partner",
            "type": "UserData"
          }
        ]
      },
      {
        "name": "clearConversationSelection",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "sendCurrentMessage",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "rebuildMessageList",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "buildMessageBubble",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "message",
            "type": "MessageData"
          }
        ]
      },
      {
        "name": "escapeHtml",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ChatViewModel": {
    "name": "ChatViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.chat",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/chat/ChatViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "messages",
        "type": "ObservableListModel<MessageData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "currentUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "getMessages",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<MessageData>",
        "parameters": []
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "getCurrentUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "setCurrentUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "currentUserId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ClasspathAcademicEmailDomainRepository": {
    "name": "ClasspathAcademicEmailDomainRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/ClasspathAcademicEmailDomainRepository.java",
    "extends": [],
    "implements": [
      "AcademicEmailDomainDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "RESOURCE_NAME",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "domains",
        "type": "Set<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ClasspathAcademicEmailDomainRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "ClasspathAcademicEmailDomainRepository",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "resourceStream",
            "type": "InputStream"
          }
        ]
      },
      {
        "name": "isAcademicEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "loadDomains",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Set<String>",
        "parameters": [
          {
            "name": "stream",
            "type": "InputStream"
          }
        ]
      },
      {
        "name": "extractDomain",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "normalizeDomain",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "domain",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ClasspathInstitutionCatalogRepository": {
    "name": "ClasspathInstitutionCatalogRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/ClasspathInstitutionCatalogRepository.java",
    "extends": [],
    "implements": [
      "InstitutionCatalogDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "DEFAULT_RESOURCE",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "institutionsById",
        "type": "Map<String, Institution>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "columns",
        "type": "return",
        "visibility": "~",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "ClasspathInstitutionCatalogRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "ClasspathInstitutionCatalogRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "resourceName",
            "type": "String"
          }
        ]
      },
      {
        "name": "ClasspathInstitutionCatalogRepository",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "stream",
            "type": "InputStream"
          }
        ]
      },
      {
        "name": "getAllInstitutions",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Institution>",
        "parameters": []
      },
      {
        "name": "findById",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Institution",
        "parameters": [
          {
            "name": "institutionId",
            "type": "String"
          }
        ]
      },
      {
        "name": "load",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Map<String, Institution>",
        "parameters": [
          {
            "name": "stream",
            "type": "InputStream"
          }
        ]
      },
      {
        "name": "parseCsvLine",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": [
          {
            "name": "line",
            "type": "String"
          }
        ]
      },
      {
        "name": "add",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "columns.",
        "parameters": [
          {
            "name": "arg1",
            "type": "value.toString()"
          }
        ]
      }
    ]
  },
  "ClosePostingController": {
    "name": "ClosePostingController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.close_posting",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/close_posting/ClosePostingController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "ClosePostingInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ClosePostingController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "ClosePostingInputBoundary"
          }
        ]
      },
      {
        "name": "closePosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ClosePostingDataAccessInterface": {
    "name": "ClosePostingDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/ClosePostingDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "closePosting",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Posting",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ClosePostingInputBoundary": {
    "name": "ClosePostingInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.close_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/close_posting/ClosePostingInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ClosePostingInputData"
          }
        ]
      }
    ]
  },
  "ClosePostingInputData": {
    "name": "ClosePostingInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.close_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/close_posting/ClosePostingInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "postingId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ClosePostingInteractor": {
    "name": "ClosePostingInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.close_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/close_posting/ClosePostingInteractor.java",
    "extends": [],
    "implements": [
      "ClosePostingInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "ClosePostingDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "ClosePostingOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ClosePostingInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "ClosePostingDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "ClosePostingOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ClosePostingInputData"
          }
        ]
      }
    ]
  },
  "ClosePostingOutputBoundary": {
    "name": "ClosePostingOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.close_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/close_posting/ClosePostingOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ClosePostingOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ClosePostingOutputData": {
    "name": "ClosePostingOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.close_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/close_posting/ClosePostingOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "posting",
        "type": "PostingData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ClosePostingPresenter": {
    "name": "ClosePostingPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.close_posting",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/close_posting/ClosePostingPresenter.java",
    "extends": [],
    "implements": [
      "ClosePostingOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "MyPostingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ClosePostingPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "MyPostingsViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ClosePostingOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "CollaborationType": {
    "name": "CollaborationType",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/CollaborationType.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "ConfirmationDialog": {
    "name": "ConfirmationDialog",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/ConfirmationDialog.java",
    "extends": [
      "JDialog"
    ],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "ConfirmationDialog",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "owner",
            "type": "Window"
          },
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "confirmText",
            "type": "String"
          },
          {
            "name": "onConfirm",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "showDialog",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "parent",
            "type": "Component"
          },
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "confirmText",
            "type": "String"
          },
          {
            "name": "onConfirm",
            "type": "Runnable"
          }
        ]
      }
    ]
  },
  "ConnectController": {
    "name": "ConnectController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.connect",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/connect/ConnectController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "ConnectInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ConnectController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "ConnectInputBoundary"
          }
        ]
      },
      {
        "name": "connect",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "connectedUserId",
            "type": "String"
          },
          {
            "name": "connectedUser",
            "type": "UserData"
          }
        ]
      }
    ]
  },
  "ConnectDataAccessInterface": {
    "name": "ConnectDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/ConnectDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "connect",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "connectedUserId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ConnectInputBoundary": {
    "name": "ConnectInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.connect",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/connect/ConnectInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ConnectInputData"
          }
        ]
      }
    ]
  },
  "ConnectInputData": {
    "name": "ConnectInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.connect",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/connect/ConnectInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "connectedUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "connectedUser",
        "type": "UserData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ConnectInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "connectedUserId",
            "type": "String"
          },
          {
            "name": "connectedUser",
            "type": "UserData"
          }
        ]
      },
      {
        "name": "getConnectedUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getConnectedUser",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "UserData",
        "parameters": []
      }
    ]
  },
  "ConnectInteractor": {
    "name": "ConnectInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.connect",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/connect/ConnectInteractor.java",
    "extends": [],
    "implements": [
      "ConnectInputBoundary"
    ],
    "attributes": [
      {
        "name": "connectDataAccessObject",
        "type": "ConnectDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "ConnectOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ConnectInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "connectDataAccessObject",
            "type": "ConnectDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "ConnectOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "ConnectInputData"
          }
        ]
      }
    ]
  },
  "ConnectOutputBoundary": {
    "name": "ConnectOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.connect",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/connect/ConnectOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareMatchFound",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ConnectOutputData"
          }
        ]
      },
      {
        "name": "prepareNoMatch",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "ConnectOutputData": {
    "name": "ConnectOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.connect",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/connect/ConnectOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "matchedUser",
        "type": "UserData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ConnectOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "matchedUser",
            "type": "UserData"
          }
        ]
      },
      {
        "name": "getMatchedUser",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "UserData",
        "parameters": []
      }
    ]
  },
  "ConnectPresenter": {
    "name": "ConnectPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.connect",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/connect/ConnectPresenter.java",
    "extends": [],
    "implements": [
      "ConnectOutputBoundary"
    ],
    "attributes": [
      {
        "name": "loadMatchesViewModel",
        "type": "LoadMatchesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ConnectPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loadMatchesViewModel",
            "type": "LoadMatchesViewModel"
          }
        ]
      },
      {
        "name": "prepareMatchFound",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "ConnectOutputData"
          }
        ]
      },
      {
        "name": "prepareNoMatch",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "CreatePostingController": {
    "name": "CreatePostingController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.create_posting",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/create_posting/CreatePostingController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "CreatePostingInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "CreatePostingController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "CreatePostingInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "createPosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          }
        ]
      }
    ]
  },
  "CreatePostingDataAccessInterface": {
    "name": "CreatePostingDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/CreatePostingDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "createPosting",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Posting",
        "parameters": [
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          }
        ]
      }
    ]
  },
  "CreatePostingDialog": {
    "name": "CreatePostingDialog",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/CreatePostingDialog.java",
    "extends": [
      "JDialog"
    ],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "CreatePostingDialog",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "owner",
            "type": "Window"
          },
          {
            "name": "onSubmit",
            "type": "Consumer<CreatePostingPanel.Submission>"
          }
        ]
      },
      {
        "name": "showDialog",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "parent",
            "type": "Component"
          },
          {
            "name": "onSubmit",
            "type": "Consumer<CreatePostingPanel.Submission>"
          }
        ]
      }
    ]
  },
  "CreatePostingInputBoundary": {
    "name": "CreatePostingInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.create_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/create_posting/CreatePostingInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "CreatePostingInputData"
          }
        ]
      }
    ]
  },
  "CreatePostingInputData": {
    "name": "CreatePostingInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.create_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/create_posting/CreatePostingInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "title",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "description",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchField",
        "type": "ResearchField",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "collaborationType",
        "type": "CollaborationType",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "capacity",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "CreatePostingInteractor": {
    "name": "CreatePostingInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.create_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/create_posting/CreatePostingInteractor.java",
    "extends": [],
    "implements": [
      "CreatePostingInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "CreatePostingDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "CreatePostingOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "CreatePostingInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "CreatePostingDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "CreatePostingOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "CreatePostingInputData"
          }
        ]
      }
    ]
  },
  "CreatePostingOutputBoundary": {
    "name": "CreatePostingOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.create_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/create_posting/CreatePostingOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "CreatePostingOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "CreatePostingOutputData": {
    "name": "CreatePostingOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.create_posting",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/create_posting/CreatePostingOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "posting",
        "type": "PostingData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "CreatePostingPanel": {
    "name": "CreatePostingPanel",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/CreatePostingPanel.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "onSubmit",
        "type": "Consumer<Submission>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "onCancel",
        "type": "Runnable",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "titleField",
        "type": "JTextField",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "descriptionArea",
        "type": "JTextArea",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchField",
        "type": "JComboBox<ResearchField>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "collaborationType",
        "type": "JComboBox<CollaborationType>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "capacityField",
        "type": "JTextField",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "validationMessage",
        "type": "JLabel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "CreatePostingPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "onSubmit",
            "type": "Consumer<Submission>"
          },
          {
            "name": "onCancel",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "fields",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": []
      },
      {
        "name": "footer",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": []
      },
      {
        "name": "submit",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "fail",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "focus",
            "type": "Component"
          }
        ]
      },
      {
        "name": "addField",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "panel",
            "type": "JPanel"
          },
          {
            "name": "text",
            "type": "String"
          },
          {
            "name": "field",
            "type": "JComponent"
          }
        ]
      },
      {
        "name": "configure",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "field",
            "type": "JTextField"
          },
          {
            "name": "name",
            "type": "String"
          }
        ]
      },
      {
        "name": "Submission",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "record",
        "parameters": [
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          }
        ]
      }
    ]
  },
  "CreatePostingPresenter": {
    "name": "CreatePostingPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.create_posting",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/create_posting/CreatePostingPresenter.java",
    "extends": [],
    "implements": [
      "CreatePostingOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "MyPostingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "CreatePostingPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "MyPostingsViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "CreatePostingOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "CurrentUserProvider": {
    "name": "CurrentUserProvider",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/CurrentUserProvider.java",
    "extends": [],
    "implements": [
      "CurrentUserProviderInterface",
      "SessionWriterInterface",
      "SessionClearerInterface"
    ],
    "attributes": [
      {
        "name": "currentUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "token",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "CurrentUserProvider",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "getCurrentUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "setCurrentUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          }
        ]
      },
      {
        "name": "clearSession",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "setToken",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "token",
            "type": "String"
          }
        ]
      },
      {
        "name": "getToken",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "isLoggedIn",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      }
    ]
  },
  "CurrentUserProviderInterface": {
    "name": "CurrentUserProviderInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/CurrentUserProviderInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getCurrentUserId",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getToken",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "DataAccessException": {
    "name": "DataAccessException",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.exception",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/exception/DataAccessException.java",
    "extends": [
      "RuntimeException"
    ],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "DataAccessException",
        "visibility": "#",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "DataAccessException",
        "visibility": "#",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "cause",
            "type": "Throwable"
          }
        ]
      }
    ]
  },
  "DeclineApplicationController": {
    "name": "DeclineApplicationController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.decline_application",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/decline_application/DeclineApplicationController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "DeclineApplicationInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DeclineApplicationController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "DeclineApplicationInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      },
      {
        "name": "decline",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DeclineApplicationDataAccessInterface": {
    "name": "DeclineApplicationDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/DeclineApplicationDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "declineApplication",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DeclineApplicationInputBoundary": {
    "name": "DeclineApplicationInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.decline_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/decline_application/DeclineApplicationInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "DeclineApplicationInputData"
          }
        ]
      }
    ]
  },
  "DeclineApplicationInputData": {
    "name": "DeclineApplicationInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.decline_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/decline_application/DeclineApplicationInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "applicationId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "DeclineApplicationInteractor": {
    "name": "DeclineApplicationInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.decline_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/decline_application/DeclineApplicationInteractor.java",
    "extends": [],
    "implements": [
      "DeclineApplicationInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "DeclineApplicationDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "DeclineApplicationOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DeclineApplicationInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "DeclineApplicationDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "DeclineApplicationOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "DeclineApplicationInputData"
          }
        ]
      }
    ]
  },
  "DeclineApplicationOutputBoundary": {
    "name": "DeclineApplicationOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.decline_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/decline_application/DeclineApplicationOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "DeclineApplicationOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DeclineApplicationOutputData": {
    "name": "DeclineApplicationOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.decline_application",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/decline_application/DeclineApplicationOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "application",
        "type": "PostingApplicationData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "DeclineApplicationPresenter": {
    "name": "DeclineApplicationPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.decline_application",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/decline_application/DeclineApplicationPresenter.java",
    "extends": [],
    "implements": [
      "DeclineApplicationOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "MyPostingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DeclineApplicationPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "MyPostingsViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "DeclineApplicationOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DegreeType": {
    "name": "DegreeType",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/DegreeType.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "DeleteAccountController": {
    "name": "DeleteAccountController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.delete_account",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/delete_account/DeleteAccountController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "DeleteAccountInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DeleteAccountController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "DeleteAccountInputBoundary"
          }
        ]
      },
      {
        "name": "deleteAccount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "DeleteAccountDataAccessInterface": {
    "name": "DeleteAccountDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/DeleteAccountDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "deleteAccount",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "DeleteAccountInputBoundary": {
    "name": "DeleteAccountInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.delete_account",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/delete_account/DeleteAccountInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "DeleteAccountInteractor": {
    "name": "DeleteAccountInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.delete_account",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/delete_account/DeleteAccountInteractor.java",
    "extends": [],
    "implements": [
      "DeleteAccountInputBoundary"
    ],
    "attributes": [
      {
        "name": "profileDataAccessObject",
        "type": "DeleteAccountDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "sessionManager",
        "type": "SessionClearerInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "DeleteAccountOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DeleteAccountInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "profileDataAccessObject",
            "type": "DeleteAccountDataAccessInterface"
          },
          {
            "name": "sessionManager",
            "type": "SessionClearerInterface"
          },
          {
            "name": "outputBoundary",
            "type": "DeleteAccountOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "DeleteAccountOutputBoundary": {
    "name": "DeleteAccountOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.delete_account",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/delete_account/DeleteAccountOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DeleteAccountPresenter": {
    "name": "DeleteAccountPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.delete_account",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/delete_account/DeleteAccountPresenter.java",
    "extends": [],
    "implements": [
      "DeleteAccountOutputBoundary"
    ],
    "attributes": [
      {
        "name": "logoutViewModel",
        "type": "LogoutViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "deleteAccountViewModel",
        "type": "DeleteAccountViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DeleteAccountPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "logoutViewModel",
            "type": "LogoutViewModel"
          },
          {
            "name": "deleteAccountViewModel",
            "type": "DeleteAccountViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DeleteAccountViewModel": {
    "name": "DeleteAccountViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.delete_account",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/delete_account/DeleteAccountViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DislikeController": {
    "name": "DislikeController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.dislike",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/dislike/DislikeController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "DislikeInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DislikeController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "DislikeInputBoundary"
          }
        ]
      },
      {
        "name": "dislike",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "dislikedUserId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DislikeDataAccessInterface": {
    "name": "DislikeDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/DislikeDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "dislike",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "dislikedScholarId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DislikeInputBoundary": {
    "name": "DislikeInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.dislike",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dislike/DislikeInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "DislikeInputData"
          }
        ]
      }
    ]
  },
  "DislikeInputData": {
    "name": "DislikeInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.dislike",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dislike/DislikeInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "dislikedUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DislikeInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dislikedUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getDislikedUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "DislikeInteractor": {
    "name": "DislikeInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.dislike",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dislike/DislikeInteractor.java",
    "extends": [],
    "implements": [
      "DislikeInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "DislikeDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "DislikeOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "DislikeInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "DislikeDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "DislikeOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "DislikeInputData"
          }
        ]
      }
    ]
  },
  "DislikeOutputBoundary": {
    "name": "DislikeOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.dislike",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dislike/DislikeOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "DislikePresenter": {
    "name": "DislikePresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.dislike",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/dislike/DislikePresenter.java",
    "extends": [],
    "implements": [
      "DislikeOutputBoundary"
    ],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "Education": {
    "name": "Education",
    "kind": "class",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/Education.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "institution",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "degreeType",
        "type": "DegreeType",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "startYear",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "startMonth",
        "type": "Month",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "endYear",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "endMonth",
        "type": "Month",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "Education",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "institution",
            "type": "String"
          },
          {
            "name": "degreeType",
            "type": "DegreeType"
          },
          {
            "name": "startYear",
            "type": "int"
          },
          {
            "name": "startMonth",
            "type": "Month"
          },
          {
            "name": "endYear",
            "type": "Integer"
          },
          {
            "name": "endMonth",
            "type": "Month"
          }
        ]
      },
      {
        "name": "getInstitution",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getDegreeType",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "DegreeType",
        "parameters": []
      },
      {
        "name": "getStartYear",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "getStartMonth",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Month",
        "parameters": []
      },
      {
        "name": "getEndYear",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getEndMonth",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Month",
        "parameters": []
      },
      {
        "name": "isOngoing",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      }
    ]
  },
  "EducationEditorPanel": {
    "name": "EducationEditorPanel",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/EducationEditorPanel.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "CARD_PADDING",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FIELD_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "LIST_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_YEAR",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_FUTURE_YEARS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "cardWidth",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "cardList",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "cards",
        "type": "List<EducationCard>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "EducationEditorPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "setEducations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "educations",
            "type": "List<Education>"
          }
        ]
      },
      {
        "name": "getEducations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Education>",
        "parameters": []
      },
      {
        "name": "addCard",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "initial",
            "type": "Education"
          }
        ]
      },
      {
        "name": "removeCard",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "card",
            "type": "EducationCard"
          }
        ]
      },
      {
        "name": "indexOfComponent",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": [
          {
            "name": "panel",
            "type": "JPanel"
          },
          {
            "name": "target",
            "type": "Component"
          }
        ]
      }
    ]
  },
  "EmailAccountType": {
    "name": "EmailAccountType",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/EmailAccountType.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "EmailChangeCodeDeliveryDataAccessInterface": {
    "name": "EmailChangeCodeDeliveryDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/EmailChangeCodeDeliveryDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "sendCode",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "code",
            "type": "String"
          }
        ]
      }
    ]
  },
  "EmailVerificationChallenge": {
    "name": "EmailVerificationChallenge",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/EmailVerificationChallenge.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "MAX_ATTEMPTS",
        "type": "int",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "codeDigest",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "expiresAt",
        "type": "Instant",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "attemptsRemaining",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "verified",
        "type": "boolean",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "EmailVerificationChallenge",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "code",
            "type": "String"
          },
          {
            "name": "expiresAt",
            "type": "Instant"
          }
        ]
      },
      {
        "name": "verify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "EmailVerificationResult",
        "parameters": [
          {
            "name": "submittedCode",
            "type": "String"
          },
          {
            "name": "now",
            "type": "Instant"
          }
        ]
      },
      {
        "name": "getEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "result",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "EmailVerificationResult",
        "parameters": [
          {
            "name": "outcome",
            "type": "EmailVerificationOutcome"
          }
        ]
      },
      {
        "name": "normalize",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "digest",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "EmailVerificationOutcome": {
    "name": "EmailVerificationOutcome",
    "kind": "enum",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/EmailVerificationOutcome.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "EmailVerificationResult": {
    "name": "EmailVerificationResult",
    "kind": "record",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/EmailVerificationResult.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "outcome",
        "type": "EmailVerificationOutcome",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "attemptsRemaining",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "ExternalServiceException": {
    "name": "ExternalServiceException",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.exception",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/exception/ExternalServiceException.java",
    "extends": [
      "DataAccessException"
    ],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "ExternalServiceException",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "ExternalServiceException",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "cause",
            "type": "Throwable"
          }
        ]
      }
    ]
  },
  "FallbackUserApiGateway": {
    "name": "FallbackUserApiGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/paper_lookup/FallbackUserApiGateway.java",
    "extends": [],
    "implements": [
      "UserAPIGatewayInterface"
    ],
    "attributes": [
      {
        "name": "primary",
        "type": "UserAPIGatewayInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "fallback",
        "type": "UserAPIGatewayInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "FallbackUserApiGateway",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "primary",
            "type": "UserAPIGatewayInterface"
          },
          {
            "name": "fallback",
            "type": "UserAPIGatewayInterface"
          }
        ]
      },
      {
        "name": "searchAuthors",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<AuthorCandidateDataAccessInterface>",
        "parameters": [
          {
            "name": "name",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthorCandidateDataAccessInterface",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthorPapers",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Publication>",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      },
      {
        "name": "attempt",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "<T>T",
        "parameters": [
          {
            "name": "primaryCall",
            "type": "Supplier<T>"
          },
          {
            "name": "fallbackCall",
            "type": "Supplier<T>"
          }
        ]
      }
    ]
  },
  "Format": {
    "name": "Format",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.style",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/style/Format.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "Format",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "stat",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "Integer"
          }
        ]
      }
    ]
  },
  "FundingStatus": {
    "name": "FundingStatus",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/FundingStatus.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "HttpSender": {
    "name": "HttpSender",
    "kind": "interface",
    "packageName": "com.scholarmatch.frameworks.data_access_object.http",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/http/HttpSender.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "send",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "HttpSenderResponse",
        "parameters": [
          {
            "name": "request",
            "type": "HttpRequest"
          }
        ]
      }
    ]
  },
  "HttpSenderResponse": {
    "name": "HttpSenderResponse",
    "kind": "record",
    "packageName": "com.scholarmatch.frameworks.data_access_object.http",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/http/HttpSenderResponse.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "statusCode",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "body",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "Icons": {
    "name": "Icons",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.style",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/style/Icons.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "Icons",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "of",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Icon",
        "parameters": [
          {
            "name": "code",
            "type": "Ikon"
          },
          {
            "name": "size",
            "type": "int"
          },
          {
            "name": "color",
            "type": "Color"
          }
        ]
      },
      {
        "name": "fromResource",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Icon",
        "parameters": [
          {
            "name": "classpathResource",
            "type": "String"
          },
          {
            "name": "size",
            "type": "int"
          }
        ]
      }
    ]
  },
  "InMemoryEmailVerificationChallengeRepository": {
    "name": "InMemoryEmailVerificationChallengeRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/InMemoryEmailVerificationChallengeRepository.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "challenges",
        "type": "Map<String, EmailVerificationChallenge>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "save",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "challenge",
            "type": "EmailVerificationChallenge"
          }
        ]
      },
      {
        "name": "findByEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Optional<EmailVerificationChallenge>",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "deleteByEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "normalize",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "Institution": {
    "name": "Institution",
    "kind": "class",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/Institution.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "MIT",
        "type": "Institution",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "UNIVERSITY_OF_TORONTO",
        "type": "Institution",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "UNIVERSITY_OF_CAMBRIDGE",
        "type": "Institution",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "OTHER",
        "type": "Institution",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "institutionId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "displayName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "Institution",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "institutionId",
            "type": "String"
          },
          {
            "name": "displayName",
            "type": "String"
          }
        ]
      },
      {
        "name": "getInstitutionId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "name",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getDisplayName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "equals",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "other",
            "type": "Object"
          }
        ]
      },
      {
        "name": "hashCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "toString",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "InstitutionCatalogDataAccessInterface": {
    "name": "InstitutionCatalogDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/InstitutionCatalogDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getAllInstitutions",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<Institution>",
        "parameters": []
      },
      {
        "name": "findById",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Institution",
        "parameters": [
          {
            "name": "institutionId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "InvalidRequestException": {
    "name": "InvalidRequestException",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.exception",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/exception/InvalidRequestException.java",
    "extends": [
      "DataAccessException"
    ],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "InvalidRequestException",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "InvalidRequestException",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "cause",
            "type": "Throwable"
          }
        ]
      }
    ]
  },
  "JdkHttpSender": {
    "name": "JdkHttpSender",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.http",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/http/JdkHttpSender.java",
    "extends": [],
    "implements": [
      "HttpSender"
    ],
    "attributes": [
      {
        "name": "httpClient",
        "type": "HttpClient",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "JdkHttpSender",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "httpClient",
            "type": "HttpClient"
          }
        ]
      },
      {
        "name": "send",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "HttpSenderResponse",
        "parameters": [
          {
            "name": "request",
            "type": "HttpRequest"
          }
        ]
      }
    ]
  },
  "JsonEnumSupport": {
    "name": "JsonEnumSupport",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/JsonEnumSupport.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "JsonEnumSupport",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "safeParseEnum",
        "visibility": "~",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "<T extends Enum<T>>T",
        "parameters": [
          {
            "name": "enumClass",
            "type": "Class<T>"
          },
          {
            "name": "value",
            "type": "String"
          },
          {
            "name": "defaultValue",
            "type": "T"
          }
        ]
      }
    ]
  },
  "LoadMatchesController": {
    "name": "LoadMatchesController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_matches",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_matches/LoadMatchesController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "loadMatchesInteractor",
        "type": "LoadMatchesInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMatchesController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loadMatchesInteractor",
            "type": "LoadMatchesInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadMatchesDataAccessInterface": {
    "name": "LoadMatchesDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/LoadMatchesDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getMatches",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<User>",
        "parameters": []
      }
    ]
  },
  "LoadMatchesInputBoundary": {
    "name": "LoadMatchesInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_matches",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_matches/LoadMatchesInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadMatchesInteractor": {
    "name": "LoadMatchesInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_matches",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_matches/LoadMatchesInteractor.java",
    "extends": [],
    "implements": [
      "LoadMatchesInputBoundary"
    ],
    "attributes": [
      {
        "name": "loadMatchesDataAccessObject",
        "type": "LoadMatchesDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "LoadMatchesOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMatchesInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loadMatchesDataAccessObject",
            "type": "LoadMatchesDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "LoadMatchesOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadMatchesOutputBoundary": {
    "name": "LoadMatchesOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_matches",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_matches/LoadMatchesOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadMatchesOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMatchesOutputData": {
    "name": "LoadMatchesOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_matches",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_matches/LoadMatchesOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "matches",
        "type": "List<UserData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMatchesOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "matches",
            "type": "List<UserData>"
          }
        ]
      },
      {
        "name": "getMatches",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<UserData>",
        "parameters": []
      }
    ]
  },
  "LoadMatchesPresenter": {
    "name": "LoadMatchesPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_matches",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_matches/LoadMatchesPresenter.java",
    "extends": [],
    "implements": [
      "LoadMatchesOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "LoadMatchesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMatchesPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "LoadMatchesViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadMatchesOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMatchesView": {
    "name": "LoadMatchesView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/LoadMatchesView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "viewModel",
        "type": "LoadMatchesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "cardList",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "centeringPanel",
        "type": "CenteringScrollPanel",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "LoadMatchesView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loadMatchesController",
            "type": "LoadMatchesController"
          },
          {
            "name": "viewModel",
            "type": "LoadMatchesViewModel"
          }
        ]
      },
      {
        "name": "rebuildCards",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadMatchesViewModel": {
    "name": "LoadMatchesViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.load_matches",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/load_matches/LoadMatchesViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "matchedUsers",
        "type": "ObservableListModel<UserData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "matchNotification",
        "type": "ObservableValue<UserData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "getMatchedUsers",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<UserData>",
        "parameters": []
      },
      {
        "name": "matchNotificationProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<UserData>",
        "parameters": []
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMessageController": {
    "name": "LoadMessageController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_message",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_message/LoadMessageController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "LoadMessageInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMessageController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "LoadMessageInputBoundary"
          }
        ]
      },
      {
        "name": "loadMessages",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "otherUserId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMessageDataAccessInterface": {
    "name": "LoadMessageDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/LoadMessageDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getConversation",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<Message>",
        "parameters": [
          {
            "name": "otherUserId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMessageInputBoundary": {
    "name": "LoadMessageInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_message/LoadMessageInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LoadMessageInputData"
          }
        ]
      }
    ]
  },
  "LoadMessageInputData": {
    "name": "LoadMessageInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_message/LoadMessageInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "otherUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMessageInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "otherUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getOtherUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "LoadMessageInteractor": {
    "name": "LoadMessageInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_message/LoadMessageInteractor.java",
    "extends": [],
    "implements": [
      "LoadMessageInputBoundary"
    ],
    "attributes": [
      {
        "name": "messageDataAccessObject",
        "type": "LoadMessageDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "LoadMessageOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMessageInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "messageDataAccessObject",
            "type": "LoadMessageDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "LoadMessageOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LoadMessageInputData"
          }
        ]
      }
    ]
  },
  "LoadMessageOutputBoundary": {
    "name": "LoadMessageOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_message/LoadMessageOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadMessageOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMessageOutputData": {
    "name": "LoadMessageOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_message/LoadMessageOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "messages",
        "type": "List<MessageData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMessageOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "messages",
            "type": "List<MessageData>"
          }
        ]
      },
      {
        "name": "getMessages",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<MessageData>",
        "parameters": []
      }
    ]
  },
  "LoadMessagePresenter": {
    "name": "LoadMessagePresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_message",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_message/LoadMessagePresenter.java",
    "extends": [],
    "implements": [
      "LoadMessageOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "ChatViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMessagePresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "ChatViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadMessageOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMyApplicationsController": {
    "name": "LoadMyApplicationsController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_my_applications",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_my_applications/LoadMyApplicationsController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "LoadMyApplicationsInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMyApplicationsController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "LoadMyApplicationsInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "loadMyApplications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadMyApplicationsDataAccessInterface": {
    "name": "LoadMyApplicationsDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/LoadMyApplicationsDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getMyApplications",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<PostingApplication>",
        "parameters": []
      }
    ]
  },
  "LoadMyApplicationsInputBoundary": {
    "name": "LoadMyApplicationsInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_my_applications",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_my_applications/LoadMyApplicationsInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadMyApplicationsInteractor": {
    "name": "LoadMyApplicationsInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_my_applications",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_my_applications/LoadMyApplicationsInteractor.java",
    "extends": [],
    "implements": [
      "LoadMyApplicationsInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "LoadMyApplicationsDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "LoadMyApplicationsOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMyApplicationsInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "LoadMyApplicationsDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "LoadMyApplicationsOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadMyApplicationsOutputBoundary": {
    "name": "LoadMyApplicationsOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_my_applications",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_my_applications/LoadMyApplicationsOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadMyApplicationsOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadMyApplicationsOutputData": {
    "name": "LoadMyApplicationsOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.load_my_applications",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_my_applications/LoadMyApplicationsOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "applications",
        "type": "List<PostingApplicationData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "LoadMyApplicationsPresenter": {
    "name": "LoadMyApplicationsPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_my_applications",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_my_applications/LoadMyApplicationsPresenter.java",
    "extends": [],
    "implements": [
      "LoadMyApplicationsOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "MyApplicationsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadMyApplicationsPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "MyApplicationsViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadMyApplicationsOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadPostingsController": {
    "name": "LoadPostingsController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_postings/LoadPostingsController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "LoadPostingsInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadPostingsController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "LoadPostingsInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          }
        ]
      },
      {
        "name": "loadPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          }
        ]
      }
    ]
  },
  "LoadPostingsDataAccessInterface": {
    "name": "LoadPostingsDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/LoadPostingsDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "loadPostings",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<Posting>",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          }
        ]
      },
      {
        "name": "loadApplicationsForOwnedPostings",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Map<String, List<PostingApplication>>",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          },
          {
            "name": "postings",
            "type": "List<Posting>"
          }
        ]
      }
    ]
  },
  "LoadPostingsInputBoundary": {
    "name": "LoadPostingsInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_postings/LoadPostingsInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LoadPostingsInputData"
          }
        ]
      }
    ]
  },
  "LoadPostingsInputData": {
    "name": "LoadPostingsInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_postings/LoadPostingsInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "scope",
        "type": "PostingScope",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "LoadPostingsInteractor": {
    "name": "LoadPostingsInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_postings/LoadPostingsInteractor.java",
    "extends": [],
    "implements": [
      "LoadPostingsInputBoundary"
    ],
    "attributes": [
      {
        "name": "dataAccessObject",
        "type": "LoadPostingsDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "LoadPostingsOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadPostingsInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "dataAccessObject",
            "type": "LoadPostingsDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "LoadPostingsOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LoadPostingsInputData"
          }
        ]
      }
    ]
  },
  "LoadPostingsOutputBoundary": {
    "name": "LoadPostingsOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_postings/LoadPostingsOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadPostingsOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadPostingsOutputData": {
    "name": "LoadPostingsOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_postings/LoadPostingsOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "postings",
        "type": "List<PostingData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicationsByPostingId",
        "type": "Map<String, List<PostingApplicationData>>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "LoadPostingsPresenter": {
    "name": "LoadPostingsPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_postings/LoadPostingsPresenter.java",
    "extends": [],
    "implements": [
      "LoadPostingsOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "PostingsListViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadPostingsPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "PostingsListViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadPostingsOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadProfileController": {
    "name": "LoadProfileController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_profile",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_profile/LoadProfileController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "loadProfileInteractor",
        "type": "LoadProfileInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadProfileController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loadProfileInteractor",
            "type": "LoadProfileInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadProfileDataAccessInterface": {
    "name": "LoadProfileDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/LoadProfileDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getProfile",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "User",
        "parameters": []
      }
    ]
  },
  "LoadProfileInputBoundary": {
    "name": "LoadProfileInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_profile/LoadProfileInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadProfileInteractor": {
    "name": "LoadProfileInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_profile/LoadProfileInteractor.java",
    "extends": [],
    "implements": [
      "LoadProfileInputBoundary"
    ],
    "attributes": [
      {
        "name": "profileDataAccessObject",
        "type": "LoadProfileDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "LoadProfileOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadProfileInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "profileDataAccessObject",
            "type": "LoadProfileDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "LoadProfileOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LoadProfileOutputBoundary": {
    "name": "LoadProfileOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.load_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_profile/LoadProfileOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadProfileOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoadProfileOutputData": {
    "name": "LoadProfileOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.load_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_profile/LoadProfileOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "profile",
        "type": "UserData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadProfileOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "profile",
            "type": "UserData"
          }
        ]
      },
      {
        "name": "getUser",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "UserData",
        "parameters": []
      }
    ]
  },
  "LoadProfilePresenter": {
    "name": "LoadProfilePresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.load_profile",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/load_profile/LoadProfilePresenter.java",
    "extends": [],
    "implements": [
      "LoadProfileOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "UpdateProfileViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoadProfilePresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "UpdateProfileViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoadProfileOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LocalAccountSettingsRepository": {
    "name": "LocalAccountSettingsRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/LocalAccountSettingsRepository.java",
    "extends": [],
    "implements": [
      "VerificationEmailSenderDataAccessInterface",
      "ChangeEmailDataAccessInterface",
      "ChangePasswordDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "state",
        "type": "LocalServerState",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "session",
        "type": "CurrentUserProviderInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "emailChallenges",
        "type": "InMemoryEmailVerificationChallengeRepository",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "codeGenerator",
        "type": "VerificationCodeGeneratorInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "codeDelivery",
        "type": "EmailChangeCodeDeliveryDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "academicEmailDomains",
        "type": "AcademicEmailDomainDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "clock",
        "type": "Clock",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LocalAccountSettingsRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "state",
            "type": "LocalServerState"
          },
          {
            "name": "session",
            "type": "CurrentUserProviderInterface"
          },
          {
            "name": "emailChallenges",
            "type": "InMemoryEmailVerificationChallengeRepository"
          },
          {
            "name": "codeGenerator",
            "type": "VerificationCodeGeneratorInterface"
          },
          {
            "name": "codeDelivery",
            "type": "EmailChangeCodeDeliveryDataAccessInterface"
          },
          {
            "name": "academicEmailDomains",
            "type": "AcademicEmailDomainDataAccessInterface"
          },
          {
            "name": "clock",
            "type": "Clock"
          }
        ]
      },
      {
        "name": "requestVerificationCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "changeEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "verificationCode",
            "type": "String"
          }
        ]
      },
      {
        "name": "changePassword",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "currentPassword",
            "type": "String"
          },
          {
            "name": "newPassword",
            "type": "String"
          }
        ]
      },
      {
        "name": "currentUser",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": []
      },
      {
        "name": "normalizeEmail",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LocalAuthRepository": {
    "name": "LocalAuthRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/LocalAuthRepository.java",
    "extends": [],
    "implements": [
      "LoginDataAccessInterface",
      "RegisterDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "state",
        "type": "LocalServerState",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "emailChallenges",
        "type": "InMemoryEmailVerificationChallengeRepository",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "clock",
        "type": "Clock",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LocalAuthRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "state",
            "type": "LocalServerState"
          },
          {
            "name": "emailChallenges",
            "type": "InMemoryEmailVerificationChallengeRepository"
          },
          {
            "name": "clock",
            "type": "Clock"
          }
        ]
      },
      {
        "name": "login",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          }
        ]
      },
      {
        "name": "register",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "data",
            "type": "RegisterAccountData"
          }
        ]
      },
      {
        "name": "toAuthResult",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "user",
            "type": "User"
          }
        ]
      }
    ]
  },
  "LocalMatchingRepository": {
    "name": "LocalMatchingRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/LocalMatchingRepository.java",
    "extends": [],
    "implements": [
      "RecommendDataAccessInterface",
      "ConnectDataAccessInterface",
      "DislikeDataAccessInterface",
      "LoadMatchesDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "state",
        "type": "LocalServerState",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "session",
        "type": "CurrentUserProviderInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LocalMatchingRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "state",
            "type": "LocalServerState"
          },
          {
            "name": "session",
            "type": "CurrentUserProviderInterface"
          }
        ]
      },
      {
        "name": "getRecommendations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<User>",
        "parameters": []
      },
      {
        "name": "getProfile",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": []
      },
      {
        "name": "connect",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "connectedUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "dislike",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "dislikedUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getMatches",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<User>",
        "parameters": []
      }
    ]
  },
  "LocalMessagingRepository": {
    "name": "LocalMessagingRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/LocalMessagingRepository.java",
    "extends": [],
    "implements": [
      "SendMessageDataAccessInterface",
      "LoadMessageDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "state",
        "type": "LocalServerState",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "session",
        "type": "CurrentUserProviderInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LocalMessagingRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "state",
            "type": "LocalServerState"
          },
          {
            "name": "session",
            "type": "CurrentUserProviderInterface"
          }
        ]
      },
      {
        "name": "sendMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Message",
        "parameters": [
          {
            "name": "receiverId",
            "type": "String"
          },
          {
            "name": "content",
            "type": "String"
          }
        ]
      },
      {
        "name": "getConversation",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Message>",
        "parameters": [
          {
            "name": "otherUserId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LocalPostingRepository": {
    "name": "LocalPostingRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/LocalPostingRepository.java",
    "extends": [],
    "implements": [
      "CreatePostingDataAccessInterface",
      "ClosePostingDataAccessInterface",
      "LoadPostingsDataAccessInterface",
      "ApplyToPostingDataAccessInterface",
      "AcceptApplicationDataAccessInterface",
      "DeclineApplicationDataAccessInterface",
      "LoadMyApplicationsDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "state",
        "type": "LocalServerState",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "session",
        "type": "CurrentUserProviderInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LocalPostingRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "state",
            "type": "LocalServerState"
          },
          {
            "name": "session",
            "type": "CurrentUserProviderInterface"
          }
        ]
      },
      {
        "name": "createPosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Posting",
        "parameters": [
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "closePosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Posting",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          }
        ]
      },
      {
        "name": "loadPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Posting>",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          }
        ]
      },
      {
        "name": "loadApplicationsForOwnedPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Map<String, List<PostingApplication>>",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          },
          {
            "name": "postings",
            "type": "List<Posting>"
          }
        ]
      },
      {
        "name": "applyToPosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "acceptApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      },
      {
        "name": "declineApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getMyApplications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<PostingApplication>",
        "parameters": []
      },
      {
        "name": "reviewApplication",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          },
          {
            "name": "newStatus",
            "type": "PostingApplicationStatus"
          }
        ]
      }
    ]
  },
  "LocalProfileRepository": {
    "name": "LocalProfileRepository",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/LocalProfileRepository.java",
    "extends": [],
    "implements": [
      "LoadProfileDataAccessInterface",
      "UpdateProfileDataAccessInterface",
      "DeleteAccountDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "state",
        "type": "LocalServerState",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "session",
        "type": "CurrentUserProviderInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "institutionCatalog",
        "type": "InstitutionCatalogDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LocalProfileRepository",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "state",
            "type": "LocalServerState"
          },
          {
            "name": "session",
            "type": "CurrentUserProviderInterface"
          },
          {
            "name": "institutionCatalog",
            "type": "InstitutionCatalogDataAccessInterface"
          }
        ]
      },
      {
        "name": "getProfile",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": []
      },
      {
        "name": "updateProfile",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "data",
            "type": "UpdateProfileInputData"
          }
        ]
      },
      {
        "name": "deleteAccount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "parseAcademicLevel",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AcademicLevel",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "parseCollaborationType",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "CollaborationType",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "parseResearchField",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ResearchField",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "parseFundingStatus",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "FundingStatus",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "parseInstitution",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Institution",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LocalServerState": {
    "name": "LocalServerState",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/LocalServerState.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "usersById",
        "type": "Map<String, User>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "seedUserIds",
        "type": "Set<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "recordedConnections",
        "type": "Set<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "recordedDislikes",
        "type": "Set<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "messages",
        "type": "List<Message>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "postingsById",
        "type": "Map<String, Posting>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicationsById",
        "type": "Map<String, PostingApplication>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LocalServerState",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "institutionCatalog",
            "type": "InstitutionCatalogDataAccessInterface"
          }
        ]
      },
      {
        "name": "usersById",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Map<String, User>",
        "parameters": []
      },
      {
        "name": "seedUserIds",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Set<String>",
        "parameters": []
      },
      {
        "name": "recordedConnections",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Set<String>",
        "parameters": []
      },
      {
        "name": "recordedDislikes",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Set<String>",
        "parameters": []
      },
      {
        "name": "messages",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Message>",
        "parameters": []
      },
      {
        "name": "postingsById",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Map<String, Posting>",
        "parameters": []
      },
      {
        "name": "applicationsById",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Map<String, PostingApplication>",
        "parameters": []
      },
      {
        "name": "findByEmail",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "displayName",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          }
        ]
      },
      {
        "name": "hasMutualConnection",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "firstUserId",
            "type": "String"
          },
          {
            "name": "secondUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "seedDemoUsers",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "institutionCatalog",
            "type": "InstitutionCatalogDataAccessInterface"
          }
        ]
      },
      {
        "name": "addSeedUser",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "Institution"
          },
          {
            "name": "academicLevel",
            "type": "AcademicLevel"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "lookingFor",
            "type": "CollaborationType"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "FundingStatus"
          }
        ]
      },
      {
        "name": "addSeedUser",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "Institution"
          },
          {
            "name": "academicLevel",
            "type": "AcademicLevel"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "lookingFor",
            "type": "CollaborationType"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "FundingStatus"
          },
          {
            "name": "password",
            "type": "String"
          },
          {
            "name": "emailAccountType",
            "type": "EmailAccountType"
          }
        ]
      }
    ]
  },
  "LocalUserApiGateway": {
    "name": "LocalUserApiGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/paper_lookup/LocalUserApiGateway.java",
    "extends": [],
    "implements": [
      "UserAPIGatewayInterface"
    ],
    "attributes": [
      {
        "name": "PAPERS",
        "type": "List<Publication>",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "PAPERS_BY_AUTHOR_ID",
        "type": "Map<String, List<Publication>>",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "AUTHORS",
        "type": "List<AuthorCandidateDataAccessInterface>",
        "visibility": "−",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "searchAuthors",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<AuthorCandidateDataAccessInterface>",
        "parameters": [
          {
            "name": "name",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AuthorCandidateDataAccessInterface",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthorPapers",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Publication>",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoginController": {
    "name": "LoginController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.login",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/login/LoginController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "LoginInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoginController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "LoginInputBoundary"
          }
        ]
      },
      {
        "name": "login",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoginDataAccessInterface": {
    "name": "LoginDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/LoginDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "login",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoginInputBoundary": {
    "name": "LoginInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.login",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/login/LoginInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LoginInputData"
          }
        ]
      }
    ]
  },
  "LoginInputData": {
    "name": "LoginInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.login",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/login/LoginInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "password",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoginInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          }
        ]
      },
      {
        "name": "getEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPassword",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "LoginInteractor": {
    "name": "LoginInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.login",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/login/LoginInteractor.java",
    "extends": [],
    "implements": [
      "LoginInputBoundary"
    ],
    "attributes": [
      {
        "name": "authDataAccessObject",
        "type": "LoginDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "sessionManager",
        "type": "SessionWriterInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "LoginOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoginInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "authDataAccessObject",
            "type": "LoginDataAccessInterface"
          },
          {
            "name": "sessionManager",
            "type": "SessionWriterInterface"
          },
          {
            "name": "outputBoundary",
            "type": "LoginOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LoginInputData"
          }
        ]
      }
    ]
  },
  "LoginOutputBoundary": {
    "name": "LoginOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.login",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/login/LoginOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoginOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoginOutputData": {
    "name": "LoginOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.login",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/login/LoginOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "userId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "fullName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoginOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          },
          {
            "name": "fullName",
            "type": "String"
          }
        ]
      },
      {
        "name": "getUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getFullName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "LoginPresenter": {
    "name": "LoginPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.login",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/login/LoginPresenter.java",
    "extends": [],
    "implements": [
      "LoginOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "LoginViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoginPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "LoginViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LoginOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoginView": {
    "name": "LoginView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/LoginView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "DIALOG_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FIELD_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "BUTTON_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "EMAIL_PATTERN",
        "type": "Pattern",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "controller",
        "type": "LoginController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "viewModel",
        "type": "LoginViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LoginView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "controller",
            "type": "LoginController"
          },
          {
            "name": "viewModel",
            "type": "LoginViewModel"
          }
        ]
      },
      {
        "name": "validate",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LoginViewModel": {
    "name": "LoginViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.login",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/login/LoginViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loggedInUserId",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "loggedInUserIdProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setLoggedInUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "LogoutController": {
    "name": "LogoutController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.logout",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/logout/LogoutController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "LogoutInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LogoutController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "LogoutInputBoundary"
          }
        ]
      },
      {
        "name": "logout",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "LogoutInputBoundary": {
    "name": "LogoutInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.logout",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/logout/LogoutInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LogoutInputData"
          }
        ]
      }
    ]
  },
  "LogoutInputData": {
    "name": "LogoutInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.logout",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/logout/LogoutInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "LogoutInteractor": {
    "name": "LogoutInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.logout",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/logout/LogoutInteractor.java",
    "extends": [],
    "implements": [
      "LogoutInputBoundary"
    ],
    "attributes": [
      {
        "name": "sessionManager",
        "type": "SessionClearerInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "LogoutOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LogoutInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "sessionManager",
            "type": "SessionClearerInterface"
          },
          {
            "name": "outputBoundary",
            "type": "LogoutOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "LogoutInputData"
          }
        ]
      }
    ]
  },
  "LogoutOutputBoundary": {
    "name": "LogoutOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.logout",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/logout/LogoutOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LogoutOutputData"
          }
        ]
      }
    ]
  },
  "LogoutOutputData": {
    "name": "LogoutOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.logout",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/logout/LogoutOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "LogoutPresenter": {
    "name": "LogoutPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.logout",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/logout/LogoutPresenter.java",
    "extends": [],
    "implements": [
      "LogoutOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "LogoutViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "LogoutPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "LogoutViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "LogoutOutputData"
          }
        ]
      }
    ]
  },
  "LogoutViewModel": {
    "name": "LogoutViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.logout",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/logout/LogoutViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "loggedOut",
        "type": "ObservableValue<Boolean>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "loggedOutProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<Boolean>",
        "parameters": []
      },
      {
        "name": "setLoggedOut",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "MainLayoutView": {
    "name": "MainLayoutView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/MainLayoutView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "listenerRemovers",
        "type": "List<Runnable>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MainLayoutView",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "recommendController",
            "type": "RecommendController"
          },
          {
            "name": "connectController",
            "type": "ConnectController"
          },
          {
            "name": "dislikeController",
            "type": "DislikeController"
          },
          {
            "name": "skipController",
            "type": "SkipController"
          },
          {
            "name": "recommendViewModel",
            "type": "RecommendViewModel"
          },
          {
            "name": "loadMatchesViewModel",
            "type": "LoadMatchesViewModel"
          },
          {
            "name": "loadMatchesController",
            "type": "LoadMatchesController"
          },
          {
            "name": "sendMessageController",
            "type": "SendMessageController"
          },
          {
            "name": "loadMessageController",
            "type": "LoadMessageController"
          },
          {
            "name": "chatViewModel",
            "type": "ChatViewModel"
          },
          {
            "name": "updateProfileController",
            "type": "UpdateProfileController"
          },
          {
            "name": "loadProfileController",
            "type": "LoadProfileController"
          },
          {
            "name": "updateProfileViewModel",
            "type": "UpdateProfileViewModel"
          },
          {
            "name": "requestEmailChangeController",
            "type": "RequestEmailVerificationController"
          },
          {
            "name": "changeEmailController",
            "type": "ChangeEmailController"
          },
          {
            "name": "changePasswordController",
            "type": "ChangePasswordController"
          },
          {
            "name": "accountSettingsViewModel",
            "type": "AccountSettingsViewModel"
          },
          {
            "name": "paperLookupController",
            "type": "PaperLookupController"
          },
          {
            "name": "paperLookupViewModel",
            "type": "PaperLookupViewModel"
          },
          {
            "name": "logoutController",
            "type": "LogoutController"
          },
          {
            "name": "logoutViewModel",
            "type": "LogoutViewModel"
          },
          {
            "name": "deleteAccountController",
            "type": "DeleteAccountController"
          },
          {
            "name": "deleteAccountViewModel",
            "type": "DeleteAccountViewModel"
          },
          {
            "name": "createPostingController",
            "type": "CreatePostingController"
          },
          {
            "name": "closePostingController",
            "type": "ClosePostingController"
          },
          {
            "name": "opportunitiesLoadPostingsController",
            "type": "LoadPostingsController"
          },
          {
            "name": "myPostingsLoadPostingsController",
            "type": "LoadPostingsController"
          },
          {
            "name": "applyToPostingController",
            "type": "ApplyToPostingController"
          },
          {
            "name": "acceptApplicationController",
            "type": "AcceptApplicationController"
          },
          {
            "name": "declineApplicationController",
            "type": "DeclineApplicationController"
          },
          {
            "name": "loadMyApplicationsController",
            "type": "LoadMyApplicationsController"
          },
          {
            "name": "opportunitiesViewModel",
            "type": "OpportunitiesViewModel"
          },
          {
            "name": "myPostingsViewModel",
            "type": "MyPostingsViewModel"
          },
          {
            "name": "myApplicationsViewModel",
            "type": "MyApplicationsViewModel"
          },
          {
            "name": "currentUserProvider",
            "type": "CurrentUserProvider"
          },
          {
            "name": "onLoggedOut",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "buildTopBar",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "updateProfileViewModel",
            "type": "UpdateProfileViewModel"
          }
        ]
      },
      {
        "name": "listen",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "<T>void",
        "parameters": [
          {
            "name": "observable",
            "type": "ObservableValue<T>"
          },
          {
            "name": "listener",
            "type": "Consumer<T>"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "MainView": {
    "name": "MainView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/MainView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "loginController",
        "type": "LoginController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loginViewModel",
        "type": "LoginViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "logoutController",
        "type": "LogoutController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "logoutViewModel",
        "type": "LogoutViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "deleteAccountController",
        "type": "DeleteAccountController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "deleteAccountViewModel",
        "type": "DeleteAccountViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "registerController",
        "type": "RegisterController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationController",
        "type": "RequestEmailVerificationController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "registerViewModel",
        "type": "RegisterViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "paperLookupController",
        "type": "PaperLookupController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "paperLookupViewModel",
        "type": "PaperLookupViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "recommendController",
        "type": "RecommendController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "connectController",
        "type": "ConnectController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "dislikeController",
        "type": "DislikeController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "skipController",
        "type": "SkipController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "recommendViewModel",
        "type": "RecommendViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadMatchesViewModel",
        "type": "LoadMatchesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadMatchesController",
        "type": "LoadMatchesController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "sendMessageController",
        "type": "SendMessageController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadMessageController",
        "type": "LoadMessageController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "chatViewModel",
        "type": "ChatViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "updateProfileController",
        "type": "UpdateProfileController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadProfileController",
        "type": "LoadProfileController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "updateProfileViewModel",
        "type": "UpdateProfileViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "requestEmailChangeController",
        "type": "RequestEmailVerificationController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "changeEmailController",
        "type": "ChangeEmailController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "changePasswordController",
        "type": "ChangePasswordController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "accountSettingsViewModel",
        "type": "AccountSettingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "currentUserProvider",
        "type": "CurrentUserProvider",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "createPostingController",
        "type": "CreatePostingController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "closePostingController",
        "type": "ClosePostingController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "opportunitiesLoadPostingsController",
        "type": "LoadPostingsController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "myPostingsLoadPostingsController",
        "type": "LoadPostingsController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applyToPostingController",
        "type": "ApplyToPostingController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "acceptApplicationController",
        "type": "AcceptApplicationController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "declineApplicationController",
        "type": "DeclineApplicationController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadMyApplicationsController",
        "type": "LoadMyApplicationsController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "opportunitiesViewModel",
        "type": "OpportunitiesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "myPostingsViewModel",
        "type": "MyPostingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "myApplicationsViewModel",
        "type": "MyApplicationsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MainView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loginController",
            "type": "LoginController"
          },
          {
            "name": "loginViewModel",
            "type": "LoginViewModel"
          },
          {
            "name": "logoutController",
            "type": "LogoutController"
          },
          {
            "name": "logoutViewModel",
            "type": "LogoutViewModel"
          },
          {
            "name": "deleteAccountController",
            "type": "DeleteAccountController"
          },
          {
            "name": "deleteAccountViewModel",
            "type": "DeleteAccountViewModel"
          },
          {
            "name": "registerController",
            "type": "RegisterController"
          },
          {
            "name": "verificationController",
            "type": "RequestEmailVerificationController"
          },
          {
            "name": "registerViewModel",
            "type": "RegisterViewModel"
          },
          {
            "name": "paperLookupController",
            "type": "PaperLookupController"
          },
          {
            "name": "paperLookupViewModel",
            "type": "PaperLookupViewModel"
          },
          {
            "name": "recommendController",
            "type": "RecommendController"
          },
          {
            "name": "connectController",
            "type": "ConnectController"
          },
          {
            "name": "dislikeController",
            "type": "DislikeController"
          },
          {
            "name": "skipController",
            "type": "SkipController"
          },
          {
            "name": "recommendViewModel",
            "type": "RecommendViewModel"
          },
          {
            "name": "loadMatchesViewModel",
            "type": "LoadMatchesViewModel"
          },
          {
            "name": "loadMatchesController",
            "type": "LoadMatchesController"
          },
          {
            "name": "sendMessageController",
            "type": "SendMessageController"
          },
          {
            "name": "loadMessageController",
            "type": "LoadMessageController"
          },
          {
            "name": "chatViewModel",
            "type": "ChatViewModel"
          },
          {
            "name": "updateProfileController",
            "type": "UpdateProfileController"
          },
          {
            "name": "loadProfileController",
            "type": "LoadProfileController"
          },
          {
            "name": "updateProfileViewModel",
            "type": "UpdateProfileViewModel"
          },
          {
            "name": "requestEmailChangeController",
            "type": "RequestEmailVerificationController"
          },
          {
            "name": "changeEmailController",
            "type": "ChangeEmailController"
          },
          {
            "name": "changePasswordController",
            "type": "ChangePasswordController"
          },
          {
            "name": "accountSettingsViewModel",
            "type": "AccountSettingsViewModel"
          },
          {
            "name": "createPostingController",
            "type": "CreatePostingController"
          },
          {
            "name": "closePostingController",
            "type": "ClosePostingController"
          },
          {
            "name": "opportunitiesLoadPostingsController",
            "type": "LoadPostingsController"
          },
          {
            "name": "myPostingsLoadPostingsController",
            "type": "LoadPostingsController"
          },
          {
            "name": "applyToPostingController",
            "type": "ApplyToPostingController"
          },
          {
            "name": "acceptApplicationController",
            "type": "AcceptApplicationController"
          },
          {
            "name": "declineApplicationController",
            "type": "DeclineApplicationController"
          },
          {
            "name": "loadMyApplicationsController",
            "type": "LoadMyApplicationsController"
          },
          {
            "name": "opportunitiesViewModel",
            "type": "OpportunitiesViewModel"
          },
          {
            "name": "myPostingsViewModel",
            "type": "MyPostingsViewModel"
          },
          {
            "name": "myApplicationsViewModel",
            "type": "MyApplicationsViewModel"
          },
          {
            "name": "currentUserProvider",
            "type": "CurrentUserProvider"
          }
        ]
      },
      {
        "name": "showAuthShell",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "showMainLayout",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "MatchedUserCard": {
    "name": "MatchedUserCard",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/MatchedUserCard.java",
    "extends": [
      "RoundedPanel"
    ],
    "implements": [
      "Reflowable"
    ],
    "attributes": [
      {
        "name": "MAX_CARD_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_CARD_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "STACK_BREAKPOINT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "AVATAR_SIZE",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "NOT_SPECIFIED",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "cardUser",
        "type": "UserData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MatchedUserCard",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          }
        ]
      },
      {
        "name": "reflow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildHeader",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          },
          {
            "name": "cardWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildColumns",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          },
          {
            "name": "cardWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildEducationColumn",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "educations",
            "type": "List<Education>"
          },
          {
            "name": "columnWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildPublicationsColumn",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "publications",
            "type": "List<Publication>"
          },
          {
            "name": "columnWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildResearchColumn",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          },
          {
            "name": "columnWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "column",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "heading",
            "type": "String"
          },
          {
            "name": "glyph",
            "type": "Ikon"
          }
        ]
      },
      {
        "name": "columnHeading",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          },
          {
            "name": "glyph",
            "type": "Ikon"
          }
        ]
      },
      {
        "name": "mutedLine",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "capHeight",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "label",
            "type": "JLabel"
          }
        ]
      },
      {
        "name": "wrappedLabel",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          },
          {
            "name": "columnWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "formatEducationRange",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "ed",
            "type": "Education"
          }
        ]
      },
      {
        "name": "formatYearMonth",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "year",
            "type": "int"
          },
          {
            "name": "month",
            "type": "Month"
          }
        ]
      },
      {
        "name": "initial",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "name",
            "type": "String"
          }
        ]
      },
      {
        "name": "displayOr",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "joinNonBlank",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "separator",
            "type": "String"
          },
          {
            "name": "parts",
            "type": "String[]"
          }
        ]
      },
      {
        "name": "formatEnum",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "Enum<?>"
          }
        ]
      },
      {
        "name": "escapeHtml",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "strut",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Component",
        "parameters": [
          {
            "name": "height",
            "type": "int"
          }
        ]
      }
    ]
  },
  "MatchingGateway": {
    "name": "MatchingGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/MatchingGateway.java",
    "extends": [],
    "implements": [
      "RecommendDataAccessInterface",
      "ConnectDataAccessInterface",
      "DislikeDataAccessInterface",
      "LoadMatchesDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "http",
        "type": "ServerHttpClient",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "userMapper",
        "type": "ScholarUserMapper",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MatchingGateway",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "http",
            "type": "ServerHttpClient"
          },
          {
            "name": "institutionCatalog",
            "type": "InstitutionCatalogDataAccessInterface"
          }
        ]
      },
      {
        "name": "getRecommendations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<User>",
        "parameters": []
      },
      {
        "name": "getProfile",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": []
      },
      {
        "name": "connect",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "connectedUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "dislike",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "dislikedUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getMatches",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<User>",
        "parameters": []
      }
    ]
  },
  "MatchToastOverlay": {
    "name": "MatchToastOverlay",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/MatchToastOverlay.java",
    "extends": [
      "JLayeredPane"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "MARGIN",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "VISIBLE_MS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "SLIDE_STEP_MS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "SLIDE_STEP_PX",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "ARC",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "activeToast",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "activeTimer",
        "type": "Timer",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "content",
        "type": "Component",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "MatchToastOverlay",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      },
      {
        "name": "setContent",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "content",
            "type": "Component"
          }
        ]
      },
      {
        "name": "layoutChildren",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "showToast",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "slideIn",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "toast",
            "type": "JPanel"
          },
          {
            "name": "targetX",
            "type": "int"
          }
        ]
      },
      {
        "name": "scheduleHold",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "toast",
            "type": "JPanel"
          },
          {
            "name": "targetX",
            "type": "int"
          }
        ]
      },
      {
        "name": "slideOut",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "toast",
            "type": "JPanel"
          },
          {
            "name": "targetX",
            "type": "int"
          }
        ]
      },
      {
        "name": "repositionActiveToast",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "buildToastPanel",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      }
    ]
  },
  "Message": {
    "name": "Message",
    "kind": "class",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/Message.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "messageId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "senderId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "receiverId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "content",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "sentAt",
        "type": "LocalDateTime",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "Message",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "messageId",
            "type": "String"
          },
          {
            "name": "senderId",
            "type": "String"
          },
          {
            "name": "receiverId",
            "type": "String"
          },
          {
            "name": "content",
            "type": "String"
          },
          {
            "name": "sentAt",
            "type": "LocalDateTime"
          }
        ]
      },
      {
        "name": "getMessageId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getSenderId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getReceiverId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getContent",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getSentAt",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "LocalDateTime",
        "parameters": []
      }
    ]
  },
  "MessageData": {
    "name": "MessageData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.dto",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dto/MessageData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "messageId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "senderId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "receiverId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "content",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "sentAt",
        "type": "LocalDateTime",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MessageData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "messageId",
            "type": "String"
          },
          {
            "name": "senderId",
            "type": "String"
          },
          {
            "name": "receiverId",
            "type": "String"
          },
          {
            "name": "content",
            "type": "String"
          },
          {
            "name": "sentAt",
            "type": "LocalDateTime"
          }
        ]
      },
      {
        "name": "from",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "MessageData",
        "parameters": [
          {
            "name": "message",
            "type": "Message"
          }
        ]
      },
      {
        "name": "fromAll",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "List<MessageData>",
        "parameters": [
          {
            "name": "messages",
            "type": "List<Message>"
          }
        ]
      },
      {
        "name": "getMessageId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getSenderId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getReceiverId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getContent",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getSentAt",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "LocalDateTime",
        "parameters": []
      }
    ]
  },
  "MessagingGateway": {
    "name": "MessagingGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/MessagingGateway.java",
    "extends": [],
    "implements": [
      "SendMessageDataAccessInterface",
      "LoadMessageDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "http",
        "type": "ServerHttpClient",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MessagingGateway",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "http",
            "type": "ServerHttpClient"
          }
        ]
      },
      {
        "name": "sendMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Message",
        "parameters": [
          {
            "name": "receiverId",
            "type": "String"
          },
          {
            "name": "content",
            "type": "String"
          }
        ]
      },
      {
        "name": "getConversation",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Message>",
        "parameters": [
          {
            "name": "otherUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "messageFromJson",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Message",
        "parameters": [
          {
            "name": "node",
            "type": "JsonNode"
          }
        ]
      }
    ]
  },
  "MyApplicationsView": {
    "name": "MyApplicationsView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/MyApplicationsView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "viewModel",
        "type": "MyApplicationsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "rows",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "holder",
        "type": "CenteringScrollPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicationsListener",
        "type": "Runnable",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MyApplicationsView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loadController",
            "type": "LoadMyApplicationsController"
          },
          {
            "name": "viewModel",
            "type": "MyApplicationsViewModel"
          }
        ]
      },
      {
        "name": "rebuild",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "MyApplicationsViewModel": {
    "name": "MyApplicationsViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.my_applications",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/my_applications/MyApplicationsViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "applications",
        "type": "ObservableListModel<PostingApplicationData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "getApplications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<PostingApplicationData>",
        "parameters": []
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setApplications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "newApplications",
            "type": "java.util.List<PostingApplicationData>"
          }
        ]
      }
    ]
  },
  "MyPostingsView": {
    "name": "MyPostingsView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/MyPostingsView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "createController",
        "type": "CreatePostingController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "loadController",
        "type": "LoadPostingsController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "closeController",
        "type": "ClosePostingController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "acceptController",
        "type": "AcceptApplicationController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "declineController",
        "type": "DeclineApplicationController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "viewModel",
        "type": "MyPostingsViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "cardList",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "holder",
        "type": "CenteringScrollPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "postingsListener",
        "type": "Runnable",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "successListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "refreshListener",
        "type": "Consumer<Integer>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "createDialogLauncher",
        "type": "CreateDialogLauncher",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "closeDialogLauncher",
        "type": "CloseDialogLauncher",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "MyPostingsView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "createController",
            "type": "CreatePostingController"
          },
          {
            "name": "loadController",
            "type": "LoadPostingsController"
          },
          {
            "name": "closeController",
            "type": "ClosePostingController"
          },
          {
            "name": "acceptController",
            "type": "AcceptApplicationController"
          },
          {
            "name": "declineController",
            "type": "DeclineApplicationController"
          },
          {
            "name": "viewModel",
            "type": "MyPostingsViewModel"
          }
        ]
      },
      {
        "name": "MyPostingsView",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "createController",
            "type": "CreatePostingController"
          },
          {
            "name": "loadController",
            "type": "LoadPostingsController"
          },
          {
            "name": "closeController",
            "type": "ClosePostingController"
          },
          {
            "name": "acceptController",
            "type": "AcceptApplicationController"
          },
          {
            "name": "declineController",
            "type": "DeclineApplicationController"
          },
          {
            "name": "viewModel",
            "type": "MyPostingsViewModel"
          },
          {
            "name": "createDialogLauncher",
            "type": "CreateDialogLauncher"
          },
          {
            "name": "closeDialogLauncher",
            "type": "CloseDialogLauncher"
          }
        ]
      },
      {
        "name": "rebuild",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "showCreateForm",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "closePosting",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          }
        ]
      },
      {
        "name": "show",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "error",
            "type": "boolean"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "MyPostingsViewModel": {
    "name": "MyPostingsViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.my_postings",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/my_postings/MyPostingsViewModel.java",
    "extends": [],
    "implements": [
      "PostingsListViewModel"
    ],
    "attributes": [
      {
        "name": "postings",
        "type": "ObservableListModel<PostingData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "successMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "refreshRequest",
        "type": "ObservableValue<Integer>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicationsByPostingId",
        "type": "Map<String, List<PostingApplicationData>>",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "getPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<PostingData>",
        "parameters": []
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "successMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "refreshRequestProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<Integer>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setSuccessMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "requestRefresh",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "setPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "newPostings",
            "type": "List<PostingData>"
          }
        ]
      },
      {
        "name": "setApplicationsByPostingId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "newApplications",
            "type": "Map<String, List<PostingApplicationData>>"
          }
        ]
      },
      {
        "name": "getApplicationsFor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<PostingApplicationData>",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          }
        ]
      },
      {
        "name": "addPosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          }
        ]
      },
      {
        "name": "replacePosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          }
        ]
      },
      {
        "name": "updateApplicationStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "updatedApplication",
            "type": "PostingApplicationData"
          }
        ]
      }
    ]
  },
  "NavigationBar": {
    "name": "NavigationBar",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/NavigationBar.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "SIDEBAR_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "NAV_ICON_SIZE",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "TRAILING_ICON_SIZE",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "LOGO_ICON_SIZE",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "TOGGLE_STYLE",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "NavigationBar",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "listener",
            "type": "NavSelectionListener"
          },
          {
            "name": "onLogout",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "navToggle",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JToggleButton",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          },
          {
            "name": "glyph",
            "type": "Ikon"
          }
        ]
      },
      {
        "name": "styleSidebarButton",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "button",
            "type": "javax.swing.AbstractButton"
          }
        ]
      }
    ]
  },
  "ObservableListModel": {
    "name": "ObservableListModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.support",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/support/ObservableListModel.java",
    "extends": [
      "AbstractList<T>"
    ],
    "implements": [
      "RandomAccess"
    ],
    "attributes": [
      {
        "name": "items",
        "type": "List<T>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "listeners",
        "type": "List<Runnable>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "addListener",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "listener",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "removeListener",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "listener",
            "type": "Runnable"
          }
        ]
      },
      {
        "name": "setAll",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "newItems",
            "type": "Collection<? extends T>"
          }
        ]
      },
      {
        "name": "get",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "T",
        "parameters": [
          {
            "name": "index",
            "type": "int"
          }
        ]
      },
      {
        "name": "size",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "set",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "T",
        "parameters": [
          {
            "name": "index",
            "type": "int"
          },
          {
            "name": "element",
            "type": "T"
          }
        ]
      },
      {
        "name": "add",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "index",
            "type": "int"
          },
          {
            "name": "element",
            "type": "T"
          }
        ]
      },
      {
        "name": "remove",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "T",
        "parameters": [
          {
            "name": "index",
            "type": "int"
          }
        ]
      },
      {
        "name": "addAll",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "newItems",
            "type": "Collection<? extends T>"
          }
        ]
      },
      {
        "name": "clear",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "notifyListeners",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "ObservableValue": {
    "name": "ObservableValue",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.support",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/support/ObservableValue.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "value",
        "type": "T",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "listeners",
        "type": "List<Consumer<T>>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ObservableValue",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "initial",
            "type": "T"
          }
        ]
      },
      {
        "name": "get",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "T",
        "parameters": []
      },
      {
        "name": "set",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "newValue",
            "type": "T"
          }
        ]
      },
      {
        "name": "addListener",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "listener",
            "type": "Consumer<T>"
          }
        ]
      },
      {
        "name": "removeListener",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "listener",
            "type": "Consumer<T>"
          }
        ]
      }
    ]
  },
  "OpportunitiesView": {
    "name": "OpportunitiesView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/OpportunitiesView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "viewModel",
        "type": "OpportunitiesViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applyController",
        "type": "ApplyToPostingController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "cardList",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "holder",
        "type": "CenteringScrollPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "postingsListener",
        "type": "Runnable",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "successListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "OpportunitiesView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "loadController",
            "type": "LoadPostingsController"
          },
          {
            "name": "applyController",
            "type": "ApplyToPostingController"
          },
          {
            "name": "viewModel",
            "type": "OpportunitiesViewModel"
          }
        ]
      },
      {
        "name": "rebuild",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "show",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "error",
            "type": "boolean"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "OpportunitiesViewModel": {
    "name": "OpportunitiesViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.opportunities",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/opportunities/OpportunitiesViewModel.java",
    "extends": [],
    "implements": [
      "PostingsListViewModel"
    ],
    "attributes": [
      {
        "name": "postings",
        "type": "ObservableListModel<PostingData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "successMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "refreshRequest",
        "type": "ObservableValue<Integer>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "getPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<PostingData>",
        "parameters": []
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "successMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "refreshRequestProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<Integer>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setSuccessMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "requestRefresh",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "setPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "newPostings",
            "type": "List<PostingData>"
          }
        ]
      },
      {
        "name": "setApplicationsByPostingId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "applicationsByPostingId",
            "type": "Map<String, List<PostingApplicationData>>"
          }
        ]
      },
      {
        "name": "removePosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "OwnedPostingCard": {
    "name": "OwnedPostingCard",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/OwnedPostingCard.java",
    "extends": [
      "RoundedPanel"
    ],
    "implements": [
      "Reflowable"
    ],
    "attributes": [
      {
        "name": "MAX_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DATE_FORMAT",
        "type": "DateTimeFormatter",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "applicationRows",
        "type": "List<PostingApplicationRow>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "OwnedPostingCard",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          },
          {
            "name": "applications",
            "type": "List<PostingApplicationData>"
          },
          {
            "name": "onClose",
            "type": "Consumer<PostingData>"
          },
          {
            "name": "acceptController",
            "type": "AcceptApplicationController"
          },
          {
            "name": "declineController",
            "type": "DeclineApplicationController"
          }
        ]
      },
      {
        "name": "reflow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "format",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PaperLookupController": {
    "name": "PaperLookupController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/paper_lookup/PaperLookupController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "paperLookupInteractor",
        "type": "PaperLookupInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PaperLookupController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "paperLookupInteractor",
            "type": "PaperLookupInputBoundary"
          }
        ]
      },
      {
        "name": "searchAuthors",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "name",
            "type": "String"
          }
        ]
      },
      {
        "name": "selectAuthor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PaperLookupInputBoundary": {
    "name": "PaperLookupInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/paper_lookup/PaperLookupInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "searchAuthors",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SearchAuthorsInputData"
          }
        ]
      },
      {
        "name": "selectAuthor",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SelectAuthorInputData"
          }
        ]
      }
    ]
  },
  "PaperLookupInteractor": {
    "name": "PaperLookupInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/paper_lookup/PaperLookupInteractor.java",
    "extends": [],
    "implements": [
      "PaperLookupInputBoundary"
    ],
    "attributes": [
      {
        "name": "EMPTY_QUERY_MESSAGE",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "UNKNOWN_AUTHOR_MESSAGE",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_AUTHOR_CANDIDATES",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "userApiGateway",
        "type": "UserAPIGatewayInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "PaperLookupOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "candidatesById",
        "type": "Map<String, AuthorCandidateData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PaperLookupInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userApiGateway",
            "type": "UserAPIGatewayInterface"
          },
          {
            "name": "outputBoundary",
            "type": "PaperLookupOutputBoundary"
          }
        ]
      },
      {
        "name": "searchAuthors",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SearchAuthorsInputData"
          }
        ]
      },
      {
        "name": "selectAuthor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SelectAuthorInputData"
          }
        ]
      },
      {
        "name": "rankCandidates",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "List<AuthorCandidateData>",
        "parameters": [
          {
            "name": "candidates",
            "type": "List<AuthorCandidateData>"
          }
        ]
      },
      {
        "name": "citationCount",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": [
          {
            "name": "candidate",
            "type": "AuthorCandidateData"
          }
        ]
      },
      {
        "name": "normalizeName",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "name",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PaperLookupOutputBoundary": {
    "name": "PaperLookupOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/paper_lookup/PaperLookupOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareAuthorCandidates",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "candidates",
            "type": "List<AuthorCandidateData>"
          }
        ]
      },
      {
        "name": "prepareAuthorPapersFound",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "papers",
            "type": "List<Publication>"
          }
        ]
      },
      {
        "name": "prepareError",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PaperLookupPresenter": {
    "name": "PaperLookupPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/paper_lookup/PaperLookupPresenter.java",
    "extends": [],
    "implements": [
      "PaperLookupOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "PaperLookupViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PaperLookupPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "PaperLookupViewModel"
          }
        ]
      },
      {
        "name": "prepareAuthorCandidates",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "candidates",
            "type": "List<AuthorCandidateData>"
          }
        ]
      },
      {
        "name": "prepareAuthorPapersFound",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "papers",
            "type": "List<Publication>"
          }
        ]
      },
      {
        "name": "prepareError",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PaperLookupViewModel": {
    "name": "PaperLookupViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/paper_lookup/PaperLookupViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "authorCandidates",
        "type": "ObservableListModel<AuthorCandidateData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "authorPapersFound",
        "type": "ObservableListModel<Publication>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "statusMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "getAuthorCandidates",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<AuthorCandidateData>",
        "parameters": []
      },
      {
        "name": "getAuthorPapersFound",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<Publication>",
        "parameters": []
      },
      {
        "name": "statusMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setStatusMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "Posting": {
    "name": "Posting",
    "kind": "class",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/Posting.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "postingId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterAcademicEmailVerified",
        "type": "boolean",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "title",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "description",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchField",
        "type": "ResearchField",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "collaborationType",
        "type": "CollaborationType",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "capacity",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "createdAt",
        "type": "LocalDateTime",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicantCount",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "acceptedCount",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "status",
        "type": "PostingStatus",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "Posting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "posterUserId",
            "type": "String"
          },
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          },
          {
            "name": "applicantCount",
            "type": "int"
          },
          {
            "name": "acceptedCount",
            "type": "int"
          },
          {
            "name": "status",
            "type": "PostingStatus"
          },
          {
            "name": "createdAt",
            "type": "LocalDateTime"
          }
        ]
      },
      {
        "name": "Posting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "posterUserId",
            "type": "String"
          },
          {
            "name": "posterName",
            "type": "String"
          },
          {
            "name": "posterAcademicEmailVerified",
            "type": "boolean"
          },
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          },
          {
            "name": "applicantCount",
            "type": "int"
          },
          {
            "name": "acceptedCount",
            "type": "int"
          },
          {
            "name": "status",
            "type": "PostingStatus"
          },
          {
            "name": "createdAt",
            "type": "LocalDateTime"
          }
        ]
      },
      {
        "name": "isFull",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "isActive",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "recordApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "setApplicantCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "applicantCount",
            "type": "int"
          }
        ]
      },
      {
        "name": "recordAcceptedApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "close",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "getPostingId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "isPosterAcademicEmailVerified",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "getTitle",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getResearchField",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ResearchField",
        "parameters": []
      },
      {
        "name": "getCollaborationType",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "CollaborationType",
        "parameters": []
      },
      {
        "name": "getCapacity",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getApplicantCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "getAcceptedCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "getStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingStatus",
        "parameters": []
      },
      {
        "name": "getCreatedAt",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "LocalDateTime",
        "parameters": []
      }
    ]
  },
  "PostingApplication": {
    "name": "PostingApplication",
    "kind": "class",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/PostingApplication.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "applicationId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "postingId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicantUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "message",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "appliedAt",
        "type": "LocalDateTime",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "postingTitle",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicantName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterAcademicEmailVerified",
        "type": "boolean",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "status",
        "type": "PostingApplicationStatus",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "PostingApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          },
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "applicantUserId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "status",
            "type": "PostingApplicationStatus"
          },
          {
            "name": "appliedAt",
            "type": "LocalDateTime"
          }
        ]
      },
      {
        "name": "PostingApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          },
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "applicantUserId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "status",
            "type": "PostingApplicationStatus"
          },
          {
            "name": "appliedAt",
            "type": "LocalDateTime"
          },
          {
            "name": "postingTitle",
            "type": "String"
          },
          {
            "name": "applicantName",
            "type": "String"
          }
        ]
      },
      {
        "name": "PostingApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          },
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "applicantUserId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "status",
            "type": "PostingApplicationStatus"
          },
          {
            "name": "appliedAt",
            "type": "LocalDateTime"
          },
          {
            "name": "postingTitle",
            "type": "String"
          },
          {
            "name": "applicantName",
            "type": "String"
          },
          {
            "name": "posterUserId",
            "type": "String"
          },
          {
            "name": "posterName",
            "type": "String"
          },
          {
            "name": "posterAcademicEmailVerified",
            "type": "boolean"
          }
        ]
      },
      {
        "name": "accept",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "reject",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "setStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "status",
            "type": "PostingApplicationStatus"
          }
        ]
      },
      {
        "name": "requirePending",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "getApplicationId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPostingId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getApplicantUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplicationStatus",
        "parameters": []
      },
      {
        "name": "getAppliedAt",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "LocalDateTime",
        "parameters": []
      },
      {
        "name": "getPostingTitle",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getApplicantName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "isPosterAcademicEmailVerified",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      }
    ]
  },
  "PostingApplicationData": {
    "name": "PostingApplicationData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.dto",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dto/PostingApplicationData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "applicationId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "postingId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicantUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "message",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "status",
        "type": "PostingApplicationStatus",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "appliedAt",
        "type": "LocalDateTime",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "postingTitle",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicantName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterAcademicEmailVerified",
        "type": "boolean",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PostingApplicationData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          },
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "applicantUserId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "status",
            "type": "PostingApplicationStatus"
          },
          {
            "name": "appliedAt",
            "type": "LocalDateTime"
          }
        ]
      },
      {
        "name": "PostingApplicationData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          },
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "applicantUserId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "status",
            "type": "PostingApplicationStatus"
          },
          {
            "name": "appliedAt",
            "type": "LocalDateTime"
          },
          {
            "name": "postingTitle",
            "type": "String"
          },
          {
            "name": "applicantName",
            "type": "String"
          }
        ]
      },
      {
        "name": "PostingApplicationData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          },
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "applicantUserId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "status",
            "type": "PostingApplicationStatus"
          },
          {
            "name": "appliedAt",
            "type": "LocalDateTime"
          },
          {
            "name": "postingTitle",
            "type": "String"
          },
          {
            "name": "applicantName",
            "type": "String"
          },
          {
            "name": "posterName",
            "type": "String"
          },
          {
            "name": "posterAcademicEmailVerified",
            "type": "boolean"
          }
        ]
      },
      {
        "name": "from",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplicationData",
        "parameters": [
          {
            "name": "application",
            "type": "PostingApplication"
          }
        ]
      },
      {
        "name": "fromAll",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "List<PostingApplicationData>",
        "parameters": [
          {
            "name": "applications",
            "type": "List<PostingApplication>"
          }
        ]
      },
      {
        "name": "getApplicationId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPostingId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getApplicantUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplicationStatus",
        "parameters": []
      },
      {
        "name": "getAppliedAt",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "LocalDateTime",
        "parameters": []
      },
      {
        "name": "getPostingTitle",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getApplicantName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterDisplayName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "isPosterAcademicEmailVerified",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      }
    ]
  },
  "PostingApplicationRow": {
    "name": "PostingApplicationRow",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/PostingApplicationRow.java",
    "extends": [
      "JPanel"
    ],
    "implements": [
      "Reflowable"
    ],
    "attributes": [
      {
        "name": "STACK_BREAKPOINT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DATE_FORMAT",
        "type": "DateTimeFormatter",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "content",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "actions",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PostingApplicationRow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "application",
            "type": "PostingApplicationData"
          },
          {
            "name": "acceptController",
            "type": "AcceptApplicationController"
          },
          {
            "name": "declineController",
            "type": "DeclineApplicationController"
          }
        ]
      },
      {
        "name": "reflow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "format",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PostingApplicationStatus": {
    "name": "PostingApplicationStatus",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/PostingApplicationStatus.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "PostingCard": {
    "name": "PostingCard",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/PostingCard.java",
    "extends": [
      "RoundedPanel"
    ],
    "implements": [
      "Reflowable"
    ],
    "attributes": [
      {
        "name": "MAX_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DATE_FORMAT",
        "type": "DateTimeFormatter",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "header",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PostingCard",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          },
          {
            "name": "onApply",
            "type": "BiConsumer<String, String>"
          }
        ]
      },
      {
        "name": "PostingCard",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          },
          {
            "name": "onApply",
            "type": "BiConsumer<String, String>"
          },
          {
            "name": "dialogLauncher",
            "type": "ApplicationDialogLauncher"
          }
        ]
      },
      {
        "name": "reflow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "bodyText",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextArea",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "capacity",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "posting",
            "type": "PostingData"
          }
        ]
      },
      {
        "name": "format",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PostingData": {
    "name": "PostingData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.dto",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dto/PostingData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "postingId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "title",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "description",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchField",
        "type": "ResearchField",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "collaborationType",
        "type": "CollaborationType",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "capacity",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applicantCount",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "acceptedCount",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "createdAt",
        "type": "LocalDateTime",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "status",
        "type": "PostingStatus",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "full",
        "type": "boolean",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "active",
        "type": "boolean",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "posterAcademicEmailVerified",
        "type": "boolean",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "applications",
        "type": "List<PostingApplicationData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PostingData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "posterUserId",
            "type": "String"
          },
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          },
          {
            "name": "applicantCount",
            "type": "int"
          },
          {
            "name": "acceptedCount",
            "type": "int"
          },
          {
            "name": "createdAt",
            "type": "LocalDateTime"
          },
          {
            "name": "status",
            "type": "PostingStatus"
          },
          {
            "name": "full",
            "type": "boolean"
          },
          {
            "name": "active",
            "type": "boolean"
          },
          {
            "name": "applications",
            "type": "List<PostingApplicationData>"
          }
        ]
      },
      {
        "name": "from",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingData",
        "parameters": [
          {
            "name": "posting",
            "type": "Posting"
          },
          {
            "name": "applications",
            "type": "List<PostingApplicationData>"
          }
        ]
      },
      {
        "name": "from",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingData",
        "parameters": [
          {
            "name": "posting",
            "type": "Posting"
          }
        ]
      },
      {
        "name": "fromAll",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "List<PostingData>",
        "parameters": [
          {
            "name": "postings",
            "type": "List<Posting>"
          }
        ]
      },
      {
        "name": "getPostingId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getTitle",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getResearchField",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ResearchField",
        "parameters": []
      },
      {
        "name": "getCollaborationType",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "CollaborationType",
        "parameters": []
      },
      {
        "name": "getCapacity",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getApplicantCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "getAcceptedCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "getCreatedAt",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "LocalDateTime",
        "parameters": []
      },
      {
        "name": "getStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingStatus",
        "parameters": []
      },
      {
        "name": "isFull",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "isActive",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "getApplications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<PostingApplicationData>",
        "parameters": []
      },
      {
        "name": "getPosterName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPosterDisplayName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "isPosterAcademicEmailVerified",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      }
    ]
  },
  "PostingGateway": {
    "name": "PostingGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/PostingGateway.java",
    "extends": [],
    "implements": [
      "CreatePostingDataAccessInterface",
      "ClosePostingDataAccessInterface",
      "LoadPostingsDataAccessInterface",
      "ApplyToPostingDataAccessInterface",
      "AcceptApplicationDataAccessInterface",
      "DeclineApplicationDataAccessInterface",
      "LoadMyApplicationsDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "http",
        "type": "ServerHttpClient",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "PostingGateway",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "http",
            "type": "ServerHttpClient"
          }
        ]
      },
      {
        "name": "createPosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Posting",
        "parameters": [
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "description",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "collaborationType",
            "type": "CollaborationType"
          },
          {
            "name": "capacity",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "closePosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Posting",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          }
        ]
      },
      {
        "name": "loadPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Posting>",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          }
        ]
      },
      {
        "name": "loadApplicationsForOwnedPostings",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Map<String, List<PostingApplication>>",
        "parameters": [
          {
            "name": "scope",
            "type": "PostingScope"
          },
          {
            "name": "postings",
            "type": "List<Posting>"
          }
        ]
      },
      {
        "name": "applyToPosting",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "postingId",
            "type": "String"
          },
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "acceptApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      },
      {
        "name": "declineApplication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "applicationId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getMyApplications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<PostingApplication>",
        "parameters": []
      },
      {
        "name": "postingFromJson",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Posting",
        "parameters": [
          {
            "name": "node",
            "type": "JsonNode"
          }
        ]
      },
      {
        "name": "applicationFromJson",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "PostingApplication",
        "parameters": [
          {
            "name": "node",
            "type": "JsonNode"
          }
        ]
      },
      {
        "name": "verified",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "node",
            "type": "JsonNode"
          }
        ]
      }
    ]
  },
  "PostingOwnerSummary": {
    "name": "PostingOwnerSummary",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/PostingOwnerSummary.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "PostingOwnerSummary",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "ownerName",
            "type": "String"
          },
          {
            "name": "academicEmailVerified",
            "type": "boolean"
          }
        ]
      }
    ]
  },
  "PostingScope": {
    "name": "PostingScope",
    "kind": "enum",
    "packageName": "com.scholarmatch.usecase.load_postings",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/load_postings/PostingScope.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "PostingsListViewModel": {
    "name": "PostingsListViewModel",
    "kind": "interface",
    "packageName": "com.scholarmatch.interface_adapter.view_model.support",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/support/PostingsListViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "setPostings",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "postings",
            "type": "List<PostingData>"
          }
        ]
      },
      {
        "name": "setApplicationsByPostingId",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "applicationsByPostingId",
            "type": "Map<String, List<PostingApplicationData>>"
          }
        ]
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "PostingStatus": {
    "name": "PostingStatus",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/PostingStatus.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "ProfileGateway": {
    "name": "ProfileGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/ProfileGateway.java",
    "extends": [],
    "implements": [
      "LoadProfileDataAccessInterface",
      "UpdateProfileDataAccessInterface",
      "DeleteAccountDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "http",
        "type": "ServerHttpClient",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "userMapper",
        "type": "ScholarUserMapper",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ProfileGateway",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "http",
            "type": "ServerHttpClient"
          },
          {
            "name": "institutionCatalog",
            "type": "InstitutionCatalogDataAccessInterface"
          }
        ]
      },
      {
        "name": "getProfile",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": []
      },
      {
        "name": "updateProfile",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "data",
            "type": "UpdateProfileInputData"
          }
        ]
      },
      {
        "name": "deleteAccount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "Publication": {
    "name": "Publication",
    "kind": "class",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/Publication.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "doi",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "title",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "year",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "citationCount",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "Publication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "theDoi",
            "type": "String"
          },
          {
            "name": "theTitle",
            "type": "String"
          },
          {
            "name": "theYear",
            "type": "int"
          },
          {
            "name": "theCitationCount",
            "type": "int"
          }
        ]
      },
      {
        "name": "getDoi",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getTitle",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getYear",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      },
      {
        "name": "getCitationCount",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "int",
        "parameters": []
      }
    ]
  },
  "PublicationEditorPanel": {
    "name": "PublicationEditorPanel",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/PublicationEditorPanel.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "DEFAULT_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FIELD_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_PUBLICATIONS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "cardWidth",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "papersModel",
        "type": "DefaultListModel<Publication>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "onAuthorMetadata",
        "type": "BiConsumer<Integer, Integer>",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "PublicationEditorPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "paperLookupController",
            "type": "PaperLookupController"
          },
          {
            "name": "paperLookupViewModel",
            "type": "PaperLookupViewModel"
          }
        ]
      },
      {
        "name": "PublicationEditorPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "paperLookupController",
            "type": "PaperLookupController"
          },
          {
            "name": "paperLookupViewModel",
            "type": "PaperLookupViewModel"
          },
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "setPublications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "publications",
            "type": "List<Publication>"
          }
        ]
      },
      {
        "name": "getPublications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Publication>",
        "parameters": []
      },
      {
        "name": "setOnAuthorMetadata",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "callback",
            "type": "BiConsumer<Integer, Integer>"
          }
        ]
      },
      {
        "name": "row",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "textField",
            "type": "JTextField"
          },
          {
            "name": "button",
            "type": "JButton"
          }
        ]
      },
      {
        "name": "field",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextField",
        "parameters": [
          {
            "name": "placeholder",
            "type": "String"
          }
        ]
      },
      {
        "name": "smallButton",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JButton",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "exampleLabel",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "strut",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Component",
        "parameters": []
      },
      {
        "name": "addAll",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "panel",
            "type": "JPanel"
          },
          {
            "name": "components",
            "type": "Component[]"
          }
        ]
      }
    ]
  },
  "RecommendController": {
    "name": "RecommendController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.recommend",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/recommend/RecommendController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "recommendInteractor",
        "type": "RecommendInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RecommendController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "recommendInteractor",
            "type": "RecommendInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "RecommendDataAccessInterface": {
    "name": "RecommendDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/RecommendDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "getRecommendations",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<User>",
        "parameters": []
      },
      {
        "name": "getProfile",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "User",
        "parameters": []
      }
    ]
  },
  "RecommendInputBoundary": {
    "name": "RecommendInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.recommend",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/recommend/RecommendInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "RecommendInteractor": {
    "name": "RecommendInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.recommend",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/recommend/RecommendInteractor.java",
    "extends": [],
    "implements": [
      "RecommendInputBoundary"
    ],
    "attributes": [
      {
        "name": "INCOMPLETE_PROFILE_MESSAGE",
        "type": "String",
        "visibility": "~",
        "static": true,
        "readOnly": true
      },
      {
        "name": "recommendDataAccessObject",
        "type": "RecommendDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "RecommendOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RecommendInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "recommendDataAccessObject",
            "type": "RecommendDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "RecommendOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "RecommendOutputBoundary": {
    "name": "RecommendOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.recommend",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/recommend/RecommendOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "RecommendOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RecommendOutputData": {
    "name": "RecommendOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.recommend",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/recommend/RecommendOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "recommendations",
        "type": "List<UserData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RecommendOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "recommendations",
            "type": "List<UserData>"
          }
        ]
      },
      {
        "name": "getRecommendations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<UserData>",
        "parameters": []
      }
    ]
  },
  "RecommendPresenter": {
    "name": "RecommendPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.recommend",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/recommend/RecommendPresenter.java",
    "extends": [],
    "implements": [
      "RecommendOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "RecommendViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RecommendPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "RecommendViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "RecommendOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RecommendUserCard": {
    "name": "RecommendUserCard",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/RecommendUserCard.java",
    "extends": [
      "RoundedPanel"
    ],
    "implements": [
      "Reflowable"
    ],
    "attributes": [
      {
        "name": "MAX_CARD_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_CARD_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "STACK_BREAKPOINT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "AVATAR_SIZE",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "cardUser",
        "type": "UserData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "connectCallback",
        "type": "ConnectListener",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RecommendUserCard",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          },
          {
            "name": "connectListener",
            "type": "ConnectListener"
          }
        ]
      },
      {
        "name": "reflow",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildHeader",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          },
          {
            "name": "cardWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildColumns",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          },
          {
            "name": "cardWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "buildButtons",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "connectListener",
            "type": "ConnectListener"
          }
        ]
      },
      {
        "name": "column",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "heading",
            "type": "String"
          },
          {
            "name": "glyph",
            "type": "Ikon"
          }
        ]
      },
      {
        "name": "columnHeading",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          },
          {
            "name": "glyph",
            "type": "Ikon"
          }
        ]
      },
      {
        "name": "mutedLine",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "capHeight",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "label",
            "type": "JLabel"
          }
        ]
      },
      {
        "name": "wrappedLabel",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          },
          {
            "name": "columnWidth",
            "type": "int"
          }
        ]
      },
      {
        "name": "initial",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "name",
            "type": "String"
          }
        ]
      },
      {
        "name": "joinNonBlank",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "separator",
            "type": "String"
          },
          {
            "name": "parts",
            "type": "String[]"
          }
        ]
      },
      {
        "name": "formatEnum",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "Enum<?>"
          }
        ]
      },
      {
        "name": "escapeHtml",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "verticalStrut",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Component",
        "parameters": [
          {
            "name": "height",
            "type": "int"
          }
        ]
      }
    ]
  },
  "RecommendView": {
    "name": "RecommendView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/RecommendView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "recommendController",
        "type": "RecommendController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "connectController",
        "type": "ConnectController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "dislikeController",
        "type": "DislikeController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "skipController",
        "type": "SkipController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "viewModel",
        "type": "RecommendViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "contentPanel",
        "type": "JPanel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RecommendView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "recommendController",
            "type": "RecommendController"
          },
          {
            "name": "connectController",
            "type": "ConnectController"
          },
          {
            "name": "dislikeController",
            "type": "DislikeController"
          },
          {
            "name": "skipController",
            "type": "SkipController"
          },
          {
            "name": "viewModel",
            "type": "RecommendViewModel"
          }
        ]
      },
      {
        "name": "buildHeader",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": []
      },
      {
        "name": "renderTopCard",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "emptyStateLabel",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RecommendViewModel": {
    "name": "RecommendViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.recommend",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/recommend/RecommendViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "cardStack",
        "type": "ObservableListModel<UserData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "excludedUserIds",
        "type": "Set<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "getCardStack",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableListModel<UserData>",
        "parameters": []
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setCardStack",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "users",
            "type": "List<UserData>"
          }
        ]
      },
      {
        "name": "excludeUser",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          }
        ]
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "Reflowable": {
    "name": "Reflowable",
    "kind": "interface",
    "packageName": "com.scholarmatch.frameworks.gui.style",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/style/Reflowable.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "reflow",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      }
    ]
  },
  "RegisterAccountData": {
    "name": "RegisterAccountData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.register",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/register/RegisterAccountData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "firstName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "lastName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "password",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationCode",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RegisterAccountData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          },
          {
            "name": "verificationCode",
            "type": "String"
          }
        ]
      },
      {
        "name": "getFirstName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getLastName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPassword",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getVerificationCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "RegisterController": {
    "name": "RegisterController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.register",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/register/RegisterController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "registerInteractor",
        "type": "RegisterInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RegisterController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "registerInteractor",
            "type": "RegisterInputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          },
          {
            "name": "verificationCode",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RegisterDataAccessInterface": {
    "name": "RegisterDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/RegisterDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "register",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "AuthResult",
        "parameters": [
          {
            "name": "data",
            "type": "RegisterAccountData"
          }
        ]
      }
    ]
  },
  "RegisterInputBoundary": {
    "name": "RegisterInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.register",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/register/RegisterInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "RegisterInputData"
          }
        ]
      }
    ]
  },
  "RegisterInputData": {
    "name": "RegisterInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.register",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/register/RegisterInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "firstName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "lastName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "password",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationCode",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RegisterInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "password",
            "type": "String"
          },
          {
            "name": "verificationCode",
            "type": "String"
          }
        ]
      },
      {
        "name": "getFirstName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getLastName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getPassword",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getVerificationCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "RegisterInteractor": {
    "name": "RegisterInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.register",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/register/RegisterInteractor.java",
    "extends": [],
    "implements": [
      "RegisterInputBoundary"
    ],
    "attributes": [
      {
        "name": "EMAIL_PATTERN",
        "type": "Pattern",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_PASSWORD_LENGTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_PASSWORD_LENGTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "authDataAccessObject",
        "type": "RegisterDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "sessionManager",
        "type": "SessionWriterInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "RegisterOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RegisterInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "authDataAccessObject",
            "type": "RegisterDataAccessInterface"
          },
          {
            "name": "sessionManager",
            "type": "SessionWriterInterface"
          },
          {
            "name": "outputBoundary",
            "type": "RegisterOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "RegisterInputData"
          }
        ]
      },
      {
        "name": "validate",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": [
          {
            "name": "inputData",
            "type": "RegisterInputData"
          }
        ]
      },
      {
        "name": "isBlank",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "isValidEmail",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RegisterOutputBoundary": {
    "name": "RegisterOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.register",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/register/RegisterOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "RegisterOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RegisterOutputData": {
    "name": "RegisterOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.register",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/register/RegisterOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "userId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "name",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RegisterOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          },
          {
            "name": "name",
            "type": "String"
          }
        ]
      },
      {
        "name": "getUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "RegisterPresenter": {
    "name": "RegisterPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.register",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/register/RegisterPresenter.java",
    "extends": [],
    "implements": [
      "RegisterOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "RegisterViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RegisterPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "RegisterViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "RegisterOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RegisterView": {
    "name": "RegisterView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/RegisterView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "CARD_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FIELD_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "controller",
        "type": "RegisterController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationController",
        "type": "RequestEmailVerificationController",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "viewModel",
        "type": "RegisterViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "registerErrorListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationMessageListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationErrorListener",
        "type": "Consumer<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RegisterView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "controller",
            "type": "RegisterController"
          },
          {
            "name": "verificationController",
            "type": "RequestEmailVerificationController"
          },
          {
            "name": "viewModel",
            "type": "RegisterViewModel"
          }
        ]
      },
      {
        "name": "title",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "field",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextField",
        "parameters": [
          {
            "name": "placeholder",
            "type": "String"
          },
          {
            "name": "leadingGlyph",
            "type": "Ikon"
          }
        ]
      },
      {
        "name": "passwordField",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPasswordField",
        "parameters": [
          {
            "name": "placeholder",
            "type": "String"
          }
        ]
      },
      {
        "name": "strut",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Component",
        "parameters": []
      },
      {
        "name": "addAll",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "panel",
            "type": "JPanel"
          },
          {
            "name": "components",
            "type": "Component[]"
          }
        ]
      },
      {
        "name": "showMessage",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "messageType",
            "type": "int"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "RegisterViewModel": {
    "name": "RegisterViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.register",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/register/RegisterViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "successMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "registrationSucceeded",
        "type": "ObservableValue<Boolean>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "verificationError",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "successMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "registrationSucceededProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<Boolean>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setSuccessMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setRegistrationSucceeded",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "succeeded",
            "type": "boolean"
          }
        ]
      },
      {
        "name": "verificationMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "verificationErrorProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setVerificationMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "setVerificationError",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RemoteVerificationEmailSender": {
    "name": "RemoteVerificationEmailSender",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/RemoteVerificationEmailSender.java",
    "extends": [],
    "implements": [
      "VerificationEmailSenderDataAccessInterface"
    ],
    "attributes": [
      {
        "name": "REQUEST_VERIFICATION_CODE_PATH",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "REQUEST_TIMEOUT",
        "type": "Duration",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "httpSender",
        "type": "HttpSender",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "objectMapper",
        "type": "ObjectMapper",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "requestVerificationCodeUri",
        "type": "URI",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RemoteVerificationEmailSender",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "serverBaseUrl",
            "type": "String"
          }
        ]
      },
      {
        "name": "RemoteVerificationEmailSender",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "httpSender",
            "type": "HttpSender"
          },
          {
            "name": "objectMapper",
            "type": "ObjectMapper"
          },
          {
            "name": "serverBaseUrl",
            "type": "String"
          }
        ]
      },
      {
        "name": "requestVerificationCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RequestEmailVerificationController": {
    "name": "RequestEmailVerificationController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.request_email_verification",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/request_email_verification/RequestEmailVerificationController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "RequestEmailVerificationInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RequestEmailVerificationController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "RequestEmailVerificationInputBoundary"
          }
        ]
      },
      {
        "name": "sendVerificationCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RequestEmailVerificationInputBoundary": {
    "name": "RequestEmailVerificationInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.request_email_verification",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/request_email_verification/RequestEmailVerificationInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "RequestEmailVerificationInputData"
          }
        ]
      }
    ]
  },
  "RequestEmailVerificationInputData": {
    "name": "RequestEmailVerificationInputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.request_email_verification",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/request_email_verification/RequestEmailVerificationInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "RequestEmailVerificationInteractor": {
    "name": "RequestEmailVerificationInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.request_email_verification",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/request_email_verification/RequestEmailVerificationInteractor.java",
    "extends": [],
    "implements": [
      "RequestEmailVerificationInputBoundary"
    ],
    "attributes": [
      {
        "name": "EMAIL_PATTERN",
        "type": "Pattern",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "emailSender",
        "type": "VerificationEmailSenderDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "RequestEmailVerificationOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RequestEmailVerificationInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "emailSender",
            "type": "VerificationEmailSenderDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "RequestEmailVerificationOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "RequestEmailVerificationInputData"
          }
        ]
      },
      {
        "name": "normalize",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RequestEmailVerificationOutputBoundary": {
    "name": "RequestEmailVerificationOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.request_email_verification",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/request_email_verification/RequestEmailVerificationOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "RequestEmailVerificationOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "RequestEmailVerificationOutputData": {
    "name": "RequestEmailVerificationOutputData",
    "kind": "record",
    "packageName": "com.scholarmatch.usecase.request_email_verification",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/request_email_verification/RequestEmailVerificationOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "RequestEmailVerificationPresenter": {
    "name": "RequestEmailVerificationPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.request_email_verification",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/request_email_verification/RequestEmailVerificationPresenter.java",
    "extends": [],
    "implements": [
      "RequestEmailVerificationOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "RegisterViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RequestEmailVerificationPresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "RegisterViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "RequestEmailVerificationOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "error",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ResearchField": {
    "name": "ResearchField",
    "kind": "enum",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/ResearchField.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": []
  },
  "ResearchInterestEditorPanel": {
    "name": "ResearchInterestEditorPanel",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.component",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/component/ResearchInterestEditorPanel.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "FIELD_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "cardWidth",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "interestsModel",
        "type": "DefaultListModel<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ResearchInterestEditorPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "width",
            "type": "int"
          }
        ]
      },
      {
        "name": "setResearchInterests",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "interests",
            "type": "List<String>"
          }
        ]
      },
      {
        "name": "getResearchInterests",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": []
      },
      {
        "name": "row",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JPanel",
        "parameters": [
          {
            "name": "textField",
            "type": "JTextField"
          },
          {
            "name": "button",
            "type": "JButton"
          }
        ]
      },
      {
        "name": "field",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextField",
        "parameters": [
          {
            "name": "placeholder",
            "type": "String"
          }
        ]
      },
      {
        "name": "smallButton",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JButton",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "strut",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Component",
        "parameters": []
      },
      {
        "name": "addAll",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "panel",
            "type": "JPanel"
          },
          {
            "name": "components",
            "type": "Component[]"
          }
        ]
      }
    ]
  },
  "ResourceNotFoundException": {
    "name": "ResourceNotFoundException",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.exception",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/exception/ResourceNotFoundException.java",
    "extends": [
      "DataAccessException"
    ],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "ResourceNotFoundException",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "ResourceNotFoundException",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          },
          {
            "name": "cause",
            "type": "Throwable"
          }
        ]
      }
    ]
  },
  "RoundedPanel": {
    "name": "RoundedPanel",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.style",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/style/RoundedPanel.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "arc",
        "type": "int",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "RoundedPanel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "arc",
            "type": "int"
          },
          {
            "name": "padding",
            "type": "int"
          }
        ]
      },
      {
        "name": "paintComponent",
        "visibility": "#",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "g",
            "type": "Graphics"
          }
        ]
      }
    ]
  },
  "ScholarMatchApp": {
    "name": "ScholarMatchApp",
    "kind": "class",
    "packageName": "com.scholarmatch.app",
    "sourcePath": "src/main/java/com/scholarmatch/app/ScholarMatchApp.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "DEFAULT_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DEFAULT_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "main",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "args",
            "type": "String[]"
          }
        ]
      },
      {
        "name": "start",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "installTheme",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      },
      {
        "name": "installTheme",
        "visibility": "~",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "themeProperties",
            "type": "InputStream"
          }
        ]
      }
    ]
  },
  "ScholarUserMapper": {
    "name": "ScholarUserMapper",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/ScholarUserMapper.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "institutionCatalog",
        "type": "InstitutionCatalogDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ScholarUserMapper",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "institutionCatalog",
            "type": "InstitutionCatalogDataAccessInterface"
          }
        ]
      },
      {
        "name": "fromJson",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "node",
            "type": "JsonNode"
          }
        ]
      }
    ]
  },
  "SearchAuthorsInputData": {
    "name": "SearchAuthorsInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/paper_lookup/SearchAuthorsInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "authorName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SearchAuthorsInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "authorName",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthorName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "SecureVerificationCodeGenerator": {
    "name": "SecureVerificationCodeGenerator",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/SecureVerificationCodeGenerator.java",
    "extends": [],
    "implements": [
      "VerificationCodeGeneratorInterface"
    ],
    "attributes": [
      {
        "name": "CODE_RANGE",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "random",
        "type": "SecureRandom",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "generateCode",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "SelectAuthorInputData": {
    "name": "SelectAuthorInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/paper_lookup/SelectAuthorInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "authorId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SelectAuthorInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthorId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "SemanticScholarGateway": {
    "name": "SemanticScholarGateway",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.paper_lookup",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/paper_lookup/SemanticScholarGateway.java",
    "extends": [],
    "implements": [
      "UserAPIGatewayInterface"
    ],
    "attributes": [
      {
        "name": "API_BASE_URL",
        "type": "String",
        "visibility": "−",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": []
  },
  "SendMessageController": {
    "name": "SendMessageController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.send_message",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/send_message/SendMessageController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "SendMessageInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SendMessageController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "SendMessageInputBoundary"
          }
        ]
      },
      {
        "name": "sendMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "receiverId",
            "type": "String"
          },
          {
            "name": "content",
            "type": "String"
          }
        ]
      }
    ]
  },
  "SendMessageDataAccessInterface": {
    "name": "SendMessageDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/SendMessageDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "sendMessage",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "Message",
        "parameters": [
          {
            "name": "receiverId",
            "type": "String"
          },
          {
            "name": "content",
            "type": "String"
          }
        ]
      }
    ]
  },
  "SendMessageInputBoundary": {
    "name": "SendMessageInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.send_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/send_message/SendMessageInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SendMessageInputData"
          }
        ]
      }
    ]
  },
  "SendMessageInputData": {
    "name": "SendMessageInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.send_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/send_message/SendMessageInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "receiverId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "content",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SendMessageInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "receiverId",
            "type": "String"
          },
          {
            "name": "content",
            "type": "String"
          }
        ]
      },
      {
        "name": "getReceiverId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getContent",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "SendMessageInteractor": {
    "name": "SendMessageInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.send_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/send_message/SendMessageInteractor.java",
    "extends": [],
    "implements": [
      "SendMessageInputBoundary"
    ],
    "attributes": [
      {
        "name": "MAX_CONTENT_LENGTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "messageDataAccessObject",
        "type": "SendMessageDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "SendMessageOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SendMessageInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "messageDataAccessObject",
            "type": "SendMessageDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "SendMessageOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SendMessageInputData"
          }
        ]
      },
      {
        "name": "validate",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": [
          {
            "name": "inputData",
            "type": "SendMessageInputData"
          }
        ]
      }
    ]
  },
  "SendMessageOutputBoundary": {
    "name": "SendMessageOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.send_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/send_message/SendMessageOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "SendMessageOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "SendMessageOutputData": {
    "name": "SendMessageOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.send_message",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/send_message/SendMessageOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "message",
        "type": "MessageData",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SendMessageOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "message",
            "type": "MessageData"
          }
        ]
      },
      {
        "name": "getMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "MessageData",
        "parameters": []
      }
    ]
  },
  "SendMessagePresenter": {
    "name": "SendMessagePresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.send_message",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/send_message/SendMessagePresenter.java",
    "extends": [],
    "implements": [
      "SendMessageOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "ChatViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SendMessagePresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "ChatViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "SendMessageOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "ServerHttpClient": {
    "name": "ServerHttpClient",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.data_access_object.server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/server/ServerHttpClient.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "baseUrl",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "session",
        "type": "CurrentUserProviderInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "httpSender",
        "type": "HttpSender",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "mapper",
        "type": "ObjectMapper",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "ServerHttpClient",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "baseUrl",
            "type": "String"
          },
          {
            "name": "session",
            "type": "CurrentUserProviderInterface"
          }
        ]
      },
      {
        "name": "ServerHttpClient",
        "visibility": "~",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "baseUrl",
            "type": "String"
          },
          {
            "name": "session",
            "type": "CurrentUserProviderInterface"
          },
          {
            "name": "httpSender",
            "type": "HttpSender"
          }
        ]
      },
      {
        "name": "get",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JsonNode",
        "parameters": [
          {
            "name": "path",
            "type": "String"
          }
        ]
      },
      {
        "name": "post",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JsonNode",
        "parameters": [
          {
            "name": "path",
            "type": "String"
          },
          {
            "name": "body",
            "type": "String"
          },
          {
            "name": "authenticated",
            "type": "boolean"
          }
        ]
      },
      {
        "name": "put",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JsonNode",
        "parameters": [
          {
            "name": "path",
            "type": "String"
          },
          {
            "name": "body",
            "type": "String"
          }
        ]
      },
      {
        "name": "delete",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "path",
            "type": "String"
          }
        ]
      },
      {
        "name": "toJson",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "obj",
            "type": "Object"
          }
        ]
      },
      {
        "name": "describeNetworkFailure",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "e",
            "type": "Exception"
          }
        ]
      },
      {
        "name": "describeStatusCode",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "statusCode",
            "type": "int"
          }
        ]
      },
      {
        "name": "parseResponse",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JsonNode",
        "parameters": [
          {
            "name": "response",
            "type": "HttpSenderResponse"
          }
        ]
      }
    ]
  },
  "SessionClearerInterface": {
    "name": "SessionClearerInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/SessionClearerInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "clearSession",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "SessionWriterInterface": {
    "name": "SessionWriterInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/SessionWriterInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "setCurrentUserId",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          }
        ]
      },
      {
        "name": "setToken",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "token",
            "type": "String"
          }
        ]
      }
    ]
  },
  "SkipController": {
    "name": "SkipController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.skip",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/skip/SkipController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "SkipInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SkipController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "SkipInputBoundary"
          }
        ]
      },
      {
        "name": "skip",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "skippedUserId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "SkipInputBoundary": {
    "name": "SkipInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.skip",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/skip/SkipInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SkipInputData"
          }
        ]
      }
    ]
  },
  "SkipInputData": {
    "name": "SkipInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.skip",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/skip/SkipInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "skippedUserId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SkipInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "skippedUserId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getSkippedUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "SkipInteractor": {
    "name": "SkipInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.skip",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/skip/SkipInteractor.java",
    "extends": [],
    "implements": [
      "SkipInputBoundary"
    ],
    "attributes": [
      {
        "name": "outputBoundary",
        "type": "SkipOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "SkipInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "outputBoundary",
            "type": "SkipOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "SkipInputData"
          }
        ]
      }
    ]
  },
  "SkipOutputBoundary": {
    "name": "SkipOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.skip",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/skip/SkipOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "SkipPresenter": {
    "name": "SkipPresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.skip",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/skip/SkipPresenter.java",
    "extends": [],
    "implements": [
      "SkipOutputBoundary"
    ],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "Theme": {
    "name": "Theme",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.style",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/style/Theme.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "BG_DEFAULT",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "BG_SUBTLE",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "BG_INSET",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FG_DEFAULT",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FG_MUTED",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FG_SUBTLE",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FG_EMPHASIS",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "BORDER_DEFAULT",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "BORDER_MUTED",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "ACCENT",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "ACCENT_BRIGHT",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "ACCENT_FG",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "SUCCESS",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "SUCCESS_FG",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DANGER",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "DANGER_FG",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "WARNING_BG",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "WARNING_BORDER",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "WARNING_FG",
        "type": "Color",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "CARD_RADIUS",
        "type": "int",
        "visibility": "+",
        "static": true,
        "readOnly": true
      },
      {
        "name": "SCHOLAR_CARD_RADIUS",
        "type": "int",
        "visibility": "+",
        "static": true,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "Theme",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": []
      }
    ]
  },
  "UpdateProfileController": {
    "name": "UpdateProfileController",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/update_profile/UpdateProfileController.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "interactor",
        "type": "UpdateProfileInputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "UpdateProfileController",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "interactor",
            "type": "UpdateProfileInputBoundary"
          }
        ]
      },
      {
        "name": "updateProfile",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "String"
          },
          {
            "name": "academicLevel",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "String"
          },
          {
            "name": "lookingFor",
            "type": "String"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "String"
          },
          {
            "name": "researchInterests",
            "type": "List<String>"
          },
          {
            "name": "phoneNumber",
            "type": "String"
          },
          {
            "name": "hIndex",
            "type": "Integer"
          },
          {
            "name": "totalCitations",
            "type": "Integer"
          },
          {
            "name": "educations",
            "type": "List<Education>"
          },
          {
            "name": "publications",
            "type": "List<Publication>"
          }
        ]
      }
    ]
  },
  "UpdateProfileDataAccessInterface": {
    "name": "UpdateProfileDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/UpdateProfileDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "updateProfile",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "User",
        "parameters": [
          {
            "name": "data",
            "type": "UpdateProfileInputData"
          }
        ]
      }
    ]
  },
  "UpdateProfileInputBoundary": {
    "name": "UpdateProfileInputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/update_profile/UpdateProfileInputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "UpdateProfileInputData"
          }
        ]
      }
    ]
  },
  "UpdateProfileInputData": {
    "name": "UpdateProfileInputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/update_profile/UpdateProfileInputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "institution",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "academicLevel",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchField",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "lookingFor",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "collaborationDescription",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchDescription",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "weeklyAvailabilityHours",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "fundingStatus",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchInterests",
        "type": "List<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "phoneNumber",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "hIndex",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "totalCitations",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "educations",
        "type": "List<Education>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "publications",
        "type": "List<Publication>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "UpdateProfileInputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "String"
          },
          {
            "name": "academicLevel",
            "type": "String"
          },
          {
            "name": "researchField",
            "type": "String"
          },
          {
            "name": "lookingFor",
            "type": "String"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "String"
          },
          {
            "name": "researchInterests",
            "type": "List<String>"
          },
          {
            "name": "phoneNumber",
            "type": "String"
          },
          {
            "name": "hIndex",
            "type": "Integer"
          },
          {
            "name": "totalCitations",
            "type": "Integer"
          },
          {
            "name": "educations",
            "type": "List<Education>"
          },
          {
            "name": "publications",
            "type": "List<Publication>"
          }
        ]
      },
      {
        "name": "getEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getInstitution",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getAcademicLevel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getResearchField",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getLookingFor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getCollaborationDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getResearchDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getWeeklyAvailabilityHours",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getFundingStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getResearchInterests",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": []
      },
      {
        "name": "getPhoneNumber",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getHIndex",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getTotalCitations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getEducations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Education>",
        "parameters": []
      },
      {
        "name": "getPublications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Publication>",
        "parameters": []
      }
    ]
  },
  "UpdateProfileInteractor": {
    "name": "UpdateProfileInteractor",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/update_profile/UpdateProfileInteractor.java",
    "extends": [],
    "implements": [
      "UpdateProfileInputBoundary"
    ],
    "attributes": [
      {
        "name": "MAX_DESCRIPTION_LENGTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_INTEREST_LENGTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MIN_EDUCATION_YEAR",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_FUTURE_YEARS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "MAX_PUBLICATIONS",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "profileDataAccessObject",
        "type": "UpdateProfileDataAccessInterface",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "outputBoundary",
        "type": "UpdateProfileOutputBoundary",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "UpdateProfileInteractor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "profileDataAccessObject",
            "type": "UpdateProfileDataAccessInterface"
          },
          {
            "name": "outputBoundary",
            "type": "UpdateProfileOutputBoundary"
          }
        ]
      },
      {
        "name": "execute",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "inputData",
            "type": "UpdateProfileInputData"
          }
        ]
      },
      {
        "name": "validate",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": [
          {
            "name": "inputData",
            "type": "UpdateProfileInputData"
          }
        ]
      },
      {
        "name": "requireText",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errors",
            "type": "List<String>"
          },
          {
            "name": "fieldName",
            "type": "String"
          },
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "checkLength",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errors",
            "type": "List<String>"
          },
          {
            "name": "fieldName",
            "type": "String"
          },
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "validateEducations",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errors",
            "type": "List<String>"
          },
          {
            "name": "educations",
            "type": "List<Education>"
          }
        ]
      },
      {
        "name": "educationLabel",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "index",
            "type": "int"
          },
          {
            "name": "education",
            "type": "Education"
          }
        ]
      }
    ]
  },
  "UpdateProfileOutputBoundary": {
    "name": "UpdateProfileOutputBoundary",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/update_profile/UpdateProfileOutputBoundary.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "UpdateProfileOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "UpdateProfileOutputData": {
    "name": "UpdateProfileOutputData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/update_profile/UpdateProfileOutputData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "userId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "UpdateProfileOutputData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "UpdateProfilePresenter": {
    "name": "UpdateProfilePresenter",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/update_profile/UpdateProfilePresenter.java",
    "extends": [],
    "implements": [
      "UpdateProfileOutputBoundary"
    ],
    "attributes": [
      {
        "name": "viewModel",
        "type": "UpdateProfileViewModel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "UpdateProfilePresenter",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "viewModel",
            "type": "UpdateProfileViewModel"
          }
        ]
      },
      {
        "name": "prepareSuccessView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "outputData",
            "type": "UpdateProfileOutputData"
          }
        ]
      },
      {
        "name": "prepareFailView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "errorMessage",
            "type": "String"
          }
        ]
      }
    ]
  },
  "UpdateProfileView": {
    "name": "UpdateProfileView",
    "kind": "class",
    "packageName": "com.scholarmatch.frameworks.gui.view",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/gui/view/UpdateProfileView.java",
    "extends": [
      "JPanel"
    ],
    "implements": [],
    "attributes": [
      {
        "name": "COLUMN_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "COLUMN_PADDING",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "COLUMN_OUTER_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "COLUMN_GAP",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "ROW_WIDTH",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "FIELD_HEIGHT",
        "type": "int",
        "visibility": "−",
        "static": true,
        "readOnly": true
      },
      {
        "name": "listenerRemovers",
        "type": "List<Runnable>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "UpdateProfileView",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "controller",
            "type": "UpdateProfileController"
          },
          {
            "name": "loadProfileController",
            "type": "LoadProfileController"
          },
          {
            "name": "viewModel",
            "type": "UpdateProfileViewModel"
          },
          {
            "name": "paperLookupController",
            "type": "PaperLookupController"
          },
          {
            "name": "paperLookupViewModel",
            "type": "PaperLookupViewModel"
          }
        ]
      },
      {
        "name": "buildColumnCard",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "RoundedPanel",
        "parameters": [
          {
            "name": "title",
            "type": "String"
          },
          {
            "name": "children",
            "type": "Component[]"
          }
        ]
      },
      {
        "name": "formatEnum",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": [
          {
            "name": "value",
            "type": "Enum<?>"
          }
        ]
      },
      {
        "name": "isBlankOrNonNegativeInt",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "blankToNull",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "styleCombo",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "combo",
            "type": "JComboBox<?>"
          }
        ]
      },
      {
        "name": "sortedInstitutions",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Institution[]",
        "parameters": [
          {
            "name": "institutions",
            "type": "java.util.List<Institution>"
          }
        ]
      },
      {
        "name": "styleInstitutionCombo",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "combo",
            "type": "JComboBox<Institution>"
          }
        ]
      },
      {
        "name": "field",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextField",
        "parameters": [
          {
            "name": "placeholder",
            "type": "String"
          }
        ]
      },
      {
        "name": "textArea",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JTextArea",
        "parameters": []
      },
      {
        "name": "scrollWrap",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JScrollPane",
        "parameters": [
          {
            "name": "component",
            "type": "JComponent"
          }
        ]
      },
      {
        "name": "labeled",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "JLabel",
        "parameters": [
          {
            "name": "text",
            "type": "String"
          }
        ]
      },
      {
        "name": "strut",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "Component",
        "parameters": []
      },
      {
        "name": "addAll",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "panel",
            "type": "JPanel"
          },
          {
            "name": "components",
            "type": "Component[]"
          }
        ]
      },
      {
        "name": "listen",
        "visibility": "−",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "<T>void",
        "parameters": [
          {
            "name": "observable",
            "type": "ObservableValue<T>"
          },
          {
            "name": "listener",
            "type": "Consumer<T>"
          }
        ]
      },
      {
        "name": "removeNotify",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": []
      }
    ]
  },
  "UpdateProfileViewModel": {
    "name": "UpdateProfileViewModel",
    "kind": "class",
    "packageName": "com.scholarmatch.interface_adapter.view_model.update_profile",
    "sourcePath": "src/main/java/com/scholarmatch/interface_adapter/view_model/update_profile/UpdateProfileViewModel.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "errorMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "saveSuccessMessage",
        "type": "ObservableValue<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "currentUser",
        "type": "ObservableValue<UserData>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "institutions",
        "type": "List<Institution>",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "academicLevels",
        "type": "List<AcademicLevel>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "collaborationTypes",
        "type": "List<CollaborationType>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchFields",
        "type": "List<ResearchField>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "fundingStatuses",
        "type": "List<FundingStatus>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "currentUserProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<UserData>",
        "parameters": []
      },
      {
        "name": "setCurrentUser",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "user",
            "type": "UserData"
          }
        ]
      },
      {
        "name": "errorMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setErrorMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "saveSuccessMessageProperty",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ObservableValue<String>",
        "parameters": []
      },
      {
        "name": "setSaveSuccessMessage",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "message",
            "type": "String"
          }
        ]
      },
      {
        "name": "getInstitutions",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Institution>",
        "parameters": []
      },
      {
        "name": "setInstitutions",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "institutions",
            "type": "List<Institution>"
          }
        ]
      },
      {
        "name": "getAcademicLevels",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<AcademicLevel>",
        "parameters": []
      },
      {
        "name": "getCollaborationTypes",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<CollaborationType>",
        "parameters": []
      },
      {
        "name": "getResearchFields",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<ResearchField>",
        "parameters": []
      },
      {
        "name": "getFundingStatuses",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<FundingStatus>",
        "parameters": []
      }
    ]
  },
  "User": {
    "name": "User",
    "kind": "class",
    "packageName": "com.scholarmatch.entity",
    "sourcePath": "src/main/java/com/scholarmatch/entity/User.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "userId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "firstName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "lastName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "phoneNumber",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "institution",
        "type": "Institution",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "academicLevel",
        "type": "AcademicLevel",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "researchField",
        "type": "ResearchField",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "lookingFor",
        "type": "CollaborationType",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "collaborationDescription",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "researchDescription",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "weeklyAvailabilityHours",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "fundingStatus",
        "type": "FundingStatus",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "passwordHash",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "researchInterests",
        "type": "List<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "educations",
        "type": "List<Education>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "publications",
        "type": "List<Publication>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "hIndex",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "totalCitations",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": false
      },
      {
        "name": "emailAccountType",
        "type": "EmailAccountType",
        "visibility": "−",
        "static": false,
        "readOnly": false
      }
    ],
    "operations": [
      {
        "name": "User",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          },
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "phoneNumber",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "Institution"
          },
          {
            "name": "academicLevel",
            "type": "AcademicLevel"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "lookingFor",
            "type": "CollaborationType"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "FundingStatus"
          },
          {
            "name": "passwordHash",
            "type": "String"
          }
        ]
      },
      {
        "name": "User",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          },
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "phoneNumber",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "Institution"
          },
          {
            "name": "academicLevel",
            "type": "AcademicLevel"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "lookingFor",
            "type": "CollaborationType"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "FundingStatus"
          },
          {
            "name": "passwordHash",
            "type": "String"
          },
          {
            "name": "emailAccountType",
            "type": "EmailAccountType"
          }
        ]
      },
      {
        "name": "getFullName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "isProfileComplete",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "isNotBlank",
        "visibility": "−",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "value",
            "type": "String"
          }
        ]
      },
      {
        "name": "addResearchInterest",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "interest",
            "type": "String"
          }
        ]
      },
      {
        "name": "removeResearchInterest",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "interest",
            "type": "String"
          }
        ]
      },
      {
        "name": "addEducation",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "education",
            "type": "Education"
          }
        ]
      },
      {
        "name": "getEducations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Education>",
        "parameters": []
      },
      {
        "name": "addPublication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "publication",
            "type": "Publication"
          }
        ]
      },
      {
        "name": "removePublication",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": [
          {
            "name": "doi",
            "type": "String"
          }
        ]
      },
      {
        "name": "getPublications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Publication>",
        "parameters": []
      },
      {
        "name": "getUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getFirstName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getLastName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getEmailAccountType",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "EmailAccountType",
        "parameters": []
      },
      {
        "name": "setEmailAccountType",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "emailAccountType",
            "type": "EmailAccountType"
          }
        ]
      },
      {
        "name": "setEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      },
      {
        "name": "getPhoneNumber",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "setPhoneNumber",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "phoneNumber",
            "type": "String"
          }
        ]
      },
      {
        "name": "getInstitution",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Institution",
        "parameters": []
      },
      {
        "name": "setInstitution",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "institution",
            "type": "Institution"
          }
        ]
      },
      {
        "name": "getAcademicLevel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AcademicLevel",
        "parameters": []
      },
      {
        "name": "setAcademicLevel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "academicLevel",
            "type": "AcademicLevel"
          }
        ]
      },
      {
        "name": "getResearchField",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ResearchField",
        "parameters": []
      },
      {
        "name": "setResearchField",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "researchField",
            "type": "ResearchField"
          }
        ]
      },
      {
        "name": "getLookingFor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "CollaborationType",
        "parameters": []
      },
      {
        "name": "setLookingFor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "lookingFor",
            "type": "CollaborationType"
          }
        ]
      },
      {
        "name": "getCollaborationDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "setCollaborationDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "collaborationDescription",
            "type": "String"
          }
        ]
      },
      {
        "name": "getResearchDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "setResearchDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "researchDescription",
            "type": "String"
          }
        ]
      },
      {
        "name": "getWeeklyAvailabilityHours",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "setWeeklyAvailabilityHours",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "getFundingStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "FundingStatus",
        "parameters": []
      },
      {
        "name": "setFundingStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "fundingStatus",
            "type": "FundingStatus"
          }
        ]
      },
      {
        "name": "getResearchInterests",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": []
      },
      {
        "name": "gethIndex",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "sethIndex",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "hIndex",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "getTotalCitations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "setTotalCitations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "totalCitations",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "getPasswordHash",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "setPasswordHash",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "passwordHash",
            "type": "String"
          }
        ]
      }
    ]
  },
  "UserAPIGatewayInterface": {
    "name": "UserAPIGatewayInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/UserAPIGatewayInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "searchAuthors",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<AuthorCandidateDataAccessInterface>",
        "parameters": [
          {
            "name": "authorName",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthor",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "AuthorCandidateDataAccessInterface",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      },
      {
        "name": "getAuthorPapers",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "List<Publication>",
        "parameters": [
          {
            "name": "authorId",
            "type": "String"
          }
        ]
      }
    ]
  },
  "UserData": {
    "name": "UserData",
    "kind": "class",
    "packageName": "com.scholarmatch.usecase.dto",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/dto/UserData.java",
    "extends": [],
    "implements": [],
    "attributes": [
      {
        "name": "userId",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "firstName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "lastName",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "email",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "phoneNumber",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "institution",
        "type": "Institution",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "academicLevel",
        "type": "AcademicLevel",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchField",
        "type": "ResearchField",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "lookingFor",
        "type": "CollaborationType",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "collaborationDescription",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchDescription",
        "type": "String",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "weeklyAvailabilityHours",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "fundingStatus",
        "type": "FundingStatus",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "researchInterests",
        "type": "List<String>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "educations",
        "type": "List<Education>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "publications",
        "type": "List<Publication>",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "hIndex",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "totalCitations",
        "type": "Integer",
        "visibility": "−",
        "static": false,
        "readOnly": true
      },
      {
        "name": "emailAccountType",
        "type": "EmailAccountType",
        "visibility": "−",
        "static": false,
        "readOnly": true
      }
    ],
    "operations": [
      {
        "name": "UserData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          },
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "phoneNumber",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "Institution"
          },
          {
            "name": "academicLevel",
            "type": "AcademicLevel"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "lookingFor",
            "type": "CollaborationType"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "FundingStatus"
          },
          {
            "name": "researchInterests",
            "type": "List<String>"
          },
          {
            "name": "educations",
            "type": "List<Education>"
          },
          {
            "name": "publications",
            "type": "List<Publication>"
          },
          {
            "name": "hIndex",
            "type": "Integer"
          },
          {
            "name": "totalCitations",
            "type": "Integer"
          }
        ]
      },
      {
        "name": "UserData",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": true,
        "returnType": "",
        "parameters": [
          {
            "name": "userId",
            "type": "String"
          },
          {
            "name": "firstName",
            "type": "String"
          },
          {
            "name": "lastName",
            "type": "String"
          },
          {
            "name": "email",
            "type": "String"
          },
          {
            "name": "phoneNumber",
            "type": "String"
          },
          {
            "name": "institution",
            "type": "Institution"
          },
          {
            "name": "academicLevel",
            "type": "AcademicLevel"
          },
          {
            "name": "researchField",
            "type": "ResearchField"
          },
          {
            "name": "lookingFor",
            "type": "CollaborationType"
          },
          {
            "name": "collaborationDescription",
            "type": "String"
          },
          {
            "name": "researchDescription",
            "type": "String"
          },
          {
            "name": "weeklyAvailabilityHours",
            "type": "Integer"
          },
          {
            "name": "fundingStatus",
            "type": "FundingStatus"
          },
          {
            "name": "researchInterests",
            "type": "List<String>"
          },
          {
            "name": "educations",
            "type": "List<Education>"
          },
          {
            "name": "publications",
            "type": "List<Publication>"
          },
          {
            "name": "hIndex",
            "type": "Integer"
          },
          {
            "name": "totalCitations",
            "type": "Integer"
          },
          {
            "name": "emailAccountType",
            "type": "EmailAccountType"
          }
        ]
      },
      {
        "name": "from",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "UserData",
        "parameters": [
          {
            "name": "user",
            "type": "User"
          }
        ]
      },
      {
        "name": "fromAll",
        "visibility": "+",
        "static": true,
        "abstract": false,
        "constructor": false,
        "returnType": "List<UserData>",
        "parameters": [
          {
            "name": "users",
            "type": "List<User>"
          }
        ]
      },
      {
        "name": "getUserId",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getFirstName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getLastName",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getEmailAccountType",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "EmailAccountType",
        "parameters": []
      },
      {
        "name": "isAcademicEmail",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "boolean",
        "parameters": []
      },
      {
        "name": "getPhoneNumber",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getInstitution",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Institution",
        "parameters": []
      },
      {
        "name": "getAcademicLevel",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "AcademicLevel",
        "parameters": []
      },
      {
        "name": "getResearchField",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "ResearchField",
        "parameters": []
      },
      {
        "name": "getLookingFor",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "CollaborationType",
        "parameters": []
      },
      {
        "name": "getCollaborationDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getResearchDescription",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      },
      {
        "name": "getWeeklyAvailabilityHours",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getFundingStatus",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "FundingStatus",
        "parameters": []
      },
      {
        "name": "getResearchInterests",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<String>",
        "parameters": []
      },
      {
        "name": "getEducations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Education>",
        "parameters": []
      },
      {
        "name": "getPublications",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "List<Publication>",
        "parameters": []
      },
      {
        "name": "gethIndex",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      },
      {
        "name": "getTotalCitations",
        "visibility": "+",
        "static": false,
        "abstract": false,
        "constructor": false,
        "returnType": "Integer",
        "parameters": []
      }
    ]
  },
  "VerificationCodeGeneratorInterface": {
    "name": "VerificationCodeGeneratorInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.frameworks.data_access_object.local_mock_server",
    "sourcePath": "src/main/java/com/scholarmatch/frameworks/data_access_object/local_mock_server/VerificationCodeGeneratorInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "generateCode",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "String",
        "parameters": []
      }
    ]
  },
  "VerificationEmailSenderDataAccessInterface": {
    "name": "VerificationEmailSenderDataAccessInterface",
    "kind": "interface",
    "packageName": "com.scholarmatch.usecase.data_access_interface",
    "sourcePath": "src/main/java/com/scholarmatch/usecase/data_access_interface/VerificationEmailSenderDataAccessInterface.java",
    "extends": [],
    "implements": [],
    "attributes": [],
    "operations": [
      {
        "name": "requestVerificationCode",
        "visibility": "+",
        "static": false,
        "abstract": true,
        "constructor": false,
        "returnType": "void",
        "parameters": [
          {
            "name": "email",
            "type": "String"
          }
        ]
      }
    ]
  }
};
