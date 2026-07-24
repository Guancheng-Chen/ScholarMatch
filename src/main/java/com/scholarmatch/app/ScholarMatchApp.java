package com.scholarmatch.app;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatPropertiesLaf;
import com.scholarmatch.frameworks.gui.MainView;
import com.scholarmatch.frameworks.gui.style.Theme;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Bootstraps ScholarMatch: applies the FlatLaf theme, delegates wiring to
 * AppBuilder, and opens the main window.
 */
public final class ScholarMatchApp {

    /**
     * Default window width in pixels (bottom-right x of 1400 minus top-left x of 82).
     */
    private static final int DEFAULT_WIDTH = 1318;

    /**
     * Default window height in pixels (bottom-right y of 878 minus top-left y of 77).
     */
    private static final int DEFAULT_HEIGHT = 801;

    /**
     * Application entry point — hands off to the Swing event dispatch thread.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(final String[] args) {
        // The UI is English-only; without this, Swing's built-in resource bundles (and any
        // Locale-sensitive formatting done without an explicit Locale) fall back to the
        // JVM's default locale, which leaks non-English text into the app on non-English
        // systems.
        Locale.setDefault(Locale.ENGLISH);
        SwingUtilities.invokeLater(() -> new ScholarMatchApp().start());
    }

    /**
     * Applies the theme, wires up the app, and shows the main window.
     */
    public void start() {
        installTheme();

        final MainView mainView = new AppBuilder()
                .addSession()
                .addRepositories()
                .addViewModels()
                .addPresenters()
                .addInteractors()
                .addControllers()
                .build();
        final JFrame frame = new JFrame("ScholarMatch");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainView);
        frame.getContentPane().setBackground(Theme.BG_DEFAULT);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void installTheme() {
        installTheme(getClass().getResourceAsStream("/themes/scholarmatch-light.properties"));
    }

    static void installTheme(final InputStream themeProperties) {
        try (InputStream in = themeProperties) {
            FlatLaf.setup(new FlatPropertiesLaf("ScholarMatch Light", in));
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load ScholarMatch theme", e);
        }
        UIManager.put("Panel.background", Theme.BG_DEFAULT);
    }
}
