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
        Level level = new LevelLoader().loadLevel(Gdx.files.internal("level.json")).level;

        game = Game.create(level);
        camera = new OrthographicCamera();
        viewport = new FitViewport(
                level.getDimensions().getnCols() * 100,
                level.getDimensions().getnRows() * 100,
                camera);
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
        batch.end();
        game.processEvents();
        game.completeTransition();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }
}
