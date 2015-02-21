package com.marlowsoft.wofsolver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.bind.WofModule;
import com.marlowsoft.wofsolver.ui.WofBoard;

import java.io.IOException;

/**
 * Main entry point of the application.
 *
 */
public class Main {
    public static void main(final String[] args) throws IOException {
        Injector injector = Guice.createInjector(new WofModule());
        final WofBoard wofBoard = new WofBoard(injector);
        wofBoard.pack();
        wofBoard.setVisible(true);
        System.exit(0);
    }
}
