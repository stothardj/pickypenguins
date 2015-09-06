package com.stoth.picky;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Converts GDX events into game events and sends them to the game.
 */
public final class InputReceiver extends InputAdapter {
    private Game game;

    private InputReceiver(Game game) {
        this.game = game;
    }

    public static InputReceiver create(Game game) {
        return new InputReceiver(game);
    }

    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT)
            game.receiveEvent(GameEvent.newSetDirection(Direction.LEFT));
        else if (keycode == Input.Keys.RIGHT)
            game.receiveEvent(GameEvent.newSetDirection(Direction.RIGHT));
        else if (keycode == Input.Keys.UP)
            game.receiveEvent(GameEvent.newSetDirection(Direction.UP));
        else if (keycode == Input.Keys.DOWN)
            game.receiveEvent(GameEvent.newSetDirection(Direction.DOWN));

        return true;
    }
}
