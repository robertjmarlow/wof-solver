package com.marlowsoft.wofsolver;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.bind.WofModule;
import com.marlowsoft.wofsolver.dictionary.WordSearch;
import com.marlowsoft.wofsolver.dictionary.WordSearchQueryImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests the {@link com.marlowsoft.wofsolver.dictionary.WordSearch} class.
 */
public class WordSearchTest {
    public static Injector injector;

    @BeforeClass
    public static void beforeClass() {
        injector = Guice.createInjector(new WofModule());
    }

    /**
     * Limit the word search to a fixed amount. Verify the word is found and the fixed amount is met.
     * @throws IOException
     */
    @Test
    public void TestWordSearchLimited() throws IOException {
        final int matchedWordLimit = 18;
        final WordSearch wordSearch = injector.getInstance(WordSearch.class);
        final WordSearchQueryImpl.WordSearchQueryBuilder queryBuilder = new WordSearchQueryImpl.WordSearchQueryBuilder();
        queryBuilder.setWordLength(5);
        queryBuilder.addKnownLetter(0, 'a');
        queryBuilder.addKnownLetter(3, 'l');
        queryBuilder.addKnownLetter(4, 'e');

        final ImmutableList<String> wordSearchResults = wordSearch.getMatchedWords(queryBuilder.build(), matchedWordLimit);

        // "apple" ought to be in here somewhere
        boolean foundAnApple = false;
        for(final String wordSearchResult : wordSearchResults) {
            if(wordSearchResult.equals("apple")) {
                foundAnApple = true;
                break;
            }
        }
        Assert.assertEquals(matchedWordLimit, wordSearchResults.size());
        Assert.assertTrue(foundAnApple);
    }

    /**
     * Search through the <i>entire</i> dictionary for a word. Verify the word is found.
     * @throws IOException
     */
    @Test
    public void TestWordSearchAll() throws IOException {
        final WordSearch wordSearch = injector.getInstance(WordSearch.class);
        final WordSearchQueryImpl.WordSearchQueryBuilder queryBuilder = new WordSearchQueryImpl.WordSearchQueryBuilder();
        queryBuilder.setWordLength(3);
        queryBuilder.addKnownLetter(0, 'd');
        queryBuilder.addKnownLetter(1, 'o');

        final ImmutableList<String> wordSearchResults = wordSearch.getMatchedWords(queryBuilder.build());

        // "dog" ought to be in here somewhere
        boolean foundADog = false;
        for(final String wordSearchResult : wordSearchResults) {
            if(wordSearchResult.equals("dog")) {
                foundADog = true;
                break;
            }
        }
        Assert.assertTrue(foundADog);
    }

    /**
     * Search through the dictionary for a word that doesn't exist. Verify the returned search result count is zero.
     * @throws IOException
     */
    @Test
    public void TestWordSearchNoResults() throws IOException {
        // "Zzyzx" is an unincorporated town in California
        // http://en.wikipedia.org/wiki/Zzyzx,_California
        final WordSearch wordSearch = injector.getInstance(WordSearch.class);
        final WordSearchQueryImpl.WordSearchQueryBuilder queryBuilder = new WordSearchQueryImpl.WordSearchQueryBuilder();
        queryBuilder.setWordLength(5);
        queryBuilder.addKnownLetter(0, 'z');
        queryBuilder.addKnownLetter(1, 'z');
        queryBuilder.addKnownLetter(2, 'y');
        queryBuilder.addKnownLetter(3, 'z');
        queryBuilder.addKnownLetter(4, 'x');

        final ImmutableList<String> wordSearchResults = wordSearch.getMatchedWords(queryBuilder.build());
        Assert.assertEquals(0, wordSearchResults.size());
    }
}
