package com.stoth.picky;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for {@link com.stoth.picky.Position}
 */
@RunWith(JUnit4.class)
public class PositionTest {
    @Test
    public void testConstruction() {
        int row = 5;
        int col = 7;
        Position p = Position.create(row, col);
        assertEquals(row, p.getRow());
        assertEquals(col, p.getCol());
    }
}
