package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * This class contains a collection of {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
 */
public class WofBoardBlocks {
    private static final int COLUMN_COUNT = 14;
    private static final int ROW_COUNT = 4;

    private final ImmutableList<ImmutableList<WofBoardBlock>> boardBlocks;

    public WofBoardBlocks() {
        // construct the boardBlock rows/columns
        ImmutableList.Builder<ImmutableList<WofBoardBlock>> boardBlockBuilder = ImmutableList.builder();

        for(int CurRow = 0; CurRow < ROW_COUNT; CurRow++) {
            List<WofBoardBlock> columnBlocks = Lists.newArrayList();
            for(int CurColumn = 0; CurColumn < COLUMN_COUNT; CurColumn++) {
                if(isContentBlock(CurRow, CurColumn)) {
                    columnBlocks.add(new WofBoardBlock(WofBoardBlock.BlockType.NO_GLYPH));
                } else {
                    columnBlocks.add(new WofBoardBlock(WofBoardBlock.BlockType.NO_CONTENT));
                }
            }
            boardBlockBuilder.add(ImmutableList.copyOf(columnBlocks));
        }

        boardBlocks = boardBlockBuilder.build();
    }

    /**
     * Get the block at the specified row and column.
     * @param row The row to get the block from.
     * @param column The column to get the block from.
     * @return The block at the specified row and column.
     * @throws IndexOutOfBoundsException If the specified row and column are out of bounds of the scoreboard.
     */
    public WofBoardBlock getBlock(final int row, final int column) throws IndexOutOfBoundsException {
        return boardBlocks.get(row).get(column);
    }

    /**
     * Determines whether or not the specified row and column can contain content. The four blocks in the corners
     * of the board <i>cannot</i> contain content. This function will, in other words, say whether or not the specified
     * row and column are one of the corner blocks.
     * @param row The row to check for a content block.
     * @param column The column to check for a content block.
     * @return <tt>true</tt> if the specified row and column can contain a content block; <tt>false</tt> otherwise.
     */
    private boolean isContentBlock(final int row, final int column) {
        // is this out of bounds?
        if(row < 0 || column < 0 || row > ROW_COUNT || column > COLUMN_COUNT) {
            return false;
        }

        // is this a block that's NOT one of the corner bocks?
        return !((row == 0 && column == 0) ||
                 (row == 0 && column == COLUMN_COUNT - 1) ||
                 (row == ROW_COUNT - 1 && column == 0) ||
                 (row == ROW_COUNT - 1 && column == COLUMN_COUNT - 1));
    }
}
