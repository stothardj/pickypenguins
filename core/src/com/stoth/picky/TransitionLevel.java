package com.stoth.picky;

import com.google.common.base.Preconditions;

import java.util.Map;

/**
 * Level with ongoing transitions tagging all objects.
 */
public final class TransitionLevel {
    private final Map<Position, Transitioning<Box, Box.Transition>> boxes;
    private final Map<Position, Transitioning<Goal, Goal.Transition>> goals;
    private final Map<Position, Transitioning<Wall, Wall.Transition>> walls;
    private final Dimensions dimensions;

    private TransitionLevel(
            Map<Position, Transitioning<Box, Box.Transition>> boxes,
            Map<Position, Transitioning<Goal, Goal.Transition>> goals,
            Map<Position, Transitioning<Wall, Wall.Transition>> walls,
            Dimensions dimensions) {
        this.boxes = Preconditions.checkNotNull(boxes);
        this.goals = Preconditions.checkNotNull(goals);
        this.walls = Preconditions.checkNotNull(walls);
        this.dimensions = Preconditions.checkNotNull(dimensions);
    }

    public static TransitionLevel create(
            Map<Position, Transitioning<Box, Box.Transition>> boxes,
            Map<Position, Transitioning<Goal, Goal.Transition>> goals,
            Map<Position, Transitioning<Wall, Wall.Transition>> walls,
            Dimensions dimensions) {
        return new TransitionLevel(boxes, goals, walls, dimensions);
    }
}
