package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Konrad on 2016-04-30.
 */
public class BeerLife implements Disposable {

    private static final int LIFES = 3;
    private static final int X_POS = 0;
    private static final int SIZE = 32;
    private static final int Y_POS = 8 + 128;

    private int currLifes = LIFES;
    private Texture littleBeerTexture;
    private Texture lifesTexture;
    private SpriteBatch batch;
    private Main gameManager;

    public BeerLife(Main gameManager) {
        this.gameManager = gameManager;
        batch = gameManager.getBatch();

        littleBeerTexture = new Texture(Gdx.files.internal("littleBeer.png"));
        lifesTexture = new Texture(Gdx.files.internal("lifes.png"));
    }

    public void update(float delta) {
        batch.draw(lifesTexture, X_POS, Y_POS);
        for(int i = 1; i <= currLifes; i++) {
            batch.draw(littleBeerTexture, X_POS + i * SIZE + lifesTexture.getWidth() - 8, Y_POS); //TODO: Better fit!
        }
    }

    public int getLifes() {
        return currLifes;
    }

    public void lostBeer() {
        currLifes--;
    }
    @Override
    public void dispose() {
        littleBeerTexture.dispose();
        lifesTexture.dispose();
    }
}
