package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.LoadMatchesController;
import com.scholarmatch.interface_adapter.controller.LoadMessageController;
import com.scholarmatch.interface_adapter.controller.SendMessageController;
import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.usecase.dto.MessageData;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.load_matches.LoadMatchesInputBoundary;
import com.scholarmatch.usecase.load_message.LoadMessageInputBoundary;
import com.scholarmatch.usecase.send_message.SendMessageInputBoundary;

import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ChatViewTest {

    @Test
    void testMatchesSelectionPollingSendingMessagesAndCleanup() throws Exception {
        final SendMessageInputBoundary send = mock(SendMessageInputBoundary.class);
        final LoadMessageInputBoundary loadMessages = mock(LoadMessageInputBoundary.class);
        final LoadMatchesInputBoundary loadMatches = mock(LoadMatchesInputBoundary.class);
        final ChatViewModel chatVm = new ChatViewModel();
        final LoadMatchesViewModel matchesVm = new LoadMatchesViewModel();
        final CurrentUserProvider currentUser = new CurrentUserProvider();
        currentUser.setCurrentUserId("me");
        SwingUtilities.invokeAndWait(() -> {
            final ChatView view = new ChatView(
                    new SendMessageController(send), new LoadMessageController(loadMessages),
                    new LoadMatchesController(loadMatches), chatVm, matchesVm, currentUser);
            final JButton sendButton = button(view, "Send");
            final JTextField message = SwingTestSupport.find(view, JTextField.class, 0);
            sendButton.doClick();
            for (final var listener : message.getActionListeners()) {
                listener.actionPerformed(new ActionEvent(message, ActionEvent.ACTION_PERFORMED, "enter"));
            }
            fireTimer(view);

            matchesVm.getMatchedUsers().setAll(List.of(user("partner", "Ada", "Lovelace")));
            button(view, "Ada Lovelace").doClick();
            fireTimer(view);
            message.setText(" ");
            sendButton.doClick();
            message.setText("  Hello <Ada> & all  ");
            for (final var listener : message.getActionListeners()) {
                listener.actionPerformed(new ActionEvent(message, ActionEvent.ACTION_PERFORMED, "enter"));
            }

            final List<MessageData> messages = List.of(
                    new MessageData("m1", "me", "partner", "Mine <&>", LocalDateTime.now()),
                    new MessageData("m2", "partner", "me", "Theirs", LocalDateTime.now()));
            chatVm.getMessages().setAll(messages);
            chatVm.getMessages().setAll(messages);
            chatVm.setErrorMessage("error");
            view.removeNotify();
        });
        verify(loadMatches).execute();
        verify(loadMessages, atLeastOnce()).execute(any());
        verify(send).execute(any());
    }

    private static JButton button(final ChatView view, final String text) {
        return SwingTestSupport.findAll(view, JButton.class).stream()
                .filter(button -> text.equals(button.getText())).findFirst().orElseThrow();
    }

    private static void fireTimer(final ChatView view) {
        for (final Timer timer : findTimers(view)) {
            for (final var listener : timer.getActionListeners()) {
                listener.actionPerformed(new ActionEvent(timer, ActionEvent.ACTION_PERFORMED, "poll"));
            }
        }
    }

    private static List<Timer> findTimers(final ChatView view) {
        try {
            final var field = ChatView.class.getDeclaredField("pollTimer");
            field.setAccessible(true);
            return List.of((Timer) field.get(view));
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static UserData user(final String id, final String first, final String last) {
        return new UserData(
                id, first, last, "", "", null, null, null, null, "", "", null, null,
                List.of(), List.of(), List.of(), null, null);
    }
}
