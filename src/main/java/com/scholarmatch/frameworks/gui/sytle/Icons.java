package com.scholarmatch.frameworks.gui.sytle;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;

/**
 * Creates themed, consistently-sized vector icons (via Ikonli/FontAwesome) for use
 * across the Swing UI — labels, buttons, and nav items.
 */
public final class Icons {

    private Icons() {
    }

    /**
     * Builds an icon at the given size and color.
     *
     * @param code  the icon glyph (e.g. a {@code FontAwesomeSolid} constant)
     * @param size  the icon's width/height in pixels
     * @param color the icon's fill color
     * @return a Swing {@link Icon} ready to attach to a label or button
     */
    public static Icon of(final Ikon code, final int size, final Color color) {
        return FontIcon.of(code, size, color);
    }

    /**
     * Loads a raster icon from the classpath (under {@code src/main/resources}) and
     * scales it, preserving aspect ratio, to fit within a {@code size}x{@code size} box.
     *
     * @param classpathResource the resource path, e.g. {@code "/images/logo.png"}
     * @param size               the icon's max width/height in pixels
     * @return a Swing {@link Icon} ready to attach to a label or button
     */
    public static Icon fromResource(final String classpathResource, final int size) {
        final URL resource = Icons.class.getResource(classpathResource);
        if (resource == null) {
            throw new IllegalArgumentException("Missing icon resource: " + classpathResource);
        }
        final ImageIcon original = new ImageIcon(resource);
        final int width = original.getIconWidth();
        final int height = original.getIconHeight();
        final double scale = (double) size / Math.max(width, height);
        final int scaledWidth = (int) Math.round(width * scale);
        final int scaledHeight = (int) Math.round(height * scale);
        final Image scaled = original.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
