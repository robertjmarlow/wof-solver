package com.marlowsoft.wofsolver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.bind.WofModule;
import com.marlowsoft.wofsolver.ui.LetterLabel;
import com.marlowsoft.wofsolver.ui.WofBoard;
import com.marlowsoft.wofsolver.ui.WofBoardBlock;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.WofBoard} class.
 */
public class WofBoardTest {
    private static Injector injector;
    private static Robot robot;

    @BeforeClass
    public static void beforeClass() throws AWTException {
        injector = Guice.createInjector(new WofModule());

        robot = new Robot();
        robot.setAutoDelay(50);
        robot.setAutoWaitForIdle(true);
    }

    /**
     * Tests proper construction of the class.
     * This test might look barren, but some magic has to be performed behind the scenes to make
     * sure the dialog can be constructed outside of Intellij (e.g. Maven).
     * <ol>
     *     <li>
     *         A Maven plugin needs to inject Intellij's form bytecode into the generated binaries.
     *     </li>
     *     <li>
     *         A runtime dependency needs exist to avoid runtime errors.
     *     </li>
     * </ol>
     * Class construction without errors verifies these items are in place.
     */
    @Test
    public void testConstruction() {
        final WofBoard wofBoard = new WofBoard(injector);

        Assert.assertEquals(Dialog.ModalityType.APPLICATION_MODAL, wofBoard.getModalityType());
    }

    /**
     * This test will verify that when guess board blocks are changed, the letter labels are
     * updated correctly.
     */
    @Test
    public void testOnBlockValueChanged() {
        final WofBoard wofBoard = new WofBoard(injector);

        wofBoard.pack();
        wofBoard.setModal(false);
        wofBoard.setVisible(true);

        final Container container = wofBoard.getContentPane();
        final JPanel controlPanel = (JPanel)container.getComponent(3);
        final JPanel usedLettersBoard  = (JPanel)container.getComponent(0);
        final JPanel gameBoard  = (JPanel)container.getComponent(1);
        final LetterLabel letterLabel = (LetterLabel)usedLettersBoard.getComponent(0);
        final WofBoardBlock gameBoardBlock = (WofBoardBlock)gameBoard.getComponent(1);
        final JButton startGameButton = (JButton)controlPanel.getComponent(1);
        final Point startGameButtonPoint = startGameButton.getLocationOnScreen();

        robot.mouseMove(startGameButtonPoint.x, startGameButtonPoint.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        // this works just as well as hooking together the event dispatchers
        gameBoardBlock.setText("A");
        wofBoard.onBlockValueChanged();

        Assert.assertEquals(LetterLabel.GuessType.CORRECT, letterLabel.getGuessType());

        gameBoardBlock.setText("");
        wofBoard.onBlockValueChanged();

        Assert.assertEquals(LetterLabel.GuessType.NONE, letterLabel.getGuessType());
    }

    /**
     * This test will verify that hitting the "reset game" button will reset all board blocks and
     * letter labels.
     * This test will also verify the "start game" button behaves correctly.
     */
    @Test
    public void testBoardControls() {
        final WofBoard wofBoard = new WofBoard(injector);

        wofBoard.pack();
        wofBoard.setModal(false);
        wofBoard.setVisible(true);

        final Container container = wofBoard.getContentPane();
        final JPanel controlPanel = (JPanel)container.getComponent(3);
        final JPanel usedLettersBoard  = (JPanel)container.getComponent(0);
        final JPanel gameBoard  = (JPanel)container.getComponent(1);
        final JPanel suggestedBoard  = (JPanel)container.getComponent(2);
        final LetterLabel letterLabel = (LetterLabel)usedLettersBoard.getComponent(0);
        final WofBoardBlock gameBoardBlock = (WofBoardBlock)gameBoard.getComponent(1);
        final WofBoardBlock suggestedBoardBlock = (WofBoardBlock)suggestedBoard.getComponent(1);
        final JButton resetBoardButton = (JButton)controlPanel.getComponent(0);
        final JButton startGameButton = (JButton)controlPanel.getComponent(1);
        final Point resetBoardButtonPoint = resetBoardButton.getLocationOnScreen();
        final Point startGameButtonPoint = startGameButton.getLocationOnScreen();

        Assert.assertTrue(startGameButton.isEnabled());
        Assert.assertTrue(resetBoardButton.isEnabled());

        gameBoardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        // mouse over to the reset button and click on it
        robot.mouseMove(resetBoardButtonPoint.x, resetBoardButtonPoint.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        // hit the "no" button
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_ALT);

        Assert.assertEquals(WofBoardBlock.BlockType.GLYPH, gameBoardBlock.getBlockType());

        // mouse over to the start game button and click on it
        robot.mouseMove(startGameButtonPoint.x, startGameButtonPoint.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        Assert.assertFalse(startGameButton.isEnabled());
        Assert.assertEquals(WofBoardBlock.BlockType.GLYPH, suggestedBoardBlock.getBlockType());

        // mouse over to the reset button and click on it
        robot.mouseMove(resetBoardButtonPoint.x, resetBoardButtonPoint.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        // hit the "yes" button
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_Y);
        robot.keyRelease(KeyEvent.VK_Y);
        robot.keyRelease(KeyEvent.VK_ALT);

        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH, gameBoardBlock.getBlockType());
        Assert.assertEquals(WofBoardBlock.BlockType.NO_GLYPH, suggestedBoardBlock.getBlockType());
        Assert.assertFalse(letterLabel.isEnabled());
    }

    /**
     * Verifies that if there's a single suggestion for a board word, then that word will be filled in
     * on the "suggested solution".
     */
    @Test
    public void testGetSingleSuggestedWord() throws InterruptedException {
        final WofBoard wofBoard = new WofBoard(injector);

        wofBoard.pack();
        wofBoard.setModal(false);
        wofBoard.setVisible(true);

        final Container container = wofBoard.getContentPane();
        final JPanel gamePanel  = (JPanel)container.getComponent(1);
        final JPanel suggestionPanel  = (JPanel)container.getComponent(2);
        final JPanel controlPanel = (JPanel)container.getComponent(3);
        final JButton startGameButton = (JButton)controlPanel.getComponent(1);
        final Point startGameButtonPoint = startGameButton.getLocationOnScreen();

        // set a bunch of board blocks to glyph blocks
        WofBoardBlock boardBlock = (WofBoardBlock)gamePanel.getComponent(1);
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        boardBlock = (WofBoardBlock)gamePanel.getComponent(2);
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        boardBlock = (WofBoardBlock)gamePanel.getComponent(3);
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        boardBlock = (WofBoardBlock)gamePanel.getComponent(4);
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        boardBlock = (WofBoardBlock)gamePanel.getComponent(5);
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        boardBlock = (WofBoardBlock)gamePanel.getComponent(6);
        boardBlock.setBlockType(WofBoardBlock.BlockType.GLYPH);

        // start the game
        robot.mouseMove(startGameButtonPoint.x, startGameButtonPoint.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        // fill in a few letters to get the word "unique"
        boardBlock = (WofBoardBlock)gamePanel.getComponent(1);
        boardBlock.setText("U");

        boardBlock = (WofBoardBlock)gamePanel.getComponent(4);
        boardBlock.setText("Q");

        boardBlock = (WofBoardBlock)gamePanel.getComponent(6);
        boardBlock.setText("E");

        wofBoard.onBlockValueChanged();

        // since there's a call to queue up the function to update the "suggested words",
        //  wait for a bit to make sure that the function executes
        Thread.currentThread().sleep(250);

        boardBlock = (WofBoardBlock)suggestionPanel.getComponent(1);
        Assert.assertEquals("U", boardBlock.getText());
        boardBlock = (WofBoardBlock)suggestionPanel.getComponent(2);
        Assert.assertEquals("N", boardBlock.getText());
        boardBlock = (WofBoardBlock)suggestionPanel.getComponent(3);
        Assert.assertEquals("I", boardBlock.getText());
        boardBlock = (WofBoardBlock)suggestionPanel.getComponent(4);
        Assert.assertEquals("Q", boardBlock.getText());
        boardBlock = (WofBoardBlock)suggestionPanel.getComponent(5);
        Assert.assertEquals("U", boardBlock.getText());
        boardBlock = (WofBoardBlock)suggestionPanel.getComponent(6);
        Assert.assertEquals("E", boardBlock.getText());
    }
}
