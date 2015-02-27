package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.dictionary.WordSearch;

import javax.swing.*;
import java.awt.*;

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
        wordSearch = injector.getInstance(WordSearch.class);

        boardBlocks.addBlocksToPanel(gameBoardPane);

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
    }
}
