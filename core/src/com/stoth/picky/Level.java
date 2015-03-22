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
    private final Map<Position, Box> boxes;
    private final Map<Position, Goal> goals;
    private final Map<Position, Wall> walls;
    private final Dimensions dimensions;

    public Map<Position, Box> getBoxes() {
        return boxes;
    }

    public Map<Position, Goal> getGoals() {
        return goals;
    }

    public Map<Position, Wall> getWalls() {
        return walls;
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

    public Dimensions getDimensions() {
        return dimensions;
    }

    private Level(Map<Position, Box> boxes, Map<Position, Goal> goals, Map<Position, Wall> walls, Dimensions dimensions) {
        this.boxes = Preconditions.checkNotNull(boxes);
        this.goals = Preconditions.checkNotNull(goals);
        this.walls = Preconditions.checkNotNull(walls);
        this.dimensions = Preconditions.checkNotNull(dimensions);
    }

    public static Level create(Map<Position, Box> boxes, Map<Position, Goal> goals, Map<Position, Wall> walls, Dimensions dimensions) {
        return new Level(boxes, goals, walls, dimensions);
    }

    public TransitionLevel getTransitions(Direction dir) {
        Queue<Position> pending = Lists.newLinkedList(boxes.keySet());
        Set<Position> pendingSet = Sets.newHashSet(boxes.keySet());
        Map<Position, Transitioning<Box, Box.Transition>> bt = new HashMap<>();
        Map<Position, Transitioning<Goal, Goal.Transition>> gt = new HashMap<>();
        Map<Position, Transitioning<Wall, Wall.Transition>> wt = new HashMap<>();
        for (Map.Entry<Position, Box> box : boxes.entrySet()) {
            bt.put(box.getKey(), Transitioning.create(box.getValue(), new HashSet<Box.Transition>()));
        }
        for (Map.Entry<Position, Goal> goal : goals.entrySet()) {
            gt.put(goal.getKey(), Transitioning.create(goal.getValue(), new HashSet<Goal.Transition>()));
        }
        for (Map.Entry<Position, Wall> wall : walls.entrySet()) {
            wt.put(wall.getKey(), Transitioning.create(wall.getValue(), new HashSet<Wall.Transition>()));
        }
        while (!pending.isEmpty()) {
            Position p = pending.peek();
            Box b = boxes.get(p);
            Position np = p.move(dir);

            // Hitting a wall
            if (walls.containsKey(np)) {
                bt.get(p).getTransitions().add(Box.Transition.STAY);
                pendingSet.remove(pending.poll());
            }

            // Going out of bounds
            else if (!dimensions.isInBounds(np)) {
                bt.get(p).getTransitions().add(Box.Transition.STAY);
                pendingSet.remove(pending.poll());
            }

            // Hitting a box which we have yet to determine whether it moves
            else if (pendingSet.contains(np)) {
                pending.offer(pending.poll());
            }

            // Hitting a box which we have determined is going to stay
            else if (bt.containsKey(np) && bt.get(np).getTransitions().contains(Box.Transition.STAY)) {
                bt.get(p).getTransitions().add(Box.Transition.STAY);
                pendingSet.remove(pending.poll());
            }

            // Goal of the correct color has been reached
            else if (gt.containsKey(np) && gt.get(np).getObject().getColor().equals(b.getColor())) {
                gt.get(np).getTransitions().add(Goal.Transition.DISAPPEAR);
                bt.get(p).getTransitions().addAll(ImmutableList.of(Box.Transition.DISAPPEAR, Box.Transition.MOVE));
            }

            // Not hitting anything
            else {
                bt.get(p).getTransitions().add(Box.Transition.MOVE);
                pendingSet.remove(pending.poll());
            }
        }
        return TransitionLevel.create(bt, gt, wt, dimensions);
    }
}
