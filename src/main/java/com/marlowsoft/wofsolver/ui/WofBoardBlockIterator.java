package com.marlowsoft.wofsolver.ui;

import java.util.*;

/**
 * Provides a way to iterate over a bunch of
 * {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
 */
public class WofBoardBlockIterator implements Iterator<WofBoardBlock> {
    private final WofBoardBlocks wofBoardBlocks;
    private int curRow;
    private int curColumn;

    /**
     *
     * @param wofBoardBlocks The collection of
     *                       {@link com.marlowsoft.wofsolver.ui.WofBoardBlock} to iterate over.
     */
    public WofBoardBlockIterator(final WofBoardBlocks wofBoardBlocks) {
        this.wofBoardBlocks = wofBoardBlocks;
        curRow = 0;
        curColumn = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return curRow < WofBoardBlocks.ROW_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WofBoardBlock next() {
        final WofBoardBlock wofBoardBlock = wofBoardBlocks.getBlock(curRow, curColumn);

        curColumn++;

        if(curColumn >= WofBoardBlocks.COLUMN_COUNT) {
            curColumn = 0;
            curRow++;
        }

        return wofBoardBlock;
    }

    /**
     * This operation is not supported.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }
}
