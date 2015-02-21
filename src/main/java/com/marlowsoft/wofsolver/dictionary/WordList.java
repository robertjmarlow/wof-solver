package com.marlowsoft.wofsolver.dictionary;

import com.google.common.collect.ImmutableList;

/**
 * Defines a way to get all known words.
 */
public interface WordList {
    /**
     * Get all known words that can exist on the board.
     * @return All known words; i.e. a dictionary.
     * @throws Exception If a problem occurs during the creation of the word list.
     */
    ImmutableList<String> getWordList() throws Exception;
}
