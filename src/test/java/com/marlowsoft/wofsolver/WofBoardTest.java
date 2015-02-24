package com.marlowsoft.wofsolver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.marlowsoft.wofsolver.bind.WofModule;
import com.marlowsoft.wofsolver.ui.WofBoard;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

/**
 * Tests the {@link com.marlowsoft.wofsolver.ui.WofBoard} class.
 */
public class WofBoardTest {
    public static Injector injector;

    @BeforeClass
    public static void beforeClass() {
        injector = Guice.createInjector(new WofModule());
    }

    /**
     * Tests proper construction of the class.
     * This test might look barren, but some magic has to be performed behind the scenes to make
     * sure the dialog can be constructed outside of Intellij (e.g. Maven).
     * <ol>
     *     <li>
     *         A Maven plugin needs to inject Intellij's form bytecode into the generated binaries.
     *     </li>
     *     <li>
     *         A runtime dependency needs exist to avoid runtime errors.
     *     </li>
     * </ol>
     * Class construction without errors verifies these items are in place.
     */
    @Test
    public void testConstruction() {
        final WofBoard wofBoard = new WofBoard(injector);

        Assert.assertEquals(Dialog.ModalityType.APPLICATION_MODAL, wofBoard.getModalityType());
    }
}
