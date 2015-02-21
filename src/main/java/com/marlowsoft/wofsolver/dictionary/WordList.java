package com.marlowsoft.wofsolver.dictionary;

import com.google.common.collect.ImmutableList;

/**
 * Defines a way to get all known words.
 */
public interface WordList {
    /**
     * Get all known words that can exist on the board.
     * @return All known words; i.e. a dictionary.
     * @throws Exception
     */
    ImmutableList<String> getWordList() throws Exception;
}
