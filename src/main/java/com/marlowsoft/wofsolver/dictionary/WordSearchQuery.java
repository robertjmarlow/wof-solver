package com.marlowsoft.wofsolver.dictionary;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Created by RM025953 on 2/15/2015.
 */
public class WordSearchQuery {
    private final int wordLength;
    private final ImmutableSet<Character> usedLetters;
    private final ImmutableMap<Integer, Character> knownLetters;

    private WordSearchQuery(final int wordLength,
                            final ImmutableSet<Character> usedLetters,
                            final ImmutableMap<Integer, Character> knownLetters) {
        this.wordLength = wordLength;
        this.usedLetters = usedLetters;
        this.knownLetters = knownLetters;
    }

    public int getWordLength() {
        return wordLength;
    }

    public ImmutableSet<Character> getUsedLetters() {
        return usedLetters;
    }

    public ImmutableMap<Integer, Character> getKnownLetters() {
        return knownLetters;
    }

    /**
     * Things we'll need in here:
     * 1. Letters that have been used
     * 2. Word length
     * 3. Known letters (at known certain positions)
     * 4. How are we going to handle dashes and apostrophes? (there aren't any in the dictionary)
     */
    public static class WordSearchQueryBuilder {
        public WordSearchQueryBuilder() {
            wordLength = 0;
            usedLettersBuilder = ImmutableSet.builder();
            knownLettersBuilder = ImmutableMap.builder();
        }

        private int wordLength;
        private final ImmutableSet.Builder<Character> usedLettersBuilder;
        private final ImmutableMap.Builder<Integer, Character> knownLettersBuilder;

        public WordSearchQueryBuilder addUsedLetter(final Character usedLetter) {
            usedLettersBuilder.add(usedLetter);
            return this;
        }

        public WordSearchQueryBuilder addKnownLetter(final Integer index, final Character knownLetter) {
            knownLettersBuilder.put(index, knownLetter);
            return this;
        }

        public WordSearchQueryBuilder setWordLength(final int wordLength) {
            this.wordLength = wordLength;
            return this;
        }

        public WordSearchQuery build() {
            return new WordSearchQuery(wordLength, usedLettersBuilder.build(), knownLettersBuilder.build());
        }
    }
}
