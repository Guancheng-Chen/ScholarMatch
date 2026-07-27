package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.usecase.dto.PostingData;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.util.function.BiConsumer;

/**
 * Modal host for the reusable application form.
 */
public final class ApplyToPostingDialog extends JDialog {

    private static final Dimension SIZE = new Dimension(720, 720);
    private static final Dimension MINIMUM_SIZE = new Dimension(620, 600);

    private ApplyToPostingDialog(
            final Window owner,
            final PostingData posting,
            final BiConsumer<String, String> onSubmit) {
        super(owner, "Apply to Posting", Dialog.ModalityType.APPLICATION_MODAL);
        final ApplyToPostingPanel panel = new ApplyToPostingPanel(
                posting,
                (postingId, message) -> {
                    onSubmit.accept(postingId, message);
                    dispose();
                },
                this::dispose);
        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(MINIMUM_SIZE);
        setSize(SIZE);
        setLocationRelativeTo(owner);
    }

    /**
     * Opens a centered modal application dialog.
     *
     * @param parent component used to locate the owner window
     * @param posting posting being applied to
     * @param onSubmit existing application callback
     */
    public static void showDialog(
            final Component parent,
            final PostingData posting,
            final BiConsumer<String, String> onSubmit) {
        final Window owner = SwingUtilities.getWindowAncestor(parent);
        new ApplyToPostingDialog(owner, posting, onSubmit).setVisible(true);
    }
}
