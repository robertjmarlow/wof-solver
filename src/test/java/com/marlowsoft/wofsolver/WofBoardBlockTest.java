package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.WofBoardBlock;
import com.marlowsoft.wofsolver.ui.event.BlockValueChangedDispatcher;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.WofBoardBlock} class.
 */
public class WofBoardBlockTest {
    private static Robot robot;

    @BeforeClass
    public static void setUpClass() throws AWTException {
        robot = new Robot();
        robot.setAutoDelay(100);
        robot.setAutoWaitForIdle(true);
    }

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

    /**
     * This test will verify that right-clicking on a block will change its type properly.
     */
    @Test
    public void testBoardBlockClicking() {
        final JDialog jDialog = new JDialog();
        final WofBoardBlock wofBoardBlock = new WofBoardBlock(WofBoardBlock.BlockType.NO_GLYPH);
        wofBoardBlock.setVisible(true);
        jDialog.add(wofBoardBlock);
        jDialog.pack();
        jDialog.setTitle("Unit Test!");
        jDialog.setVisible(true);
        final Point point = wofBoardBlock.getLocationOnScreen();
        robot.mouseMove(point.x, point.y);
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);

        Assert.assertEquals(WofBoardBlock.BlockType.GLYPH, wofBoardBlock.getBlockType());

        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);

        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH, wofBoardBlock.getBlockType());

        wofBoardBlock.setBlockType(WofBoardBlock.BlockType.NO_CONTENT);

        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);

        Assert.assertEquals(WofBoardBlock.BlockType.NO_CONTENT, wofBoardBlock.getBlockType());

        // this is to merely cover that "mouseExited" event
        robot.mouseMove(point.x + 500, point.y + 500);
    }

    /**
     * Test the {@link com.marlowsoft.wofsolver.ui.WofBoardBlock.BoardBlockDocumentListener} and
     * {@link com.marlowsoft.wofsolver.ui.WofBoardBlock.BoardBlockFieldLimit} classes.
     */
    @Test
    public void testDocumentListener() {
        final JDialog jDialog = new JDialog();
        final BlockChangedListenerTestImpl listener = new BlockChangedListenerTestImpl();
        BlockValueChangedDispatcher.addListener(listener);
        final WofBoardBlock wofBoardBlock = new WofBoardBlock(WofBoardBlock.BlockType.GLYPH);
        wofBoardBlock.setVisible(true);
        wofBoardBlock.setEditable(true);
        jDialog.add(wofBoardBlock);
        jDialog.pack();
        jDialog.setTitle("Unit Test!");
        jDialog.setVisible(true);

        // mouse over to the block and type an "A"
        // verify that the text was changed and the listener heard about the event
        final Point point = wofBoardBlock.getLocationOnScreen();
        robot.mouseMove(point.x, point.y);

        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);

        Assert.assertEquals("A", wofBoardBlock.getText());
        Assert.assertEquals(1, listener.getValueChangedCount());

        // backspace and verify the text was deleted and the listener heard about the event
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);

        Assert.assertEquals("", wofBoardBlock.getText());
        Assert.assertEquals(2, listener.getValueChangedCount());

        // type in a character that isn't accepted
        // verify the character wasn't accepted
        robot.keyPress(KeyEvent.VK_COMMA);
        robot.keyRelease(KeyEvent.VK_COMMA);
        Assert.assertEquals("", wofBoardBlock.getText());
        Assert.assertEquals(2, listener.getValueChangedCount());

        // type in two characters
        // verify that only the first character was accepted
        robot.keyPress(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_B);
        Assert.assertEquals("B", wofBoardBlock.getText());
        Assert.assertEquals(3, listener.getValueChangedCount());

        robot.keyPress(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_B);
        Assert.assertEquals("B", wofBoardBlock.getText());
        Assert.assertEquals(3, listener.getValueChangedCount());
    }
}
