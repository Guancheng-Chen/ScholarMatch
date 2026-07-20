package com.scholarmatch.frameworks.gui.sytle;

/**
 * Implemented by components whose internal layout depends on the width they're given —
 * e.g. a card that switches from a 3-column layout to a vertically-stacked one once its
 * container gets too narrow to fit them side by side.
 *
 * <p>{@link CenteringScrollPanel} walks its child hierarchy for Reflowable components and
 * calls {@link #reflow(int)} on each whenever the scroll viewport is resized.
 */
public interface Reflowable {

    /**
     * Rebuilds this component's internal layout for the given available width.
     *
     * @param width the width, in pixels, this component should now occupy
     */
    void reflow(int width);
}
