package com.marlowsoft.wofsolver.ui;

import com.google.inject.Injector;
import com.marlowsoft.wofsolver.dictionary.WordSearch;

import javax.swing.*;

/**
 * Main dialog of the application.
 * Displays a collection of {@link com.marlowsoft.wofsolver.ui.WofBoardBlocks}
 * and allows for user input.
 */
public class WofBoard extends JDialog {
    private JPanel contentPane;

    private final WofBoardBlocks boardBlocks;
    private final WordSearch wordSearch;

    /**
     * Initializes all elements in the board.
     * @param injector Injector that will be used to create new objects.
     */
    public WofBoard(final Injector injector) {
        //contentPane = new JPanel();

        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setTitle("Wheel of Fortune Solver");

        // this centers the dialog
        setLocationRelativeTo(null);

        boardBlocks = new WofBoardBlocks();
        wordSearch = injector.getInstance(WordSearch.class);
    }
}
