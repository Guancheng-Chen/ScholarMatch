package com.scholarmatch.frameworks.gui.sytle;

import java.awt.Color;

/**
 * Color palette and shared style constants for the Swing UI.
 *
 * <p>A white + teal-green academic-platform palette. ACCENT is deepened from the
 * brand's #17997a to #128066 so white button text clears WCAG AA (4.5:1); #17997a
 * itself is reserved for the brighter hover state. SUCCESS is deliberately blue
 * rather than a second green, so "Connect" reads as a distinct action from the
 * green brand/selection accent.
 */
public final class Theme {

    /** Main window / panel background. */
    public static final Color BG_DEFAULT = Color.decode("#f6f8f8");

    /** Card / top-bar background, one step lighter than #BG_DEFAULT. */
    public static final Color BG_SUBTLE = Color.decode("#ffffff");

    /** Input field background, one step darker than #BG_DEFAULT. */
    public static final Color BG_INSET = Color.decode("#edf2f1");

    /** Primary text color. */
    public static final Color FG_DEFAULT = Color.decode("#1c2624");

    /** Secondary / caption text color. */
    public static final Color FG_MUTED = Color.decode("#566461");

    /** Tertiary / placeholder text color. */
    public static final Color FG_SUBTLE = Color.decode("#7c8985");

    /** High-emphasis text (titles, avatar initials on accent background). */
    public static final Color FG_EMPHASIS = Color.decode("#ffffff");

    /** Default border color for cards, fields, and dividers. */
    public static final Color BORDER_DEFAULT = Color.decode("#d8dedc");

    /** Softer border color for subtle dividers. */
    public static final Color BORDER_MUTED = Color.decode("#e8ecea");

    /** Accent (primary action) button background — deepened brand teal. */
    public static final Color ACCENT = Color.decode("#128066");

    /** Brand teal at full brightness — used for hover states and icon-only accents. */
    public static final Color ACCENT_BRIGHT = Color.decode("#17997a");

    /** Accent text / link color on white backgrounds. */
    public static final Color ACCENT_FG = Color.decode("#0c6e58");

    /** Success (positive action, e.g. "Connect") button background — blue, distinct from ACCENT. */
    public static final Color SUCCESS = Color.decode("#0969da");

    /** Success text color. */
    public static final Color SUCCESS_FG = Color.decode("#0969da");

    /** Danger (destructive / error) button background. */
    public static final Color DANGER = Color.decode("#cf222e");

    /** Danger / error text color. */
    public static final Color DANGER_FG = Color.decode("#cf222e");

    /** Warning / notification background (e.g. the "matched!" toast) — soft yellow. */
    public static final Color WARNING_BG = Color.decode("#fff3cd");

    /** Warning / notification border, darker than #WARNING_BG. */
    public static final Color WARNING_BORDER = Color.decode("#ffe69c");

    /** Warning / notification text color, dark enough on #WARNING_BG for WCAG AA. */
    public static final Color WARNING_FG = Color.decode("#664d03");

    /** Corner radius used for form cards (login, register, update profile). */
    public static final int CARD_RADIUS = 14;

    /** Corner radius used for the connectable user card. */
    public static final int SCHOLAR_CARD_RADIUS = 18;

    private Theme() {
    }
}
