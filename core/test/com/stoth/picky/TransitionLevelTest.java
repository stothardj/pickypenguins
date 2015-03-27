package com.stoth.picky;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TransitionLevelTest {
    @Test
    public void isDoneTransitioning() {
        Level lvl = new Level.Builder(Dimensions.create(10, 10))
                .putWall(5, 3, Wall.create(Wall.Type.NORMAL))
                .putBox(5, 4, Box.create(Color.BLUE))
                .build();
        assertTrue(lvl.getTransitions(Direction.LEFT).isDoneTransitioning());
        assertFalse(lvl.getTransitions(Direction.RIGHT).isDoneTransitioning());
    }
}