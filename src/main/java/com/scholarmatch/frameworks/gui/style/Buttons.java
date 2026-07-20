package com.scholarmatch.frameworks.gui.style;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Applies semantic color variants (accent / success / danger / outlined) to JButtons.
 *
 * <p>Paints its own rounded, state-colored background via a custom {@link BasicButtonUI}
 * instead of FlatLaf's per-component style-string properties — the style-string approach
 * left these buttons rendering with no visible fill until the mouse moved over them (only
 * the hover/pressed color keys were being picked up, not the rest-state one). Painting
 * directly, the same technique {@link RoundedPanel} already uses for cards, is deterministic
 * regardless of look-and-feel quirks.
 */
public final class Buttons {

    private static final int ARC = 8;

    private Buttons() {
    }

    /**
     * Styles a button as the primary call-to-action (solid brand-teal background).
     *
     * @param button the button to style
     */
    public static void accent(final JButton button) {
        solid(button, Theme.ACCENT, Theme.ACCENT_BRIGHT, Color.decode("#0d6b54"), Color.WHITE);
    }

    /**
     * Styles a button as a positive / confirming action (solid blue background,
     * deliberately distinct from the green ACCENT so "Connect" doesn't read as a
     * second brand-color action).
     *
     * @param button the button to style
     */
    public static void success(final JButton button) {
        solid(button, Theme.SUCCESS, Color.decode("#388bfd"), Color.decode("#0550ae"), Color.WHITE);
    }

    /**
     * Styles a button as a destructive action (solid red background).
     *
     * @param button the button to style
     */
    public static void danger(final JButton button) {
        solid(button, Theme.DANGER, Color.decode("#e5534b"), Color.decode("#a40e26"), Color.WHITE);
    }

    /**
     * Styles a button as a secondary action: a light neutral-gray fill with a visible border.
     *
     * @param button the button to style
     */
    public static void outlined(final JButton button) {
        final Color base = Color.decode("#edf2f1");
        final Color hover = Color.decode("#dfe7e5");
        final Color pressed = Color.decode("#cfdad7");
        final Color border = Color.decode("#9fb0ac");
        final Color hoverBorder = Color.decode("#566461");
        final Color foreground = Color.decode("#1c2624");

        button.setForeground(foreground);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(final Graphics g, final JComponent c) {
                final AbstractButton b = (AbstractButton) c;
                final Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                final Color fill = b.getModel().isPressed() ? pressed : b.getModel().isRollover() ? hover : base;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), ARC, ARC);
                g2.setColor(b.getModel().isRollover() ? hoverBorder : border);
                g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, ARC, ARC);
                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    private static void solid(
        final JButton button, final Color base, final Color hover, final Color pressed, final Color foreground) {
        button.setForeground(foreground);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(final Graphics g, final JComponent c) {
                final AbstractButton b = (AbstractButton) c;
                final Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                final Color fill = !b.isEnabled() ? base.darker()
                    : b.getModel().isPressed() ? pressed : b.getModel().isRollover() ? hover : base;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), ARC, ARC);
                g2.dispose();
                super.paint(g, c);
            }
        });
    }
}
