package com.fitch.dungeon.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by fitch on 28/02/2017.
 */

public class Tube {

    public static final int TUBE_WIDTH = 52;

    private static final int FLUCTUATION = 150;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 20;

    private Texture topTube, bottomTube;
    private Vector2 positionTopTube, positionBotTube;
    private Rectangle boundsTop;
    private Rectangle boundsBot;
    private Random rand;

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPositionTopTube() {
        return positionTopTube;
    }

    public Vector2 getPositionBotTube() {
        return positionBotTube;
    }

    public Rectangle getBoundsTop() {
        return boundsTop;
    }

    public void setBoundsTop(Rectangle boundsTop) {
        this.boundsTop = boundsTop;
    }

    public Rectangle getBoundsBot() {
        return boundsBot;
    }

    public void setBoundsBot(Rectangle boundsBot) {
        this.boundsBot = boundsBot;
    }

    public Tube(float x) {

        topTube = new Texture("toptube2.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        positionTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionBotTube = new Vector2(x, positionTopTube.y - TUBE_GAP - bottomTube.getHeight());

        setBoundsTop(new Rectangle(positionTopTube.x, positionTopTube.y, topTube.getWidth(), topTube.getHeight()));
        setBoundsBot(new Rectangle(positionBotTube.x, positionBotTube.y, bottomTube.getWidth(), bottomTube.getHeight()));
    }

    public void reposition(float x) {
        positionTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        positionBotTube.set(x, positionTopTube.y - TUBE_GAP - bottomTube.getHeight());
        getBoundsTop().setPosition(positionTopTube.x, positionTopTube.y);
        getBoundsBot().setPosition(positionBotTube.x, positionBotTube.y);
    }

    public void animationWDelta() {
        getPositionTopTube().x  -= Gdx.graphics.getDeltaTime() * 90;
        getPositionBotTube().x  -= Gdx.graphics.getDeltaTime() * 90;
        getBoundsTop().x -= Gdx.graphics.getDeltaTime() * 90;
        getBoundsBot().x -= Gdx.graphics.getDeltaTime() * 90;
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(getBoundsTop()) ||player.overlaps(getBoundsBot());
    }


}
