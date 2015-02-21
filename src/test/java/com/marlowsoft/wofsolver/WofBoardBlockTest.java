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
}
