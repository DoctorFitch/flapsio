package com.fitch.dungeon.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by fitch on 23/02/2017.
 */

public class Player {

    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation playerAnimation;

    private Integer heightMax;

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return playerAnimation.getFrame();
    }

    public Player(int x, int y, OrthographicCamera ps_cam) {

        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);

        Texture texture = new Texture("animations.png");
        playerAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

        heightMax = Math.round(ps_cam.viewportHeight - texture.getHeight());
    }

    public void update(float dt) {

        playerAnimation.update(dt);

        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(dt);

        position.add(MOVEMENT * dt, velocity.y, 0);

        if (position.y < 0) position.y = 0;
        if (position.y > heightMax) position.y = heightMax;


        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = 250;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
