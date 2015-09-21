package com.stoth.picky;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Loads a level created by "pp-lvlgen". This has to do some conversion to change
 * (x,y) to (row,col) and switch 0,0 from top-left to bottom-left. This means that
 * the board appears to be flipped vertically. All of this is self-contained within
 * this class, the returned solution and level are consistent with each other and in
 * the standard libgdx coordinate system with 0,0 at the bottom-left.
 */
public final class LevelLoader {
    private final JsonReader reader;

    public LevelLoader() {
        reader = new JsonReader();
    }

    public static final class LoadedLevel {
        public final Level level;
        public final List<Direction> solution;

        private LoadedLevel(Level level, List<Direction> solution) {
            this.level = level;
            this.solution = solution;
        }
    }

    private Position parsePosition(JsonValue json) {
        int x = json.getInt("x");
        int y = json.getInt("y");

        return Position.create(y, x);
    }

    private Color parseColor(String color) {
        return Color.valueOf(color.toUpperCase());
    }

    public LoadedLevel loadLevel(FileHandle levelFile) {
        JsonValue top = reader.parse(levelFile);
        JsonValue lvl = top.get("level");
        JsonValue walls = lvl.get("walls");
        JsonValue boxes = lvl.get("boxes");
        JsonValue goals = lvl.get("goals");
        JsonValue dimensions = lvl.get("dimensions");
        Level.Builder levelBuilder =
                new Level.Builder(Dimensions.create(dimensions.getInt("dimY"), dimensions.getInt("dimX")));
        for (JsonValue wall : walls) {
            levelBuilder.putWall(parsePosition(wall.get("pos")), Wall.create(Wall.Type.NORMAL));
        }
        for (JsonValue box : boxes) {
            levelBuilder.putBox(parsePosition(box.get("pos")), Box.create(parseColor(box.getString("color"))));
        }
        for (JsonValue goal : goals) {
            levelBuilder.putGoal(parsePosition(goal.get("pos")), Goal.create(parseColor(goal.getString("color"))));
        }

        ImmutableList.Builder<Direction> solution =
                ImmutableList.<Direction>builder();
        for (String dir : top.get("solution").asStringArray()) {
            switch (dir) {
                case "UP":
                    solution.add(Direction.UP);
                    break;
                case "DOWN":
                    solution.add(Direction.DOWN);
                    break;
                case "LEFT":
                    solution.add(Direction.LEFT);
                    break;
                case "RIGHT":
                    solution.add(Direction.RIGHT);
                    break;
            }
        }
        return new LoadedLevel(levelBuilder.build(), solution.build());
    }
}
