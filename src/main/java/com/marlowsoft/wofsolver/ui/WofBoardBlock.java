package com.marlowsoft.wofsolver.ui;

import javax.swing.*;

/**
 * Created by RM025953 on 2/15/2015.
 */
public class WofBoardBlock extends JTextField {
    public enum BlockType {
        NO_CONTENT,
        GLYPH,
        NO_GLYPH
    }

    private BlockType blockType;

    public WofBoardBlock(final BlockType blockType) {
        this.blockType = blockType;
        setSize(20, 30);
    }

    public void setBlockType(final BlockType blockType) {
        this.blockType = blockType;
        // TODO apply some sort of visual stuff here
    }

    public BlockType getBlockType() {
        return blockType;
    }
}
