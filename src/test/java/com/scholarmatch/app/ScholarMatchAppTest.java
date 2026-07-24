package com.scholarmatch.app;

import com.scholarmatch.frameworks.gui.MainView;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScholarMatchAppTest {

    @Test
    void testStartBuildsAndShowsWindowWithoutLaunchingRealApplication() {
        final MainView mainView = mock(MainView.class);
        final Container content = mock(Container.class);
        try (MockedConstruction<AppBuilder> builders = mockConstruction(
                    AppBuilder.class, (builder, context) -> configure(builder, mainView));
             MockedConstruction<JFrame> frames = mockConstruction(
                    JFrame.class, (frame, context) -> when(frame.getContentPane()).thenReturn(content))) {
            new ScholarMatchApp().start();
            final JFrame frame = frames.constructed().get(0);
            verify(frame).setSize(1318, 801);
            verify(frame).setLocationRelativeTo(null);
            verify(frame).setVisible(true);
            assertEquals(1, builders.constructed().size());
        }
    }

    @Test
    void testMainUsesEnglishLocaleAndSchedulesStartOnEdt() {
        try (MockedStatic<SwingUtilities> swing =
                     org.mockito.Mockito.mockStatic(SwingUtilities.class);
             MockedConstruction<ScholarMatchApp> applications =
                     mockConstruction(ScholarMatchApp.class)) {
            ScholarMatchApp.main(new String[] {"ignored"});
            final ArgumentCaptor<Runnable> task = ArgumentCaptor.forClass(Runnable.class);
            swing.verify(() -> SwingUtilities.invokeLater(task.capture()));
            task.getValue().run();
            assertEquals(Locale.ENGLISH, Locale.getDefault());
            verify(applications.constructed().get(0)).start();
        }
    }

    @Test
    void testThemeIOExceptionIsReported() {
        final ByteArrayInputStream stream = new ByteArrayInputStream(new byte[0]) {
            @Override
            public void close() throws IOException {
                throw new IOException("close failed");
            }
        };
        final IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> ScholarMatchApp.installTheme(stream));
        assertEquals("Failed to load ScholarMatch theme", exception.getMessage());
    }

    private static void configure(final AppBuilder builder, final MainView mainView) {
        when(builder.addSession()).thenReturn(builder);
        when(builder.addRepositories()).thenReturn(builder);
        when(builder.addViewModels()).thenReturn(builder);
        when(builder.addPresenters()).thenReturn(builder);
        when(builder.addInteractors()).thenReturn(builder);
        when(builder.addControllers()).thenReturn(builder);
        when(builder.build()).thenReturn(mainView);
    }
}
