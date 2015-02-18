package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by RM025953 on 2/15/2015.
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

    public WofBoardBlock getBlock(final int row, final int column) throws IndexOutOfBoundsException {
        return boardBlocks.get(row).get(column);
    }

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
