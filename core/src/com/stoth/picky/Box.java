package com.stoth.picky;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import java.util.Set;

/**
 * The object moved towards the goals by the player.
 */
public final class Box {
    public static enum Transition {
        STAY,
        MOVE,
        DISAPPEAR
    }

    public static Predicate<Set<Transition>> hasTransistionFn(final Transition t) {
        return new Predicate<Set<Transition>>() {
            @Override
            public boolean apply(Set<Transition> input) {
                return input.contains(t);
            }
        };
    }

    @Override
    public String toString() {
        return "Box{" +
                "color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        if (color != box.color) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    public Color getColor() {
        return color;
    }

    private Box(Color color) {
        this.color = Preconditions.checkNotNull(color);
    }

    public static Box create(Color color) {
        return new Box(color);
    }

    private final Color color;
}
