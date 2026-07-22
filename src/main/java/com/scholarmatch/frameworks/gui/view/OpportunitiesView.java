package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.component.PostingCard;
import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.ApplyToPostingController;
import com.scholarmatch.interface_adapter.controller.LoadPostingsController;
import com.scholarmatch.interface_adapter.view_model.OpportunitiesViewModel;
import com.scholarmatch.usecase.dto.PostingData;
import com.scholarmatch.usecase.load_postings.PostingScope;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.function.Consumer;

/**
 * Screen for browsing and applying to active postings from other users.
 */
public final class OpportunitiesView extends JPanel {

    private final OpportunitiesViewModel viewModel;
    private final ApplyToPostingController applyController;
    private final JPanel cardList = new JPanel();
    private final Runnable postingsListener;
    private final Consumer<String> errorListener;
    private final Consumer<String> successListener;

    public OpportunitiesView(
            final LoadPostingsController loadController,
            final ApplyToPostingController applyController,
            final OpportunitiesViewModel viewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;
        this.applyController = applyController;
        this.postingsListener = this::rebuild;
        this.errorListener = message -> show(message, true);
        this.successListener = message -> show(message, false);
        setBackground(Theme.BG_DEFAULT);

        final JLabel title = new JLabel("Opportunities");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(new EmptyBorder(18, 22, 12, 22));

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
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        rebuild();
        loadController.loadPostings(PostingScope.ALL_ACTIVE);
    }

    private void rebuild() {
        this.cardList.removeAll();
        for (final PostingData posting : this.viewModel.getPostings()) {
            this.cardList.add(new PostingCard(posting, this.applyController::apply));
            this.cardList.add(Box.createVerticalStrut(12));
        }
        this.cardList.revalidate();
        this.cardList.repaint();
    }

    private void show(final String message, final boolean error) {
        if (message != null && !message.isBlank()) {
            JOptionPane.showMessageDialog(
                    this, message, error ? "Apply Failed" : "Opportunities",
                    error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void removeNotify() {
        this.viewModel.getPostings().removeListener(this.postingsListener);
        this.viewModel.errorMessageProperty().removeListener(this.errorListener);
        this.viewModel.successMessageProperty().removeListener(this.successListener);
        super.removeNotify();
    }
}
