package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.interface_adapter.controller.AcceptApplicationController;
import com.scholarmatch.interface_adapter.controller.DeclineApplicationController;
import com.scholarmatch.usecase.dto.PostingApplicationData;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * One applicant row inside the owner's posting list.
 */
public final class PostingApplicationRow extends JPanel {

    public PostingApplicationRow(
            final PostingApplicationData application,
            final AcceptApplicationController acceptController,
            final DeclineApplicationController declineController) {
        super(new BorderLayout(12, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        final String applicant = application.getApplicantName().isBlank()
                ? application.getApplicantUserId() : application.getApplicantName();
        final JLabel summary = new JLabel(
                applicant + " — " + application.getMessage()
                        + " [" + application.getStatus() + "]");
        summary.setForeground(Theme.FG_DEFAULT);

        final JButton acceptButton = new JButton("Accept");
        Buttons.success(acceptButton);
        final JButton declineButton = new JButton("Decline");
        Buttons.danger(declineButton);
        final boolean pending = application.getStatus() == PostingApplicationStatus.PENDING;
        acceptButton.setEnabled(pending);
        declineButton.setEnabled(pending);
        acceptButton.addActionListener(event -> acceptController.accept(application.getApplicationId()));
        declineButton.addActionListener(event -> declineController.decline(application.getApplicationId()));

        final JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        actions.setOpaque(false);
        actions.add(acceptButton);
        actions.add(declineButton);

        add(summary, BorderLayout.CENTER);
        add(actions, BorderLayout.EAST);
    }
}
