package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.component.MatchedUserCard;
import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.LoadMatchesController;
import com.scholarmatch.interface_adapter.view_model.LoadMatchesViewModel;
import com.scholarmatch.usecase.dto.UserData;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;

/**
 * Panel displaying the current user's mutual matches.
 *
 * <p>Renders a scrollable stack of {@link MatchedUserCard}s, one per matched user.
 * The list is bound to LoadMatchesViewModel#getMatchedUsers() and updates
 * automatically when a new mutual match is detected. On construction, it also
 * triggers LoadMatchesController to fetch matches confirmed in a previous
 * session — not just ones detected live by a connect during this run.
 */
public final class LoadMatchesView extends JPanel {

    private final LoadMatchesViewModel viewModel;
    private final JPanel cardList;
    private CenteringScrollPanel centeringPanel;

    /**
     * Constructs the LoadMatchesView.
     *
     * @param loadMatchesController loads the current user's confirmed matches
     * @param viewModel             the observable matches state
     */
    public LoadMatchesView(final LoadMatchesController loadMatchesController, final LoadMatchesViewModel viewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;
        setBackground(Theme.BG_DEFAULT);

        this.cardList = new JPanel();
        this.cardList.setLayout(new BoxLayout(this.cardList, BoxLayout.Y_AXIS));
        this.cardList.setOpaque(false);
        rebuildCards();

        viewModel.getMatchedUsers().addListener(this::rebuildCards);

        final JLabel titleLabel = new JLabel("Matched Users");
        titleLabel.setForeground(Theme.FG_DEFAULT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        titleLabel.setBorder(new EmptyBorder(16, 16, 12, 16));

        final JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Theme.DANGER_FG);
        errorLabel.setFont(errorLabel.getFont().deriveFont(11f));
        errorLabel.setBorder(new EmptyBorder(0, 16, 8, 16));
        viewModel.errorMessageProperty().addListener(errorLabel::setText);

        final JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(errorLabel, BorderLayout.CENTER);

        this.centeringPanel = new CenteringScrollPanel(this.cardList);
        this.centeringPanel.setBorder(new EmptyBorder(32, 32, 32, 32));
        final JScrollPane scrollPane = new JScrollPane(this.centeringPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.BG_DEFAULT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadMatchesController.execute();
        // The panel's width isn't known until Swing finishes laying out the frame, so the
        // constructor's own size is still 0 here — defer the first reflow past that.
        SwingUtilities.invokeLater(this.centeringPanel::reflowNow);
    }

    private void rebuildCards() {
        this.cardList.removeAll();
        for (final UserData user : this.viewModel.getMatchedUsers()) {
            this.cardList.add(new MatchedUserCard(user));
            this.cardList.add(Box.createVerticalStrut(12));
        }
        this.cardList.revalidate();
        this.cardList.repaint();
        // New cards default to their max width until reflowed — align them with the
        // current viewport width immediately instead of waiting for the next resize.
        if (this.centeringPanel != null) {
            this.centeringPanel.reflowNow();
        }
    }
}
