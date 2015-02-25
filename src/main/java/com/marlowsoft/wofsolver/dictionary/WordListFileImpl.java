package com.marlowsoft.wofsolver.dictionary;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * File-backed implementation get get all words.
 */
public class WordListFileImpl implements WordList {
    /**
     * All known words.
     */
    final private ImmutableList<String> wordList;

    /**
     *
     * @param filePath The path to load the file from.
     * @throws IOException If a problem occurs when reading the file.
     */
    @Inject
    public WordListFileImpl(@Named("WordListFileLoc")String filePath)
            throws IOException {
        wordList = ImmutableList.copyOf(IOUtils.readLines(getClass().
                getClassLoader().getResourceAsStream(filePath)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<String> getWordList() {
        return wordList;
    }
}
