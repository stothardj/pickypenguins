package com.stoth.picky;

import static org.junit.Assert.*;

import com.google.common.collect.ImmutableMap;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;
import java.util.Set;

/**
 * Unit tests for {@link com.stoth.picky.Level}
 */
@RunWith(JUnit4.class)
public class LevelTest {
    @Test
    public void testConstruction() {
        Level level = new Level.Builder(Dimensions.create(12, 20))
                .putBox(5, 7, Box.create(Color.RED))
                .putGoal(1, 9, Goal.create(Color.GREEN))
                .putWall(3, 2, Wall.create(Wall.Type.NORMAL))
                .putGoal(6, 1, Goal.create(Color.BLUE))
                .build();
        PositionMap<Box> expectedBoxes = new PositionMap.Builder<Box>()
                .putAt(5, 7, Box.create(Color.RED))
                .build();
        assertEquals(expectedBoxes, level.getBoxes());
        PositionMap<Wall> expectedWalls = new PositionMap.Builder<Wall>()
                .putAt(3, 2, Wall.create(Wall.Type.NORMAL))
                .build();
        assertEquals(expectedWalls, level.getWalls());
        PositionMap<Goal> expectedGoals = new PositionMap.Builder<Goal>()
                .putAt(6, 1, Goal.create(Color.BLUE))
                .putAt(1, 9, Goal.create(Color.GREEN))
                .build();
        assertEquals(expectedGoals, level.getGoals());
    }

    @Test
    public void testGetTransitions_moves() {
        Level level = new Level.Builder(Dimensions.create(5, 5))
                .putBox(3, 2, Box.create(Color.RED))
                .build();
        TransitionLevel transitionLevel = level.getTransitions(Direction.LEFT);

        Set<Box.Transition> actual = transitionLevel.getBoxes().getAt(3, 2).getTransitions();
        assertEquals(ImmutableSet.of(Box.Transition.MOVE), actual);
    }

    @Test
    public void testGetTransitions_hitsWall() {
        Level level = new Level.Builder(Dimensions.create(5, 5))
                .putBox(3, 2, Box.create(Color.RED))
                .putWall(3, 1, Wall.create(Wall.Type.NORMAL))
                .build();
        TransitionLevel transitionLevel = level.getTransitions(Direction.LEFT);

        Set<Box.Transition> actual = transitionLevel.getBoxes().getAt(3, 2).getTransitions();
        assertEquals(ImmutableSet.of(Box.Transition.STAY), actual);
    }

    @Test
    public void testGetTransitions_hitsBorder() {
        Level level = new Level.Builder(Dimensions.create(5, 3))
                .putBox(3, 2, Box.create(Color.RED))
                .build();
        TransitionLevel transitionLevel = level.getTransitions(Direction.RIGHT);

        Set<Box.Transition> actual = transitionLevel.getBoxes().getAt(3, 2).getTransitions();
        assertEquals(ImmutableSet.of(Box.Transition.STAY), actual);
    }

    @Test
    public void testGetTransitions_hitsBoxNoWall() {
        Level level = new Level.Builder(Dimensions.create(10, 10))
                .putBox(5, 5, Box.create(Color.BLUE))
                .putBox(5, 6, Box.create(Color.RED))
                .build();
        TransitionLevel transitionLevel = level.getTransitions(Direction.LEFT);

        Set<Box.Transition> actual = transitionLevel.getBoxes().getAt(5, 6).getTransitions();
        assertEquals(ImmutableSet.of(Box.Transition.MOVE), actual);
    }

    @Test
    public void testGetTransitions_hitsBoxWithWall() {
        Level level = new Level.Builder(Dimensions.create(10, 10))
                .putWall(5, 4, Wall.create(Wall.Type.NORMAL))
                .putBox(5, 5, Box.create(Color.BLUE))
                .putBox(5, 6, Box.create(Color.RED))
                .build();
        TransitionLevel transitionLevel = level.getTransitions(Direction.LEFT);

        Set<Box.Transition> actual = transitionLevel.getBoxes().getAt(5, 6).getTransitions();
        assertEquals(ImmutableSet.of(Box.Transition.STAY), actual);
    }

    @Test
    public void testGetTransitions_reachesGoalSameColor() {
        Level level = new Level.Builder(Dimensions.create(10, 10))
                .putGoal(5, 5, Goal.create(Color.BLUE))
                .putBox(5, 4, Box.create(Color.BLUE))
                .build();
        TransitionLevel transitionLevel = level.getTransitions(Direction.RIGHT);

        Set<Box.Transition> actualBox = transitionLevel.getBoxes().getAt(5, 4).getTransitions();
        assertEquals(ImmutableSet.of(Box.Transition.MOVE, Box.Transition.DISAPPEAR), actualBox);

        Set<Goal.Transition> actualGoal = transitionLevel.getGoals().getAt(5, 5).getTransitions();
        assertEquals(ImmutableSet.of(Goal.Transition.DISAPPEAR), actualGoal);
    }

    @Test
    public void testGetTransitions_reachesGoalDifferentColor() {
        Level level = new Level.Builder(Dimensions.create(10, 10))
                .putGoal(5, 5, Goal.create(Color.BLUE))
                .putBox(5, 4, Box.create(Color.GREEN))
                .build();
        TransitionLevel transitionLevel = level.getTransitions(Direction.RIGHT);

        Set<Box.Transition> actualBox = transitionLevel.getBoxes().getAt(5, 4).getTransitions();
        assertEquals(ImmutableSet.of(Box.Transition.MOVE), actualBox);

        Set<Goal.Transition> actualGoal = transitionLevel.getGoals().getAt(5, 5).getTransitions();
        assertEquals(ImmutableSet.of(), actualGoal);
    }
}
