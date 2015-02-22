package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.WofBoardBlock;
import com.marlowsoft.wofsolver.ui.WofBoardBlocks;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.WofBoardBlocks} class.
 */
public class WofBoardBlocksTest {
    /**
     * Verify that, after construction, the board blocks have the correct content type. The important blocks
     * to look for are those four in the corners that don't have any content in them.
     */
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

    /**
     * Verify that attempting access a block out of bounds will throw a
     * {@link java.lang.IndexOutOfBoundsException}.
     */
    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetBlockOutOfBounds() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();
        boardBlocks.getBlock(99, 99);
    }

    /**
     * Verify that the proper amount of blocks get added to the panel.
     */
    @Test
    public void testAddBlocksToJPanel() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        boardBlocks.addBlocksToPanel(jPanel);

        final Component[] components = jPanel.getComponents();
        Assert.assertEquals(
                WofBoardBlocks.ROW_COUNT * WofBoardBlocks.COLUMN_COUNT,
                components.length);
    }

    /**
     * Verify that attempting to add the board blocks to a JPanel with an incorrect
     * layout will throw an {@link java.lang.IllegalArgumentException}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testIllegalLayout() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();

        // JPanels will be constructed with a FlowLayout by default
        boardBlocks.addBlocksToPanel(new JPanel());
    }
}
