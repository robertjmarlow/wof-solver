package com.marlowsoft.wofsolver.ui;

import javax.swing.*;
import java.awt.*;

/**
 * This class displays letters that have been used or unused.
 */
public class LetterLabel extends JLabel {
    private final char letter;
    private boolean hasBeenUsed;

    private static final Color usedLetterColor = new Color(0, 0, 0);
    private static final Color unusedLetterColor = new Color(0, 0, 0, 50);
    private static final float FONT_SIZE = 18;

    public enum GuessType {
        CORRECT,
        INCORRECT,
        NONE
    }

    private GuessType guessType;

    public LetterLabel(final char letter) {
        this.letter = letter;
        hasBeenUsed = false;
        guessType = GuessType.NONE;

        setForeground(unusedLetterColor);
        setText(Character.toString(letter));
        setFont(getFont().deriveFont(FONT_SIZE));
    }

    public GuessType getGuessType() {
        return guessType;
    }

    public void setGuessType(final GuessType guessType) {
        this.guessType = guessType;
    }
}
