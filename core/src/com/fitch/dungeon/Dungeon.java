package com.fitch.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fitch.dungeon.states.GameStateManager;
import com.fitch.dungeon.states.MenuState;

public class Dungeon extends ApplicationAdapter {

	public static int WIDTH = 0;
	public static int HEIGHT = 0;
	public static final String TITLE = "Dungeon";

    GameStateManager gsm;
	SpriteBatch batch;
	
	@Override
	public void create () {
        gsm = new GameStateManager();
		batch = new SpriteBatch();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MenuState(gsm));
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
	}
}
