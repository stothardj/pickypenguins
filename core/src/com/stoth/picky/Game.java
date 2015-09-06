package com.stoth.picky;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable object representing the current state of the game. Changes based on events it receives.
 */
public final class Game {
    private boolean isTransitioning;
    private Level level;
    private TransitionLevel transitionLevel;
    private List<GameEvent> eq;
    private Direction currentDirection;

    private Game(Level level) {
        eq = new ArrayList<>();
        isTransitioning = false;
        this.level = level;
    }

    public static Game create(Level level) {
        return new Game(level);
    }

    public void receiveEvent(GameEvent ev) {
        eq.add(ev);
    }

    public void setCurrentDirection(Direction direction) {
        if (currentDirection == null) {
            currentDirection = direction;
        }
    }

    public void processEvents() {
        checkState(!isTransitioning);
        for (GameEvent ev : eq) {
            ev.applyEvent(this);
        }
        eq.clear();

        if (currentDirection != null) {
            isTransitioning = true;
            transitionLevel = level.getTransitions(currentDirection);
            level = null;
        }
    }

    public void completeTransition() {
        if(isTransitioning) {
            if (transitionLevel.isDoneTransitioning())
                currentDirection = null;
            level = transitionLevel.applyTransitions();
            transitionLevel = null;
            isTransitioning = false;
        }
    }

    public Level getLevel() {
        checkState(!isTransitioning);
        return level;
    }

    public TransitionLevel getTransitionLevel() {
        checkState(isTransitioning);
        return transitionLevel;
    }
}
