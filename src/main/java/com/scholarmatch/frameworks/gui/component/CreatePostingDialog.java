package com.scholarmatch.frameworks.gui.component;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.util.function.Consumer;

/**
 * Modal host for the reusable create-posting form.
 */
public final class CreatePostingDialog extends JDialog {

    private CreatePostingDialog(
            final Window owner,
            final Consumer<CreatePostingPanel.Submission> onSubmit) {
        super(owner, "Create a Posting", Dialog.ModalityType.APPLICATION_MODAL);
        setContentPane(new CreatePostingPanel(submission -> {
            onSubmit.accept(submission);
            dispose();
        }, this::dispose));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(620, 620));
        setSize(new Dimension(720, 720));
        setLocationRelativeTo(owner);
    }

    /**
     * Opens a centered modal posting form.
     *
     * @param parent component used to find the owner window
     * @param onSubmit valid submission callback
     */
    public static void showDialog(
            final Component parent,
            final Consumer<CreatePostingPanel.Submission> onSubmit) {
        final Window owner = SwingUtilities.getWindowAncestor(parent);
        new CreatePostingDialog(owner, onSubmit).setVisible(true);
    }
}
