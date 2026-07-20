package com.scholarmatch.interface_adapter.view_model.support;


import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * Minimal Swing-friendly replacement for javafx.beans.property.Property.
 *
 * <p>ViewModels hold one of these per observable field; Presenters write to it via
 * #set(Object) and Views subscribe via #addListener(Consumer) to refresh
 * themselves whenever the value changes.
 *
 * @param <T> the value type
 */
public final class ObservableValue<T> {


    private T value;
    private final List<Consumer<T>> listeners = new ArrayList<>();


    /**
     * Constructs an ObservableValue with an initial value.
     *
     * @param initial the initial value
     */
    public ObservableValue(final T initial) {
        this.value = initial;
    }


    /**
     * Returns the current value.
     *
     * @return the current value
     */
    public T get() {
        return this.value;
    }


    /**
     * Sets a new value and notifies all registered listeners.
     *
     * <p>Listeners typically update Swing components, so notification always happens on the
     * EDT — dispatched via {@link SwingUtilities#invokeLater} when #set is called from a
     * background thread (e.g. a presenter invoked from inside a SwingWorker), or run
     * immediately when already on the EDT.
     *
     * @param newValue the new value
     */
    public void set(final T newValue) {
        this.value = newValue;
        final List<Consumer<T>> currentListeners = List.copyOf(this.listeners);
        final Runnable notify = () -> {
            for (final Consumer<T> listener : currentListeners) {
                listener.accept(newValue);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            notify.run();
        } else {
            SwingUtilities.invokeLater(notify);
        }
    }


    /**
     * Registers a listener invoked with the new value on every #set(Object) call.
     *
     * @param listener the listener to add
     */
    public void addListener(final Consumer<T> listener) {
        this.listeners.add(listener);
    }


    /**
     * Unregisters a previously added listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(final Consumer<T> listener) {
        this.listeners.remove(listener);
    }
}

