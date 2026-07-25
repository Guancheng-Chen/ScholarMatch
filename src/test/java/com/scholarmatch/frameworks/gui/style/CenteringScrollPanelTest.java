package com.scholarmatch.frameworks.gui.style;

import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CenteringScrollPanelTest {

    @Test
    void testReflowNowDoesNothingWhileWidthIsZero() throws Exception {
        final FakeReflowable child = onEdt(FakeReflowable::new);
        onEdt(() -> new CenteringScrollPanel(child).reflowNow());

        assertEquals(0, child.reflowCallCount);
    }

    @Test
    void testReflowNowPropagatesAvailableWidthToADirectReflowableChild() throws Exception {
        final FakeReflowable child = onEdt(FakeReflowable::new);
        onEdt(() -> {
            final CenteringScrollPanel panel = new CenteringScrollPanel(child);
            panel.setSize(500, 300);
            panel.reflowNow();
        });

        // setSize() itself already reflows synchronously (see CenteringScrollPanel#setBounds),
        // so the explicit reflowNow() call right after it reflows a second time with the same width.
        assertEquals(2, child.reflowCallCount);
        assertEquals(500, child.lastWidth);
    }

    @Test
    void testReflowNowPropagatesToANestedReflowableDescendant() throws Exception {
        final FakeReflowable nested = onEdt(FakeReflowable::new);
        onEdt(() -> {
            final JPanel wrapper = new JPanel();
            wrapper.add(nested);
            wrapper.add(new PlainComponent());
            final CenteringScrollPanel panel = new CenteringScrollPanel(wrapper);
            panel.setSize(640, 400);
            panel.reflowNow();
        });

        assertEquals(2, nested.reflowCallCount);
        assertEquals(640, nested.lastWidth);
    }

    @Test
    void testReflowNowCanBeCalledRepeatedlyAndReflectsLatestWidth() throws Exception {
        final FakeReflowable child = onEdt(FakeReflowable::new);
        onEdt(() -> {
            final CenteringScrollPanel panel = new CenteringScrollPanel(child);
            panel.setSize(500, 300);
            panel.reflowNow();
            panel.setSize(300, 300);
            panel.reflowNow();
        });

        assertEquals(4, child.reflowCallCount);
        assertEquals(300, child.lastWidth);
    }

    @Test
    void testResizeReflowsAutomatically() throws Exception {
        final FakeReflowable child = onEdt(FakeReflowable::new);
        onEdt(() -> {
            final CenteringScrollPanel panel = new CenteringScrollPanel(child);
            panel.setSize(420, 200);
        });

        assertEquals(1, child.reflowCallCount);
        assertEquals(420, child.lastWidth);
    }

    @Test
    void testSetBoundsReflowsWhenOnlyHeightChanges() throws Exception {
        final FakeReflowable child = onEdt(FakeReflowable::new);

        onEdt(() -> {
            final CenteringScrollPanel panel = new CenteringScrollPanel(child);

            panel.setBounds(0, 0, 400, 200);
            child.reset();

            panel.setBounds(0, 0, 400, 300);
        });

        assertEquals(1, child.reflowCallCount);
        assertEquals(400, child.lastWidth);
    }

    @Test
    void testSetBoundsDoesNotReflowWhenSizeDoesNotChange() throws Exception {
        final FakeReflowable child = onEdt(FakeReflowable::new);

        onEdt(() -> {
            final CenteringScrollPanel panel = new CenteringScrollPanel(child);

            panel.setBounds(0, 0, 400, 200);
            child.reset();

            panel.setBounds(10, 20, 400, 200);
        });

        assertEquals(0, child.reflowCallCount);
    }

    @Test
    void testScrollableContract() throws Exception {
        onEdt(() -> {
            final CenteringScrollPanel panel = new CenteringScrollPanel(new JPanel());
            panel.setPreferredSize(new Dimension(320, 240));
            final Rectangle visible = new Rectangle(0, 0, 125, 75);

            assertEquals(new Dimension(320, 240), panel.getPreferredScrollableViewportSize());
            assertEquals(16, panel.getScrollableUnitIncrement(visible, SwingConstants.VERTICAL, 1));
            assertEquals(75, panel.getScrollableBlockIncrement(visible, SwingConstants.VERTICAL, 1));
            assertEquals(125, panel.getScrollableBlockIncrement(visible, SwingConstants.HORIZONTAL, -1));
            assertTrue(panel.getScrollableTracksViewportWidth());
            assertFalse(panel.getScrollableTracksViewportHeight());
        });
    }

    private static void onEdt(final Runnable action)
            throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(action);
    }

    private static <T> T onEdt(final Callable<T> action)
            throws InvocationTargetException, InterruptedException {
        final AtomicReference<T> result = new AtomicReference<>();
        final AtomicReference<Exception> failure = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            try {
                result.set(action.call());
            } catch (Exception ex) {
                failure.set(ex);
            }
        });
        if (failure.get() != null) {
            throw new IllegalStateException(failure.get());
        }
        return result.get();
    }

    /**
     * Minimal Reflowable spy: a real JComponent (so it participates in the Swing component
     * tree CenteringScrollPanel walks) that just records how it was called.
     */
    private static final class FakeReflowable extends JPanel implements Reflowable {

        private int reflowCallCount;
        private int lastWidth;

        @Override
        public void reflow(final int width) {
            this.reflowCallCount++;
            this.lastWidth = width;
        }

        private void reset() {
            this.reflowCallCount = 0;
            this.lastWidth = 0;
        }
    }

    private static final class PlainComponent extends Component {
    }
}
