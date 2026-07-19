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
