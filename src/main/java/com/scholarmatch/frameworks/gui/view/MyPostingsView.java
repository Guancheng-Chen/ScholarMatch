package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.component.ConfirmationDialog;
import com.scholarmatch.frameworks.gui.component.CreatePostingDialog;
import com.scholarmatch.frameworks.gui.component.OwnedPostingCard;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.AcceptApplicationController;
import com.scholarmatch.interface_adapter.controller.ClosePostingController;
import com.scholarmatch.interface_adapter.controller.CreatePostingController;
import com.scholarmatch.interface_adapter.controller.DeclineApplicationController;
import com.scholarmatch.interface_adapter.controller.LoadPostingsController;
import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.load_postings.PostingScope;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.function.Consumer;

/**
 * Screen for creating postings and reviewing their applicants.
 */
public final class MyPostingsView extends JPanel {

    private final CreatePostingController createController;
    private final LoadPostingsController loadController;
    private final ClosePostingController closeController;
    private final AcceptApplicationController acceptController;
    private final DeclineApplicationController declineController;
    private final MyPostingsViewModel viewModel;
    private final JPanel cardList = new JPanel();
    private final CenteringScrollPanel holder;
    private final Runnable postingsListener;
    private final Consumer<String> errorListener;
    private final Consumer<String> successListener;
    private final Consumer<Integer> refreshListener;
    private final CreateDialogLauncher createDialogLauncher;
    private final CloseDialogLauncher closeDialogLauncher;

    public MyPostingsView(
            final CreatePostingController createController,
            final LoadPostingsController loadController,
            final ClosePostingController closeController,
            final AcceptApplicationController acceptController,
            final DeclineApplicationController declineController,
            final MyPostingsViewModel viewModel) {
        this(createController, loadController, closeController, acceptController,
                declineController, viewModel, CreatePostingDialog::showDialog,
                (parent, posting, onConfirm) -> ConfirmationDialog.showDialog(
                        parent,
                        "Close Posting",
                        "Close this posting? New applications will no longer be accepted.",
                        "Close Posting",
                        onConfirm));
    }

    MyPostingsView(
            final CreatePostingController createController,
            final LoadPostingsController loadController,
            final ClosePostingController closeController,
            final AcceptApplicationController acceptController,
            final DeclineApplicationController declineController,
            final MyPostingsViewModel viewModel,
            final CreateDialogLauncher createDialogLauncher,
            final CloseDialogLauncher closeDialogLauncher) {
        super(new BorderLayout());
        this.createController = createController;
        this.loadController = loadController;
        this.closeController = closeController;
        this.acceptController = acceptController;
        this.declineController = declineController;
        this.viewModel = viewModel;
        this.createDialogLauncher = createDialogLauncher;
        this.closeDialogLauncher = closeDialogLauncher;
        this.postingsListener = this::rebuild;
        this.errorListener = message -> show(message, true);
        this.successListener = message -> show(message, false);
        this.refreshListener = ignored -> this.loadController.loadPostings(PostingScope.MINE);
        setBackground(Theme.BG_DEFAULT);

        final JLabel title = new JLabel("My Postings");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        final JButton newButton = new JButton("New Posting");
        Buttons.accent(newButton);
        newButton.addActionListener(event -> showCreateForm());
        final JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(18, 22, 12, 22));
        header.add(title, BorderLayout.WEST);
        header.add(newButton, BorderLayout.EAST);

        this.cardList.setLayout(new BoxLayout(this.cardList, BoxLayout.Y_AXIS));
        this.cardList.setOpaque(false);
        this.holder = new CenteringScrollPanel(this.cardList);
        this.holder.setBorder(new EmptyBorder(20, 28, 28, 28));
        final JScrollPane scrollPane = new JScrollPane(this.holder);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        viewModel.getPostings().addListener(this.postingsListener);
        viewModel.errorMessageProperty().addListener(this.errorListener);
        viewModel.successMessageProperty().addListener(this.successListener);
        viewModel.refreshRequestProperty().addListener(this.refreshListener);
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        rebuild();
        loadController.loadPostings(PostingScope.MINE);
    }

    private void rebuild() {
        this.cardList.removeAll();
        if (this.viewModel.getPostings().isEmpty()) {
            final JLabel empty = new JLabel(
                    "You have not created a posting yet. Select New Posting to get started.");
            empty.setName("emptyState");
            empty.setForeground(Theme.FG_MUTED);
            empty.setAlignmentX(LEFT_ALIGNMENT);
            this.cardList.add(empty);
        }
        for (final PostingData posting : this.viewModel.getPostings()) {
            final OwnedPostingCard card = new OwnedPostingCard(
                    posting, this.viewModel.getApplicationsFor(posting.getPostingId()),
                    this::closePosting, this.acceptController, this.declineController);
            this.cardList.add(card);
            this.cardList.add(Box.createVerticalStrut(12));
        }
        this.cardList.revalidate();
        this.cardList.repaint();
        this.holder.reflowNow();
    }

    private void showCreateForm() {
        this.createDialogLauncher.open(this, submission ->
                this.createController.createPosting(
                        submission.title(), submission.description(),
                        submission.researchField(), submission.collaborationType(),
                        submission.capacity()));
    }

    private void closePosting(final PostingData posting) {
        this.closeDialogLauncher.open(
                this, posting,
                () -> this.closeController.closePosting(posting.getPostingId()));
    }

    private void show(final String message, final boolean error) {
        if (message != null && !message.isBlank()) {
            JOptionPane.showMessageDialog(
                    this, message, error ? "Posting Failed" : "My Postings",
                    error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void removeNotify() {
        this.viewModel.getPostings().removeListener(this.postingsListener);
        this.viewModel.errorMessageProperty().removeListener(this.errorListener);
        this.viewModel.successMessageProperty().removeListener(this.successListener);
        this.viewModel.refreshRequestProperty().removeListener(this.refreshListener);
        super.removeNotify();
    }

    @FunctionalInterface
    interface CreateDialogLauncher {
        void open(
                java.awt.Component parent,
                Consumer<com.scholarmatch.frameworks.gui.component.CreatePostingPanel.Submission>
                        onSubmit);
    }

    @FunctionalInterface
    interface CloseDialogLauncher {
        void open(java.awt.Component parent, PostingData posting, Runnable onConfirm);
    }
}
