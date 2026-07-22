package com.scholarmatch.interface_adapter.view_model.support;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Minimal Swing-friendly replacement for javafx.collections.ObservableList.
 *
 * <p>Behaves like a normal ArrayList, except every mutation notifies listeners
 * registered via #addListener(Runnable) so Swing views can refresh themselves.
 *
 * @param <T> the element type
 */
public final class ObservableListModel<T> extends ArrayList<T> {


    private final transient List<Runnable> listeners = new ArrayList<>();


    /**
     * Registers a listener invoked after every mutation (add, remove, clear, setAll).
     *
     * @param listener the listener to add
     */
    public void addListener(final Runnable listener) {
        this.listeners.add(listener);
    }

    public void removeListener(final Runnable listener) {
        this.listeners.remove(listener);
    }


    /**
     * Replaces the entire contents of this list with items in one notification.
     *
     * @param items the new contents
     */
    public void setAll(final Collection<? extends T> items) {
        super.clear();
        super.addAll(items);
        notifyListeners();
    }


    @Override
    public boolean add(final T item) {
        final boolean result = super.add(item);
        notifyListeners();
        return result;
    }


    @Override
    public T remove(final int index) {
        final T removed = super.remove(index);
        notifyListeners();
        return removed;
    }


    @Override
    public boolean remove(final Object item) {
        final boolean result = super.remove(item);
        if (result) {
            notifyListeners();
        }
        return result;
    }


    @Override
    public boolean addAll(final Collection<? extends T> items) {
        final boolean result = super.addAll(items);
        if (result) {
            notifyListeners();
        }
        return result;
    }


    @Override
    public void clear() {
        super.clear();
        notifyListeners();
    }


    private void notifyListeners() {
        for (final Runnable listener : List.copyOf(this.listeners)) {
            listener.run();
        }
    }
}

