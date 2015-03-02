package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.ImmutableList;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * This class contains a collection of {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
 */
public class WofBoardBlocks implements Iterable<WofBoardBlock> {
    public static final int COLUMN_COUNT = 14;
    public static final int ROW_COUNT = 4;

    private final ImmutableList<ImmutableList<WofBoardBlock>> boardBlocks;

    /**
     * Initializes the collection of {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
     * All blocks will <i>not</i> be suggested blocks.
     */
    public WofBoardBlocks() {
        this(false);
    }

    /**
     * Initializes the collection of {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
     * @param suggestedBlocks Whether or not all the constructed blocks are "suggested" blocks.
     */
    public WofBoardBlocks(final boolean suggestedBlocks) {
        // construct the boardBlock rows/columns
        final ImmutableList.Builder<ImmutableList<WofBoardBlock>> boardBlockBuilder =
                ImmutableList.builder();

        for(int curRow = 0; curRow < ROW_COUNT; curRow++) {
            final ImmutableList.Builder<WofBoardBlock> columnBlocks = ImmutableList.builder();
            for(int curColumn = 0; curColumn < COLUMN_COUNT; curColumn++) {
                if(isContentBlock(curRow, curColumn)) {
                    columnBlocks.add(new WofBoardBlock(WofBoardBlock.BlockType.NO_GLYPH,
                            suggestedBlocks));
                } else {
                    columnBlocks.add(new WofBoardBlock(WofBoardBlock.BlockType.NO_CONTENT));
                }
            }
            boardBlockBuilder.add(columnBlocks.build());
        }

        boardBlocks = boardBlockBuilder.build();
    }

    /**
     * Get the block at the specified row and column.
     * @param row The row to get the block from.
     * @param column The column to get the block from.
     * @return The block at the specified row and column.
     * @throws IndexOutOfBoundsException If the specified row and column are out of bounds
     * of the scoreboard.
     */
    public WofBoardBlock getBlock(final int row,
                                  final int column) throws IndexOutOfBoundsException {
        return boardBlocks.get(row).get(column);
    }

    /**
     * Add the entire collection of board blocks to the specified JPanel.
     * @param jpanel The JPanel to add all the board blocks to. Note that:
     *               <ol>
     *               <li>All components in this panel will be removed.</li>
     *               <li>The layout needs to be a {@link java.awt.GridBagLayout}.</li>
     *               </ol>
     * @throws IllegalArgumentException If the layout of jpanel is
     * not a {@link java.awt.GridBagLayout}.
     */
    public void addBlocksToPanel(final JPanel jpanel) throws IllegalArgumentException {
        if(jpanel.getLayout() instanceof GridBagLayout) {
            final GridBagConstraints constraints = new GridBagConstraints();
            jpanel.removeAll();
            for(int curRow = 0; curRow < WofBoardBlocks.ROW_COUNT; curRow++) {
                for(int curColumn = 0; curColumn < WofBoardBlocks.COLUMN_COUNT; curColumn++) {
                    constraints.gridx = curColumn;
                    constraints.gridy = curRow;
                    jpanel.add(getBlock(curRow, curColumn), constraints);
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "The specified JPanel's layout needs to be a GridBagLayout");
        }
    }

    /**
     * For every block that is <i>not</i> a no-content type:
     * <ol>
     *     <li>Set its type to no-glyph type.</li>
     *     <li>Set its text to an empty string.</li>
     * </ol>
     */
    public void resetBlocks() {
        for(int curRow = 0; curRow < ROW_COUNT; curRow++) {
            for(int curColumn = 0; curColumn < COLUMN_COUNT; curColumn++) {
                if(isContentBlock(curRow, curColumn)) {
                    final WofBoardBlock wofBoardBlock = getBlock(curRow, curColumn);
                    wofBoardBlock.setText("");
                    wofBoardBlock.setBlockType(WofBoardBlock.BlockType.NO_GLYPH);
                }
            }
        }
    }

    /**
     * Disallow the changing of the type of block to all blocks that are <i>not</i> no-content type.
     */
    public void lockBlocks() {
        for(final WofBoardBlock wofBoardBlock : this) {
            wofBoardBlock.lockBlock();
        }
    }

    /**
     * Allow the changing of the type of block to all blocks that are <i>not</i> no-content type.
     */
    public void unlockBlocks() {
        for(final WofBoardBlock wofBoardBlock : this) {
            wofBoardBlock.unlockBlock();
        }
    }

    /**
     * Set the editable property for all blocks.
     * @param editable <code>true</code> to make the blocks editable.
     *                 <code>false</code> to make the blocks un-editable.
     */
    public void setBlocksEditable(final boolean editable) {
        for(final WofBoardBlock wofBoardBlock : this) {
            wofBoardBlock.setEditable(editable);
        }
    }

    /**
     * Copies the block types from the specified {@link com.marlowsoft.wofsolver.ui.WofBoardBlocks}
     * to <code>this</code> one.
     * @param wofBoardBlocks The source of the block types to copy from.
     */
    public void copyLayout(final WofBoardBlocks wofBoardBlocks) {
        for(int curRow = 0; curRow < ROW_COUNT; curRow++) {
            for(int curColumn = 0; curColumn < COLUMN_COUNT; curColumn++) {
                getBlock(curRow, curColumn).setBlockType(
                        wofBoardBlocks.getBlock(curRow, curColumn).getBlockType()
                );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<WofBoardBlock> iterator() {
        return new WofBoardBlockIterator(this);
    }

    /**
     * Determines whether or not the specified row and column can contain content.
     * The four blocks in the corners of the board <i>cannot</i> contain content.
     * This function will, in other words, say whether or not the specified row and column are
     * one of the corner blocks.
     * @param row The row to check for a content block.
     * @param column The column to check for a content block.
     * @return <tt>true</tt> if the specified row and column can contain a content block;
     * <tt>false</tt> otherwise.
     */
    private boolean isContentBlock(final int row, final int column) {
        // is this a block that's NOT one of the corner bocks?
        return !((row == 0 && column == 0) ||
                 (row == 0 && column == COLUMN_COUNT - 1) ||
                 (row == ROW_COUNT - 1 && column == 0) ||
                 (row == ROW_COUNT - 1 && column == COLUMN_COUNT - 1));
    }
}
