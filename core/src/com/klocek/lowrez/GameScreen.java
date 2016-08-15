package com.klocek.lowrez;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Konrad on 2016-08-15.
 */
public abstract class GameScreen extends ScreenAdapter{

    private static final int WIDTH = 512, HEIGHT = 512 + 128;

    private SpriteBatch batch;
    private LowRez game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Controls controls;

    public GameScreen(LowRez game) {
        this.game = game;
        batch = new SpriteBatch();
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, getWidth(), getHeight());
        camera.update();

        controls = new Controls(this);
        viewport = new StretchViewport(getWidth(), getHeight(), camera);
    }


    public abstract void goLeft();
    public abstract void goRight();
    public abstract void pickBeer();

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public boolean isBeer() {
        return false;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public LowRez getGame() {
        return game;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Controls getControls() {
        return controls;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
