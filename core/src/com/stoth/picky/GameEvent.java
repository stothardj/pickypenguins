package com.stoth.picky;

/**
 * An event which needs to be processed to move the game forwards.
 */
public final class GameEvent {
    public static enum Type {
        SET_DIRECTION
    }

    // Everything nullable based on Type
    private final Type type;
    private final Direction direction;

    private GameEvent(Type type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }

    public static GameEvent newSetDirection(Direction direction) {
        return new GameEvent(Type.SET_DIRECTION, direction);
    }

    public void applyEvent(Game game) {
        switch (type) {
            case SET_DIRECTION:
                game.setCurrentDirection(direction);
                break;
        }
    }
}
