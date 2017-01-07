package com.klocek.lowrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

/**
 * Created by Konrad on 2016-04-11.
 */
public class BeerTable implements Disposable{

    private Texture beerTexture;
    private SpriteBatch batch;
    private boolean isBeerAvailable;
    private float timer;
    private float timeToNext;
    private Random rnd;
    private int x, y;
    private float ratio;
    private int beers;
    private int level;

    public BeerTable(SpriteBatch batch, int x, int y) {
        beerTexture = Beer.getBeerTexture();
        this.batch = batch;
        this.x = x;
        this.y = y;
        rnd = new Random();
        reset();
    }

    public boolean isBeerAvailable() {
        return isBeerAvailable;
    }

    public void pickBeer() {
        isBeerAvailable = false;
    }

    public void update(float delta) {
        if (!isBeerAvailable) {
            timer += delta;
            if (timer >= timeToNext) {
                timer = 0;
                timeToNext = rnd.nextFloat() * ratio + 1;
                isBeerAvailable = true;
                batch.draw(beerTexture, x, y);
                beers++;
                if (beers / 4 > level) {
                    level++;
                    ratio -= 0.2f;
                    if (ratio <= 0)
                        ratio = 0.2f;
                }
            }
        } else {
            batch.draw(beerTexture, x, y);
        }
    }

    public void reset() {
        isBeerAvailable = false;
        timeToNext = rnd.nextFloat() * 1.2f + 1;
        timer = 0;
        beers = 0;
        level = 1;
        ratio = 1.4f;
    }

    @Override
    public void dispose() {
        Beer.clear();
    }
}
