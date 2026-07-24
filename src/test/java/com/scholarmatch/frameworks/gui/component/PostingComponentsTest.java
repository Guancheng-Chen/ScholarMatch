package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.AcceptApplicationController;
import com.scholarmatch.interface_adapter.controller.DeclineApplicationController;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInputBoundary;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInputData;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInputBoundary;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInputData;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

class PostingComponentsTest {

    @Test
    void testPendingApplicationUsesNameAndInvokesBothControllers() throws Exception {
        final AcceptApplicationInputBoundary accept = mock(AcceptApplicationInputBoundary.class);
        final DeclineApplicationInputBoundary decline = mock(DeclineApplicationInputBoundary.class);
        SwingUtilities.invokeAndWait(() -> {
            final PostingApplicationRow row = new PostingApplicationRow(
                    application(PostingApplicationStatus.PENDING, "Ada"),
                    new AcceptApplicationController(accept),
                    new DeclineApplicationController(decline));
            final List<JButton> buttons = SwingTestSupport.findAll(row, JButton.class);
            assertTrue(buttons.get(0).isEnabled());
            assertTrue(buttons.get(1).isEnabled());
            assertTrue(SwingTestSupport.find(row, JLabel.class, 0).getText().startsWith("Ada —"));
            buttons.forEach(JButton::doClick);
        });
        final ArgumentCaptor<AcceptApplicationInputData> accepted =
                ArgumentCaptor.forClass(AcceptApplicationInputData.class);
        verify(accept).execute(accepted.capture());
        assertEquals("application-1", accepted.getValue().applicationId());
        final ArgumentCaptor<DeclineApplicationInputData> declined =
                ArgumentCaptor.forClass(DeclineApplicationInputData.class);
        verify(decline).execute(declined.capture());
        assertEquals("application-1", declined.getValue().applicationId());
    }

    @Test
    void testReviewedApplicationFallsBackToUserIdAndDisablesActions() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final PostingApplicationRow row = new PostingApplicationRow(
                    application(PostingApplicationStatus.ACCEPTED, " "),
                    new AcceptApplicationController(mock(AcceptApplicationInputBoundary.class)),
                    new DeclineApplicationController(mock(DeclineApplicationInputBoundary.class)));
            final List<JButton> buttons = SwingTestSupport.findAll(row, JButton.class);
            assertFalse(buttons.get(0).isEnabled());
            assertFalse(buttons.get(1).isEnabled());
            assertTrue(SwingTestSupport.find(row, JLabel.class, 0).getText().startsWith("user-1 —"));
        });
    }

    @Test
    void testActivePostingSubmitsDialogMessageIncludingBlank() throws Exception {
        final AtomicReference<String> postingId = new AtomicReference<>();
        final AtomicReference<String> message = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            try (MockedStatic<JOptionPane> dialogs = mockStatic(JOptionPane.class)) {
                dialogs.when(() -> JOptionPane.showInputDialog(
                        any(), any(), any(), org.mockito.ArgumentMatchers.anyInt())).thenReturn("");
                final PostingCard card = new PostingCard(
                        posting(null, true, false), (id, text) -> {
                            postingId.set(id);
                            message.set(text);
                        });
                button(card).doClick();
            }
        });
        assertEquals("posting-1", postingId.get());
        assertEquals("", message.get());
    }

    @Test
    void testCancelledDialogDoesNotApplyAndInactiveLabelsCoverFullAndClosed() throws Exception {
        final AtomicReference<String> applied = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            try (MockedStatic<JOptionPane> dialogs = mockStatic(JOptionPane.class)) {
                dialogs.when(() -> JOptionPane.showInputDialog(
                        any(), any(), any(), org.mockito.ArgumentMatchers.anyInt())).thenReturn(null);
                final PostingCard active = new PostingCard(
                        posting(2, true, false), (id, text) -> applied.set(id));
                button(active).doClick();
            }
            final JButton full = button(new PostingCard(posting(2, false, true), (id, text) -> { }));
            final JButton closed = button(new PostingCard(posting(2, false, false), (id, text) -> { }));
            assertEquals("Full", full.getText());
            assertEquals("Closed", closed.getText());
            assertFalse(full.isEnabled());
            assertFalse(closed.isEnabled());
        });
        assertEquals(null, applied.get());
    }

    private static JButton button(final PostingCard card) {
        return SwingTestSupport.find(card, JButton.class, 0);
    }

    private static PostingApplicationData application(
            final PostingApplicationStatus status, final String name) {
        return new PostingApplicationData(
                "application-1", "posting-1", "user-1", "Please consider me",
                status, LocalDateTime.now(), "Title", name);
    }

    private static PostingData posting(
            final Integer capacity, final boolean active, final boolean full) {
        return new PostingData(
                "posting-1", "owner-1", "Research Assistant", "Description",
                ResearchField.MACHINE_LEARNING, CollaborationType.CO_AUTHOR,
                capacity, 1, 1, LocalDateTime.now(), PostingStatus.OPEN,
                full, active, List.of());
    }
}
