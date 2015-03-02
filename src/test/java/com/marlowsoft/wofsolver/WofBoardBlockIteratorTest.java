package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.*;
import org.junit.*;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.WofBoardBlockIterator} class.
 */
public class WofBoardBlockIteratorTest {

    /**
     * Verify that the <code>remove</code> function will throw a
     * {@link java.lang.UnsupportedOperationException}.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testRemove() {
        final WofBoardBlockIterator iterator = new WofBoardBlockIterator(new WofBoardBlocks());
        iterator.remove();
    }
}
