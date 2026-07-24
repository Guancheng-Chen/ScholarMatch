package com.scholarmatch.app;

import com.scholarmatch.frameworks.gui.MainView;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class AppBuilderTest {

    @Test
    void testEveryBuilderStepRejectsMissingPredecessor() {
        assertMissing(() -> new AppBuilder().addRepositories(), "addSession");
        assertMissing(() -> new AppBuilder().addViewModels(), "addRepositories");
        assertMissing(() -> new AppBuilder().addPresenters(), "addViewModels");
        assertMissing(() -> new AppBuilder().addInteractors(), "addPresenters");
        assertMissing(() -> new AppBuilder().addControllers(), "addInteractors");
        assertMissing(() -> new AppBuilder().build(), "addControllers");
    }

    @Test
    void testCompleteOnlineAndOfflineWiringBuildsMainView() throws Exception {
        buildOnEdt(false);
        buildOnEdt(true);
        assertTrue(invokeHealthCheckWithResult(200));
        assertFalse(invokeHealthCheckWithResult(500));
        invokeHealthCheck("not a valid URI");
    }

    @Test
    void testOfflineSelectionCoversEnvironmentAndHealthBranches() {
        assertTrue(AppBuilder.selectOffline("TRUE", () -> {
            throw new AssertionError("Health check must be skipped in explicit offline mode");
        }));
        assertTrue(AppBuilder.selectOffline(null, () -> false));
        assertFalse(AppBuilder.selectOffline("false", () -> true));
    }

    @Test
    void testInterruptedRetryRestoresInterruptFlag() throws Exception {
        final AppBuilder builder = new AppBuilder();
        final var sleep = AppBuilder.class.getDeclaredMethod("sleep", java.time.Duration.class);
        sleep.setAccessible(true);
        Thread.currentThread().interrupt();
        sleep.invoke(builder, java.time.Duration.ofMillis(1));
        assertTrue(Thread.currentThread().isInterrupted());
        Thread.interrupted();
    }

    private static boolean invokeHealthCheckWithResult(final int status) throws Exception {
        final HttpClient.Builder httpBuilder = mock(HttpClient.Builder.class);
        final HttpClient client = mock(HttpClient.class);
        final HttpResponse<Void> response = mock(HttpResponse.class);
        when(httpBuilder.connectTimeout(any())).thenReturn(httpBuilder);
        when(httpBuilder.build()).thenReturn(client);
        when(response.statusCode()).thenReturn(status);
        doReturn(response).when(client).send(any(), any());
        try (MockedStatic<HttpClient> http = mockStatic(HttpClient.class)) {
            http.when(HttpClient::newBuilder).thenReturn(httpBuilder);
            if (status == 200) {
                buildDefaultOnEdt();
            }
            final var method = AppBuilder.class.getDeclaredMethod("isServerReachable", String.class);
            method.setAccessible(true);
            return (Boolean) method.invoke(new AppBuilder(), "https://example.test");
        }
    }

    private static void invokeHealthCheck(final String url) throws Exception {
        final var method = AppBuilder.class.getDeclaredMethod("isServerReachable", String.class);
        method.setAccessible(true);
        org.junit.jupiter.api.Assertions.assertFalse((Boolean) method.invoke(new AppBuilder(), url));
    }

    private static void buildOnEdt(final boolean offline) throws Exception {
        final MainView[] result = new MainView[1];
        SwingUtilities.invokeAndWait(() -> result[0] = new AppBuilder(offline)
                .addSession()
                .addRepositories()
                .addViewModels()
                .addPresenters()
                .addInteractors()
                .addControllers()
                .build());
        assertTrue(result[0].getComponentCount() > 0);
    }

    private static void buildDefaultOnEdt() throws Exception {
        final MainView[] result = new MainView[1];
        SwingUtilities.invokeAndWait(() -> result[0] = new AppBuilder()
                .addSession()
                .addRepositories()
                .addViewModels()
                .addPresenters()
                .addInteractors()
                .addControllers()
                .build());
        assertTrue(result[0].getComponentCount() > 0);
    }

    private static void assertMissing(final Runnable action, final String step) {
        final IllegalStateException exception = assertThrows(IllegalStateException.class, action::run);
        assertTrue(exception.getMessage().contains(step));
    }
}
