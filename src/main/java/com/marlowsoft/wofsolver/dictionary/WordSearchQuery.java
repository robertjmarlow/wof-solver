package com.marlowsoft.wofsolver.dictionary;

import java.util.Map;
import java.util.Set;

/**
 * Defines everything needed for a word search.
 */
public interface WordSearchQuery {
    /**
     * Gets the length of the word.
     * @return The length of the word.
     */
    int getWordLength();

    /**
     * Get all letters that have been used.
     * @return All letters that have been used.
     */
    Set<Character> getUsedLetters();

    /**
     * Get all known letters in the word where
     * the key is the index of the character and
     * the key is the known character.
     * @return All known letters in the word.
     */
    Map<Integer, Character> getKnownLetters();
}
