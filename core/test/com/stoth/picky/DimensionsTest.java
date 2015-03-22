package com.stoth.picky;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DimensionsTest {
    @Test
    public void testConstruction() {
        int nRows = 12;
        int nCols = 13;
        Dimensions d = Dimensions.create(nRows, nCols);
        assertEquals(nRows, d.getnRows());
        assertEquals(nCols, d.getnCols());
    }

    @Test
    public void testIsInBounds() {
        Dimensions d = Dimensions.create(5, 4);
        assertFalse(d.isInBounds(Position.create(-1, 2)));
        assertFalse(d.isInBounds(Position.create(2, -1)));
        assertFalse(d.isInBounds(Position.create(6, 2)));
        assertFalse(d.isInBounds(Position.create(2, 6)));
        assertTrue(d.isInBounds(Position.create(0, 0)));
        assertTrue(d.isInBounds(Position.create(4, 3)));
        assertFalse(d.isInBounds(Position.create(5, 4)));
    }
}