package com.klocek.lowrez;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Konrad on 2016-05-01.
 */
public class Pause implements Disposable {

    private Texture buttonTexture;
    private Texture pausedTexture;
    private Game gameManager;
    private SpriteBatch batch;
    private Camera camera;
    private Vector3 input;
    private boolean isPaused = false;

    public Pause(Game gameManager) {
        this.gameManager = gameManager;
        batch = gameManager.getBatch();
        camera = gameManager.getCamera();
        buttonTexture = new Texture(Gdx.files.internal("pause.png"));
        pausedTexture = new Texture(Gdx.files.internal("paused.png"));
        input = new Vector3();
    }

    public void update(float delta) {
        if (isPaused)
            batch.draw(pausedTexture, 160, 236 + 128);

        batch.draw(buttonTexture, 8, GameConstants.HEIGHT - 8 - buttonTexture.getHeight());
        if (Gdx.input.justTouched()) {
            input.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            input = camera.unproject(input);
            if (input.x >= 8 && input.x <= 8 + buttonTexture.getWidth() && input.y <= GameConstants.HEIGHT && input.y >= GameConstants.HEIGHT - buttonTexture.getHeight()) {
                isPaused = !isPaused;
            }
        }

    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    @Override
    public void dispose() {
        buttonTexture.dispose();
        pausedTexture.dispose();
    }
}
