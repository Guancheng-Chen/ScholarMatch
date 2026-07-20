package com.scholarmatch.frameworks.gui.style;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * A panel meant as the view of a javax.swing.JScrollPane: it holds one child (typically
 * a card built with RoundedPanel, or a JPanel stacking several of them), always centers it
 * horizontally, and tracks the viewport's width so the child re-centers as the window is
 * resized — the Swing equivalent of a JavaFX ScrollPane with fitToWidth(true) wrapping a
 * StackPane.
 *
 * <p>Also notifies any {@link Reflowable} descendant (a card whose own layout depends on
 * the width it's given, e.g. switching to a stacked column layout once too narrow) whenever
 * this panel's width changes, so cards shrink/reflow with the window instead of being
 * clipped by a fixed preferred size.
 */
public final class CenteringScrollPanel extends JPanel implements Scrollable {

    private static final int UNIT_INCREMENT = 16;

    /**
     * Constructs a CenteringScrollPanel wrapping a single child.
     *
     * @param child the component to center, e.g. a form card
     */
    public CenteringScrollPanel(final JComponent child) {
        super(new GridBagLayout());
        setOpaque(false);
        add(child);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent evt) {
                notifyReflowable(CenteringScrollPanel.this);
            }
        });
    }

    /**
     * Recalculates and applies this panel's current available width to every Reflowable
     * descendant. Call once after construction (in addition to letting resize events drive
     * it) so the initial layout — which may already be narrower than a card's max width —
     * reflows correctly before the first resize.
     */
    public void reflowNow() {
        notifyReflowable(this);
    }

    private void notifyReflowable(final Container root) {
        final Insets insets = getInsets();
        final int availableWidth = Math.max(0, getWidth() - insets.left - insets.right);
        if (availableWidth == 0) {
            return;
        }
        for (final Component child : root.getComponents()) {
            if (child instanceof Reflowable reflowable) {
                reflowable.reflow(availableWidth);
            }
            if (child instanceof Container container) {
                notifyReflowable(container);
            }
        }
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
        return UNIT_INCREMENT;
    }

    @Override
    public int getScrollableBlockIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
        return orientation == javax.swing.SwingConstants.VERTICAL ? visibleRect.height : visibleRect.width;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
