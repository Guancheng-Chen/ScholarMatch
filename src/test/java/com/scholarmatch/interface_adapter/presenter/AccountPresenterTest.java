package com.scholarmatch.interface_adapter.presenter;

import com.scholarmatch.interface_adapter.view_model.DeleteAccountViewModel;
import com.scholarmatch.interface_adapter.view_model.AccountSettingsViewModel;
import com.scholarmatch.interface_adapter.view_model.LoginViewModel;
import com.scholarmatch.interface_adapter.view_model.LogoutViewModel;
import com.scholarmatch.interface_adapter.view_model.RegisterViewModel;
import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.change_email.ChangeEmailOutputData;
import com.scholarmatch.usecase.change_password.ChangePasswordOutputData;
import com.scholarmatch.usecase.load_profile.LoadProfileOutputData;
import com.scholarmatch.usecase.login.LoginOutputData;
import com.scholarmatch.usecase.logout.LogoutOutputData;
import com.scholarmatch.usecase.register.RegisterOutputData;
import com.scholarmatch.usecase.request_email_verification.RequestEmailVerificationOutputData;
import com.scholarmatch.usecase.update_profile.UpdateProfileOutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountPresenterTest {

    @Test
    void testDeleteAccountReportsSuccessAndFailure() {
        final LogoutViewModel logoutViewModel = new LogoutViewModel();
        final DeleteAccountViewModel deleteViewModel = new DeleteAccountViewModel();
        final DeleteAccountPresenter presenter =
                new DeleteAccountPresenter(logoutViewModel, deleteViewModel);

        presenter.prepareFailView("cannot delete");
        presenter.prepareSuccessView();

        assertEquals("cannot delete", deleteViewModel.errorMessageProperty().get());
        assertTrue(logoutViewModel.loggedOutProperty().get());
    }

    @Test
    void testLoadProfileReportsSuccessAndFailure() {
        final UpdateProfileViewModel viewModel = new UpdateProfileViewModel();
        final LoadProfilePresenter presenter = new LoadProfilePresenter(viewModel);
        final UserData user = mock(UserData.class);

        presenter.prepareFailView("cannot load");
        presenter.prepareSuccessView(new LoadProfileOutputData(user));

        assertEquals("cannot load", viewModel.errorMessageProperty().get());
        assertSame(user, viewModel.currentUserProperty().get());
    }

    @Test
    void testLoginReportsSuccessAndFailure() {
        final LoginViewModel viewModel = new LoginViewModel();
        final LoginPresenter presenter = new LoginPresenter(viewModel);

        presenter.prepareFailView("invalid credentials");
        presenter.prepareSuccessView(new LoginOutputData("user-1", "Ada Lovelace"));

        assertEquals("invalid credentials", viewModel.errorMessageProperty().get());
        assertEquals("user-1", viewModel.loggedInUserIdProperty().get());
    }

    @Test
    void testLogoutReportsSuccess() {
        final LogoutViewModel viewModel = new LogoutViewModel();

        new LogoutPresenter(viewModel).prepareSuccessView(new LogoutOutputData());

        assertTrue(viewModel.loggedOutProperty().get());
    }

    @Test
    void testRegisterReportsSuccessAndFailure() {
        final RegisterViewModel viewModel = new RegisterViewModel();
        final RegisterPresenter presenter = new RegisterPresenter(viewModel);

        presenter.prepareFailView("registration failed");
        assertEquals("registration failed", viewModel.errorMessageProperty().get());
        assertFalse(viewModel.registrationSucceededProperty().get());

        presenter.prepareSuccessView(new RegisterOutputData("user-1", "Ada"));
        assertEquals("Welcome, Ada!", viewModel.successMessageProperty().get());
        assertTrue(viewModel.registrationSucceededProperty().get());
    }

    @Test
    void testVerificationRequestReportsSuccessAndFailure() {
        final RegisterViewModel viewModel = new RegisterViewModel();
        final RequestEmailVerificationPresenter presenter =
                new RequestEmailVerificationPresenter(viewModel);

        presenter.prepareFailView("cannot send");
        assertEquals("", viewModel.verificationMessageProperty().get());
        assertEquals("cannot send", viewModel.verificationErrorProperty().get());

        presenter.prepareSuccessView(
                new RequestEmailVerificationOutputData("ada@example.edu"));
        assertEquals("Verification code sent to ada@example.edu.",
                viewModel.verificationMessageProperty().get());
        assertEquals("", viewModel.verificationErrorProperty().get());
    }

    @Test
    void testUpdateProfileReportsSuccessAndFailure() {
        final UpdateProfileViewModel viewModel = new UpdateProfileViewModel();
        final UpdateProfilePresenter presenter = new UpdateProfilePresenter(viewModel);

        presenter.prepareFailView("cannot save");
        assertEquals("cannot save", viewModel.errorMessageProperty().get());

        presenter.prepareSuccessView(new UpdateProfileOutputData("user-1"));
        assertEquals("", viewModel.errorMessageProperty().get());
        assertEquals("Profile saved successfully.",
                viewModel.saveSuccessMessageProperty().get());
    }

    @Test
    void testAccountSettingsReportsEachOutcome() {
        final AccountSettingsViewModel viewModel = new AccountSettingsViewModel();
        final UpdateProfileViewModel profileViewModel = new UpdateProfileViewModel();
        final AccountSettingsPresenter presenter =
                new AccountSettingsPresenter(viewModel, profileViewModel);
        final UserData user = mock(UserData.class);
        when(user.getEmail()).thenReturn("new@example.com");

        presenter.prepareFailView("cannot update");
        assertEquals("cannot update", viewModel.errorMessageProperty().get());

        presenter.prepareSuccessView(
                new RequestEmailVerificationOutputData("new@example.com"));
        assertEquals("", viewModel.errorMessageProperty().get());
        assertEquals("Verification code sent to new@example.com.",
                viewModel.successMessageProperty().get());

        presenter.prepareSuccessView(new ChangeEmailOutputData(user));
        assertEquals("new@example.com", viewModel.currentEmailProperty().get());
        assertSame(user, profileViewModel.currentUserProperty().get());
        assertEquals("Email changed successfully.",
                viewModel.successMessageProperty().get());

        presenter.prepareSuccessView(new ChangePasswordOutputData());
        assertEquals("Password changed successfully.",
                viewModel.successMessageProperty().get());
    }
}
