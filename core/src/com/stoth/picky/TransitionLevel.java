package com.stoth.picky;

import static com.google.common.base.Preconditions.checkNotNull;

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
    private final Direction direction;

    public TransitionLevel(
            PositionMap<Transitioning<Box, Box.Transition>> boxes,
            PositionMap<Transitioning<Goal, Goal.Transition>> goals,
            PositionMap<Transitioning<Wall, Wall.Transition>> walls,
            Dimensions dimensions,
            Direction direction) {
        this.boxes = checkNotNull(boxes);
        this.goals = checkNotNull(goals);
        this.walls = checkNotNull(walls);
        this.dimensions = checkNotNull(dimensions);
        this.direction = checkNotNull(direction);
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

    public Direction getDirection() {
        return direction;
    }

    public static TransitionLevel fromLevel(Level level, Direction dir) {
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
        return new TransitionLevel(boxBuilder.build(), goalBuilder.build(), wallBuilder.build(), level.getDimensions(), dir);
    }

    public boolean isDoneTransitioning() {
        return boxes.getValues()
                .transform(Transitioning.<Box, Box.Transition>getTransistionsFn())
                .allMatch(Box.hasTransistionFn(Box.Transition.STAY));
    }

    public Level applyTransitions() {
        return Level.fromTransitionLevel(this);
    }
}
