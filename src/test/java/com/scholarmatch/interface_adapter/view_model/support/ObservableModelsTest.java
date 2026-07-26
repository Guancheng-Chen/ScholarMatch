package com.scholarmatch.interface_adapter.view_model.support;

import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObservableModelsTest {

    @Test
    void testObservableValueNotifiesAndRemovesListeners() throws Exception {
        final ObservableValue<String> value = new ObservableValue<>("initial");
        final AtomicReference<String> observed = new AtomicReference<>();
        final Consumer<String> listener = observed::set;
        value.addListener(listener);

        value.set("background update");
        SwingUtilities.invokeAndWait(() -> { });
        assertEquals("background update", value.get());
        assertEquals("background update", observed.get());

        SwingUtilities.invokeAndWait(() -> value.set("EDT update"));
        assertEquals("EDT update", observed.get());

        value.removeListener(listener);
        value.set("unobserved update");
        SwingUtilities.invokeAndWait(() -> { });
        assertEquals("EDT update", observed.get());
    }

    @Test
    void testObservableListNotifiesForMutations() {
        final ObservableListModel<String> values = new ObservableListModel<>();
        final AtomicInteger notifications = new AtomicInteger();
        final Runnable listener = notifications::incrementAndGet;
        values.addListener(listener);

        values.setAll(List.of("a", "b"));
        assertTrue(values.add("c"));
        assertTrue(values.addAll(List.of("d", "e")));
        assertFalse(values.addAll(List.of()));
        assertEquals("a", values.remove(0));
        assertTrue(values.remove("b"));
        assertFalse(values.remove("missing"));
        values.clear();

        assertEquals(6, notifications.get());
        values.removeListener(listener);
        values.add("unobserved");
        assertEquals(6, notifications.get());
    }
}
