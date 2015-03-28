package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.event.BlockValueChangedListener;

/**
 * Implementation of {@link BlockValueChangedListener} that
 * will merely count the number of times it's been told about changes.
 */
public class BlockChangedListenerTestImpl implements BlockValueChangedListener {
    int onValueChangedCount;

    public BlockChangedListenerTestImpl() {
        onValueChangedCount = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBlockValueChanged() {
        onValueChangedCount++;
    }

    public int getValueChangedCount() {
        return onValueChangedCount;
    }
}
