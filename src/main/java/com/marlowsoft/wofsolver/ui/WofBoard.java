package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.*;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.dictionary.WordSearch;
import com.marlowsoft.wofsolver.dictionary.WordSearchQueryImpl;
import com.marlowsoft.wofsolver.ui.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Main dialog of the application.
 * Displays a collection of {@link WofBoardBlocks}
 * and allows for user input.
 */
public class WofBoard extends JDialog implements BlockValueChangedListener {
    private JPanel contentPane;
    private JPanel gameBoardPane;
    private JPanel usedLettersPane;
    private JPanel suggestedSolutionPane;
    private JButton buttonStart;
    private JButton buttonResetBoard;

    private final WofBoardBlocks boardBlocks;
    private final WofBoardBlocks suggestedBoardBlocks;
    private final WordSearch wordSearch;
    private final Map<Character, LetterLabel> letterLabels;
    private List<WofBoardWord> boardWords;

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
        boardWords = ImmutableList.of();

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
        // for any used characters on the board, mark them as correct
        for(final Character usedChar : boardBlocks.getUsedCharsOnBoard()) {
            letterLabels.get(usedChar).setGuessType(LetterLabel.GuessType.CORRECT);
        }

        // for any unused characters that haven't been marked as incorrect, mark them as none
        for(final Character unusedChar : boardBlocks.getUnusedCharsOnBoard()) {
            final LetterLabel letterLabel = letterLabels.get(unusedChar);
            if(letterLabel.getGuessType() != LetterLabel.GuessType.INCORRECT) {
                letterLabel.setGuessType(LetterLabel.GuessType.NONE);
            }
        }

        // find suggestions for every word
        for(final WofBoardWord boardWord : boardWords) {
            final List<String> wordSearchResults =
                    getSuggestedWords(boardWord, 10);

            System.out.println("Found " + wordSearchResults.size() + ":");
            for(int wordIdx = 0; wordIdx < wordSearchResults.size(); wordIdx++) {
                System.out.println(" [" + wordIdx + "] " + wordSearchResults.get(wordIdx));
            }
        }
    }

    /**
     * Get suggested words based on the specified board word.
     * @param wofBoardWord The board word to search with.
     * @param suggestionLimit The maximum amount of words to return.
     * @return Suggested words. This collection's maximum length will be
     * the specified suggestion limit.
     */
    private List<String> getSuggestedWords(final WofBoardWord wofBoardWord,
                                                    final int suggestionLimit) {
        final WordSearchQueryImpl.WordSearchQueryBuilder queryBuilder =
                new WordSearchQueryImpl.WordSearchQueryBuilder();

        // set the length of the word
        queryBuilder.setWordLength(wofBoardWord.getLength());

        // set all known letters
        for(final Map.Entry<Integer, Character> entry :
                wofBoardWord.getKnownLetters().entrySet()) {
            queryBuilder.addKnownLetter(entry.getKey(), entry.getValue());
        }

        // set all incorrect guesses
        for(final Character incorrectChar : getIncorrectLetterGuesses()) {
            queryBuilder.addUsedLetter(incorrectChar);
        }

        // let's find some words!
        return wordSearch.getMatchedWords(queryBuilder.build(), suggestionLimit);
    }

    /**
     * Create a collection of words on the board.
     * @return A collection of words on the board.
     */
    private List<WofBoardWord> getBoardWords() {
        final ImmutableList.Builder<WofBoardWord> boardWordBuilder = ImmutableList.builder();

        // go through all blocks on the board
        for(int curRow = 0; curRow < WofBoardBlocks.ROW_COUNT; curRow++) {
            boolean readingAWord = false;
            List<WofBoardBlock> boardWord = Lists.newArrayList();
            for(int curColumn = 0; curColumn < WofBoardBlocks.COLUMN_COUNT; curColumn++) {
                final WofBoardBlock wofBoardBlock = boardBlocks.getBlock(curRow, curColumn);

                // if the block is a glyph block, this is part of a word
                if(wofBoardBlock.getBlockType() == WofBoardBlock.BlockType.GLYPH) {
                    if(!readingAWord) {
                        boardWord = Lists.newArrayList();
                    }
                    readingAWord = true;
                    boardWord.add(wofBoardBlock);
                }
                // check if a word should to be added to the collection
                // (i.e. if this is the end of a word)
                // if this is a no glyph block OR if this is the last column of a row
                else if(wofBoardBlock.getBlockType() == WofBoardBlock.BlockType.NO_GLYPH ||
                        curColumn == WofBoardBlocks.COLUMN_COUNT - 1) {
                    if(readingAWord) {
                        boardWordBuilder.add(new WofBoardWord(boardWord));
                    }
                    readingAWord = false;
                }
            }
        }

        return boardWordBuilder.build();
    }

    /**
     * Gets all characters marked as "incorrect guess" from the collection of letter labels.
     * @return All characters marked as "incorrect guess".
     */
    private List<Character> getIncorrectLetterGuesses() {
        final ImmutableList.Builder<Character> incorrectLettersBuilder =
                new ImmutableList.Builder<Character>();

        for(char curChar = 'A'; curChar <= 'Z'; curChar++) {
            final LetterLabel letterLabel = letterLabels.get(curChar);
            if(letterLabel.getGuessType() == LetterLabel.GuessType.INCORRECT) {
                incorrectLettersBuilder.add(curChar);
            }
        }

        return incorrectLettersBuilder.build();
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
                    letterLabelEntry.getValue().setGuessType(LetterLabel.GuessType.NONE);
                }
                boardWords = ImmutableList.of();
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
            boardWords = getBoardWords();
        }
    }
}
