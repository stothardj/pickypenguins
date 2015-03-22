package com.stoth.picky;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BoxTest {
    @Test
    public void testConstruction() {
        Color c = Color.BLUE;
        Box b = Box.create(c);
        assertEquals(c, b.getColor());
    }
}