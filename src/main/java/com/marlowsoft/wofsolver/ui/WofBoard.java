package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.*;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.dictionary.WordSearch;
import com.marlowsoft.wofsolver.ui.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Main dialog of the application.
 * Displays a collection of {@link WofBoardBlocks}
 * and allows for user input.
 */
public class WofBoard extends JDialog implements BlockValueChangedListener {
    private JPanel contentPane;
    private JPanel gameBoardPane;
    private JPanel usedLettersPane;
    private JPanel controlPane;
    private JPanel suggestedSolutionPane;
    private JButton buttonStart;
    private JButton buttonResetBoard;

    private final WofBoardBlocks boardBlocks;
    private final WofBoardBlocks suggestedBoardBlocks;
    private final WordSearch wordSearch;
    private final ImmutableMap<Character, LetterLabel> letterLabels;

    // TODO would a "tutorial" mode be nice for the user?

    /**
     * Initializes all elements in the board.
     *
     * @param injector Injector that will be used to create new objects.
     */
    public WofBoard(final Injector injector) {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setTitle("Wheel of Fortune Solver");

        // this centers the dialog
        setLocationRelativeTo(null);

        boardBlocks = new WofBoardBlocks();
        boardBlocks.addBlocksToPanel(gameBoardPane);
        suggestedBoardBlocks = new WofBoardBlocks(true);
        suggestedBoardBlocks.addBlocksToPanel(suggestedSolutionPane);

        wordSearch = injector.getInstance(WordSearch.class);

        boardBlocks.setBlocksEditable(false);

        // construct the panel with the used letters
        final ImmutableMap.Builder<Character, LetterLabel> letterLabelsBuilder =
                ImmutableMap.builder();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridy = 1;
        constraints.ipadx = 5;
        constraints.ipady = 5;
        for(char curChar = 'A'; curChar <= 'Z'; curChar++) {
            constraints.gridx = curChar - 'A';
            final LetterLabel letterLabel = new LetterLabel(curChar);
            letterLabel.setEnabled(false);
            usedLettersPane.add(letterLabel, constraints);
            letterLabelsBuilder.put(curChar, letterLabel);
        }
        letterLabels = letterLabelsBuilder.build();

        buttonStart.addActionListener(new StartGameActionListener());
        buttonResetBoard.addActionListener(new ResetBoardActionListener());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBlockValueChanged() {
        for(final Character usedChar : boardBlocks.getUsedCharsOnBoard()) {
            final LetterLabel letterLabel = letterLabels.get(usedChar);
            if(letterLabel != null) {
                letterLabel.setGuessType(LetterLabel.GuessType.CORRECT);
            }
        }

        for(final Character unusedChar : boardBlocks.getUnusedCharsOnBoard()) {
            final LetterLabel letterLabel = letterLabels.get(unusedChar);
            if(letterLabel != null) {
                letterLabel.setGuessType(LetterLabel.GuessType.NONE);
            }
        }
    }

    /**
     * Listener for clicking on the "Reset Board" button.
     */
    private class ResetBoardActionListener implements ActionListener {
        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent event) {
            if(JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to reset the game board?", "Reset Board?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ==
                    JOptionPane.YES_OPTION) {
                boardBlocks.unlockBlocks();
                boardBlocks.resetBlocks();
                suggestedBoardBlocks.resetBlocks();
                buttonStart.setEnabled(true);
                boardBlocks.setBlocksEditable(false);
                for(final Map.Entry<Character, LetterLabel> letterLabelEntry :
                        letterLabels.entrySet()) {
                    letterLabelEntry.getValue().setEnabled(false);
                }
            }
        }
    }

    /**
     * Listener for clicking on the "Start Game" button.
     */
    private class StartGameActionListener implements ActionListener {
        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent event) {
            buttonStart.setEnabled(false);
            boardBlocks.lockBlocks();
            suggestedBoardBlocks.copyLayout(boardBlocks);
            boardBlocks.setBlocksEditable(true, WofBoardBlock.BlockType.GLYPH);
            for(final Map.Entry<Character, LetterLabel> letterLabelEntry :
                    letterLabels.entrySet()) {
                letterLabelEntry.getValue().setEnabled(true);
            }
        }
    }
}
