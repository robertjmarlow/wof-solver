package com.marlowsoft.wofsolver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.marlowsoft.wofsolver.dictionary.WordSearchQueryImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link com.marlowsoft.wofsolver.dictionary.WordSearchQueryImpl.WordSearchQueryBuilder} class.
 */
public class WordSearchQueryImplBuilderTest {
    /**
     * Verify that a {@link com.marlowsoft.wofsolver.dictionary.WordSearchQueryImpl.WordSearchQueryBuilder} builds a
     * {@link com.marlowsoft.wofsolver.dictionary.WordSearchQueryImpl} correctly.
     */
    @Test
    public void testWordSearchQueryBuild() {
        final int expectedLength = 12;
        final ImmutableList<Character> expectedUsedLetters = ImmutableList.of('t', 'E', 's', 't');
        final ImmutableMap<Integer, Character> expectedKnownLetters = ImmutableMap.of(2, 'o', 3, 'b', 5, 'r');

        final WordSearchQueryImpl.WordSearchQueryBuilder queryBuilder = new WordSearchQueryImpl.WordSearchQueryBuilder();
        queryBuilder.setWordLength(expectedLength);
        for(final Character expectedUsedLetter : expectedUsedLetters) {
            queryBuilder.addUsedLetter(expectedUsedLetter);
        }
        for(final ImmutableMap.Entry<Integer, Character> expectedKnownLetter : expectedKnownLetters.entrySet()) {
            queryBuilder.addKnownLetter(expectedKnownLetter.getKey(), expectedKnownLetter.getValue());
        }

        final WordSearchQueryImpl searchQuery = queryBuilder.build();

        Assert.assertEquals(expectedLength, searchQuery.getWordLength());
        Assert.assertTrue(searchQuery.getUsedLetters().contains('t'));
        Assert.assertTrue(searchQuery.getUsedLetters().contains('E'));
        Assert.assertFalse(searchQuery.getUsedLetters().contains('z'));
        Assert.assertEquals(3, searchQuery.getUsedLetters().size());
        Assert.assertTrue(searchQuery.getKnownLetters().containsKey(2));
        Assert.assertTrue(searchQuery.getKnownLetters().containsKey(5));
        Assert.assertFalse(searchQuery.getKnownLetters().containsKey(1));
        Assert.assertEquals((Character)'o', searchQuery.getKnownLetters().get(2));
        Assert.assertEquals((Character)'r', searchQuery.getKnownLetters().get(5));
    }
}
