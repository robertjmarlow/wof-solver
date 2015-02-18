package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.WofBoardBlock;
import com.marlowsoft.wofsolver.ui.WofBoardBlocks;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by RM025953 on 2/15/2015.
 */
public class WofBoardBlocksTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetContentTypeOfBlocks() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();
        Assert.assertEquals(WofBoardBlock.BlockType.NO_CONTENT, boardBlocks.getBlock(0, 0).getBlockType());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_CONTENT, boardBlocks.getBlock(0, 13).getBlockType());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_CONTENT, boardBlocks.getBlock(3, 0).getBlockType());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_CONTENT, boardBlocks.getBlock(3, 13).getBlockType());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH, boardBlocks.getBlock(2, 10).getBlockType());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH, boardBlocks.getBlock(1, 8).getBlockType());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH, boardBlocks.getBlock(0, 5).getBlockType());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetBlockOutOfBounds() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();
        boardBlocks.getBlock(99, 99);
    }
}
