package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.data_access_object.CurrentUserProvider;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.LoadMatchesController;
import com.scholarmatch.interface_adapter.controller.LoadMessageController;
import com.scholarmatch.interface_adapter.controller.SendMessageController;
import com.scholarmatch.interface_adapter.view_model.ChatViewModel;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.usecase.dto.MessageData;
import com.scholarmatch.usecase.dto.UserData;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Panel for chatting with mutually matched scholars.
 *
 * <p>The left column lists every user the current user has matched with (bound to the
 * shared LoadMatchesViewModel, the same list backing the "View Matched" tab).
 * Selecting one loads the full conversation history via LoadMessageController and
 * displays it on the right; sending a message appends it to the same view immediately
 * once the server confirms it. Chat is only possible with mutual matches — the server
 * (and the offline fallback) reject anything else, surfaced here via
 * ChatViewModel#errorMessageProperty().
 */
public final class ChatView extends JPanel {

    private static final int LIST_WIDTH = 240;
    private static final int BUBBLE_WIDTH = 280;
    private static final int POLL_INTERVAL_MS = 3000;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("MMM d, HH:mm", Locale.ENGLISH);

    private final SendMessageController sendMessageController;
    private final LoadMessageController loadMessageController;
    private final ChatViewModel chatViewModel;
    private final LoadMatchesViewModel loadMatchesViewModel;
    private final String currentUserId;
    private final Timer pollTimer;

    private final JPanel matchList;
    private final JPanel messageList;
    private final JScrollPane messageScroll;
    private final JLabel conversationTitle;
    private final JTextField messageField;
    private final JButton sendButton;

    private UserData selectedPartner;
    private List<String> renderedMessageIds = List.of();

    /**
     * Constructs the ChatView.
     *
     * @param sendMessageController  sends a chat message to the selected match
     * @param loadMessageController  loads the conversation history with the selected match
     * @param loadMatchesController  loads the current user's confirmed matches
     * @param chatViewModel          observable state for the currently open conversation
     * @param loadMatchesViewModel   observable state for the matched-users list
     * @param currentUserProvider    the shared session, used to tell "mine" messages from "theirs"
     */
    public ChatView(
        final SendMessageController sendMessageController,
        final LoadMessageController loadMessageController,
        final LoadMatchesController loadMatchesController,
        final ChatViewModel chatViewModel,
        final LoadMatchesViewModel loadMatchesViewModel,
        final CurrentUserProvider currentUserProvider) {
        super(new BorderLayout());
        this.sendMessageController = sendMessageController;
        this.loadMessageController = loadMessageController;
        this.chatViewModel = chatViewModel;
        this.loadMatchesViewModel = loadMatchesViewModel;
        this.currentUserId = currentUserProvider.getCurrentUserId();
        setBackground(Theme.BG_DEFAULT);

        this.matchList = new JPanel();
        this.matchList.setLayout(new BoxLayout(this.matchList, BoxLayout.Y_AXIS));
        this.matchList.setOpaque(false);
        this.matchList.setBorder(new EmptyBorder(4, 8, 8, 8));

        final JLabel matchesTitle = new JLabel("Matches");
        matchesTitle.setForeground(Theme.FG_DEFAULT);
        matchesTitle.setFont(matchesTitle.getFont().deriveFont(Font.BOLD, 14f));
        matchesTitle.setBorder(new EmptyBorder(12, 16, 8, 16));

        final JScrollPane matchScroll = new JScrollPane(this.matchList);
        matchScroll.setBorder(null);
        matchScroll.getViewport().setBackground(Theme.BG_DEFAULT);
        matchScroll.getVerticalScrollBar().setUnitIncrement(16);

        final JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BORDER_DEFAULT));
        leftPanel.setPreferredSize(new Dimension(LIST_WIDTH, 0));
        leftPanel.add(matchesTitle, BorderLayout.NORTH);
        leftPanel.add(matchScroll, BorderLayout.CENTER);

        this.conversationTitle = new JLabel("Select a match to start chatting");
        this.conversationTitle.setForeground(Theme.FG_DEFAULT);
        this.conversationTitle.setFont(this.conversationTitle.getFont().deriveFont(Font.BOLD, 15f));
        this.conversationTitle.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_DEFAULT),
            new EmptyBorder(14, 16, 14, 16)));

        this.messageList = new JPanel();
        this.messageList.setLayout(new BoxLayout(this.messageList, BoxLayout.Y_AXIS));
        this.messageList.setOpaque(false);
        this.messageList.setBorder(new EmptyBorder(8, 16, 8, 16));

        this.messageScroll = new JScrollPane(this.messageList);
        this.messageScroll.setBorder(null);
        this.messageScroll.getViewport().setBackground(Theme.BG_DEFAULT);
        this.messageScroll.getVerticalScrollBar().setUnitIncrement(16);

        final JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Theme.DANGER_FG);
        errorLabel.setFont(errorLabel.getFont().deriveFont(11f));
        errorLabel.setBorder(new EmptyBorder(4, 16, 0, 16));
        chatViewModel.errorMessageProperty().addListener(errorLabel::setText);

        this.messageField = new JTextField();
        this.messageField.setEnabled(false);
        this.messageField.setMargin(new Insets(8, 8, 8, 8));
        this.messageField.setPreferredSize(
            new Dimension(this.messageField.getPreferredSize().width, 40));
        this.sendButton = new JButton("Send");
        Buttons.accent(this.sendButton);
        this.sendButton.setEnabled(false);
        this.sendButton.addActionListener(evt -> sendCurrentMessage());
        this.messageField.addActionListener(evt -> sendCurrentMessage());

        final JPanel composeBar = new JPanel(new BorderLayout(8, 0));
        composeBar.setOpaque(false);
        composeBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER_DEFAULT),
            new EmptyBorder(10, 16, 10, 16)));
        composeBar.add(this.messageField, BorderLayout.CENTER);
        composeBar.add(this.sendButton, BorderLayout.EAST);

        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(errorLabel, BorderLayout.NORTH);
        bottomPanel.add(composeBar, BorderLayout.SOUTH);

        final JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(this.conversationTitle, BorderLayout.NORTH);
        rightPanel.add(this.messageScroll, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        rebuildMatchList();
        loadMatchesViewModel.getMatchedUsers().addListener(this::rebuildMatchList);
        chatViewModel.getMessages().addListener(this::rebuildMessageList);

        loadMatchesController.execute();

        // The server has no push mechanism (no WebSocket/SSE), so an open conversation
        // otherwise only ever refreshes on user action (selecting it, sending a message).
        // Polling is the cheap stand-in: reload the open conversation on an interval so
        // incoming messages show up without the user having to leave and re-enter the tab.
        this.pollTimer = new Timer(POLL_INTERVAL_MS, evt -> {
            if (this.selectedPartner != null) {
                this.loadMessageController.loadMessages(this.selectedPartner.getUserId());
            }
        });
        this.pollTimer.start();
    }

    /**
     * Stops the polling timer once this view is torn down (e.g. the user switches away
     * from the Chat tab) so it doesn't keep polling in the background after MainLayoutView
     * has already replaced it with a fresh ChatView instance.
     */
    @Override
    public void removeNotify() {
        super.removeNotify();
        this.pollTimer.stop();
    }

    private void rebuildMatchList() {
        this.matchList.removeAll();
        for (final UserData match : this.loadMatchesViewModel.getMatchedUsers()) {
            this.matchList.add(buildMatchRow(match));
            this.matchList.add(Box.createVerticalStrut(4));
        }
        this.matchList.revalidate();
        this.matchList.repaint();
    }

    private JComponent buildMatchRow(final UserData match) {
        final JButton row = new JButton(match.getFirstName() + " " + match.getLastName());
        row.setHorizontalAlignment(SwingConstants.LEFT);
        Buttons.outlined(row);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, row.getPreferredSize().height));
        row.addActionListener(evt -> selectConversation(match));
        return row;
    }

    private void selectConversation(final UserData partner) {
        this.selectedPartner = partner;
        this.conversationTitle.setText(partner.getFirstName() + " " + partner.getLastName());
        this.messageField.setEnabled(true);
        this.sendButton.setEnabled(true);
        this.loadMessageController.loadMessages(partner.getUserId());
    }

    private void sendCurrentMessage() {
        if (this.selectedPartner == null) {
            return;
        }
        final String text = this.messageField.getText().trim();
        if (text.isEmpty()) {
            return;
        }
        this.sendMessageController.sendMessage(this.selectedPartner.getUserId(), text);
        this.messageField.setText("");
    }

    private void rebuildMessageList() {
        final List<String> currentIds = this.chatViewModel.getMessages().stream()
            .map(MessageData::getMessageId)
            .toList();
        // The poll timer re-triggers this listener every few seconds even when nothing
        // changed; rebuilding on every tick would reset the user's scroll position back to
        // the bottom while they're reading older messages, so skip it when the message IDs
        // are exactly the ones already on screen.
        if (currentIds.equals(this.renderedMessageIds)) {
            return;
        }
        this.renderedMessageIds = currentIds;

        this.messageList.removeAll();
        for (final MessageData message : this.chatViewModel.getMessages()) {
            this.messageList.add(buildMessageBubble(message));
            this.messageList.add(Box.createVerticalStrut(6));
        }
        this.messageList.revalidate();
        this.messageList.repaint();
        SwingUtilities.invokeLater(() -> {
            final JScrollBar vertical = this.messageScroll.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private JPanel buildMessageBubble(final MessageData message) {
        final boolean mine = message.getSenderId().equals(this.currentUserId);

        final JPanel bubble = new JPanel();
        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setOpaque(true);
        bubble.setBackground(mine ? Theme.ACCENT : Theme.BG_SUBTLE);
        bubble.setBorder(new EmptyBorder(8, 10, 8, 10));

        final JLabel textLabel = new JLabel(
            "<html><body style='width:" + BUBBLE_WIDTH + "px'>" + escapeHtml(message.getContent()) + "</body></html>");
        textLabel.setForeground(mine ? Theme.FG_EMPHASIS : Theme.FG_DEFAULT);
        textLabel.setFont(textLabel.getFont().deriveFont(13f));
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bubble.add(textLabel);

        final JLabel timeLabel = new JLabel(message.getSentAt().format(TIME_FORMAT));
        timeLabel.setForeground(mine ? Theme.FG_EMPHASIS : Theme.FG_SUBTLE);
        timeLabel.setFont(timeLabel.getFont().deriveFont(10f));
        timeLabel.setBorder(new EmptyBorder(4, 0, 0, 0));
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bubble.add(timeLabel);

        final JPanel row = new JPanel(new FlowLayout(mine ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, bubble.getPreferredSize().height + 12));
        row.add(bubble);
        return row;
    }

    private String escapeHtml(final String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
