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

    @Test
    public void testMove() {
        Position p = Position.create(5, 7);
        assertEquals(Position.create(6, 7), p.move(Direction.DOWN));
        assertEquals(Position.create(4, 7), p.move(Direction.UP));
        assertEquals(Position.create(5, 8), p.move(Direction.RIGHT));
        assertEquals(Position.create(5, 6), p.move(Direction.LEFT));
    }
}
