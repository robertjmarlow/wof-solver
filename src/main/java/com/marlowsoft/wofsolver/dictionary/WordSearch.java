package com.marlowsoft.wofsolver.dictionary;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gets a collection of matched words based on a {@link com.marlowsoft.wofsolver.dictionary.WordSearchQuery}.
 */
public class WordSearch {
    private final ImmutableList<String> dictionaryEntries;
    private final ImmutableMultimap<Integer, String> knownLengthEntries;

    public WordSearch() throws IOException {
        // TODO do we want to put this behind an interface or something?
        // TODO hardcoding that file isn't a good thing...
        final CharSource dictionarySource = Files.asCharSource(new File("src/main/resources/words.txt"), Charsets.UTF_8);

        final ImmutableList.Builder<String> dictionaryEntriesBuilder = ImmutableList.builder();
        final ImmutableMultimap.Builder<Integer, String> knownLengthEntriesBuilder = ImmutableMultimap.builder();

        for(final String dictionaryEntry : dictionarySource.readLines()) {
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
    public ImmutableList<String> getMatchedWords(final WordSearchQuery searchQuery) {
        // the length of all the dictionary entries ought to be a good way to say, "give me everything".
        return getMatchedWords(searchQuery, dictionaryEntries.size());
    }

    /**
     * Get <i>up to</i> the specified number of matched words, based on the specified query.
     * @param searchQuery The query to match words.
     * @param matchedWordLimit The maximum number of words to match.
     * @return A <i>maximum</i> of the specified amount of limit of matched words.
     */
    public ImmutableList<String> getMatchedWords(final WordSearchQuery searchQuery, final int matchedWordLimit) {
        final ImmutableList.Builder<String> matchedWordsBuilder = ImmutableList.builder();

        // first, get the matched word length; this should narrow things down a lot
        ImmutableList<String> sameLengthWords = knownLengthEntries.get(searchQuery.getWordLength()).asList();

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
     * Get a regex {@link java.util.regex.Pattern} based on the specified {@link com.marlowsoft.wofsolver.dictionary.WordSearchQuery}.
     * @param searchQuery The query used to build the {@link java.util.regex.Pattern}.
     * @return A {@link java.util.regex.Pattern} optimized for searching for word matches based on the specified
     * {@link com.marlowsoft.wofsolver.dictionary.WordSearchQuery}.
     */
    private Pattern getWordPattern(final WordSearchQuery searchQuery) {
        final StringBuilder stringBuilder = new StringBuilder();

        // for every known letter, insert the letter
        // for every unknown letter, insert a '.', or a wildcard
        for(int charIdx = 0; charIdx < searchQuery.getWordLength(); charIdx++) {
            final Character knownLetter = searchQuery.getKnownLetters().get(charIdx);
            stringBuilder.append(knownLetter != null ? knownLetter : '.');
        }

        return Pattern.compile(stringBuilder.toString());
    }
}
