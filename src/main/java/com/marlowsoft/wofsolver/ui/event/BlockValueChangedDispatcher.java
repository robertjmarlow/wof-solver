package com.marlowsoft.wofsolver.ui.event;

import com.google.common.collect.*;

import java.util.*;

/**
 * This class is responsible for sending events when the value of a block changes to any listeners.
 */
public abstract class BlockValueChangedDispatcher {
    private static final List<BlockValueChangedListener> listeners = Lists.newArrayList();

    /**
     * Add a listener for any block value changed events.
     * @param listener Whoever is interested in listening for block value changed events.
     */
    public static synchronized void addListener(final BlockValueChangedListener listener) {
        listeners.add(listener);
    }

    /**
     * Dispatched whenever a character is added or removed from a block.
     */
    public static void dispatchOnBlockCharChanged() {
        for(final BlockValueChangedListener listener : listeners) {
            listener.onValueChanged();
        }
    }
}
