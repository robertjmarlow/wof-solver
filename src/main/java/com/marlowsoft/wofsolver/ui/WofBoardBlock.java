package com.marlowsoft.wofsolver.ui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Defines a single block on the board.
 */
public class WofBoardBlock extends JTextField {
    private static final Color GLYPH_BG_COLOR = new Color(255, 255, 255);
    private static final Color NO_GLYPH_BG_COLOR = new Color(27, 119, 56);
    private static final Color NO_CONTENT_BG_COLOR = new Color(255, 255, 255, 0);

    /**
     * Defines all possible block types.
     */
    public enum BlockType {
        NO_CONTENT,
        GLYPH,
        NO_GLYPH
    }

    private BlockType blockType;

    /**
     *
     * @param blockType The type of block.
     */
    public WofBoardBlock(final BlockType blockType) {
        setBlockType(blockType);

        setColumns(1);

        addMouseListener(new BoardBlockEventListener(this));
    }

    /**
     * Set the block type.
     * @param blockType The block type to set.
     */
    public void setBlockType(final BlockType blockType) {
        this.blockType = blockType;

        switch(blockType) {
            case GLYPH:
                setBackground(GLYPH_BG_COLOR);
                setDocument(new BoardBlockFieldLimit());
                setEditable(true);
                break;
            case NO_GLYPH:
                setBackground(NO_GLYPH_BG_COLOR);
                setDocument(new BoardBlockFieldLimit());
                setEditable(false);
                setText("");
                break;
            case NO_CONTENT:
                setBackground(NO_CONTENT_BG_COLOR);
                setEditable(false);
                setText("");
                break;
        }
    }

    /**
     * Get the block type.
     * @return The block type.
     */
    public BlockType getBlockType() {
        return blockType;
    }

    /**
     * Defines what characters are allowed in a word block.
     * <ol>
     *     <li>All letters in the alphabet, upper and lower case, are allowed.</li>
     *     <li>Apostrophes are allowed.</li>
     *     <li>Only one character at at time can be typed.</li>
     *     <li>A character can be entered in a word block only if the block is empty.</li>
     * </ol>
     */
    private class BoardBlockFieldLimit extends PlainDocument {
        private final Pattern allowedLetters = Pattern.compile("[a-zA-Z']");

        /**
         * {@inheritDoc}
         */
        @Override
        public void insertString(final int offset,
                                 final String str,
                                 final AttributeSet attr) throws BadLocationException {
            if((getLength() == 0) &&
                    (str.length() == 1) &&
                    (allowedLetters.matcher(str).matches())) {
                super.insertString(0, str.substring(0, 1).toUpperCase(), attr);
            }
        }
    }

    /**
     * Handler for events for a {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
     */
    private class BoardBlockEventListener implements MouseListener {
        final WofBoardBlock boardBlock;

        /**
         *
         * @param boardBlock The {@link com.marlowsoft.wofsolver.ui.WofBoardBlock} that this
         *                   listener will be listening to.
         */
        public BoardBlockEventListener(final WofBoardBlock boardBlock) {
            this.boardBlock = boardBlock;
        }

        /**
         * If the right-mouse button is clicked, switch glyph block to a no-glyph block and
         * vice-versa.
         * @param event The mouse event.
         */
        private void toggleBlockType(final MouseEvent event) {
            if(SwingUtilities.isRightMouseButton(event)) {
                switch(boardBlock.getBlockType()) {
                    case GLYPH:
                        boardBlock.setBlockType(BlockType.NO_GLYPH);
                        break;
                    case NO_GLYPH:
                        boardBlock.setBlockType(BlockType.GLYPH);
                        break;
                    default:
                        break;
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mousePressed(MouseEvent event) {
            toggleBlockType(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseEntered(MouseEvent event) {
            toggleBlockType(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseClicked(MouseEvent event) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseReleased(MouseEvent event) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseExited(MouseEvent event) {
        }
    }
}
