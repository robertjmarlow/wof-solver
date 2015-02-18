package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.WofBoardBlock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by RM025953 on 2/15/2015.
 */
public class WofBoardBlockTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

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
