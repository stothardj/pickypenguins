package com.stoth.picky;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * An object which is transitioning. This is mutable!
 *
 * @param <O> Object type.
 * @param <T> Transition type.
 */
public final class Transitioning<O, T> {
    private final O object;
    private final Set<T> transitions;

    private Transitioning(O object, Set<T> transitions) {
        this.object = Preconditions.checkNotNull(object);
        this.transitions = Preconditions.checkNotNull(transitions);
    }

    public static <O, T> Transitioning empty(O object, Class<T> transitionType) {
        return new Transitioning(object, new HashSet<T>());
    }

    public static <O, T> Function<Transitioning<O, T>, Set<T>> getTransistionsFn() {
        return new Function<Transitioning<O, T>, Set<T>>() {
            @Override
            public Set<T> apply(Transitioning<O, T> input) {
                return input.getTransitions();
            }
        };
    }

    public O getObject() {
        return object;
    }

    public Set<T> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return "Transitioning{" +
                "object=" + object +
                ", transitions=" + transitions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transitioning that = (Transitioning) o;

        if (!object.equals(that.object)) return false;
        if (!transitions.equals(that.transitions)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = object.hashCode();
        result = 31 * result + transitions.hashCode();
        return result;
    }
}
