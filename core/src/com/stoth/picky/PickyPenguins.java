package com.stoth.picky;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Timer;

public class PickyPenguins extends ApplicationAdapter {
	private SpriteBatch batch;
    private Resources resources;
    private Camera camera;
    private Viewport viewport;
    private LevelRenderer levelRenderer;
    private Game game;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        resources = Resources.load();
        levelRenderer = new LevelRenderer(batch, resources);
        Level level = new Level.Builder(Dimensions.create(10, 10))
                .putBox(5, 3, Box.create(Color.RED))
                .putBox(7, 2, Box.create(Color.BLUE))
                .putWall(1, 0, Wall.create(Wall.Type.NORMAL))
                .putGoal(3, 8, Goal.create(Color.GREEN))
                .build();
        game = Game.create(level);
        camera = new OrthographicCamera();
        viewport = new FitViewport(1000, 1000, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        Gdx.input.setInputProcessor(InputReceiver.create(game));
	}

	@Override
	public void render () {
        camera.update();
		Gdx.gl.glClearColor(0, 0.3f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        levelRenderer.renderLevel(game.getLevel());
        game.processEvents();
        game.completeTransition();
		batch.end();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }
}
