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
    private Main gameManager;
    private Vector3 input;

    public Controls(Main gameManager) {
        this.batch = gameManager.getBatch();
        this.camera = gameManager.getCamera();
        this.gameManager = gameManager;

        input = new Vector3();
        leftArrow = new Texture(Gdx.files.internal("leftArrow.png"));
        rightArrow = new Texture(Gdx.files.internal("rightArrow.png"));
        beerButton = new Texture(Gdx.files.internal("beerButton.png"));
        beerIdleButton = new Texture(Gdx.files.internal("beerIdleButton.png"));
        leftArrowIdle = new Texture(Gdx.files.internal("leftArrowIdle.png"));
        rightArrowIdle = new Texture(Gdx.files.internal("rightArrowIdle.png"));
    }

    public void update(float delta) {
        if(Gdx.input.justTouched()) {
            input.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            input = camera.unproject(input);
            //Checking buttons
            handleInput();
        } else {
            handleKeyboardInput();
        }

        if(gameManager.getPlayer().isFirstBeerLane())
            left = leftArrowIdle;
        else
            left = leftArrow;

        if(gameManager.getPlayer().isLastBeerLane())
            right = rightArrowIdle;
        else
            right = rightArrow;

        batch.draw(left, 0, 0);
        if(gameManager.isBeer())
            batch.draw(beerButton, gameManager.getWidth() / 4, 0);
        else
            batch.draw(beerIdleButton, gameManager.getWidth() / 4, 0);
        batch.draw(right, gameManager.getWidth() / 4 * 3, 0);
    }

    private void handleKeyboardInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            gameManager.getPlayer().goLeft();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            gameManager.getPlayer().goRight();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameManager.getPlayer().pickBeer();
        }
    }

    private void handleInput() {
        //Left
        if(input.x >= 0 && input.x < (gameManager.getWidth() / 4) && input.y < (gameManager.getHeight() / 5)) {
            //LEFT
            gameManager.goLeft();
        } else if(input.x >= (gameManager.getWidth() / 4) && input.x < ((gameManager.getWidth() / 4) * 3) && input.y < (gameManager.getHeight() / 5)) {
            //Button
            gameManager.pickBeer();
        } else if(input.x >= ((gameManager.getWidth() / 4) * 3) && input.x <= gameManager.getWidth()  && input.y < (gameManager.getHeight() / 5)) {
            //Right
            gameManager.goRight();
        }
    }

    @Override
    public void dispose() {

    }
}
