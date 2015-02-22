package com.marlowsoft.wofsolver.ui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines a single block on the board.
 */
public class WofBoardBlock extends JTextField {
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
        this.blockType = blockType;
        setColumns(1);
        if(blockType == BlockType.NO_CONTENT) {
            setEditable(false);
        } else {
            setDocument(new BoardBlockFieldLimit());
        }
    }

    /**
     * Set the block type.
     * @param blockType The block type to set.
     */
    public void setBlockType(final BlockType blockType) {
        this.blockType = blockType;
        // TODO apply some sort of visual stuff here
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
}
