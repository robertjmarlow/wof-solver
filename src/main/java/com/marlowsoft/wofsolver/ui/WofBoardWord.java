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

    /**
     *
     * @param wordBlocks The {@link WofBoardBlock} collection that comprises this word.
     */
    public WofBoardWord(final List<WofBoardBlock> wordBlocks) {
        final ImmutableList.Builder<WofBoardBlock> wordBlockBuilder = ImmutableList.builder();
        for(final WofBoardBlock wofBoardBlock : wordBlocks) {
            wordBlockBuilder.add(wofBoardBlock);
        }

        this.wordBlocks = wordBlockBuilder.build();
    }

    /**
     * Gets the length of the word.
     * @return The length of the word.
     */
    public int getLength() {
        return wordBlocks.size();
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
}
