package com.stoth.picky;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Map;

/**
 * Object to draw a level.
 */
public final class LevelRenderer {
    private final SpriteBatch batch;
    private final Resources resources;

    public LevelRenderer(SpriteBatch batch, Resources resources) {
        this.batch = batch;
        this.resources = resources;
    }

    public void renderLevel(Level level) {
        batch.draw(
                resources.getBackgroundImage(), 0, 0,
                level.getDimensions().getnCols() * 100,
                level.getDimensions().getnRows() * 100);
        for (Map.Entry<Position, Box> entry : level.getBoxes().getAll()) {
            Position p = entry.getKey();
            batch.draw(resources.getBoxImage(entry.getValue().getColor()), p.getCol() * 100, p.getRow() * 100, 100, 100);
        }
        for (Map.Entry<Position, Goal> entry : level.getGoals().getAll()) {
            Position p = entry.getKey();
            batch.draw(resources.getGoalImage(entry.getValue().getColor()), p.getCol() * 100, p.getRow() * 100, 100, 100);
        }
        for (Position p : level.getWalls().getPositions()) {
            batch.draw(resources.getWallImage(), p.getCol() * 100, p.getRow() * 100, 100, 100);
        }
    }
}
