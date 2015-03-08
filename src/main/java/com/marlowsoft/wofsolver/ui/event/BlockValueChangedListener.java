package com.marlowsoft.wofsolver.ui.event;

/**
 * Defines events that fire when interacting with the keyboard in a
 * {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
 */
public interface BlockValueChangedListener {
    /**
     * Fired when a character is added to or removed from a block.
     */
    void onValueChanged();
}
