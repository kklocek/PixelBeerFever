package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Konrad on 2016-04-10.
 */
public class Controls implements Disposable {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture leftArrow, rightArrow, beerButton, beerIdleButton, leftArrowIdle, rightArrowIdle, left, right, beer;
    private GameScreen parent;
    private Vector3 input;
    private boolean beerIdle = true;

    public Controls(GameScreen parent) {
        this.batch = parent.getBatch();
        this.camera = parent.getCamera();
        this.parent = parent;

        input = new Vector3();
        leftArrow = new Texture(Gdx.files.internal("leftArrow.png"));
        rightArrow = new Texture(Gdx.files.internal("rightArrow.png"));
        beerButton = new Texture(Gdx.files.internal("beerButton.png"));
        beerIdleButton = new Texture(Gdx.files.internal("beerIdleButton.png"));
        leftArrowIdle = new Texture(Gdx.files.internal("leftArrowIdle.png"));
        rightArrowIdle = new Texture(Gdx.files.internal("rightArrowIdle.png"));

        setLeftArrowIdle(true);
        setRightArrowIdle(false);
    }

    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            input.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            input = camera.unproject(input);
            handleInput();
        }

        batch.draw(left, 0, 0);
        resolveBeer();
        batch.draw(beer, parent.getWidth() / 4, 0);
        batch.draw(right, parent.getWidth() / 4 * 3, 0);
    }

    private void resolveBeer() {
        if (parent.isBeer())
            setBeerIdle(false);
        else if (!beerIdle)
            setBeerIdle(false);
        else
            setBeerIdle(true);
    }

    private void handleInput() {
        //Left
        if (input.x >= 0 && input.x < (parent.getWidth() / 4) && input.y < (parent.getHeight() / 5)) {
            //LEFT
            parent.goLeft();
        } else if (input.x >= (parent.getWidth() / 4) && input.x < ((parent.getWidth() / 4) * 3) && input.y < (parent.getHeight() / 5)) {
            //Button
            parent.pickBeer();
        } else if (input.x >= ((parent.getWidth() / 4) * 3) && input.x <= parent.getWidth() && input.y < (parent.getHeight() / 5)) {
            //Right
            parent.goRight();
        }
    }

    public void setLeftArrowIdle(boolean status) {
        if (status)
            left = leftArrowIdle;
        else
            left = leftArrow;
    }

    public void setRightArrowIdle(boolean status) {
        if (status)
            right = rightArrowIdle;
        else
            right = rightArrow;
    }

    public void setBeerIdle(boolean status) {
        if (status)
            beer = beerIdleButton;
        else
            beer = beerButton;
        beerIdle = status;
    }

    @Override
    public void dispose() {
        leftArrow.dispose();
        rightArrow.dispose();
        beerButton.dispose();
        beerIdleButton.dispose();
        leftArrowIdle.dispose();
        rightArrowIdle.dispose();
    }

    public void setIdle(boolean b) {
        if (b) {
            right = rightArrowIdle;
            left = leftArrowIdle;
            beer = beerButton;
        } else {
            right = rightArrow;
            left = leftArrow;
            beer = beerIdleButton;
        }
    }
}
