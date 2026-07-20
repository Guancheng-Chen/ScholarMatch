package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.component.RecommendUserCard;
import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.ConnectController;
import com.scholarmatch.interface_adapter.controller.DislikeController;
import com.scholarmatch.interface_adapter.controller.RecommendController;
import com.scholarmatch.interface_adapter.controller.SkipController;
import com.scholarmatch.interface_adapter.view_model.RecommendViewModel;
import com.scholarmatch.usecase.dto.UserData;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 * Discover-users screen showing a stack of user cards.
 *
 * <p>The top card represents the current recommendation, with its full profile
 * (everything except email and password). Clicking Dislike permanently excludes
 * this user; clicking Skip moves past them without excluding them; clicking
 * Connect records a connect action. The card is wrapped in a scrollable,
 * horizontally-centered panel since a fully-detailed card can be taller than
 * the window.
 */
public final class RecommendView extends JPanel {

    private final RecommendController recommendController;
    private final ConnectController connectController;
    private final DislikeController dislikeController;
    private final SkipController skipController;
    private final RecommendViewModel viewModel;

    /**
     * Constructs the RecommendView.
     *
     * @param recommendController refreshes the recommendation list
     * @param connectController   records a connect action
     * @param dislikeController   records a dislike action
     * @param skipController      records a skip action
     * @param viewModel           the observable card-stack state
     */
    public RecommendView(
            final RecommendController recommendController,
            final ConnectController connectController,
            final DislikeController dislikeController,
            final SkipController skipController,
            final RecommendViewModel viewModel) {
        super(new BorderLayout());
        this.recommendController = recommendController;
        this.connectController = connectController;
        this.dislikeController = dislikeController;
        this.skipController = skipController;
        this.viewModel = viewModel;
        setBackground(Theme.BG_DEFAULT);

        viewModel.getCardStack().addListener(this::renderTopCard);
        viewModel.errorMessageProperty().addListener(message -> renderTopCard());

        recommendController.execute();
    }

    private void renderTopCard() {
        removeAll();

        final String errorMessage = this.viewModel.errorMessageProperty().get();
        if (errorMessage != null && !errorMessage.isBlank()) {
            add(emptyStateLabel(errorMessage), BorderLayout.CENTER);
            revalidate();
            repaint();
            return;
        }

        if (this.viewModel.getCardStack().isEmpty()) {
            add(emptyStateLabel("No more recommendations right now."), BorderLayout.CENTER);
            revalidate();
            repaint();
            return;
        }

        final UserData top = this.viewModel.getCardStack().get(0);
        final RecommendUserCard card = new RecommendUserCard(top, new RecommendUserCard.ConnectListener() {
            @Override
            public void onDislike() {
                dislikeController.dislike(top.getUserId());
                viewModel.excludeUser(top.getUserId());
                viewModel.getCardStack().remove(0);
            }

            @Override
            public void onSkip() {
                skipController.skip(top.getUserId());
                // Deliberately no excludeUser() call here — skip is not persisted, so a
                // future recommendation fetch is free to resurface this user.
                viewModel.getCardStack().remove(0);
            }

            @Override
            public void onConnect() {
                connectController.connect(top.getUserId(), top);
                viewModel.excludeUser(top.getUserId());
                viewModel.getCardStack().remove(0);
            }
        });

        final CenteringScrollPanel centeringPanel = new CenteringScrollPanel(card);
        centeringPanel.setBorder(new EmptyBorder(32, 32, 32, 32));
        final JScrollPane scrollPane = new JScrollPane(centeringPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.BG_DEFAULT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
        // The panel's width isn't known until Swing finishes laying out the frame, so the
        // constructor's own size is still 0 here — defer the first reflow past that.
        javax.swing.SwingUtilities.invokeLater(centeringPanel::reflowNow);
    }

    private JLabel emptyStateLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setForeground(Theme.FG_MUTED);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 14f));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }
}
