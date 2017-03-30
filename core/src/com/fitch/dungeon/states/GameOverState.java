package com.fitch.dungeon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fitch.dungeon.Dungeon;

/**
 * Created by fitch on 22/02/2017.
 */

public class GameOverState extends State {

    private Texture backgroundGameOver;
    private Texture playButton;
    private Integer score;
    BitmapFont font;

    public GameOverState(GameStateManager gsm, Integer score) {
        super(gsm);
        backgroundGameOver = new Texture("backgroundGameOver.png");
        playButton = new Texture("playbutton.png");
        this.score = score;

        // font generated with hiero (https://github.com/libgdx/libgdx/wiki/Hier) I need to use the .fnt and provide him the others file.png generated to be functionnal
        font = new BitmapFont(Gdx.files.internal("flappyStyle.fnt"), false);
        font.getData().setScale(0.13f);
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
        sb.draw(backgroundGameOver, 0, 0);
        sb.draw(playButton, (backgroundGameOver.getWidth() / 2) - (playButton.getWidth() / 2), (backgroundGameOver.getHeight() / 2) - (playButton.getHeight() / 2));
        font.draw(sb, "" + score / 100, 170, 60);
        sb.end();
    }

    // Avoid memory leaks
    @Override
    public void dispose() {
        backgroundGameOver.dispose();
        playButton.dispose();
    }

}
