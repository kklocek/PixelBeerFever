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
public class Player implements Disposable{

    private Texture playerTexture;
    private SpriteBatch batch;
    private Sprite playerSprite;
    private Sound beerSound, wrongSound;

    private Main gameManager;
    private int beerLane;
    private int score;
    private boolean hasBeer = false;

    private static final int BEER_LANES = 4;
    private static final int RESOLUTION = 64;
    private final int WIDTH;
    private final int HEIGHT;
    private final int SCALE_FACTOR;
    private final int Y_POS;

    public Player(Main gameManager) {
        playerTexture = new Texture(Gdx.files.internal("player.png"));
        playerSprite = new Sprite(playerTexture);
        beerSound = Gdx.audio.newSound(Gdx.files.internal("beer.wav"));
        wrongSound = Gdx.audio.newSound(Gdx.files.internal("wrong.wav"));

        WIDTH = gameManager.getWidth();
        HEIGHT = gameManager.getHeight();
        this.batch = gameManager.getBatch();
        this.gameManager = gameManager;

        SCALE_FACTOR = WIDTH / RESOLUTION;
        Y_POS = 8 * SCALE_FACTOR - 1 + 128;
        beerLane = 1;
        score = 0;
    }

    public void update(float delta) {
        playerSprite.setPosition((beerLane - 1) * 16 * SCALE_FACTOR, Y_POS);
        playerSprite.draw(batch);

        if(beerLane == BEER_LANES && gameManager.getBeerTable().isBeerAvailable() && !hasBeer) {
            hasBeer = true;
            gameManager.getBeerTable().pickBeer();
        }
    }

    public void goLeft() {
        if(beerLane > 1)
            beerLane--;
    }

    public void pickBeer() {
        if(hasBeer) {
            gameManager.addBeer(new Beer(batch, (beerLane - 1) * 16 * SCALE_FACTOR, HEIGHT / 8 * 3 , HEIGHT / 8 * 6 ));
            hasBeer = false;
            beerSound.play();
        } else {
            //TODO: Sound?
            wrongSound.play();
        }
    }

    public void goRight() {
        if(beerLane < BEER_LANES)
            beerLane++;
    }

    public boolean isLastBeerLane() {
        return beerLane == BEER_LANES;
    }

    public boolean isFirstBeerLane() {
        return beerLane == 1;
    }

    public boolean hasBeer() {
        return hasBeer;
    }

    public void addPoint() {
        score++;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void dispose() {

    }
}
