package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private Texture leftArrow, rightArrow, beerButton, beerIdleButton, leftArrowIdle, rightArrowIdle, left, right;
    private GameScreen parent;
    private Vector3 input;
    private boolean beerIdle = false;

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
        if(Gdx.input.justTouched()) {
            input.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            input = camera.unproject(input);
            //Checking buttons
            handleInput();
        }

        batch.draw(left, 0, 0);
        if(parent.isBeer() && !beerIdle)
            batch.draw(beerButton, parent.getWidth() / 4, 0);
        else
            batch.draw(beerIdleButton, parent.getWidth() / 4, 0);
        batch.draw(right, parent.getWidth() / 4 * 3, 0);
    }

    private void handleInput() {
        //Left
        if(input.x >= 0 && input.x < (parent.getWidth() / 4) && input.y < (parent.getHeight() / 5)) {
            //LEFT
            parent.goLeft();
        } else if(input.x >= (parent.getWidth() / 4) && input.x < ((parent.getWidth() / 4) * 3) && input.y < (parent.getHeight() / 5)) {
            //Button
            parent.pickBeer();
        } else if(input.x >= ((parent.getWidth() / 4) * 3) && input.x <= parent.getWidth()  && input.y < (parent.getHeight() / 5)) {
            //Right
            parent.goRight();
        }
    }

    public void setLeftArrowIdle(boolean status) {
        if(status)
            left = leftArrowIdle;
        else
            left = leftArrow;
    }

    public void setRightArrowIdle(boolean status) {
        if(status)
            right = rightArrowIdle;
        else
            right = rightArrow;
    }

    @Override
    public void dispose() {

    }

    public void setIdle(boolean b) {
        if(b){
            right = rightArrowIdle;
            left = leftArrowIdle;
        } else {
            right = rightArrow;
            left = leftArrow;
        }
        beerIdle = b;
    }
}
