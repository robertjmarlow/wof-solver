package com.marlowsoft.wofsolver.ui;

import javax.swing.*;

/**
 * Defines a single block on the board.
 */
public class WofBoardBlock extends JTextField {
    /**
     * Defines all possible block types.
     */
    public enum BlockType {
        NO_CONTENT,
        GLYPH,
        NO_GLYPH
    }

    private BlockType blockType;

    /**
     *
     * @param blockType The type of block.
     */
    public WofBoardBlock(final BlockType blockType) {
        this.blockType = blockType;
        setSize(20, 30);
    }

    /**
     * Set the block type.
     * @param blockType The block type to set.
     */
    public void setBlockType(final BlockType blockType) {
        this.blockType = blockType;
        // TODO apply some sort of visual stuff here
    }

    /**
     * Get the block type.
     * @return The block type.
     */
    public BlockType getBlockType() {
        return blockType;
    }
}
