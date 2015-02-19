package com.marlowsoft.wofsolver;

import com.google.common.collect.ImmutableList;
import com.marlowsoft.wofsolver.dictionary.WordSearch;
import com.marlowsoft.wofsolver.dictionary.WordSearchQuery;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by RM025953 on 2/18/2015.
 */
public class WordSearchTest {
    @Test
    public void TestWordSearchLimited() throws IOException {
        final int matchedWordLimit = 18;
        final WordSearch wordSearch = new WordSearch();
        final WordSearchQuery.WordSearchQueryBuilder queryBuilder = new WordSearchQuery.WordSearchQueryBuilder();
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

    @Test
    public void TestWordSearchAll() throws IOException {
        final WordSearch wordSearch = new WordSearch();
        final WordSearchQuery.WordSearchQueryBuilder queryBuilder = new WordSearchQuery.WordSearchQueryBuilder();
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

    @Test
    public void TestWordSearchNoResults() throws IOException {
        // "Zzyzx" is an unincorporated town in California
        // http://en.wikipedia.org/wiki/Zzyzx,_California
        final WordSearch wordSearch = new WordSearch();
        final WordSearchQuery.WordSearchQueryBuilder queryBuilder = new WordSearchQuery.WordSearchQueryBuilder();
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
