package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.DeleteAccountController;
import com.scholarmatch.interface_adapter.view_model.DeleteAccountViewModel;
import com.scholarmatch.usecase.delete_account.DeleteAccountInputBoundary;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class NavigationBarTest {

    @Test
    void testEachToggleInvokesListenerWithItsOwnTarget() {
        final List<String> selections = new ArrayList<>();
        final NavigationBar navBar = new NavigationBar(
            selections::add, () -> { },
            new DeleteAccountController(mock(DeleteAccountInputBoundary.class)), new DeleteAccountViewModel());

        final List<JToggleButton> toggles = SwingTestSupport.findAll(navBar, JToggleButton.class);
        assertEquals(4, toggles.size());
        for (final JToggleButton toggle : toggles) {
            toggle.doClick();
        }

        assertEquals(List.of("recommend", "matched", "chat", "profile"), selections);
    }

    @Test
    void testLogoutButtonInvokesOnLogoutCallback() {
        final AtomicInteger logoutCount = new AtomicInteger();
        final NavigationBar navBar = new NavigationBar(
            target -> { }, logoutCount::incrementAndGet,
            new DeleteAccountController(mock(DeleteAccountInputBoundary.class)), new DeleteAccountViewModel());

        SwingTestSupport.find(navBar, JButton.class, 0).doClick();

        assertEquals(1, logoutCount.get());
    }

    @Test
    void testConfirmingDeleteAccountDialogCallsController() {
        final DeleteAccountInputBoundary interactor = mock(DeleteAccountInputBoundary.class);
        final NavigationBar navBar = new NavigationBar(
            target -> { }, () -> { },
            new DeleteAccountController(interactor), new DeleteAccountViewModel());

        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            optionPane.when(() -> JOptionPane.showConfirmDialog(any(), any(), any(), anyInt(), anyInt()))
                .thenReturn(JOptionPane.YES_OPTION);

            SwingTestSupport.find(navBar, JButton.class, 1).doClick();
        }

        verify(interactor).execute();
    }

    @Test
    void testDecliningDeleteAccountDialogDoesNotCallController() {
        final DeleteAccountInputBoundary interactor = mock(DeleteAccountInputBoundary.class);
        final NavigationBar navBar = new NavigationBar(
            target -> { }, () -> { },
            new DeleteAccountController(interactor), new DeleteAccountViewModel());

        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            optionPane.when(() -> JOptionPane.showConfirmDialog(any(), any(), any(), anyInt(), anyInt()))
                .thenReturn(JOptionPane.NO_OPTION);

            SwingTestSupport.find(navBar, JButton.class, 1).doClick();
        }

        verify(interactor, never()).execute();
    }

    @Test
    void testDeleteAccountFailureShowsErrorDialog() throws Exception {
        final DeleteAccountViewModel deleteAccountViewModel = new DeleteAccountViewModel();
        new NavigationBar(
            target -> { }, () -> { },
            new DeleteAccountController(mock(DeleteAccountInputBoundary.class)), deleteAccountViewModel);

        // ObservableValue#set() only notifies listeners synchronously when called from the EDT
        // (otherwise it defers via invokeLater); run the whole mock-and-verify on the EDT so the
        // static mock registered here is the one still active when the listener actually fires.
        SwingUtilities.invokeAndWait(() -> {
            try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
                deleteAccountViewModel.setErrorMessage("Could not delete account");

                optionPane.verify(() -> JOptionPane.showMessageDialog(
                    any(), eq("Could not delete account"), eq("Delete Account Failed"),
                    eq(JOptionPane.ERROR_MESSAGE)));
            }
        });
    }
}
