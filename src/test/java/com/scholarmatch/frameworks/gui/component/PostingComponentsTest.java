package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.accept_application.AcceptApplicationController;
import com.scholarmatch.interface_adapter.decline_application.DeclineApplicationController;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInputBoundary;
import com.scholarmatch.usecase.accept_application.AcceptApplicationInputData;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInputBoundary;
import com.scholarmatch.usecase.decline_application.DeclineApplicationInputData;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
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
            final JButton acceptButton = named(row, JButton.class, "acceptButton");
            final JButton declineButton = named(row, JButton.class, "declineButton");
            assertTrue(acceptButton.isEnabled());
            assertTrue(declineButton.isEnabled());
            assertEquals("Ada", named(row, JLabel.class, "applicantName").getText());
            assertEquals("Please consider me",
                    named(row, JTextArea.class, "applicationMessage").getText());
            row.reflow(400);
            assertTrue(row.getLayout() instanceof javax.swing.BoxLayout);
            row.reflow(700);
            assertTrue(row.getLayout() instanceof java.awt.BorderLayout);
            acceptButton.doClick();
            declineButton.doClick();
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
            assertFalse(named(row, JButton.class, "acceptButton").isEnabled());
            assertFalse(named(row, JButton.class, "declineButton").isEnabled());
            assertEquals("user-1", named(row, JLabel.class, "applicantName").getText());
        });
    }

    @Test
    void testActivePostingOpensApplicationFormWithPostingAndCallback() throws Exception {
        final AtomicReference<String> postingId = new AtomicReference<>();
        final AtomicReference<String> message = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            final PostingCard card = new PostingCard(
                    posting(null, true, false), (id, text) -> {
                        postingId.set(id);
                        message.set(text);
                    }, (parent, selectedPosting, callback) -> {
                        assertEquals("posting-1", selectedPosting.getPostingId());
                        callback.accept(selectedPosting.getPostingId(), "Full application");
                    });
            assertEquals("Research Assistant",
                    named(card, JLabel.class, "postingTitle").getText());
            assertEquals("Description",
                    named(card, JTextArea.class, "postingDescription").getText());
            assertEquals("Posted by Unknown user",
                    named(card, JLabel.class, "postingOwner").getText());
            assertTrue(named(card, JLabel.class, "postingMetadata").getText()
                    .contains("unlimited"));
            card.reflow(320);
            assertEquals(320, card.getPreferredSize().width);
            button(card).doClick();
        });
        assertEquals("posting-1", postingId.get());
        assertEquals("Full application", message.get());
    }

    @Test
    void testApplicationCardDisplaysFallbackMessageStatusAndReflows() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final ApplicationCard fallback = new ApplicationCard(application(
                    PostingApplicationStatus.PENDING, "Ada"));
            assertEquals("Title", named(fallback, JLabel.class, "postingTitle").getText());
            assertEquals("Pending",
                    named(fallback, JLabel.class, "applicationStatus").getText());
            assertEquals("Please consider me",
                    named(fallback, JTextArea.class, "applicationMessage").getText());
            fallback.reflow(400);
            assertTrue(SwingTestSupport.findAll(fallback, javax.swing.JPanel.class).stream()
                    .anyMatch(panel -> panel.getLayout() instanceof javax.swing.BoxLayout));
            fallback.reflow(700);

            final PostingApplicationData unnamedPosting = new PostingApplicationData(
                    "a2", "posting-2", "user-2", "Long message",
                    PostingApplicationStatus.REJECTED, LocalDateTime.now(), "", "");
            final ApplicationCard second = new ApplicationCard(unnamedPosting);
            assertEquals("Posting posting-2",
                    named(second, JLabel.class, "postingTitle").getText());
        });
    }

    @Test
    void testOwnedPostingCardShowsDetailsApplicationsEmptyStateAndClose() throws Exception {
        final AtomicReference<String> closed = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            final AcceptApplicationController accept = new AcceptApplicationController(
                    mock(AcceptApplicationInputBoundary.class));
            final DeclineApplicationController decline = new DeclineApplicationController(
                    mock(DeclineApplicationInputBoundary.class));
            final OwnedPostingCard active = new OwnedPostingCard(
                    posting(2, true, false),
                    List.of(application(PostingApplicationStatus.PENDING, "Ada")),
                    value -> closed.set(value.getPostingId()), accept, decline);
            assertEquals("Research Assistant",
                    named(active, JLabel.class, "postingTitle").getText());
            assertEquals("Description",
                    named(active, JTextArea.class, "postingDescription").getText());
            assertTrue(named(active, JLabel.class, "applicationsTitle").getText().contains("1"));
            named(active, JButton.class, "closePostingButton").doClick();
            active.reflow(400);

            final OwnedPostingCard closedCard = new OwnedPostingCard(
                    posting(null, false, false), List.of(), value -> { }, accept, decline);
            assertEquals("No applications received yet.",
                    named(closedCard, JLabel.class, "emptyApplications").getText());
            assertFalse(SwingTestSupport.findAll(closedCard, JButton.class).stream()
                    .anyMatch(button -> "Close Posting".equals(button.getText())));
        });
        assertEquals("posting-1", closed.get());
    }

    @Test
    void testInactiveLabelsCoverFullAndClosed() throws Exception {
        final AtomicReference<String> applied = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            final JButton full = button(new PostingCard(posting(2, false, true), (id, text) -> { }));
            final JButton closed = button(new PostingCard(posting(2, false, false), (id, text) -> { }));
            assertEquals("Full", full.getText());
            assertEquals("Closed", closed.getText());
            assertFalse(full.isEnabled());
            assertFalse(closed.isEnabled());
        });
        assertEquals(null, applied.get());
    }

    @Test
    void testPostingScreensShowVerifiedOwnerBadge() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final PostingData posting = new PostingData(
                    "posting-1", "owner-1", "Research Assistant",
                    "Description", ResearchField.MACHINE_LEARNING,
                    CollaborationType.CO_AUTHOR, 2, 1, 1,
                    LocalDateTime.now(), PostingStatus.OPEN,
                    false, true, "Ada Lovelace", true, List.of());
            final PostingCard opportunity =
                    new PostingCard(posting, (id, text) -> { });
            final OwnedPostingCard owned = new OwnedPostingCard(
                    posting, List.of(), value -> { },
                    new AcceptApplicationController(
                            mock(AcceptApplicationInputBoundary.class)),
                    new DeclineApplicationController(
                            mock(DeclineApplicationInputBoundary.class)));
            final PostingApplicationData application =
                    new PostingApplicationData(
                            "a-1", "posting-1", "user-1", "Message",
                            PostingApplicationStatus.PENDING,
                            LocalDateTime.now(), "Title", "Applicant",
                            "Ada Lovelace", true);
            final ApplicationCard submitted =
                    new ApplicationCard(application);

            assertEquals("Verified university email",
                    named(opportunity, JLabel.class,
                            "academicVerificationBadge").getText());
            assertEquals("Verified university email",
                    named(owned, JLabel.class,
                            "academicVerificationBadge").getText());
            assertEquals("Verified university email",
                    named(submitted, JLabel.class,
                            "academicVerificationBadge").getText());
            assertTrue(named(opportunity, JLabel.class, "postingOwner")
                    .getText().contains("Ada Lovelace"));
        });
    }

    @Test
    void testOwnerSummaryIsTextOnlyAndHandlesMissingNames() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final PostingOwnerSummary named = new PostingOwnerSummary(
                    " Ada Lovelace ", true);
            final PostingOwnerSummary unnamed = new PostingOwnerSummary(
                    " ", false);
            final PostingOwnerSummary nullnamed = new PostingOwnerSummary(
                    null, false);

            assertEquals("Posted by Ada Lovelace",
                    named(named, JLabel.class, "postingOwner").getText());
            assertEquals("Verified university email",
                    named(named, JLabel.class,
                            "academicVerificationBadge").getText());
            assertEquals("Posted by Unknown user",
                    named(unnamed, JLabel.class, "postingOwner").getText());
            assertEquals("Posted by Unknown user",
                    named(nullnamed, JLabel.class, "postingOwner").getText());
            assertTrue(SwingTestSupport.findAll(named, JButton.class).isEmpty());
            assertTrue(SwingTestSupport.findAll(unnamed, JButton.class).isEmpty());
            assertFalse(SwingTestSupport.findAll(unnamed, JLabel.class).stream()
                    .anyMatch(label -> "academicVerificationBadge"
                            .equals(label.getName())));
        });
    }

    private static JButton button(final PostingCard card) {
        return SwingTestSupport.find(card, JButton.class, 0);
    }

    private static <T extends java.awt.Component> T named(
            final java.awt.Container root, final Class<T> type, final String name) {
        return SwingTestSupport.findAll(root, type).stream()
                .filter(component -> name.equals(component.getName()))
                .findFirst().orElseThrow();
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
