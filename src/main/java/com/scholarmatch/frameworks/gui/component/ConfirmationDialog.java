package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Theme;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

/**
 * Small styled confirmation used by posting workflow actions.
 */
public final class ConfirmationDialog extends JDialog {

    private ConfirmationDialog(
            final Window owner,
            final String title,
            final String message,
            final String confirmText,
            final Runnable onConfirm) {
        super(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
        final JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setBackground(Theme.BG_DEFAULT);
        content.setBorder(BorderFactory.createEmptyBorder(24, 26, 22, 26));
        final JPanel copy = new JPanel();
        copy.setOpaque(false);
        copy.setLayout(new BoxLayout(copy, BoxLayout.Y_AXIS));
        final JLabel heading = new JLabel(title);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 21f));
        heading.setForeground(Theme.FG_DEFAULT);
        final JLabel detail = new JLabel("<html><body style='width: 390px'>"
                + message + "</body></html>");
        detail.setForeground(Theme.FG_MUTED);
        copy.add(heading);
        copy.add(Box.createVerticalStrut(10));
        copy.add(detail);

        final JButton cancel = new JButton("Cancel");
        Buttons.outlined(cancel);
        cancel.addActionListener(event -> dispose());
        final JButton confirm = new JButton(confirmText);
        confirm.setName("confirmActionButton");
        Buttons.danger(confirm);
        confirm.addActionListener(event -> {
            onConfirm.run();
            dispose();
        });
        final JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        actions.add(cancel);
        actions.add(confirm);
        content.add(copy, BorderLayout.CENTER);
        content.add(actions, BorderLayout.SOUTH);
        setContentPane(content);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(480, 230));
        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Opens a styled destructive confirmation.
     *
     * @param parent owner component
     * @param title dialog title
     * @param message explanatory text
     * @param confirmText confirmation button label
     * @param onConfirm callback invoked only on confirmation
     */
    public static void showDialog(
            final Component parent,
            final String title,
            final String message,
            final String confirmText,
            final Runnable onConfirm) {
        final Window owner = SwingUtilities.getWindowAncestor(parent);
        new ConfirmationDialog(owner, title, message, confirmText, onConfirm).setVisible(true);
    }
}
