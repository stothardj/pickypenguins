package com.stoth.picky;

import com.google.common.base.Preconditions;

import java.util.Map;

/**
 * Level with ongoing transitions tagging all objects. For simplicity the underlying transitions on the objects are
 * mutable!
 */
public final class TransitionLevel {
    private final PositionMap<Transitioning<Box, Box.Transition>> boxes;
    private final PositionMap<Transitioning<Goal, Goal.Transition>> goals;
    private final PositionMap<Transitioning<Wall, Wall.Transition>> walls;
    private final Dimensions dimensions;

    private TransitionLevel(
            PositionMap<Transitioning<Box, Box.Transition>> boxes,
            PositionMap<Transitioning<Goal, Goal.Transition>> goals,
            PositionMap<Transitioning<Wall, Wall.Transition>> walls,
            Dimensions dimensions) {
        this.boxes = boxes;
        this.goals = goals;
        this.walls = walls;
        this.dimensions = dimensions;
    }

    public PositionMap<Transitioning<Box, Box.Transition>> getBoxes() {
        return boxes;
    }

    public PositionMap<Transitioning<Goal, Goal.Transition>> getGoals() {
        return goals;
    }

    public PositionMap<Transitioning<Wall, Wall.Transition>> getWalls() {
        return walls;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public static TransitionLevel fromLevel(Level level) {
        PositionMap.Builder<Transitioning<Box, Box.Transition>> boxBuilder = new PositionMap.Builder<>();
        PositionMap.Builder<Transitioning<Goal, Goal.Transition>> goalBuilder = new PositionMap.Builder<>();
        PositionMap.Builder<Transitioning<Wall, Wall.Transition>> wallBuilder = new PositionMap.Builder<>();

        for (Map.Entry<Position, Box> entry : level.getBoxes().getAll()) {
            boxBuilder.putAt(entry.getKey(), Transitioning.empty(entry.getValue(), Box.Transition.class));
        }
        for (Map.Entry<Position, Goal> entry : level.getGoals().getAll()) {
            goalBuilder.putAt(entry.getKey(), Transitioning.empty(entry.getValue(), Goal.Transition.class));
        }
        for (Map.Entry<Position, Wall> entry : level.getWalls().getAll()) {
            wallBuilder.putAt(entry.getKey(), Transitioning.empty(entry.getValue(), Wall.Transition.class));
        }
        return new TransitionLevel(boxBuilder.build(), goalBuilder.build(), wallBuilder.build(), level.getDimensions());
    }
}
