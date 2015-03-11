package com.marlowsoft.wofsolver.ui;

import com.marlowsoft.wofsolver.ui.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Defines a single block on the board.
 */
public class WofBoardBlock extends JTextField {
    private static final Color GLYPH_BG_COLOR = new Color(255, 255, 255);
    private static final Color NO_GLYPH_BG_COLOR = new Color(27, 119, 56);
    private static final Color NO_CONTENT_BG_COLOR = new Color(233, 233, 233);

    private static final int FONT_SIZE = 36;
    private static final Font blockFont = new Font(Font.SERIF, Font.BOLD, FONT_SIZE);

    private boolean isLocked;

    // TODO make the border bigger; the real board blocks have big, chunky borders

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
     * Create a new block that is <i>not</i> a suggested block.
     * @param blockType The type of block.
     */
    public WofBoardBlock(final BlockType blockType) {
        this(blockType, false);
    }

    /**
     *
     * @param blockType The type of block.
     * @param suggestedBlock Whether or not the block is a suggested block.
     */
    public WofBoardBlock(final BlockType blockType, final boolean suggestedBlock) {
        setBlockType(blockType);
        isLocked = false;
        setFont(blockFont);
        setColumns(1);
        setHorizontalAlignment(SwingConstants.CENTER);
        setEditable(false);

        if(!suggestedBlock) {
            addMouseListener(new BoardBlockEventListener());
        }
    }

    /**
     * Set the block type, unless the block is locked.
     * @param blockType The block type to set.
     */
    public void setBlockType(final BlockType blockType) {
        if(isLocked) {
            return;
        }

        this.blockType = blockType;

        switch(blockType) {
            case GLYPH:
                setBackground(GLYPH_BG_COLOR);
                setDocument(new BoardBlockFieldLimit());
                getDocument().addDocumentListener(new BoardBlockDocumentListener());
                break;
            case NO_GLYPH:
                setBackground(NO_GLYPH_BG_COLOR);
                setDocument(new BoardBlockFieldLimit());
                setText("");
                break;
            case NO_CONTENT:
                setBackground(NO_CONTENT_BG_COLOR);
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
     * Disallow the changing of the type of block.
     */
    public void lockBlock() {
        isLocked = true;
    }

    /**
     * Allow the changing of the type of block.
     */
    public void unlockBlock() {
        isLocked = false;
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
    private static class BoardBlockFieldLimit extends PlainDocument {
        private final Pattern allowedLetters = Pattern.compile("[a-zA-Z'\\-]");

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
                super.insertString(0, str.substring(0, 1).toUpperCase(getDefaultLocale()), attr);
            }
        }
    }

    /**
     * Handler for events for a {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
     */
    private class BoardBlockEventListener implements MouseListener {
        /**
         * If the right-mouse button is clicked, switch glyph block to a no-glyph block and
         * vice-versa.
         * @param event The mouse event.
         */
        private void toggleBlockType(final MouseEvent event) {
            if(SwingUtilities.isRightMouseButton(event)) {
                switch(getBlockType()) {
                    case GLYPH:
                        setBlockType(BlockType.NO_GLYPH);
                        break;
                    case NO_GLYPH:
                        setBlockType(BlockType.GLYPH);
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
            grabFocus();
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

    /**
     * Handler for any {@link javax.swing.event.DocumentEvent} for this
     * {@link com.marlowsoft.wofsolver.ui.WofBoardBlock}.
     */
    private static class BoardBlockDocumentListener implements DocumentListener {
        /**
         * {@inheritDoc}
         */
        @Override
        public void insertUpdate(final DocumentEvent documentEvent) {
            BlockValueChangedDispatcher.dispatchOnBlockCharChanged();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void removeUpdate(final DocumentEvent documentEvent) {
            BlockValueChangedDispatcher.dispatchOnBlockCharChanged();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void changedUpdate(final DocumentEvent documentEvent) {
        }
    }
}
