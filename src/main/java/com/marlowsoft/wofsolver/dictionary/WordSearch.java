package com.marlowsoft.wofsolver.dictionary;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Inject;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Gets a collection of matched words based on a {@link WordSearchQueryImpl}.
 */
public class WordSearch {
    private final List<String> dictionaryEntries;
    private final ImmutableMultimap<Integer, String> knownLengthEntries;

    /**
     * Initializes the word list.
     * @param wordList All known words; i.e. a dictionary.
     * @throws Exception If a problem occurs while creating the word list.
     */
    @Inject
    public WordSearch(final WordList wordList) throws Exception {
        final ImmutableList.Builder<String> dictionaryEntriesBuilder =
                ImmutableList.builder();
        final ImmutableMultimap.Builder<Integer, String> knownLengthEntriesBuilder =
                ImmutableMultimap.builder();

        for(final String dictionaryEntry : wordList.getWordList()) {
            dictionaryEntriesBuilder.add(dictionaryEntry);
            knownLengthEntriesBuilder.put(dictionaryEntry.length(), dictionaryEntry);
        }

        dictionaryEntries = dictionaryEntriesBuilder.build();
        knownLengthEntries = knownLengthEntriesBuilder.build();
    }

    // TODO add a "give up" overload for getMatchedWords?

    /**
     * Get <i>all</i> matched words, based on the specified query.
     * @param searchQuery The query to match words.
     * @return <i>All</i> matched words, based on the specified query.
     */
    public List<String> getMatchedWords(final WordSearchQueryImpl searchQuery) {
        // the length of all the dictionary entries ought to be a
        // good way to say, "give me everything".
        return getMatchedWords(searchQuery, dictionaryEntries.size());
    }

    /**
     * Get <i>up to</i> the specified number of matched words, based on the specified query.
     * @param searchQuery The query to match words.
     * @param matchedWordLimit The maximum number of words to match.
     * @return A <i>maximum</i> of the specified amount of limit of matched words.
     */
    public List<String> getMatchedWords(final WordSearchQueryImpl searchQuery,
                                                 final int matchedWordLimit) {
        final ImmutableList.Builder<String> matchedWordsBuilder = ImmutableList.builder();

        // first, get the matched word length; this should narrow things down a lot
        ImmutableList<String> sameLengthWords =
                knownLengthEntries.get(searchQuery.getWordLength()).asList();

        // next, apply a pattern to all the words and
        // bail if the matched word count reaches the specified limit, or
        // list of known words is exhausted
        final Pattern wordPattern = getWordPattern(searchQuery);
        int matchedWordCount = 0;
        int curWordIdx = 0;
        while((matchedWordCount < matchedWordLimit) &&
                (curWordIdx < sameLengthWords.size())) {
            final String curWord = sameLengthWords.get(curWordIdx);
            if(wordPattern.matcher(curWord).matches()) {
                matchedWordsBuilder.add(curWord);
                matchedWordCount++;
            }
            curWordIdx++;
        }

        return matchedWordsBuilder.build();
    }

    /**
     * Get a regex {@link java.util.regex.Pattern} based on the specified
     * {@link WordSearchQueryImpl}.
     * @param searchQuery The query used to build the {@link java.util.regex.Pattern}.
     * @return A {@link java.util.regex.Pattern} optimized for searching for
     * word matches based on the specified {@link WordSearchQueryImpl}.
     */
    private Pattern getWordPattern(final WordSearchQueryImpl searchQuery) {
        final Set<Character> usedLetters = searchQuery.getUsedLetters();
        final StringBuilder usedLettersRegexBuilder = new StringBuilder();
        final String usedLettersRegex;
        final StringBuilder regexStringBuilder = new StringBuilder();

        // if there aren't any used letters, an "any letter" will have to do
        if(usedLetters.isEmpty()) {
            usedLettersRegexBuilder.append(".");
        } else {
            usedLettersRegexBuilder.append("[^");
            for (final char usedLetter : usedLetters) {
                usedLettersRegexBuilder.append(usedLetter);
            }
            usedLettersRegexBuilder.append("]");
        }

        usedLettersRegex = usedLettersRegexBuilder.toString();

        // for every known letter, insert the letter
        // for every unknown letter, use the used letters regex string
        for(int charIdx = 0; charIdx < searchQuery.getWordLength(); charIdx++) {
            final Character knownLetter = searchQuery.getKnownLetters().get(charIdx);
            regexStringBuilder.append(knownLetter != null ? knownLetter : usedLettersRegex);
        }

        return Pattern.compile(regexStringBuilder.toString(), Pattern.CASE_INSENSITIVE);
    }
}
