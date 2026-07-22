package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.frameworks.gui.style.CenteringScrollPanel;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.LoadMyApplicationsController;
import com.scholarmatch.interface_adapter.view_model.MyApplicationsViewModel;
import com.scholarmatch.usecase.dto.PostingApplicationData;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.function.Consumer;

/**
 * Read-only list of the current user's posting applications.
 */
public final class MyApplicationsView extends JPanel {

    private final MyApplicationsViewModel viewModel;
    private final JPanel rows = new JPanel();
    private final Runnable applicationsListener;
    private final Consumer<String> errorListener;

    /**
     * Constructs the My Applications view.
     *
     * @param loadController controller used to load the current user's applications
     * @param viewModel view model containing the current user's applications
     */

    public MyApplicationsView(
            final LoadMyApplicationsController loadController,
            final MyApplicationsViewModel viewModel) {
        super(new BorderLayout());
        this.viewModel = viewModel;
        this.applicationsListener = this::rebuild;
        this.errorListener = message -> {
            if (message != null && !message.isBlank()) {
                JOptionPane.showMessageDialog(
                        this, message, "Load Applications Failed", JOptionPane.ERROR_MESSAGE);
            }
        };
        setBackground(Theme.BG_DEFAULT);
        final JLabel title = new JLabel("My Applications");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setBorder(new EmptyBorder(18, 22, 12, 22));
        this.rows.setLayout(new BoxLayout(this.rows, BoxLayout.Y_AXIS));
        this.rows.setOpaque(false);
        final CenteringScrollPanel holder = new CenteringScrollPanel(this.rows);
        holder.setBorder(new EmptyBorder(20, 28, 28, 28));
        final JScrollPane scrollPane = new JScrollPane(holder);
        scrollPane.setBorder(null);
        viewModel.getApplications().addListener(this.applicationsListener);
        viewModel.errorMessageProperty().addListener(this.errorListener);
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        rebuild();
        loadController.loadMyApplications();
    }

    private void rebuild() {
        this.rows.removeAll();
        for (final PostingApplicationData application : this.viewModel.getApplications()) {
            final RoundedPanel row = new RoundedPanel(Theme.CARD_RADIUS, 18);
            row.setLayout(new BorderLayout());
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            final String title = application.getPostingTitle().isBlank()
                    ? "Posting " + application.getPostingId() : application.getPostingTitle();
            row.add(new JLabel(title), BorderLayout.WEST);
            row.add(new JLabel(application.getStatus().name()), BorderLayout.EAST);
            this.rows.add(row);
            this.rows.add(Box.createVerticalStrut(10));
        }
        this.rows.revalidate();
        this.rows.repaint();
    }

    @Override
    public void removeNotify() {
        this.viewModel.getApplications().removeListener(this.applicationsListener);
        this.viewModel.errorMessageProperty().removeListener(this.errorListener);
        super.removeNotify();
    }
}
