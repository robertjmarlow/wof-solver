package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.WofBoardBlock;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.WofBoardBlock} class.
 */
public class WofBoardBlockTest {
    /**
     * Verify that the getters and setters function correctly.
     */
    @Test
    public void testGetSetBlockType() {
        final WofBoardBlock boardBlock = new WofBoardBlock(WofBoardBlock.BlockType.NO_CONTENT);
        Assert.assertEquals(WofBoardBlock.BlockType.NO_CONTENT, boardBlock.getBlockType());
        boardBlock.setBlockType(WofBoardBlock.BlockType.NO_GLYPH);
        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH, boardBlock.getBlockType());
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);
        Assert.assertEquals(WofBoardBlock.BlockType.GLYPH, boardBlock.getBlockType());
    }

    /**
     * Verify that if a {@link com.marlowsoft.wofsolver.ui.WofBoardBlock} is locked, its
     * type cannot be changed.
     * Verify that if a {@link com.marlowsoft.wofsolver.ui.WofBoardBlock} is unlocked, its
     * type can be changed.
     */
    @Test
    public void testLockUnlockBlock() {
        final WofBoardBlock boardBlock = new WofBoardBlock(WofBoardBlock.BlockType.NO_CONTENT);
        boardBlock.lockBlock();
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        Assert.assertEquals(WofBoardBlock.BlockType.NO_CONTENT, boardBlock.getBlockType());

        boardBlock.unlockBlock();
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);
        Assert.assertEquals(WofBoardBlock.BlockType.GLYPH, boardBlock.getBlockType());
    }
}
