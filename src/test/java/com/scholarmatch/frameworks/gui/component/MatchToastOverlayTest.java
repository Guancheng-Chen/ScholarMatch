package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;

import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchToastOverlayTest {

    @Test
    void testContentResizeToastReplacementAnimationAndPainting() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                final MatchToastOverlay overlay = new MatchToastOverlay();
                overlay.setSize(600, 400);
                fireResize(overlay);

                final Component plain = new Component() { };
                overlay.setContent(plain);
                assertEquals(600, plain.getWidth());

                final JPanel content = new JPanel();
                overlay.setContent(content);
                fireResize(overlay);
                assertEquals(400, content.getHeight());

                overlay.showToast("First match");
                final JPanel first = activeToast(overlay);
                assertTrue(SwingTestSupport.findAll(first, JLabel.class).stream()
                        .anyMatch(label -> "First match".equals(label.getText())));
                paint(first);

                overlay.setSize(50, 300);
                fireResize(overlay);
                assertEquals(20, first.getX());

                overlay.showToast("Second match");
                final JPanel second = activeToast(overlay);
                assertTrue(first != second);
                invokeSlideOut(overlay, first);
                fireUntilStopped(activeTimer(overlay), 100);
                assertSame(second, activeToast(overlay));

                invokeSlideIn(overlay, second);
                Timer timer = activeTimer(overlay);
                fireUntilStopped(timer, 100);
                timer = activeTimer(overlay);
                fireOnce(timer);
                timer = activeTimer(overlay);
                fireUntilStopped(timer, 100);
                assertNull(activeToast(overlay));
                assertNull(activeTimer(overlay));
            } catch (ReflectiveOperationException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    private static void fireResize(final MatchToastOverlay overlay) {
        for (final var listener : overlay.getComponentListeners()) {
            listener.componentResized(new ComponentEvent(overlay, ComponentEvent.COMPONENT_RESIZED));
        }
    }

    private static void paint(final JPanel panel) {
        panel.setSize(panel.getPreferredSize());
        final BufferedImage image = new BufferedImage(
                panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = image.createGraphics();
        try {
            panel.paint(graphics);
        } finally {
            graphics.dispose();
        }
    }

    private static void invokeSlideIn(
            final MatchToastOverlay overlay, final JPanel toast) throws ReflectiveOperationException {
        final Method method = MatchToastOverlay.class.getDeclaredMethod("slideIn", JPanel.class, int.class);
        method.setAccessible(true);
        method.invoke(overlay, toast, 20);
    }

    private static void invokeSlideOut(
            final MatchToastOverlay overlay, final JPanel toast) throws ReflectiveOperationException {
        final Method method = MatchToastOverlay.class.getDeclaredMethod("slideOut", JPanel.class, int.class);
        method.setAccessible(true);
        method.invoke(overlay, toast, 20);
    }

    private static JPanel activeToast(final MatchToastOverlay overlay)
            throws ReflectiveOperationException {
        return (JPanel) field("activeToast").get(overlay);
    }

    private static Timer activeTimer(final MatchToastOverlay overlay)
            throws ReflectiveOperationException {
        return (Timer) field("activeTimer").get(overlay);
    }

    private static Field field(final String name) throws ReflectiveOperationException {
        final Field field = MatchToastOverlay.class.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private static void fireUntilStopped(final Timer timer, final int limit) {
        for (int i = 0; i < limit && timer.isRunning(); i++) {
            fireOnce(timer);
        }
    }

    private static void fireOnce(final Timer timer) {
        for (final var listener : timer.getActionListeners()) {
            listener.actionPerformed(new ActionEvent(timer, ActionEvent.ACTION_PERFORMED, "test"));
        }
    }
}
