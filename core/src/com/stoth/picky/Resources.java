package com.stoth.picky;

import com.badlogic.gdx.graphics.Texture;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Loads the appropriate resources.
 */
public final class Resources {
    private final Map<Color, Texture> boxes;
    private final Map<Color, Texture> goals;
    private final Texture wall;
    private final Texture background;

    private Resources() {
        boxes = ImmutableMap.of(
                Color.RED, new Texture("penguin-red.png"),
                Color.GREEN, new Texture("penguin-green.png"),
                Color.BLUE, new Texture("penguin-blue.png")
        );
        goals = ImmutableMap.of(
                Color.RED, new Texture("fish-red.png"),
                Color.GREEN, new Texture("fish-green.png"),
                Color.BLUE, new Texture("fish-blue.png")
        );
        wall = new Texture("iceblock.png");
        background = new Texture("background.png");
    }

    public static Resources load() {
        return new Resources();
    }

    public Texture getBoxImage(Color color) {
        return boxes.get(color);
    }

    public Texture getGoalImage(Color color) {
        return goals.get(color);
    }

    public Texture getWallImage() {
        return wall;
    }

    public Texture getBackgroundImage() { return background; }
}
