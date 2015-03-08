package com.marlowsoft.wofsolver;

import com.google.common.collect.*;
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

    /**
     * Verify that resetting all blocks will set their text to an empty string and
     * their block type to no glyph.
     */
    @Test
    public void testResetBlocks() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();

        boardBlocks.getBlock(1, 4).setText("This should be removed!");
        boardBlocks.getBlock(1, 4).setBlockType(WofBoardBlock.BlockType.GLYPH);

        boardBlocks.resetBlocks();

        Assert.assertEquals("", boardBlocks.getBlock(1, 4).getText());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH,
                boardBlocks.getBlock(1, 4).getBlockType());
    }

    /**
     * Verify that locking blocks will disallow the setting of the block type.
     * Verify that unlocking blocks will allow the setting of the block type.
     */
    @Test
    public void testLockUnlockBlocks() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();

        boardBlocks.getBlock(1, 4).setBlockType(WofBoardBlock.BlockType.NO_GLYPH);
        boardBlocks.lockBlocks();
        boardBlocks.getBlock(1, 4).setBlockType(WofBoardBlock.BlockType.GLYPH);
        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH,
                boardBlocks.getBlock(1, 4).getBlockType());

        boardBlocks.unlockBlocks();
        boardBlocks.getBlock(1, 4).setBlockType(WofBoardBlock.BlockType.GLYPH);
        Assert.assertEquals(WofBoardBlock.BlockType.GLYPH,
                boardBlocks.getBlock(1, 4).getBlockType());
    }

    /**
     * Verifies that block types will get copied from one instance of
     * {@link com.marlowsoft.wofsolver.ui.WofBoardBlocks} to another.
     */
    @Test
    public void testCopyLayout() {
        final WofBoardBlocks sourceBoardBlocks = new WofBoardBlocks();
        final WofBoardBlocks destBoardBlocks = new WofBoardBlocks();

        sourceBoardBlocks.getBlock(1, 4).setBlockType(WofBoardBlock.BlockType.GLYPH);
        destBoardBlocks.copyLayout(sourceBoardBlocks);
        Assert.assertEquals(WofBoardBlock.BlockType.GLYPH,
                destBoardBlocks.getBlock(1, 4).getBlockType());
    }

    /**
     * Verifies that all blocks in this collection can be set to editable/not editable.
     */
    @Test
    public void testSetBlocksEditable() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();

        boardBlocks.setBlocksEditable(true);
        Assert.assertTrue(boardBlocks.getBlock(1, 4).isEditable());

        boardBlocks.setBlocksEditable(false);
        Assert.assertFalse(boardBlocks.getBlock(1, 4).isEditable());
    }

    /**
     * Verifies that blocks of a specified type in this collection can be set to editable/not editable.
     */
    @Test
    public void testSetBlocksEditableOfType() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();
        boardBlocks.getBlock(1, 4).setBlockType(WofBoardBlock.BlockType.GLYPH);

        boardBlocks.setBlocksEditable(true, WofBoardBlock.BlockType.GLYPH);
        Assert.assertTrue(boardBlocks.getBlock(1, 4).isEditable());
        Assert.assertFalse(boardBlocks.getBlock(1, 5).isEditable());

        boardBlocks.setBlocksEditable(false, WofBoardBlock.BlockType.GLYPH);
        Assert.assertFalse(boardBlocks.getBlock(1, 4).isEditable());
        Assert.assertFalse(boardBlocks.getBlock(1, 5).isEditable());
    }

    /**
     * Verifies that the function to get used characters on the board functions correctly.
     */
    @Test
    public void testGetUsedCharsOnBoard() {
        final WofBoardBlocks boardBlocks = new WofBoardBlocks();
        ImmutableSet<Character> usedChars;

        boardBlocks.getBlock(1, 4).setText("P");
        boardBlocks.getBlock(1, 5).setText("U");
        boardBlocks.getBlock(1, 6).setText("G");
        boardBlocks.getBlock(2, 6).setText("G");

        usedChars = boardBlocks.getUsedCharsOnBoard();

        Assert.assertTrue(usedChars.contains('P'));
        Assert.assertTrue(usedChars.contains('U'));
        Assert.assertTrue(usedChars.contains('G'));
        Assert.assertFalse(usedChars.contains('S'));
    }
}
