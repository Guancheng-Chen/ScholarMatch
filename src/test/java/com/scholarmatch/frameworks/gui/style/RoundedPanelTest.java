package com.scholarmatch.frameworks.gui.style;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class RoundedPanelTest {

    @Test
    void testConstructorAndPainting() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final RoundedPanel panel = new RoundedPanel(18, 12);
            panel.setSize(160, 80);
            final BufferedImage image = new BufferedImage(160, 80, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D graphics = image.createGraphics();
            try {
                panel.paint(graphics);
            } finally {
                graphics.dispose();
            }

            assertFalse(panel.isOpaque());
            final EmptyBorder border = assertInstanceOf(EmptyBorder.class, panel.getBorder());
            assertEquals(12, border.getBorderInsets().top);
            assertEquals(Theme.BORDER_DEFAULT.getRGB(), image.getRGB(80, 0));
            assertEquals(Theme.BG_SUBTLE.getRGB(), image.getRGB(80, 40));
        });
    }
}
