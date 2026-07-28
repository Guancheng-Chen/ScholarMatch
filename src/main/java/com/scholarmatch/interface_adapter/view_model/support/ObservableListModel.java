package com.scholarmatch.interface_adapter.view_model.support;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/**
 * Minimal Swing-friendly replacement for javafx.collections.ObservableList.
 *
 * <p>Behaves like a normal list, except every mutation notifies listeners registered via
 * #addListener(Runnable) so Swing views can refresh themselves.
 *
 * <p>Extends {@link AbstractList} rather than {@code ArrayList} on purpose: {@code ArrayList}
 * has its own concrete iterator and bulk-operation implementations that mutate the backing
 * array directly, bypassing any add/remove methods overridden here — so a caller using
 * {@code list.iterator().remove()}, {@code list.sort(...)}, or {@code list.removeIf(...)} would
 * silently skip the notification this class promises. {@code AbstractList}'s default iterator,
 * {@code sort}, and other bulk operations are all implemented in terms of {@link #get(int)},
 * {@link #set(int, Object)}, {@link #add(int, Object)}, and {@link #remove(int)}, so overriding
 * just those guarantees every mutation path notifies.
 *
 * @param <T> the element type
 */
public final class ObservableListModel<T> extends AbstractList<T> implements RandomAccess {

    private final List<T> items = new ArrayList<>();
    private final List<Runnable> listeners = new ArrayList<>();

    /**
     * Registers a listener invoked after every mutation (add, remove, set, clear, setAll).
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
     * @param newItems the new contents
     */
    public void setAll(final Collection<? extends T> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyListeners();
    }

    @Override
    public T get(final int index) {
        return this.items.get(index);
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public T set(final int index, final T element) {
        final T previous = this.items.set(index, element);
        notifyListeners();
        return previous;
    }

    @Override
    public void add(final int index, final T element) {
        this.items.add(index, element);
        notifyListeners();
    }

    @Override
    public T remove(final int index) {
        final T removed = this.items.remove(index);
        notifyListeners();
        return removed;
    }

    @Override
    public boolean addAll(final Collection<? extends T> newItems) {
        final boolean result = this.items.addAll(newItems);
        if (result) {
            notifyListeners();
        }
        return result;
    }

    @Override
    public void clear() {
        this.items.clear();
        notifyListeners();
    }

    private void notifyListeners() {
        for (final Runnable listener : List.copyOf(this.listeners)) {
            listener.run();
        }
    }
}
