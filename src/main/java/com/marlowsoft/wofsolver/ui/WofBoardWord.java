package com.marlowsoft.wofsolver.ui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * Defines a single word on the board.
 */
public class WofBoardWord {
    private final List<WofBoardBlock> wordBlocks;
    private final int row;
    private final int column;

    /**
     *
     * @param wordBlocks The {@link WofBoardBlock} collection that comprises this word.
     * @param row The row this word begins on.
     * @param column The column this word begins on.
     */
    public WofBoardWord(final List<WofBoardBlock> wordBlocks, final int row, final int column) {
        this.wordBlocks = ImmutableList.copyOf(wordBlocks);
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the length of the word.
     * @return The length of the word.
     */
    public int getLength() {
        return wordBlocks.size();
    }

    /**
     * Gets the row this word begins on.
     * @return The row this word begins on.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column this word begins on.
     * @return The column this word begins on.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Get known letters in this word.
     * @return Known letters in this word where the key is the index of the character in the word
     * and the value is the character.
     */
    public Map<Integer, Character> getKnownLetters() {
        final ImmutableMap.Builder<Integer, Character> knownLettersBuilder = ImmutableMap.builder();

        for(int boardBlockIdx = 0; boardBlockIdx < wordBlocks.size(); boardBlockIdx++) {
            final WofBoardBlock wofBoardBlock = wordBlocks.get(boardBlockIdx);
            final String boardBlockText = wofBoardBlock.getText();
            if(!boardBlockText.isEmpty()) {
                knownLettersBuilder.put(boardBlockIdx, boardBlockText.charAt(0));
            }
        }

        return knownLettersBuilder.build();
    }

    /**
     * Set the suggested words for this board word.
     * @param suggestedWords The suggested words for this board word.
     * @param moreWords Whether or not there are actually more suggested words than are suggested in
     *                  {@code suggestedWords}.
     */
    public void setSuggestedWords(final List<String> suggestedWords, final boolean moreWords) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html>");
        stringBuilder.append("<div><u><b>Suggested Word(s)</b> (");
        if(moreWords) {
            stringBuilder.append("more than ");
        }
        stringBuilder.append(suggestedWords.size());
        stringBuilder.append(")</u></div>");
        for(final String suggestedWord : suggestedWords) {
            stringBuilder.append(suggestedWord);
            stringBuilder.append("<br />");
        }
        stringBuilder.append("</html>");

        for(final WofBoardBlock wordBlock : wordBlocks) {
            wordBlock.setToolTipText(stringBuilder.toString());
        }
    }
}
