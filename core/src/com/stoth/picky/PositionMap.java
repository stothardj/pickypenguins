package com.stoth.picky;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks position of objects of type T.
 *
 * @param <T> Objects at certain positions.
 */
public final class PositionMap<T> {
    private final ImmutableMap<Position, T> map;

    private PositionMap(ImmutableMap<Position, T> map) {
        this.map = map;
    }

    public boolean isAnythingAt(Position p) {
        return map.containsKey(p);
    }

    public boolean isAnythingAt(int row, int col) {
        return isAnythingAt(Position.create(row, col));
    }

    public T getAt(Position p) {
        Preconditions.checkArgument(isAnythingAt(p));
        return map.get(p);
    }

    public T getAt(int row, int col) {
        return getAt(Position.create(row, col));
    }

    public ImmutableSet<Position> getPositions() {
        return map.keySet();
    }

    public ImmutableSet<Map.Entry<Position, T>> getAll() {
        return map.entrySet();
    }

    public static class Builder<T> {
        private final HashMap<Position, T> map = new HashMap<>();

        public boolean anythingAt(Position p) {
            return map.containsKey(p);
        }

        public boolean anythingAt(int row, int col) {
            return map.containsKey(Position.create(row, col));
        }

        public T getAt(Position p) {
            return map.get(p);
        }

        public T getAt(int row, int col) {
            return getAt(Position.create(row, col));
        }

        public Builder<T> putAt(Position p, T t) {
            map.put(p, t);
            return this;
        }

        public Builder<T> putAt(int row, int col, T t) {
            return putAt(Position.create(row, col), t);
        }

        public PositionMap<T> build() {
            return new PositionMap<T>(ImmutableMap.copyOf(map));
        }
    }

    @Override
    public String toString() {
        return "PositionMap{" +
                "map=" + map +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PositionMap that = (PositionMap) o;

        if (!map.equals(that.map)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
