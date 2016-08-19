package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Konrad on 2016-04-10.
 */
public class Beer implements Disposable{

    private static Texture beerTexture;
    private Sprite beerSprite;
    private float  x, y;
    private SpriteBatch batch;
    private boolean isEnded;
    private boolean withCollision = true;
    private final int Y_MAX;
    private Sound lostBeerSound;

    static {
        beerTexture = new Texture(Gdx.files.internal("beer.png"));
    }

    public Beer(SpriteBatch batch, int x, int y, int yMax) {
        this.x = x;
        this.y = y;
        this.batch = batch;
        isEnded = false;
        beerSprite = new Sprite(beerTexture);
        beerSprite.setPosition(x,y);
        Y_MAX = yMax;
        lostBeerSound = Gdx.audio.newSound(Gdx.files.internal("beer_fail.wav"));
    }

    public static Texture getBeerTexture() {
        return beerTexture;
    }

    public void update(float delta) {
        if(!isEnded) {
            y += delta * 100;
            int currPos = ((int)y / 8 ) * 8;
            beerSprite.setY(currPos);
            beerSprite.draw(batch);

            if(y > Y_MAX) {
                isEnded = true;
                withCollision = false;
                lostBeerSound.play();
            }
        }
    }

    public Sprite getBeerSprite() {
        return beerSprite;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void markEnded() {
        isEnded = true;
    }

    @Override
    public void dispose() {
        beerTexture.dispose();
    }

    public boolean isWithCollision() {
        return withCollision;
    }
}
