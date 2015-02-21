package com.marlowsoft.wofsolver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.bind.WofModule;
import com.marlowsoft.wofsolver.ui.WofBoard;

/**
 * Main entry point of the application.
 *
 */
public class Main {
    /**
     * Main entry point of the application.
     * @param args Arguments to the application.
     */
    public static void main(final String[] args) {
        Injector injector = Guice.createInjector(new WofModule());
        final WofBoard wofBoard = new WofBoard(injector);
        wofBoard.pack();
        wofBoard.setVisible(true);
        System.exit(0);
    }
}
