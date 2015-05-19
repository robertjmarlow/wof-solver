package com.marlowsoft.wofsolver;

import com.google.common.collect.Lists;
import com.marlowsoft.wofsolver.ui.WofBoardBlock;
import com.marlowsoft.wofsolver.ui.WofBoardWord;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.WofBoardWord} class.
 */
public class WofBoardWordTest {
    /**
     * Verifies the length is retrieved properly.
     */
    @Test
    public void testGetLength() {
        final List<WofBoardBlock> boardBlocks = Lists.newArrayList();
        WofBoardWord boardWord = new WofBoardWord(boardBlocks);

        Assert.assertEquals(0, boardWord.getLength());

        boardBlocks.add(new WofBoardBlock(WofBoardBlock.BlockType.GLYPH));
        boardBlocks.add(new WofBoardBlock(WofBoardBlock.BlockType.GLYPH));
        boardBlocks.add(new WofBoardBlock(WofBoardBlock.BlockType.GLYPH));
        boardWord = new WofBoardWord(boardBlocks);

        Assert.assertEquals(3, boardWord.getLength());
    }

    /**
     * Verifies that getting known letters for this word functions properly.
     */
    @Test
    public void testGetKnownLetters() {
        final List<WofBoardBlock> boardBlocks = Lists.newArrayList();
        WofBoardWord boardWord = new WofBoardWord(boardBlocks);

        Assert.assertEquals(0, boardWord.getKnownLetters().entrySet().size());

        WofBoardBlock boardBlock = new WofBoardBlock(WofBoardBlock.BlockType.GLYPH);
        boardBlock.setText("A");
        boardBlocks.add(boardBlock);

        boardBlock = new WofBoardBlock(WofBoardBlock.BlockType.GLYPH);
        boardBlock.setText("B");
        boardBlocks.add(boardBlock);

        boardBlock = new WofBoardBlock(WofBoardBlock.BlockType.GLYPH);
        boardBlock.setText("");
        boardBlocks.add(boardBlock);

        boardBlock = new WofBoardBlock(WofBoardBlock.BlockType.GLYPH);
        boardBlock.setText("C");
        boardBlocks.add(boardBlock);

        boardWord = new WofBoardWord(boardBlocks);

        final Map<Integer, Character> knownLetters = boardWord.getKnownLetters();

        Assert.assertTrue(knownLetters.get(0).equals('A'));
        Assert.assertTrue(knownLetters.get(1).equals('B'));
        Assert.assertTrue(knownLetters.get(3).equals('C'));
    }

    /**
     * Verifies that setting the suggested words will create the proper html string.
     */
    @Test
    public void testSetSuggestedWords() {
        final WofBoardBlock wofBoardBlock = new WofBoardBlock(WofBoardBlock.BlockType.GLYPH);
        final List<WofBoardBlock> boardBlocks = Lists.newArrayList();
        boardBlocks.add(wofBoardBlock);

        final WofBoardWord wofBoardWord = new WofBoardWord(boardBlocks);

        wofBoardWord.setSuggestedWords(Lists.newArrayList("foo", "bar", "baz"), false);
        final String tooltipText = wofBoardBlock.getToolTipText();

        Assert.assertTrue(tooltipText.contains("Suggested Word(s)"));
        Assert.assertTrue(tooltipText.contains("(3)"));
        Assert.assertTrue(tooltipText.contains("foo"));
        Assert.assertTrue(tooltipText.contains("bar"));
        Assert.assertTrue(tooltipText.contains("baz"));
    }
}
