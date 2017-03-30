package com.fitch.dungeon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fitch.dungeon.Dungeon;
import com.fitch.dungeon.sprites.Player;
import com.fitch.dungeon.sprites.Tube;

/**
 * Created by fitch on 22/02/2017.
 */

public class PlayState extends State {

    BitmapFont font;

    // Parameters and array tubes
    private static final int TUBE_SPACING = 150;
    private static final int TUBE_COUNT = 5;
    private Array<Tube> tubes;

    // Textures
    private Player player;
    private Texture backgroundGameOver;
    private Texture background;
    private Tube tube;

    // Sounds
    private Music music;
    private Sound soundExplosion;
    private Sound soundJump;

    // GameOver
    private Integer score = 0;


    public PlayState(GameStateManager gsm) {
        super(gsm);

        //  texture initialization
        backgroundGameOver = new Texture("backgroundGameOver.png");
        background = new Texture("background.png");

        // set parameters of camera before passing arguments to player constructor (allow to know the max height of the world)
        cam.setToOrtho(false, background.getWidth(), background.getHeight());
        player = new Player(40, 300, cam);

        // sounds & music initialization
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundSound.wav"));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        soundJump = Gdx.audio.newSound(Gdx.files.internal("sounds/Jump.wav"));
        music.setLooping(true);
        music.play();

        // tubes generator
        tube = new Tube(100);
        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        // font generated with hiero (https://github.com/libgdx/libgdx/wiki/Hier) I need to use the .fnt and provide him the others file.png generated to be functionnal
        font = new BitmapFont(Gdx.files.internal("flappyStyle.fnt"), false);
        font.getData().setScale(0.13f);

        // d e b u g \\
        //cam.setToOrtho(false, background.getWidth() * 2, background.getHeight() * 2);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            soundJump.play();
            player.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        player.update(dt);
        score += Math.round(dt);

        player.getPosition().x = 80;

        if (player.getPosition().y <= 0) {
            endGameSound();
            gsm.set(new GameOverState(gsm, score));
        }

        for (Tube tube : tubes) {

            score++;

            tube.animationWDelta();

            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPositionTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPositionTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(player.getBounds())) {
                endGameSound();
                gsm.set(new GameOverState(gsm, score));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);

        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPositionTopTube().x, tube.getPositionTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPositionBotTube().x, tube.getPositionBotTube().y);
        }

        font.draw(sb, "Score : " + score / 100, 5, 25);

        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
        sb.end();
    }

    // Avoid memory leaks
    @Override
    public void dispose() {
        soundJump.dispose();
        soundExplosion.dispose();
        music.dispose();
    }

    public void endGameSound() {
        soundExplosion.play();
        music.stop();
    }

}
