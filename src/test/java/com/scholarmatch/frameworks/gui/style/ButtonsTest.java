package com.scholarmatch.frameworks.gui.style;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;

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
}
