package com.scholarmatch.frameworks.gui.style;

import org.junit.jupiter.api.Test;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CenteringScrollPanelTest {

    @Test
    void testReflowNowDoesNothingWhileWidthIsZero() {
        final FakeReflowable child = new FakeReflowable();
        new CenteringScrollPanel(child).reflowNow();

        assertEquals(0, child.reflowCallCount);
    }

    @Test
    void testReflowNowPropagatesAvailableWidthToADirectReflowableChild() {
        final FakeReflowable child = new FakeReflowable();
        final CenteringScrollPanel panel = new CenteringScrollPanel(child);
        panel.setSize(500, 300);

        panel.reflowNow();

        assertEquals(1, child.reflowCallCount);
        assertEquals(500, child.lastWidth);
    }

    @Test
    void testReflowNowPropagatesToANestedReflowableDescendant() {
        final FakeReflowable nested = new FakeReflowable();
        final JPanel wrapper = new JPanel();
        wrapper.add(nested);
        final CenteringScrollPanel panel = new CenteringScrollPanel(wrapper);
        panel.setSize(640, 400);

        panel.reflowNow();

        assertEquals(1, nested.reflowCallCount);
        assertEquals(640, nested.lastWidth);
    }

    @Test
    void testReflowNowCanBeCalledRepeatedlyAndReflectsLatestWidth() {
        final FakeReflowable child = new FakeReflowable();
        final CenteringScrollPanel panel = new CenteringScrollPanel(child);

        panel.setSize(500, 300);
        panel.reflowNow();
        panel.setSize(300, 300);
        panel.reflowNow();

        assertEquals(2, child.reflowCallCount);
        assertEquals(300, child.lastWidth);
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
    }
}
