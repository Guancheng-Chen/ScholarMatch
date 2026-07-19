package com.scholarmatch.frameworks.gui.sytle;

/**
 * Small display-formatting helpers shared across views.
 */
public final class Format {

    private Format() {
    }

    /**
     * Formats a nullable stat (h-index, citation count, ...) for display.
     *
     * @param value the stat, or null if it was never looked up or entered
     * @return the value as a string, or "N/A" if null
     */
    public static String stat(final Integer value) {
        return value == null ? "N/A" : String.valueOf(value);
    }
}
