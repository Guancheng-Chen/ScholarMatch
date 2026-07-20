package com.scholarmatch.frameworks.gui.style;

import org.junit.jupiter.api.Test;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.Icon;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IconsTest {

    @Test
    void testOfBuildsANonEmptyIcon() {
        final Icon icon = Icons.of(FontAwesomeSolid.HEART, 24, Color.RED);

        assertNotNull(icon);
        assertTrue(icon.getIconWidth() > 0);
        assertTrue(icon.getIconHeight() > 0);
    }

    @Test
    void testFromResourceThrowsOnMissingResource() {
        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Icons.fromResource("/images/does-not-exist.png", 24));

        assertEquals("Missing icon resource: /images/does-not-exist.png", exception.getMessage());
    }
}
