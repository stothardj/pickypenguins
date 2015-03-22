package com.stoth.picky;

import com.google.common.base.Preconditions;

/**
 * A wall in the puzzle.
 */
public final class Wall {
    public static enum Transition {}

    public static Wall create(Type type) {
        return new Wall(type);
    }

    private Wall(Type type) {
        this.type = Preconditions.checkNotNull(type);
    }

    public enum Type {
        NORMAL
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wall wall = (Wall) o;

        if (type != wall.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "type=" + type +
                '}';
    }

    private final Type type;

}
