package com.scholarmatch.frameworks.gui.style;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ButtonsTest {

    @Test
    void testAccentInstallsACustomUiAndStaysEnabled() {
        final JButton button = new JButton("Save");

        Buttons.accent(button);

        assertNotNull(button.getUI());
        assertFalse(button.isContentAreaFilled());
        assertFalse(button.isBorderPainted());
        assertTrue(button.isEnabled());
    }

    @Test
    void testSuccessInstallsACustomUi() {
        final JButton button = new JButton("Connect");

        Buttons.success(button);

        assertNotNull(button.getUI());
        assertFalse(button.isOpaque());
    }

    @Test
    void testDangerInstallsACustomUi() {
        final JButton button = new JButton("Delete Account");

        Buttons.danger(button);

        assertNotNull(button.getUI());
        assertFalse(button.isFocusPainted());
    }

    @Test
    void testOutlinedInstallsACustomUi() {
        final JButton button = new JButton("Skip");

        Buttons.outlined(button);

        assertNotNull(button.getUI());
        assertFalse(button.isContentAreaFilled());
    }

    @Test
    void testStyledButtonCanStillBeToggledDisabled() {
        final JButton button = new JButton("Connect");
        Buttons.success(button);

        button.setEnabled(false);

        assertFalse(button.isEnabled());
    }

    @Test
    void testSolidPaintCoversRestPressedRolloverAndDisabledStates() {
        final JButton button = new JButton("Save");
        Buttons.accent(button);
        button.setSize(80, 32);

        paint(button);
        button.getModel().setRollover(true);
        paint(button);
        button.getModel().setPressed(true);
        button.getModel().setArmed(true);
        paint(button);
        button.getModel().setRollover(false);
        button.getModel().setPressed(false);
        button.getModel().setArmed(false);
        button.setEnabled(false);
        paint(button);
    }

    @Test
    void testOutlinedPaintCoversRestPressedAndRolloverStates() {
        final JButton button = new JButton("Skip");
        Buttons.outlined(button);
        button.setSize(80, 32);

        paint(button);
        button.getModel().setRollover(true);
        paint(button);
        button.getModel().setPressed(true);
        button.getModel().setArmed(true);
        paint(button);
    }

    private void paint(final JButton button) {
        final BufferedImage image = new BufferedImage(
                Math.max(1, button.getWidth()), Math.max(1, button.getHeight()),
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = image.createGraphics();
        try {
            button.paint(graphics);
        } finally {
            graphics.dispose();
        }
    }
}
