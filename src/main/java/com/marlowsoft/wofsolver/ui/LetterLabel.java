package com.marlowsoft.wofsolver.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class displays letters that have been used or unused.
 */
public class LetterLabel extends JLabel {
    private static final Color unusedLetterColor = new Color(20, 20, 20);
    private static final float FONT_SIZE = 18;
    private final GuessAction guessAction;
    private final JMenuItem guessMenuItem;
    private static final String LETTER_GUESSED_CORRECTLY = "Letter was guessed correctly";
    private static final String MARK_AS_INCORRECT = "Mark as incorrect guess";
    private static final String UNMARK_AS_INCORRECT = "Un-mark as incorrect guess";

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
        guessType = GuessType.NONE;

        setForeground(unusedLetterColor);
        setText(Character.toString(letter));
        setFont(getFont().deriveFont(FONT_SIZE));
        guessAction = new GuessAction();
        final JPopupMenu popupMenu = new JPopupMenu();
        guessMenuItem = new JMenuItem(guessAction);
        popupMenu.add(guessMenuItem);
        setComponentPopupMenu(popupMenu);
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
        switch(this.guessType) {
            case NONE:
                setForeground(unusedLetterColor);
                guessMenuItem.setEnabled(true);
                guessMenuItem.setText(MARK_AS_INCORRECT);
                break;
            case INCORRECT:
                setForeground(Color.red);
                guessMenuItem.setEnabled(true);
                guessMenuItem.setText(UNMARK_AS_INCORRECT);
                break;
            case CORRECT:
                setForeground(Color.green);
                guessMenuItem.setEnabled(false);
                guessMenuItem.setText(LETTER_GUESSED_CORRECTLY);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        guessMenuItem.setEnabled(enabled);
    }

    /**
     * Handles events from the context menu.
     */
    private class GuessAction extends AbstractAction {
        /**
         * Construct a new {@link com.marlowsoft.wofsolver.ui.LetterLabel.GuessAction} and give the
         * menu item a default label.
         */
        public GuessAction() {
            super(MARK_AS_INCORRECT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            // this is essentially a state machine to move to the next guess state
            switch(getGuessType()) {
                case NONE:
                    setGuessType(GuessType.INCORRECT);
                    break;
                case INCORRECT:
                    setGuessType(GuessType.NONE);
                    break;
                case CORRECT:
                    break;
            }
        }
    }
}
