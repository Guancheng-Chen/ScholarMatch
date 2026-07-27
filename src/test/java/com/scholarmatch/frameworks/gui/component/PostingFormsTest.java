package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.usecase.dto.PostingData;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostingFormsTest {

    @Test
    void testApplicationFormDisplaysDetailsValidatesAndSubmitsExactMessageOnce()
            throws Exception {
        final AtomicInteger calls = new AtomicInteger();
        final AtomicReference<String> postingId = new AtomicReference<>();
        final AtomicReference<String> submittedMessage = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            final ApplyToPostingPanel panel = new ApplyToPostingPanel(
                    posting(), (id, message) -> {
                        calls.incrementAndGet();
                        postingId.set(id);
                        submittedMessage.set(message);
                    }, () -> { });
            assertEquals("Apply to Posting",
                    named(panel, JLabel.class, "applyHeading").getText());
            assertEquals("Research Assistant",
                    named(panel, JLabel.class, "applyPostingTitle").getText());
            assertTrue(named(panel, JLabel.class, "applyPostingOwner").getText()
                    .contains("owner-1"));
            assertTrue(named(panel, JLabel.class, "applyPostingMetadata").getText()
                    .contains("Machine learning"));
            assertEquals("A detailed posting description.",
                    named(panel, JTextArea.class, "applyPostingDescription").getText());

            final JTextArea message =
                    named(panel, JTextArea.class, "applicationMessageInput");
            assertTrue(message.getRows() >= 8);
            assertTrue(message.getLineWrap());
            assertTrue(message.getWrapStyleWord());
            final JScrollPane scroll =
                    named(panel, JScrollPane.class, "applicationMessageScroll");
            assertTrue(scroll.getPreferredSize().width >= 520);
            assertTrue(scroll.getPreferredSize().height >= 180);

            button(panel, "Submit Application").doClick();
            assertEquals(0, calls.get());
            assertTrue(named(panel, JLabel.class, "applicationValidation")
                    .getText().contains("Please write"));
            final String exact = "  I study usable research tools.\nI would value collaborating.  ";
            message.setText(exact);
            assertTrue(named(panel, JLabel.class, "applicationCharacterCount")
                    .getText().startsWith(String.valueOf(exact.length())));
            button(panel, "Submit Application").doClick();
        });
        assertEquals(1, calls.get());
        assertEquals("posting-1", postingId.get());
        assertEquals("  I study usable research tools.\nI would value collaborating.  ",
                submittedMessage.get());
    }

    @Test
    void testApplicationCancelDoesNotSubmitAndScrollableFormSupportsNarrowWidth()
            throws Exception {
        final AtomicInteger submits = new AtomicInteger();
        final AtomicInteger cancels = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            final ApplyToPostingPanel panel = new ApplyToPostingPanel(
                    posting(), (id, message) -> submits.incrementAndGet(),
                    cancels::incrementAndGet);
            panel.setSize(400, 520);
            panel.doLayout();
            assertTrue(SwingTestSupport.findAll(panel, JScrollPane.class).size() >= 2);
            final JTextArea message =
                    named(panel, JTextArea.class, "applicationMessageInput");
            for (final DocumentListener listener
                    : ((javax.swing.text.AbstractDocument) message.getDocument())
                            .getDocumentListeners()) {
                if (listener.getClass().getName().contains("ApplyToPostingPanel")) {
                    listener.changedUpdate(null);
                }
            }
            button(panel, "Cancel").doClick();

            final PostingData unlimited = new PostingData(
                    "posting-2", "owner-2", "Unlimited", "Description",
                    ResearchField.MACHINE_LEARNING, CollaborationType.CO_AUTHOR,
                    null, 0, 0, LocalDateTime.now(), PostingStatus.OPEN,
                    false, true, List.of());
            final ApplyToPostingPanel unlimitedPanel =
                    new ApplyToPostingPanel(unlimited, (id, messageText) -> { }, () -> { });
            assertTrue(named(unlimitedPanel, JLabel.class, "applyPostingMetadata")
                    .getText().contains("unlimited capacity"));
        });
        assertEquals(0, submits.get());
        assertEquals(1, cancels.get());
    }

    @Test
    void testCreatePostingFormValidatesAllBranchesAndSubmits() throws Exception {
        final AtomicReference<CreatePostingPanel.Submission> submission =
                new AtomicReference<>();
        final AtomicInteger cancels = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            final CreatePostingPanel panel =
                    new CreatePostingPanel(submission::set, cancels::incrementAndGet);
            final JTextField title =
                    named(panel, JTextField.class, "postingTitleInput");
            final JTextArea description =
                    named(panel, JTextArea.class, "postingDescriptionInput");
            final JTextField capacity =
                    named(panel, JTextField.class, "postingCapacityInput");
            final JButton create = button(panel, "Create Posting");

            create.doClick();
            assertTrue(validation(panel).contains("title"));
            title.setText("  New Collaboration  ");
            create.doClick();
            assertTrue(validation(panel).contains("Description"));
            description.setText("  A complete description.  ");
            capacity.setText("bad");
            create.doClick();
            assertTrue(validation(panel).contains("positive whole number"));
            capacity.setText("0");
            create.doClick();
            assertTrue(validation(panel).contains("positive whole number"));
            capacity.setText("3");
            named(panel, JComboBox.class, "postingResearchFieldInput")
                    .setSelectedItem(ResearchField.MACHINE_LEARNING);
            named(panel, JComboBox.class, "postingCollaborationTypeInput")
                    .setSelectedItem(CollaborationType.CO_AUTHOR);
            create.doClick();
            button(panel, "Cancel").doClick();
            panel.setSize(430, 540);
            panel.doLayout();
        });
        assertEquals("New Collaboration", submission.get().title());
        assertEquals("A complete description.", submission.get().description());
        assertEquals(3, submission.get().capacity());
        assertEquals(1, cancels.get());
    }

    @Test
    void testCreatePostingAllowsUnlimitedCapacity() throws Exception {
        final AtomicReference<CreatePostingPanel.Submission> submission =
                new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            final CreatePostingPanel panel =
                    new CreatePostingPanel(submission::set, () -> { });
            named(panel, JTextField.class, "postingTitleInput").setText("Title");
            named(panel, JTextArea.class, "postingDescriptionInput")
                    .setText("Description");
            button(panel, "Create Posting").doClick();
        });
        assertNull(submission.get().capacity());
    }

    private static String validation(final CreatePostingPanel panel) {
        return named(panel, JLabel.class, "createPostingValidation").getText();
    }

    private static JButton button(
            final java.awt.Container root,
            final String text) {
        return SwingTestSupport.findAll(root, JButton.class).stream()
                .filter(button -> text.equals(button.getText()))
                .findFirst().orElseThrow();
    }

    private static <T extends java.awt.Component> T named(
            final java.awt.Container root,
            final Class<T> type,
            final String name) {
        return SwingTestSupport.findAll(root, type).stream()
                .filter(component -> name.equals(component.getName()))
                .findFirst().orElseThrow();
    }

    private static PostingData posting() {
        return new PostingData(
                "posting-1", "owner-1", "Research Assistant",
                "A detailed posting description.",
                ResearchField.MACHINE_LEARNING, CollaborationType.CO_AUTHOR,
                4, 2, 1, LocalDateTime.now(), PostingStatus.OPEN,
                false, true, List.of());
    }
}
