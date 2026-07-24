package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.AcceptApplicationController;
import com.scholarmatch.interface_adapter.controller.ApplyToPostingController;
import com.scholarmatch.interface_adapter.controller.ClosePostingController;
import com.scholarmatch.interface_adapter.controller.CreatePostingController;
import com.scholarmatch.interface_adapter.controller.DeclineApplicationController;
import com.scholarmatch.interface_adapter.controller.LoadMyApplicationsController;
import com.scholarmatch.interface_adapter.controller.LoadPostingsController;
import com.scholarmatch.interface_adapter.view_model.MyApplicationsViewModel;
import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.interface_adapter.view_model.OpportunitiesViewModel;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInputBoundary;
import com.scholarmatch.usecase.apply_to_posting.ApplyToPostingInputBoundary;
import com.scholarmatch.usecase.close_posting.ClosePostingInputBoundary;
import com.scholarmatch.usecase.create_posting.CreatePostingInputBoundary;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInputBoundary;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.load_my_applications.LoadMyApplicationsInputBoundary;
import com.scholarmatch.usecase.load_postings.LoadPostingsInputBoundary;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PostingViewsTest {

    @Test
    void testOpportunitiesEmptyNonEmptyMessagesApplyAndRemoval() throws Exception {
        final LoadPostingsInputBoundary load = mock(LoadPostingsInputBoundary.class);
        final ApplyToPostingInputBoundary apply = mock(ApplyToPostingInputBoundary.class);
        SwingUtilities.invokeAndWait(() -> {
            final OpportunitiesViewModel vm = new OpportunitiesViewModel();
            final OpportunitiesView view = new OpportunitiesView(
                    new LoadPostingsController(load), new ApplyToPostingController(apply), vm);
            vm.setPostings(List.of(posting("p1", true, null, List.of())));
            try (MockedStatic<JOptionPane> dialogs = mockStatic(JOptionPane.class)) {
                dialogs.when(() -> JOptionPane.showInputDialog(
                        any(), any(), any(), anyInt())).thenReturn("hello");
                SwingTestSupport.find(view, JButton.class, 0).doClick();
                messages(vm, dialogs);
            }
            vm.setPostings(List.of());
            view.removeNotify();
        });
        verify(load).execute(any());
        verify(apply).execute(any());
    }

    @Test
    void testMyApplicationsTitlesErrorsAndRemoval() throws Exception {
        final LoadMyApplicationsInputBoundary load = mock(LoadMyApplicationsInputBoundary.class);
        SwingUtilities.invokeAndWait(() -> {
            final MyApplicationsViewModel vm = new MyApplicationsViewModel();
            final MyApplicationsView view =
                    new MyApplicationsView(new LoadMyApplicationsController(load), vm);
            vm.setApplications(List.of(
                    application("a1", ""), application("a2", "Named Posting")));
            try (MockedStatic<JOptionPane> dialogs = mockStatic(JOptionPane.class)) {
                vm.setErrorMessage(null);
                vm.setErrorMessage(" ");
                vm.setErrorMessage("load failed");
                dialogs.verify(() -> JOptionPane.showMessageDialog(
                        any(), org.mockito.ArgumentMatchers.eq("load failed"),
                        org.mockito.ArgumentMatchers.eq("Load Applications Failed"),
                        org.mockito.ArgumentMatchers.eq(JOptionPane.ERROR_MESSAGE)));
            }
            view.removeNotify();
        });
        verify(load).execute();
    }

    @Test
    void testMyPostingsRenderingRefreshCloseCreateValidationAndRemoval() throws Exception {
        final CreatePostingInputBoundary create = mock(CreatePostingInputBoundary.class);
        final LoadPostingsInputBoundary load = mock(LoadPostingsInputBoundary.class);
        final ClosePostingInputBoundary close = mock(ClosePostingInputBoundary.class);
        SwingUtilities.invokeAndWait(() -> {
            final MyPostingsViewModel vm = new MyPostingsViewModel();
            final MyPostingsView view = new MyPostingsView(
                    new CreatePostingController(create), new LoadPostingsController(load),
                    new ClosePostingController(close),
                    new AcceptApplicationController(mock(AcceptApplicationInputBoundary.class)),
                    new DeclineApplicationController(mock(DeclineApplicationInputBoundary.class)), vm);
            final PostingData active = posting("active", true, null, List.of());
            final PostingData closed = posting("closed", false, 2, List.of());
            vm.setPostings(List.of(active, closed));
            vm.setApplicationsByPostingId(Map.of("active", List.of(application("a1", "Applicant"))));
            vm.requestRefresh();

            try (MockedStatic<JOptionPane> dialogs = mockStatic(JOptionPane.class)) {
                dialogs.when(() -> JOptionPane.showConfirmDialog(
                        any(), any(), org.mockito.ArgumentMatchers.eq("Close Posting"),
                        anyInt(), anyInt()))
                        .thenReturn(JOptionPane.NO_OPTION, JOptionPane.YES_OPTION);
                final JButton closeButton = button(view, "Close Posting");
                closeButton.doClick();
                closeButton.doClick();

                dialogs.when(() -> JOptionPane.showConfirmDialog(
                        any(), any(), org.mockito.ArgumentMatchers.eq("New Posting"), anyInt()))
                        .thenAnswer(invocation -> {
                            fillForm((JPanel) invocation.getArgument(1), "", "");
                            return JOptionPane.OK_OPTION;
                        })
                        .thenAnswer(invocation -> {
                            fillForm((JPanel) invocation.getArgument(1), "Title", "");
                            return JOptionPane.OK_OPTION;
                        })
                        .thenAnswer(invocation -> {
                            fillForm((JPanel) invocation.getArgument(1), "Title", "0");
                            return JOptionPane.OK_OPTION;
                        })
                        .thenAnswer(invocation -> {
                            fillForm((JPanel) invocation.getArgument(1), "Title", "bad");
                            return JOptionPane.OK_OPTION;
                        })
                        .thenAnswer(invocation -> {
                            fillForm((JPanel) invocation.getArgument(1), "Title", "3");
                            return JOptionPane.OK_OPTION;
                        })
                        .thenReturn(JOptionPane.CANCEL_OPTION);
                final JButton newButton = button(view, "New Posting");
                for (int i = 0; i < 6; i++) {
                    newButton.doClick();
                }
                vm.setErrorMessage(null);
                vm.setErrorMessage(" ");
                vm.setErrorMessage("error");
                vm.setSuccessMessage(null);
                vm.setSuccessMessage(" ");
                vm.setSuccessMessage("success");
            }
            view.removeNotify();
        });
        verify(load, times(2)).execute(any());
        verify(close).execute(any());
        verify(create, times(2)).execute(any());
    }

    private static void messages(
            final OpportunitiesViewModel vm, final MockedStatic<JOptionPane> dialogs) {
        vm.setErrorMessage(null);
        vm.setErrorMessage(" ");
        vm.setSuccessMessage(null);
        vm.setSuccessMessage(" ");
        vm.setErrorMessage("error");
        vm.setSuccessMessage("success");
        dialogs.verify(() -> JOptionPane.showMessageDialog(
                any(), org.mockito.ArgumentMatchers.eq("error"),
                org.mockito.ArgumentMatchers.eq("Apply Failed"),
                org.mockito.ArgumentMatchers.eq(JOptionPane.ERROR_MESSAGE)));
        dialogs.verify(() -> JOptionPane.showMessageDialog(
                any(), org.mockito.ArgumentMatchers.eq("success"),
                org.mockito.ArgumentMatchers.eq("Opportunities"),
                org.mockito.ArgumentMatchers.eq(JOptionPane.INFORMATION_MESSAGE)));
    }

    private static void fillForm(final JPanel form, final String title, final String capacity) {
        final List<JTextField> fields = SwingTestSupport.findAll(form, JTextField.class);
        fields.get(0).setText(title);
        fields.get(1).setText(capacity);
    }

    private static JButton button(final JPanel view, final String text) {
        return SwingTestSupport.findAll(view, JButton.class).stream()
                .filter(button -> text.equals(button.getText())).findFirst().orElseThrow();
    }

    private static PostingData posting(
            final String id, final boolean active, final Integer capacity,
            final List<PostingApplicationData> applications) {
        return new PostingData(
                id, "owner", "Title", "Description", ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR, capacity, applications.size(), 1,
                LocalDateTime.now(), active ? PostingStatus.OPEN : PostingStatus.CLOSED,
                false, active, applications);
    }

    private static PostingApplicationData application(final String id, final String title) {
        return new PostingApplicationData(
                id, "p1", "user", "message", PostingApplicationStatus.PENDING,
                LocalDateTime.now(), title, "Ada");
    }
}
