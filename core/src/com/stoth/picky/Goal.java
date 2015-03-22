package com.stoth.picky;

import com.google.common.base.Preconditions;

/**
 * A goal to reach.
 */
public final class Goal {
    public static enum Transition {
        DISAPPEAR
    }

    private final Color color;

    @Override
    public String toString() {
        return "Goal{" +
                "color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goal goal = (Goal) o;

        if (color != goal.color) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    public Color getColor() {
        return color;
    }

    private Goal(Color color) {
        this.color = Preconditions.checkNotNull(color);
    }

    public static Goal create(Color color) {
        return new Goal(color);
    }
}
