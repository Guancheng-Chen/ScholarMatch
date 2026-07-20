package com.scholarmatch.frameworks.gui.testsupport;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

/**
 * Test-only helpers for reaching into a Swing component tree.
 *
 * <p>None of the production views in this codebase expose their internal fields (labels,
 * text fields, buttons) for testing — they're built and wired entirely inside the
 * constructor. These helpers do a depth-first walk of the real component tree instead, in
 * the same order the view itself added the components, so tests can locate "the first
 * JTextField" or "the only JButton" without the production code needing test-only getters.
 */
public final class SwingTestSupport {

    private SwingTestSupport() {
    }

    /**
     * Depth-first-searches the given container for every descendant assignable to the given
     * type, in component-tree order (a component's own children are visited immediately
     * after it).
     *
     * @param root the container to search (its own children and their descendants)
     * @param type the component type to match
     * @param <T>  the component type
     * @return every matching descendant, in tree order
     */
    public static <T extends Component> List<T> findAll(final Container root, final Class<T> type) {
        final List<T> found = new ArrayList<>();
        collect(root, type, found);
        return found;
    }

    /**
     * Convenience wrapper over {@link #findAll} for when exactly one match — or one match at
     * a known position among several — is expected.
     *
     * @param root  the container to search
     * @param type  the component type to match
     * @param index which match to return (0 for "the first")
     * @param <T>   the component type
     * @return the match at the given position
     */
    public static <T extends Component> T find(final Container root, final Class<T> type, final int index) {
        final List<T> found = findAll(root, type);
        if (index >= found.size()) {
            throw new IllegalStateException(
                "Expected at least " + (index + 1) + " " + type.getSimpleName()
                    + "(s) but found " + found.size());
        }
        return found.get(index);
    }

    private static <T extends Component> void collect(
        final Container root, final Class<T> type, final List<T> found) {
        for (final Component child : root.getComponents()) {
            if (type.isInstance(child)) {
                found.add(type.cast(child));
            }
            if (child instanceof Container container) {
                collect(container, type, found);
            }
        }
    }
}
