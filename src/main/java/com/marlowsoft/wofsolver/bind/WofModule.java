package com.marlowsoft.wofsolver.bind;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.marlowsoft.wofsolver.dictionary.WordList;
import com.marlowsoft.wofsolver.dictionary.WordListFileImpl;

/**
 * Created by RM025953 on 2/20/2015.
 */
public class WofModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WordList.class).to(WordListFileImpl.class);
        bind(String.class)
                .annotatedWith(Names.named("WordListFileLoc"))
                .toInstance("src/main/resources/words.txt");
    }
}
