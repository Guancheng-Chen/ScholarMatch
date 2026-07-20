package com.scholarmatch.frameworks.gui.style;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * A JPanel painted as a rounded, bordered "card" — the Swing equivalent of the
 * .sm-card / .sm-user-card CSS classes from the previous JavaFX UI.
 */
public class RoundedPanel extends JPanel {

    private final int arc;

    /**
     * Constructs a RoundedPanel with the given corner radius and inner padding.
     *
     * @param arc     the corner radius in pixels
     * @param padding the inner padding in pixels on every side
     */
    public RoundedPanel(final int arc, final int padding) {
        this.arc = arc;
        setOpaque(false);
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Theme.BG_SUBTLE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), this.arc, this.arc);

        g2.setColor(Theme.BORDER_DEFAULT);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, this.arc, this.arc);

        g2.dispose();
        super.paintComponent(g);
    }
}
