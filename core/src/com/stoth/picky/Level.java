package com.stoth.picky;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * A level to be played.
 */
public final class Level {
    private final PositionMap<Box> boxes;
    private final PositionMap<Goal> goals;
    private final PositionMap<Wall> walls;
    private final Dimensions dimensions;

    public PositionMap<Box> getBoxes() {
        return boxes;
    }

    public PositionMap<Goal> getGoals() {
        return goals;
    }

    public PositionMap<Wall> getWalls() {
        return walls;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;

        if (!boxes.equals(level.boxes)) return false;
        if (!dimensions.equals(level.dimensions)) return false;
        if (!goals.equals(level.goals)) return false;
        if (!walls.equals(level.walls)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = boxes.hashCode();
        result = 31 * result + goals.hashCode();
        result = 31 * result + walls.hashCode();
        result = 31 * result + dimensions.hashCode();
        return result;
    }

    private Level(PositionMap<Box> boxes, PositionMap<Goal> goals, PositionMap<Wall> walls, Dimensions dimensions) {
        this.boxes = Preconditions.checkNotNull(boxes);
        this.goals = Preconditions.checkNotNull(goals);
        this.walls = Preconditions.checkNotNull(walls);
        this.dimensions = Preconditions.checkNotNull(dimensions);
    }

    public static class Builder {
        private final PositionMap.Builder<Box> boxBuilder = new PositionMap.Builder<>();
        private final PositionMap.Builder<Goal> goalBuilder = new PositionMap.Builder<>();
        private final PositionMap.Builder<Wall> wallBuilder = new PositionMap.Builder<>();
        private final Dimensions dimensions;

        public Builder(Dimensions dimensions) {
            this.dimensions = dimensions;
        }

        public Builder putBox(Position p, Box b) {
            boxBuilder.putAt(p, b);
            return this;
        }

        public Builder putBox(int row, int col, Box b) {
            boxBuilder.putAt(row, col, b);
            return this;
        }

        public Builder putGoal(Position p, Goal g) {
            goalBuilder.putAt(p, g);
            return this;
        }

        public Builder putGoal(int row, int col, Goal g) {
            goalBuilder.putAt(row, col, g);
            return this;
        }

        public Builder putWall(Position p, Wall w) {
            wallBuilder.putAt(p, w);
            return this;
        }

        public Builder putWall(int row, int col, Wall w) {
            wallBuilder.putAt(row, col, w);
            return this;
        }

        public Level build() {
            return new Level(boxBuilder.build(), goalBuilder.build(), wallBuilder.build(), dimensions);
        }
    }

    public TransitionLevel getTransitions(Direction dir) {
        Queue<Position> pending = Lists.newLinkedList(boxes.getPositions());
        Set<Position> pendingSet = Sets.newHashSet(boxes.getPositions());

        TransitionLevel transitionLevel = TransitionLevel.fromLevel(this, dir);
        PositionMap<Transitioning<Box, Box.Transition>> bt = transitionLevel.getBoxes();
        PositionMap<Transitioning<Goal, Goal.Transition>> gt = transitionLevel.getGoals();
        
        while (!pending.isEmpty()) {
            Position p = pending.peek();
            Box b = boxes.getAt(p);
            Position np = p.move(dir);

            // Hitting a wall
            if (walls.isAnythingAt(np)) {
                bt.getAt(p).getTransitions().add(Box.Transition.STAY);
                pendingSet.remove(pending.poll());
            }

            // Going out of bounds
            else if (!dimensions.isInBounds(np)) {
                bt.getAt(p).getTransitions().add(Box.Transition.STAY);
                pendingSet.remove(pending.poll());
            }

            // Hitting a box which we have yet to determine whether it moves
            else if (pendingSet.contains(np)) {
                pending.offer(pending.poll());
            }

            // Hitting a box which we have determined is going to stay
            else if (bt.isAnythingAt(np) && bt.getAt(np).getTransitions().contains(Box.Transition.STAY)) {
                bt.getAt(p).getTransitions().add(Box.Transition.STAY);
                pendingSet.remove(pending.poll());
            }

            // Goal of the correct color has been reached
            else if (gt.isAnythingAt(np) && gt.getAt(np).getObject().getColor().equals(b.getColor())) {
                gt.getAt(np).getTransitions().add(Goal.Transition.DISAPPEAR);
                bt.getAt(p).getTransitions().addAll(ImmutableList.of(Box.Transition.DISAPPEAR, Box.Transition.MOVE));
                pendingSet.remove(pending.poll());
            }

            // Not hitting anything
            else {
                bt.getAt(p).getTransitions().add(Box.Transition.MOVE);
                pendingSet.remove(pending.poll());
            }
        }
        return transitionLevel;
    }

    private static PositionMap<Box> applyBoxTransition(TransitionLevel tl) {
        PositionMap.Builder<Box> builder = new PositionMap.Builder<>();
        for (Map.Entry<Position, Transitioning<Box, Box.Transition>> entry : tl.getBoxes().getAll()) {
            Position p = entry.getKey();
            Set<Box.Transition> transitions = entry.getValue().getTransitions();
            Box oldBox = entry.getValue().getObject();
            if (transitions.contains(Box.Transition.DISAPPEAR)) {
                continue;
            } else if (transitions.contains(Box.Transition.MOVE)) {
                builder.putAt(p.move(tl.getDirection()), oldBox);
            } else if (transitions.contains(Box.Transition.STAY)) {
                builder.putAt(p, oldBox);
            }
        }
        return builder.build();
    }

    private static PositionMap<Goal> applyGoalTransition(TransitionLevel tl) {
        PositionMap.Builder<Goal> builder = new PositionMap.Builder<>();
        for (Map.Entry<Position, Transitioning<Goal, Goal.Transition>> entry : tl.getGoals().getAll()) {
            Position p = entry.getKey();
            Set<Goal.Transition> transitions = entry.getValue().getTransitions();
            Goal oldGoal = entry.getValue().getObject();
            if (!transitions.contains(Goal.Transition.DISAPPEAR)) {
                builder.putAt(p, oldGoal);
            }
        }
        return builder.build();
    }

    private static PositionMap<Wall> applyWallTransition(TransitionLevel tl) {
        PositionMap.Builder<Wall> builder = new PositionMap.Builder<>();
        for (Map.Entry<Position, Transitioning<Wall, Wall.Transition>> entry : tl.getWalls().getAll()) {
            Position p = entry.getKey();
            Wall oldWall = entry.getValue().getObject();
            builder.putAt(p, oldWall);
        }
        return builder.build();
    }

    public static Level fromTransitionLevel(TransitionLevel tl) {
        return new Level(
                applyBoxTransition(tl),
                applyGoalTransition(tl),
                applyWallTransition(tl),
                tl.getDimensions());
    }
}
