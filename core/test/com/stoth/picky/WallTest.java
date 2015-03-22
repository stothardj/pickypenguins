package com.stoth.picky;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class WallTest {
    @Test
    public void testConstruction() {
        Wall.Type type = Wall.Type.NORMAL;
        Wall wall = Wall.create(type);
        assertEquals(type, wall.getType());
    }
}