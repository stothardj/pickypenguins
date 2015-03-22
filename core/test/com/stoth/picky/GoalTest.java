package com.stoth.picky;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class GoalTest {
    @Test
    public void testConstruction() {
        Color c = Color.BLUE;
        Goal g = Goal.create(c);
        assertEquals(c, g.getColor());
    }

}