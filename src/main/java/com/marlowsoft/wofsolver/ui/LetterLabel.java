package com.marlowsoft.wofsolver.ui;

import javax.swing.*;
import java.awt.*;

/**
 * This class displays letters that have been used or unused.
 */
public class LetterLabel extends JLabel {
    private final char letter;
    private boolean hasBeenUsed;

    private static final Color usedLetterColor = new Color(255, 255, 255, 10);
    private static final Color unusedLetterColor = new Color(255, 255, 255, 50);
    private static final float FONT_SIZE = 18;

    /**
     * Defines the type of guess for this block
     */
    public enum GuessType {
        CORRECT,
        INCORRECT,
        NONE
    }

    private GuessType guessType;

    /**
     *
     * @param letter The letter for this label.
     */
    public LetterLabel(final char letter) {
        this.letter = letter;
        hasBeenUsed = false;
        guessType = GuessType.NONE;

        setForeground(unusedLetterColor);
        setText(Character.toString(letter));
        setFont(getFont().deriveFont(FONT_SIZE));
    }

    /**
     * Get the type of guess.
     * @return The type of guess.
     */
    public GuessType getGuessType() {
        return guessType;
    }

    /**
     * Set the type of guess.
     * @param guessType The type of guess.
     */
    public void setGuessType(final GuessType guessType) {
        this.guessType = guessType;
    }
}
