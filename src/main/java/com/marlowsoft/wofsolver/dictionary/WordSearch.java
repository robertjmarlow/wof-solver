package com.marlowsoft.wofsolver.dictionary;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Inject;

import java.util.regex.Pattern;

/**
 * Gets a collection of matched words based on a {@link WordSearchQueryImpl}.
 */
public class WordSearch {
    private final ImmutableList<String> dictionaryEntries;
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
    public ImmutableList<String> getMatchedWords(final WordSearchQueryImpl searchQuery) {
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
    public ImmutableList<String> getMatchedWords(final WordSearchQueryImpl searchQuery,
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
        final StringBuilder stringBuilder = new StringBuilder();

        // TODO this could be vastly improved by using the used letters collection
        // for every known letter, insert the letter
        // for every unknown letter, insert a '.', or a wildcard
        for(int charIdx = 0; charIdx < searchQuery.getWordLength(); charIdx++) {
            final Character knownLetter = searchQuery.getKnownLetters().get(charIdx);
            stringBuilder.append(knownLetter != null ? knownLetter : '.');
        }

        return Pattern.compile(stringBuilder.toString(), Pattern.CASE_INSENSITIVE);
    }
}
