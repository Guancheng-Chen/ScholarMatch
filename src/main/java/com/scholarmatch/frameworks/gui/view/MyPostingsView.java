package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.component.PostingApplicationRow;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.AcceptApplicationController;
import com.scholarmatch.interface_adapter.controller.ClosePostingController;
import com.scholarmatch.interface_adapter.controller.CreatePostingController;
import com.scholarmatch.interface_adapter.controller.DeclineApplicationController;
import com.scholarmatch.interface_adapter.controller.LoadPostingsController;
import com.scholarmatch.interface_adapter.view_model.MyPostingsViewModel;
import com.scholarmatch.usecase.dto.PostingApplicationData;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.load_postings.PostingScope;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
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
    private final Runnable postingsListener;
    private final Consumer<String> errorListener;
    private final Consumer<String> successListener;
    private final Consumer<Integer> refreshListener;

    public MyPostingsView(
            final CreatePostingController createController,
            final LoadPostingsController loadController,
            final ClosePostingController closeController,
            final AcceptApplicationController acceptController,
            final DeclineApplicationController declineController,
            final MyPostingsViewModel viewModel) {
        super(new BorderLayout());
        this.createController = createController;
        this.loadController = loadController;
        this.closeController = closeController;
        this.acceptController = acceptController;
        this.declineController = declineController;
        this.viewModel = viewModel;
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
        final CenteringScrollPanel holder = new CenteringScrollPanel(this.cardList);
        holder.setBorder(new EmptyBorder(20, 28, 28, 28));
        final JScrollPane scrollPane = new JScrollPane(holder);
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
        for (final PostingData posting : this.viewModel.getPostings()) {
            final RoundedPanel card = new RoundedPanel(Theme.CARD_RADIUS, 18);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            final JLabel title = new JLabel(posting.getTitle() + " - " + posting.getStatus());
            title.setFont(title.getFont().deriveFont(Font.BOLD, 17f));
            title.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(title);
            card.add(Box.createVerticalStrut(8));
            final String accepted = posting.getCapacity() == null
                    ? posting.getAcceptedCount() + " accepted / unlimited capacity"
                    : posting.getAcceptedCount() + " / " + posting.getCapacity() + " accepted";
            final JLabel count = new JLabel(
                    posting.getApplicantCount() + " applicants; " + accepted);
            count.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(count);
            if (posting.isActive()) {
                final JButton closeButton = new JButton("Close Posting");
                Buttons.outlined(closeButton);
                closeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                closeButton.addActionListener(event -> closePosting(posting));
                card.add(Box.createVerticalStrut(8));
                card.add(closeButton);
            }
            for (final PostingApplicationData application
                    : this.viewModel.getApplicationsFor(posting.getPostingId())) {
                card.add(new PostingApplicationRow(
                        application, this.acceptController, this.declineController));
            }
            this.cardList.add(card);
            this.cardList.add(Box.createVerticalStrut(12));
        }
        this.cardList.revalidate();
        this.cardList.repaint();
    }

    private void showCreateForm() {
        final JTextField title = new JTextField();
        final JTextArea description = new JTextArea(4, 28);
        final JComboBox<ResearchField> field = new JComboBox<>(ResearchField.values());
        final JComboBox<CollaborationType> type = new JComboBox<>(CollaborationType.values());
        final JTextField capacity = new JTextField();
        final JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.add(new JLabel("Title"));
        form.add(title);
        form.add(new JLabel("Description"));
        form.add(new JScrollPane(description));
        form.add(new JLabel("Research field"));
        form.add(field);
        form.add(new JLabel("Collaboration type"));
        form.add(type);
        form.add(new JLabel("Team capacity (blank = unlimited)"));
        form.add(capacity);
        if (JOptionPane.showConfirmDialog(
                this, form, "New Posting", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {
            try {
                if (title.getText().isBlank()) {
                    show("Title is required.", true);
                    return;
                }
                final Integer maximum = capacity.getText().isBlank()
                        ? null : Integer.valueOf(capacity.getText().trim());
                if (maximum != null && maximum <= 0) {
                    throw new IllegalArgumentException();
                }
                this.createController.createPosting(
                        title.getText().trim(), description.getText().trim(),
                        (ResearchField) field.getSelectedItem(),
                        (CollaborationType) type.getSelectedItem(), maximum);
            } catch (final IllegalArgumentException exception) {
                show("Team capacity must be a positive whole number.", true);
            }
        }
    }

    private void closePosting(final PostingData posting) {
        final int choice = JOptionPane.showConfirmDialog(
                this, "Close this posting? New applications will no longer be accepted.",
                "Close Posting", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            this.closeController.closePosting(posting.getPostingId());
        }
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
}
