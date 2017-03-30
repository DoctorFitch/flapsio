package com.fitch.dungeon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fitch.dungeon.Dungeon;

/**
 * Created by fitch on 22/02/2017.
 */

public class MenuState extends State {

    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("menu.png");
        playButton = new Texture("playbutton.png");
        //cam.setToOrtho(false, background.getWidth(), background.getHeight());
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0, Dungeon.WIDTH, Dungeon.HEIGHT);
        sb.draw(playButton, (Dungeon.WIDTH / 2) - ((playButton.getWidth() * 3.5f) / 2), (Dungeon.HEIGHT / 2) - ((playButton.getHeight() * 3.5f) / 2), playButton.getWidth() * 3.5f, playButton.getHeight() * 3.5f);
        sb.end();
    }

    // Avoid memory leaks
    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }

}
