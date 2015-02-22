package com.marlowsoft.wofsolver.ui;

import com.google.inject.Injector;
import com.marlowsoft.wofsolver.dictionary.WordSearch;

import javax.swing.*;
import java.awt.*;

/**
 * Main dialog of the application.
 * Displays a collection of {@link WofBoardBlocks}
 * and allows for user input.
 */
public class WofBoard extends JDialog {
    private JPanel contentPane;
    private JPanel gameBoardPane;
    private JPanel usedLettersPane;

    private final WofBoardBlocks boardBlocks;
    private final WordSearch wordSearch;

    /**
     * Initializes all elements in the board.
     *
     * @param injector Injector that will be used to create new objects.
     */
    public WofBoard(final Injector injector) {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setTitle("Wheel of Fortune Solver");

        // this centers the dialog
        setLocationRelativeTo(null);

        boardBlocks = new WofBoardBlocks();
        wordSearch = injector.getInstance(WordSearch.class);

        boardBlocks.addBlocksToPanel(gameBoardPane);
    }
}
