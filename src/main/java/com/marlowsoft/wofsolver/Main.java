package com.marlowsoft.wofsolver;

import com.google.inject.Guice;
import com.marlowsoft.wofsolver.bind.WofModule;
import com.marlowsoft.wofsolver.ui.WofBoard;
import com.marlowsoft.wofsolver.ui.event.*;

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
        final WofBoard wofBoard = new WofBoard(Guice.createInjector(new WofModule()));
        BlockValueChangedDispatcher.addListener(wofBoard);
        wofBoard.pack();
        wofBoard.setVisible(true);
        System.exit(0);
    }
}
