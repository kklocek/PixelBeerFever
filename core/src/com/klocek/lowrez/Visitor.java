package com.klocek.lowrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Konrad on 2016-04-10.
 */
public class Visitor implements Disposable {

    private Animation animation;
    private Rectangle rectangle, textureRectangle;
    private int x, y;
    private final int Y_MIN;
    private SpriteBatch batch;
    private float timer = 0;
    private boolean isEnded = false;
    private boolean isLost = false;

    public Visitor(SpriteBatch batch, Texture texture, int x, int y, int yMin) {
        this.batch = batch;
        this.x = x;
        this.y = y;
        this.Y_MIN = yMin;
        TextureRegion[][] t2 = TextureRegion.split(texture, 64, 64);
        TextureRegion[] t3 = new TextureRegion[t2[0].length];

        for (int i = 0; i < t2[0].length; i++) {
            t3[i] = t2[0][i];
        }
        animation = new Animation(0.25f, t3);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        rectangle = new Rectangle(x - 64, y, 64, 64);
        textureRectangle = new Rectangle();
    }

    public void update(float delta) {
        if (!isEnded) {
            timer += delta;
            y -= delta * 50;
            int currPos = (y / 8) * 8;
            rectangle.setPosition(x - 64, currPos); // :
            textureRectangle.setPosition(x, currPos);

            if (y <= Y_MIN)
                isLost = true;

            batch.draw(animation.getKeyFrame(timer, true), x, currPos);
        }
    }

    public Rectangle getBarRectangle() {
        return rectangle;
    }

    public Rectangle getVisitorRectangle() {
        return rectangle;
    }

    public boolean isEnded() {
        return isEnded;
    }

    @Override
    public void dispose() {

    }

    public void markEnded() {
        isEnded = true;
    }

    public boolean isLost() {
        return isLost;
    }
}
