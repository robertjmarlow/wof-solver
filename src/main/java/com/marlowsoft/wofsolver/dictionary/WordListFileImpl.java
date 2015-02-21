package com.marlowsoft.wofsolver.dictionary;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.File;
import java.io.IOException;

/**
 * File-backed implementation get get all words.
 */
public class WordListFileImpl implements WordList {
    final ImmutableList<String> wordList;

    @Inject
    public WordListFileImpl(@Named("WordListFileLoc")String filePath) throws IOException {
        final CharSource dictionarySource = Files.asCharSource(new File(filePath), Charsets.UTF_8);
        wordList = dictionarySource.readLines();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<String> getWordList() {
        return wordList;
    }
}
