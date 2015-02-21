package com.marlowsoft.wofsolver.dictionary;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Default implementation of {@link com.marlowsoft.wofsolver.dictionary.WordSearchQuery}.
 */
public class WordSearchQueryImpl implements WordSearchQuery {
    private final int wordLength;
    private final ImmutableSet<Character> usedLetters;
    private final ImmutableMap<Integer, Character> knownLetters;

    private WordSearchQueryImpl(final int wordLength,
                                final ImmutableSet<Character> usedLetters,
                                final ImmutableMap<Integer, Character> knownLetters) {
        this.wordLength = wordLength;
        this.usedLetters = usedLetters;
        this.knownLetters = knownLetters;
    }

    /**
     * {@inheritDoc}
     */
    public int getWordLength() {
        return wordLength;
    }

    /**
     * {@inheritDoc}
     */
    public ImmutableSet<Character> getUsedLetters() {
        return usedLetters;
    }

    /**
     * {@inheritDoc}
     */
    public ImmutableMap<Integer, Character> getKnownLetters() {
        return knownLetters;
    }

    /**
     * Builds a {@link com.marlowsoft.wofsolver.dictionary.WordSearchQueryImpl}.
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

        /**
         * Add a used letter to this builder.
         * @param usedLetter The used letter to add to this builder.
         * @return This, for chaining.
         */
        public WordSearchQueryBuilder addUsedLetter(final Character usedLetter) {
            usedLettersBuilder.add(usedLetter);
            return this;
        }

        /**
         * Add a known letter for this builder.
         * @param index The index of the known letter.
         * @param knownLetter The known letter.
         * @return This, for chaining.
         */
        public WordSearchQueryBuilder addKnownLetter(final Integer index, final Character knownLetter) {
            knownLettersBuilder.put(index, knownLetter);
            return this;
        }

        /**
         * Set the word length for this builder.
         * @param wordLength The length of the word.
         * @return This, for chaining.
         */
        public WordSearchQueryBuilder setWordLength(final int wordLength) {
            this.wordLength = wordLength;
            return this;
        }

        public WordSearchQueryImpl build() {
            // TODO should there be a thing in here to guarantee knownLetters' key isn't greater than wordLength?
            return new WordSearchQueryImpl(wordLength, usedLettersBuilder.build(), knownLettersBuilder.build());
        }
    }
}
