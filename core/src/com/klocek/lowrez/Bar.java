package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Konrad on 2016-04-11.
 */
public class Bar implements Disposable {

    private Texture barTexture;
    private SpriteBatch batch;
    private int yPos;

    public Bar(SpriteBatch batch, int yPos) {
        this.batch = batch;
        this.yPos = yPos;

        barTexture = new Texture(Gdx.files.internal("bar.png"));
    }

    public void update(float delta) {
        batch.draw(barTexture, 0, yPos);
    }

    @Override
    public void dispose() {
        barTexture.dispose();
    }
}
