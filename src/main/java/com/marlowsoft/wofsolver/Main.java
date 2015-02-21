package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.WofBoard;

import java.io.IOException;

/**
 * Main entry point of the application.
 *
 */
public class Main {
    public static void main(final String[] args) throws IOException {
        final WofBoard wofBoard = new WofBoard();
        wofBoard.pack();
        wofBoard.setVisible(true);
        System.exit(0);
    }
}
