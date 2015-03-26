package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.LetterLabel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.LetterLabel} class.
 */
public class LetterLabelTest {
    private static Robot robot;

    @BeforeClass
    public static void setUpClass() throws AWTException {
        robot = new Robot();
        robot.setAutoDelay(50);
        robot.setAutoWaitForIdle(true);
    }

    /**
     * Verifies proper construction of the class.
     */
    @Test
    public void testConstructor() {
        final LetterLabel letterLabel = new LetterLabel('A');

        Assert.assertEquals(LetterLabel.GuessType.NONE, letterLabel.getGuessType());
    }

    /**
     * Verifies getters and setters.
     */
    @Test
    public void testSetterGetter() {
        final LetterLabel letterLabel = new LetterLabel('A');

        letterLabel.setGuessType(LetterLabel.GuessType.CORRECT);
        Assert.assertEquals(LetterLabel.GuessType.CORRECT, letterLabel.getGuessType());
        letterLabel.setGuessType(LetterLabel.GuessType.INCORRECT);
        Assert.assertEquals(LetterLabel.GuessType.INCORRECT, letterLabel.getGuessType());
        letterLabel.setGuessType(LetterLabel.GuessType.NONE);
        Assert.assertEquals(LetterLabel.GuessType.NONE, letterLabel.getGuessType());
        letterLabel.setGuessType(LetterLabel.GuessType.NONE);
    }

    /**
     * Verifies the context menu sets the GuessType properly.
     */
    @Test
    public void testContextMenu() {
        final JDialog jDialog = new JDialog();
        final LetterLabel letterLabel = new LetterLabel('A');
        jDialog.add(letterLabel);
        jDialog.pack();
        jDialog.setTitle("Unit Test!");
        jDialog.setVisible(true);
        final Point point = letterLabel.getLocationOnScreen();
        robot.mouseMove(point.x, point.y);
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
        robot.mouseMove(point.x + 5, point.y + 5);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Assert.assertEquals(LetterLabel.GuessType.INCORRECT, letterLabel.getGuessType());

        robot.mouseMove(point.x, point.y);
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
        robot.mouseMove(point.x + 5, point.y + 5);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Assert.assertEquals(LetterLabel.GuessType.NONE, letterLabel.getGuessType());
    }
}
