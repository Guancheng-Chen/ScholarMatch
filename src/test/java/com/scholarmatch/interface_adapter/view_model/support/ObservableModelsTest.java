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
        assertEquals("c", values.set(0, "c-replaced"));
        values.clear();

        assertEquals(7, notifications.get());
        values.removeListener(listener);
        values.add("unobserved");
        assertEquals(7, notifications.get());
    }

    @Test
    void testObservableListIteratorAndSortRouteThroughOverriddenMethods() {
        final ObservableListModel<String> values = new ObservableListModel<>();
        final AtomicInteger notifications = new AtomicInteger();
        values.addListener(notifications::incrementAndGet);
        values.setAll(List.of("b", "a", "c"));

        final var iterator = values.iterator();
        assertEquals("b", iterator.next());
        iterator.remove();
        assertEquals(List.of("a", "c"), values);

        values.sort(null);
        assertEquals(List.of("a", "c"), values);
        assertTrue(notifications.get() > 1);
    }
}
