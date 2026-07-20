package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.style.Icons;
import com.scholarmatch.frameworks.gui.style.Theme;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A JLayeredPane wrapper that lets a small, self-dismissing "toast" banner float over
 * whatever main content it holds, anchored to the top-right corner.
 *
 * <p>Used for the "You matched with X!" notification: match formation happens while the
 * Recommend tab is showing, but LoadMatchesView/ChatView (the two views that actually
 * display matches) only refresh when the user navigates to them. This overlay wraps the
 * always-mounted MainLayoutView shell so the toast can appear immediately regardless of
 * which tab is open, instead of the user having to discover the new match by switching
 * tabs.
 */
public final class MatchToastOverlay extends JLayeredPane {

    private static final int MARGIN = 20;
    private static final int VISIBLE_MS = 5000;
    private static final int SLIDE_STEP_MS = 15;
    private static final int SLIDE_STEP_PX = 18;
    private static final int ARC = 14;

    private JPanel activeToast;
    private Timer activeTimer;
    private Component content;

    /**
     * Constructs an empty MatchToastOverlay; call #setContent(Component) to fill it.
     *
     * <p>Deliberately leaves the pane's layout manager at JLayeredPane's default (none):
     * {@link #showToast} relies on {@code add(comp, JLayeredPane.POPUP_LAYER)} to place the
     * toast above the content, and that Integer layer constraint gets forwarded straight to
     * whatever LayoutManager2 is installed — a real one (e.g. BorderLayout) rejects it with
     * "constraint must be a string (or null)". With no layout manager, content and toast are
     * positioned manually instead, via #layoutChildren.
     */
    public MatchToastOverlay() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                layoutChildren();
            }
        });
    }

    /**
     * Sets the main content this overlay displays, beneath any toast.
     *
     * @param content the panel to fill the overlay with
     */
    public void setContent(final Component content) {
        this.content = content;
        add(content, JLayeredPane.DEFAULT_LAYER);
        layoutChildren();
    }

    private void layoutChildren() {
        if (this.content != null) {
            this.content.setBounds(0, 0, getWidth(), getHeight());
            // setBounds() alone only marks content invalid; with no LayoutManager driving
            // this pane, nothing else schedules the follow-up pass that would actually
            // arrange content's own children (navBar/contentColumn) inside its new size --
            // validate() forces that now instead of leaving them stuck at their stale sizes.
            if (this.content instanceof Container container) {
                container.validate();
            }
        }
        repositionActiveToast();
    }

    /**
     * Shows a toast banner in the top-right corner with the given text: slides in from
     * above, holds for a few seconds, then slides back out and removes itself.
     *
     * <p>Replaces any toast already showing, so a rapid string of matches doesn't stack
     * banners on top of each other.
     *
     * @param text the message to display, e.g. "You matched with Ada Lovelace!"
     */
    public void showToast(final String text) {
        if (this.activeTimer != null) {
            this.activeTimer.stop();
        }
        if (this.activeToast != null) {
            remove(this.activeToast);
            this.activeToast = null;
        }

        final JPanel toast = buildToastPanel(text);
        final Dimension pref = toast.getPreferredSize();
        toast.setSize(pref);
        final int targetX = Math.max(MARGIN, getWidth() - pref.width - MARGIN);
        toast.setLocation(targetX, -pref.height);

        add(toast, JLayeredPane.POPUP_LAYER);
        this.activeToast = toast;
        moveToFront(toast);
        revalidate();
        repaint();

        slideIn(toast, targetX);
    }

    private void slideIn(final JPanel toast, final int targetX) {
        final Timer timer = new Timer(SLIDE_STEP_MS, null);
        timer.addActionListener(e -> {
            final int y = Math.min(MARGIN, toast.getY() + SLIDE_STEP_PX);
            toast.setLocation(targetX, y);
            repaint();
            if (y >= MARGIN) {
                timer.stop();
                scheduleHold(toast, targetX);
            }
        });
        this.activeTimer = timer;
        timer.start();
    }

    private void scheduleHold(final JPanel toast, final int targetX) {
        final Timer holdTimer = new Timer(VISIBLE_MS, e -> slideOut(toast, targetX));
        holdTimer.setRepeats(false);
        this.activeTimer = holdTimer;
        holdTimer.start();
    }

    private void slideOut(final JPanel toast, final int targetX) {
        final Timer timer = new Timer(SLIDE_STEP_MS, null);
        timer.addActionListener(e -> {
            final int y = toast.getY() - SLIDE_STEP_PX;
            toast.setLocation(targetX, y);
            repaint();
            if (y <= -toast.getHeight()) {
                timer.stop();
                if (toast == this.activeToast) {
                    remove(toast);
                    this.activeToast = null;
                    this.activeTimer = null;
                    revalidate();
                    repaint();
                }
            }
        });
        this.activeTimer = timer;
        timer.start();
    }

    private void repositionActiveToast() {
        if (this.activeToast == null) {
            return;
        }
        final int targetX = Math.max(MARGIN, getWidth() - this.activeToast.getWidth() - MARGIN);
        this.activeToast.setLocation(targetX, this.activeToast.getY());
    }

    private JPanel buildToastPanel(final String text) {
        final JPanel panel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(final Graphics g) {
                final Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.WARNING_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
                g2.setColor(Theme.WARNING_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(12, 16, 12, 18));

        final JLabel icon = new JLabel(Icons.of(FontAwesomeSolid.HEART, 18, Theme.WARNING_FG));
        final JLabel label = new JLabel(text);
        label.setForeground(Theme.WARNING_FG);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        panel.add(icon, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
