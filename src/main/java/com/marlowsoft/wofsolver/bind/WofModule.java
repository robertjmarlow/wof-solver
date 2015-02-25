package com.marlowsoft.wofsolver.bind;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.marlowsoft.wofsolver.dictionary.WordList;
import com.marlowsoft.wofsolver.dictionary.WordListFileImpl;

/**
 * Binds the reading of the word list to a file-backed implementation.
 */
public class WofModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WordList.class).to(WordListFileImpl.class);
        bind(String.class)
                .annotatedWith(Names.named("WordListFileLoc"))
                .toInstance("words.txt");
    }
}
