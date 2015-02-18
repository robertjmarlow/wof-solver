package com.marlowsoft.wofsolver.ui;

import com.marlowsoft.wofsolver.dictionary.WordSearch;

import javax.swing.*;
import java.io.IOException;

public class WofBoard extends JDialog {
    private final JPanel contentPane;

    private final WofBoardBlocks boardBlocks;
    private final WordSearch wordSearch;

    public WofBoard() throws IOException {
        contentPane = new JPanel();

        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setTitle("Wheel of Fortune Solver");

        // this centers the dialog
        setLocationRelativeTo(null);

        boardBlocks = new WofBoardBlocks();
        wordSearch = new WordSearch();
    }
}
