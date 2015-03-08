package com.marlowsoft.wofsolver;

import com.marlowsoft.wofsolver.ui.event.*;
import org.junit.*;

/**
 * Tests the {@link com.marlowsoft.wofsolver.BlockValueChangedDispatcherTest} class.
 */
public class BlockValueChangedDispatcherTest {
    private class OnBlockChangedListener implements BlockValueChangedListener {
        int onValueChangedCount;

        public OnBlockChangedListener() {
            onValueChangedCount = 0;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public void onValueChanged() {
            onValueChangedCount++;
        }

        public int getValueChangedCount() {
            return onValueChangedCount;
        }
    }

    /**
     * Tests proper dispatching of block value events when the character in the block is changed.
     */
    @Test
    public void testOnBlockCharChanged() {
        final OnBlockChangedListener listener = new OnBlockChangedListener();
        BlockValueChangedDispatcher.addListener(listener);

        BlockValueChangedDispatcher.dispatchOnBlockCharChanged();
        BlockValueChangedDispatcher.dispatchOnBlockCharChanged();
        BlockValueChangedDispatcher.dispatchOnBlockCharChanged();

        Assert.assertEquals(3, listener.getValueChangedCount());
    }

    /**
     * This test will create a new implementation of the abstract class
     * {@link com.marlowsoft.wofsolver.ui.event.BlockValueChangedDispatcher}. Due to strangeness in
     * Cobertura/Java, this needs to be done. See these links for more info:
     * http://sourceforge.net/p/cobertura/bugs/92/
     * http://cobertura.996293.n3.nabble.com/Uncovered-class-declaration-line-why-td2982.html
     */
    @SuppressWarnings("unused")
    @Test
    public void testOnBlockDecl() {
        final BlockValueChangedDispatcher dispatcher = new BlockValueChangedDispatcher() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }
}
