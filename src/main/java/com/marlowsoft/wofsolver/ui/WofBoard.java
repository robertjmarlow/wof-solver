package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.dictionary.WordSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main dialog of the application.
 * Displays a collection of {@link WofBoardBlocks}
 * and allows for user input.
 */
public class WofBoard extends JDialog {
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
            usedLettersPane.add(letterLabel, constraints);
            letterLabelsBuilder.put(curChar, letterLabel);
        }
        letterLabels = letterLabelsBuilder.build();

        buttonStart.addActionListener(new StartGameActionListener(this));
        buttonResetBoard.addActionListener(new ResetBoardActionListener(this));
    }

    /**
     * Listener for clicking on the "Reset Board" button.
     */
    private static class ResetBoardActionListener implements ActionListener {
        private final WofBoard wofBoard;

        /**
         *
         * @param wofBoard The entire board.
         */
        public ResetBoardActionListener(final WofBoard wofBoard) {
            this.wofBoard = wofBoard;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent event) {
            if(JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to reset the game board?", "Reset Board?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ==
                    JOptionPane.YES_OPTION) {
                wofBoard.boardBlocks.unlockBlocks();
                wofBoard.boardBlocks.resetBlocks();
                wofBoard.suggestedBoardBlocks.resetBlocks();
                wofBoard.buttonStart.setEnabled(true);
            }
        }
    }

    /**
     * Listener for clicking on the "Start Game" button.
     */
    private static class StartGameActionListener implements ActionListener {
        private final WofBoard wofBoard;

        /**
         *
         * @param wofBoard The entire board.
         */
        public StartGameActionListener(final WofBoard wofBoard) {
            this.wofBoard = wofBoard;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent event) {
            wofBoard.buttonStart.setEnabled(false);
            wofBoard.boardBlocks.lockBlocks();
        }
    }
}
