package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.LetterLabel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.LetterLabel} class.
 */
public class LetterLabelTest {
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
    }
}
