package com.klocek.lowrez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Konrad on 2016-04-17.
 */
public class About extends ScreenAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private LowRez game;
    private Texture aboutTexture;

    public About(LowRez lowRez) {
        this.game = lowRez;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameConstants.WIDTH, GameConstants.HEIGHT);
        camera.update();
        viewport = new StretchViewport(GameConstants.WIDTH, GameConstants.HEIGHT, camera);
        aboutTexture = new Texture(Gdx.files.internal("about.png"));
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        Gdx.gl.glClearColor(255,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        viewport.apply();
        batch.begin();
        batch.draw(aboutTexture, 0, 128);
        batch.end();

        if(Gdx.input.justTouched()) {
            game.backToMenu();
        }
    }

    @Override
    public void dispose() {
        aboutTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
