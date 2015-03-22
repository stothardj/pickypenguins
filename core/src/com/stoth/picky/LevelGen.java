package com.stoth.picky;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * Class responsible for generating levels.
 */
public final class LevelGen {
    private final Random random;

    public LevelGen(Random random) {
        this.random = random;
    }

    private Position genPos(Dimensions dim) {
        return Position.create(random.nextInt(dim.getnRows()), random.nextInt(dim.getnCols()));
    }

    private Set<Position> genNPos(Dimensions dim, int n) {
        Set<Position> r = new HashSet<>();
        while(r.size() < n) {
            r.add(genPos(dim));
        }
        return r;
    }

    private <T> T choose(List<T> ls) {
        return ls.get(random.nextInt(ls.size()));
    }

    private List<Direction> randPath(int len) {
        List<Direction> result = new ArrayList<>();
        List<Direction> vertical = ImmutableList.of(Direction.DOWN, Direction.UP);
        List<Direction> horizontal = ImmutableList.of(Direction.LEFT, Direction.RIGHT);
        Queue<List<Direction>> chooseFrom = new LinkedList<>(choose(ImmutableList.of(
                ImmutableList.of(vertical, horizontal),
                ImmutableList.of(horizontal, vertical))));
        for (int i=0; i<len; i++) {
            List<Direction> chosenFrom = chooseFrom.poll();
            result.add(choose(chosenFrom));
            chooseFrom.add(chosenFrom);
        }
        return result;
    }

    private Level tryGenLevel(int nBoxes, Dimensions dim) {
        List<Position> boxPos = ImmutableList.copyOf(genNPos(dim, nBoxes));
        List<Color> colors = new ArrayList<>();
        for (int i=0; i<nBoxes; i++) colors.add(choose(ImmutableList.copyOf(Color.values())));
        Map<Position, Box> boxes = new HashMap<>();
        for (int i=0; i<nBoxes; i++) boxes.put(boxPos.get(i), Box.create(colors.get(i)));
        List<Position> wallPos = Lists.newArrayList(genNPos(dim, nBoxes));
        wallPos.removeAll(boxPos);
        Map<Position, Wall> walls = new HashMap<>();
        for (Position p : wallPos) walls.put(p, Wall.create(Wall.Type.NORMAL));
        // TODO: simulate movement.
        return null;
    }
}
